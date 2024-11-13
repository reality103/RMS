<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!-- Notification User -->
<c:if test="${notification !=null}">
    <div class="alert ${notification.toLowerCase().contains("thành công") ? "alert-success" : "alert-danger"} alert-dismissible fade show " role="alert" style=" position: fixed; z-index: 15 ; margin-left: 80%; margin-top: 5%;">
        <strong>${notification}</strong>
        <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
    </div>
</c:if>

<c:if test="${sessionScope.message !=null}">
    <div class="alert ${sessionScope.message.toLowerCase().contains("thành công") ? "alert-success" : "alert-danger"} alert-dismissible fade show " role="alert" style="position: fixed; z-index: 15 ; margin-left: 80%; margin-top: 5%;">
        <strong>${sessionScope.message}</strong>
        <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
    </div>
    <c:remove var="message" scope="session" />
</c:if>

<!-- Login -->
<div class="modal fade col-md-12" role="dialog" id="loginModal">
    <div class="modal-dialog">
        <div class="modal-content" style="border-radius: 10px; margin-top: 258px;">
            <div class="modal-header">
                <h2 class="" id="loginModal" style="text-align:center; font-family: Arial"><b style="padding-left: 150px;">Đăng Nhập</b></h2><br>
            </div>
            <div class="modal-body">
                <form action="login" method="post">
                    <div class="form-group">
                        <label class="labels" style="font-size: 12px;font-weight: bold;">Email <b class="ic">*</b></label>
                        <input name="email" type="email" class="form-control" placeholder="Email của bạn"style="border-radius: 100px;" required="">
                    </div>
                    <div class="form-group">
                        <label class="labels" style="font-size: 12px; font-weight: bold;">Mật khẩu <b class="ic">*</b></label>
                        <input name="password" type="password" class="form-control" placeholder="Mật khẩu" style="border-radius: 100px;" required>
                    </div>
                    <center><button type="submit" class="btn btn-dark" style="padding-right: 193px;padding-left: 193px; border-radius: 100px;">Đăng nhập</button></center>
                    <center>
                        <a class="btn btn-lg btn-google btn-block text btn-outline " style="border-radius: 150px;"
                           href="https://accounts.google.com/o/oauth2/auth?scope=email%20profile 
                           &redirect_uri=http://localhost:8080/SWP391_Fitnesscare/logingoogle&response_type=code
                           &client_id=658277419145-hml20trk79j37nvtdpb7qja08dlppd0u.apps.googleusercontent.com&approval_prompt=force">
                            <img src="https://img.icons8.com/color/16/000000/google-logo.png"> Login with Google
                        </a>
                    </center>
                </form>
                <br><br>
                <a type="button" data-toggle="modal" data-dismiss="modal" data-target="#ResetPasswordModal" style="float:right; text-decoration: none; border-radius: 100px;">Quên Mật Khẩu</a>
                <a type="button" data-toggle="modal" data-dismiss="modal" data-target="#registerModal" style="float:left; text-decoration: none; border-radius: 100px;">Đăng ký tài khoản mới</a>
            </div>
        </div>
    </div>
</div>

