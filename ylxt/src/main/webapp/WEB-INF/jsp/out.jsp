<%--
  Created by IntelliJ IDEA.
  User: super007
  Date: 2021/9/22
  Time: 14:43
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" scope="page"/>
<html>
<head>
    <title>外出登记</title>
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
<div class="layui-inline title">
    <ul class="layui-tab-title">
        <li class="home">
            <i class="layui-icon">&#xe6b2;</i>外出登记
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
                            <label class="layui-form-label inputLabel">长者姓名:</label>
                            <div class="layui-input-inline layui-show-xs-block">
                                <input type="text" placeholder="长者姓名" class="layui-input inputBox" id="elderName">
                            </div>
                        </div>
                        <div class="layui-inline layui-show-xs-block">
                            <label class="layui-form-label inputLabel">外出时间:</label>
                            <div class="layui-input-inline layui-show-xs-block">
                                <input type="text" id="outTimes" placeholder="登记时间" class="layui-input inputBox">
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
                    <button class="layui-btn layui-btn-normal" id="btnAdd">
                        <i class="layui-icon layui-icon-edit"></i>外出登记
                    </button>
                    <button class="layui-btn layui-btn-normal" id="btnEdit">
                        <i class="layui-icon layui-icon-edit"></i>回来登记
                    </button>
                    <button class="layui-btn layui-btn-danger" id="btnDelete">
                        <i class="layui-icon layui-icon-delete"></i>删除
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
<div>
    <form class="layui-form" id="formUser" lay-filter="formUser" style="display: none;margin: 20px;"
          autocomplete="off">
        <%--标识是新增1还是修改0--%>
        <input type="hidden" name="isInsert" value="1">
        <%--修改时存放主键id--%>
        <input type="hidden" name="id">
        <div class="layui-container">
            <div class="layui-row">
                <div class="layui-col-md12">
                    <div class="layui-form-item">
                        <div class="layui-inline">
                            <label class="layui-form-label">姓名</label>
                            <div class="layui-input-block">
                                <input type="text" name="elderName" lay-verify="required" class="layui-input dataInput">
                            </div>
                        </div>
                        <div class="layui-inline">
                            <label class="layui-form-label">身份证号</label>
                            <div class="layui-input-block">
                                <input type="text" id="elderIdCode" name="elderIdCode" class="layui-input dataInput"
                                       lay-verify="required" lay-filter="departmentIdTreeSelect">
                            </div>
                        </div>
                    </div>
                    <div class="layui-form-item">
                        <div class="layui-inline">
                            <label class="layui-form-label">外出时间</label>
                            <div class="layui-input-block">
                                <input type="text" id="outTime" name="outTime" class="layui-input dataInput"
                                       lay-verify="required" lay-filter="departmentIdTreeSelect">
                            </div>
                        </div>
                        <div class="layui-inline">
                            <label class="layui-form-label">回来时间</label>
                            <div class="layui-input-block">
                                <input type="text" id="comeBack" name="comeBack" class="layui-input dataInput"
                                       lay-verify="required" lay-filter="departmentIdTreeSelect">
                            </div>
                        </div>
                    </div>
                    <div class="layui-form-item layui-form-text">
                        <label class="layui-form-label">外出理由</label>
                        <div class="layui-input-block">
                            <textarea name="reason" placeholder="请输入外出理由（不可留空）" class="layui-textarea"></textarea>
                        </div>
                    </div>

                </div>
            </div>
        </div>
        <div class="layui-btn-container" style="text-align: end">
            <button type="submit" class="layui-btn" lay-submit lay-filter="formUserSubmit">提交</button>
            <button type="reset" class="layui-btn layui-btn-warm">重置</button>
        </div>
    </form>
