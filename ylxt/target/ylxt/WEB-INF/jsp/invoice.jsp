<%--
  Created by IntelliJ IDEA.
  User: super007
  Date: 2021/9/11
  Time: 18:35
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" scope="page"/>
<html>
<head>
    <title>发票管理</title>
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
            height: 99.5%;
        }
        .invoiceImgBig{
            position: absolute;
            width: 100%;
            height: 100%;
            left: 0;
            top: 0;
            z-index: 10;
            background: #FFFFFF;
        }
        .invoiceImg{
            margin-top: 5%;
            width: 70%;
            height: 80%;
            margin-left: 15%;
            position: relative;
        }
        .invoiceImg img{
            width: 100%;
            height: 100%;
        }
        .dataInput{
            position: absolute;
            width: 10%;
            height: 18px;
        }
        .dataInputs{
            width: 100%;
            height: 100%;
            border: none;
            outline: none;
            color: #01AAED;
            font-size: 14px;
            text-align: center;
        }
        .return{
            position: absolute;
            top: 1%;
            left: 1%;
            cursor: pointer;
        }
    </style>
</head>
<body>
<div>
    <ul class="layui-tab-title">
        <li class="home">
            <i class="layui-icon">&#xe6b2;</i>发票管理
        </li>
    </ul>
</div>
<div class="layui-fluid bigBox">
    <div class="layui-row layui-col-space15">
        <div class="layui-col-md12">
            <div class="layui-card">
                <%--搜索栏--%>
                <div class="layui-card-body ">
                    <form class="layui-form layui-col-space5">
                        <div class="layui-inline layui-show-xs-block">
                            <label class="layui-form-label inputLabel" style="padding: 9px 5px">姓名:</label>
                            <div class="layui-input-inline layui-show-xs-block">
                                <input type="text" id="buyerName" placeholder="买方姓名" class="layui-input" style="height: 40px">
                            </div>
                        </div>
                        <div class="layui-inline layui-show-xs-block">
                            <label class="layui-form-label inputLabel" style="padding: 9px 5px">发票号码:</label>
                            <div class="layui-input-inline layui-show-xs-block">
                                <input type="text" id="invoiceNumber" placeholder="发票号码" class="layui-input" style="height: 40px">
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
                <%--操作栏--%>
                <div class="layui-card-header">
                    <button class="layui-btn layui-btn-normal" id="btnEdit">
                        <i class="layui-icon layui-icon-search"></i>查看
                    </button>
                    <button class="layui-btn layui-btn-primary" id="btnExportXlsx">
                        <i class="layui-icon layui-icon-export"></i>打印
                    </button>
                </div>
            </div>
        </div>
    </div>
