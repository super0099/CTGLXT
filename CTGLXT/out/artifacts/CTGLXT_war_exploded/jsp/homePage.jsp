<%@ page import="com.yxm.po.dbUser" %><%--
  Created by IntelliJ IDEA.
  User: super007
  Date: 2021/5/12
  Time: 17:06
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><!--需要引入jstl-1.2.jar包-->
<c:set var="ctx" value="${pageContext.request.contextPath}" scope="page"/><!--需要引入jstl-1.2.jar包-->
<html>
<head>
    <title>个人资料</title>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport"
          content="width=device-width,user-scalable=yes, minimum-scale=0.4, initial-scale=0.8"/>
    <meta http-equiv="Cache-Control" content="no-siteapp"/>
    <!--[if lt IE 9]>
    <script src="https://cdn.staticfile.org/html5shiv/r29/html5.min.js"></script>
    <script src="https://cdn.staticfile.org/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
    <link rel="stylesheet" href="${ctx}/static/css/homePage.css">
</head>
<body>
<div class="user">
    <%--用户资料--%>
    <div class="userData clearfix">
        <div class="userImg fl">
            <img src="" id="myUserImg">
            <div class="userData-s fl">
                <span id="myNickname"></span><br><span id="myUserName"></span>
            </div>
        </div>
        <i></i>
        <div class="userDataz fl">
            <span>优惠卷</span><br>
            <a style="color: red" id="discounts">0</a>&nbsp;&nbsp;张
        </div>
        <i></i>
        <div class="userDataz fl">
            <span>余额</span><br>
            <a style="color: #FFB800;" id="balance"></a>&nbsp;&nbsp;￥
        </div>
        <i></i>
        <div class="userDatazs fl">

        </div>
    </div>
    <%--用户订单--%>
    <div class="userOrder">
        <div class="userOrder-top clearfix">
            <p class="fl">最近订单</p>
            <a class="fr" href="${ctx}/homePage?mothed=order">查看全部订单>></a>
        </div>
        <div class="userOrder-body" id="order">
<%--            <div class="orders clearfix">--%>
<%--                <ul>--%>
<%--                    <li style="width: 20%;line-height: 80px;">--%>
<%--                        <p>14小时12分前</p>--%>
<%--                    </li>--%>
<%--                    <li style="width: 40%;text-align: left;">--%>
<%--                        <img src="${ctx}/static/img/龙.png">--%>
<%--                        <div class="order-s">--%>
<%--                            <div style="overflow: hidden;" class="menuCont">--%>
<%--                                <span>无骨鸭爪2份 /</span>--%>
<%--                                <span>菊花炒鹅肝2份 /</span>--%>
<%--                            </div>--%>
<%--                            <p style="margin-top: 10px">订单编号: <a>asda4564654654654654</a></p>--%>
<%--                        </div>--%>
<%--                    </li>--%>
<%--                    <li style="width: 20%;line-height: 80px;">--%>
<%--                        <a>48</a>--%>
<%--                    </li>--%>
<%--                    <li style="width: 20%;line-height: 80px;font-size: 13px">--%>
<%--                        <span>订单已经取消</span>--%>
<%--                    </li>--%>
<%--                </ul>--%>
<%--            </div>--%>
        </div>
    </div>
    <%--用户收藏--%>
    <div class="userCollect">
        <div class="userOrder-top">
            <p>我的收藏</p>
        </div>
        <div class="collect clearfix" id="collect">
<%--            <div class="collectCont" onclick="openMenu()">--%>
<%--                <input type="text"hidden id="collects" value="">--%>
<%--                <img src="${ctx}/static/img/龙.png"><br>--%>
<%--                <span>五谷杂粮</span>--%>
<%--            </div>--%>
        </div>
    </div>
