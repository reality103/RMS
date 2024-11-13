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
        <link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/1.11.3/css/jquery.dataTables.css">
        <script type="text/javascript" charset="utf8" src="https://cdn.datatables.net/1.11.3/js/jquery.dataTables.js"></script>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>

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
        </style>
    </head>
    <body>
        <div id="main">

            <%@include file="../component/header.jsp" %>

            <div class="container-fluid" style="margin-top: 100px ">
                <div class="row">
                    <h1 style="text-align: center;">Danh Sách Món Ăn</h1>

                    <!--leftboard-->
                    <%@include file="../views/left.jsp" %>

                    <div class="col-md-10">
                        <button type="button" class="btn btn-success" data-bs-toggle="modal" data-bs-target="#addModal">
                            Thêm Món Ăn
                        </button>

                        <c:if test="${listOfPage.size() eq 0}">
                            <div style="text-align: center; ">  <h3 style="color:red;">Không tìm thấy kết quả </h3></div>
                        </c:if>
                        <br> <br> <br>
                        <div class="row" style="margin-top:40px;">
                            <div class="container mtop" style="width:100%">
                                <table class="table table-striped table-bordered" id="sortTable">
                                    <thead>
                                        <tr>
                                            <th>ID</th>
                                            <th>Image</th>
                                            <th>Name</th>
                                            <th>Unit Price</th>
                                            <th>Status</th>
                                            <th>Action</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach items="${listOfPage}" var="c" >
                                            <tr>
                                                <td>${c.id}</td>
                                                <td><img data-toggle="modal" data-target="#" style="height: 50px;
                                                         width: 50px;" src="${c.images.get(0).getUrl()}" alt=""></td>
                                                <td>${c.name}</td>
                                                <td>${c.price}</td>
                                                <td>
                                                    <c:choose>
                                                        <c:when test="${c.is_available}">
                                                            <span style="color:green">Available</span>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <span style="color:gray">Not Available</span>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </td>
                                                <td>
                                                    <button type="button" class="btn btn-info" data-bs-toggle="modal" data-bs-target="#editModal" onclick="showEditModal(${c.id}, '${c.name}', '${c.price}', '${c.is_available}', '${c.brief_info}', '${c.description}', '${c.category_id}')">Edit</button>
                                                </td>
                                            </tr>
                                        </c:forEach>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
                    
        <!-- Modal for Edit User -->
        <div class="modal fade" id="editModal" tabindex="-1" aria-labelledby="editUserModalLabel" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <form id="editUserForm" action="manage-dish" method="post" enctype="multipart/form-data">
                        <input type="hidden" name="action" value="edit"/>
                        <div class="modal-header">
                            <h5 class="modal-title" id="editUserModalLabel">Edit Dish Information</h5>
                            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                        </div>
                        <div class="modal-body">
                            <div class="mb-3">
                                <label for="dishId" class="form-label">ID</label>
                                <input type="text" class="form-control" id="dishId" name="dishId" readonly>
                            </div>
                            <div class="mb-3">
                                <label for="dishname" class="form-label">Name</label>
                                <input type="text" class="form-control" id="dishname" name="dishname">
                            </div>
                            <div class="mb-3">
                                <label for="price" class="form-label">Price</label>
                                <input type="text" class="form-control" id="price" name="price">
                            </div>
                            <div class="mb-3">
                                <label for="brief_info" class="form-label">brief_info</label>
                                <input type="text" class="form-control" id="brief_info" name="brief_info">
                            </div>
                            <div class="mb-3">
                                <label for="description" class="form-label">Description</label>
                                <input type="text" class="form-control" id="description" name="description">
                            </div>
                            <div class="mb-3">
                                <label for="category_id" class="form-label">Category</label>
                                <select class="form-select" id="category_id" name="category_id">
                                    <c:forEach items="${categories}" var="cat">
                                        <option value="${cat.id}">${cat.name}</option>
                                    </c:forEach>
                                </select>
                            </div>
                            <div class="mb-3">
                                <label for="is_available" class="form-label">Status</label>
                                <select class="form-select" id="is_available" name="is_available">
                                    <option value="true">Available</option>
                                    <option value="false">Not Available</option>
                                </select>
                            </div>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                            <button type="submit" class="btn btn-primary">Save changes</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>

        <!-- Modal for Add User -->
        <div class="modal fade" id="addModal" tabindex="-1" aria-labelledby="addUserModalLabel" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <form id="addUserForm" action="manage-dish" method="post" enctype="multipart/form-data">
                        <input type="hidden" name="action" value="add"/>
                        <div class="modal-header">
                            <h5 class="modal-title" id="addUserModalLabel">Thêm Món ăn Mới</h5>
                            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                        </div>
                        <div class="modal-body">
                            <div class="mb-3">
                                <label for="name" class="form-label">Name</label>
                                <input type="text" class="form-control" id="dishname" name="dishname" required>
                            </div>
                            <div class="mb-3">
                                <label for="price" class="form-label">Price</label>
                                <input type="text" class="form-control" id="price" name="price" required>
                            </div>
                            <div class="mb-3">
                                <label for="category_id" class="form-label">Category</label>
                                <select class="form-select" id="category_id" name="category_id">
                                    <c:forEach items="${categories}" var="c">
                                        <option value="${c.id}">${c.name}</option>
                                    </c:forEach>
                                </select>
                            </div>
                            <div class="mb-3">
                                <label for="brief_info" class="form-label">Brief_info</label>
                                <input type="text" class="form-control" id="brief_info" name="brief_info" required>
                            </div><!-- comment -->
                            <div class="mb-3">
                                <label for="description" class="form-label">Description</label>
                                <input type="text" class="form-control" id="description" name="description" required>
                            </div>
                            <div class="mb-3">
                                <label for="dishImage" class="form-label">Dish Image</label>
                                <input type="file" class="form-control" id="dishImage" name="dishImage" accept="image/*" required>
                            </div>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Đóng</button>
                            <button type="submit" class="btn btn-primary">Lưu thay đổi</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>

        <script>
            $(document).ready(function () {
                $('#sortTable').DataTable({
                    "paging": true,
                    "ordering": true,
                    "info": true,
                    "pageLength": 10,
                    "language": {
                        "search": "Tìm kiếm:",
                        "lengthMenu": "Hiển thị _MENU_ mục",
                        "info": "Hiển thị từ _START_ đến _END_ của _TOTAL_ mục",
                        "paginate": {
                            "next": "Trang sau",
                            "previous": "Trang trước"
                        }
                    }
                });
            });
        </script>

        <script>
            function showEditModal(id, name, price, is_available, brief_info, description, category_id) {
                document.getElementById('dishId').value = id;
                document.getElementById('dishname').value = name;
                document.getElementById('price').value = price;
                document.getElementById('is_available').value = is_available;
                document.getElementById('brief_info').value = brief_info;
                document.getElementById('description').value = description;
                $('#category_id').val(category_id).change(); // This will trigger the correct category to be selected
            }
        </script>
    </body>
</html>