</div>
<div class="invoiceImgBig" style="display: none;" id="invoiceImgBig">
    <div class="return" onclick="quit()" id="quit">
        <i class="layui-icon">&#xe65a;</i> 返回
    </div>
    <div class="invoiceImg">
        <img src="${ctx}/static/img/电子发票.jpg">
        <div class="dataInput" style="width:10%;top: 8%;left: 80%;">
            <input type="text" name="invoiceCode" class="dataInputs" id="invoiceCode" readonly>
        </div>
        <div class="dataInput" style="width:16%;top: 11%;left: 80%;">
            <input type="text" name="invoiceNumber" class="dataInputs" id="invoiceNumbers" readonly>
        </div>
        <div class="dataInput" style="top: 14%;left: 80%;">
            <input type="text" name="clearing" class="dataInputs" id="clearing" readonly>
        </div>
        <div class="dataInput" style="top: 22%;left: 19%;">
            <input type="text" name="buyerName" class="dataInputs" id="buyerNames" readonly>
        </div>
        <div class="dataInput" style="top: 30%;left: 19%;">
            <input type="text" name="buyerPhone" class="dataInputs" id="buyerPhone" readonly>
        </div>
        <div class="dataInput" style="top: 50%;left: 11%;">
            <input type="text" name="commodity" class="dataInputs" id="commodity" readonly>
        </div>
        <div class="dataInput" style="top: 50%;left: 29.5%;">
            <input type="text" name="invoiceType" class="dataInputs" id="invoiceType" readonly>
        </div>
        <div class="dataInput" style="width:4%;top: 50%;left: 48%;">
            <input type="text" name="quantity" class="dataInputs" id="quantity" readonly>
        </div>
        <div class="dataInput" style="width:8%;top: 50%;left: 56%;">
            <input type="text" name="price" class="dataInputs" id="price" readonly>
        </div>
        <div class="dataInput" style="top: 50%;left: 66.5%;">
            <input type="text" name="invoiceSum" class="dataInputs" id="invoiceSum" readonly>
        </div>
        <div class="dataInput" style="width:4%;top: 50%;left: 78.5%;">
            <input type="text" name="tax" class="dataInputs" id="tax" readonly>
        </div>
        <div class="dataInput" style="top: 50%;left: 85.5%;">
            <input type="text" name="taxSum" class="dataInputs" id="taxSum" readonly>
        </div>
        <div class="dataInput" style="top: 69.5%;left: 29.5%;">
            <input type="text" name="sumBig" class="dataInputs" id="sumBig" readonly>
        </div>
        <div class="dataInput" style="top: 69.5%;left: 79.5%;">
            <input type="text" name="sumSmall" class="dataInputs" id="sumSmall" readonly>
        </div>
        <div class="dataInput" style="top: 75%;left: 19%;">
            <input type="text" name="sellerName" class="dataInputs" id="sellerName" readonly>
        </div>
        <div class="dataInput" style="top: 82%;left: 19%;">
            <input type="text" name="sellerPhone" class="dataInputs" id="sellerPhone" readonly>
        </div>
        <div class="dataInput" style="width:33%;top: 80%;left: 61.5%;">
            <input type="text" name="remark" class="dataInputs" id="remark" readonly>
        </div>
        <div class="dataInput" style="top: 90%;left: 12%;">
            <input type="text" name="gathering" class="dataInputs" id="gathering" readonly>
        </div>
        <div class="dataInput" style="top: 90%;left: 58.5%;">
            <input type="text" name="operation" class="dataInputs" id="operation" readonly>
        </div>
    </div>
