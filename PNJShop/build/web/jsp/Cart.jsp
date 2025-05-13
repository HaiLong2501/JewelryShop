<%-- 
    Document   : Cart
    Created on : Mar 10, 2025, 10:36:47 AM
    Author     : Hai Long
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="model.Cart, java.util.Map, java.util.Vector, java.text.DecimalFormat" %>
<!DOCTYPE html>
<html>
    <% Vector<Cart> list = (Vector<Cart>) request.getAttribute("data"); 
     DecimalFormat df = new DecimalFormat("#,###"); 
     Map<String, Integer> listQuantity = (Map<String, Integer>)request.getAttribute("listQuantity");
     String id = "";
    %>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link rel="stylesheet" href="<%= request.getContextPath()%>/style/Cart.css"/>
    </head>
    <body>
        <%@include file="Header.jsp" %>

        <div class="cut">
            <hr/>
        </div>
        <form action="checkout" method="get">
            <div class="cart">
                <div class="cart-content">
                    <% if(list.size() == 0) { %>
                    <h2>Giỏ hàng đang trống</h2>
                    <% }else{ %>
                    <h2>Giỏ hàng</h2>
                    <% 
                    int count = 0;
                    for(Cart c: list){
                        count += c.getQuantity();
                    }
                    %>

                    <div class="product-nums">
                        <p>Tất cả (<%= count%> sản phẩm)</p>
                        <a href="cart?service=removeAll" onclick="return confirm('Bạn có chắc muốn xóa toàn bộ sản phẩm ra khỏi giỏ hàng không')">Xóa tất cả</a>
                    </div>
                    <% double total = 0, totalDiscount = 0;%>
                    <% for(Cart c: list) { %>
                    <div class="cart-detail">
                        <div class="detail-img">
                            <% id = c.getProductID(); %>
                            <a href="<%= request.getContextPath()%>/detail?pId=<%= c.getProductID() %>">
                                <img src="<%= request.getContextPath() %>/images/products/<%= c.getImage() %>" alt="alt"/>
                            </a>
                        </div>
                        <div class="detail-right">
                            <div class="detail-info">
                                <a href="<%= request.getContextPath()%>/detail?pId=<%= c.getProductID() %>">
                                    <p class="product-name"><%= c.getProductName() %></p>
                                    <span class="product-id">Mã: <%= c.getProductID() %></span>
                                </a>
                                <div class="buy-amount">
                                    <button type="button" class="minus" onclick="minus('<%= c.getProductID() %>')">-</button>
                                    <input 
                                        id="input-amount-<%= c.getProductID() %>" 
                                        type="text" 
                                        readonly="" 
                                        name="amount_<%= c.getProductID() %>" 
                                        value="<%= c.getQuantity()%>"
                                        data-price="<%= c.getPrice() %>" 
                                        data-discount="<%= c.getDiscount() %>">
                                    <button class="plus" type="button" onclick="plus('<%= c.getProductID() %>',<%= listQuantity.get(c.getProductID()) %>)">+</button>
                                </div>
                                <div class="item-total">
                                    <%= df.format(c.getPrice())%> ₫
                                </div>
                                <% total +=c.getPrice() * c.getQuantity(); %>
                                <% totalDiscount += (c.getPrice() * c.getDiscount() / 100) * c.getQuantity();%>

                            </div>
                            <div class="delete">
                                <a onclick="return confirm('Bạn có chắc muốn sản phẩm ra khỏi giỏ hàng không?');" href="cart?service=removeItem&pId=<%= c.getProductID() %>">Xóa</a>
                            </div>
                        </div>

                    </div>
                    <% } %>
                    <div class="cart-total">

                        <div class="before-discount">
                            <i>Tạm tính</i>
                            <p><%= df.format(total)%> đ</p>
                        </div>
                        <div class="discount">
                            <p>Giảm giá</p>
                            <p>- <%= df.format(totalDiscount)%> đ</p>
                        </div>
                        <div class="after-discount">
                            <p>Tổng tiền</p>
                            <p><%= df.format(total - totalDiscount)  %> ₫</p>
                        </div>
                    </div>

                    <% if(list.size() > 0){ %>
                    <!--                <a class="continue" href="checkout?service=addFromCart">Tiếp tục</a>-->
                    <input type="hidden" name="service" value="addFromCart">
                    <input class="continue" type="submit" value="Tiếp tục"/>
                    <% } %>
                    <% } %>
                </div>

            </div>
        </form>
        <div class="cut">
            <hr/>
        </div>

        <%@include file="Footer.jsp" %>
    </body>
    <script>
        console.log('<%= list %>');


        function plus(id, quantity) {
            let amountElement = document.getElementById('input-amount-' + id);
            let amount = amountElement.value;
            if (amount < quantity) {
                amount++;
            amountElement.value = amount;
            updateTotal();
            }
           

        }
        function minus(id) {
            let amountElement = document.getElementById('input-amount-' + id);
            let amount = amountElement.value;
             if (amount > 1) {
            amount--;
            amountElement.value = amount;
            updateTotal();
        }
        }
        
        function updateTotal() {
        let total = 0;
        let totalDiscount = 0;

        
        let amountInputs = document.querySelectorAll("input[name^='amount_']");
        
        amountInputs.forEach(input => {
            let quantity = parseInt(input.value);
            let price = parseFloat(input.getAttribute("data-price"));
            let discount = parseFloat(input.getAttribute("data-discount"));

            total += price * quantity;
            totalDiscount += (price * discount / 100) * quantity;
        });

        
        document.querySelector(".before-discount p").innerText = formatCurrency(total) + " đ";
        document.querySelector(".discount p:last-child").innerText = "- " + formatCurrency(totalDiscount) + " đ";
        document.querySelector(".after-discount p:last-child").innerText = formatCurrency(total - totalDiscount) + " đ";
    }

    
    function formatCurrency(value) {
        return new Intl.NumberFormat('vi-VN').format(value);
    }


        function search() {
            let key = document.querySelector(".search_input").value.trim();

            if (key !== "") {
                window.location.href = '<%= request.getContextPath()%>/list?key=' + key;
            }
        }
        function login(action, e) {
            e.preventDefault();
            let currentUrl = window.location.href;
            window.location.href = "<%= request.getContextPath() %>/login?service=login&redirectUrl=" + encodeURIComponent(currentUrl);
        }
        function logout(action, e) {
            e.preventDefault();
            let currentUrl = "<%= request.getContextPath()%>/home";
            window.location.href = "<%= request.getContextPath() %>/login?service=logout&redirectUrl=" + encodeURIComponent(currentUrl);
        }
    </script>
</html>
