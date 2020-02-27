<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8" %>
<%@ page import="java.util.*" %>    
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">  
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>查看图片</title>
</head>
<script type="text/javascript" src="static/jquery.min.js"/>
<script type="text/javascript">
    function seePic() {
        document.getElementById("seePicLink").click();
    }
    //查看
    function view(){
        window.open("http://localhost:8082/myProject/testController/readPhoto","_blank","top=100,left=100,height=600,width=1000,status=yes,toolbar=1,menubar=no,location=no,scrollbas=yes");

    }
$(function () {
   $("#button").click(function () {
       for(var i =0;i<1;i++){
           $.ajax({
               url: "./testController/ajaxTest",
               type: "post",
               success: function (data) {
                  $("#div").innerHTML="<tr><td>"+data+"</td></tr></br>" ;
               },
               error: function () {
                   console.log("失败")
               }

           })
       }

   }) 
})

</script>
<body>
<h1>查看图片</h1>
<a href="./viewer.html" style="display: none;" id="seePicLink"></a>
<button onclick="seePic()">查看大pdf</button>
<button onclick="view()">查看hhpdf</button>
<a href="search.html">搜索页面</a>
<button  id="button">ajax异步测试</button>
<div id="div"></div>
</body>
</html>