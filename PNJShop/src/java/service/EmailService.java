package service;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


import dal.OrderDetailDAO;
import dal.ProductDAO;
import dal.UserDAO;
import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;

import java.util.Properties;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;
import model.OrderDetail;
import model.User;

/**
 *
 * @author Hai Long
 */
public class EmailService {

    private static final String SMTP_HOST = "smtp.gmail.com";
    private static final String SMTP_PORT = "587";
    private static final String SMTP_USER = "jewelryshop898@gmail.com";
    private static final String SMTP_PASSWORD = "fsav ghct tybh jcpf";
//    private static final String SMTP_PASSWORD = "jewelry123";

    public void sendConfirmOrder(User u, Vector<OrderDetail> listProduct) throws AddressException, MessagingException {

        ProductDAO pDao = new ProductDAO();

        Properties props = new Properties();
        props.put("mail.smtp.host", SMTP_HOST);
        props.put("mail.smtp.port", SMTP_PORT);
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(SMTP_USER, SMTP_PASSWORD);
            }
        });

        MimeMessage message = new MimeMessage(session);
        // Mã hóa tên hiển thị trước khi dùng
//            String encodedName = MimeUtility.encodeText(u.getName(), "UTF-8", "B");
        message.setFrom(new InternetAddress(SMTP_USER));

        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(u.getEmail()));

        try {
            message.setSubject(MimeUtility.encodeText("Xác nhận đơn hàng từ PNJ Shop", "UTF-8", "B"));
        } catch (UnsupportedEncodingException ex) {
            ex.printStackTrace();
        }

        double beforeDiscount = 0, discount = 0, afterDiscount = 0;
        StringBuilder htmlContent = new StringBuilder();
        DecimalFormat df = new DecimalFormat("#,###");

        htmlContent.append("<html>");
        htmlContent.append("<body>");
        htmlContent.append("<div>");
        htmlContent.append("<h1 style='color: #004B8D;'>Xin chào ").append(u.getName()).append("</h1>");
        htmlContent.append("<h2 style='color: #004B8D;'>Cảm ơn bạn đã đặt hàng tại PNJ Shop</h2>");
        htmlContent.append("<h2 style='color: #004B8D;'>Đây là đơn hàng của bạn</h2>");
        htmlContent.append("<div>");
        htmlContent.append("<table border='1' cellspacing='0' style='font-size: 18px;'>");
        htmlContent.append("<thead>");
        htmlContent.append("<tr>");
        htmlContent.append("<th>Tên sản phẩm</th>");
        htmlContent.append("<th>Giá gốc</th>");
        htmlContent.append("<th>Số lượng</th>");
        htmlContent.append("</tr>");
        htmlContent.append("</thead>");
        htmlContent.append("<tbody>");
        for (OrderDetail orderDetail : listProduct) {
            beforeDiscount += orderDetail.getPrice();
            discount += (orderDetail.getPrice()- orderDetail.getTotalPrice());
            afterDiscount += orderDetail.getTotalPrice();
            htmlContent.append("<tr>");
            htmlContent.append("<td>").append(pDao.searchProduct(orderDetail.getProductID()).getProductName()).append("</td>");
            htmlContent.append("<td>").append(df.format(orderDetail.getPrice())).append("</td>");
            
            htmlContent.append("<td>").append(orderDetail.getQuantity()).append("</td>");
            htmlContent.append("</tr>");
        }
        htmlContent.append("<tr>");
        htmlContent.append("<td colspan='3'><strong>Tạm tính: </strong>").append(df.format(beforeDiscount)).append(" đ</td>");
        htmlContent.append("</tr>");
        htmlContent.append("<tr>");
        htmlContent.append("<td colspan='3'><strong>Giảm giá: </strong>-").append(df.format(discount)).append(" đ</td>");
        htmlContent.append("</tr>");
        htmlContent.append("<tr>");
        htmlContent.append("<td colspan='3'><strong>Tổng tiền: </strong>").append(df.format(afterDiscount)).append(" đ</td>");
        htmlContent.append("</tr>");
        
        htmlContent.append("</tbody>");
        htmlContent.append("</table>");
        
        htmlContent.append("</div>");
        htmlContent.append("</div>");
        htmlContent.append("</body>");
        htmlContent.append("</html>");

        MimeBodyPart bodyPart = new MimeBodyPart();
        bodyPart.setContent(htmlContent.toString(), "text/html; charset=UTF-8");

        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(bodyPart);

        message.setContent(multipart);

        Transport.send(message);
    }

    public static void main(String[] args) {
        UserDAO uDao = new UserDAO();

        OrderDetailDAO odDao = new OrderDetailDAO();

        User u = uDao.checkEmail("longx3732@gmail.com");

        System.out.println(u.getName());

        EmailService service = new EmailService();

        Vector<OrderDetail> list = odDao.getOrderDetailsByOrderID(20);

        try {
            service.sendConfirmOrder(u, list);
            System.out.println("Đã được gửi");
        } catch (MessagingException ex) {
            ex.printStackTrace();
            System.out.println("Gửi thất bại");
        }
    }
}
