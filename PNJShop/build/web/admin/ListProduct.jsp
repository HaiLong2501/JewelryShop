<%-- 
    Document   : ListCategory
    Created on : Mar 17, 2025, 6:49:08 PM
    Author     : Hai Long
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="model.Product, java.util.Vector, java.text.DecimalFormat" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
    <% 
    Vector<Product> listProd = (Vector<Product>)request.getAttribute("listProd");
    %>
    <head>
        <meta charset="utf-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <title>Simple Responsive Admin</title>

        <!-- BOOTSTRAP STYLES-->
        <link rel="stylesheet" href="//maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
        <!--<link href="<%= request.getContextPath() %>/admin/assets/css/bootstrap.css" rel="stylesheet" />-->
            <!--<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">-->
            <!-- FONTAWESOME STYLES-->
            <link href="<%= request.getContextPath() %>/admin/assets/css/font-awesome.css" rel="stylesheet" />
            <!-- CUSTOM STYLES-->
            <link href="<%= request.getContextPath() %>/admin/assets/css/custom.css" rel="stylesheet" />
            <!-- GOOGLE FONTS-->
            <link href='http://fonts.googleapis.com/css?family=Open+Sans' rel='stylesheet' type='text/css' />
            <!--        css md5 
                    <link
                        href="https://cdn.jsdelivr.net/npm/mdb-ui-kit@8.2.0/css/mdb.min.css"
                        rel="stylesheet"
                        />-->
            <!-- Google Fonts -->
            <link
                href="https://fonts.googleapis.com/css?family=Roboto:300,400,500,700&display=swap"
                rel="stylesheet"
                />
    </head>
    <body>



        <div id="wrapper">
            <div class="navbar navbar-inverse navbar-fixed-top">
                <div class="adjust-nav">
                    <div class="navbar-header">
                        <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".sidebar-collapse">
                            <span class="icon-bar"></span>
                            <span class="icon-bar"></span>
                            <span class="icon-bar"></span>
                        </button>
                        <a class="navbar-brand" href="home">
                            <img src="<%= request.getContextPath() %>/images/logo/pnj.com.vn.png" />

                        </a>

                    </div>

                    <span class="logout-spn" >
                        <a href="login?service=logout" style="color:#fff;">LOGOUT</a>  

                    </span>
                </div>
            </div>
            <!-- /. NAV TOP  -->
            <nav class="navbar-default navbar-side" role="navigation">
                <div class="sidebar-collapse">
                    <ul class="nav" id="main-menu">



                        <li class="active-link">
                            <a href="admin" ><i class="fa fa-desktop "></i>List all Products</a>
                        </li>


                        <li >
                            <a href="category"><i class="fa fa-table "></i>List All Categories</a>
                        </li>
                        <li>
                            <a href="order"><i class="fa fa-edit "></i>List All Orders</a>
                        </li>


                        <li>
                            <a href="productline"><i class="fa fa-qrcode "></i>List All ProductLines</a>
                        </li>
                        <li>
                            <a href="user"><i class="fa fa-qrcode "></i>List All Users</a>
                        </li>
                        

                    </ul>
                </div>

            </nav>
            <!-- /. NAV SIDE  -->
            <div id="page-wrapper" >
                <div id="page-inner">
                    <div class="row">
                        <div class="col-lg-12">
                            <h2>ADMIN DASHBOARD</h2>   
                        </div>
                    </div>              
                    <!-- /. ROW  -->
                    <hr />
                    <div class="row">
                        <div class="col-lg-12 ">
                            <div class="alert alert-info">
                                <strong>Welcome admin ! </strong> You Have No pending Task For Today.
                            </div>

                        </div>
                    </div>
                    <c:if test="${requestScope.errorID != null}">
                        <div id="myAlert" class="alert alert-success alert-fixed">
                            <strong>ID đã tồn tại!</strong>
                        </div>
                    </c:if>

                    <div class="btn-group">
                        <div class="search">
                            <form action="admin" method="get">
                                <div class="form-group" style="margin-right: 20px;">
                                    <input value="${param.search != null ? param.search : ''}" class="form-control" type="text" name="search"/>
                                </div>
                                <div class="form-group">
                                    <button type="submit" name="submit" value="search" class="btn btn-primary"><span class="glyphicon glyphicon-search" aria-hidden="true"></span></button>
                                </div>
                            </form>
                        </div>
                        <div class="insert-btn">

                            <button type="button" class="btn btn-primary btn-lg" data-toggle="modal" data-target="#flipFlop_Insert">
                                Insert Product
                            </button>

                            <!-- The modal -->
                            <form action="admin" method="get">
                                <div class="modal fade" id="flipFlop_Insert" tabindex="-1" role="dialog" aria-labelledby="modalLabel" aria-hidden="true">
                                    <div class="modal-dialog" role="document">
                                        <div class="modal-content">
                                            <div class="modal-header">
                                                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                                    <span aria-hidden="true">×</span>
                                                </button>
                                                <h4 class="modal-title" id="modalLabel">Product Info</h4>
                                            </div>
                                            <div class="modal-body">
                                                <div class="modal-container">
                                                    <div class="modal-h"><h5>Product's ID</h5></div>
                                                    <div class="modal-con">
                                                        <input type="text" name="productID" value="">
                                                    </div>
                                                </div>
                                                <div class="modal-container">
                                                    <div class="modal-h"><h5>Product's Name</h5></div>
                                                    <div class="modal-con">
                                                        <input type="text" name="productName" value="">
                                                    </div>
                                                </div>
                                                <div class="modal-container">
                                                    <div class="modal-h"><h5>Price (đ)</h5></div>
                                                    <div class="modal-con">
                                                        <input type="text" name="price" value="">
                                                    </div>
                                                </div>
                                                <div class="modal-container">
                                                    <div class="modal-h"><h5>Discount(%)</h5></div>
                                                    <div class="modal-con">
                                                        <input type="text" name="discount" value="">
                                                    </div>
                                                </div>
                                                <div class="modal-container">
                                                    <div class="modal-h"><h5>Category</h5></div>
                                                    <div class="modal-con modal-select">
                                                        <select name="cate">
                                                            <c:forEach items="${requestScope.listCate}" var="cate">
                                                                <option value="${cate.categoryID}">${cate.categoryName}</option>
                                                            </c:forEach>
                                                        </select>

                                                    </div>
                                                </div>
                                                <div class="modal-container">
                                                    <div class="modal-h"><h5>ProductLine</h5></div>
                                                    <div class="modal-con modal-select">
                                                        <select name="prodLine">
                                                            <c:forEach items="${requestScope.listProdLine}" var="pl">
                                                                <option value="${pl.productLineID}">${pl.productLineName}</option>
                                                            </c:forEach>
                                                        </select>

                                                    </div>
                                                </div>
                                                <div class="modal-container">
                                                    <div class="modal-h"><h5>Material</h5></div>
                                                    <div class="modal-con modal-select">
                                                        <select name="mate">
                                                            <c:forEach items="${requestScope.listMate}" var="m">
                                                                <option value="${m.materialID}">${m.materialName}</option>
                                                            </c:forEach>
                                                        </select>

                                                    </div>
                                                </div>
                                                <div class="modal-container">
                                                    <div class="modal-h"><h5>imageURL</h5></div>
                                                    <div class="modal-con">
                                                        <input type="text" name="image" value="">
                                                    </div>
                                                </div>
                                                <div class="modal-container">
                                                    <div class="modal-h"><h5>status</h5></div>
                                                    <div class="modal-con modal-radio">
                                                        <input type="radio" name="status" value="1">On
                                                            <input type="radio" name="status" value="0">Off
                                                                </div>
                                                                </div>
                                                                <div class="modal-container">
                                                                    <div class="modal-h"><h5>quantity</h5></div>
                                                                    <div class="modal-con">
                                                                        <input type="text" name="quantity" value="">
                                                                    </div>
                                                                </div>
                                                                </div>
                                                                <div class="modal-footer">

                                                                    <button type="submit" name="service" value="insertProduct" class="btn btn-change">Insert</button>

                                                                    <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                                                                </div>
                                                                </div>

                                                                </div>
                                                                </div>
                                                                </form>

                                                                </div>
                                                                </div>


                                                                <div class="contentPro">
                                                                    <table>
                                                                        <thead class="card-head">
                                                                            <tr>
                                                                                <th>Mã sản phẩm</th>
                                                                                <th>Tên</th>
                                                                                <th>Hình ảnh</th>
                                                                                <th>Giá</th>
                                                                                <th></th>
                                                                            </tr>
                                                                        </thead>
                                                                        <tbody>
                                                                            <% for(Product p: listProd) { %>
                                                                            <tr>
                                                                                <td><%= p.getProductID()%></td>
                                                                                <td><%= p.getProductName()%></td>
                                                                                <td><img style="width: 150px;height: 150px;" src="<%= request.getContextPath() %>/images/products/<%= p.getImage()%>" alt="<%= p.getProductName()%>"/></td>
                                                                                <% DecimalFormat df = new DecimalFormat("#,###"); %>
                                                                                <td><%= df.format(p.getPrice())%>&nbsp;đ</td>
                                                                                <td>
                                                                                    <button type="button" class="btn btn-primary btn-lg" data-toggle="modal" data-target="#flipFlop_<%= p.getProductID()%>">
                                                                                        Detail
                                                                                    </button>

                                                                                    <!-- The modal -->
                                                                                    <form action="admin" method="get">
                                                                                        <div class="modal fade" id="flipFlop_<%= p.getProductID()%>" tabindex="-1" role="dialog" aria-labelledby="modalLabel" aria-hidden="true">
                                                                                            <div class="modal-dialog" role="document">
                                                                                                <div class="modal-content">
                                                                                                    <div class="modal-header">
                                                                                                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                                                                                            <span aria-hidden="true">&times;</span>
                                                                                                        </button>
                                                                                                        <h4 class="modal-title" id="modalLabel">Modal Title</h4>
                                                                                                    </div>
                                                                                                    <div class="modal-body">
                                                                                                        <div class="modal-container">
                                                                                                            <div class="modal-h"><h5>Name Product</h5></div>
                                                                                                            <div class="modal-con">
                                                                                                                <input type="text" name="productName" value="<%= p.getProductName() %>" >
                                                                                                            </div>
                                                                                                        </div>
                                                                                                        <div class="modal-container">
                                                                                                            <div class="modal-h"><h5>Price (đ)</h5></div>
                                                                                                            <div class="modal-con">
                                                                                                                <input type="text" name="price" value="<%=df.format(p.getPrice()) %>" >
                                                                                                            </div>
                                                                                                        </div>
                                                                                                        <div class="modal-container">
                                                                                                            <div class="modal-h"><h5>Discount(%)</h5></div>
                                                                                                            <div class="modal-con">
                                                                                                                <input type="text" name="discount" value="<%= p.getDiscount() %>">
                                                                                                            </div>
                                                                                                        </div>
                                                                                                        <div class="modal-container">
                                                                                                            <div class="modal-h"><h5>status</h5></div>
<!--                                                                                                            <div class="modal-con">
                                                                                                                <input type="text" name="status" value="<%= (p.getStatus() == 1 ? "on" : "off") %>">
                                                                                                            </div>-->
                                                                                                             <div class="modal-con modal-radio">
                                                                                                                  <input type="radio" name="status" <%= (p.getStatus() == 1 ? "checked" : "") %>  value="<%= (p.getStatus() == 1 ? "on" : "on") %>">On
                                                                                                                  <input type="radio" name="status" <%= (p.getStatus() == 0 ? "checked" : "") %> value="<%= (p.getStatus() == 0 ? "off" : "off") %>">Off
                                                                                                              </div>
                                                                                                        </div>
                                                                                                        <div class="modal-container">
                                                                                                            <div class="modal-h"><h5>quantity</h5></div>
                                                                                                            <div class="modal-con">
                                                                                                                <input type="text" name="quantity" value="<%= p.getQuantity() %>">
                                                                                                            </div>
                                                                                                        </div>
                                                                                                    </div>
                                                                                                    <div class="modal-footer">
                                                                                                        <input type="hidden" name="pId" value="<%= p.getProductID()%>">
                                                                                                            <button type="submit" name="service" value="updateProduct" class="btn btn-change">Update</button>
                                                                                                            <button type="submit" name="service" value="deleteProduct" onclick="return confirm('Do you sure to delete this product?')" class="btn btn-change">Delete</button>
                                                                                                            <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                                                                                                    </div>
                                                                                                </div>
                                                                                            </div>
                                                                                        </div>
                                                                                    </form>
                                                                                </td>
                                                                            </tr>
                                                                            <% } %>
                                                                        </tbody>
                                                                    </table>
                                                                </div>




                                                                <!-- /. ROW  -->   
                                                                <div class="row">
                                                                    <div class="col-lg-12 ">
                                                                        <br/>
                                                                        <div class="alert alert-danger">
                                                                            <strong>Want More Icons Free ? </strong> Checkout fontawesome website and use any icon <a target="_blank" href="http://fortawesome.github.io/Font-Awesome/icons/">Click Here</a>.
                                                                        </div>

                                                                    </div>
                                                                </div>
                                                                <!-- /. ROW  --> 
                                                                </div>
                                                                <!-- /. PAGE INNER  -->
                                                                </div>
                                                                <!-- /. PAGE WRAPPER  -->
                                                                </div>
                                                                <div class="footer">


                                                                    <div class="row">
                                                                        <div class="col-lg-12" >
                                                                            &copy;  2014 yourdomain.com | Design by: <a href="http://binarytheme.com" style="color:#fff;" target="_blank">www.binarytheme.com</a>
                                                                        </div>
                                                                    </div>
                                                                </div>


                                                                <!-- /. WRAPPER  -->
                                                                <!-- SCRIPTS -AT THE BOTOM TO REDUCE THE LOAD TIME-->
                                                                <!-- JQUERY SCRIPTS -->
                                                                <!-- jQuery library -->
                                                                <script src="//ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
                                                                <!-- Bootstrap JS -->
                                                                <script src="//maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
                                                                <!-- CUSTOM SCRIPTS -->
                                                                <script src="<%= request.getContextPath() %>/admin/assets/js/custom.js"></script>

                                                                <!--         MDB 
                                                                        <script
                                                                            type="text/javascript"
                                                                            src="https://cdn.jsdelivr.net/npm/mdb-ui-kit@8.2.0/js/mdb.umd.min.js"
                                                                        ></script>-->
                                                                <script>
                                                                                                                // Initialize tooltip component
                                                                                                                $(function () {
                                                                                                                    $('[data-toggle="tooltip"]').tooltip();
                                                                                                                });

                                                                                                                // Initialize popover component
                                                                                                                $(function () {
                                                                                                                    $('[data-toggle="popover"]').popover();
                                                                                                                });
                                                                                                                $(document).ready(function () {
                                                                                                                    // Hiển thị thông báo
                                                                                                                    $("#myAlert").fadeIn();

                                                                                                                    // Ẩn thông báo sau 3 giây (3000ms)
                                                                                                                    setTimeout(function () {
                                                                                                                        $("#myAlert").fadeOut();
                                                                                                                    }, 5000);
                                                                                                                });
                                                                </script>

                                                                <!--        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>
                                                                        <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js" integrity="sha384-IQsoLXl5PILFhosVNubq5LC7Qb9DXgDA9i+tQ8Zj3iwWAwPtgFTxbJ8NT4GN1R8p" crossorigin="anonymous"></script>
                                                                <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.min.js" integrity="sha384-cVKIPhGWiC2Al4u+LWgxfKTRIcfu0JTxR+EQDz/bgldoEyl4H0zUF0QKbrJ0EcQF" crossorigin="anonymous"></script>-->
                                                                </body>
                                                                </html>