<!-- user profile -->
<div class="modal fade col-md-12" role="dialog" id="userProfileModal" style="padding-right: 18px" >
    <div class="modal-dialog">
        <div class="modal-content" style="border-radius: 10px; margin-top: 10px; min-width: 1150px; margin-left: -320px;">
            <div class="modal-header">
                <h2 class="" id="userProfileModal" style="text-align:center; font-family: Arial"><b style="padding-left: 400px;"> Thông tin cá nhân</b></h2><br>
            </div>

            <div class="modal-body">
                <section>
                    <div class="container">

                        <div class="row">
                            <c:if test="${sessionScope.us.avatar != null && sessionScope.us.avatar ne ''}">
                                <div class="col-md-4">
                                    <div class="d-flex flex-column align-items-center text-center p-3 py-5">
                                        <img class="rounded-circle mt-5" width="150px" height="150px" src="${sessionScope.us.avatar}">
                                        <span class="font-weight-bold">${sessionScope.us.fullName}</span>
                                        <span class="text-black-50">${sessionScope.us.email}</span>
                                    </div>
                                </div>
                            </c:if>
                            <c:if test="${sessionScope.us.avatar == null || sessionScope.us.avatar eq ''}">
                                <div class="col-md-4">
                                    <div class="d-flex flex-column align-items-center text-center p-3 py-5">
                                        <img class="rounded-circle mt-5" width="150px" height="150px" src="./images/avatar/avtdefault.png"/>
                                        <span class="font-weight-bold">${sessionScope.us.fullName}</span><span class="text-black-50">${sessionScope.us.email}</span>
                                    </div>
                                </div>
                            </c:if>

                            <div class="col-md-8">
                                <div class="p-3 py-5">
                                    <form action="editprofile" method="post" enctype="multipart/form-data"  name="editProfileForm" onsubmit="return validateFormEditProfile()">

                                        <div class="row mt-4">
                                            <div class="row mt-4 col-md-6">
                                                <label class="labels" style="font-size: 10px;">Họ và tên<b class="ic">*</b></label>
                                                <input type="text" class="form-control" name="fullName" placeholder="Họ và tên" value="${sessionScope.us.fullName}">
                                            </div>&nbsp;
                                            <div class="row mt-4 col-md-6">
                                                <label class="labels" style="font-size: 10px;">Số điện thoại<b class="ic">*</b></label>
                                                <input type="text" class="form-control" name="phone" placeholder="Số điện thoại" value="${sessionScope.us.phone}">
                                            </div>&nbsp;
                                            <div class="row mt-4 col-md-6">
                                                <label class="labels" style="font-size: 10px;">Địa chỉ<b class="ic">*</b></label>
                                                <input type="text" class="form-control" name="address" placeholder="Địa chỉ" value="${sessionScope.us.address}">
                                            </div>&nbsp;
                                            <div class="row mt-4 col-md-6">
                                                <label class="labels" style="font-size: 10px;">Email<b class="ic">*</b></label>
                                                <input type="text" class="form-control" name="email" placeholder="Email" value="${sessionScope.us.email}">
                                            </div>&nbsp;
                                            <div class="row mt-4 col-md-6">
                                                <label class="labels" style="font-size: 10px;">Ảnh đại diện<b class="ic">*</b></label>
                                                <input type="file" class="form-control" accept="image/*" name="avatar" placeholder="Ảnh đại diện" value="${sessionScope.us.avatar}">
                                            </div>&nbsp;
                                            <div class="row mt-4 col-md-3"><label class="labels" style="font-size: 10px;" name="gender" value="${sessionScope.us.gender}">Giới tính<b class="ic">*</b></label>
                                                <div>
                                                    <span>
                                                        <input name="gender" type="radio" value="1" ${sessionScope.us.gender  ? 'checked' : ''}/>  Nam
                                                    </span> &nbsp; &nbsp; &nbsp;
                                                    <span>
                                                        <input name="gender" type="radio" value="0" ${!sessionScope.us.gender  ? 'checked' : ''}/>  Nữ
                                                    </span> 
                                                </div>               
                                            </div>

                                            <input type="hidden" name="user_id" value="${sessionScope.us.user_id}"/>
                                            <c:if test="${sessionScope.us.password.length() ne  0}">
                                                <div class="row mt-4 col-md-3">
                                                    <label class="labels" style="font-size: 10px;">Mật khẩu</label>
                                                    <a href="#" style="text-decoration: none;">
                                                        <button type="button" data-toggle="modal" data-dismiss="modal" data-target="#ChangePasswordModal"  class="btn btn-dark" value="">Đổi mật khẩu</button>
                                                    </a>
                                                </div>
                                            </c:if>
                                        </div>

                                        <input type="hidden" class="form-control" name="password"  value="${sessionScope.us.password}">
                                        <div class="row mt-5 col-md-6 text-center">
                                            <button class="btn btn-dark" type="submit">Lưu</button>
                                        </div>
                                    </form>
                                    <div class="row mt-5 col-md-6 text-center d-flex ">
                                        <a type="button" href="home" type="button" data-toggle="modal" 
                                           data-dismiss="modal" style="padding-left: 150px; text-decoration: none;
                                           border-radius: 100px;" >
                                            Quay về trang chủ
                                        </a>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </section>
            </div>
        </div>
    </div>
</div> 

<!-- Change Password -->
<div class="modal fade col-md-12" role="dialog" id="ChangePasswordModal" >
    <div class="modal-dialog">
        <div class="modal-content" style="border-radius: 10px; margin-top: 150px;">
            <div class="modal-header">
                <h1 style="text-align: center ; margin-left: 50px;">Thay đổi mật khẩu</h1>
            </div>
            <div class="modal-body">
                <form action="changepassword" method="post" name="changePasswordForm" onsubmit="return validateChangePasswordForm()">
                    <input type="hidden" name="user_id" value="${sessionScope.us.user_id}"/>
                    <b>Mật khẩu cũ<b class="ic">*</b>&nbsp;&nbsp;

                        <div class="form-group">
                            <input name="old_pass" type="password" class="form-control" placeholder="Mật khẩu cũ"style="border-radius: 100px;" required="">
                        </div>
                        <b>Mật khẩu mới<b class="ic">*</b></b>&nbsp;&nbsp;

                        <div class="form-group">
                            <input name="new_pass1" type="password" class="form-control" placeholder="Mật khẩu mới"style="border-radius: 100px;" required>
                        </div>
                        <b>Nhập lại mật khẩu mới<b class="ic">*</b></b>&nbsp;&nbsp;

                        <div class="form-group">
                            <input name="new_pass2" type="password" class="form-control" placeholder="Nhập lại mật khẩu mới"style="border-radius: 100px;" required>
                        </div>
                        <br>
                        <center><button type="submit" class="btn btn-dark" style="padding-right: 160px;padding-left: 160px; border-radius: 100px;">Cập nhập mật khẩu</button></center>
                </form>
                <br><br>
                <a type="button" data-toggle="modal" data-dismiss="modal" data-target="#userProfileModal" style="padding-left: 190px; text-decoration: none; border-radius: 100px;">Quay lại </a> 
            </div>
        </div>
    </div>
