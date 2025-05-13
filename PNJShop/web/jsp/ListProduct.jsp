<%-- 
    Document   : ListProduct
    Created on : Mar 9, 2025, 8:52:11 AM
    Author     : Hai Long
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="model.Product, java.util.Vector, java.text.DecimalFormat" %>
<!DOCTYPE html>
<html>
    <% 
    Vector<Product> listProd = (Vector<Product>)request.getAttribute("listProd");
    %>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link rel="stylesheet" href="<%= request.getContextPath()%>/style/ListProduct.css"/>
    </head>
    <body>
        <%@include file="Header.jsp" %>

        <div class="cut">
            <hr/>
        </div>

        <div class="product">
            <div class="list-content">
                <% for(Product p: listProd) { %>
                <div class="product-item">
                    <a class="products-detail" href="detail?pId=<%= p.getProductID() %>">
                        <div class="product-img">
                            <img src="<%= request.getContextPath()%>/images/products/<%= p.getImage() %>" alt="<%= p.getProductName() %>">
                        </div>
                        <div class="product-info">
                                    <% DecimalFormat df = new DecimalFormat("#,###"); %>
                                    <p class="product-name"><%= p.getProductName() %></p>
                                    <%if (p.getDiscount() > 0) { %>
                                    <p class="price"><%= df.format(p.getPrice() - p.getPrice()*p.getDiscount()/100) %>&nbsp;đ</p>
                                    <del class="product-price"><%= df.format(p.getPrice()) %>&nbsp;đ</del>
                                    <% }else{ %>
                                    <p class="price"><%= df.format(p.getPrice()) %>&nbsp;đ</p>
                                    <% } %>
                                </div>
                    </a>
                </div>
                <% } %>
            </div>
        </div>

        <div class="cut">
            <hr/>
        </div>

        <%@include file="Footer.jsp" %>
    </body>
    <script>
        function search() {
            let key = document.querySelector(".search_input").value.trim();

            if (key !== "") {
                window.location.href = '<%= request.getContextPath()%>/list?key='+key;
            }
        }
//        function search() {
//            let key = document.querySelector(".search_input").value.trim();
//            let hiddenInput = document.getElementById("search-hidden");
//
//            console.log(key);
//
//            if (key !== "") {
//                hiddenInput.value = key;
//                document.getElementById("search-form").submit();
//            }
//        }
        function login(action, e) {
            e.preventDefault();
            let currentUrl = window.location.href;
            console.log(currentUrl);
            window.location.href = "<%= request.getContextPath() %>/login?service=login&redirectUrl=" + encodeURIComponent(currentUrl);
        }
        function logout(action, e) {
            e.preventDefault();
            console.log("Logout");
            let currentUrl = window.location.href;
            window.location.href = "<%= request.getContextPath() %>/login?service=logout&redirectUrl=" + encodeURIComponent(currentUrl);
        }
    </script>
</html>
