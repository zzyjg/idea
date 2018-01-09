<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>file upload</title>
</head>
<body>
	<h1>上传文件</h1>
	<form action="<%= request.getContextPath()%>/doUpload" enctype="multipart/form-data"
		method="post">
		<input type="file" name="file" /> <input type="submit" />
	</form>
</body>
</html>