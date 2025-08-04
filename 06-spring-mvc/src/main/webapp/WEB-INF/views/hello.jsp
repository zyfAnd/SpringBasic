<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Hello Spring MVC</title>
</head>
<body>
    <h1>${message}</h1>
    <p>欢迎使用Spring MVC!</p>
    <a href="${pageContext.request.contextPath}/user/list">查看用户列表</a>
</body>
</html> 