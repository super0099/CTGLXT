<%@ page import="com.yxm.po.dbUser" %><%--
  Created by IntelliJ IDEA.
  User: super007
  Date: 2021/5/12
  Time: 20:25
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><!--需要引入jstl-1.2.jar包-->
<c:set var="ctx" value="${pageContext.request.contextPath}" scope="page"/><!--需要引入jstl-1.2.jar包-->
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" href="${ctx}/static/css/order.css">
</head>
<body>
<div class="order">
    <div class="order-top">
        <p>近三个月订单</p>
    </div>
    <div class="order-headline clearfix">
        <ul>
            <li style="width: 15%;">下单时间</li>
            <li style="width: 40%;">订单内容</li>
            <li style="width: 15%;">支付金额(元)</li>
            <li style="width: 15%;">状态</li>
            <li style="width: 15%;">操作</li>
        </ul>
    </div>
    <div class="order-content" id="order">

    </div>
</div>
<script src="${ctx}/static/js/jquery.min.js" charset="utf-8"></script>
<script src="${ctx}/static/lib/layui/layui.js" charset="utf-8"></script>
<script>
    <%dbUser dbuser = (dbUser) request.getAttribute("dbuser");%>
    var userId = <%=dbuser.getId()%>
    $(function (){

        $.post("${ctx}/homePage?mothed=selectOrder",{userId:userId},function (jsonMsg){
            console.log(jsonMsg);
            var order = jsonMsg.order;
            var menu = jsonMsg.menu;
            var str = "";
            var orders = document.getElementById("order")
            for(var i = 0;i<order.length;i++){
                var date = Dt(order[i].orderDate);
                for(var j = 0;j<menu.length;j++){
                    if(order[i].id==menu[j].orderFormId){
                        str+=menu[j].menuName+' '+menu[j].quantity+" 份 /";
                    }
                }
                if(order[i].orderStatus==1){
                    orders.innerHTML+='<div class="orders clearfix"><ul><li style="width: 15%;line-height: 100px;"><p>'+date+'</p></li><li style="width: 40%;text-align: left;"><img src="${ctx}/static/img/龙.png"><div class="order-s"><div class="menuCont"><span>'+str+'</span></div><p style="margin-top: 10px">订单编号: <a>'+order[i].orderNumber+'</a></p></div></li><li style="width: 15%;line-height: 100px;"><a>'+order[i].price+'</a></li><li style="width: 15%;line-height: 100px;font-size: 13px"><span>待支付</span></li><li style="width: 15%;line-height: 100px;"><button type="button" onclick="openOrder('+order[i].id+')">订单详细</button><br></li></ul></div>';
                }
                if(order[i].orderStatus==2){
                    orders.innerHTML+='<div class="orders clearfix""><ul><li style="width: 15%;line-height: 100px;"><p>'+date+'</p></li><li style="width: 40%;text-align: left;"><img src="${ctx}/static/img/龙.png"><div class="order-s"><div class="menuCont"><span>'+str+'</span></div><p style="margin-top: 10px">订单编号: <a>'+order[i].orderNumber+'</a></p></div></li><li style="width: 15%;line-height: 100px;"><a>'+order[i].price+'</a></li><li style="width: 15%;line-height: 100px;font-size: 13px"><span>待发货</span></li><li style="width: 15%;line-height: 100px;"><button type="button" onclick="openOrder('+order[i].id+')">订单详细</button><br></li></ul></div>';
                }
                if(order[i].orderStatus==3){
                    orders.innerHTML+='<div class="orders clearfix"><ul><li style="width: 15%;line-height: 100px;"><p>'+date+'</p></li><li style="width: 40%;text-align: left;"><img src="${ctx}/static/img/龙.png"><div class="order-s"><div class="menuCont"><span>'+str+'</span></div><p style="margin-top: 10px">订单编号: <a>'+order[i].orderNumber+'</a></p></div></li><li style="width: 15%;line-height: 100px;"><a>'+order[i].price+'</a></li><li style="width: 15%;line-height: 100px;font-size: 13px"><span>待配送</span></li><li style="width: 15%;line-height: 100px;"><button type="button" onclick="openOrder('+order[i].id+')">订单详细</button><br></li></ul></div>';
                }
                if(order[i].orderStatus==4){
                    orders.innerHTML+='<div class="orders clearfix"><ul><li style="width: 15%;line-height: 100px;"><p>'+date+'</p></li><li style="width: 40%;text-align: left;"><img src="${ctx}/static/img/龙.png"><div class="order-s"><div class="menuCont"><span>'+str+'</span></div><p style="margin-top: 10px">订单编号: <a>'+order[i].orderNumber+'</a></p></div></li><li style="width: 15%;line-height: 100px;"><a>'+order[i].price+'</a></li><li style="width: 15%;line-height: 100px;font-size: 13px"><span>待收货</span></li><li style="width: 15%;line-height: 100px;"><button type="button" onclick="openOrder('+order[i].id+')">订单详细</button><br></li></ul></div>';
                }
                if(order[i].orderStatus==5){
                    orders.innerHTML+='<div class="orders clearfix"><ul><li style="width: 15%;line-height: 100px;"><p>'+date+'</p></li><li style="width: 40%;text-align: left;"><img src="${ctx}/static/img/龙.png"><div class="order-s"><div class="menuCont"><span>'+str+'</span></div><p style="margin-top: 10px">订单编号: <a>'+order[i].orderNumber+'</a></p></div></li><li style="width: 15%;line-height: 100px;"><a>'+order[i].price+'</a></li><li style="width: 15%;line-height: 100px;font-size: 13px"><span>已完成</span></li><li style="width: 15%;line-height: 100px;"><button type="button" onclick="openOrder('+order[i].id+')">订单详细</button><br></li></ul></div>';
                }
                if(order[i].orderStatus==6){
                    orders.innerHTML+='<div class="orders clearfix"><ul><li style="width: 15%;line-height: 100px;"><p>'+date+'</p></li><li style="width: 40%;text-align: left;"><img src="${ctx}/static/img/龙.png"><div class="order-s"><div class="menuCont"><span>'+str+'</span></div><p style="margin-top: 10px">订单编号: <a>'+order[i].orderNumber+'</a></p></div></li><li style="width: 15%;line-height: 100px;"><a>'+order[i].price+'</a></li><li style="width: 15%;line-height: 100px;font-size: 13px"><span>已取消</span></li><li style="width: 15%;line-height: 100px;"><button type="button" onclick="openOrder('+order[i].id+')">订单详细</button><br></li></ul></div>';
                }
                str="";
            }
        })
    })
    function openOrder(o){
        window.location.replace("${ctx}/order?mothed=firstHtml&orderId="+o);
    }
    function Dt(o){
        var dt = new Date(o);
        return dt.getFullYear() + "-" + (dt.getMonth() + 1) + "-" + dt.getDate() + " " +
            dt.getHours() + ":" + dt.getMinutes() + ":" + dt.getSeconds();
    }
</script>
</body>
</html>
