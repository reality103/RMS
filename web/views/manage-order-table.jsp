<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <link rel="icon" type="image/x-icon" href="./images/logo.png">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="./assets/css/style.css">
        <link rel="stylesheet" href="./assets/css/styles.css">
        <link rel="stylesheet" href="./assets/css/style_1.css">
        <link rel="stylesheet" href="./assets/fonts/themify-icons/themify-icons.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-alpha1/dist/css/bootstrap.min.css">
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-alpha1/dist/js/bootstrap.bundle.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
        <title>Restaurant</title>
        <%@include file="../component/javascript.jsp" %>
        <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
        <link href="https://cdn.jsdelivr.net/npm/simple-datatables@latest/dist/style.css" rel="stylesheet" />
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-alpha1/dist/css/bootstrap.min.css">
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-alpha1/dist/js/bootstrap.bundle.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
        <link rel="stylesheet" href="./assets/css/styles.css">
        <link rel="stylesheet" href="./assets/css/style.css">
        <link rel="stylesheet" href="./assets/fonts/themify-icons/themify-icons.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css" integrity="sha512-xxxxxxx" crossorigin="anonymous" />
        <script src="https://use.fontawesome.com/releases/v6.1.0/js/all.js" crossorigin="anonymous"></script>
        <style>
            li.nav-item a.active{
                color: red;
                font-weight: bold;
            }
            li.nav-item a{
                color: black;
            }
            .form-select option{
                background-color: transparent;
            }

            .input-container {
                position: relative;
                display: inline-block;
            }

            .input-container input[type="text"] {
                padding-right: 30px;
            }

            .clear-icon {
                position: absolute;
                top: 50%;
                color: red;
                right: 10px;
                transform: translateY(-50%);
                cursor: pointer;
                display: none;
            }

            .input-container:hover .clear-icon {
                display: block;
            }

            .product-item {
                background-color: white;
                border-radius: 10px;
                box-shadow: 0 0 10px rgb(0 0 0);
                text-align: center;
            }

            .product-thumb {
                width: 180px;
                height: 250px;
            }

            .product-name {
                /* You can adjust the font size as needed */
                font-size: 14px;
                /* You can add additional styles if needed */
            }

            .table-card {
                width: 100%;
                padding: 20px;
                border-radius: 8px;
                color: #fff;
                text-align: center;
                margin-bottom: 20px;
                box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            }

            .table-name {
                font-size: 18px;
                font-weight: bold;
                margin: 0;
            }

            .table-capacity {
                font-size: 14px;
                margin: 0;
                margin-top: 10px;
            }

        </style>

    </head>
    <body>
        <div id="main">

            <%@include file="../component/header.jsp" %>

            <div class="container-fluid" style="margin-top: 100px ">
                <div class="row">
                    <h1 style="text-align: center;">Danh Sách Đơn Hàng</h1>

                    <!--leftboard-->
                    <%@include file="../views/left.jsp" %>

                    <div class="col-md-10">
                        <div class="row justify-content-between"">

                            <div class="col-md-4">
                                <form class="search-bar" action="manageorder">
                                    <div class="input-container">
                                        <input id="myInput1" class="form-control"  type="text" name="key" value="${key}" placeholder="Tìm kiếm món" >
                                        <span class="clear-icon" onclick="clearInput()">X</span>
                                    </div>
                                    <input type="hidden" name="status" value="${status}"/>
                                    <button type="submit" class="btn btn-dark">Search</button>
                                </form>
                            </div>
                        </div>
                        <br> <br> <br>

                        <div class="row" style="margin-top:40px;">
                            <div class="container mtop" style="width:100%">
                                <div class="container">
                                    <div class="row">
                                        <c:forEach var="table" items="${tables}">
                                            <div class="col-md-3">
                                                <c:set var="isBooked" value="false" />
                                                <c:set var="orderId" value="" />

                                                <!-- Loop through orders to check if there is a matching order for the table -->
                                                <c:forEach var="order" items="${table_orders}">
                                                    <c:if test="${order.table_id == table.id}">
                                                        <c:set var="isBooked" value="true" />
                                                        <c:set var="orderId" value="${order.id}" />
                                                    </c:if>
                                                </c:forEach>

                                                <!-- Use isBooked to determine the color and link of the table card -->
                                                <c:choose>
                                                    <c:when test="${isBooked}">
                                                        <!-- Table is booked, display blue -->
                                                        <a href="order-details?order_id=${orderId}" class="table-card-link">
                                                            <div class="table-card" style="background-color: green;">
                                                                <h3 class="table-name">${table.name}</h3>
                                                                <p class="table-capacity">Sức chứa: ${table.capacity}</p>
                                                            </div>
                                                        </a>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <!-- Table is available, display green -->
                                                        <a href="table_booking?tableId=${table.id}" class="table-card-link">
                                                            <div class="table-card" style="background-color: gray;">
                                                                <h3 class="table-name">${table.name}</h3>
                                                                <p class="table-capacity">Sức chứa: ${table.capacity}</p>
                                                            </div>
                                                        </a>
                                                    </c:otherwise>
                                                </c:choose>
                                            </div>
                                        </c:forEach>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <script>
                function clearInput() {
                    document.getElementById("myInput1").value = "";
                }
            </script>
    </body>
</html>


