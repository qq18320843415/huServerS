<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: hu
  Date: 2018/3/23
  Time: 11:27
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<body>
<c:forEach items="${result }" var="item">
    <c:forEach items="${item }" var="m">
        <c:if test="${m.key eq 'realName' }">
            上传文件名（压缩后）: ${m.value }
            <br />
        </c:if>
        <c:if test="${m.key eq 'path' }">
            上传文件路径: ${m.value }
            <br />
        </c:if>
        <c:if test="${m.key eq 'alais' }">
            别名: ${m.value }
            <br />
        </c:if>
    </c:forEach>
</c:forEach>
</body>
</body>
</html>
