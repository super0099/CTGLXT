<%@ page import="com.yxm.po.dbUser" %><%--
  Created by IntelliJ IDEA.
  User: super007
  Date: 2021/5/12
  Time: 14:49
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><!--需要引入jstl-1.2.jar包-->
<c:set var="ctx" value="${pageContext.request.contextPath}" scope="page"/><!--需要引入jstl-1.2.jar包-->
<html>
<head>
    <title>个人中心</title>
  <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
  <meta name="viewport"
        content="width=device-width,user-scalable=yes, minimum-scale=0.4, initial-scale=0.8"/>
  <meta http-equiv="Cache-Control" content="no-siteapp"/>
  <!--[if lt IE 9]>
  <script src="https://cdn.staticfile.org/html5shiv/r29/html5.min.js"></script>
  <script src="https://cdn.staticfile.org/respond.js/1.4.2/respond.min.js"></script>
  <![endif]-->
  <link rel="stylesheet" href="${ctx}/static/css/personal.css">
</head>
<body>
<div class="content" id="content">
  <%--头部功能区--%>
  <div class="top clearfix">
    <div class="topLeft fl">
      <img src="" id="myUserImg">
    </div>
    <div class="topAuto fl">
      <span id="myNickname"></span>
    </div>
    <div class="topright fr">
      <a href="${ctx}/takeout">首页</a>
      <a href="${ctx}/personal">个人中心</a>
      <a href="${ctx}/shoppingCar">购物车</a>
    </div>
  </div>
  <%--logo区--%>
  <div class="logo clearfix">
    <div class="logoImg fl"></div>
    <div class="logoName fl">
      <p>穷比快乐屋</p>
      <span>The Poor Happy Shop</span>
    </div>
  </div>
  <%--导航区--%>
  <div class="navigation"></div>
  <%--显示区--%>
  <div class="view clearfix" id="view">
    <%--功能区--%>
    <div class="flhead fl">
      <div class="flhead-tp"><b>个人中心</b></div>
      <div class="flhead-hd" id="head-hd">
        <div class="headlineImg">
          <i class="orderImg"></i>
          <i class="propertyImg"></i>
          <i class="myImg"></i>
          <i class="menuImg"></i>
          <i class="homeImg"></i>
        </div>
        <ul>
          <li class="lix">
            <b class="green">我的订单</b>
            <ul class="ulsh" id="sp">
              <li value="0" id="order"><a class="blue">近三个月订单</a></li>
            </ul>
          </li>
          <li class="lix">
            <b class="green">我的资产</b>
            <ul class="ulsh">
              <li value="0" id="preferential"><a  class="blue">我的优惠卷</a></li>
              <li value="0" id="balance"><a  class="blue">我的余额</a></li>
            </ul>
          </li>
          <li class="lix">
            <b class="green">我的资料</b>
            <ul class="ulsh">
              <li id="myData"><a  class="blue">个人资料</a></li>
              <li id="site"><a  class="blue">地址管理</a></li>
            </ul>
          </li>
          <li class="lix" value="0">
            <b class="green">我的收藏</b>
            <ul class="ulsh">
              <li id="menu"><a  class="blue">菜品收藏</a></li>
            </ul>
          </li>
        </ul>
      </div>
    </div>
    <%--功能显示区--%>
    <div class="show fl">
      <iframe src="${ctx}/homePage" class="win" id="newWin"></iframe>
    </div>
  </div>
</div>
<script src="${ctx}/static/js/jquery.min.js" charset="utf-8"></script>
<script src="${ctx}/static/lib/layui/layui.js" charset="utf-8"></script>
<script>
  <%dbUser LoginUser = (dbUser)request.getAttribute("dbuser"); %>
  $(function (){
    var userId = <%=LoginUser.getId()%>
            $.post("${ctx}/modification?mothed=selectUser",{userId:userId},function (jsonMsg){
              console.log(jsonMsg)
              if(jsonMsg.state){
                var data = jsonMsg.data;
                $("#myUserImg").prop("src","${ctx}/modification?mothed=getPortraitImage&imgName="+data.portrait);
                $("#myNickname").text(data.nickname);
              }else {
                layer.msg(jsonMsg.msg,{icon:5})
              }
            });
  });
  $("#order").click(function (){
      $("#newWin").prop('src','${ctx}/homePage?mothed=order');
  });
  $("#preferential").click(function (){
    $("#newWin").prop('src','${ctx}/homePage?mothed=preferential');
  });
  $("#balance").click(function (){
    $("#newWin").prop('src','${ctx}/homePage?mothed=balance');
  });
  $("#myData").click(function (){
    $("#newWin").prop('src','${ctx}/homePage?mothed=myData');
  });
  $("#site").click(function (){
    $("#newWin").prop('src','${ctx}/homePage?mothed=site');
  });
  $("#menu").click(function (){
    $("#newWin").prop('src','${ctx}/homePage?mothed=menu');
  });

</script>
</body>
</html>