</div>

<!-- Register -->
<div class="modal fade col-md-12" role="dialog" id="registerModal" style="padding-right: 18px" >
    <div class="modal-dialog">
        <div class="modal-content" style="border-radius: 10px; margin-top: -20px;">
            <div class="modal-header">
                <h2 class="" id="registerModal" style="text-align:center; font-family: Arial"><b style="padding-left: 100px;">Đăng ký tài khoản</b></h2><br>
            </div>
            <div class="modal-body">
                <section>
                    <div class="container">
                        <form name="registerForm" action="register" method="POST" onsubmit="return validateFormRegister()">
                            <div class="row">
                                <div class="col-md-12">
                                    <div class="row form-group">
                                        <label class="labels" style="font-size: 12px;">Họ và tên <b class="ic">*</b></label>
                                        <input type="text" class="form-control" id="name" placeholder=" Họ và tên " name="fullName" style="border-radius: 100px;" required>
                                    </div></div>
                                <div class="col-md-12">
                                    <div class="row form-group">
                                        <label class="labels" style="font-size: 12px;">Email <b class="ic">*</b></label>
                                        <input type="email" class="form-control" id="email" placeholder="Email" name="email" style="border-radius: 100px;" required>
                                    </div>
                                </div>
                                <div class="col-md-12">
                                    <div class="row form-group">
                                        <label class="labels" style="font-size: 12px;">Số điện thoại <b class="ic">*</b></label>
                                        <input type="text" class="form-control" id="phone" placeholder="Số điện thoại" name="phone" style="border-radius: 100px;" required>
                                    </div></div>
                                <div class="col-md-12">
                                    <div class="row form-group">
                                        <label class="labels" style="font-size: 12px;">Mật khẩu <b class="ic">*</b></label>
                                        <input type="password" class="form-control" id="pwd" placeholder="Mật khẩu" name="password" style="border-radius: 100px;" required>
                                    </div>
                                </div>
                                <div class="col-md-12">
                                    <div class="row form-group">
                                        <label class="labels" style="font-size: 12px;">Nhập lại Mật khẩu <b class="ic">*</b></label>
                                        <input type="password" class="form-control" id="repwd" placeholder="Nhập lại mật khẩu" name="repassword" style="border-radius: 100px;" required>
                                    </div>
                                </div>

                                <div class="col-md-12">
                                    <div class="form-group" required>
                                        Giới tính<b class="ic">*</b>&nbsp;&nbsp;&nbsp;
                                        <input class="" name="gender" type="radio" value="True" required/>&nbsp; Nam
                                        &nbsp;&nbsp;&nbsp;&nbsp;
                                        <input class="" name="gender" type="radio" value="False" required/>&nbsp; Nữ
                                    </div>
                                </div>
                                <div class="col-md-12">
                                    <div class="row form-group">
                                        <label class="labels" style="font-size: 12px;">Địa chỉ <b class="ic">*</b></label>
                                        <input type="text" class="form-control" id="address" placeholder="Nhập địa chỉ" name="address" style="border-radius: 100px;" required>
                                    </div>
                                </div>
                                <br><br>
                                <center>
                                    <button type="submit" class="btn btn-dark" style="padding-right: 190px;padding-left: 190px; border-radius: 100px;">Đăng ký</button>
                                </center><br><br>
                            </div>
                        </form>
                        <a type="button" data-toggle="modal" data-dismiss="modal" data-target="#loginModal" style="padding-left: 150px; text-decoration: none; border-radius: 100px;">Quay lại đăng nhập</a> 
                    </div> 
                </section>
            </div>
        </div>
    </div>
</div> 

