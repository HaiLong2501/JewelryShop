<%-- 
    Document   : SuccessfulOrder
    Created on : Mar 16, 2025, 8:32:44 PM
    Author     : Hai Long
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link rel="stylesheet" href="<%= request.getContextPath()%>/style/SuccessfulOrder.css"/>
    </head>
    <body>
        <%@include file="Header.jsp" %>
        
        <div class="content">
            <h1>Bạn đã đặt hàng thành công</h1>
        
        <h3>Quay lại trang chủ nếu bạn muốn tiếp tục mua hàng</h3>
        <a href="home">Home</a>
        
        </div>
        
        
        
        <%@include file="Footer.jsp" %>
    </body>
    <script>
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
