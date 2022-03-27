<%--
  Created by IntelliJ IDEA.
  User: super007
  Date: 2021/9/18
  Time: 14:30
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" scope="page"/>
<html>
<head>
    <title>月费标准</title>
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
            height: 40px;
            color: #0000FF;
            font-size: 30px;
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
<div>
    <ul class="layui-tab-title">
        <li class="home">
            <i class="layui-icon">&#xe656;</i>月费标准
        </li>
    </ul>
</div>
<div class="bigBox">
    <div class="main">
        <div class="advanced">
            <div class="layui-row">
                <div class="layui-col-md12 Title">
                    <span id="advancedName">高级护理</span>
                </div>
                <div class="layui-col-md12 price">
                    <div class="layui-col-md3 little">床位价格</div>
                    <div class="layui-col-md3 little">伙食价格</div>
                    <div class="layui-col-md3 little">护理价格</div>
                    <div class="layui-col-md3 little">设施价格</div>
                </div>
                <div class="layui-col-md12 money">
                    <div class="layui-col-md3 little">800</div>
                    <div class="layui-col-md3 little">1000</div>
                    <div class="layui-col-md3 little">800</div>
                    <div class="layui-col-md3 little">800</div>
                </div>
                <div class="layui-col-md12 price">
                    <div class="layui-col-md3 little">床位介绍</div>
                    <div class="layui-col-md3 little">伙食介绍</div>
                    <div class="layui-col-md3 little">护理介绍</div>
                    <div class="layui-col-md3 little">设施介绍</div>
                </div>
                <div class="layui-col-md12 introduce">
                    <div class="layui-col-md3 little">高端的红木床具,视野良好楼层</div>
                    <div class="layui-col-md3 little">提供长者所需的所有营业品</div>
                    <div class="layui-col-md3 little">按摩,推拿,散步等休闲护理</div>
                    <div class="layui-col-md3 little">更高端的设备</div>
                </div>
            </div>
        </div>
        <div class="intermediate">
            <div class="layui-row">
                <div class="layui-col-md12 Title">
                    <span id="intermediateName little">中级护理</span>
                </div>
                <div class="layui-col-md12 price">
                    <div class="layui-col-md3 little">床位价格</div>
                    <div class="layui-col-md3 little">伙食价格</div>
                    <div class="layui-col-md3 little">护理价格</div>
                    <div class="layui-col-md3 little">设施价格</div>
                </div>
                <div class="layui-col-md12 money">
                    <div class="layui-col-md3 little">500</div>
                    <div class="layui-col-md3 little">800</div>
                    <div class="layui-col-md3 little">500</div>
                    <div class="layui-col-md3 little">800</div>
                </div>
                <div class="layui-col-md12 price">
                    <div class="layui-col-md3 little">床位介绍</div>
                    <div class="layui-col-md3 little">伙食介绍</div>
                    <div class="layui-col-md3 little">护理介绍</div>
                    <div class="layui-col-md3 little">设施介绍</div>
                </div>
                <div class="layui-col-md12 introduce">
                    <div class="layui-col-md3 little">温馨的床位,视野和采光较良好</div>
                    <div class="layui-col-md3 little">除了一日三餐,每晚更有营业品</div>
                    <div class="layui-col-md3 little">拥有两个专属的护工</div>
                    <div class="layui-col-md3 little">更高端的设备</div>
                </div>
            </div>
        </div>
        <div class="introduction">
            <div class="layui-row">
                <div class="layui-col-md12 Title">
                    <span id="introductionName">入门护理</span>
                </div>
                <div class="layui-col-md12 price">
                    <div class="layui-col-md3 little">床位价格</div>
                    <div class="layui-col-md3 little">伙食价格</div>
                    <div class="layui-col-md3 little">护理价格</div>
                    <div class="layui-col-md3 little">设施价格</div>
                </div>
                <div class="layui-col-md12 money">
                    <div class="layui-col-md3 little">300</div>
                    <div class="layui-col-md3 little">500</div>
                    <div class="layui-col-md3 little">300</div>
                    <div class="layui-col-md3 little">800</div>
                </div>
                <div class="layui-col-md12 price">
                    <div class="layui-col-md3 little">床位介绍</div>
                    <div class="layui-col-md3 little">伙食介绍</div>
                    <div class="layui-col-md3 little">护理介绍</div>
                    <div class="layui-col-md3 little">设施介绍</div>
                </div>
                <div class="layui-col-md12 introduce">
                    <div class="layui-col-md3 little">普通床位</div>
                    <div class="layui-col-md3 little">饿不死</div>
                    <div class="layui-col-md3 little">拥有专属的负责人</div>
                    <div class="layui-col-md3 little">更高端的设备</div>
                </div>
            </div>
        </div>
    </div>
</div>
<script src="${ctx}/static/lib/layui/layui.js" charset="UTF-8"></script>
<script src="${ctx}/static/js/jquery.min.js" charset="UTF-8"></script>

</body>
</html>
