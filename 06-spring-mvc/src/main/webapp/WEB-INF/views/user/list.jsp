<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>用户列表</title>
</head>
<body>
    <h1>用户列表</h1>
    <ul>
        <c:forEach items="${users}" var="user">
            <li>${user}</li>
        </c:forEach>
    </ul>
    <a href="${pageContext.request.contextPath}/user/hello">返回首页</a>
</body>
</html> 