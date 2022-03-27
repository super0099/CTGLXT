<%--
  Created by IntelliJ IDEA.
  User: super007
  Date: 2021/9/14
  Time: 19:02
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
    <link rel="stylesheet" href="${ctx}/static/css/advance.css">
    <!--[if lt IE 9]>
    <script src="https://cdn.staticfile.org/html5shiv/r29/html5.min.js"></script>
    <script src="https://cdn.staticfile.org/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
    <style>
        .dataInput{
            display: inline-block;
            width: 20%;
            margin: 20px;
        }
        .elderLabel{
            text-align: center;
            width: 30%;
            float: left;
            margin: 2px 0;
            font-size: 13px;
        }
        .elderInput{
            width: 70%;
            float: left;
        }
        .elderInput input{
            width: 100%;
            color: #01AAED;
            background: #dcd9ca;
            border: none;
        }
        .elderInput select{
            width: 100%;
            color: #01AAED;
        }
        .dataInputs{
            display: inline-block;
            width: 20%;
            margin: 20px;
        }
        .elderData{
            background: #ffffff;
            border-radius: 5px;
        }
        .rightBox{
            background: #ffffff;
        }
        .elderData button{
            position: absolute;
            right: 20px;
        }
        .leftTitle{
            background: #4476A7;
        }
        .leftBodyTitle li{
            background: lightpink;
        }
        .insetInvoice{
            margin: 1%;
            width: 98%;
            height: 45%;
            border-radius: 3px;
            border: 1px solid #0000FF;
        }
        #userPicture{
            width: 90%;
            height: 30%;
            padding-left: 1%;
        }
    </style>
</head>
<body>
<div class="layui-inline title">
    <ul class="layui-tab-title">
        <li class="home">
            <i class="layui-icon">&#xe606;</i>待入职长者信息
        </li>
    </ul>
</div>
<div class="bigBox">
    <div class="leftBox">
        <div class="leftTitle"><span>待入职长者信息</span></div>
        <div class="leftBody">
            <div class="leftBodyTitle layui-row">
                <ul>
                    <li class="layui-col-md1">&nbsp;</li>
                    <li class="layui-col-md4">入住号</li>
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
        <div class="elderData clearfix">
            <div class="layui-col-md2">
                <img src="${ctx}/static/img/uploadImg.png" alt="照片" id="userPicture">
            </div>
            <div class="layui-col-md10">
                <div class="dataInput">
                    <label class="elderLabel">入住号</label>
                    <div class="elderInput">
                        <input type="text" name="checkInCode" id="checkInCode" readonly>
                        <input type="text" id="appointmentId" hidden>
                        <input type="text" id="berthId" hidden>
                    </div>
                </div>
                <div class="dataInput">
                    <label class="elderLabel">姓名</label>
                    <div class="elderInput">
                        <input type="text" name="elderName" id="elderName" readonly>
                    </div>
                </div>
                <div class="dataInput">
                    <label class="elderLabel">性别</label>
                    <div class="elderInput">
                        <select id="gender" name="gender" disabled="disabled">
                            <option value="1">男</option>
                            <option value="2">女</option>
                        </select>
                    </div>
                </div>
                <div class="dataInput">
                    <label class="elderLabel">年龄</label>
                    <div class="elderInput">
                        <input type="text" name="age" id="age" readonly>
                    </div>
                </div>
                <div class="dataInput">
                    <label class="elderLabel">籍贯</label>
                    <div class="elderInput">
                        <input type="text" name="nativePlace" id="nativePlace" readonly>
                    </div>
                </div>
                <div class="dataInput">
                    <label class="elderLabel">民族</label>
                    <div class="elderInput">
                        <input type="text" name="nation" id="nation" readonly>
                    </div>
                </div>
                <div class="dataInput">
                    <label class="elderLabel">婚姻状况:</label>
                    <div class="elderInput">
                        <select id="marriage" name="marriage" disabled="disabled">
                            <option value="1">已婚</option>
                            <option value="2">未婚</option>
                        </select>
                    </div>
                </div>
                <div class="dataInput">
                    <label class="elderLabel">出生日期:</label>
                    <div class="elderInput">
                        <input type="text" name="birth" id="birth" readonly>
                    </div>
                </div>
                <div class="dataInput">
                    <label class="elderLabel">医保类型:</label>
                    <div class="elderInput">
                        <select id="healthInsuranceId" name="healthInsuranceId" disabled="disabled">
                            <option value="0">没有医保</option>
                            <option value="1">职工医保</option>
                            <option value="2">居民医保</option>
                        </select>
                    </div>
                </div>
                <div class="dataInput">
                    <label class="elderLabel">当前状态</label>
                    <div class="elderInput">
                        <select name="state" id="state" disabled="disabled">
                            <option value="1">预约中</option>
                        </select>
                    </div>
                </div>
                <div class="dataInput">
                    <label class="elderLabel">护理类型</label>
                    <div class="elderInput">
                        <select name="nurseType" id="nurseType" disabled="disabled">
                            <option value="0">------</option>
                            <option value="1">高级护理</option>
                            <option value="2">中级护理</option>
                            <option value="3">入门护理</option>
                        </select>
                    </div>
                </div>
                <div class="dataInput">
                    <label class="elderLabel">入住地点</label>
                    <div class="elderInput">
                        <input type="text" name="site" id="site" readonly>
                    </div>
                </div>
            </div>
            <div class="layui-col-md12">
                <div class="dataInputs">
                    <label class="elderLabel">现住址</label>
                    <div class="elderInput">
                        <input type="text" name="residence" id="residence" readonly>
                    </div>
                </div>
                <div class="dataInputs">
                    <label class="elderLabel">家庭电话</label>
                    <div class="elderInput">
                        <input type="text" name="homePhone" id="homePhone" readonly>
                    </div>
                </div>
                <div class="dataInputs">
                    <label class="elderLabel">家庭电话</label>
                    <div class="elderInput">
                        <select name="credentialsType" id="credentialsType" disabled>
                            <option value="1">居民身份证</option>
                            <option value="2">军人证件</option>
                        </select>
                    </div>
                </div>
                <div class="dataInputs">
                    <label class="elderLabel">证件号</label>
                    <div class="elderInput">
                        <input type="text" name="IDCode" id="IDCode" readonly>
                    </div>
                </div>
            </div>
            <div class="layui-col-md12">
                <div class="insetInvoice" style="display: none" id="insetInvoice">
                    <h4 style="color: #FF5722">长者发票</h4>
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
            <div class="layui-col-md12 btn" id="btn">
            </div>
        </div>
    </div>