<!-- ResetPassword -->
<div class="modal fade col-md-12" role="dialog" id="ResetPasswordModal">
    <div class="modal-dialog">
        <div class="modal-content" style="border-radius: 10px; margin-top: 150px;">
            <div class="modal-header">
                <h1 style="text-align: center ; margin-left: 70px;">Cấp lại mật khẩu</h1>
            </div>
            <div class="modal-body">
                <form action="resetpassword" method="post">
                    <div class="row form-group">
                        <label class="labels" style="font-size: 12px;">Email <b class="ic">*</b></label>
                        <input name="email" type="email" class="form-control" placeholder="Email của bạn"style="border-radius: 100px;" required>
                    </div>
                    <br>
                    <center>
                        <button type="submit" class="btn btn-dark" style="padding-right: 200px;
                                padding-left: 200px;
                                border-radius: 100px;
                                margin-bottom: -40px;
                                height: 50px;">Gửi
                        </button>
                    </center>
                </form>
                <br><br>
                <a type="button" data-toggle="modal" data-dismiss="modal" data-target="#loginModal" style="padding-left: 170px; text-decoration: none; border-radius: 100px;">Quay lại Đăng nhập</a> 
            </div>
        </div>
    </div>
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

<!--<script>
    var popup = document.getElementById("popup");

// Đặt thời gian chờ 5 giây (5000 mili giây) để đóng popup
    setTimeout(function () {
        // Ẩn phần tử popup bằng cách đặt thuộc tính CSS display thành none
        popup.style.display = "none";
    }, 3000);
</script>-->
<script>
    function validateFormRegister() {
        
        // Lấy giá trị của các trường input
        const name = document.forms["registerForm"]["fullName"].value.trim();
        const email = document.forms["registerForm"]["email"].value.trim();
        const phone = document.forms["registerForm"]["phone"].value.trim();
        const password = document.forms["registerForm"]["password"].value.trim();
        const repassword = document.forms["registerForm"]["repassword"].value.trim();
        const address = document.forms["registerForm"]["address"].value.trim();

        // Kiểm tra độ dài của tên
        if (name.length < 8 || name.trim().length > 32) {
            alert("Vui lòng nhập tên có độ dài từ 8 đến 32 ký tự");

            return false;
        }

        // Kiểm tra định dạng email
        const emailRegex = /^[a-zA-Z][a-zA-Z0-9]*@[a-zA-Z]+\.[a-zA-Z]{2,}$/;
        if (!emailRegex.test(email)) {
            alert("Vui lòng nhập email hợp lệ");
            return false;
        }

        // Kiểm tra định dạng số điện thoại
        const phoneRegex = /^0\d{9}$/;
        if (!phoneRegex.test(phone)) {
            alert("Vui lòng nhập số điện thoại hợp lệ");
            return false;
        }

        if (password.length < 8 || password.length > 32) {
            alert("Vui lòng nhập mật khẩu có độ dài từ 8 đến 32 ký tự");
            return false;
        }

        if (password !== repassword) {
            alert("Nhập lại mật khẩu không đúng. Hãy kiểm tra lại");
            return false;
        }

        if (address.length < 1) {
            alert("Độ dài address không hợp lệ. Hãy nhập lại!");
            return false;
        }
        // Nếu không có lỗi, cho phép submit form
        return true;
    }
</script>

<script>
    function validateChangePasswordForm() {
        const oldPassword = document.forms["changePasswordForm"]["old_pass"].value.trim();
        const newPassword1 = document.forms["changePasswordForm"]["new_pass1"].value.trim();
        const newPassword2 = document.forms["changePasswordForm"]["new_pass2"].value.trim();
        if ((oldPassword.length < 8 || oldPassword.length > 32)) {
            alert("Mật khẩu cũ có độ dài từ 8 đến 32 ký tự");
            return false;
        }
        if ((newPassword1.length < 8 || newPassword1.length > 32)) {
            alert("Vui lòng nhập mật khẩu mới có độ dài từ 8 đến 32 ký tự");
            return false;
        }
        if (newPassword1 !== newPassword2) {
            alert("Nhập lại mật khẩu không đúng. Hãy kiểm tra lại");
            return false;
        }
        return true;
    }

</script>
<script>
    function validateFormEditProfile() {
        const name = document.forms["editProfileForm"]["fullName"].value.trim();
        const email = document.forms["editProfileForm"]["email"].value.trim();
        const phone = document.forms["editProfileForm"]["phone"].value.trim();
        const address = document.forms["editProfileForm"]["address"].value.trim();
        if (name.length < 8 || namename.length > 32) {
            alert("Tên phải từ 8-32 ký tự");
            return false;
        }

        const regexEmail = /^[a-zA-Z][a-zA-Z0-9]*@[a-zA-z]+\.[a-zA-Z]{2,}$/;
        if (!regexEmail.test(email)) {
            alert("Vui lòng email hợp lệ");

            return false;
        }

        const regexPhone = /^0\d{9}$/;
        if (!regexPhone.test(phone)) {
            alert("Vui lòng nhập số điện thoại hợp lệ");
            return false;
        }

        if (address.length <= 0) {
            alert("Vui lòng nhập địa chỉ hợp lệ");
            return false;
        }
        return true;
    }
</script>