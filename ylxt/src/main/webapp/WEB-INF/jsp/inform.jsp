<%--
  Created by IntelliJ IDEA.
  User: super007
  Date: 2021/9/10
  Time: 19:57
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" scope="page"/>
<html>
<head>
    <title>缴费通知</title>
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
</head>
<body>
<div class="layui-inline title">
    <ul class="layui-tab-title">
        <li class="home">
            <i class="layui-icon">&#xe65e;</i>缴费通知
        </li>
    </ul>
</div>
<div class="informBody">
    <div class="leftBox">
        <div class="leftTitle"><span>需缴费名单</span></div>
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
        <div class="bill">
            <h4>长者账单</h4>
            <div class="layui-card-body billTable">
                <table class="layui-hide" id="bill" lay-filter="bill"></table>
            </div>
            <div class="layui-card-header billBtn" id="billBtn">
                <button class="layui-btn layui-btn-normal" id="btnEdit" style="display: none"><i class="layui-icon layui-icon-rmb"></i>结算</button>
            </div>
        </div>
        <div class="insetInvoice">
            <h4>长者发票</h4>
            <div class="elderMessage layui-row">
                <h6>发票信息</h6>
                <div class="dataInput1">
                    <label class="layui-form-label">发票代码:</label>
                    <div class="layui-input-block">
                        <input type="text" name="invoiceCode" class="layui-input" id="invoiceCode" value="ALJYLXT" readonly>
                    </div>
                </div>
                <div class="dataInput1">
                    <label class="layui-form-label">发票号码:</label>
                    <div class="layui-input-block">
                        <input type="text" name="invoiceNumber" class="layui-input" id="invoiceNumber" readonly>
                    </div>
                </div>
                <div class="dataInput1">
                    <label class="layui-form-label">开票日期:</label>
                    <div class="layui-input-block">
                        <input type="date" name="clearing" class="layui-input" id="clearing" lay-verify="required">
                    </div>
                </div>
                <div class="dataInput1">
                    <label class="layui-form-label">买方姓名:</label>
                    <div class="layui-input-block">
                        <input type="text" name="buyerName" class="layui-input" id="buyerName" value="" readonly>
                    </div>
                </div>
                <div class="dataInput1">
                    <label class="layui-form-label">买方电话:</label>
                    <div class="layui-input-block">
                        <input type="text" name="buyerPhone" class="layui-input" id="buyerPhone" readonly>
                    </div>
                </div>
                <div class="dataInput1">
                    <label class="layui-form-label">商品名称:</label>
                    <div class="layui-input-block">
                        <input type="text" name="commodity" class="layui-input" id="commodity" value="养老服务" readonly>
                    </div>
                </div>
                <div class="dataInput1">
                    <label class="layui-form-label">规模,类型:</label>
                    <div class="layui-input-block">
                        <input type="text" name="type" class="layui-input" id="type" readonly>
                    </div>
                </div>
                <div class="dataInput1">
                    <label class="layui-form-label">商品单位:</label>
                    <div class="layui-input-block">
                        <input type="text" name="unit" class="layui-input" id="unit" value="/月"  readonly>
                    </div>
                </div>
                <div class="dataInput1">
                    <label class="layui-form-label">商品数量:</label>
                    <div class="layui-input-block">
                        <input type="text" name="quantity" class="layui-input" id="quantity" value="1"  readonly>
                    </div>
                </div>
                <div class="dataInput1">
                    <label class="layui-form-label">商品单价:</label>
                    <div class="layui-input-block">
                        <input type="text" name="price" class="layui-input" id="price"  readonly>
                    </div>
                </div>
                <div class="dataInput1">
                    <label class="layui-form-label">商品全额:</label>
                    <div class="layui-input-block">
                        <input type="text" name="sum" class="layui-input" id="sum"  readonly>
                    </div>
                </div>
                <div class="dataInput1">
                    <label class="layui-form-label">商品税率:</label>
                    <div class="layui-input-block">
                        <input type="text" name="tax" class="layui-input" id="tax" value="0"  readonly>
                    </div>
                </div>
                <div class="dataInput1">
                    <label class="layui-form-label">商品税额:</label>
                    <div class="layui-input-block">
                        <input type="text" name="taxSum" class="layui-input" id="taxSum" value="0"  readonly>
                    </div>
                </div>
                <div class="dataInput1">
                    <label class="layui-form-label">金额大写:</label>
                    <div class="layui-input-block">
                        <input type="text" name="sumBig" class="layui-input" id="sumBig" lay-verify="required">
                    </div>
                </div>
                <div class="dataInput1">
                    <label class="layui-form-label">金额小写:</label>
                    <div class="layui-input-block">
                        <input type="text" name="sumSmall" class="layui-input" id="sumSmall"  readonly>
                    </div>
                </div>
                <div class="dataInput1">
                    <label class="layui-form-label">卖方名称:</label>
                    <div class="layui-input-block">
                        <input type="text" name="sellerName" class="layui-input" id="sellerName" value="安乐居" readonly>
                    </div>
                </div>
                <div class="dataInput1">
                    <label class="layui-form-label">卖方电话:</label>
                    <div class="layui-input-block">
                        <input type="text" name="sellerPhone" class="layui-input" id="sellerPhone" value="13126033786" readonly>
                    </div>
                </div>
                <div class="dataInput1">
                    <label class="layui-form-label">备注:</label>
                    <div class="layui-input-block">
                        <input type="text" name="remark" class="layui-input" id="remark" lay-verify="required">
                    </div>
                </div>
                <div class="dataInput1">
                    <label class="layui-form-label">收款人:</label>
                    <div class="layui-input-block">
                        <input type="text" name="gathering" class="layui-input" id="gathering" value="安乐居财务部" readonly>
                    </div>
                </div>
                <div class="dataInput1">
                    <label class="layui-form-label">开票人:</label>
                    <div class="layui-input-block">
                        <input type="text" name="operation" class="layui-input" id="operation" readonly placeholder="无须填写">
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<script src="${ctx}/static/lib/layui/layui.js" charset="UTF-8"></script>
<script src="${ctx}/static/js/jquery.min.js" charset="UTF-8"></script>
<script>
    var table;
    layui.use(function (){
        table = layui.table;
        var $ = layui.$,
            layer = layui.layer,
            form = layui.form,
            tree=layui.tree;
        //结算
        $("#btnEdit").click(function (){
            var invoiceCode = $("#invoiceCode").val();
            var invoiceNumber = $("#invoiceNumber").val();
            var clearing = $("#clearing").val();
            var buyerName = $("#buyerName").val();
            var buyerPhone = $("#buyerPhone").val();
            var commodity = $("#commodity").val();
            var invoiceType = $("#type").val();
            var unit = $("#unit").val();
            var quantity = $("#quantity").val();
            var price = $("#price").val();
            var sum = $("#sum").val();
            var tax = $("#tax").val();
            var taxSum = $("#taxSum").val();
            var sumBig = $("#sumBig").val();
            var sumSmall = $("#sumSmall").val();
            var sellerName = $("#sellerName").val();
            var sellerPhone = $("#sellerPhone").val();
            var remark = $("#remark").val();
            var gathering = $("#gathering").val();
            var operation = $("#operation").val();
            if(clearing==null||clearing==""||clearing==undefined){
                layer.msg("开票日期不能为空");
                return;
            }
            if(sumBig==null||sumBig==""||sumBig==undefined){
                layer.msg("大写金额不能为空");
                return;
            }
            var DeleteData = table.checkStatus("bill");
            if(DeleteData.data.length>0){
                var collectionId = DeleteData.data[0].id;
                //加载被修改的数据
                layer.load();
                $.post('${ctx}/inform/updateCollection',{
                    collectionId:collectionId,
                    invoiceCode:invoiceCode,
                    invoiceNumber:invoiceNumber,
                    clearing:clearing,
                    buyerName:buyerName,
                    buyerPhone:buyerPhone,
                    commodity:commodity,
                    invoiceType:invoiceType,
                    unit:unit,
                    quantity:quantity,
                    price:price,
                    invoiceSum:sum,
                    tax:tax,
                    taxSum:taxSum,
                    sumBig:sumBig,
                    sumSmall:sumSmall,
                    sellerName:sellerName,
                    sellerPhone:sellerPhone,
                    remark:remark,
                    gathering:gathering,
                    operation:operation
                },function (jsonMsg){
                    if(jsonMsg.state){
                        layer.msg(jsonMsg.msg+"请到发票管理打印电子发票");
                        setTimeout(function (){
                            window.location.reload();
                        }, 3000);
                    }else {
                        layer.msg(jsonMsg.msg,{icon:5});
                    }
                });
            }else {
                layer.msg("请选择需要修改的数据");
            }
        });
    })
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
        $.post("${ctx}/inform/selectElderById",{elderId:elderId},function (jsonData){
            $("#buyerName").val(jsonData.elderName);
            $("#buyerPhone").val(jsonData.homePhone);
            if(jsonData.nurseType==1){
                $("#type").val("高级护理");
                $("#sumSmall").val(3400);
                $("#sum").val(3400);
                $("#price").val(3400);
            }if(jsonData.nurseType==2){
                $("#type").val("中级护理");
                $("#sumSmall").val(2800);
                $("#sum").val(2800);
                $("#price").val(2800);
            }if(jsonData.nurseType==3){
                $("#type").val("入门护理");
                $("#sumSmall").val(1900);
                $("#sum").val(1900);
                $("#price").val(1900);
            }
            var dt = new Date();
            var coo = 'aljylxt'+dt.getFullYear()+(dt.getMonth()+1)+dt.getDate()+dt.getHours()+dt.getMinutes()+dt.getSeconds();
            $("#invoiceNumber").val(coo);
        })
        // 菜单树形table初始化
        var tableRole = table.render({
            elem: '#bill',//table元素
            id: 'bill',
            url: '${ctx}/cost/selectElderPayment?elderId='+elderId,//数据url
            cols: [[
                {type: 'radio'},
                {field: 'id', title: 'id', event: 'id',hide: true},
                {field: 'alimony',title: '设施费',width: 120},
                {field: 'boardWages', title: '伙食费', width: 120},
                {field: 'berth', title: '床位费', event: 'berth', width: 120},
                {field: 'serviceCharge', title: '护理费',width: 120,},
                {field: 'totalMoney', title: '总金额', event: 'totalMoney',width: 140},
                {field: 'practical', title: '应付款', event: 'practical',width: 140},
                {field: 'state', title: '状态', event: 'state',width: 100,templet:function (rowData){
                    console.log(rowData)
                        if(rowData.state==1){
                            return "未付款";
                        }
                        if (rowData.state==2){
                            return "已付款";
                        }
                    }},
                {field: 'clearing',title: '账单生成时间', event: 'clearing',width: 196,templet:function (rowData){
                        var dt = new Date(rowData.clearing);
                        return dt.getFullYear() + "-" + (dt.getMonth() + 1) + "-" + dt.getDate();
                    }}
            ]]
        });
        document.getElementById("btnEdit").style.display='block';
    }
</script>
</body>
</html>
