<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>通过流的形式加载图片到JSP页面显示</title>
</head>
<body>
<h1>通过流的形式加载图片到JSP页面显示</h1>

<img alt="" src="http://localhost:8082/myProject/testController/readPhoto" title="" style="width: auto;height: auto">
<object data="http://localhost:8082/myProject/testController/readPhoto" type="application/pdf" width="750px" height="750px">
    <embed src="http://localhost:8082/myProject/testController/readPhoto" type="application/pdf">
    <p>This browser does not support PDFs. Please download the PDF to view it: <a href="http://localhost:8082/myProject/testController/readPhoto">Download PDF</a>.</p>
    </embed>
</object>
</body>


</html>