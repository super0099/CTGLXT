<%--
  Created by IntelliJ IDEA.
  User: super007
  Date: 2021/9/7
  Time: 16:31
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" scope="page"/>
<html>
<head>
    <title>预付款</title>
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
        .layui-table-cell{
            background: #dcedff;
            color: #01AAED;
            border-radius: 3px;
        }
        .recordBut{
            overflow: auto;
        }

    </style>
</head>
<body>
<div class="layui-inline title">
    <ul class="layui-tab-title">
        <li class="home">
            <i class="layui-icon">&#xe65e;</i>预付款
        </li>
    </ul>
</div>
<div class="bigBox">
    <div class="leftBox">
        <div class="leftTitle"><span>待收费</span></div>
        <div class="leftBody">
            <div class="leftBodyTitle layui-row">
                <ul>
                    <li class="layui-col-md1">&nbsp;</li>
                    <li class="layui-col-md4">床位号</li>
                    <li class="layui-col-md3">姓名</li>
                    <li class="layui-col-md4">&nbsp;</li>
                </ul>
            </div>
            <div class="leftBodyBodyBigBox">
                <div class="leftBodyBody layui-row" id="leftBodyBody">

                </div>
            </div>
        </div>
    </div>
    <div class="rightBox">
        <div class="rightBoxTitle">
            <div class="TitleStyle1"><label>入住编号:</label><input type="text" readonly id="checkInCode"></div>
            <div class="TitleStyle1"><label>姓名:</label><input type="text" readonly id="elderName"></div>
            <div class="TitleStyle1"><label>性别:</label><input type="text" readonly id="gender"></div>
            <div class="TitleStyle1"><label>年龄:</label><input type="text" readonly id="age"></div>
            <div class="TitleStyle2"><label>身份证号:</label><input type="text" readonly id="idcode"></div>
            <div class="TitleStyle1"><label>结算方式:</label><input type="text" readonly value="月付"></div>
            <div class="TitleStyle1"><label>民族:</label><input type="text" readonly id="nation"></div>
            <div class="TitleStyle1"><label>籍贯:</label><input type="text" readonly id="nativePlace"></div>
            <div class="TitleStyle1"><label>护理等级:</label><input type="text" readonly id="nurseTypeName"></div>
            <div class="TitleStyle2"><label>入住地点:</label><input type="text" readonly id="checkSite"></div>
            <div class="TitleStyle2"><label>手机号:</label><input type="text" readonly id="homePhone"></div>
            <div class="TitleStyle2"><label>开始时间:</label><input type="text" readonly id="startDate"></div>
            <div class="TitleStyle2"><label>结束时间:</label><input type="text" readonly id="endDate"></div>
        </div>
        <div class="rightBoxBody clearfix">
            <h4>预收款</h4>
            <div class="layui-card-body">
                <table class="layui-hide" id="advancesReceived" lay-filter="advancesReceived"></table>
            </div>
        </div>
        <div class="record">
            <h4>收款记录</h4>
            <div class="layui-card-body recordBut">
                <table class="layui-hide" id="record" lay-filter="record"></table>
            </div>
        </div>
    </div>