</div>
<script src="${ctx}/static/js/jquery.min.js" charset="UTF-8"></script>
<script src="${ctx}/static/lib/layui/layui.js" charset="UTF-8"></script>
<script>
    var layerFormIndex;
    layui.use(function() {
        var table = layui.table,
            $ = layui.$,
            laydate = layui.laydate,
            layer = layui.layer,
            form = layui.form,
            tree = layui.tree;
        //表单 日期控件
        laydate.render({
            elem: '#outTimes', //指定元素
            type: 'date'
        });
        //表单 日期控件
        laydate.render({
            elem: '#outTime', //指定元素
            type: 'date'
        });
        laydate.render({
            elem: '#comeBack', //指定元素
            type: 'date'
        });
        // 菜单树形table初始化
        var tableRole = table.render({
            elem: '#tableRole',//table元素
            id: 'tableRole',
            url: '${ctx}/out/selectOutAll',//数据url
            page: true,//分页
            cols: [[
                {type: 'radio'},//单选框
                {field: 'elderName', title: '姓名',width: 80},
                {field: 'id', title: 'id', event: 'id', hide: true},
                {field: 'elderIdCode', title: '身份证号', event: 'elderIdCode',width: 180},
                {field: 'outTime', title: '外出时间', event: 'outTime', width: 160, templet: function (rowData) {
                        var dt = new Date(rowData.outTime);
                        return dt.getFullYear() + "-" + (dt.getMonth() + 1) + "-" + dt.getDate()+" "+dt.getHours()+":"+dt.getMinutes()+":"+dt.getSeconds();
                    }
                },
                {field: 'comeBack', title: '归来时间', event: 'comeBack', width: 160, templet: function (rowData) {
                        var dt = new Date(rowData.comeBack);
                        return dt.getFullYear() + "-" + (dt.getMonth() + 1) + "-" + dt.getDate()+" "+dt.getHours()+":"+dt.getMinutes()+":"+dt.getSeconds();
                    }
                },
                {field: 'reason', title: '外出理由', event: 'reason'}
            ]],
            limit: 10,//每页页数
            limits: [5, 10, 15, 20, 25, 30, 35, 40, 45, 50]//定义每页页数选择下拉框
        });

        $("#btnSearch").click(function () {
            //获取搜索表单的数据
            var elderName = $("#elderName").val();
            var idCode = $("#idCode").val();
            //表格重载
            table.reload('tableWarn', {
                where:{
                    elderName: elderName,
                    idCode:idCode
                }
            });
        });

        //打开新增弹窗
        $("#btnAdd").click(function (){
            $('#formUser [type="reset"]').click();//重置表单
            $('#formUser [name="isInsert"]').val(1);//设置为新增
            layerFormIndex = layer.open({
                type: 1,//0（信息框，默认）1（页面层）2（iframe层）3（加载层）4（tips层）
                skin:'layui-layer-molv',
                area: ['800px', '500px'],
                title:'登记外出',
                content:$("#formUser"),
                cancel:function (){
                    layer.closeAll();
                }
            });
        });

        //打开修改弹窗
        $("#btnEdit").click(function (){
            $('#formUser [type="reset"]').click();//重置表单
            $('#formUser [name="isInsert"]').val(0);//设置为修改
            //获取table中勾选的数据
            var selectData= table.checkStatus('tableRole');
            if (selectData.data.length>0) {
                var outId = selectData.data[0].id;
                //加载被修改数据
                $.post('${ctx}/out/selectById',{outId:outId},function (jsonMsg){
                    if (jsonMsg.state) {//正常}
                        //回填表单数据
                        form.val("formUser",jsonMsg.data);
                        $("#outTime").val(parsing(jsonMsg.data.outTime));
                        $("#comeBack").val(parsing(jsonMsg.data.comeBack));
                        //表单 日期控件
                        layerFormIndex = layer.open({
                            type: 1,//0（信息框，默认）1（页面层）2（iframe层）3（加载层）4（tips层）
                            skin:'layui-layer-molv',
                            area: ['800px', '500px'],
                            title:'',
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



        //新增、修改表单提交事件  submit(提交按钮的lay-filter属性的值)
        form.on('submit(formUserSubmit)', function(fromData){
            //判断是新增 还是修改
            var url="";
            if (fromData.field.isInsert==="1"){//新增
                url='${ctx}/out/insert';
            }else {//修改
                url='${ctx}/out/update';
            }

            var upFormData=new FormData();
            for (var x in fromData.field) {
                //append(名称，值)
                upFormData.append(x,fromData.field[x])
            }
            // 提交表单
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
                        table.reload('tableRole',{});//表格的重载
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
            var selectData= table.checkStatus('tableRole');
            if (selectData.data.length>0){
                var outId=selectData.data[0].id;
                layer.confirm('您确定要删除该数据吗?', {icon: 3, title:'提示'}, function(index){
                    layer.close(index);//关闭询问框
                    //发送请求
                    var layerIndex=layer.load();//打开加载层
                    $.post('${ctx}/out/deleteById',{outId:outId},function (jsonMsg){
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
    })
    function parsing(rowData){
        var date=new Date(rowData);
        return date.getFullYear()+"-"+(date.getMonth()+1)+"-"+date.getDate();
    }
</script>
</body>
</html>
