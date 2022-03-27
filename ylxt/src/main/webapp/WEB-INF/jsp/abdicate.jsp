<%--
  Created by IntelliJ IDEA.
  User: super007
  Date: 2021/9/18
  Time: 11:27
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" scope="page"/>
<html>
<head>
    <title>退住结算</title>
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
        .layui-input{
            height: 40px;
        }
        .layui-form-label{
            padding: 10px 5px;
        }
        .dataInput{
            color: #01AAED;
        }
    </style>
</head>
<body>
<div class="layui-inline title">
    <ul class="layui-tab-title">
        <li class="home">
            <i class="layui-icon">&#xe6af;</i>退住结算
        </li>
    </ul>
</div>
<span style="margin-left: 5%;color: brown;">解析:针对合同未到期但是中途退住的,并且已经缴费的长者,根据创建订单的日与退住的日期进行比较,住满1~9天按照0.6比例进行退款,住满11~19天的按照0.3比例,住满21~月末的不进行退款</span>
<div class="layui-fluid">
    <div class="layui-row layui-col-space15">
        <div class="layui-col-md12">
            <div class="layui-card">
                <%--表格--%>
                <div class="layui-card-body ">
                    <table class="layui-hide" id="tableCheckOut" lay-filter="tableCheckOut"></table>
                </div>
                <%--操作栏--%>
                <div class="layui-card-header">
                    <button class="layui-btn layui-btn-normal" id="btnEdit">
                        <i class="layui-icon layui-icon-edit"></i>退款操作
                    </button>
                </div>
            </div>
        </div>
    </div>
