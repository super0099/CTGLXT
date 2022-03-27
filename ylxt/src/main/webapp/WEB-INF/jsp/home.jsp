<%--
  Created by IntelliJ IDEA.
  User: super007
  Date: 2021/9/3
  Time: 18:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" scope="page"/>
<html>
<head>
    <title>主页</title>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport"
          content="width=device-width,user-scalable=yes, minimum-scale=0.4, initial-scale=0.8"/>
    <meta http-equiv="Cache-Control" content="no-siteapp"/>
    <link rel="stylesheet" href="${ctx}/static/lib/layui/css/layui.css">
    <link rel="stylesheet" href="${ctx}/static/css/home.css">
    <!--[if lt IE 9]>
    <script src="https://cdn.staticfile.org/html5shiv/r29/html5.min.js"></script>
    <script src="https://cdn.staticfile.org/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
    
</head>
<body>
<div class="smallWindow" id="smallWindow">
    <ul class="layui-nav" lay-filter="">
        <c:forEach items="${requestScope.menuList}" var="menuLvl1">
            <li class="layui-nav-item">
                <a href="javascript:;">
                    <i class="layui-icon ${menuLvl1.menuIcon}" lay-tips="${menuLvl1.menuName}"></i>
                    <cite>${menuLvl1.menuName}</cite>
                    <dl class="layui-nav-child">
                        <c:forEach items="${menuLvl1.treeList}" var="menuLvl2">
                            <dd>
                                <a onclick="openWin('${ctx.concat(menuLvl2.menuUrl)}')">
                                    <i class="layui-icon ${menuLvl2.menuIcon}" lay-tips="${menuLvl2.menuName}"></i>
                                    <cite>${menuLvl2.menuName}</cite>
                                </a>
                            </dd>
                        </c:forEach>
                    </dl>
                </a>
            </li>
        </c:forEach>
        <li class="layui-nav-item" style="float: right;">
            <a href="javascript:;">
                ${requestScope.loginUser.userName}
            </a>
            <dl class="layui-nav-child">
                <dd><a href="javascript:;" onclick="loginOut()">退了</a></dd>
            </dl>
        </li>
    </ul>
    <iframe src='${ctx}/home/welcome' frameborder="0" scrolling="yes" class="x-iframe" id="iframe"></iframe>
</div>
<script src="${ctx}/static/lib/layui/layui.js" charset="UTF-8"></script>
<script src="${ctx}/static/js/jquery.min.js" charset="UTF-8"></script>
<script>
    var ws = null;
    if(WebSocket){
        ws = new WebSocket("wss://192.168.191.1:8443/ylxt/home");
    }else{
        alert("浏览器不支持Websocket");
    }

    ws.onmessage=function(event){
        var obj = JSON.parse(event.data);
        if(obj.stateCode==1){
            alert(obj.msg)
            loginOut();
        }
    }
    window.onload=function (){
        $.post("${ctx}/home/createIndent",{},function (jsonData){
        })
        var offsetHeight = window.innerHeight;
        document.getElementById("smallWindow").style.height=offsetHeight+"px";
        document.getElementById("iframe").style.height=offsetHeight-61+"px";
        layui.use('element', function(){
            var element = layui.element;
            window.onresize = function(){
                var offsetHeight = window.innerHeight;
                document.getElementById("smallWindow").style.height=offsetHeight+"px";
                document.getElementById("iframe").style.height=offsetHeight-61+"px";
            }
        });
    }
    function loginOut(){
        $.post('${ctx}/login/loginOut',function (){
            window.location.reload();
        })
    }

    function openWin(o){
        document.getElementById("iframe").setAttribute("src",o);
    }
</script>
</body>
</html>
