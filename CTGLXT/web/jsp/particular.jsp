<%@ page import="com.yxm.po.dbUser" %>
<%@ page import="com.yxm.po.dbMenu" %><%--
  Created by IntelliJ IDEA.
  User: super007
  Date: 2021/5/18
  Time: 16:24
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><!--需要引入jstl-1.2.jar包-->
<c:set var="ctx" value="${pageContext.request.contextPath}" scope="page"/><!--需要引入jstl-1.2.jar包-->
<html>
<head>
    <title>菜品详细</title>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport"
          content="width=device-width,user-scalable=yes, minimum-scale=0.4, initial-scale=0.8"/>
    <meta http-equiv="Cache-Control" content="no-siteapp"/>
    <!--[if lt IE 9]>
    <script src="https://cdn.staticfile.org/html5shiv/r29/html5.min.js"></script>
    <script src="https://cdn.staticfile.org/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
    <link rel="stylesheet" href="${ctx}/static/css/particular.css">
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
        <a>菜品&nbsp;&nbsp;|&nbsp;&nbsp;菜品详细</a>
    </div>
    <div class="menuShow clearfix">
        <div class="menuIng fl">
            <img src="" id="menuImg">
        </div>
        <div class="menuContent fl">
            <b id="menuName"></b><br>
            <label>价格:</label><input type="text" id="pice" style="color: red;font-size: 18px;" readonly><br>
            <label>销量:</label><input type="text" id="market" style="color:blue;" readonly>份<br>
            <label>收藏:</label><input type="text" id="collect" style="color:green;" readonly value="">份<br>
            <label>数量:</label><i class="ImgLe" onclick="Minuse()"></i><input type="text" value="1" readonly style="margin-left: 50px;border: 1px solid #000000;" id="quantitys"><i class="ImgLr" onclick="Pushse()"></i><br>
            <p class="time" id="time" style="display: none">数量不能大于10或者小于1</p>
            <div class="menuFunction">
                <button type="button" style="background: red" onclick="purchase()">立即购买</button>
                <button type="button" style="background: #FF5722" onclick="AddCar()">加入购物车</button>
                <button type="button" style="background: #4476A7" onclick="collect()">收藏</button>
            </div>
            <div>

            </div>
        </div>
    </div>
    <div class="particular fl">
        <div class="particular-top">
            <ul>
                <li class="liis" onclick="chant(this)" style="border-bottom: 3px solid #eb7350;"><input type="text" value="1" hidden>菜品信息</li>
                <li class="liis" onclick="chant(this)"><input type="text" value="2" hidden>菜品评价</li>
                <li class="liis" onclick="chant(this)"><input type="text" value="3" hidden>主厨信息</li>
            </ul>
        </div>
        <div id="common">
            <div class="particularParent">
                <div class="particularImg">
                    <img src="" alt="" id="particularImg">
                </div>
                <div class="particularText">
                    <p>介绍:</p>
                    <textarea readonly id="introduce"></textarea>
                </div>
            </div>
        </div>
    </div>
    <div class="hot fl">
        <p>热门推荐</p>
        <div id="ranking">
<%--            <div class="ranking" onclick="switchover()">--%>
<%--                <img src="${ctx}/static/img/背景.jpg">--%>
<%--                <span>背景烤鸭</span>--%>
<%--            </div>--%>
        </div>
    </div>
    <%--购物车弹出层--%>
    <div class="shoppingCar" id="shoppingCar" style="display: none;">
        <div class="CarContent">
            <div class="Cartop">
                <p>购物车</p><i onclick="closes()"></i>
            </div>
            <div class="Carbody clearfix" id="Carbody">

            </div>
            <div class="Cartail">
                <div class="Cartail-1">下单 ￥<input type="text" value="" id="total"></div>
            </div>
        </div>
    </div>
