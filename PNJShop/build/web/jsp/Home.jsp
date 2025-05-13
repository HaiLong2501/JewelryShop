<%-- 
    Document   : Home
    Created on : Mar 1, 2025, 9:21:23 PM
    Author     : Hai Long
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="model.Category, model.Brand,model.Material , model.ProductLine, model.Product" %>
<%@page import="java.util.Vector, java.text.DecimalFormat" %>
<!DOCTYPE html>
<html>
    <% 
        
    Vector<Category> listCate = (Vector<Category>)request.getAttribute("listCate");
    
    Vector<ProductLine> listProdLine = (Vector<ProductLine>)request.getAttribute("listProdLine");
    
    Vector<Material> listMate = (Vector<Material>)request.getAttribute("listMate");
    
    Vector<Product> listProd = (Vector<Product>)request.getAttribute("listProd");
    
    Vector<Brand> listBrand = (Vector<Brand>)request.getAttribute("listBrand");
    
    String id = "";
    %>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
        <link
            rel="stylesheet"
            href="https://cdn.jsdelivr.net/npm/swiper@11/swiper-bundle.min.css"
            />
        <link rel="stylesheet" href="<%= request.getContextPath() %>/style/Home.css">
    </head>
    <body>
        <%@include file="Header.jsp" %>  
        <div class="banner">
            <div id="carouselExampleIndicators" class="carousel slide" data-bs-ride="carousel">
                <div class="carousel-indicators">
                    <button type="button" data-bs-target="#carouselExampleIndicators" data-bs-slide-to="0" class="active" aria-current="true" aria-label="Slide 1"></button>
                    <button type="button" data-bs-target="#carouselExampleIndicators" data-bs-slide-to="1" aria-label="Slide 2"></button>
                    <button type="button" data-bs-target="#carouselExampleIndicators" data-bs-slide-to="2" aria-label="Slide 3"></button>
                </div>
                <div class="carousel-inner">
                    <div class="carousel-item active">
                        <img src="${pageContext.request.contextPath}/images/banners/banner1.png" class="d-block w-100" alt="banner1">
                    </div>
                    <div class="carousel-item">
                        <img src="${pageContext.request.contextPath}/images/banners/banner2.png" class="d-block w-100" alt="banner2">
                    </div>
                    <div class="carousel-item">
                        <img src="${pageContext.request.contextPath}/images/banners/banner3.png" class="d-block w-100" alt="banner3">
                    </div>
                </div>
                <button class="carousel-control-prev" type="button" data-bs-target="#carouselExampleIndicators" data-bs-slide="prev">
                    <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                    <span class="visually-hidden">Previous</span>
                </button>
                <button class="carousel-control-next" type="button" data-bs-target="#carouselExampleIndicators" data-bs-slide="next">
                    <span class="carousel-control-next-icon" aria-hidden="true"></span>
                    <span class="visually-hidden">Next</span>
                </button>
            </div>
        </div>

        <div class="service">
            <div class="service-item">
                <div class="service-img">
                    <img src="${pageContext.request.contextPath}/images/service/shipping-icon.svg" alt="shipping-icon"/>
                </div>
                <div class="service-info">
                    <h5 style="padding-top: 20px">MIỄN PHÍ</h5>
                    <p>Giao hàng 3h</p>
                </div>

            </div>
            <div class="service-item">
                <div class="service-img">
                    <img src="${pageContext.request.contextPath}/images/service/shopping-247-icon.svg" alt="shopping 247-icon"/>
                </div>
                <div class="service-info">
                    <h5>Phục vụ 24/7</h5>
                </div>
            </div>
            <div class="service-item">
                <div class="service-img">
                    <img src="${pageContext.request.contextPath}/images/service/thudoi-icon.svg" alt="thudoi-icon"/>
                </div>
                <div class="service-info">
                    <h5>THU ĐỔI 48H</h5>
                </div>
            </div>
        </div>

        <h3 class="title">Bạn đang tìm gì hôm nay?</h3>

        <div class="suggest">

            <% for(int i = 0; i < 5; i++){ %>
            <div class="suggest-item">
                <a href="list?cId=<%= listCate.get(i).getCategoryID() %>">
                    <img style="width: 142px; height: 142px;" src="<%= request.getContextPath() %>/images/category/<%= listCate.get(i).getImage()%>" alt="<%= listCate.get(i).getCategoryName()%>"/>
                    <p class="suggest-info"><%= listCate.get(i).getCategoryName()%></p>
                </a>
            </div>
            <% } %>
        </div>

        <h3 class="title">Thương hiệu nổi bật</h3>

        <div class="brand">
            <!-- Slider main container -->
            <div class="swiper" id="swiper-brand">
                <!-- Additional required wrapper -->
                <div class="swiper-wrapper">
                    <%for(Brand b : listBrand) { %>
                        <div class="swiper-slide">
                            <a href="list?bId=<%= b.getBrandID() %>">
                                <img src="<%= request.getContextPath() %>/images/brands/<%= b.getImage() %>" alt="<%= b.getBrandName()%>"/>
                            </a>
                        </div>
                            <% } %>
                    
                </div>
                <!-- If we need pagination -->
                <div class="swiper-pagination"></div>

                <!-- If we need navigation buttons -->
                <div class="swiper-button-prev"></div>
                <div class="swiper-button-next"></div>

                <!-- If we need scrollbar -->
                <div class="swiper-scrollbar" style="display: none;"></div>
            </div>
        </div>

        <!--        <h3 class="title">Dòng hàng nổi bật</h3>
        
                <div class="productLine">
                    <div class="productLine-content">
                        <div class="productLine-img">
        <% ProductLine activePl = null; %>
        <% for(ProductLine pl : listProdLine){ %>
        <% if(pl.getProductLineID().equals("TS01")){ %>
        <% activePl = pl; break;}}%>
        <% if(activePl != null) {%>
        <a href=""><img src="<%=  request.getContextPath()%>/images/productLines/<%= activePl.getImage() %>" alt="<%= activePl.getProductLineName() %>"/></a>
        <% } %>
