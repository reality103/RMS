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

        <style>
            li.nav-item a.active{
                color: blue;
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
        </style>
    </head>
    <body>
        <div id="main">

            <%@include file="../component/header.jsp" %>

            <div class="container-fluid" style="margin-top: 100px ">
                <div class="row">
                    <h1 style="text-align: center;">Danh Sách Món Ăn</h1>

                    <div class="col-md-2" style="border-right: 1px solid #DCDCDC;">

                        <div class="title_module_arrow">
                            <h2 class="margin-top-0"><span>Danh mục</span></h2>
                        </div>

                        <div class="aside-content aside-cate-link-cls">
                            <nav class="cate_padding nav-category navbar-toggleable-md">

                                <ul class="nav-ul nav navbar-pills flex-column">

                                    <li class="nav-item">
                                        <a  class="nav-link ${cid == 0 ? "active":""}" href="dishes?cid=0${historyKey}${historyValue}${historyType}"> Tất Cả </a>
                                    </li>

                                    <c:forEach items="${sessionScope.listcp}" var="c">
                                        <li class="nav-item">
                                            <a class="nav-link ${cid == c.id ?"active" : ""}"  href="dishes?cid=${c.id}${historyKey}${historyValue}${historyType}">${c.name}</a>
                                        </li>
                                    </c:forEach>
                                </ul>
                            </nav>
                        </div>
                    </div>

                    <div class="col-md-10">
                        <div class="row justify-content-between"">

                            <div class="col-md-4">
                                <form class="search-bar" action="dishes">
                                    <div class="input-container">

                                        <input id="myInput1" class="form-control"  type="text" name="key" value="${key}" placeholder="Tìm kiếm món" >
                                        <span class="clear-icon" onclick="clearInput()">X</span>
                                        <c:if test="${cid != 0}">
                                            <input type="hidden" name="cid" value="${cid}">
                                        </c:if>
                                        <input type="hidden" name="type" value="${type}">
                                        <input type="hidden" name="value" value="${value}">
                                    </div>
                                    <button type="submit" class="btn btn-dark">Search</button>
                                </form>
                            </div>

                            <div class="col-md-2 " style="margin-right: -530px; font-size: 20px;"> <b>Sắp xếp: </b> </div>

                            <div class="col-md-3">
                                <select class="form-select" aria-label="Default select example" onchange="location = this.value;">                                                                     
                                    <option class=" text-center" value="dishes?${historyCategoryId}${historyKey}&value=name" ${value eq "name" ? "Selected" : ""}>
                                        A -> Z
                                    </option>
                                    <option class=" text-center" value="dishes?${historyCategoryId}${historyKey}&value=name&type=desc" ${value eq "name" && type eq "desc" ? "Selected" : ""}>
                                        Z -> A
                                    </option>
                                    <option class="text-center " value="dishes?${historyCategoryId}${historyKey}&value=price" ${value eq "price" ? "Selected" : ""}>
                                        Giá tăng dần
                                    </option>
                                    <option class="text-center " value="dishes?${historyCategoryId}${historyKey}&value=price&type=desc" ${value eq "price" && type eq "desc" ? "Selected" : ""}>
                                        Giá giảm dần
                                    </option> 
                                </select>
                            </div>
                        </div>

                        <div class="row" style="margin-top:40px;">
                            <c:forEach items="${list}" var="p">
                                <div class="col-lg-2 col-md-3 col-sm-6 product-down">
                                    <div class="row">
                                        <div class="product-item" style="background-color: white;  border-radius: 10px;  box-shadow: 0 0 10px rgb(0 0 0);   text-align: center;">                  
                                            <div class="product-top"  style="margin-top: 10px">
                                                <a  href="dish-details?id=${p.id}&cid=${p.category_id}" class="product-thumb">
                                                    <img data-toggle="modal" data-target="#" style="height: 250px;
                                                         width: 250px;" src="${p.images.get(0).getUrl()}" alt="">
                                                </a>
                                                <c:if test="${sessionScope.user == null}" >
                                                    <a style="cursor: pointer;" class="buy-now" href="/SWP/login" style="color: white">Đặt ngay</a>
                                                </c:if>
                                                <c:if test="${sessionScope.user != null and sessionScope.user.getRole() eq 'user'}" >
                                                    <a style="cursor: pointer;" onclick="showOrderModal(${p.id}, '${p.name}', ${p.price}, '${p.images.get(0).getUrl()}')" class="buy-now">Đặt ngay</a>
                                                </c:if>

                                            </div>
                                            <div class="product-infor">
                                                <div style="height: 100px; ">
                                                    <a href="#" style="font-size: 20px" class="product-name">${p.name}</a>
                                                </div>

                                                <div class="product-price">
                                                    ${p.price}đ
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </c:forEach>
                        </div>
                    </div>
                </div>
            </div>

            <c:if test="${list.size() != 0}">
                <!-- pagination -->
                <nav aria-label="..." class="pagination">
                    <ul class="pagination">
                        <li class="page-item">
                            <a <c:if test="${pageIndex!=1}">                         
                                    href="dishes?index=${pageIndex-1}${historyCategoryId}${historyKey}${historyValue}${historyType}"
                                </c:if> class="page-link" aria-label="Previous">
                                <span  aria-hidden="true">«</span>
                            </a>
                        </li>

                        <c:forEach begin="1" end="${endPage}" var="i">
                            <li class="page-item ${i==pageIndex ?"active" : ""}">
                                <a class="page-link" href="dishes?index=${i}${historyCategoryId}${historyKey}${historyValue}${historyType}">${i}</a>
                            </li>
                        </c:forEach>

                        <li class="page-item">
                            <a <c:if test="${pageIndex != endPage}">
                                    href="dishes?index=${pageIndex+1}${historyCategoryId}${historyKey}${historyValue}${historyType}"
                                </c:if> class="page-link" aria-label="Next">
                                <span aria-hidden="true">»</span>
                            </a>
                        </li>
                    </ul>
                </nav>
            </c:if>
        </div>

    </div>
    <%@include file="../component/footer.jsp" %>
    <script>
        function clearInput() {
            document.getElementById("myInput1").value = "";
        }

    </script>

    <!-- Modal -->
    <form action="carts" method="post">
        <div class="modal fade" id="orderModal" tabindex="-1" role="dialog" aria-labelledby="orderModalLabel" aria-hidden="true">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="orderModalLabel">Thông tin đặt hàng</h5>
                        <button type="button" class="close" data-bs-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>

                    <input id="modalId" type="hidden" name="product_id"/>
                    <div class="modal-body">
                        <!-- Nội dung modal -->
                        <div class="modal-img">
                            <img id="modalImage" src="" alt="Hình ảnh món ăn" style="width: 300px; height: 200px;">
                        </div>
                        <div class="modal-info">
                            <h3 id="modalName"></h3>
                            <p id="modalPrice" style="color:red"></p>
                            <div class="modal-quantity">
                                <label for="quantity">Số lượng:</label>
                                <input type="number" id="quantity" name="quantity" value="1" min="1" onchange="calculateTotalPrice()">
                            </div>
                            <div class="modal-table">
                                <label for="table">Chọn bàn:</label>
                                <select id="table" name="table">
                                    <optgroup label="Lựa chọn khác">
                                        <option value="-1">Đặt tới địa chỉ cụ thể</option>
                                    </optgroup>
                                    <c:choose>
                                        <c:when test="${not empty sessionScope.tb}">
                                            <optgroup label="Bàn hiện tại của bạn">
                                                <option value="${sessionScope.tb.id}">${sessionScope.tb.name} (Sức chứa: ${sessionScope.tb.capacity})</option>
                                            </optgroup>
                                        </c:when>
                                        <c:otherwise>
                                            <optgroup label="Chọn bàn">
                                                <c:forEach var="table" items="${sessionScope.tables}">
                                                    <option value="${table.id}">${table.name} (Sức chứa: ${table.capacity})</option>
                                                </c:forEach>
                                            </optgroup>
                                        </c:otherwise>
                                    </c:choose>
                                </select>

                            </div>
                            <!-- Tổng giá -->
                            <div class="modal-total-price">
                                <label for="totalPrice">Tổng giá:</label>
                                <span id="totalPrice" style="font-weight: bold; color: green;"></span>
                            </div>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Đóng</button>
                        <button  type="submit" class="btn btn-primary">Thêm vào giỏ hàng</button>
                    </div>
                    </form>

                </div>
            </div>
        </div>
        <script>
            let price = 0; // Biến lưu giá của món ăn

            function showOrderModal(id, name, priceValue, imageUrl) {
                // Gán các giá trị vào modal
                document.getElementById('modalId').value = id;
                document.getElementById('modalImage').src = imageUrl;
                document.getElementById('modalName').innerText = name;
                document.getElementById('modalPrice').innerText = priceValue + 'đ';
                price = priceValue; // Lưu giá vào biến toàn cục
                document.getElementById('quantity').value = 1; // Đặt lại số lượng mặc định là 1
                calculateTotalPrice(); // Tính tổng giá ban đầu khi mở modal

                // Mở modal
                $('#orderModal').modal('show');
            }

            function calculateTotalPrice() {
                // Lấy số lượng từ input
                const quantity = document.getElementById('quantity').value;

                // Tính tổng giá
                const totalPrice = price * quantity;

                // Hiển thị tổng giá
                document.getElementById('totalPrice').innerText = totalPrice + 'đ';
            }
        </script>

</body>
</html>


