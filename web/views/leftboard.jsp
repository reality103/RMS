<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<div id="layoutSidenav_nav">
    <nav class="sb-sidenav accordion sb-sidenav-dark" id="sidenavAccordion">
        <div class="sb-sidenav-menu">          
            <div class="nav">
                <c:if test="${sessionScope.user.getRole() eq 'admin'}">
                    <div class="sb-sidenav-menu-heading">Bảng điểu khiển</div>
                    <a class="nav-link" href="admin-manage">        
                        <div class="sb-nav-link-icon"><i class="fas fa-tachometer-alt"></i></div>
                        Thống kê
                    </a>
                </c:if>

                <div class="sb-sidenav-menu-heading">Quản lý</div>
                <c:if test="${sessionScope.user.getRole() eq 'admin' || sessionScope.user.getRole() eq 'staff'}">

                    <a class="nav-link" href="manageproductlist">        
                        <div class="sb-nav-link-icon"><i class="fas fa-columns"></i></div>
                        Quản lý sản phẩm
                    </a>

                    <c:if test="${sessionScope.user.getRole() eq 'admin'}">
                        <a class="nav-link" href="managemateriallist">        
                            <div class="sb-nav-link-icon"><i class="fas fa-columns"></i></div>
                            Quản lý nguyên liệu
                        </a>
                    </c:if>

                    <a class="nav-link" href="manageorder?key=&status=0">        
                        <div class="sb-nav-link-icon"><i class="fas fa-columns"></i></div>
                        Quản lý đơn hàng
                    </a>

                </c:if>
            </div>
        </div>
        <div class="sb-sidenav-footer">
            <div class="small">Đăng nhập bởi:</div>
            <b>${sessionScope.user.fullName}</b>
        </div>
    </nav>
</div>