</div>
<script src="${ctx}/static/js/jquery.min.js" charset="utf-8"></script>
<script src="${ctx}/static/lib/layui/layui.js" charset="utf-8"></script>
<script>
    <%dbUser LoginUser = (dbUser)request.getAttribute("dbuser"); %>
    $(function (){
        var userId = <%=LoginUser.getId()%>
        $.post("${ctx}/homePage?mothed=selectHomePageData",{userId:userId},function (jsonMsg){
            $("#myUserImg").prop("src","${ctx}/modification?mothed=getPortraitImage&imgName="+jsonMsg.portrait);
            $("#myNickname").text(jsonMsg.nickname);
            if(jsonMsg.balance==null){
                $("#balance").text(0);
            }else {
                $("#balance").text(jsonMsg.balance);
            }
            $("#myUserName").text(jsonMsg.userName);

        });
        $.post("${ctx}/homePage?mothed=selectDiscount",{userId:userId},function (jsonMsg){
            $("#discounts").text(jsonMsg);
        });
        $.post("${ctx}/homePage?mothed=selectOrder",{userId:userId},function (jsonMsg){
            var orderVice = document.getElementById("order");
            var order = jsonMsg.order;
            var menu = jsonMsg.menu;
            var str = "";
            for (var i = 0;i<order.length;i++){

                var date = Dt(order[i].orderDate);
                for (var j = 0;j<menu.length;j++){
                    if(order[i].id==menu[j].orderFormId){
                        str+=menu[j].menuName+' '+menu[j].quantity+" 份 /";
                    }
                }
                if(order[i].orderStatus==1){
                    orderVice.innerHTML+='<div class="orders clearfix"><ul><li style="width: 20%;line-height: 80px;"><p>'+date+'</p></li><li style="width: 40%;text-align: left;"><img src="${ctx}/static/img/龙.png"><div class="order-s"><div class="menuCont"><span >'+str+'</span></div><p style="margin-top: 10px">订单编号: <a>'+order[i].orderNumber+'</a></p></div></li><li style="width: 20%;line-height: 80px;"><a>'+order[i].price+'</a></li><li style="width: 20%;line-height: 80px;font-size: 13px"><span>待支付</span></li></ul></div>';
                }
                if(order[i].orderStatus==2){
                    orderVice.innerHTML+='<div class="orders clearfix"><ul><li style="width: 20%;line-height: 80px;"><p>'+date+'</p></li><li style="width: 40%;text-align: left;"><img src="${ctx}/static/img/龙.png"><div class="order-s"><div class="menuCont"><span>'+str+'</span></div><p style="margin-top: 10px">订单编号: <a>'+order[i].orderNumber+'</a></p></div></li><li style="width: 20%;line-height: 80px;"><a>'+order[i].price+'</a></li><li style="width: 20%;line-height: 80px;font-size: 13px"><span>待发货</span></li></ul></div>';
                }
                if(order[i].orderStatus==3){
                    orderVice.innerHTML+='<div class="orders clearfix"><ul><li style="width: 20%;line-height: 80px;"><p>'+date+'</p></li><li style="width: 40%;text-align: left;"><img src="${ctx}/static/img/龙.png"><div class="order-s"><div class="menuCont"><span>'+str+'</span></div><p style="margin-top: 10px">订单编号: <a>'+order[i].orderNumber+'</a></p></div></li><li style="width: 20%;line-height: 80px;"><a>'+order[i].price+'</a></li><li style="width: 20%;line-height: 80px;font-size: 13px"><span>待配送</span></li></ul></div>';
                }
                if(order[i].orderStatus==4){
                    orderVice.innerHTML+='<div class="orders clearfix"><ul><li style="width: 20%;line-height: 80px;"><p>'+date+'</p></li><li style="width: 40%;text-align: left;"><img src="${ctx}/static/img/龙.png"><div class="order-s"><div class="menuCont"><span>'+str+'</span></div><p style="margin-top: 10px">订单编号: <a>'+order[i].orderNumber+'</a></p></div></li><li style="width: 20%;line-height: 80px;"><a>'+order[i].price+'</a></li><li style="width: 20%;line-height: 80px;font-size: 13px"><span>待收货</span></li></ul></div>';
                }
                if(order[i].orderStatus==5){
                    orderVice.innerHTML+='<div class="orders clearfix"><ul><li style="width: 20%;line-height: 80px;"><p>'+date+'</p></li><li style="width: 40%;text-align: left;"><img src="${ctx}/static/img/龙.png"><div class="order-s"><div class="menuCont"><span>'+str+'</span></div><p style="margin-top: 10px">订单编号: <a>'+order[i].orderNumber+'</a></p></div></li><li style="width: 20%;line-height: 80px;"><a>'+order[i].price+'</a></li><li style="width: 20%;line-height: 80px;font-size: 13px"><span>已完成</span></li></ul></div>';
                }
                if(order[i].orderStatus==6){
                    orderVice.innerHTML+='<div class="orders clearfix"><ul><li style="width: 20%;line-height: 80px;"><p>'+date+'</p></li><li style="width: 40%;text-align: left;"><img src="${ctx}/static/img/龙.png"><div class="order-s"><div class="menuCont"><span>'+str+'</span></div><p style="margin-top: 10px">订单编号: <a>'+order[i].orderNumber+'</a></p></div></li><li style="width: 20%;line-height: 80px;"><a>'+order[i].price+'</a></li><li style="width: 20%;line-height: 80px;font-size: 13px"><span>已取消</span></li></ul></div>';
                }
                str="";
            }
        })
        $.post("${ctx}/homePage?mothed=selectCollect",{userId:userId},function (jsonMsg){
            var collect = document.getElementById("collect");
            for(var i = 0;i<jsonMsg.length;i++){
                collect.innerHTML+='<div class="collectCont fl" onclick="openMenu('+jsonMsg[i].menuId+')"><input type="text"hidden id="collects" value=""><img src="${ctx}/modification?mothed=getPortraitImage&imgName='+jsonMsg[i].picture+'"><br><span>'+jsonMsg[i].menuName+'</span></div>';
            }
        });
    });
    function openMenu(o){
        window.parent.location.replace("${ctx}/particular?mothed=index&t="+o);
    }
    function Dt(o){
        var dt = new Date(o);
        return dt.getFullYear() + "-" + (dt.getMonth() + 1) + "-" + dt.getDate() + " " +
            dt.getHours() + ":" + dt.getMinutes() + ":" + dt.getSeconds();
    }
</script>
</body>
</html>
