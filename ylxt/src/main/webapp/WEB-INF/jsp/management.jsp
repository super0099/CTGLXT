<%--
  Created by IntelliJ IDEA.
  User: super007
  Date: 2021/9/21
  Time: 9:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" scope="page"/>
<html>
<head>
    <title>订餐查询</title>
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
        .inputLabel{
            padding: 10px 0;
            margin-right: 10px;
        }
        .layui-input{
            height: 45px;
        }
    </style>
</head>
<body>
<div class="layui-inline title">
    <ul class="layui-tab-title">
        <li class="home">
            <i class="layui-icon">&#xe60a;</i>订餐查询
        </li>
    </ul>
</div>
<div class="layui-fluid">
    <div class="layui-row layui-col-space15">
        <div class="layui-col-md12">
            <div class="layui-card">
                <%--搜索栏--%>
                <div class="layui-card-body ">
                    <form class="layui-form layui-col-space5">
                        <div class="layui-inline layui-show-xs-block">
                            <label class="layui-form-label inputLabel">长者姓名:</label>
                            <div class="layui-input-inline layui-show-xs-block">
                                <input type="text" placeholder="长者姓名" class="layui-input inputBox" id="elderName">
                            </div>
                        </div>
                        <div class="layui-input-inline layui-show-xs-block">
                            <button class="layui-btn" type="button" id="btnSearch">
                                <i class="layui-icon layui-icon-search"></i>
                            </button>
                        </div>
                    </form>
                </div>
                <%--表格--%>
                <div class="layui-card-body ">
                    <table class="layui-hide" id="tableOrder" lay-filter="tableRoleEvent"></table>
                </div>
            </div>
        </div>
    </div>
</div>
<script src="${ctx}/static/js/jquery.min.js" charset="UTF-8"></script>
<script src="${ctx}/static/lib/layui/layui.js" charset="UTF-8"></script>
<script>
    var layerFormIndex;
    layui.use(function(){
        var table = layui.table,
            $ = layui.$,
            laydate = layui.laydate,
            layer = layui.layer,
            form = layui.form,
            tree=layui.tree;
        // 菜单树形table初始化
        var tableRole = table.render({
            elem: '#tableOrder',//table元素
            id: 'tableOrder',
            url: '${ctx}/management/selectOrderAll',//数据url
            page: true,//分页
            cols: [[
                {field: 'elderName', title: '长者姓名', width: 120,},
                {field: 'id', title: 'id', event: 'id', width: 120, hide: true},
                {field: 'elderId', title: '长者Id', event:'elderId',hide:true},
                {field: 'elderSite', title: '床位号', event: 'elderSite'},
                {field: 'orderDate', title: '订餐日期', event: 'orderDate',templet:function (rowData){
                    var dt = new Date(rowData.orderDate);
                    return dt.getFullYear()+"-"+(dt.getMonth()+1)+"-"+dt.getDate();
                    }},
                {field: 'bf', title: '早餐套餐名称', event: 'bf'},
                {field: 'lc', title: '午餐套餐名称', event: 'lc'},
                {field: 'de', title: '晚餐套餐名称', event: 'de'}
            ]],
            limit: 5,//每页页数
            limits: [5, 10, 15, 20, 25, 30, 35, 40, 45, 50]//定义每页页数选择下拉框
        });


        //查询按钮
        $("#btnSearch").click(function (){
            var elderName=$("#elderName").val();
            //表格重载
            table.reload('tableOrder', {
                where: {
                    elderName:elderName
                } //设定异步数据接口的额外参数
            });
        });
    });
</script>
</body>
</html>
