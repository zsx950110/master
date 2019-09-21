$(function () {

    //密码框的监听
    $('#userpwd').bind('input propertychange', function()
    {    var regex=/<a\s+href\s*=\s*".*"\s*>.+?<\/\s*a\s*>\s*/g;
       /* var vv = "<"

        alert(vv.replace(regex,""))
        return ;*/
        var  multi = "---匹配非空，只要匹配到 就算文本框不为空/[^\s]/" +
            ""+
        "---匹配英文字母，从头到尾都必须是英文，中间如果有非字母，则本次匹配只能匹配到非字母，而无法匹配到 结尾，就返回false" +
            ""+
        "---验证日期格式为-- /^[1-9]\d{3}-((0?[1-9])|([1][0-2]))-((0?[1-9])|([12]\d)|(3[01]))$/";
        var v = $(this).val()
        //去掉文字中的空格

      //  $('#info').html("<span>"+str+"<span/>")
     //   return;
       var pos = regex.test($(this).val())
      //  alert(pos)
        if (!pos){
            $('#info').html("<span style='color: #ff442a'>输入不符合规则<span/>")

        }else{
          //  var regex1 = /(?:0*)(\d*)/
            $('#info').html("<span><span/>")
           // alert(regex1.exec(v)[1])




        }
    })

    $('#button,#Retrievenow,#denglou').css('opacity', 0.7).hover(function () {
        $(this).stop().fadeTo(650, 1);
    }, function () {
        $(this).stop().fadeTo(650, 0.7);
    });

    $("#button").click(function () {
        var username = $("#username").val();
        var userpwd = $("#userpwd").val();
   /*     if (username.length > 0 && userpwd.length > 0) {
            getLogStatx(1);
        }*/
        var user={
            "password":userpwd,
            "name":username
        }
        $.ajax({
            type: "POST",
            url: './ssoController/login.login',
            data: {"user":JSON.stringify(user)},
            success: function (data) {
          if (data=="success"){
              window.location.href="http://localhost:8080/sso/pages/success.html";
          } else{
              alert(data)
          }

            }
        });

    });
    $("#signOut").click(function(){
        $.ajax({
            type: "POST",
            url: '../ssoController/signOut.ctrl',
            success: function (data) {
                //登出成功后跳转至登录页面
                if("success"==data){
                    window.location.href="http://localhost:8080/sso/";
                }else{
                    alert("请求失败")
                }

            }
        });
    });
    ////忘记密码
    $("#iforget").click(function () {
        $("#login_model").hide();
        $("#forget_model").show();

    });

    ///取回密码 
    $("#Retrievenow").click(function () {
        var usrmail = $("#usrmail").val();
        if (!Test_email(usrmail)) {
           // alert(msgggg.pssjs1);
            return false;
        }
        $.ajax({
            type: "POST",
            url: '/users/AjaxServer/checkis.ashx',
            data: { typex: 5, usrmail: usrmail },
            success: function (data) {//

                alert(data);
                $("#login_model").show();
                $("#forget_model").hide();
                $("#usrmail").val("");
                $("#username").val("");
                $("#userpwd").val("");

            }
        });


    });
    //返回
    $("#denglou").click(function () {
        $("#usrmail").val("");
        $("#username").val("");
        $("#userpwd").val("");
        $("#login_model").show();
        $("#forget_model").hide();

    });


    //typexx 自动 还是手动
    function getLogStatx(typex) {
        var current = (location.href);
        var screenwidth = $(window).width();
        var screenheight = $(window).height();
        var username = $("#username").val();
        var userpwd = $("#userpwd").val();
        var issavecookies = "NO";
        if ($("#save_me").attr("checked") == true) {
            issavecookies = "Yes";
        }
        else {
            issavecookies = "NO";
        }
        var l_dot = screenwidth + "*" + screenheight;
        if (typex == "2") {
            if (username == null && userpwd == null) {
                ////保存了cook
                username = $.cookie('codeusername');
                userpwd = $.cookie('codeppsd');
                $.ajax({
                    type: "POST",
                    url: '/users/AjaxServer/Ajax_User_Loading.ashx',
                    data: { username: username, userpwd: userpwd, issavecookies: issavecookies, l_dot: l_dot, typex: 2 },
                    success: function (data) {///去更新cookies
                        if (current.indexOf("index.aspx") > -1) {


                        } else {

                            if (data == "0" || data == "1") {
                                window.location.href = "http://home.wopop.com/UserHome/ot5lst/website.aspx";

                            } else {
                                ot5alert(data, "1");

                            }
                        }
                    }
                });


            }
        } else if (typex == "1") {
            ///// 手動 登錄
            $.ajax({
                type: "POST",
                url: '/users/AjaxServer/Ajax_User_Loading.ashx',
                data: { username: username, userpwd: userpwd, issavecookies: issavecookies, l_dot: l_dot, typex: 1 },
                success: function (data) {///去更新cookies
                    if (data == "0" || data == "1") {
                        window.location.href = "http://home.wopop.com/UserHome/ot5lst/website.aspx";

                    } else {
                        ot5alert(data, "1");

                    }
                }
            });
        }
    }


});
//Email 规则以后重新整理所有网站关于js 验证
function Test_email(strEmail) { var myReg = /^[-a-z0-9\._]+@([-a-z0-9\-]+\.)+[a-z0-9]{2,3}$/i; if (myReg.test(strEmail)) return true; return false; }
