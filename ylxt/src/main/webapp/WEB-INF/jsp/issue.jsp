<%--
  Created by IntelliJ IDEA.
  User: super007
  Date: 2021/9/12
  Time: 19:49
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" scope="page"/>
<html>
<head>
    <title>发布信息</title>
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
        .clearfix:after {
            content: "";
            display: block;
            clear: both;
        }
        .clearfix {
            zoom: 1;
        }
        .character{
            width: 100%;
            height: 85%;
        }
        .billTable{
            width: 50%;
            float: left;
        }
        .operation button{
            float: right;
        }
        .rightTextarea h4{
            padding: 12px;
            font-size: 16px;
            color: #01AAED;
        }
        .rightTextarea textarea{
            resize: none;
            width: 47%;
            height: 352px;
            color: #01AAED;
        }
        .textareaBtn{
            padding: 12px;
        }
        .textareaBtn button{
            float: right;
        }
    </style>
</head>
<body>
<div>
    <ul class="layui-tab-title">
        <li class="home">
            <i class="layui-icon">&#xe63a;</i>发布信息
        </li>
    </ul>
</div>
<div class="character clearfix">
    <div class="layui-card-body billTable">
        <h4 style="color: #01AAED">历史信息</h4>
        <table class="layui-hide" id="character" lay-filter="character"></table>
        <div class="layui-card-header operation">
            <button class="layui-btn layui-btn-danger" id="btnDelete">
                <i class="layui-icon layui-icon-delete"></i>删除
            </button>
        </div>
    </div>
    <div class="rightTextarea">
        <h4>发布信息</h4>
        <textarea id="rightCharacter" placeholder="请输入需要发布的信息"></textarea>
        <div class="textareaBtn">
            <button class="layui-btn layui-btn-group" id="btnAdd">
                <i class="layui-icon layui-icon-release"></i>发布
            </button>
        </div>
    </div>
</div>
<script src="${ctx}/static/lib/layui/layui.js" charset="UTF-8"></script>
<script src="${ctx}/static/js/jquery.min.js" charset="UTF-8"></script>
<script>
    layui.use(function () {
        var $ = layui.$,
            layer = layui.layer,
            form = layui.form,
            tree = layui.tree,
            table = layui.table;
        var tableRole = table.render({
            elem: '#character',//table元素
            id: 'character',
            url: '${ctx}/system/selectIssueAll',//数据url
            page:true,
            cols: [[
                {type:'radio'},
                {field: 'id', title: 'id', event: 'id',hide: true},
                {field: 'content',title: '内容信息'},
                {field: 'releaseDate',title: '发布时间', event: 'releaseDate',templet:function (rowData){
                        var dt = new Date(rowData.releaseDate);
                        console.log(rowData)
                        return dt.getFullYear() + "-" + (dt.getMonth() + 1) + "-" + dt.getDate()+" "+
                            dt.getHours()+":"+dt.getMinutes()+":"+dt.getSeconds();
                    }}
            ]],
            limit: 10,//每页页数
            limits: [5, 10]//定义每页页数选择下拉框
        });
        //删除
        $("#btnDelete").click(function (){
            //获取table中被勾选的数据
            var selectData = table.checkStatus("character");
            if(selectData.data.length>0){
                var issueId = selectData.data[0].id;
                $.post("${ctx}/system/deleteIssueById",{
                    issueId:issueId
                },function (jsonData){
                    if(jsonData.state){
                        layer.msg(jsonData.msg,{icon:6});
                        table.reload('character',{});//表格的重载
                    }else {
                        layer.msg(jsonData.msg,{icon:5});
                    }
                });
            }else {
                layer.msg("请选择需要删除的数据");
            }
        })
        //发布
        $("#btnAdd").click(function (){
            var rightCharacter = $("#rightCharacter").val();
            if(rightCharacter==null||rightCharacter==""||rightCharacter==undefined){
                layer.msg("请输入需要发布的信息内容");
                return;
            }
            $.post("${ctx}/system/insertIssue",{
                rightCharacter:rightCharacter
            },function (jsonData){
                if(jsonData.state){
                    $("#rightCharacter").val("");
                    layer.msg(jsonData.msg,{icon:6});
                    table.reload('character',{});//表格的重载
                }else {
                    layer.msg(jsonData.msg,{icon:5});
                }
            })
        })
    });
</script>
</body>
</html>
