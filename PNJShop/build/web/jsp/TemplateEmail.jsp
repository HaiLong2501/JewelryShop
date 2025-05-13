<%-- 
    Document   : TemplateEmail
    Created on : Mar 19, 2025, 5:36:44 PM
    Author     : Hai Long
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <style>

            .content{

            }

            h1, h2{
                color: #004B8D;
            }

            table{
                font-size: 18px;
            }

            .cart-total{
                display: flex;
                flex-direction: column;
                gap:10px;
            }

            .before-discount{
                color: #707070;
                display: flex;
                justify-content: space-between;
            }

            .before-discount i{
                display: flex;
                align-items: center;
            }

            .discount, .after-discount{
                display: flex;
                justify-content: space-between;
                color: #003468;
                font-size: 18px;
            }
        </style>
    </head>
    <body>
        <div class="content" style="max-width: 600px;margin: 0px auto;">
            <h1 style="color: #004B8D;">Xin chào </h1>
            <h2 style="color: #004B8D;">Cảm ơn bạn đã đặt hàng tại PNJ Shop</h2>
            <h2 style="color: #004B8D;">Hãy kiểm tra đơn hàng của bạn</h2>
            <div class="order-info">
                <table border="1" cellspacing="0" style="font-size: 18px;">
                    <thead>
                        <tr>
                            <th>Tên sản phẩm</th>
                            <th>Giá gốc</th>
                            <th>Số lượng</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr>
                            <td>
                                Bông tai Vàng trắng 10K đính đá ECZ PNJ XM00W000131

                            </td>
                            <td>2.583.000 đ</td>
                            <td>2</td>
                        </tr>
                        <tr>
                            <td>
                                Lắc tay Kim cương Vàng 14K Disney|PNJ Sleeping Beauty DDDDY000032

                            </td>
                            <td>8.989.345 đ</td>
                            <td>1</td>
                        </tr>
                        <tr>
                            <td colspan="3"><strong>Tạm tính: </strong>3.123.123 đ</td>
                            
                        </tr>
                        <tr>
                            <td colspan="3"><strong>Giảm giá: </strong>- 32131 đ</td>
                            
                        </tr>
                        <tr>
                            <td colspan="3"><strong>Tổng tiền: </strong>321312123 đ</td>
                            
                        </tr>
                    </tbody>
                </table>
                
            </div>
        </div>
    </body>
</html>
