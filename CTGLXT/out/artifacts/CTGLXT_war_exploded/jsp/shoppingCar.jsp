<%@ page import="com.yxm.po.dbUser" %><%--
  Created by IntelliJ IDEA.
  User: super007
  Date: 2021/5/19
  Time: 15:11
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><!--需要引入jstl-1.2.jar包-->
<c:set var="ctx" value="${pageContext.request.contextPath}" scope="page"/><!--需要引入jstl-1.2.jar包-->
<html>
<head>
    <title>购物车</title>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport"
          content="width=device-width,user-scalable=yes, minimum-scale=0.4, initial-scale=0.8"/>
    <meta http-equiv="Cache-Control" content="no-siteapp"/>
    <!--[if lt IE 9]>
    <script src="https://cdn.staticfile.org/html5shiv/r29/html5.min.js"></script>
    <script src="https://cdn.staticfile.org/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
    <link rel="stylesheet" href="${ctx}/static/css/shoppingCar.css">
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
        <a>购物车</a>
    </div>
    <div class="headline">
        <ul>
            <li style="width: 25%">商品信息</li>
            <li style="width: 18.5%;">单价</li>
            <li style="width: 18.5%;">数量</li>
            <li style="width: 18.5%;">金额</li>
            <li style="width: 18.5%;">操作</li>
        </ul>
    </div>
    <div class="carContent" id="carContent">
<%--        <div class="carMenu">--%>
<%--            <ul>--%>
<%--                <li style="width: 5%;">--%>
<%--                    <input type="checkbox">--%>
<%--                </li>--%>
<%--                <li style="width: 25%">--%>
<%--                    <img src="${ctx}/static/img/背景.jpg" class="fl MenuImg"><span class="fl">爱上对方过后就哭了</span>--%>
<%--                </li>--%>
<%--                <li style="width: 17.5%;color: red;font-size: 20px;">--%>
<%--                    38--%>
<%--                </li>--%>
<%--                <li style="width: 17.5%;">--%>
<%--                    <i class="minusFl"></i><input type="text" class="quantity" readonly value="1"><i class="pushFr"></i>--%>
<%--                </li>--%>
<%--                <li style="width: 17.5%;color: red;font-size: 20px;">--%>
<%--                    76--%>
<%--                </li>--%>
<%--                <li style="width: 17.5%">--%>
<%--                    <a onclick="removeMenu()">移除商品</a>--%>
<%--                </li>--%>
<%--            </ul>--%>
<%--        </div>--%>
    </div>
    <div class="Settlement">
        <button onclick="purchase()">结算</button>
    </div>
</div>
<script src="${ctx}/static/js/jquery.min.js" charset="utf-8"></script>
<script src="${ctx}/static/lib/layui/layui.js" charset="utf-8"></script>
<script>
    <%dbUser LoginUser = (dbUser) request.getAttribute("dbUser");%>
    $(function (){
        selectUser()
        selectMune()
    });
    function selectUser(){
        var userId = <%=LoginUser.getId()%>
        $.post("${ctx}/modification?mothed=selectUser",{userId:userId},function (jsonMsg){
            if(jsonMsg.state){
                var data = jsonMsg.data;
                $("#myUserImg").prop("src","${ctx}/modification?mothed=getPortraitImage&imgName="+data.portrait);
                $("#myNickname").text(data.nickname);
            }else {
                layer.msg(jsonMsg.msg,{icon:5})
            }
        });
    }
    function selectMune(){
        var userId = <%=LoginUser.getId()%>
        var carContent = document.getElementById("carContent");
        $.post("${ctx}/shoppingCar?mothed=selectMune",{userId:userId},function (data){
            console.log(data)
            for (var i = 0;i<data.length;i++){
                carContent.innerHTML+='<div class="carMenu"><input type="text" hidden value="'+data[i].carId+'"><ul><li style="width: 25%"><img src="${ctx}/modification?mothed=getPortraitImage&imgName='+data[i].picture+'" class="fl MenuImg"><span class="fl">'+data[i].menuName+'</span></li><li style="width: 18.5%;color: red;font-size: 20px;" id="price'+i+'">'+data[i].price+'</li><li style="width: 18.5%;position: relative;"><i class="minusFl" onclick="minus('+data[i].carId+','+data[i].id+','+i+')"></i><input type="text" class="quantity" readonly value="'+data[i].amount+'" id="quantity'+i+'"><i class="pushFr" onclick="push('+data[i].carId+','+data[i].id+','+i+')"></i></li><li style="width: 18.5%;color: red;font-size: 20px;" id="countPrice'+i+'">'+data[i].amount*data[i].price+'</li><li style="width: 18.5%"><a onclick="removeMenu('+data[i].carId+','+data[i].id+',this)" style="color: #000000;cursor: pointer;">移除商品</a></li></ul></div>';
            }
        });
    }
    //购物车减少按钮
    function minus(carId,Id,i){
        var quantity = document.getElementById("quantity"+i);
        var price = document.getElementById("price"+i);
        var total = document.getElementById("countPrice"+i);
        var q = parseInt(quantity.value);
        var p = parseInt(price.innerText);
        var t = parseInt(total.innerText);
        console.log(q,p,t)
        if((quantity.value)<2){
            layer.alert("不能为零")
            return
        }
        $.post('${ctx}/takeout?mothed=minus',{
            carId:carId,
            menuId:Id
        },function (jsonMsg){
            if(jsonMsg.state){
                var quantitys = quantity.value--;
                total.innerText=t-p;
            }else {
                layer.msg(jsonMsg.msg,{icon:5});
            }
        })
    }
    //购物车添加按钮
    function push(carId,Id,i){
        var quantity = document.getElementById("quantity"+i);
        var price = document.getElementById("price"+i);
        var total = document.getElementById("countPrice"+i);
        var q = parseInt(quantity.value);
        var p = parseInt(price.innerText);
        var t = parseInt(total.innerText);
        if((quantity.value)>9){
            layer.alert("不能大于10")
            return
        }
        $.post('${ctx}/takeout?mothed=push',{
            carId:carId,
            menuId:Id
        },function (jsonMsg){
            if(jsonMsg.state){
                var quantitys = quantity.value++;
                total.innerText=t+p;
            }else {
                layer.msg(jsonMsg.msg,{icon:5});
            }
        })
    }
    function removeMenu(carId,menuId,o){
        layer.confirm('是否删除该菜品?', {icon: 3, title:'提示'}, function(index){
            layer.close(index);
            //发送请求
            var layerIndex=layer.load();
            $.post('${ctx}/takeout?mothed=deletes',{
                carId:carId,
                menuId:menuId
            },function (jsonMsg){
                layer.close(layerIndex);
                if (jsonMsg.state){
                    document.getElementById("carContent").removeChild(o.parentElement.parentElement.parentElement);
                }else{
                    layer.msg(jsonMsg.msg,{icon:5});
                }
            })
        });
    }
    function purchase(){
        var carContent = document.getElementById("carContent");
        var carId = carContent.firstElementChild.firstElementChild.value;
        window.location.replace("${ctx}/order?mothed=index&carId="+carId);
    }
</script>
</body>
</html>