</div>
<div class="productLine-title">
        <% for(ProductLine pl : listProdLine){ %>
        <% boolean isActive = pl.getProductLineID().equals("TS01"); %>
        <div class="productLine-detail <%= isActive ? "active" : "" %>" 
             
             data-image="<%= pl.getImage() %>" 
             onclick="changeDetail(this)">
        <%= pl.getProductLineName() %>
    </div>
        <% } %>
    </div>
</div>
</div>-->

        <% for(ProductLine pl: listProdLine) { %>
        
        <% id = pl.getProductLineID(); %>

        <h3 class="title"><%= pl.getProductLineName() %></h3>
        
        <div class="prodLine-banner">
            <img style="height: 250px; width: 1200px;" src="<%= request.getContextPath() %>/images/productLines/<%= pl.getImage() %>" alt="alt"/>
        </div>

        <div class="products">
            <div class="product-content">
                <div class="swiper" id="swiper_Product">

                    <div class="swiper-wrapper">
                        <% int count = 5;%>
                        <% for(int i = 0; i < listProd.size(); i++) { %>
                        <% if(listProd.get(i).getProductLineID().equals(pl.getProductLineID())) { %>
                        <div class="swiper-slide product-item" data-productLineID="<%= listProd.get(i).getProductLineID() %>">
                            <a class="products-detail" href="detail?pId=<%= listProd.get(i).getProductID() %>">
                                <div class="product-img">
                                    <img src="<%= request.getContextPath()%>/images/products/<%= listProd.get(i).getImage()%>" alt="<%= listProd.get(i).getProductName() %>"/>
                                </div>
                                <div class="product-info">
                                    <% DecimalFormat df = new DecimalFormat("#,###"); %>
                                    <p class="product-name"><%= listProd.get(i).getProductName() %></p>
                                    <%if (listProd.get(i).getDiscount() > 0) { %>
                                    <p class="price"><%= df.format(listProd.get(i).getPrice() - listProd.get(i).getPrice()*listProd.get(i).getDiscount()/100) %>&nbsp;đ</p>
                                    <del class="product-price"><%= df.format(listProd.get(i).getPrice()) %>&nbsp;đ</del>
                                    <% }else{ %>
                                    <p class="price"><%= df.format(listProd.get(i).getPrice()) %>&nbsp;đ</p>
                                    <% } %>
                                </div>
                            </a>
                        </div>
                        <% count--; %>
                        <% if(count == 0) break; %>
                        <% } } %>
                    </div>
                    <!-- If we need pagination -->
                    <div class="swiper-pagination"></div>

                    <!-- If we need navigation buttons -->
                    <div class="swiper-button-prev"></div>
                    <div class="swiper-button-next"></div>

                    <!-- If we need scrollbar -->
                    <div class="swiper-scrollbar"></div>
                    
                    
                </div>
            </div>
                    <div class="more">
                        <a href="list?plId=<%= id %>">Xem thêm</a>
                    </div>
        </div>
        <% } %>

        <div class="address">
            <img src="<%= request.getContextPath() %>/images/backgrounds/map.png" alt="Địa chỉ"/>
        </div>

        <div class="ads">
            <video width="640" height="360" controls="" crossorigin="anonymous" loop muted poster="<%= request.getContextPath() %>/images/ads/thumbnail.png">
                 <source src="<%= request.getContextPath() %>/video_ads/video_ads.mp4" type="video/mp4">
            </video>

        </div>
            
            <hr/>
            
                 <%@include file="Footer.jsp"  %>
    </body>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/swiper@11/swiper-bundle.min.js"></script>
    <script src="<%= request.getContextPath() %>/js/Swiper.js"></script>
    <!--  <script>
                                function changeDetail(productLineID, image, element) {
                                    let imageTag = document.querySelector('.productLine-img');
                                    imageTag.innerHTML = '<a href=""><img src="<%= request.getContextPath()%>/images/productLines/' + image + '" alt="alt"/></a>';
                                    let productTag = document.querySelector('.products');                           
        
                                    document.querySelectorAll('.productLine-detail').forEach(div => {
                                        div.classList.remove('active');
                                    });
                                    element.classList.add('active');
                                }
            </script>-->
    
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
