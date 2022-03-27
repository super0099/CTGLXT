<%--
  Created by IntelliJ IDEA.
  User: super007
  Date: 2021/9/5
  Time: 19:41
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" scope="page"/>
<html>
<head>
    <title>担保人信息</title>
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
        .layui-container{
            width: 100%;
        }
    </style>
</head>
<body>
<div>
    <ul class="layui-tab-title">
        <li class="home">
            <i class="layui-icon">&#xe607;</i>担保人信息
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
                            <label class="layui-form-label inputLabel">姓名:</label>
                            <div class="layui-input-inline layui-show-xs-block">
                                <input type="text" id="bondsmanName" placeholder="姓名" class="layui-input inputBox">
                            </div>
                        </div>
                        <div class="layui-inline layui-show-xs-block">
                            <label class="layui-form-label inputLabel">手机号:</label>
                            <div class="layui-input-inline layui-show-xs-block">
                                <input type="text" id="phone" placeholder="手机号" class="layui-input inputBox">
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
                    <button class="layui-btn layui-btn-normal" id="btnEdit">
                        <i class="layui-icon layui-icon-edit"></i>修改
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
                                <input type="text" name="bondsmanName" lay-verify="required" class="layui-input dataInput">
                            </div>
                        </div>
                        <div class="layui-inline">
                            <label class="layui-form-label">关系</label>
                            <div class="layui-input-block">
                                <select name="relation" id="relation" lay-verify="required">
                                    <option value="1" class="dataInput">父子</option>
                                    <option value="2" class="dataInput">父女</option>
                                    <option value="3" class="dataInput">母子</option>
                                    <option value="4" class="dataInput">母女</option>
                                    <option value="5" class="dataInput">伴侣</option>
                                </select>
                            </div>
                        </div>
                    </div>
                    <div class="layui-form-item">
                        <div class="layui-inline">
                            <label class="layui-form-label">手机号</label>
                            <div class="layui-input-block">
                                <input type="text" name="phone" id="BPhone" lay-verify="birthday"
                                       class="layui-input dataInput">
                            </div>
                        </div>
                        <div class="layui-inline">
                            <label class="layui-form-label">身份证号</label>
                            <div class="layui-input-block">
                                <input type="text" id="idnumber" name="idnumber" class="layui-input dataInput"
                                       lay-verify="required" lay-filter="departmentIdTreeSelect">
                            </div>
                        </div>
                    </div>
                    <div class="layui-form-item">
                        <div class="layui-inline">
                            <label class="layui-form-label">家庭地址</label>
                            <div class="layui-input-block">
                                <input type="text" id="homeAddress" name="homeAddress" class="layui-input dataInput"
                                       lay-verify="required" lay-filter="departmentIdTreeSelect">
                            </div>
                        </div>
                        <div class="layui-inline">
                            <label class="layui-form-label">工作单位</label>
                            <div class="layui-input-block">
                                <input type="text" id="workUnit" name="workUnit" class="layui-input dataInput"
                                       lay-verify="required" lay-filter="departmentIdTreeSelect">
                            </div>
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
            url: '${ctx}/reception/selectBondsmanAll',//数据url
            page: true,//分页
            cols: [[
                {type: 'radio'},//单选框
                {field: 'bondsmanName', title: '姓名', width: 120,},
                {field: 'id', title: 'id', event: 'id', width: 120, hide: true},
                {field: 'relation', title: '关系',width: 60,templet:function (rowData){
                        if (rowData.relation === 1) {//男
                            return '父子';
                        } else if(rowData.relation === 2){
                            return '父女';
                        }else if(rowData.relation === 3){
                            return '母子';
                        }else if(rowData.relation === 4){
                            return '母女';
                        }else if(rowData.relation === 5){
                            return '伴侣';
                        }

                    }},
                {field: 'idnumber', title: '身份证号', event:'idnumber',},
                {field: 'workUnit', title: '工作单位', event: 'workUnit'},
                {field: 'appointmentId', title: '入住号', event: 'appointmentId',width: 80},
                {field: 'homeAddress', title: '家庭地址', event: 'homeAddress'},
                {field: 'phone', title: '手机号', event: 'phone'}
            ]],
            limit: 5,//每页页数
            limits: [5, 10, 15, 20, 25, 30, 35, 40, 45, 50]//定义每页页数选择下拉框
        });


        //查询按钮
        $("#btnSearch").click(function (){
            var elderName=$("#elderName").val();
            var bondsmanName=$("#bondsmanName").val();
            var phone=$("#phone").val();
            //表格重载
            table.reload('tableRole', {
                where: {
                    elderName:elderName,
                    bondsmanName:bondsmanName,
                    phone:phone
                } //设定异步数据接口的额外参数
            });
        });

        //打开修改弹窗
        $("#btnEdit").click(function (){
            $('#formUser [type="reset"]').click();//重置表单
            //获取table中勾选的数据
            var selectData= table.checkStatus('tableRole');
            if (selectData.data.length>0) {
                var bondsmanId = selectData.data[0].id;
                //加载被修改数据
                $.post('${ctx}/reception/selectBondsmanById',{bondsmanId:bondsmanId},function (jsonMsg){
                    if (jsonMsg.state) {//正常}
                        //回填表单数据
                        form.val("formUser",jsonMsg.data);
                        $("select[name='relationId']").val(jsonMsg.data.relation);
                        layerFormIndex = layer.open({
                            type: 1,//0（信息框，默认）1（页面层）2（iframe层）3（加载层）4（tips层）
                            skin:'layui-layer-molv',
                            area: ['800px', '500px'],
                            title:'修改担保人信息',
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
            var upFormData=new FormData();
            for (var x in fromData.field) {
                //append(名称，值)
                upFormData.append(x,fromData.field[x])
            }
            // 提交表单
            var layerIndex=layer.load();
            $.ajax({
                type: "POST",//文件上传 只能是post
                url: "${ctx}/reception/updateBondsman",
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
                var bondsmanId = selectData.data[0].id;
                var appointmentId = selectData.data[0].appointmentId;
                layer.confirm('您确定要删除该角色吗?', {icon: 3, title:'提示'}, function(index){
                    layer.close(index);//关闭询问框
                    //发送请求
                    var layerIndex=layer.load();//打开加载层
                    $.post('${ctx}/reception/deleteBondsmanById',{bondsmanId:bondsmanId,appointmentId:appointmentId},function (jsonMsg){
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
    });
</script>
</body>
</html>
