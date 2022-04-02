<%@ page import="com.yxm.po.dbUser" %>
<%@ page import="java.util.List" %>
<%@ page import="com.yxm.po.dbMenu" %><%--
  Created by IntelliJ IDEA.
  User: super007
  Date: 2021/5/23
  Time: 8:20
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><!--需要引入jstl-1.2.jar包-->
<c:set var="ctx" value="${pageContext.request.contextPath}" scope="page"/><!--需要引入jstl-1.2.jar包-->
<html>
<head>
    <title>订单结算</title>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport"
          content="width=device-width,user-scalable=yes, minimum-scale=0.4, initial-scale=0.8"/>
    <meta http-equiv="Cache-Control" content="no-siteapp"/>
    <!--[if lt IE 9]>
    <script src="https://cdn.staticfile.org/html5shiv/r29/html5.min.js"></script>
    <script src="https://cdn.staticfile.org/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
    <link rel="stylesheet" href="${ctx}/static/css/orderAffirm.css">
</head>
<body>
<div class="content clearfix" id="content">
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
    <div class="navigation">
        <a>结算</a>
        <a href="${ctx}/homePage?mothed=site" class="fr">使用新地址</a>
    </div>
    <div class="orderContent">
        <div class="site clearfix" id="site">

        </div>
        <div class="carContent" id="carContent">

        </div>
    </div>
    <div class="Settlement">
        <button onclick="submit()">结算</button>
    </div>
</div>
<script src="${ctx}/static/js/jquery.min.js" charset="utf-8"></script>
<script src="${ctx}/static/lib/layui/layui.js" charset="utf-8"></script>
<script>
    <%dbUser LoginUser = (dbUser)request.getAttribute("dbUser"); %>
    <%String carId = (String) request.getAttribute("carId");%>
    <%String menuId = (String) request.getAttribute("menuId");%>

    $(function (){
        var userId = <%=LoginUser.getId()%>;
        //个人信息
        $.post("${ctx}/modification?mothed=selectUser",{userId:userId},function (jsonMsg){
            if(jsonMsg.state){
                var data = jsonMsg.data;
                $("#myUserImg").prop("src","${ctx}/modification?mothed=getPortraitImage&imgName="+data.portrait);
                $("#myNickname").text(data.nickname);
            }else {
                layer.msg(jsonMsg.msg,{icon:5})
            }
        });
        //地址
        $.post("${ctx}/site?mothed=selectSite",{userId:userId},function (data){
            var site = document.getElementById("site");
            console.log(data);
            for (var i = 0;i<data.length;i++){
                if(i==0){
                    site.innerHTML+='<div class="site-content fl"><div class="site-content-top clearfix"><span class="fl" style="margin-left: 15px;"><b>'+data[i].linkman+'</b></span></div><div class="site-content-con"><p>'+data[i].site+'</p><span>'+data[i].phone+'</span></div><div class="select" id="select"><input type="radio" name="site" checked value="'+data[i].id+'"></div></div>';
                }else {
                    site.innerHTML+='<div class="site-content fl"><div class="site-content-top clearfix"><span class="fl" style="margin-left: 15px;"><b>'+data[i].linkman+'</b></span></div><div class="site-content-con"><p>'+data[i].site+'</p><span>'+data[i].phone+'</span></div><div class="select" id="select"><input type="radio" name="site" value="'+data[i].id+'"></div></div>';
                }
            }
        });
        var carId = <%=carId%>;
        if(carId!=null){
            var carContent = document.getElementById("carContent");
            $.post('${ctx}/order?mothed=selectCarMenu',{
                carId:'<%=carId%>'
            },function (data){
                for (var i = 0;i<data.length;i++){
                    carContent.innerHTML+='<div class="carMenu"><ul><li><img src="${ctx}/modification?mothed=getPortraitImage&imgName='+data[i].picture+'" class="MenuImg"></li><li><span>'+data[i].menuName+'</span></li><li style="color: red;font-size: 20px;" id="price'+i+'">'+data[i].price+'</li><li><input type="text" class="quantity" readonly value="'+data[i].amount+'" id="quantity'+i+'"></li></ul></div>';
                }
            })
        }
        var menuId = <%=menuId%>;
        if(menuId!=null){
            var carContent = document.getElementById("carContent");
            $.post('${ctx}/order?mothed=selectMenu',{
                menuId:'<%=menuId%>'
            },function (data){
                carContent.innerHTML+='<div class="carMenu"><ul><li><img src="${ctx}/modification?mothed=getPortraitImage&imgName='+data.picture+'" class="MenuImg"></li><li><span>'+data.menuName+'</span></li><li style="color: red;font-size: 20px;" id="price">'+data.price+'</li><li><input type="text" class="quantity" readonly value="1" id="quantity"></li></ul></div>';
            })
        }
    });
    function submit(){
        var carId = <%=carId%>;
        var menuId = <%=menuId%>;
        var checked = document.getElementsByName("site");
        var siteId = null;
        for(var i = 0;i<checked.length;i++){
            if(checked[i].checked){
                siteId = checked[i].value;
            }
        }
        if(carId!=null){
            $.post("${ctx}/order?mothed=addWorderForm",{
                carId:carId,
                siteId:siteId
            },function (jsonMsg){
                if(jsonMsg.state){
                    var obj = jsonMsg.data;
                    window.location.replace("${ctx}/order?mothed=firstHtml&orderId="+obj.id);
                }else {
                    layer.msg(jsonMsg.msg,{icon: 5});
                }
            })
        }
        if(menuId!=null){
            $.post("${ctx}/order?mothed=addWorderForm",{
                menuId:menuId,
                siteId:siteId
            },function (jsonMsg){
                if(jsonMsg.state){
                    var obj = jsonMsg.data;
                    window.location.replace("${ctx}/order?mothed=firstHtml&orderId="+obj.id);
                }else {
                    layer.msg(jsonMsg.msg,{icon: 5});
                }
            })
        }

    }
</script>
</body>
</html>
