<%@ page import="model.Users" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page isELIgnored="false" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<%--
    <%! %> : Thẻ dùng để khai báo biến
    <% %> : Thẻ dùng để xử lý logic code, thẻ đa năng
    <%= %> : Xuất giá trị của biến ra màn hình
--%>
<head>
    <title>Bootstrap Example</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
</head>
<body>

<%!
    int count = 0;
    Users users = new Users();
%>
<%--
    Khi biến count chia hết cho 2 thì mới xuất username ra màn hình
    Ngược lại không làm gì hết
--%>
<%--<%--%>
<%--    int kq = (int) request.getAttribute("kq");--%>
<%--    users.setUsername("nguyenvana");--%>
<%--    users.setPassword("123456");--%>
<%--    count++;--%>
<%--%>--%>

<%--<b style="padding: 20px"> <%= kq %> </b>--%>
<%--<b style="padding: 20px"> <%= users.getUsername() %> </b>--%>
<%--<% if(count %2 == 0) { %>--%>
<%--<b style="padding: 20px"> <%= users.getUsername() %> </b>--%>
<%--<% } %>--%>
<%
    String msg = (String) request.getAttribute("msg");

%>

<%--    <%= msg %>--%>
<div class="container">
    <div class="row mt-5">
        <div class="col-md-5 m-auto mt-5">
            <h3 class="text-center">ĐĂNG NHẬP HỆ THỐNG</h3>
            <h3 class="text-center" style="color: red;">${errorMessage}</h3>
            <div class="p-4 border mt-4">
                <%--                action : Đường dẫn mà tham số sẽ được gửi về
                                    method  : Phương thức gửi tham số cho đường dẫn khai báo ở action
                --%>
                <% String contextPath = request.getContextPath(); %>

                <form action="<%=contextPath%>/login" method="post">
                    <div class="form-group">
                        <label>Email</label>
                        <input type="email" class="form-control" name="username" value="${username}">
                    </div>
                    <div class="form-group">
                        <label>Mật khẩu</label>
                        <input type="password" class="form-control" name="password" value="${password}">
                    </div>
                    <!-- Checkbox -->
                    <div class="form-check">
                        <input class="form-check-input" type="checkbox" name="remember" id="form2Example31" />
                        <label class="form-check-label" for="form2Example31"> Remember me </label>
                    </div>
                    <button type="submit" class="btn btn-primary">Đăng nhập</button>


                </form>
            </div>
        </div>
    </div>

</div>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>
</body>
</html>