<%--
  Created by IntelliJ IDEA.
  User: super007
  Date: 2021/9/5
  Time: 9:13
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" scope="page"/>
<html>
<head>
    <title>床位一览</title>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport"
          content="width=device-width,user-scalable=yes, minimum-scale=0.4, initial-scale=0.8"/>
    <meta http-equiv="Cache-Control" content="no-siteapp"/>
    <link rel="stylesheet" href="${ctx}/static/lib/layui/css/layui.css">
    <link rel="stylesheet" href="${ctx}/static/css/berth.css">
    <!--[if lt IE 9]>
    <script src="https://cdn.staticfile.org/html5shiv/r29/html5.min.js"></script>
    <script src="https://cdn.staticfile.org/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
    <style>

    </style>
</head>
<body>
<div>
    <ul class="layui-tab-title">
        <li class="home">
            <i class="layui-icon">&#xe615;</i>床位一览
        </li>
    </ul>
</div>
<div class="layui-tab layui-tab-card">
    <ul class="layui-tab-title">
        <li class="layui-this" onclick="selectBerth(this)" value="1">一栋一楼</li>
        <li onclick="selectBerth(this)" value="2">一栋二楼</li>
        <li onclick="selectBerth(this)" value="3">一栋三楼</li>
        <li onclick="selectBerth(this)" value="4">一栋五楼</li>
        <li onclick="selectBerth(this)" value="5">一栋六楼</li>
        <li onclick="selectBerth(this)" value="6">一栋七楼</li>
        <li onclick="selectBerth(this)" value="7">一栋八楼</li>
        <li onclick="selectBerth(this)" value="8">一栋九楼</li>
        <li onclick="selectBerth(this)" value="9">一栋十楼</li>
    </ul>
    <div class="shows">
        <div class="counter" id="counter">一楼服务台</div>
        <div class="active">活动区</div>
        <div class="houseWoman">
            <ul id="houseWoman">
            </ul>
            卧室
        </div>
        <div class="houseMan">
            <ul id="houseMan">
            </ul>
            卧室
        </div>
    </div>
</div>
<script src="${ctx}/static/lib/layui/layui.js" charset="UTF-8"></script>
<script src="${ctx}/static/js/jquery.min.js" charset="UTF-8"></script>
<script>
    $(function (){
        call(1);
    })
    function selectBerth(o){
        var berthId = o.value;
        switch (berthId){
            case 1:
                document.getElementById("counter").innerText="一楼服务台";
                call(berthId);
                break;
            case 2:
                document.getElementById("counter").innerText="二楼服务台";
                call(berthId);
                break;
            case 3:
                document.getElementById("counter").innerText="三楼服务台";
                call(berthId);
                break;
            case 4:
                document.getElementById("counter").innerText="五楼服务台";
                call(berthId);
                break;
            case 5:
                document.getElementById("counter").innerText="六楼服务台";
                call(berthId);
                break;
            case 6:
                document.getElementById("counter").innerText="七楼服务台";
                call(berthId);
                break;
            case 7:
                document.getElementById("counter").innerText="八楼服务台";
                call(berthId);
                break;
            case 8:
                document.getElementById("counter").innerText="九楼服务台";
                call(berthId);
                break;
            case 9:
                document.getElementById("counter").innerText="十楼服务台";
                call(berthId);
                break;
        }
    }
    function call(o){
        var houseWoman = document.getElementById("houseWoman");
        var houseMan = document.getElementById("houseMan");
        var houseWomanChild= document.getElementById("houseWoman").childNodes;
        var houseManChild= document.getElementById("houseMan").childNodes;
        for (var j = houseWomanChild.length - 1; j >= 0; j--) {
            houseWoman.removeChild(houseWomanChild[j]);
        }
        for (var j = houseManChild.length - 1; j >= 0; j--) {
            houseMan.removeChild(houseManChild[j])
        }
        $.post("${ctx}/nurse/selectBerthAll",{berthId:o},function (jsonData){
            if(jsonData.state){
                var berthData = jsonData.data;
                for (var i = 0;i<berthData.length;i++){
                    if(berthData[i].berthCode==0){
                        var elderName = berthData[i].elderName;
                        if(elderName==null){
                            elderName="空";
                        }
                        if(i<4){
                            houseWoman.innerHTML+='<li><p>'+berthData[i].berthNumber+'</p>'+elderName+'</li>';
                        }else {
                            houseMan.innerHTML+='<li><p>'+berthData[i].berthNumber+'</p>'+elderName+'</li>';
                        }
                    }
                    if (berthData[i].berthCode==1){
                        var elderName = berthData[i].elderName;
                        if(i<4){
                            houseWoman.innerHTML+='<li><p>'+berthData[i].berthNumber+'</p>'+elderName+'<br>待入住</li>';
                        }else {
                            houseMan.innerHTML+='<li><p>'+berthData[i].berthNumber+'</p>'+elderName+'<br>待入住</li>';
                        }
                    }
                    if(berthData[i].berthCode==2){
                        var elderName = berthData[i].elderName;
                        if(i<4){
                            houseWoman.innerHTML+='<li><p>'+berthData[i].berthNumber+'</p>'+elderName+'<br>已入住</li>';
                        }else {
                            houseMan.innerHTML+='<li><p>'+berthData[i].berthNumber+'</p>'+elderName+'<br>已入住</li>';
                        }
                    }

                }
            }else {
                layer.msg(jsonData.msg)
            }
        });
    }
</script>
</body>
</html>
