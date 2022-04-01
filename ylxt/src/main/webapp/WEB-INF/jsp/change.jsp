<%--
  Created by IntelliJ IDEA.
  User: super007
  Date: 2021/9/18
  Time: 9:35
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" scope="page"/>
<html>
<head>
    <title>人员变更管理</title>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport"
          content="width=device-width,user-scalable=yes, minimum-scale=0.4, initial-scale=0.8"/>
    <meta http-equiv="Cache-Control" content="no-siteapp"/>
    <link rel="stylesheet" href="${ctx}/static/lib/layui/css/layui.css">
    <!--[if lt IE 9]>
    <script src="https://cdn.staticfile.org/html5shiv/r29/html5.min.js"></script>
    <script src="https://cdn.staticfile.org/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
    <style>
        body{
            height: 100%;
        }
        .inputBox{
            padding: 20px 5px;
        }
        .inputLabel{
            padding: 10px 0;
            margin-right: 10px;
        }
        .layui-form-label{
            padding: 11px 5px;
        }
        .layui-input{
            height: 45px;
        }
    </style>
</head>
<body>
<div>
    <ul class="layui-tab-title">
        <li class="home">
            <i class="layui-icon">&#xe607;</i>人员变更管理
        </li>
    </ul>
</div>
<div class="layui-fluid">
    <div class="layui-row layui-col-space15">
        <%----%>
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
                        <div class="layui-inline layui-show-xs-block">
                            <label class="layui-form-label inputLabel">身份证号:</label>
                            <div class="layui-input-inline layui-show-xs-block">
                                <input type="text" id="idNumber" placeholder="身份证号" class="layui-input inputBox">
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
                    <table class="layui-hide" id="tableRole" lay-filter="tableRoleEvent"></table>
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
            elem: '#tableRole',//table元素
            id: 'tableRole',
            url: '${ctx}/change/selectAlterationAll',//数据url
            page: true,//分页
            cols: [[
                {field: 'elderName', title: '长者姓名'},
                {field: 'residence', title: '长者家庭地址'},
                {field: 'idnumber', title: '身份证号', event:'idnumber',},
                {
                    field: 'alterationType', title: '变更类型', templet: function (rowData) {
                        if (rowData.alterationType === 1) {
                            return '入住安乐居';
                        } else if (rowData.alterationType === 2) {
                            return '请假外出';
                        } else if (rowData.alterationType === 3) {
                            return '退住安乐居';
                        }
                    }
                },
                {field: 'alterationTime', title: '时间', event: 'alterationTime',templet:function (rowData){
                        var dt = new Date(rowData.alterationTime);
                        var minute = dt.getMinutes()<10?"0"+dt.getMinutes():dt.getMinutes();
                        var second = dt.getSeconds()<10?"0"+dt.getSeconds():dt.getSeconds();
                        return dt.getFullYear() + "-" + (dt.getMonth() + 1) + "-" + dt.getDate()+" "+
                            dt.getHours()+":"+minute+":"+second;
                    }
                },
            ]],
            limit: 5,//每页页数
            limits: [5, 10, 15, 20, 25, 30, 35, 40, 45, 50]//定义每页页数选择下拉框
        });


        //查询按钮
        $("#btnSearch").click(function (){
            var elderName=$("#elderName").val();
            var idNumber=$("#idNumber").val();
            //表格重载
            table.reload('tableRole', {
                where: {
                    elderName:elderName,
                    idNumber:idNumber
                } //设定异步数据接口的额外参数
            });
        });
    })
</script>
</body>
</html>