</div>
<div>
    <form class="layui-form" id="formUser" lay-filter="formUser" style="display: none;margin: 20px;"
          autocomplete="off">
        <%--修改时存放主键id--%>
        <input type="hidden" name="id">
        <div class="layui-container">
            <div class="layui-row">
                <div class="layui-col-md12">
                    <div class="layui-form-item">
                        <div class="layui-inline">
                            <label class="layui-form-label">姓名</label>
                            <div class="layui-input-block">
                                <input type="text" name="elderName" class="layui-input dataInput" readonly>
                            </div>
                        </div>
                        <div class="layui-inline">
                            <label class="layui-form-label">身份证号</label>
                            <div class="layui-input-block">
                                <input type="text" name="elderIdCode" class="layui-input dataInput" readonly>
                            </div>
                        </div>
                    </div>
                    <div class="layui-form-item">
                        <div class="layui-inline">
                            <label class="layui-form-label">账单付款时间</label>
                            <div class="layui-input-block">
                                <input type="text" name="expire" id="expire" lay-verify="birthday"
                                       class="layui-input dataInput" readonly>
                            </div>
                        </div>
                        <div class="layui-inline">
                            <label class="layui-form-label">退住时间</label>
                            <div class="layui-input-block">
                                <input type="text" id="practicalExpire" name="practicalExpire" class="layui-input dataInput" readonly>
                            </div>
                        </div>
                    </div>
                    <div class="layui-form-item">
                        <div class="layui-inline">
                            <label class="layui-form-label">实际收款</label>
                            <div class="layui-input-block">
                                <input type="text" id="amount" name="amount" class="layui-input dataInput" readonly>
                            </div>
                        </div>
                        <div class="layui-inline">
                            <label class="layui-form-label">需退款</label>
                            <div class="layui-input-block">
                                <input type="text" id="needRefund" name="needRefund" class="layui-input dataInput" readonly>
                            </div>
                        </div>
                    </div>
                    <div class="layui-form-item">
                        <div class="layui-inline">
                            <label class="layui-form-label">状态</label>
                            <div class="layui-input-block">
                                <select name="state" disabled class="dataInput">
                                    <option value="1">未办理</option>
                                    <option value="2">已办理</option>
                                </select>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="layui-btn-container" style="text-align: end">
            <button type="submit" class="layui-btn" lay-submit lay-filter="formUserSubmit">退款</button>
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
        var tableCheckOut = table.render({
            elem: '#tableCheckOut',//table元素
            id: 'tableCheckOut',
            url: '${ctx}/abdicate/selectCheckoutAll',//数据url
            page: true,//分页
            cols: [[
                {type: 'radio'},//单选框
                {field: 'elderName', title: '长者姓名'},
                {field: 'id', title: 'id', event: 'id', width: 120, hide: true},
                {field: 'elderId', title: '长者Id',width: 60,hide:true},
                {field: 'expire', title: '账单付款时间', event:'expire',templet:function (rowData){
                        var dt = new Date(rowData.expire);
                        return dt.getFullYear()+"-"+dt.getMonth()+1+"-"+dt.getDate();
                    }},
                {field: 'practicalExpire', title: '退住时间', event: 'practicalExpire',templet:function (rowData){
                        var dt = new Date(rowData.practicalExpire);
                        return dt.getFullYear()+"-"+dt.getMonth()+1+"-"+dt.getDate();
                    }},
                {field: 'amount', title: '实际收款', event: 'amount'},
                {field: 'needRefund', title: '需退款', event: 'needRefund'},
                {field: 'elderIdCode', title: '长者身份证', event: 'elderIdCode'},
                {field: 'state', title: '状态', event: 'state',templet:function (rowData){
                        if (rowData.state === 1) {//男
                            return '未办理';
                        } else if(rowData.state === 2){
                            return '已办理';
                        }
                    }}
            ]],
            limit: 5,//每页页数
            limits: [5, 10, 15, 20, 25, 30, 35, 40, 45, 50]//定义每页页数选择下拉框
        });
        //打开修改弹窗
        $("#btnEdit").click(function (){
            //获取table中勾选的数据
            var selectData= table.checkStatus('tableCheckOut');
            if (selectData.data.length>0) {
                var checkOutId = selectData.data[0].id;
                var CheckState = selectData.data[0].state;
                //加载被修改数据
                $.post('${ctx}/abdicate/selectCheckOutById',{checkOutId:checkOutId,CheckState:CheckState},function (jsonMsg){
                    if (jsonMsg.state) {//正常}
                        //回填表单数据
                        form.val("formUser",jsonMsg.data);
                        $("#expire").val(parsing(jsonMsg.data.expire));
                        $("#practicalExpire").val(parsing(jsonMsg.data.practicalExpire));
                        $("select[name='state']").val(jsonMsg.data.state);
                        layerFormIndex = layer.open({
                            type: 1,//0（信息框，默认）1（页面层）2（iframe层）3（加载层）4（tips层）
                            skin:'layui-layer-molv',
                            area: ['1000px', '500px'],
                            title:'退款信息详细',
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
                layer.msg("请选择退款数据",{icon:5});
            }
        });
        //提交
        form.on('submit(formUserSubmit)', function(fromData){
            console.log(fromData.field)
            var upFormData=new FormData();
            for (var x in fromData.field) {
                //append(名称，值)
                upFormData.append(x,fromData.field[x])
            }
            layer.confirm('你确定需要办理此业务吗?', {icon: 3, title:'提示'}, function(index) {
                layer.close(index);//关闭询问框
                // 提交表单
                var layerIndex=layer.load();
                $.ajax({
                    type: "POST",//文件上传 只能是post
                    url: "${ctx}/abdicate/updateCheckOutById",
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
                            table.reload('tableCheckOut',{});//表格的重载
                        }else{
                            layer.msg(jsonMsg.msg,{icon:5});
                        };
                    }
                });
            })
            return false; //阻止表单跳转。如果需要表单跳转，去掉这段即可。
        });
    });
    function parsing(rowData){
        var date=new Date(rowData);
        return date.getFullYear()+"-"+(date.getMonth()+1)+"-"+date.getDate();
    }
</script>
</body>
</html>
