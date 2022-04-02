<%@ page import="com.yxm.po.dbUser" %><%--
  Created by IntelliJ IDEA.
  User: super007
  Date: 2021/5/10
  Time: 19:28
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><!--需要引入jstl-1.2.jar包-->
<c:set var="ctx" value="${pageContext.request.contextPath}" scope="page"/><!--需要引入jstl-1.2.jar包-->
<%dbUser LoginUser = (dbUser)request.getAttribute("dbUser");%>
<html>
<head>
    <title>订餐</title>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport"
          content="width=device-width,user-scalable=yes, minimum-scale=0.4, initial-scale=0.8"/>
    <meta http-equiv="Cache-Control" content="no-siteapp"/>
    <!--[if lt IE 9]>
    <script src="https://cdn.staticfile.org/html5shiv/r29/html5.min.js"></script>
    <script src="https://cdn.staticfile.org/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
    <link rel="stylesheet" href="${ctx}/static/css/takeout.css">
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
            <a onclick="loginOut()" style="cursor: pointer;">[ 退出 ]</a>
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
        <a>菜品</a>
    </div>
    <%--筛选区--%>
    <div class="screen clearfix">
        <div class="fl gather">
            <ul>
                <li onclick="All()" class="liis" style="color: #FF5722">全部</li>
                <li onclick="chant(this)" class="liis"><input type="text" value="1" hidden />主食</li>
                <li onclick="chant(this)" class="liis"><input type="text" value="2" hidden />甜品</li>
                <li onclick="chant(this)" class="liis"><input type="text" value="3" hidden />海鲜</li>
                <li onclick="chant(this)" class="liis"><input type="text" value="4" hidden />蔬菜</li>
                <li onclick="chant(this)" class="liis"><input type="text" value="5" hidden />肉类</li>
                <li onclick="chant(this)" class="liis"><input type="text" value="6" hidden />汤类</li>
                <li onclick="chant(this)" class="liis"><input type="text" value="7" hidden />酒水</li>
                <li onclick="chant(this)" class="liis"><input type="text" value="8" hidden />饮料</li>
            </ul>
        </div>
        <i class="fr" onclick="selectMune()"></i>
        <div class="search fr">
            <input type="text" id="muneName">
        </div>
    </div>
    <%--显示区--%>
    <div class="view clearfix" id="view">

    </div>
    <%--购物车弹出层--%>
    <div class="shoppingCar" style="display: none;" id="shoppingCar">
        <div class="CarContent">
            <div class="Cartop">
                <p>购物车</p><i onclick="closes()"></i>
            </div>
            <div class="Carbody clearfix" id="Carbody">

            </div>
            <div class="Cartail">
                    <div class="Cartail-1" onclick="purchase()">下单 ￥<input type="text" value="" id="total"></div>
            </div>
        </div>
    </div>
