<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8" %>
<%@ page import="java.util.*" %>    
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">  
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>查看图片</title>
</head>
<script type="text/javascript">
    function seePic() {
        document.getElementById("seePicLink").click();
    }
    //查看
    function view(){
        window.open("http://localhost:8082/myProject/testController/readPhoto","_blank","top=100,left=100,height=600,width=1000,status=yes,toolbar=1,menubar=no,location=no,scrollbas=yes");

    }
</script>
<body>
500
</body>
</html>