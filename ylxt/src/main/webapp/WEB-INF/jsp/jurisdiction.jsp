<%--
  Created by IntelliJ IDEA.
  User: super007
  Date: 2021/9/23
  Time: 8:39
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" scope="page"/>
<html>
<head>
    <title>权限管理</title>
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
        .dataInput{
            height: 45px;
        }
        .layui-form-label{
            padding: 11px 5px;
        }
        .layui-input{
            height: 45px;
        }
        .layui-layer-content{
            width: 100%;
        }
        .layui-container{
            width: 100%;
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
<%--主容器布局--%>
<div class="layui-fluid">
    <div class="layui-row layui-col-space15">
        <div class="layui-col-md12">
            <div class="layui-card">
                <%--搜索栏--%>
                <div class="layui-card-body ">
                    <form class="layui-form layui-col-space5">
                        <div class="layui-inline layui-show-xs-block">
                            <label class="layui-form-label">角色名称</label>
                            <div class="layui-input-inline layui-show-xs-block">
                                <input type="text" id="positionName" placeholder="角色名称" class="layui-input">
                            </div>
                        </div>
                        <div class="layui-input-inline layui-show-xs-block">
                            <button class="layui-btn" type="button" id="btnSearch">
                                <i class="layui-icon layui-icon-search"></i>
                            </button>
                        </div>
                    </form>
                </div>
                <%--操作栏--%>
                <div class="layui-card-header">
                    <button class="layui-btn" id="btnAdd">
                        <i class="layui-icon layui-icon-addition"></i>添加
                    </button>
                    <button class="layui-btn layui-btn-normal" id="btnEdit">
                        <i class="layui-icon layui-icon-edit"></i>修改
                    </button>
                    <button class="layui-btn layui-btn-danger" id="btnDelete">
                        <i class="layui-icon layui-icon-delete"></i>删除
                    </button>
                    <button class="layui-btn layui-btn-normal" id="btnAuthorize">
                        <i class="layui-icon layui-icon-transfer"></i>授权
                    </button>
                </div>
                <%--表格--%>
                <div class="layui-card-body ">
                    <table class="layui-hide" id="tableRole" lay-filter="tableRoleEvent"></table>
                </div>
            </div>
        </div>
    </div>
</div>
<%--表单弹窗--%>
<div>
    <form class="layui-form" id="formRole" lay-filter="formRole" style="display: none;margin: 20px;"
          autocomplete="off">
        <%--标识是新增1还是修改0--%>
        <input type="hidden" name="isInsert" value="1">
        <%--修改时存放主键id--%>
        <input type="hidden" name="id">
        <div class="layui-form-item">
            <label class="layui-form-label">角色名称</label>
            <div class="layui-input-block">
                <input type="text" name="positionName" lay-verify="required" class="layui-input">
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">创建时间</label>
            <div class="layui-input-block">
                <input type="text" name="createDate" lay-verify="required" class="layui-input" id="createDate">
            </div>
        </div>
        <div class="layui-btn-container" style="text-align: end">
            <button type="button" class="layui-btn" id="btnSubmit">提交</button>
            <button type="reset" class="layui-btn layui-btn-warm">重置</button>
        </div>
    </form>

    <%--授权的表单--%>
    <form class="layui-form" id="formAuthorize" lay-filter="formAuthorize" style="display: none;margin: 20px;">
        <div class="layui-form-item layui-form-text">
            <label class="layui-form-label">勾选权限</label>
            <div class="layui-input-block">
                <%--layui tree的位置--%>
                <div id="treeAuthorize" class="demo-tree-more"></div>
            </div>
        </div>
    </form>
</div>
<script src="${ctx}/static/lib/layui/layui.js" charset="utf-8"></script>
<script src="${ctx}/static/js/myUtils.js"></script>
<script>
    var layerFormIndex;
    layui.use(function(){
        var table = layui.table,
            $ = layui.$,
            layer = layui.layer,
            form = layui.form,
            laydate = layui.laydate,
            tree=layui.tree;
        //表单 日期控件
        laydate.render({
            elem: '#createDate', //指定元素
            type: 'date'
        });
        // 菜单树形table初始化
        var tableRole = table.render({
            elem: '#tableRole',//table元素
            id: 'tableRole',
            url: '${ctx}/jurisdiction/selectPageList',//数据url
            page: true,//分页
            cols: [[
                {type: 'radio'},//单选框
                {field: 'positionName', title: '职位名称'},
                {field: 'id', title: 'id', event: 'id', width: 120, hide: true},
                {
                    field: 'createDate', title: '创建时间', event: 'createDate',templet:function (rowData){
                        var date=new Date(rowData.createDate);
                        return date.getFullYear()+"-"+(date.getMonth()+1)+"-"+date.getDate();
                    }
                }
            ]],
            limit: 10,//每页页数
            limits: [5, 10, 15, 20, 25, 30, 35, 40, 45, 50]//定义每页页数选择下拉框
        });


        //查询按钮
        $("#btnSearch").click(function (){
            var positionName=$("#positionName").val();
            //表格重载
            table.reload('tableRole', {
                where: {
                    positionName:positionName
                } //设定异步数据接口的额外参数
            });
        });

        //打开新增弹窗
        $("#btnAdd").click(function (){
            $('#formRole [type="reset"]').click();//重置表单
            $('#formRole [name="isInsert"]').val(1);//设置为新增

            layerFormIndex = layer.open({
                type: 1,//0（信息框，默认）1（页面层）2（iframe层）3（加载层）4（tips层）
                skin:'layui-layer-molv',
                area:['500px','420px'],
                title:'新增职位',
                content:$("#formRole"),
                cancel:function (){
                    layer.closeAll();
                }
            });
        });

        //打开修改弹窗（表单回填）
        $("#btnEdit").click(function (){
            $('#formRole [type="reset"]').click();//重置表单
            $('#formRole [name="isInsert"]').val(0);//设置为修改
            //获取table中勾选的数据
            var selectData= table.checkStatus('tableRole');
            if (selectData.data.length>0){
                var positionId=selectData.data[0].id;
                //加载被修改数据
                $.post('${ctx}/jurisdiction/selectById',{positionId:positionId},function (jsonMsg){
                    if (jsonMsg.state){//正常
                        //数据回填
                        $('#formRole [name="id"]').val(jsonMsg.data.id);
                        $('#formRole [name="positionName"]').val(jsonMsg.data.positionName);
                        $('#formRole [name="createDate"]').val(parsing(jsonMsg.data.createDate));
                        form.render(); //更新全部
                        //打开修改弹窗
                        layerFormIndex = layer.open({
                            type: 1,//0（信息框，默认）1（页面层）2（iframe层）3（加载层）4（tips层）
                            skin:'layui-layer-molv',
                            area:['500px','420px'],
                            title:'修改职位',
                            content:$("#formRole"),
                            cancel:function (){
                                layer.closeAll();
                            }
                        });
                    }else {
                        layer.msg(jsonMsg.msg,{icon:5});
                    }
                });
            }else {
                layer.msg("请选择需要修改的数据");
            }
        });



        //提交表单
        $("#btnSubmit").click(function (){
            //判断是新增还是修改 指定不同url
            var isInsert=$('#formRole [name="isInsert"]').val();
            var url="";
            if (isInsert=="1"){
                url='${ctx}/jurisdiction/insert';
            }else {
                url='${ctx}/jurisdiction/update';
            }

            //获取表单值
            var id=$('#formRole [name="id"]').val();
            var createDate=$('#formRole [name="createDate"]').val();
            var positionName=$('#formRole [name="positionName"]').val();
            if (positionName===undefined || positionName===null || positionName===""){
                layer.msg("请输入角色名称",{icon:5});
                return;
            }
            if (createDate===undefined || createDate===null){
                layer.msg("请输入创建时间",{icon:5});
                return;
            }
            //表单提交
            layerIndex=layer.load();//打开加载层
            $.post(url,{
                id:id,
                positionName:positionName,
                createDate:createDate,
            },function (jsonMsg){
                layer.close(layerIndex);//关闭加载层
                if (jsonMsg.state){
                    layer.msg(jsonMsg.msg,{icon:6});
                    layer.close(layerFormIndex);//关闭弹窗
                    table.reload('tableRole',{});//表格的重载
                }else{
                    layer.msg(jsonMsg.msg,{icon:5});
                }
            });

        });


        //删除
        $("#btnDelete").click(function (){
            //获取table中勾选的数据
            var selectData= table.checkStatus('tableRole');
            if (selectData.data.length>0){
                var positionId=selectData.data[0].id;
                layer.confirm('您确定要删除该角色吗?', {icon: 3, title:'提示'}, function(index){
                    layer.close(index);//关闭询问框
                    //发送请求
                    var layerIndex=layer.load();//打开加载层
                    $.post('${ctx}/jurisdiction/deleteById',{positionId:positionId},function (jsonMsg){
                        layer.close(layerIndex);//关闭加载层
                        if (jsonMsg.state){//正常
                            layer.msg(jsonMsg.msg,{icon:6});
                            table.reload('tableRole',{});//表格的重载
                        }else {
                            layer.msg(jsonMsg.msg,{icon:5});
                        }
                    });
                });
            }else {
                layer.msg("请选择需要删除的数据");
            }
        });


        //权限授权
        $("#btnAuthorize").click(function () {
            var selectData = table.checkStatus('tableRole'); //idTest 即为基础参数 id 对应的值
            if (selectData.data.length > 0) {
                //选中的角色id
                var selectRoleId = selectData.data[0].id;
                $.post('${ctx}/jurisdiction/selectMenuForLayuiTree',{positionId:selectRoleId}, function (jsonMsg) {
                    console.log(jsonMsg)
                    tree.render({
                        elem: '#treeAuthorize',  //绑定元素
                        id: 'treeAuthorize',
                        data: jsonMsg.data,
                        showCheckbox: true//显示复选框
                    });
                    layerForm = layer.open({
                        type: 1,
                        skin: 'layui-layer-molv',
                        area: ['500px', '610px'],
                        title: '授权',
                        content: $("#formAuthorize"),//表单
                        cancel: function (layero, index) {
                            layer.closeAll();
                        },
                        btn: ['确定', '取消'],//自定义的按钮
                        yes: function (index, layero) {//确定按钮的点击事件
                            //获得选中的节点
                            var checkData = tree.getChecked('treeAuthorize');
                            var selectMenuIds = getLayuiTreeCheckId(checkData);
                            //发送网络请求
                            var layerIndex = layer.load();//打开加载层
                            $.post('${ctx}/jurisdiction/updateRoleAuthorize', {
                                    roleId: selectRoleId,//角色id
                                    selectMenuIds: selectMenuIds.join()//把数组拼接为字符串 默认使用 , 分割
                                },
                                function (jsonMsg) {
                                    layer.close(index);//关闭表单弹窗
                                    layer.close(layerIndex);//关闭加载层
                                    if (jsonMsg.state) {//成功
                                        layer.msg(jsonMsg.msg, {icon: 1});
                                    } else {//失败
                                        layer.msg(jsonMsg.msg, {icon: 2});
                                    }
                                }, 'json');
                        },
                        btn2: function (index, layero) {//第二个按钮（取消）的点击事件
                            layer.close(index);
                        }
                    });
                }, 'json');
            } else {
                layer.msg("请选择要授权的角色", {icon: 0});
            }
        });

        function parsing(rowData){
            var date=new Date(rowData);
            return date.getFullYear()+"-"+(date.getMonth()+1)+"-"+date.getDate();
        }
    });
</script>
</body>
</html>