</div>
<script src="${ctx}/static/js/jquery.min.js" charset="utf-8"></script>
<script src="${ctx}/static/lib/layui/layui.js" charset="utf-8"></script>
<script>
    $(function (){
        All();
        selectUser();
        var userId = '<%=LoginUser.getId()%>';
        $.post("${ctx}/takeout?mothed=inserCar",{
            userId:userId
        },function (jsonMsg){
            if(!jsonMsg.state){
                layer.msg(jsonMsg.msg,{icon:5})
            }
        });
    })
    //查询用户信息
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
    //页面加载时加载所以菜品
    function All(){
        var select = document.getElementsByClassName("liis");
        for (var i = 0; i < select.length; i++) {
            select[i].style.color = "#000000";
            select[0].style.color = " #FF5722";
        }
        $.post('${ctx}/takeout?mothed=selectMenu',{},function (jsonMsg){
            var view = document.getElementById("view");

            for (var i = 0; i < jsonMsg.length; i++) {
                view.innerHTML +='<div class="menu" id="menu" onmouseover="shows(this)" onmouseout="hident(this)"><img src="${ctx}/modification?mothed=getPortraitImage&imgName='+jsonMsg[i].picture+'" class="menuImg"><div class="show" style="display: none" id="show'+i+'"><div class=""><p>'+jsonMsg[i].menuName+'</p><span>￥'+jsonMsg[i].price+'</span></div><div class="Add"><i onclick="AddCar('+jsonMsg[i].id+')"></i>&nbsp;&nbsp;&nbsp;<a href="${ctx}/particular?mothed=index&t='+jsonMsg[i].id+'"></a></div></div></div>'
            }
        });
    }
    //菜品搜索
    function chant(o){
        var select = document.getElementsByClassName("liis");
        for (var i = 0; i < select.length; i++) {
            select[i].style.color = "#000000";
            o.style.color = " #FF5722";
        }
        var menuTypeId = o.firstElementChild.value;
        $.post('${ctx}/takeout?mothed=selectMenuType',{
            menuTypeId:menuTypeId
        },function (jsonMsg){
            var view = document.getElementById("view");
            for (var i = view.children.length - 1; i >= 0; i--) {
                view.removeChild(view.children[i]);
            }
            for (var i = 0; i < jsonMsg.length; i++) {
                view.innerHTML +='<div class="menu" id="menu" onmouseover="shows(this)" onmouseout="hident(this)"><img src="${ctx}/modification?mothed=getPortraitImage&imgName='+jsonMsg[i].picture+'" class="menuImg"><div class="show" style="display: none" id="show'+i+'"><div class=""><p>'+jsonMsg[i].menuName+'</p><span>￥'+jsonMsg[i].price+'</span></div><div class="Add"><i onclick="AddCar('+jsonMsg[i].id+')"></i>&nbsp;&nbsp;&nbsp;<a href="${ctx}/particular?mothed=index&t='+jsonMsg[i].id+'"></a></div></div></div>'
            }
        });
    }
    //打开
    function shows(o){
        o.lastElementChild.style.display="block";
    }
    //关闭
    function hident(o){
        o.lastElementChild.style.display="none";
    }
    //添加购物车
    function AddCar(o){
        var Carbody = document.getElementById("Carbody");
        var shoppingCar = document.getElementById("shoppingCar");
        var userId = '<%=LoginUser.getId()%>'
        var pice = 0;
        $.post('${ctx}/takeout?mothed=addShopingCar',{
            userId:userId,
            menuId:o
        },function (jsonMsg){
            var data = jsonMsg.data;
            if(jsonMsg.state){
                shoppingCar.style.display="block";
                for (var i = Carbody.children.length - 1; i >= 0; i--) {
                    Carbody.removeChild(Carbody.children[i]);
                }
                for (var i = 0;i<data.length;i++){
                    pice+=data[i].amount*data[i].price
                    Carbody.innerHTML+='<div class="clearfix" style="margin: 3px 0 3px;"><input type="text" hidden value="'+data[i].carId+'"><img src="${ctx}/modification?mothed=getPortraitImage&imgName='+data[i].picture+'" class="fl"><span class="fl">'+data[i].menuName+'</span><div class="Carbody-1 fl"><b style="cursor: pointer;" onclick="deletes('+data[i].carId+','+data[i].id+',this,'+i+')">Del</b><i class="push" onclick="minus('+data[i].carId+','+data[i].id+','+i+')"></i><input type="text" value="'+data[i].amount+'" id="quantity'+i+'"><i class="minus" style="left: 100px" onclick="push('+data[i].carId+','+data[i].id+','+i+')"></i><p>￥<b id="price'+i+'">'+data[i].price+'</b></p></div></div>'
                }
                document.getElementById("total").setAttribute("value",pice);
            }else {
                layer.msg(jsonMsg.msg,{icon: 5});
            }
        });
    }

    //购物车减少按钮
    function minus(carId,Id,i){
        var quantity = document.getElementById("quantity"+i);
        var price = document.getElementById("price"+i);
        var total = document.getElementById("total");
        var q = parseInt(quantity.value);
        var p = parseInt(price.innerText);
        var t = parseInt(total.value);
        if((quantity.value-1)<1){
            layer.alert("不能为零")
            return
        }
        $.post('${ctx}/takeout?mothed=minus',{
            carId:carId,
            menuId:Id
        },function (jsonMsg){
            if(jsonMsg.state){
                var quantitys = quantity.value--;
                total.setAttribute("value",t-p);
            }else {
                layer.msg(jsonMsg.msg,{icon:5});
            }
        })
    }
    //购物车添加按钮
    function push(carId,Id,i){
        var quantity = document.getElementById("quantity"+i);
        var price = document.getElementById("price"+i);
        var total = document.getElementById("total");
        var q = parseInt(quantity.value);
        var p = parseInt(price.innerText);
        var t = parseInt(total.value);
        if((quantity.value-1)>8){
            layer.alert("不能大于10")
            return
        }
        $.post('${ctx}/takeout?mothed=push',{
            carId:carId,
            menuId:Id
        },function (jsonMsg){
            if(jsonMsg.state){
                var quantitys = quantity.value++;
                total.setAttribute("value",t+p);
            }else {
                layer.msg(jsonMsg.msg,{icon:5});
            }
        })
    }
    //删除购物车数据
    function deletes(carId,Id,o,i){
        var quantity = document.getElementById("quantity"+i);
        var price = document.getElementById("price"+i);
        var total = document.getElementById("total");
        var q = parseInt(quantity.value);
        var p = parseInt(price.innerText);
        var t = parseInt(total.value);
        layer.confirm('是否删除该菜品?', {icon: 3, title:'提示'}, function(index){
            layer.close(index);
            //发送请求
            var layerIndex=layer.load();
            $.post('${ctx}/takeout?mothed=deletes',{
                carId:carId,
                menuId:Id
            },function (jsonMsg){
                layer.close(layerIndex);
                if (jsonMsg.state){
                    document.getElementById("Carbody").removeChild(o.parentElement.parentElement);
                    total.setAttribute("value",t-q*p);
                }else{
                    layer.msg(jsonMsg.msg,{icon:5});
                }
            })
        });
    }
    //关闭购物车弹出层
    function closes(o){
        var shoppingCar = document.getElementById("shoppingCar");
        shoppingCar.style.display="none";
    }
    //根据名字查询菜品
    function selectMune(){
        var muneName = document.getElementById("muneName").value;
        var view = document.getElementById("view");
        $.post('${ctx}/takeout?mothed=selectMenuName',{muneName:muneName},function (jsonMsg){
            for (var i = view.children.length - 1; i >= 0; i--) {
                view.removeChild(view.children[i]);
            }
            for (var i = 0; i < jsonMsg.length; i++) {
                view.innerHTML +='<div class="menu" id="menu" onmouseover="shows(this)" onmouseout="hident(this)"><img src="${ctx}/modification?mothed=getPortraitImage&imgName='+jsonMsg[i].picture+'" class="menuImg"><div class="show" style="display: none" id="show'+i+'"><div class=""><p>'+jsonMsg[i].menuName+'</p><span>￥'+jsonMsg[i].price+'</span></div><div class="Add"><i onclick="AddCar('+jsonMsg[i].id+')"></i>&nbsp;&nbsp;&nbsp;<a href="${ctx}/particular?mothed=index&t='+jsonMsg[i].id+'"></a></div></div></div>'
            }
        })
    }
    function purchase(){
        var Carbody = document.getElementById("Carbody");
        var carId = Carbody.firstElementChild.firstElementChild.value;
        window.location.replace("${ctx}/order?mothed=index&carId="+carId);
    }
    //退出登录
    function loginOut(){
        $.post('${ctx}/login?mothed=loginOut',function (){
            window.location.reload();
        })
    }
</script>
</body>
</html>
