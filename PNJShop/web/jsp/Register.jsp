<%-- 
    Document   : Register
    Created on : Mar 12, 2025, 8:12:34 AM
    Author     : Hai Long
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <!-- Font Awesome -->
        <link
            href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css"
            rel="stylesheet"

            <!-- Google Fonts -->
        <link
            href="https://fonts.googleapis.com/css?family=Roboto:300,400,500,700&display=swap"
            rel="stylesheet"
            />
        <!-- MDB -->
        <link
            href="https://cdn.jsdelivr.net/npm/mdb-ui-kit@8.2.0/css/mdb.min.css"
            rel="stylesheet"
            />

        <link rel="stylesheet" href="<%= request.getContextPath()%>/style/Register.css"/>
    </head>
    <body>
        <div class="register">
            <div class="register-content">
                <section class="vh-100" style="background-color: #eee;">
                    <div class="container h-100">
                        <div class="row d-flex justify-content-center align-items-center h-100">
                            <div class="col-lg-12 col-xl-11">
                                <div class="card text-black" style="border-radius: 25px;">
                                    <div class="card-body p-md-5">
                                        <div class="row justify-content-center">
                                            <div class="col-md-10 col-lg-6 col-xl-5 order-2 order-lg-1">

                                                <p class="text-center h1 fw-bold mb-5 mx-1 mx-md-4 mt-4">Đăng ký</p>

                                                <form action="register" method="get" class="mx-1 mx-md-4">

                                                    <div class="d-flex flex-row align-items-center mb-4">
                                                        <i class="fas fa-user fa-lg me-3 fa-fw"></i>
                                                        <div data-mdb-input-init class="form-outline flex-fill mb-0">
                                                            <input type="text" value="${param.fullName != null ? param.fullName : ''}" name="fullName" id="form3Example1c" class="form-control" />
                                                            <label class="form-label" for="form3Example1c">Họ tên</label>
                                                        </div>
                                                    </div>

                                                    <div class="d-flex flex-row align-items-center mb-4">
                                                        <i class="fas fa-user fa-lg me-3 fa-fw"></i>
                                                        <div data-mdb-input-init class="form-outline flex-fill mb-0">
                                                            <input type="text" value="${param.userName != null ? param.userName : ''}" name="userName" id="form3Example2c" class="form-control" />
                                                            <label class="form-label" for="form3Example3c">Tên đăng nhập</label>
                                                        </div>
                                                    </div>

                                                    <div class="d-flex flex-row align-items-center mb-4">
                                                        <i class="fas fa-envelope fa-lg me-3 fa-fw"></i>
                                                        <div data-mdb-input-init class="form-outline flex-fill mb-0">
                                                            <input type="email" value="${param.email != null ? param.email : ''}" name="email" id="form3Example3c" class="form-control" />
                                                            <label class="form-label" for="form3Example3c">Email của bạn</label>
                                                        </div>
                                                    </div>

                                                    <!--                                                    <div class="d-flex flex-row align-items-center mb-4">
                                                                                                            <i class="fas fa-mobile-alt fa-lg me-3 fa-fw"></i>
                                                                                                            <div data-mdb-input-init class="form-outline flex-fill mb-0">
                                                                                                                <input type="text" value="${param.phone != null ? param.phone : ''}" name="phone" id="form3Example4c" class="form-control" />
                                                                                                                <label class="form-label" for="form3Example3c">Số điện thoại</label>
                                                                                                            </div>
                                                                                                        </div>-->



                                                    <div class="d-flex flex-row align-items-center mb-4">
                                                        <i class="fas fa-lock fa-lg me-3 fa-fw"></i>
                                                        <div data-mdb-input-init class="form-outline flex-fill mb-0">
                                                            <input type="password" value="${param.password != null ? param.password : ''}" name="password" id="form3Example5c" class="form-control" />
                                                            <label class="form-label" for="form3Example4c">Password</label>
                                                        </div>
                                                    </div>



                                                    <div class="d-flex flex-row align-items-center mb-4">
                                                        <i class="fas fa-key fa-lg me-3 fa-fw"></i>
                                                        <div data-mdb-input-init class="form-outline flex-fill mb-0">
                                                            <input type="password" value="${param.passwordCheck != null ? param.passwordCheck : ''}" name="passwordCheck" id="form3Example6cd" class="form-control" />
                                                            <label class="form-label" for="form3Example4cd">Repeat your password</label>
                                                        </div>
                                                    </div>


                                                    <div data-mdb-input-init class="form-outline flex-fill mb-0" style="display: flex;gap: 20px;">
                                                        <!-- Default radio -->
                                                        <div class="form-check">
                                                            <input class="form-check-input" ${param.gender.equals("Male") ? 'checked' : ''} type="radio" name="gender" value="Male" id="flexRadioDefault1"/>
                                                            <label class="form-check-label" for="flexRadioDefault1"> Nam </label>
                                                        </div>

                                                        <!-- Default checked radio -->
                                                        <div class="form-check">
                                                            <input class="form-check-input" type="radio" ${param.gender.equals("Female") ? 'checked' : ''} name="gender" value="Female" id="flexRadioDefault2"/>
                                                            <label class="form-check-label" for="flexRadioDefault2"> Nữ </label>
                                                        </div>
                                                    </div>

                                                    <c:if test="${requestScope.error != null}">
                                                        <div class="error">${requestScope.error}</div>
                                                    </c:if>
                                                    <div style="margin: 20px 0px; color: red;" class="captcha">
                                                        <div class="g-recaptcha" data-sitekey="6LfXuv8qAAAAADSl66h76G3PIVKdifIQ990ZGVhm"></div>
                                                        <div  id="error"></div>
                                                    </div>
                                                    <div class="d-flex justify-content-center mx-4 mb-3 mb-lg-4" style="display: flex; gap: 10px;">
                                                        <input type="hidden" name="submit" value="register-btn">
                                                        <input type="hidden" name="service" value="register">
                                                        <input type="hidden" name="redirectUrl" value="${requestScope.redirectUrl != null ? requestScope.redirectUrl : ''}">
                                                        <button  type="submit"  class="btn btn-primary btn-lg">Đăng ký</button>
                                                        <!--<a class="btn btn-primary" href="">Login with google</a>-->
                                                    </div>

                                                </form>


                                            </div>
                                            <div class="col-md-10 col-lg-6 col-xl-7 d-flex align-items-center order-1 order-lg-2">

                                                <a href="home">
                                                    <img src="<%= request.getContextPath() %>/images/logo/pnj.com.vn.png"
                                                         class="img-fluid" alt="pnj.com.vn.png">
                                                </a>

                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </section>

            </div>
        </div>
        <!-- MDB -->
        <script
            type="text/javascript"
            src="https://cdn.jsdelivr.net/npm/mdb-ui-kit@8.2.0/js/mdb.umd.min.js"
        ></script>
        <script src="https://www.google.com/recaptcha/api.js" async defer></script>
        <script>
            document.querySelector("form").addEventListener("submit", function (event) {
                let response = grecaptcha.getResponse();
                if (response.length === 0) {
                    event.preventDefault();
                    document.getElementById("error").innerHTML = "Vui lòng xác thực reCAPTCHA trước khi đăng ký.";
                }
            });
        </script>
    </body>


</html>