</div>
<script src="${ctx}/static/js/jquery.min.js" charset="utf-8"></script>
<script src="${ctx}/static/lib/layui/layui.js" charset="utf-8"></script>
<script>
    <%dbUser LoginUser = (dbUser)request.getAttribute("dbUser"); %>
    <%dbMenu Menu = (dbMenu)request.getAttribute("dbMenu"); %>
    $(function (){
        selectUser();
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
        var menuId = <%=Menu.getId()%>
        $.post("${ctx}/particular?mothed=selectMenu",{menuId:menuId},function (jsonMsg){
            if(jsonMsg.state){
                var data = jsonMsg.data;
                $("#menuName").text(data.menuName);
                $("#pice").prop("value",data.price);
                $("#market").prop("value",data.market);
                $("#particularImg").prop("src","${ctx}/modification?mothed=getPortraitImage&imgName="+data.picture);
                $("#menuImg").prop("src","${ctx}/modification?mothed=getPortraitImage&imgName="+data.picture);
                $("#collect").prop("value",data.collectS)
                if(data.introduce==null||data.introduce==""){
                    $("#introduce").text("");
                }else {
                    $("#introduce").text(data.introduce);
                }
            }else {
                layer.msg(jsonMsg.msg,{icon:5})
            }
        });
        $.post("${ctx}/particular?mothed=ranking",function (data){
            var ranking = document.getElementById("ranking");
            for (var i = 0;i<3;i++){
                ranking.innerHTML+='<div class="ranking" onclick="switchover('+data[i].id+')"><img src="${ctx}/modification?mothed=getPortraitImage&imgName='+data[i].picture+'"><span>'+data[i].menuName+'</span></div>';
            }
        });
    }
    function Minuse(){
        var quantity = document.getElementById("quantitys");
        var time = document.getElementById("time");
        var q = quantity.value;
        if(q<2){
            time.style.display="block";
            setTimeout(function (){
                time.style.display="none";
            },2500);
            return
        }
        var quantitys = quantity.value--;
        quantity.setAttribute("value",quantitys);
    }
    function AddCar(){
        var Carbody = document.getElementById("Carbody");
        var shoppingCar = document.getElementById("shoppingCar");
        var quantity = document.getElementById("quantitys");
        var userId = '<%=LoginUser.getId()%>';
        var menuId = '<%=Menu.getId()%>';
        var pice = 0;
        $.post('${ctx}/takeout?mothed=addShopingCarS',{
            userId:userId,
            menuId:menuId,
            quantity:quantity.value
        },function (jsonMsg){
            var data = jsonMsg.data;
            if(jsonMsg.state){
                shoppingCar.style.display="block";
                console.log(data);
                for (var i = Carbody.children.length - 1; i >= 0; i--) {
                    Carbody.removeChild(Carbody.children[i]);
                }
                for (var i = 0;i<data.length;i++){
                    pice+=data[i].amount*data[i].price
                    Carbody.innerHTML+='<div class="clearfix" style="margin: 3px 0 3px;"><img src="${ctx}/modification?mothed=getPortraitImage&imgName='+data[i].picture+'" class="fl"><span class="fl">'+data[i].menuName+'</span><div class="Carbody-1 fl"><b style="cursor: pointer;" onclick="deletes('+data[i].carId+','+data[i].id+',this,'+i+')">Del</b><i class="push" onclick="minus('+data[i].carId+','+data[i].id+','+i+')"></i><input type="text" value="'+data[i].amount+'" id="quantity'+i+'"><i class="minus" style="left: 100px" onclick="push('+data[i].carId+','+data[i].id+','+i+')"></i><p>￥<b id="price'+i+'">'+data[i].price+'</b></p></div></div>'
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
    function Pushse(){
        var quantity = document.getElementById("quantitys");
        var time = document.getElementById("time");
        var q = quantity.value;
        if(q>9){
            time.style.display="block";
            setTimeout(function (){
                time.style.display="none";
            },2500);
            return
        }
        var quantitys = quantity.value++;
        quantity.setAttribute("value",quantitys);
    }
    function chant(o){
        var select = document.getElementsByClassName("liis");
        var common = document.getElementById("common");
        for (var i = 0; i < select.length; i++) {
            select[i].style.borderBottom = "none";
            o.style.borderBottom = "3px solid #eb7350";
        }
        var menuTypeId = o.firstElementChild.value;
        if(menuTypeId==1){

        }
        if(menuTypeId==2){

        }
        if(menuTypeId==3){

        }
    }
    function switchover(menuId){
        window.location.replace("${ctx}/particular?mothed=index&t="+menuId);
    }
    function collect(){
        var collect = document.getElementById("collect");
        console.log(collect.value)
        var menuId = '<%=Menu.getId()%>';
        var userId = '<%=LoginUser.getId()%>';
        $.post('${ctx}/particular?mothed=collect',{
            menuId:menuId,
            userId:userId
        },function (jsonMsg){
            if(jsonMsg.state){
                collect.setAttribute("value",collect.value++);
                layer.msg(jsonMsg.msg,{icon:6})
            }else {
                layer.msg(jsonMsg.msg,{icon:5})
            }
        });
    }
    function closes(o){
        var shoppingCar = document.getElementById("shoppingCar");
        shoppingCar.style.display="none";
    }
    function purchase(){
        var menuId = '<%=Menu.getId()%>';
        window.location.replace("${ctx}/order?mothed=index&menuId="+menuId);
    }
</script>
</body>
</html>
