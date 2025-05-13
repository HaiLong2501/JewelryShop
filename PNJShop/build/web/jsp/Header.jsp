<%-- 
    Document   : Header
    Created on : Mar 6, 2025, 9:09:22 PM
    Author     : Hai Long
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page  import="model.User" %>
<% 
    User u = (User)session.getAttribute("user");
    
%>
<header>
    <div class="ads-top"><img src="https://st-media-template.antsomi.com/upload/2024/11/06/cd7ed18a-29fc-4a5a-b650-145b71674ac9.png" alt="Banner Welcome to PNJ"/></div>
    <div class="info-top">
        <div class="info-top-left">
            <a href="">
                <img src="https://cdn.pnj.io/images/image-update/layout/icon-relationship-new.svg" alt="Câu chuyện PNJ"/>
                <div>Câu chuyện PNJ</div>
            </a>
            <a href="">
                <img src="https://cdn.pnj.io/images/image-update/layout/icon-stores-new.svg" alt="Cửa hàng"/>
                <div>Cửa hàng</div>
            </a>
            <a href="">
                <img src="https://cdn.pnj.io/images/2024/12/hiring-icon.svg" alt="Tuyển dụng"/>
                <div>Tuyển dụng</div>
            </a>
        </div>
        <div class="info-top-mid">
            <a href="home"><img src="https://cdn.pnj.io/images/logo/pnj.com.vn.png" alt="PNJ logo"/></a>
        </div>
        <div class="info-top-right">
            <a href="">
                <img src="https://cdn.pnj.io/images/image-update/layout/icon-hotline-new.svg" alt="Liên hệ"/>
                <div>1800 54 54 57</div>
            </a>
            <a href="cart?service=showCart">
                <img src="https://cdn.pnj.io/images/image-update/layout/icon-cart-new.svg" alt="cart"/>
                <div>Giỏ hàng</div>
            </a>
            <% if(u == null) { %>
            <a onclick="login('login', event)" href="">
                <img src="https://cdn.pnj.io/images/2023/user-regular.svg" alt="My account"/>
                <div>Tài khoản của tôi</div>
                
            </a>
            <% }else{ %>
            <a href="">
                <img src="https://cdn.pnj.io/images/2023/user-regular.svg" alt="My account"/>
                <p style="margin: 0px 10px;"><%= u.getName() %></p>
            </a>
                <% if(u.getRoleID() == 1) { %>
                <a href="admin" >Admin Page</a>
                <% } %>
                <a onclick="logout('logout', event)" href="" style="color: red">Đăng xuất</a>
            <% } %>
            
        </div>
    </div>

    <div class="nav-bar">
        <ul class="menu">
            <li class="menu-title">
                <a class="title-detail"  href="url">Chủng loại</a>
                <ul class="menu-detail">
                    <c:forEach var="cate" items="${requestScope.listCate}">
                        <li><a href="list?cId=${cate.categoryID}">${cate.categoryName}</a></li>
                        </c:forEach>
                </ul>

            </li>
            <li class="menu-title"><a  href="">Chất liệu</a>
                <ul class="menu-detail">
                    <c:forEach var="mate" items="${requestScope.listMate}">
                        <li><a href="list?mId=${mate.materialID}">${mate.materialName}</a></li>
                        </c:forEach>
                </ul>
            </li>
            <li class="menu-title"><a  href="">Dòng hàng</a>
                <ul class="menu-detail">
                    <c:forEach var="prodLine" items="${requestScope.listProdLine}">
                        <li><a href="list?plId=${prodLine.productLineID}">${prodLine.productLineName}</a></li>
                        </c:forEach>
                </ul>
            </li>
            <li class="menu-title"><a  href="">Quà tặng</a>
                <ul class="menu-detail">
                    <li style="font-weight: bold"><a href="">Gợi ý quà tặng</a></li>
                    <li><a href="">Cho nàng</a></li>
                    <li><a href="">Cho chàng</a></li>
                </ul>

            </li>
            <li class="menu-title" style="color: #C48E52 !important"><a  href="list?service=sale">Khuyến mãi</a></li>
        </ul>
        <div class="search-product">
            <input
                class="search_input"
                type="text"
                placeholder="Tìm kiếm nhanh"
                />
<!--            <form id="search-form" action="list" method="get">
                <input id="search-hidden" type="hidden" name="key">
            </form>-->
            <button onclick="search()">
                <img
                    src="https://cdn.pnj.io/images/image-update/layout/mobile/find-icon.svg"
                    alt="Tìm kiếm nhanh"
                    />
            </button>
        </div>
    </div>

</header>
