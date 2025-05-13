<%-- 
    Document   : ProductDetail
    Created on : Mar 9, 2025, 9:03:26 AM
    Author     : Hai Long
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="model.Product,model.User, model.ProductDetail, model.Brand, java.text.DecimalFormat" %>
<!DOCTYPE html>
<html>
    <% 
       Product p = (Product) request.getAttribute("product");
       
       ProductDetail pd = (ProductDetail) request.getAttribute("productDetail");
       
       Brand b = (Brand)request.getAttribute("brand");


    %>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.7.2/css/all.min.css"/>
        <link rel="stylesheet" href="<%= request.getContextPath() %>/style/ProductDetail.css"/>
    </head>
    <body>
        <%@include file="Header.jsp" %>

        <hr/>

        <div class="product-detail">
            <div class="detail-content">
                <div class="product-describe">
                    <div class="product-image">
                        <img style="height: 500px; width: 500px;" src="<%= request.getContextPath() %>/images/products/<%= p.getImage() %>" alt="alt"/>
                    </div>
                    <div class="describe-detail">
                        <h1>Mô tả sản phẩm</h1>
                        <p>Trọng lượng tham khảo: <%= pd.getWeight()%> phân</p>
                        <% if(pd.getMainStone() != null){%>
                        <p>Loại đá chính: <%= pd.getMainStone()%></p>
                        <% } else { %>
                        <p>Loại đá chính: Không gắn đá</p>
                        <% } %>
                        <% if(pd.getSecondaryStone() != null){%>
                        <p>Loại đá phụ: <%= pd.getSecondaryStone()%></p>
                        <% } else { %>
                        <p>Loại đá phụ: Không gắn đá</p>
                        <% } %>
                        <p>Giới tính: <%= pd.getGender() %></p>
                        <p>Thương hiệu: <%= b.getBrandName()%></p>
                    </div>
                </div>

                <div class="product-info">
                    <div class="product-name">
                        <h3 class="change-color"><%= p.getProductName() %></h3>
                    </div>
                    <div class="product-id">Mã: <%= p.getProductID() %></div>

                    <div class="product-price">
                        <% DecimalFormat df = new DecimalFormat("#,###"); %>
                        <% if(p.getDiscount() == 0) { %>
                        <p class="price change-color"><%= df.format(p.getPrice()) %>&nbsp;đ</p>
                        <p class="tra-gop">Chỉ cần trả <%= df.format(p.getPrice() / 12) %> ₫/tháng</p>
                        <% }else{ %>
                        <p class="price change-color"><%= df.format(p.getPrice() - p.getPrice() * p.getDiscount() / 100) %>&nbsp;đ<span><del><%= df.format(p.getPrice()) %>&nbsp;đ</del></span></p>

                        <p class="tra-gop">Chỉ cần trả <%= df.format((p.getPrice() - p.getPrice() * p.getDiscount() / 100) / 12) %> ₫/tháng</p>
                        <% } %>

                    </div>

                    <i>(Giá sản phẩm thay đổi tùy trọng lượng vàng và đá)</i>
                    
                    
                    

                    <% if(p.getStatus() == 0 || !(p.getQuantity() > 0)) { %>
                    <h2 class="shop-mess">(Hiện tại cửa hàng đang tạm ngừng kinh doannh sản phẩm này)</h2>
                    <% }else{ %>
                    <p>Số lượng hiện có: <%= p.getQuantity() %></p>
                    <% } %>
                    <div class="endow">
                        <p>Ưu đãi:</p>
                        <div class="endow-info">
                            <p>
                                <i class="fa-solid fa-circle-check"></i>
                                Cơ hội sở hữu bộ quà tặng phiên bản Premium giới hạn PNJ & LANCÔME
                            </p>
                            <p>
                                <i class="fa-solid fa-circle-check"></i>
                                Ưu đãi đến 1.3 triệu và quà tặng trang sức đến 2 triệu
                            </p>
                        </div>
                    </div>

                    <div class="service">
                        <div class="service-item">
                            <img src="<%= request.getContextPath()%>/images/service-detail/shipping-icon.svg" alt="Miễn phí giao hàng​"/>
                            <p>Miễn phí giao hàng​</p>
                        </div>
                        <div class="service-item">
                            <img src="<%= request.getContextPath()%>/images/service-detail/shopping.svg" alt="Phục vụ 24/7"/>
                            <p>Phục vụ 24/7​</p>
                        </div>
                        <div class="service-item">
                            <img src="<%= request.getContextPath()%>/images/service-detail/thudoi-icon.svg" alt="Thu đổi 48h​"/>
                            <p>Thu đổi 48h​​</p>
                        </div>
                    </div>
                    <div class="buy">
                        <div class="buy-now" onclick="checkLogin('buy', event)">
                            <a href="">
                                <p>Mua ngay</p>
                                <i>(Giao hàng miễn phí hoặc nhận tại cửa hàng)</i>
                            </a>
                        </div>
                        <div class="add-and-call">
                            <div class="add" onclick="checkLogin('cart', event)"><a href="<%= request.getContextPath() %>?service=addCart&pId=<%= p.getProductID() %>">Thêm vào giỏ hàng</a></div>
                            <div class="call"><a href="">Gọi ngay (miễn phí)</a></div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <hr/>
        <%@include file="Footer.jsp" %>
    </body>
    <script>
        function checkLogin(action, e) {
            e.preventDefault();
            let isLoggedIn = <%= u != null%>;

            let mess = <%= p.getStatus() == 0 || !(p.getQuantity() > 0) %>;

            console.log(isLoggedIn);

            if (!isLoggedIn) {
                if (mess) {
                    if(action === 'buy')alert("Sản phẩm này không thể mua!");
                    if(action === 'cart') alert("Sản phẩm này không thể thêm vào giỏ hàng!");
                } else {
                    let currentUrl = window.location.href;
                    alert("Vui lòng đăng nhập để tiếp tục!");
                    window.location.href = "<%= request.getContextPath() %>/login?service=login&redirectUrl=" + encodeURIComponent(currentUrl);
                }
            } else {
                if (action === 'cart') {
                    if (mess) {
                        alert("Sản phẩm này không thể thêm vào giỏ hàng!");
                    } else {
                        alert("Thêm vào giỏ hàng thành công");
                        let currentUrl = window.location.href;
                        window.location.href = "<%= request.getContextPath() %>/cart?service=addCart&pId=<%= p.getProductID() %>&redirectUrl=" + encodeURIComponent(currentUrl);
                    }
                }

                if (action === 'buy') {
                    if (mess) {
                        alert("Sản phẩm này không thể mua!");
                    } else {
                        window.location.href = "<%= request.getContextPath()%>/checkout?service=addFromDetail&pId=<%= p.getProductID() %>";
                                        }
                                    }
                                }


                            }
                            function search() {
                                let key = document.querySelector(".search_input").value.trim();

                                if (key !== "") {
                                    window.location.href = '<%= request.getContextPath()%>/list?key=' + key;
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
