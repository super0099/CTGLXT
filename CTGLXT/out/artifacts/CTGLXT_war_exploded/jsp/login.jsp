<%--
  Created by IntelliJ IDEA.
  User: super007
  Date: 2021/5/9
  Time: 9:49
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><!--需要引入jstl-1.2.jar包-->
<c:set var="ctx" value="${pageContext.request.contextPath}" scope="page"/><!--需要引入jstl-1.2.jar包-->
<html>
<head>
    <title>餐厅管理系统</title>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport"
          content="width=device-width,user-scalable=yes, minimum-scale=0.4, initial-scale=0.8"/>
    <meta http-equiv="Cache-Control" content="no-siteapp"/>
    <!--[if lt IE 9]>
    <script src="https://cdn.staticfile.org/html5shiv/r29/html5.min.js"></script>
    <script src="https://cdn.staticfile.org/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
    <script>
        if (window.top.location.href !== window.location.href) {
            window.top.location.href = window.location.href;
        }
    </script>
    <link rel="stylesheet" href="${ctx}/static/css/login.css">
</head>
<body>
<div class="content">
    <div class="head">
        <div class="head-a fl">
            <p><span><b>穷比快乐屋</b></span><br>The Poor Happy Shop</p>
        </div>
        <div class="head-c fl"></div>
        <div class="head-b fr">
            <p><span><b>运营商系统</b></span><br>hnm.cntslm.com</p>
        </div>
    </div>
    <div class="login" id="lg">
        <div class="top-a">
            <div class="line"></div>
            <h1>登录</h1>
            <div class="line"></div>
        </div>
        <div class="head-d">
            <form action="">
                <p><input type="text" placeholder="用户名:super007" class="input-a" id="account" autocomplete="off"></p>
                <p><input type="password" placeholder="密码:a123456" class="input-a" id="password" autocomplete="off"></p>
                <span><input type="text" placeholder="输入验证码" class="input-b" id="code" autocomplete="off"><img src="${ctx}/login?mothed=imgCode" onclick="change(this)"/></span>
                <button type="reset" hidden id="rests"></button>
            </form>
        </div>
        <div class="div-a"><button type="button" class="logon" onClick="loging()">登&nbsp;&nbsp;录</button></div>
        <div style="margin-top:10px;color:aqua;font-size:12px;margin-left:5px;"><a href="#" id="Register">没有账号?😭点这里注册😎</a></div>
    </div>
    <div class="login" id="rs" style="display:none;">
        <div class="top-a">
            <div class="line"></div>
            <h1>注册</h1>
            <div class="line"></div>
        </div>
        <div class="head-d">
            <form action="">
                <input value="3" hidden id="userType"/>
                <p><input type="text" placeholder="输入好记的账号" class="input-a" id="Accounts" autocomplete="off"></p>
                <p><input type="password" placeholder="输入密码随意" class="input-a" id="Passwords" autocomplete="off"></p>
                <p><input type="text" placeholder="输入您的手机号" class="input-a" id="Phone" autocomplete="off"></p>
                <button type="reset" hidden id="Rest"></button>
            </form>
        </div>
        <div class="div-a"><button type="button" class="logon" onClick="register()">注&nbsp;&nbsp;册</button></div>
        <div style="margin-top:10px;color:aqua;font-size:12px;margin-left:5px;"><a href="#" id="Login">又想起有账号?🔪</a></div>
    </div>
    <div class="bt">
        <span>广信科技-穷比快乐屋运营商系统</span>
    </div>
</div>
<script src="${ctx}/static/js/jquery.min.js" charset="utf-8"></script>
<script src="${ctx}/static/lib/layui/layui.js" charset="utf-8"></script>
<script>
    var Register = document.getElementById("Register");
    var Login = document.getElementById("Login");
    var rs = document.getElementById("rs");
    var lg = document.getElementById("lg");
    var rests = document.getElementById("rests");
    var Rest = document.getElementById("Rest");
    window.onload = function () {
        Register.onclick = function () {
            rests.click();
            rs.style.display = "block";
            lg.style.display = "none";
        }
        Login.onclick = function () {
            Rest.click();
            rs.style.display = "none";
            lg.style.display = "block";
        }
    };
    function loging(){
        var userName = $("#account").val();
        var password = $("#password").val();
        var code = $("#code").val();
        if(userName==null||userName==undefined||userName==""||userName==""){
            layer.alert("账号不能为空");
            return;
        }
        if(password==null||password==undefined||password==""){
            layer.alert("请输入密码");
            return;
        }
        if(code==null||code==undefined||code==""){
            layer.alert("请输入验证码");
            return;
        }
        $.post("${ctx}/login?mothed=login",{
            userName:userName,
            password:password,
            code:code
        },function (jsonMsg){
            if(jsonMsg.state){
                window.location.replace('${ctx}/home');
            }else {
                layer.alert(jsonMsg.msg);
            }
        });
    }
    function register(){
        var userPassword = /^[a-zA-Z]\w{5,17}$/;
        var userName = /^[a-zA-Z][a-zA-Z0-9]{4,15}$/;
        var phone = /^(13[0-9]|14[5|7]|15[0|1|2|3|5|6|7|8|9]|18[0|1|2|3|5|6|7|8|9])\d{8}$/;
        var Accounts = $("#Accounts").val();
        var Passwords = $("#Passwords").val();
        var Phone = $("#Phone").val();
        if (!userName.test(Accounts)){
            layer.alert("账号必须以字母开头并且和数字组合")
            return
        }
        if (!userPassword.test(Passwords)){
            layer.alert("必须以字母开头并且和数字组合,不能有特殊符号")
            return
        }
        if (!phone.test(Phone)){
            layer.alert("手机号不存在")
            return
        }
        $.post("${ctx}/login?mothed=register",{
            Accounts:Accounts,
            Passwords:Passwords,
            Phone:Phone
        },function (jsonMsg){
            if(jsonMsg.state){
                layer.alert(jsonMsg.msg)
                rs.style.display = "none";
                lg.style.display = "block";
                Rest.click();
                $("#account").val(jsonMsg.data.userName);
            }else {
                layer.alert(jsonMsg.msg)
            }
        });
    }
    function change(o){
        o.setAttribute("src","${ctx}/login?mothed=imgCode&t="+new Date().getTime());
    }
</script>
</body>
</html>