</div>
<script src="${ctx}/static/lib/layui/layui.js" charset="UTF-8"></script>
<script src="${ctx}/static/js/jquery.min.js" charset="UTF-8"></script>
<script>
    $(function (){
        $.post("${ctx}/await/selectAwaitElderAll",{},function (jsonData){
            var leftBodyBody = document.getElementById("leftBodyBody");
            var leftBodyBodyChild = leftBodyBody.childNodes;
            for (var i=leftBodyBodyChild.length-1;i >= 0; i--){
                leftBodyBody.removeChild(leftBodyBodyChild[i]);
            }
            for (var i =0;i<jsonData.length;i++){
                leftBodyBody.innerHTML+='<ul onclick="selectElder('+jsonData[i].id+')" class="clearfix"><li class="layui-col-md1"><i class="layui-icon">&#xe602;</i></li><li class="layui-col-md4">'+jsonData[i].appointmentId+'</li><li class="layui-col-md3">'+jsonData[i].elderName+'</li><li class="layui-col-md4">&nbsp;</li></ul>';
            }
        })
    });
    function selectElder(elderId){
        $.post("${ctx}/await/selectAwaitElder",{elderId:elderId},function (jsonData){
            if(jsonData.state){
                $.post("${ctx}/reception/selectElderById",{elderId:elderId},function (obj){
                    if(obj.state){
                        $("#userPicture").prop("src","${ctx}/home/getPortraitImage?imgName="+obj.data.portrait);
                    }
                })
                document.getElementById("insetInvoice").style.display="none";
                var data = jsonData.data;
                $("#berthId").val(data.site);
                $("#checkInCode").val(data.checkInCode);
                $("#appointmentId").val(data.id);
                $("#elderName").val(data.elderName);
                $("select[name = gender]").val(data.gender);
                $("#age").val(data.age);
                $("#nativePlace").val(data.nativePlace);
                $("#nation").val(data.nation);
                $("select[name = marriage]").val(data.marriage);
                $("#birth").val(parsing(data.birth));
                $("select[name = healthInsuranceId]").val(data.healthInsuranceId);
                $("select[name = state]").val(data.state);
                $("select[name = nurseType]").val(data.nurseType);
                $("#site").val(data.checkSite);
                $("#residence").val(data.residence);
                $("#homePhone").val(data.homePhone);
                $("#IDCode").val(data.idcode);
                $("select[name = credentialsType]").val(data.credentialsType);
                var btn = document.getElementById("btn");
                var btnChild = btn.childNodes;
                for(var i = btnChild.length-1;i>=0;i--){
                    btn.removeChild(btnChild[i]);
                }
                if(data.collectionState==1){
                    document.getElementById("insetInvoice").style.display="block";
                    $("#buyerName").val(data.elderName);
                    $("#buyerPhone").val(data.homePhone);
                    if(data.nurseType==1){
                        $("#type").val("高级护理");
                        $("#sumSmall").val(3400);
                        $("#sum").val(3400);
                        $("#price").val(3400);
                    }if(data.nurseType==2){
                        $("#type").val("中级护理");
                        $("#sumSmall").val(2800);
                        $("#sum").val(2800);
                        $("#price").val(2800);
                    }if(data.nurseType==3){
                        $("#type").val("入门护理");
                        $("#sumSmall").val(1900);
                        $("#sum").val(1900);
                        $("#price").val(1900);
                    }
                    var dt = new Date();
                    var coo = 'aljylxt'+dt.getFullYear()+(dt.getMonth()+1)+dt.getDate()+dt.getHours()+dt.getMinutes()+dt.getSeconds();
                    $("#invoiceNumber").val(coo);
                    btn.innerHTML+='<button class="layui-btn layui-btn-orange" onclick="btnEdit('+elderId+')"><i class="layui-icon layui-icon-edit"></i>缴费并且入住</button>';
                }else if(data.collectionState==2){
                    btn.innerHTML+='<button class="layui-btn layui-btn-normal" onclick="checkIn()"><i class="layui-icon layui-icon-edit"></i>立即入住</button>';
                }
            }else {
                layer.msg(jsonData.msg)
            }
        })
    }
    function parsing(rowData){
        var date=new Date(rowData);
        return date.getFullYear()+"-"+(date.getMonth()+1)+"-"+date.getDate();
    }
    function btnEdit(elderId){
        var invoiceCode = $("#invoiceCode").val();
        var berthId = $("#berthId").val();
        var invoiceNumber = $("#invoiceNumber").val();
        var appointmentId = $("#appointmentId").val();
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
        var checkInCode = $("#checkInCode").val();
        if(clearing==null||clearing==""||clearing==undefined){
            layer.msg("开票日期不能为空");
            return;
        }
        if(sumBig==null||sumBig==""||sumBig==undefined){
            layer.msg("大写金额不能为空");
            return;
        }
        if(appointmentId==null||appointmentId==""||appointmentId==undefined){
            layer.msg("数据异常");
            return;
        }
        if(berthId==null||berthId==""||berthId==undefined){
            layer.msg("数据异常");
            return;
        }
        layer.load();
        $.post('${ctx}/await/updateElderAllData',{
            checkInCode:checkInCode,
            elderId:elderId,
            berthId:berthId,
            appointmentId:appointmentId,
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
                layer.msg(jsonMsg.msg+",请到发票管理打印电子发票");
                setTimeout(function (){
                    window.location.reload();
                }, 3000);
            }else {
                layer.msg(jsonMsg.msg,{icon:5});
            }
        });
    }
    function checkIn(){
        var berthId = $("#berthId").val();
        var appointmentId = $("#appointmentId").val();
        if(appointmentId==null||appointmentId==""||appointmentId==undefined){
            layer.msg("数据异常");
            return;
        }
        if(berthId==null||berthId==""||berthId==undefined){
            layer.msg("数据异常");
            return;
        }
        layer.load();
        $.post("${ctx}/await/elderCheckIn",{berthId:berthId,appointmentId:appointmentId},function (jsonData){
            if(jsonData.state){
                layer.msg(jsonData.msg);
                setTimeout(function (){
                    window.location.reload();
                }, 3000);
            }else {
                layer.msg(jsonData.msg,{icon:5});
            }
        })
    }
</script>
</body>
</html>
