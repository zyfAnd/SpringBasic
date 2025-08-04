<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Spring MVC 首页</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 50px;
            background-color: #f5f5f5;
        }
        .container {
            max-width: 800px;
            margin: 0 auto;
            background: white;
            padding: 30px;
            border-radius: 8px;
            box-shadow: 0 2px 10px rgba(0,0,0,0.1);
        }
        h1 {
            color: #333;
            text-align: center;
        }
        .message {
            background: #e8f5e8;
            padding: 15px;
            border-radius: 5px;
            margin: 20px 0;
            border-left: 4px solid #4CAF50;
        }
        .links {
            margin-top: 30px;
            text-align: center;
        }
        .links a {
            display: inline-block;
            margin: 0 10px;
            padding: 10px 20px;
            background: #4CAF50;
            color: white;
            text-decoration: none;
            border-radius: 5px;
        }
        .links a:hover {
            background: #45a049;
        }
    </style>
</head>
<body>
    <div class="container">
        <h1>Spring MVC 应用</h1>
        
        <div class="message">
            <strong>消息:</strong> ${message}
        </div>
        
        <div class="links">
            <a href="/user/hello">测试API接口</a>
            <a href="/">刷新页面</a>
        </div>
        
        <div style="margin-top: 30px; text-align: center; color: #666;">
            <p>当前时间: <%= new java.util.Date() %></p>
        </div>
    </div>
</body>
</html> 