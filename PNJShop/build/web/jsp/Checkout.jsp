<%-- 
    Document   : Checkout
    Created on : Mar 11, 2025, 5:08:23 PM
    Author     : Hai Long
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="model.Product, model.Product, model.Cart, java.util.Vector, java.text.DecimalFormat" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <% Vector<Cart> list = (Vector<Cart>) request.getAttribute("data"); 
        
        Product p = (Product)request.getAttribute("product");
        
     DecimalFormat df = new DecimalFormat("#,###");
     
     String error = (String)request.getAttribute("error");
    %>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"/>
        <link rel="stylesheet" href="<%= request.getContextPath()%>/style/Checkout.css"/>
    </head>
    <body>
        <%@include file="Header.jsp" %>

        <div class="cut"><hr/></div>

        <div class="checkout">
            <div class="checkout-content">
                <form id="checkoutForm" method="get" onsubmit="updateFormAction()">

                    <div class="checkout-title">
                        <h2>Thông tin đặt hàng</h2>
                    </div>
                    <% double totalPrice = 0, totalDiscount = 0; %>
                    <div class="product-info">
                        <% if(p != null) { %>
                        <div class="product-detail">
                            <div class="detail-img">
                                <a href="">
                                    <img src="<%= request.getContextPath()%>/images/products/<%= p.getImage() %>" alt="<%= p.getProductName() %>"/>
                                </a>
                            </div>
                            <div class="detail-right">
                                <div class="detail-info">
                                    <a href="">
                                        <p class="product-name"><%= p.getProductName() %></p>
                                        <span class="product-id">Mã: <%= p.getProductID() %></span>
                                        <input type="hidden" name="pId" value="<%= p.getProductID()%>">

                                    </a>
                                    <div class="buy-amount">
                                        <label for="amount">Số lượng: </label>
                                        <input id="input-amount" type="text" readonly="" name="amount" value="<%= p.getQuantity()%>">

                                    </div>
                                    <div class="item-total">
                                        <span>Đơn giá:</span> <%= df.format(p.getPrice()) %> ₫
                                    </div>
                                    <% totalPrice = p.getPrice(); totalDiscount += p.getPrice() * p.getDiscount() / 100; %>

                                </div>

                            </div>

                        </div>
                        <% }else{
                            if(list != null){
                            for(Cart c: list){
                        %>
                        <div class="product-detail">
                            <div class="detail-img">
                                <a href="">
                                    <img src="<%= request.getContextPath()%>/images/products/<%= c.getImage() %>" alt="<%= c.getProductName() %>"/>
                                </a>
                            </div>
                            <div class="detail-right">
                                <div class="detail-info">
                                    <a href="">
                                        <p class="product-name"><%= c.getProductName() %></p>
                                        <span class="product-id">Mã: <%= c.getProductID() %></span>
                                        <input type="hidden" name="pId" value="<%= c.getProductID() %>">
                                    </a>
                                    <div class="buy-amount">
                                        <label for="amount">Số lượng: </label>
                                        <input id="input-amount" type="text" readonly="" name="amount" value="<%= c.getQuantity()%>">

                                    </div>
                                    <div class="item-total">
                                        <span>Đơn giá:</span> <%= df.format(c.getPrice()) %> ₫
                                    </div>
                                    <% totalPrice += c.getPrice() * c.getQuantity(  ); totalDiscount += c.getPrice() * c.getDiscount() / 100 * c.getQuantity(); %>

                                </div>

                            </div>

                        </div>
                        <% } %>
                        <% } %>
                        <% } %>

                        <div class="product-total">

                            <div class="before-discount">
                                <i>Tạm tính</i>
                                <p><%= df.format(totalPrice) %> đ</p>
                            </div>
                            <div class="ship">
                                <p>Giao hàng</p>
                                <p>Miễn phí</p>
                            </div>
                            <div class="discount">
                                <p>Giảm giá</p>
                                <p>- <%= df.format(totalDiscount) %> đ</p>
                            </div>
                            <div class="after-discount">
                                <p>Tổng tiền</p>
                                <p><%= df.format(totalPrice - totalDiscount) %> ₫</p>
                                <input type="hidden" name="total" value="<%= totalPrice - totalDiscount %>">
                            </div>
                        </div>
                    </div>
                            
                                <c:if test="${sessionScope.user != null}">
                                    <c:set var="u" value="${sessionScope.user}"/>
                        <div class="customer-info">
                            <h2>Thông tin người mua</h2>
                            <p style="color: red;">Vui lòng kiểm tra lại thông tin (họ tên, email, số điện thoại) hoặc sửa lại theo ý muốn</p>
                            <div class="gender">
                                <div class="gender_content">
                                    <input name="customer_gender" value="Male" ${param.customer_gender.equals("Male") ? 'checked' : ''} type="radio" />
                                    <label for="male">Anh</label>
                                </div>

                                <div class="gender_content">
                                    <input name="customer_gender" value="Female" ${param.customer_gender.equals("Female") ? 'checked' : ''} type="radio" />
                                    <label for="female">Chị</label>
                                </div>
                            </div>
                               
                        <c:if test="${requestScope.errorGender != null}">
                            <div class="error">${requestScope.errorGender}</div>
                        </c:if>
                        <div class="info_input">
                            <div class="form_row">
                                <div class="checkout_detail">
                                    <label style="display: none" for="fullname">Họ tên</label>
                                    <input placeholder="Họ tên *" 
                                           type="text" 
                                           name="customer_fullname" 
                                           value="${u.name != null ? u.name : param.customer_fullname}" />
                                </div>
                                <div class="checkout_detail">
                                    <label style="display: none" for="phone">SĐT</label>
                                    <input placeholder="SĐT *" 
                                           type="number" id="phone" 
                                           name="customer_phone" 
                                           value="${u.phone != null ? u.phone : param.customer_phone}"/>
                                </div>
                            </div>
                            <div class="form_row">
                                <div class="checkout_detail">
                                    <label style="display: none" for="email">Email:</label>
                                    <input type="text" 
                                           id="email" 
                                           name="customer_email" 
                                           placeholder="Email *" 
                                           value="${u.email != null ? u.email : param.customer_email}"/>
                                </div>
                            </div>
                        </div>
                        </div>
                                </c:if> 
                        <c:if test="${requestScope.errorInfo != null}">
                            <div class="error">${requestScope.errorInfo} </div>
                        </c:if>
                        
                        <div class="ship-type">
                            <ul class="nav nav-pills mb-3" id="pills-tab" role="tablist">
                                <li class="nav-item" role="presentation">
                                    <button class="nav-link active" id="pills-home-tab" data-bs-toggle="pill" data-bs-target="#pills-home" type="button" role="tab" aria-controls="pills-home" aria-selected="true">
                                        <div class="ship-title">
                                            <img src="<%= request.getContextPath()%>/images/ship-type/delivery-bike.svg" alt="in_store.png"/>
                                            <div class="title">
                                                <p>Giao hàng tận nơi</p>
                                            </div>

                                        </div>
                                    </button>
                                </li>
                                <li class="nav-item" role="presentation">
                                    <button class="nav-link" id="pills-profile-tab" data-bs-toggle="pill" data-bs-target="#pills-profile" type="button" role="tab" aria-controls="pills-profile" aria-selected="false">
                                        <div class="ship-title">
                                            <img style="height: 33px; width: 33px;" src="<%= request.getContextPath()%>/images/ship-type/in_store.png" alt="in_store.png"/>
                                            <div class="title">
                                                <p>Nhận tại cửa hàng</p>
                                            </div>

                                        </div>
                                    </button>
                                </li>
                            </ul>
                            <div class="tab-content" id="pills-tabContent">
                                <div class="tab-pane fade show active" id="pills-home" role="tabpanel" aria-labelledby="pills-home-tab" tabindex="0">

                                    <div class="address">
                                        <div class="info_input">
                                            <div class="form_row">
                                                <div class="checkout_detail">
                                                    <label style="display: none" for="province">Tỉnh thành</label>
                                                    <input value="${param.province != null ? param.province : ''}" placeholder="Tỉnh/Thành *" type="text" name="province"/>
                                                </div>
                                                <div class="checkout_detail">
                                                    <label style="display: none" for="ward">Xã</label>
                                                    <input value="${param.ward != null ? param.ward : ''}" placeholder="Xã *" type="text" name="ward" />
                                                </div>
                                            </div>
                                            <div class="form_row">
                                                <div class="checkout_detail">
                                                    <label style="display: none" for="district">Huyện</label>
                                                    <input value="${param.district != null ? param.district : ''}" type="text" name="district" placeholder="Huyện *" />
                                                </div>
                                                <div class="checkout_detail">
                                                    <label style="display: none" for="village">Thôn</label>
                                                    <input value="${param.village != null ? param.village : ''}" type="text" placeholder="Tòa nhà/Số nhà/Thôn" name="village" />
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="tab-pane fade" id="pills-profile" role="tabpanel" aria-labelledby="pills-profile-tab" tabindex="0">
                                    <div class="address">
                                        <div class="info_input">
                                            <div class="form_row">
                                                <div class="checkout_detail">
                                                    <label style="display: none" for="province">Tỉnh thành</label>
                                                    <input placeholder="Tỉnh/Thành *" type="text"/>
                                                </div>
                                            </div>
                                            <div class="form_row">
                                                <div class="checkout_detail">
                                                    <label style="display: none" for="district">Huyện</label>
                                                    <input type="text" placeholder="Huyện *" />
                                                </div>                                               
                                            </div>
                                        </div>
                                        <div class="alert">
                                            <i style="color: red;">Rất tiếc! 
                                                Hệ thống chưa tìm được Cửa hàng phù hợp để Quý khách đến lấy hàng cho tất cả sản phẩm. 
                                                Vui lòng thử lại sau hoặc chọn hình thức Giao hàng tận nơi.
                                            </i>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <c:if test="${requestScope.errorAddress != null}">
                            <div class="error">${requestScope.errorAddress} </div>
                        </c:if>



                    
                    <div class="pay-type">
                        <h2>Phương thức thanh toán</h2>
                        <div class="pay-detail">
                            <div class="cod">
                                <input type="radio" name="pay_type" value="cod" ${param.pay_type == 'cod' ? 'checked' : ''}>Thanh toán tiền mặt khi nhận hàng(COD)
                            </div>
                            <div class="online">
                                <input type="radio" name="pay_type" value="online" ${param.pay_type == 'online' ? 'checked' : ''}>Thanh toán chuyển khoản online
                            </div>
                        </div>
                    </div>
                    <c:if test="${requestScope.errorPay != null}">
                        <div class="error">${requestScope.errorPay}</div>
                    </c:if>
                        <div style="margin: 20px 0px; color: red;" class="captcha">
                                                        <div class="g-recaptcha" data-sitekey="6LfXuv8qAAAAADSl66h76G3PIVKdifIQ990ZGVhm"></div>
                                                        <div  id="error"></div>
                                                    </div>
                    <input type="hidden" name="submit" value="addOrder">
                    <button onclick="payment()" class="finish" type="submit">Đặt hàng</button>
                </form>
            </div>

            <div class="cut"><hr/></div>

            <%@include file="Footer.jsp" %>
    </body>
    <script src="https://www.google.com/recaptcha/api.js" async defer></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
    <script>
        
        document.querySelector("form").addEventListener("submit", function(event) {
        let response = grecaptcha.getResponse(); 
        if (response.length === 0) { 
            event.preventDefault(); 
            document.getElementById("error").innerHTML = "Vui lòng xác thực reCAPTCHA trước khi đặt hàng.";
        }
    });
        function updateFormAction() {
        var paymentType = document.querySelector('input[name="pay_type"]:checked').value;
        var form = document.getElementById('checkoutForm');

        if (paymentType === 'cod') {
            form.action = 'checkout';
        } else if (paymentType === 'online') {
            form.action = 'payment';
        }
    }
        console.log('<%= list %>');
        console.log('<%= p %>');
        function search() {
            let key = document.querySelector(".search_input").value.trim();

            if (key !== "") {
                window.location.href = '<%= request.getContextPath()%>/list?key=' + key;
            }
        }
        function logout(action, e) {
            e.preventDefault();
            let currentUrl = "<%= request.getContextPath()%>/home";
            window.location.href = "<%= request.getContextPath() %>/login?service=logout&redirectUrl=" + encodeURIComponent(currentUrl);
        }
    </script>
</html>
