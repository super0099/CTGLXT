<%--
  Created by IntelliJ IDEA.
  User: super007
  Date: 2021/8/9
  Time: 15:07
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" scope="page"/>
<html>
<head>
    <title>登录页面</title>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport"
          content="width=device-width,user-scalable=yes, minimum-scale=0.4, initial-scale=0.8"/>
    <meta http-equiv="Cache-Control" content="no-siteapp"/>
    <link rel="stylesheet" href="${ctx}/static/css/login.css">
    <!--[if lt IE 9]>
    <script src="https://cdn.staticfile.org/html5shiv/r29/html5.min.js"></script>
    <script src="https://cdn.staticfile.org/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
    <script>
        if (window.top.location.href !== window.location.href) {
            window.top.location.href = window.location.href;
        }
    </script>
</head>
<body>
<h1></h1>
<div class="content">
    <div class="userName">
        <img src="${ctx}/static/img/me_show.png">
        <input type="text" autocomplete="off" placeholder="请输入用户名" id="userName" onblur="onblurs()">
    </div>
    <div class="userType">
        <input type="text" autocomplete="off" placeholder="用户类别" readonly id="userType">
    </div>
    <div class="password">
        <img src="${ctx}/static/img/密码.png">
        <input type="password" placeholder="请输入密码" id="password">
    </div>
    <div class="authCode">
        <p class="authCodeImg">
            <img src="${ctx}/static/img/验证码.png">
            <input type="text" autocomplete="off" placeholder="请输入验证码" id="authCode">
        </p>
        <img src="${ctx}/login/imgCode" onclick="changeAuthCode(this)" id="imgCode">
    </div>
    <div class="login">
        <button type="button" onclick="login()">登 录</button>
    </div>
</div>
<script src="${ctx}/static/js/jquery.min.js" charset="utf-8"></script>
<script src="${ctx}/static/lib/layui/layui.js" charset="UTF-8"></script>
<script>
    function login(){
        var userName = $("#userName").val();
        var userPassword = $("#password").val();
        var authCode = $("#authCode").val();
        var imgCode = document.getElementById("imgCode");
        if(userName==null||userName==""||userName==undefined){
            layer.msg("请输入用户名");
            return;
        }
        if(userPassword==null||userPassword==""||userPassword==undefined){
            layer.msg("请输入密码");
            return;
        }
        if(authCode==null||authCode==""||authCode==undefined){
            layer.msg("请输入验证码");
            return;
        }
        $.post("${ctx}/login/login",{
            userName:userName,
            password:userPassword,
            authCode:authCode
        },function (jsonData){
            if(jsonData.state){
                window.location.replace("${ctx}/home/index");
            }else {
                layer.msg(jsonData.msg)
                changeAuthCode(imgCode);
            }
        })
    }
    function onblurs(){
        var userName = $("#userName").val();
        $.post("${ctx}/login/onblur",{
            userName:userName,
        },function (jsonData){
            if(jsonData.state){
                $("#userType").val(jsonData.msg)
            }
        })
    }
    function changeAuthCode(o){
        o.setAttribute("src","${ctx}/login/imgCode?t="+new Date().getTime());
    }
</script>
</body>
</html>
