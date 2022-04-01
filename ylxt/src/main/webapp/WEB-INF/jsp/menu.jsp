<%--
  Created by IntelliJ IDEA.
  User: super007
  Date: 2021/9/21
  Time: 10:59
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" scope="page"/>
<html>
<head>
    <title>菜谱大全</title>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport"
          content="width=device-width,user-scalable=yes, minimum-scale=0.4, initial-scale=0.8"/>
    <meta http-equiv="Cache-Control" content="no-siteapp"/>
    <link rel="stylesheet" href="${ctx}/static/lib/layui/css/layui.css">
    <link rel="stylesheet" href="${ctx}/static/css/advance.css">
    <!--[if lt IE 9]>
    <script src="https://cdn.staticfile.org/html5shiv/r29/html5.min.js"></script>
    <script src="https://cdn.staticfile.org/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
    <style>
        .bigBox{
            text-align: center;
            position: relative;
        }
        .main{
            position: absolute;
            left: 2%;
            top: 2%;
            width: 96%;
            height: 96%;
            border-radius: 12px;
            background: #FFFFFF;
            border: 1px solid #FFB800;
            overflow: auto;
        }
        .advanced{
            margin-top: 1%;
            margin-left: 2%;
            width: 94%;
        }
        .intermediate{
            margin-top: 1%;
            margin-left: 2%;
            width: 94%;
        }
        .introduction{
            margin-top: 1%;
            margin-left: 2%;
            width: 94%;
        }
        .Title{
            height: 30px;
            color: #0000FF;
            font-size: 20px;
            font-family: 楷体;
        }
        .price{
            height: 30px;
            font-size: 20px;
            color: #FFB800;
        }
        .money{
            height:30px;
            line-height: 30px;
            font-size: 20px;
            color: #0000FF;
            font-family: 楷体;
        }
        .introduce{
            height:60px;
            line-height: 30px;
            font-size: 14px;
        }
        .little{
            border: 1px solid #C2BE9E;
            border-radius: 3px;
        }
    </style>
</head>
<body>
<div class="layui-inline">
    <ul class="layui-tab-title">
        <li class="home">
            <i class="layui-icon">&#xe857;</i>菜谱大全
        </li>
    </ul>
</div>
<div class="bigBox">
    <div class="main">
        <div class="advanced">
            <div class="layui-row expand">
                <div class="layui-col-md12 price">
                    <div class="layui-col-md1">&nbsp;</div>
                    <div class="layui-col-md2 little">套餐名称</div>
                    <div class="layui-col-md2 little">主食</div>
                    <div class="layui-col-md2 little">蔬菜</div>
                    <div class="layui-col-md2 little">肉类</div>
                    <div class="layui-col-md2 little">汤类</div>
                    <div class="layui-col-md1">&nbsp;</div>
                </div>
                <c:forEach items="${requestScope.sysCombos}" var="menuLvl1">
                    <div class="layui-col-md12 price">
                        <div class="layui-col-md1">&nbsp;</div>
                        <div class="layui-col-md2 money">${menuLvl1.comboName}</div>
                        <div class="layui-col-md2 money">${menuLvl1.stapleFood}</div>
                        <div class="layui-col-md2 money">${menuLvl1.vegetable}</div>
                        <div class="layui-col-md2 money">${menuLvl1.meat}</div>
                        <div class="layui-col-md2 money">${menuLvl1.soup}</div>
                        <div class="layui-col-md1">&nbsp;</div>
                    </div>
                </c:forEach>
            </div>
        </div>
    </div>
</div>
</body>
</html>
