<%--
  Created by IntelliJ IDEA.
  User: hu
  Date: 2018/3/23
  Time: 11:26
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<form enctype="multipart/form-data" action="${pageContext.request.contextPath}/upload.form" method="post" >
    <input type="file" name="file1" />
    别名：<input type="text" name="alais" />
    <br />
    <input type="file" name="file2" />
    别名：<input type="text" name="alais" />
    <br />
    <input type="file" name="file3" />
    别名：<input type="text" name="alais" />
    <br><br>
    <input type="submit" value="上传" />
</form>
</body>
</html>