</div>
<script src="${ctx}/static/lib/layui/layui.js" charset="UTF-8"></script>
<script src="${ctx}/static/js/jquery.min.js" charset="UTF-8"></script>
<script>
    var layerFormIndex,table;
    layui.use(function(){
        table = layui.table
        var $ = layui.$,
            layer = layui.layer,
            form = layui.form,
            tree=layui.tree;
    });
    $(function (){
        $.post("${ctx}/inform/selectElderInform",{},function (jsonData){
            var leftBodyBody = document.getElementById("leftBodyBody");
            var leftBodyBodyChild = leftBodyBody.childNodes;
            for (var i=leftBodyBodyChild.length-1;i >= 0; i--){
                leftBodyBody.removeChild(leftBodyBodyChild[i]);
            }

            for (var i =0;i<jsonData.length;i++){
                leftBodyBody.innerHTML+='<ul onclick="selectElder('+jsonData[i].id+')" class="clearfix"><li class="layui-col-md1"><i class="layui-icon">&#xe602;</i></li><li class="layui-col-md4">'+jsonData[i].berthId+'</li><li class="layui-col-md3">'+jsonData[i].elderName+'</li><li class="layui-col-md4">&nbsp;</li></ul>';
            }
        })
    });
    function selectElder(elderId){
        $.post("${ctx}/cost/selectElderData",{
            elderId:elderId
        },function (jsonData){
            $("#checkInCode").val(jsonData.checkInCode);
            $("#elderName").val(jsonData.elderName);
            if(jsonData.gender==1){
                $("#gender").val("男");
            }else {
                $("#gender").val("女");
            }

            $("#age").val(jsonData.age);
            $("#idcode").val(jsonData.idcode);
            $("#nation").val(jsonData.nation);
            $("#nativePlace").val(jsonData.nativePlace);
            $("#nurseTypeName").val(jsonData.nurseTypeName);
            $("#checkSite").val(jsonData.checkSite);
            $("#homePhone").val(jsonData.homePhone);
            $("#startDate").val(parsing(jsonData.startDate));
            $("#endDate").val(parsing(jsonData.endDate));
        })
        // 菜单树形table初始化
        var tableRole = table.render({
            elem: '#advancesReceived',//table元素
            id: 'advancesReceived',
            url: '${ctx}/cost/selectElderPayment?elderId='+elderId,//数据url
            cols: [[
                {field: 'alimony',title: '设施费',width: 110},
                {field: 'boardWages', title: '伙食费', width: 110},
                {field: 'berth', title: '床位费', event: 'berth', width: 110},
                {field: 'serviceCharge', title: '护理费',width: 110,},
                {field: 'totalMoney', title: '总金额', event: 'totalMoney',width: 140},
                {field: 'practical', title: '应付款', event: 'practical',width: 140},
                {field: 'state', title: '状态', event: 'state',width: 80,templet:function (rowData){
                    if(rowData.state==1){
                        return "未付款";
                    }
                    if (rowData.state==2){
                        return "已付款";
                    }
                    }},
                {field: 'remark', title: '备注', event: 'remark', width: 130},
                {field: 'clearing',title: '订单生成时间', event: 'clearing',width: 160,templet:function (rowData){
                        var dt = new Date(rowData.clearing);
                        return dt.getFullYear() + "-" + (dt.getMonth() + 1) + "-" + dt.getDate();
                    }}
            ]]
        });

        var tableRole = table.render({
            elem: '#record',//table元素
            id: 'record',
            url: '${ctx}/cost/selectElderAllSettle?elderId='+elderId,//数据url
            cols: [[
                {field: 'alimony',title: '设施费',width: 110},
                {field: 'boardWages', title: '伙食费', width: 110,},
                {field: 'berth', title: '床位费', event: 'berth', width: 110},
                {field: 'serviceCharge', title: '护理费',width: 110,},
                {field: 'totalMoney', title: '总金额', event: 'birthday',width: 140},
                {field: 'practical', title: '应付款', event: 'idnumber',width: 140},
                {field: 'state', title: '状态', event: 'state',width: 80,templet:function (rowData){
                        if(rowData.state==1){
                            return "未付款";
                        }
                        if (rowData.state==2){
                            return "已付款";
                        }
                    }},
                {field: 'portrait', title: '备注', event: 'portrait', width: 130},
                {field: 'paymentDate', title: '收费时间', event: 'paymentDate',width: 170,templet:function (rowData){
                        var dt = new Date(rowData.paymentDate);
                        return dt.getFullYear() + "-" + (dt.getMonth() + 1) + "-" + dt.getDate();
                    }}
            ]]
        });
    }
    function parsing(rowData){
        var date=new Date(rowData);
        return date.getFullYear()+"-"+(date.getMonth()+1)+"-"+date.getDate();
    }
</script>
</body>
</html>
