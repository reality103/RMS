<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<style>
    .nav-link.active {
        color: red;
        font-weight: bold;
    }

    .nav-item a .item-numb {
        width: 15px;
        height: 18px;
        text-align: center;
        line-height: 20px;
        position: absolute;
        background: #0089ff;
        color: #fff;
        font-size: 12px;
        border-radius: 50%;
    }


    .ti-shopping-cart {
        font-size: 16px;
        display: inline-block;
        font-size: inherit;
        -webkit-font-smoothing: antialiased;
        -moz-osx-font-smoothing: grayscale;
    }

    #header {
        position: -webkit-sticky; /* Safari */
        position: sticky;
        top: 0;
        z-index: 999;
        background-color: white;
        box-shadow: 0 4px 2px -2px gray;
    }

</style>
<c:if test="${sessionScope.noti !=null}">
    <div class="alert ${noti.toLowerCase().contains("thành công") ? "alert-success" : "alert-danger"} alert-dismissible fade show " role="alert" style=" position: fixed; z-index: 15 ; margin-left: 80%; margin-top: 5%;">
        <strong>${noti}</strong>
        <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
    </div>
    <c:remove var="noti" scope="session" />

</c:if>
<div id="header">
    <nav class="navbar navbar-expand-lg navbar-header bg-body">
        <div class="container-fluid">
            <nav class="navbar navbar-light bg-light">
                <a class="navbar-brand" href="dishes" style="font-size:20px">
                    <img src="./images/logo.png" width="50" height="50" class="d-inline-block align-top" alt="">
                    <b>SWP Restaurant</b>
                </a>
            </nav>
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>

            <div class="collapse navbar-collapse" id="navbarSupportedContent">
                <!-- Căn giữa phần điều hướng -->
                <c:if test="${sessionScope.user == null or sessionScope.user.getRole() eq 'user'}">
                    <nav class="mx-auto">
                        <div class="nav nav-tabs" id="nav-tab" role="tablist">
                            <a class="nav-item nav-link ${active == 1 ? 'active' : ''}"
                               data-toggle="link" href="dishes" role="tab" 
                               aria-controls="nav-home" aria-selected="true" style="font-size: 15px"><b>Menu</b></a>
                            &nbsp; &nbsp;
                            <button data-toggle="modal" data-target="#contactModal" 
                                    class="nav-item nav-link" aria-controls="nav-contact" 
                                    aria-selected="false" style="font-size: 15px"><b>Liên hệ</b></button>
                        </div>
                    </nav>
                </c:if>
                <!-- Đẩy phần navbar-nav sang phải -->
                <ul class="navbar-nav mb-2 mb-lg-0 ms-auto">
                    <c:if test="${sessionScope.user != null}">
                        <div class="btn-group">
                            <button type="button" style="border-radius: 4px" class="btn btn-outline-dark dropdown-toggle py-2 px-4" data-toggle="dropdown" aria-expanded="false">
                                <c:if test="${sessionScope.user.avatar != null && sessionScope.user.avatar ne ''}">
                                    <img class="rounded-circle" width="20px" src="${sessionScope.user.avatar}">
                                    <span class="font-weight-bold" style="font-size: 16px">${sessionScope.user.fullName ne "" ? sessionScope.user.fullName : sessionScope.user.email}</span>
                                </c:if>
                                <c:if test="${sessionScope.user.avatar == null || sessionScope.user.avatar eq ''}">
                                    <img class="rounded-circle" width="20px" src="./images/user.png">
                                    <span class="font-weight-bold" style="font-size: 16px">${sessionScope.user.fullName ne "" ? sessionScope.user.fullName : sessionScope.user.email}</span>
                                </c:if>
                            </button>

                            <div class="dropdown-menu">
                                <c:if test="${sessionScope.user != null && sessionScope.user.avatar ne 'headmaster'}">
                                    <a class="dropdown-item" href="/SWP/profile">My Profile</a>
                                </c:if>
                                <a class="dropdown-item" href="/SWP/logout">Logout</a>
                            </div>
                        </div>
                    </c:if>
                    <c:if test="${sessionScope.user == null}">
                        <li class="nav-item">
                            <a href="/SWP/login"><i style="border: 1px solid black;border-radius: 10px;" type="button" class="ti-user btn btn-icon py-2 px-4"></i></a>
                        </li>
                    </c:if>
                    <c:if test="${sessionScope.user ne null and sessionScope.user.getRole() eq 'user'}">
                        <li class="nav-item">
                            <a class="nav-link btn btn-icon py-2 px-4" href="cartinfo?is_takeaway=true" tabindex="-1" aria-disabled="true">
                                <i class="ti-shopping-cart"></i> 
                                <c:if test="${sessionScope.totalItem > 0}">
                                    <span style="background: blue;" class="item-numb">${sessionScope.totalItem}</span>
                                </c:if>
                            </a>
                        </li>
                    </c:if>
                </ul>
            </div>
        </div>
    </nav>
</div>


<script>
    if (document.querySelector('.alert')) {
        document.querySelectorAll('.alert').forEach(function ($el) {
            setTimeout(() => {
                $el.classList.remove('show');
            }, 3000);
        });
    }
</script>

<!-- contact modal -->
<div class="modal fade col-md-12 text-center" role="dialog" id="contactModal">
    <div class="modal-dialog">
        <div class="modal-content" style="border-radius: 10px; margin-top: 50px;">
            <div class="modal-header text-center">
                <h2 id="contactModal" ><b>Thông tin liên hệ</b></h2><br>
            </div>
            <div class="modal-body">
                <div style="padding-top:50px;padding-bottom:40px; text-align: center;">
                    <div>
                        <p>• Hotline: 0123456789</p>
                        <p>• IG: @swprestaurant</p>
                        <p>• FB: fb.com/swprestaurant</p>
                        <p>• Address: Đại học FPT Hà Nội</p>
                        <p>-------------------------------</p>
                    </div>
                </div>
                <div class="map">
                    <iframe src="https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d3724.483649132689!2d105.52468021467764!3d21.01332549368153!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x31345b465a4e65fb%3A0xaae6040cfabe8fe!2sFPT%20University!5e0!3m2!1sen!2s!4v1676534300383!5m2!1sen!2s" 
                            width="100%" height="400" style="border:0;" allowfullscreen="" loading="lazy" 
                            referrerpolicy="no-referrer-when-downgrade">
                    </iframe>
                </div>
            </div>
        </div>
    </div>
</div>