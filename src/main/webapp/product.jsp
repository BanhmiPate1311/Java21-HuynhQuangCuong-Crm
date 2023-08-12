<%@ page import="model.Products" %><%--
  Created by IntelliJ IDEA.
  User: Mr Spider
  Date: 4/18/2023
  Time: 7:54 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ page isELIgnored="false" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
</head>
<body>

<div class="container">
  <% String contextPath = request.getContextPath(); %>

    <h1>QUẢN LÝ SẢN PHẨM</h1>


  <form action="<%=contextPath%>/product" method="post">
    <div class="form-group">
      <label>Tên sản phẩm</label>
      <input type="text" class="form-control" name="product_name">
    </div>
    <div class="form-group">
      <label>Số lượng</label>
      <input type="number" class="form-control" name="quantity">
    </div>
    <div class="form-group">
      <label>Giá bán</label>
      <input type="number" class="form-control" name="price">
    </div>
    <button type="submit" class="btn btn-primary">Lưu lại</button>

    <table class="mt-5 table">
      <thead>
      <tr>
        <th scope="col">STT</th>
        <th scope="col">Tên sản phẩm</th>
        <th scope="col">Số lượng</th>
        <th scope="col">Giá bán</th>
      </tr>
      </thead>

      <tbody>
      <c:set var="count" value="0" scope="page" />
    <c:forEach items="${productList}" var="product">
      <!-- Từng phần tử trong list hoặc mảng -->
      <tr>
        <c:set var="count" value="${count + 1}" scope="page"/>
        <th scope="row">${count}</th>
        <td>${product.product_name}</td>
        <td>${product.quantity}</td>
        <td>${product.price}</td

      </tr>
    </c:forEach>

      </tbody>
    </table>

  </form>
</div>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>
</body>
</html>