</div>
<script src="${ctx}/static/lib/layui/layui.js" charset="UTF-8"></script>
<script src="${ctx}/static/js/jquery.min.js" charset="UTF-8"></script>
<script>
    var layerFormIndex;
    layui.use(function(){
        var table = layui.table,
            $ = layui.$,
            layer = layui.layer,
            form = layui.form,
            tree=layui.tree;
        // 菜单树形table初始化
        var tableRole = table.render({
            elem: '#tableRole',//table元素
            id: 'tableRole',
            url: '${ctx}/system/selectInvoiceAll',//数据url
            page: true,//分页
            cols: [[
                {type: 'radio'},//单选框
                {field: 'invoiceCode', title: '发票代码'},
                {field: 'id', title: 'id', event: 'id',hide: true},
                {field: 'invoiceNumber', title: '发票号码'},
                {field: 'clearing', title: '开票日期', event: 'clearing', width: 160, templet: function (rowData) {
                        var dt = new Date(rowData.clearing);
                        return dt.getFullYear() + "-" + (dt.getMonth() + 1) + "-" + dt.getDate();
                    }
                },
                {field: 'buyerName', title: '买方姓名', event: 'buyerName'},
                {field: 'buyerPhone', title: '买方电话', event: 'buyerPhone'},
                {field: 'commodity', title: '商品名称', event: 'commodity'},
                {field: 'price', title: '商品单价', event: 'price'},
                {field: 'sellerName', title: '卖方名称', event: 'sellerName'},
                {field: 'sellerPhone', title: '卖方电话', event: 'sellerPhone'},
                {field: 'operation', title: '开票人', event: 'operation'},
            ]],
            limit: 10,//每页页数
            limits: [5, 10]//定义每页页数选择下拉框
        });
        //查询按钮
        $("#btnSearch").click(function (){
            var buyerName=$("#buyerName").val();
            var invoiceNumber=$("#invoiceNumber").val();
            //表格重载
            table.reload('tableRole', {
                where: {
                    buyerName:buyerName,
                    invoiceNumber:invoiceNumber
                } //设定异步数据接口的额外参数
            });
        });
        //点击查看
        $("#btnEdit").click(function (){
            //获取table中被勾选的数据
            var selectData = table.checkStatus("tableRole");
            if(selectData.data.length>0){
                var invoiceId = selectData.data[0].id;
                //加载被修改的数据
                $.post('${ctx}/system/selectInvoiceById',{
                    invoiceId:invoiceId
                },function (jsonMsg){
                    if(jsonMsg.state){
                        document.getElementById("invoiceImgBig").style.display="block";
                        var obj = jsonMsg.data;
                        $("#invoiceCode").val(obj.invoiceCode);
                        $("#invoiceNumbers").val(obj.invoiceNumber);
                        $("#clearing").val(dateTransition(obj.clearing));
                        $("#buyerNames").val(obj.buyerName);
                        $("#buyerPhone").val(obj.buyerPhone);
                        $("#commodity").val(obj.commodity);
                        $("#invoiceType").val(obj.invoiceType);
                        $("#quantity").val(obj.quantity);
                        $("#price").val(obj.price);
                        $("#invoiceSum").val(obj.invoiceSum);
                        $("#tax").val(obj.tax);
                        $("#taxSum").val(obj.taxSum);
                        $("#sumBig").val(obj.sumBig);
                        $("#sumSmall").val(obj.sumSmall);
                        $("#sellerName").val(obj.sellerName);
                        $("#sellerPhone").val(obj.sellerPhone);
                        $("#remark").val(obj.remark);
                        $("#gathering").val(obj.gathering);
                        $("#operation").val(obj.operation);
                    }else {
                        layer.msg(jsonMsg.msg,{icon:5});
                    }
                });
            }else {
                layer.msg("请选择需要查看的数据");
            }
        });
        //点击打印
        $("#btnExportXlsx").click(function (){
            //获取table中被勾选的数据
            var selectData = table.checkStatus("tableRole");
            if(selectData.data.length>0){
                var invoiceId = selectData.data[0].id;
                //加载被修改的数据
                $.post('${ctx}/system/selectInvoiceById',{
                    invoiceId:invoiceId
                },function (jsonMsg){
                    if(jsonMsg.state){
                        document.getElementById("invoiceImgBig").style.display="block";
                        var obj = jsonMsg.data;
                        $("#invoiceCode").val(obj.invoiceCode);
                        $("#invoiceNumbers").val(obj.invoiceNumber);
                        $("#clearing").val(dateTransition(obj.clearing));
                        $("#buyerNames").val(obj.buyerName);
                        $("#buyerPhone").val(obj.buyerPhone);
                        $("#commodity").val(obj.commodity);
                        $("#invoiceType").val(obj.invoiceType);
                        $("#quantity").val(obj.quantity);
                        $("#price").val(obj.price);
                        $("#invoiceSum").val(obj.invoiceSum);
                        $("#tax").val(obj.tax);
                        $("#taxSum").val(obj.taxSum);
                        $("#sumBig").val(obj.sumBig);
                        $("#sumSmall").val(obj.sumSmall);
                        $("#sellerName").val(obj.sellerName);
                        $("#sellerPhone").val(obj.sellerPhone);
                        $("#remark").val(obj.remark);
                        $("#gathering").val(obj.gathering);
                        $("#operation").val(obj.operation);

                    }else {
                        layer.msg(jsonMsg.msg,{icon:5});
                    }
                });
            }else {
                layer.msg("请选择需要打印的数据");
            }
        });
    });
    function dateTransition(date){
        var dt = new Date(date);
        return dt.getFullYear() + "-" + (dt.getMonth() + 1) + "-" + dt.getDate();
    }
    function quit(){
        document.getElementById("invoiceImgBig").style.display="none";
    }
    function $(selector){
        return document.querySelector(selector);
    }



    //打印功能
    //获取整个页面
    $("#btnExportXlsx").onclick =function(){
        document.getElementById("quit").style.display="none";
        setTimeout(function (){
            window.print();
            document.getElementById("quit").style.display="block";
        }, 1000);

    }
    /* 实现打印全部页面（也可以打印局部页面 - 看需求） ----
   	   我是只打印boby里边的内容
   	   获取我们定义的id
   	*/
    $("#btnExport").onclick =function(){
        var oldHtml = $(".invoiceImgBig").innerHTML;
        window.print();
        $(".invoiceImgBig").innerHTML = oldHtml;

    }
</script>
</body>
</html>
