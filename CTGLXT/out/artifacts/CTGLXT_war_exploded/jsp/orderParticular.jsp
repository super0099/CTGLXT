<%@ page import="com.yxm.po.dbWorderForm" %>
<%@ page import="com.yxm.po.dbUser" %><%--
  Created by IntelliJ IDEA.
  User: super007
  Date: 2021/6/16
  Time: 19:05
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><!--需要引入jstl-1.2.jar包-->
<c:set var="ctx" value="${pageContext.request.contextPath}" scope="page"/><!--需要引入jstl-1.2.jar包-->
<%dbWorderForm worderForm = (dbWorderForm) request.getAttribute("order");%>
<%dbUser user = (dbUser) request.getAttribute("dbUser");%>
<html>
<head>
    <title>订单详细</title>
    <link rel="stylesheet" href="${ctx}/static/css/orderParticular.css">
</head>
<body>
<div class="order">
    <div class="orderState">
        <div class="headline">
            &nbsp;&nbsp;<span>订单状态</span>
        </div>
        <ul>
            <li class="stateStyle">待支付</li>
            <li class="stateStyle">待发货</li>
            <li class="stateStyle">待配送</li>
            <li class="stateStyle">待收货</li>
            <li class="stateStyle">已完成</li>
            <li class="stateStyle">已取消</li>
        </ul>
    </div>
    <div class="myData">
        <div class="headline">
            &nbsp;&nbsp;<span>我的信息</span>
        </div>
        <div class="myContent clearfix">
            <img src="${ctx}/modification?mothed=getPortraitImage&imgName=<%=user.getPortrait()%>" class="fl">
            <div class="message fl">
                <span><%=worderForm.getLinkman()%></span><a><%=worderForm.getUserPhone()%></a>
                <p><%=worderForm.getSite()%></p>
            </div>
        </div>
    </div>
    <div class="commodityData">
        <div class="headline">
            &nbsp;&nbsp;<span>订单菜品</span>
        </div>
        <div class="menu" id="menu">

        </div>
    </div>
    <div class="orderData">
        <div class="headline">
            &nbsp;&nbsp;<span>订单信息</span>
        </div>
        <div class="orderMessage">
            <div>订单编号: <span><%=worderForm.getOrderNumber()%></span></div>
            <div>实付金额: <span><%=worderForm.getPrice()%></span></div>
            <div>打包费用: <span><%=worderForm.getTip()%></span></div>
            <div>订单日期: <span id="dateTime"></span></div>
        </div>
        <div class="payment" id="payment" style="display: none;">
            <button type="button" onclick="payment()">付款</button>
        </div>
    </div>
</div>
<script src="${ctx}/static/js/jquery.min.js" charset="utf-8"></script>
<script src="${ctx}/static/lib/layui/layui.js" charset="utf-8"></script>
<script>
    var orderState=<%=worderForm.getOrderStatus()%>;
    $(function (){
        var dateTime = document.getElementById("dateTime");
        dateTime.innerText=Dt('<%=worderForm.getOrderDate()%>');
        var stateStyle = document.getElementsByClassName("stateStyle");
        for(var i=0;i<stateStyle.length;i++){
            if(orderState==1){
                var payment = document.getElementById("payment");
                payment.style.display="block";
                stateStyle[0].style.background="green";
            }
            if(orderState==2){
                stateStyle[1].style.background="green";
            }
        }

        $.post("${ctx}/order?mothed=menuList",{
            orderId:<%=worderForm.getId()%>
        },function (JsonMsg){
            var menu = document.getElementById("menu");
            for(var i=0;i<JsonMsg.length;i++){
                menu.innerHTML+='<div class="food fl"><p>'+JsonMsg[i].menuName+'</p><img src="${ctx}/modification?mothed=getPortraitImage&imgName='+JsonMsg[i].picture+'"><span class="fl"><a>'+JsonMsg[i].quantity+'</a>份</span><span class="fr">'+JsonMsg[i].price+'<a>￥</a></span></div>';
            }
        })
    })
    function Dt(o){
        var dt = new Date(o);
        return dt.getFullYear() + "-" + (dt.getMonth() + 1) + "-" + dt.getDate() + " " +
            dt.getHours() + ":" + dt.getMinutes() + ":" + dt.getSeconds();
    }
    function payment(){
        $.post("${ctx}/order?mothed=orderPayment",{
            orderId:<%=worderForm.getId()%>
        },function (jsonMsg){
            if(jsonMsg.state){
                layer.msg(jsonMsg.msg);
                window.location.reload();
            }else {
                layer.msg(jsonMsg.msg);
            }
        })
    }
</script>
</body>
</html>
