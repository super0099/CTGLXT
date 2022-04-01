<%--
  Created by IntelliJ IDEA.
  User: super007
  Date: 2021/9/25
  Time: 15:53
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" scope="page"/>
<html>
<head>
    <title>Title</title>
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
        .layui-table-cell .layui-form-radio[lay-type="layTableRadio"]{
            top: 50%;
            transform: translateY(-50%);
        }
        .dataInput{
            height: 45px;
        }
        .layui-form-label{
            padding: 11px 5px;
        }
        .layui-container{
            width: 100%;
        }
        .layui-unselect{
            height: 45px;
        }
    </style>
</head>
<body>
<div>
    <ul class="layui-tab-title">
        <li class="home">
            <i class="layui-icon">&#xe656;</i>护理套餐维护
        </li>
    </ul>
</div>
<div class="layui-fluid bigBox">
    <div class="layui-row layui-col-space15">
        <div class="layui-col-md12">
            <div class="layui-card">
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
                </div>
                <%--表格--%>
                <div class="layui-card-body ">
                    <table class="layui-hide" id="tableElder" lay-filter="tableRoleEvent"></table>
                </div>
            </div>
        </div>
    </div>
</div>
<div>
    <form class="layui-form" id="formUser" lay-filter="formUser" style="display: none;margin: 20px;"
          autocomplete="off">
        <input type="hidden" name="isInsert" value="1">
        <%--修改时存放主键id--%>
        <input type="hidden" name="id">
        <div class="layui-container">
            <div class="layui-form-item">
                <div class="layui-inline">
                    <label class="layui-form-label">护理类型名称</label>
                    <div class="layui-input-block">
                        <input type="text" name="nurseType" lay-verify="required" class="layui-input dataInput">
                    </div>
                </div>
                <div class="layui-inline">
                    <label class="layui-form-label">设施费</label>
                    <div class="layui-input-block">
                        <input type="number" id="alimony" name="alimony" class="layui-input dataInput"
                               lay-verify="required" lay-filter="departmentIdTreeSelect">
                    </div>
                </div>
            </div>
            <div class="layui-form-item">
                <div class="layui-inline">
                    <label class="layui-form-label">床位费</label>
                    <div class="layui-input-block">
                        <input type="number" name="berth" class="layui-input dataInput">
                    </div>
                </div>
                <div class="layui-inline">
                    <label class="layui-form-label">伙食费</label>
                    <div class="layui-input-block">
                        <input type="number" name="boardWages" class="layui-input dataInput">
                    </div>
                </div>
            </div>
            <div class="layui-form-item">
                <div class="layui-inline">
                    <label class="layui-form-label">护理费</label>
                    <div class="layui-input-block">
                        <input type="number" name="serviceCharge" class="layui-input dataInput">
                    </div>
                </div>
            </div>
            </div>
            <div class="layui-btn-container" style="text-align: end">
                <button type="submit" class="layui-btn" lay-submit lay-filter="formUserSubmit">提交</button>
                <button type="reset" class="layui-btn layui-btn-warm">重置</button>
            </div>
        </div>
    </form>
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
            elem: '#tableElder',//table元素
            id: 'tableElder',
            url: '${ctx}/maintain/selectNurseTypeAll',//数据url
            page: true,//分页
            cols: [[
                {type: 'radio'},//单选框
                {field: 'nurseType', title: '护理类型名称'},
                {field: 'id', title: 'id', event: 'id', width: 120, hide: true},
                {field: 'alimony', title: '生活设施费', event:'alimony'},
                {field: 'berth', title: '床位费', event: 'berth'},
                {field: 'boardWages', title: '伙食费', event: 'boardWages'},
                {field: 'serviceCharge', title: '护理费', event: 'serviceCharge'},
            ]],
            limit: 5,//每页页数
            limits: [5, 10, 15, 20, 25, 30, 35, 40, 45, 50]//定义每页页数选择下拉框
        });
        //打开新增弹窗
        $("#btnAdd").click(function (){
            $('#formUser [type="reset"]').click();//重置表单
            //清空文件选择框
            $('#formUser [name="isInsert"]').val(1);//设置为新增
            layerFormIndex = layer.open({
                type: 1,//0（信息框，默认）1（页面层）2（iframe层）3（加载层）4（tips层）
                skin:'layui-layer-molv',
                area: ['800px', '400px'],
                title:'新增护理类型',
                content:$("#formUser"),
                cancel:function (){
                    layer.closeAll();
                }
            });
        });
        //打开修改弹窗
        $("#btnEdit").click(function (){
            //清空文件选择框
            $('#formUser [type="reset"]').click();//重置表单
            $('#formUser [name="isInsert"]').val(0);//设置为新增
            //获取table中勾选的数据
            var selectData= table.checkStatus('tableElder');
            if (selectData.data.length>0) {
                var userId = selectData.data[0].id;
                //加载被修改数据
                $.post('${ctx}/maintain/selectUserById',{id:userId},function (jsonMsg){
                    if (jsonMsg.state) {//正常}
                        //回填表单数据
                        form.val("formUser",jsonMsg.data);
                        layerFormIndex = layer.open({
                            type: 1,//0（信息框，默认）1（页面层）2（iframe层）3（加载层）4（tips层）
                            skin:'layui-layer-molv',
                            area: ['900px', '500px'],
                            title:'修改护理信息',
                            content:$("#formUser"),
                            cancel:function (){
                                layer.closeAll();
                            }
                        });
                    }else {
                        layer.msg(jsonMsg.msg,{icon:5});
                    }
                });
            }else {
                layer.msg("请选择需要修改的数据",{icon:5});
            }
        });
        //提交
        form.on('submit(formUserSubmit)', function(fromData){
            var url="";
            if (fromData.field.isInsert==="1"){//新增
                url='${ctx}/maintain/insert';
            }else {//修改
                url='${ctx}/maintain/update';
            }
            var upFormData=new FormData();
            for (var x in fromData.field) {
                //append(名称，值)
                upFormData.append(x,fromData.field[x])
            }
            var layerIndex=layer.load();
            $.ajax({
                type: "POST",//文件上传 只能是post
                url: url,
                data: upFormData,
                cache:false,
                processData:false,//禁止jquery对上传的数据进行处理
                contentType: false,
                dataType:'json',
                success: function(jsonMsg){
                    layer.close(layerIndex);
                    if (jsonMsg.state){
                        layer.msg(jsonMsg.msg,{icon:6});
                        layer.close(layerFormIndex);//关闭弹窗
                        table.reload('tableElder',{});//表格的重载
                    }else{
                        layer.msg(jsonMsg.msg,{icon:5});
                    };
                }
            });
            return false; //阻止表单跳转。如果需要表单跳转，去掉这段即可。
        });

        //删除
        $("#btnDelete").click(function (){
            //获取table中勾选的数据
            var selectData= table.checkStatus('tableElder');
            if (selectData.data.length>0){
                var userId=selectData.data[0].id;
                layer.confirm('您确定要删除该数据吗?', {icon: 3, title:'提示'}, function(index){
                    layer.close(index);//关闭询问框
                    //发送请求
                    var layerIndex=layer.load();//打开加载层
                    $.post('${ctx}/maintain/delete',{id:userId},function (jsonMsg){
                        layer.close(layerIndex);//关闭加载层
                        if (jsonMsg.state){//正常
                            layer.msg(jsonMsg.msg,{icon:6});
                            table.reload('tableElder',{});//表格的重载
                        }else {
                            layer.msg(jsonMsg.msg,{icon:5});
                        }
                    });
                });
            }else {
                layer.msg("请选择需要删除的数据");
            }
        });

    });
</script>
</body>
</html>
