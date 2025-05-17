<%-- 
    Document   : Login
    Created on : Mar 9, 2025, 5:28:06 PM
    Author     : Hai Long
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <% 
        String error = (String)request.getAttribute("error");
        String redirectUrl = (String)request.getAttribute("redirectUrl");
    %>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
        <link rel="stylesheet" href="<%= request.getContextPath()%>/style/Login.css"/>
    </head>
    <body>
        <div class="login-register-form">

            <div class="login-content">
                <div class="logo">
                    <a href="home"><img src="<%= request.getContextPath() %>/images/logo/logo.svg" alt="logo"/></a>
                </div>

                <div class="title"><h2>Vui lòng đăng nhập để tiếp tục</h2></div>

                <div class="login-form">
                    <form action="login" method="post" autocomplete="on">
                        <div class="mb-3">
                            <label for="exampleInputEmail1" class="form-label">Tên đăng nhập</label>
                            <input value="${param.userName != null ? param.userName : ''}" type="text" name="userName" class="form-control" id="exampleInputEmail1" aria-describedby="emailHelp">
                            <!--<div id="emailHelp" class="form-text">We'll never share your email with anyone else.</div>-->
                        </div>
                        <div class="mb-3">
                            <label for="exampleInputPassword1" class="form-label">Mật khẩu</label>
                            <input value="${param.password != null ? param.password : ''}" type="password" name="password" class="form-control" id="exampleInputPassword1">
                        </div>
<!--                        <div class="g-recaptcha" data-sitekey="6LfXuv8qAAAAADSl66h76G3PIVKdifIQ990ZGVhm"></div>
			<div id="error"></div>-->
			

                        <input type="hidden" name="service" value="login">
                        <input type="hidden" name="redirectUrl" value="<%= redirectUrl != null ? redirectUrl : "" %>">
                        <div class="login-register-btn">
                            <div class="register-btn" onclick="register(event)">
                                <a href="">Đăng ký</a>
                            </div>                        
                            <div class="login-btn">
                                <input type="hidden" value="login" name="submit">
                                <button type="submit" class="btn btn-primary">Đăng nhập</button>
                                
                            </div>
                            
                        </div>
                        <div class="login-google">
                            <button type="button" onclick="loginGoogle()"  class="btn btn-primary">Login with google</button>
                            
                        </div>
                    </form>
                </div>

                <c:if test="${requestScope.error != null}">
                    <div class="login-fail">
                        <div class="alert alert-danger d-flex align-items-center" role="alert">
                            <svg class="bi flex-shrink-0 me-2" width="24" height="24" role="img" aria-label="Danger:"><use xlink:href="#exclamation-triangle-fill"/></svg>
                            <div>
                                Sai tên đăng nhập hoặc mật khẩu
                            </div>
                        </div>
                    </div>
                </c:if>
                <c:if test="${requestScope.report != null}">
                    <div class="login-continue">
                        <div class="alert alert-primary" role="alert">
                            ${requestScope.report}
                        </div>
                    </div>
                </c:if>

            </div>
        </div>
    </body>
    <script src="https://www.google.com/recaptcha/api.js" async defer></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>
    <script>
        document.querySelector("form").addEventListener("submit", function(event) {
        let response = grecaptcha.getResponse(); 
        if (response.length === 0) { 
            event.preventDefault(); 
            document.getElementById("error").innerHTML = "Vui lòng xác thực reCAPTCHA trước khi đăng nhập.";
        }
    });
        function register(e){
            e.preventDefault();
            console.log('ok');
            window.location.href = "<%= request.getContextPath() %>/register?service=register&redirectUrl=" + encodeURIComponent('<%= redirectUrl%>');
        }
        
        function loginGoogle(){
            window.location.href =
"https://accounts.google.com/o/oauth2/auth?scope=email%20profile%20openid&redirect_uri=http://localhost:9999/pnj/login&response_\n\
type=code&client_id=&approval_prompt=force&state=loginGoogle" + "|" + encodeURIComponent('<%= redirectUrl%>');
        }
    </script>
</html>
