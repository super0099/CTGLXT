<%--
  Created by IntelliJ IDEA.
  User: super007
  Date: 2021/9/20
  Time: 11:06
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" scope="page"/>
<html>
<head>
    <title>订餐管理</title>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport"
          content="width=device-width,user-scalable=yes, minimum-scale=0.4, initial-scale=0.8"/>
    <meta http-equiv="Cache-Control" content="no-siteapp"/>
    <link rel="stylesheet" href="${ctx}/static/lib/layui/css/layui.css">
    <link rel="stylesheet" href="${ctx}/static/css/appointment.css">
    <!--[if lt IE 9]>
    <script src="https://cdn.staticfile.org/html5shiv/r29/html5.min.js"></script>
    <script src="https://cdn.staticfile.org/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
</head>
<body>
<div class="layui-inline title">
    <ul class="layui-tab-title">
        <li class="home">
            <i class="layui-icon">&#xe60a;</i>订餐管理
        </li>
    </ul>
</div>
<form class="layui-form" action="" autocomplete="off">
    <div class="elderData clearfix">
        <div class="elderTitle">
            <i class="layui-icon">&#xe770;</i>需订餐长者信息
        </div>
        <div class="BigBox">
            <div class="elderMessage layui-row">
                <div class="dataInput">
                    <label class="layui-form-label">长 者:</label>
                    <div class="layui-input-block">
                        <select name="elderId" id="elderId" lay-filter="demo">

                        </select>
                    </div>
                </div>
                <div class="dataInput">
                    <label class="layui-form-label">床位号:</label>
                    <div class="layui-input-block">
                        <input type="text" name="elderSite" class="layui-input" id="elderSite" readonly>
                    </div>
                </div>
                <div class="dataInput">
                    <label class="layui-form-label">订餐日期:</label>
                    <div class="layui-input-block">
                        <input type="date" name="orderDate" class="layui-input" id="orderDate" lay-verify="required">
                    </div>
                </div>
            </div>
            <div class="elderMessage">
                <div class="dataInput">
                    <label class="layui-form-label">早 餐:</label>
                    <div class="layui-input-block">
                        <select name="breakfast" id="breakfast" lay-filter="breakfast">

                        </select>
                    </div>
                </div>
                <div class="dataInput">
                    <label class="layui-form-label">午 餐:</label>
                    <div class="layui-input-block">
                        <select name="lunch" id="lunch" lay-filter="lunch">

                        </select>
                    </div>
                </div>
                <div class="dataInput">
                    <label class="layui-form-label">晚 餐:</label>
                    <div class="layui-input-block">
                        <select name="dinner" id="dinner" lay-filter="dinner">
                        </select>
                    </div>
                </div>
                <div class="dataInput">
                    <label class="layui-form-label">当前状态:</label>
                    <div class="layui-input-block">
                        <select name="state" id="state">
                            <option value="1">订餐中</option>
                        </select>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="bottomData clearfix">
        <div class="guarantee">
            <div class="guaranteeTitle">
                <i class="layui-icon">&#xe612;</i>早 餐
            </div>
            <div class="elderMessage layui-row">
                <div class="dataInput">
                    <label class="layui-form-label">主 食:</label>
                    <div class="layui-input-block">
                        <input type="text" name="bStapleFood" class="layui-input" id="bStapleFood" readonly>
                    </div>
                </div>
                <div class="dataInput">
                    <label class="layui-form-label">蔬 菜:</label>
                    <div class="layui-input-block">
                        <input type="text" name="bVegetable" class="layui-input" id="bVegetable" readonly>
                    </div>
                </div>
                <div class="dataInput">
                    <label class="layui-form-label">肉 类:</label>
                    <div class="layui-input-block">
                        <input type="text" name="bMeat" class="layui-input" id="bMeat" readonly>
                    </div>
                </div>
                <div class="dataInput">
                    <label class="layui-form-label">汤 类:</label>
                    <div class="layui-input-block">
                        <input type="text" name="bSoup" class="layui-input" id="bSoup" readonly>
                    </div>
                </div>
            </div>
        </div>
        <div class="guarantee">
            <div class="guaranteeTitle">
                <i class="layui-icon">&#xe612;</i>午 餐
            </div>
            <div class="elderMessage layui-row">
                <div class="dataInput">
                    <label class="layui-form-label">主 食:</label>
                    <div class="layui-input-block">
                        <input type="text" name="lStapleFood" class="layui-input" id="lStapleFood" readonly>
                    </div>
                </div>
                <div class="dataInput">
                    <label class="layui-form-label">蔬 菜:</label>
                    <div class="layui-input-block">
                        <input type="text" name="lVegetable" class="layui-input" id="lVegetable" readonly>
                    </div>
                </div>
                <div class="dataInput">
                    <label class="layui-form-label">肉 类:</label>
                    <div class="layui-input-block">
                        <input type="text" name="lMeat" class="layui-input" id="lMeat" readonly>
                    </div>
                </div>
                <div class="dataInput">
                    <label class="layui-form-label">汤 类:</label>
                    <div class="layui-input-block">
                        <input type="text" name="lSoup" class="layui-input" id="lSoup" readonly>
                    </div>
                </div>
            </div>
        </div>
        <div class="nurseType">
            <div class="nurseTypeTitle">
                <i class="layui-icon">&#xe656;</i>晚 餐
            </div>
            <div class="elderMessage layui-row">
                <div class="dataInput">
                    <label class="layui-form-label">主 食:</label>
                    <div class="layui-input-block">
                        <input type="text" name="dStapleFood" class="layui-input" id="dStapleFood" readonly>
                    </div>
                </div>
                <div class="dataInput">
                    <label class="layui-form-label">蔬 菜:</label>
                    <div class="layui-input-block">
                        <input type="text" name="dVegetable" class="layui-input" id="dVegetable" readonly>
                    </div>
                </div>
                <div class="dataInput">
                    <label class="layui-form-label">肉 类:</label>
                    <div class="layui-input-block">
                        <input type="text" name="dMeat" class="layui-input" id="dMeat" readonly>
                    </div>
                </div>
                <div class="dataInput">
                    <label class="layui-form-label">汤 类:</label>
                    <div class="layui-input-block">
                        <input type="text" name="dSoup" class="layui-input" id="dSoup" readonly>
                    </div>
                </div>
            </div>
        </div>
        <div class="nurseType">
            <div class="nurseTypeTitle">
                <i class="layui-icon">&#xe63c;</i>内容提交
            </div>
            <div class="layui-btn-container" style="text-align: end">
                <button type="submit" class="layui-btn" lay-submit lay-filter="formUserSubmit">提交</button>
                <button type="reset" class="layui-btn layui-btn-warm" id="resetBtn">重置</button>
            </div>
        </div>
    </div>
</form>
<script src="${ctx}/static/lib/layui/layui.js" charset="UTF-8"></script>
<script src="${ctx}/static/js/jquery.min.js" charset="UTF-8"></script>
<script src="${ctx}/static/js/myUtils.js" charset="utf-8"></script>
<script src="${ctx}/static/js/layuiVerifyExt.js" charset="utf-8"></script>
<script>
    layui.use(function () {
        var layer = layui.layer,
            $ = layui.jquery,
            form = layui.form;
        createH5Select("elderId",'${ctx}/reservation/selectBerth',null,null,function (){
            form.render('select'); //刷新select选择框渲染
        });
        form.on('select(demo)', function(data){
            var elderId = data.value;
            $.post("${ctx}/reservation/selectBerthData",{elderId:elderId},function (jsonData){
                if(jsonData.state){
                    $("#elderSite").val(jsonData.data.berthNumber);
                }
            })
            $.post("${ctx}/reservation/selectElderNurse",{elderId:elderId},function (jsonData){
                var nurseTypeId = jsonData.nurseType;
                createH5Select("breakfast",'${ctx}/reservation/selectBreakfast',{comboGrade:nurseTypeId,comboType:1},null,function (){
                    form.render('select'); //刷新select选择框渲染
                });
                createH5Select("lunch",'${ctx}/reservation/selectLunch',{comboGrade:nurseTypeId,comboType:2},null,function (){
                    form.render('select'); //刷新select选择框渲染
                });
                createH5Select("dinner",'${ctx}/reservation/selectDinner',{comboGrade:nurseTypeId,comboType:3},null,function (){
                    form.render('select'); //刷新select选择框渲染
                });
            })
        });
        form.on('select(breakfast)', function(data){
            var comboId = data.value;
            $.post("${ctx}/reservation/selectComboById",{comboId:comboId},function (jsonData){
                $("#bStapleFood").val(jsonData.stapleFood);
                $("#bVegetable").val(jsonData.vegetable);
                $("#bMeat").val(jsonData.meat);
                $("#bSoup").val(jsonData.soup);
            })
        })
        form.on('select(lunch)', function(data){
            var comboId = data.value;
            $.post("${ctx}/reservation/selectComboById",{comboId:comboId},function (jsonData){
                $("#lStapleFood").val(jsonData.stapleFood);
                $("#lVegetable").val(jsonData.vegetable);
                $("#lMeat").val(jsonData.meat);
                $("#lSoup").val(jsonData.soup);
            })
        })
        form.on('select(dinner)', function(data){
            var comboId = data.value;
            $.post("${ctx}/reservation/selectComboById",{comboId:comboId},function (jsonData){
                $("#dStapleFood").val(jsonData.stapleFood);
                $("#dVegetable").val(jsonData.vegetable);
                $("#dMeat").val(jsonData.meat);
                $("#dSoup").val(jsonData.soup);
            })
        })

        //新增、修改表单提交事件  submit(提交按钮的lay-filter属性的值)
        form.on('submit(formUserSubmit)', function(fromData){
            var upFormData=new FormData();
            for (var x in fromData.field) {
                upFormData.append(x,fromData.field[x])
            }
            var elderName = $("#elderId").find("option:selected").text();
            upFormData.append("elderName",elderName);
            var layerIndex=layer.load();
            //"${ctx}/reception/insertAppointment"
            $.ajax({
                type: "POST",//文件上传 只能是post
                url: "${ctx}/reservation/insertOrder",
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
                        $("#resetBtn").click();
                    }else{
                        layer.msg(jsonMsg.msg,{icon:5});
                    };
                }
            });
            return false; //阻止表单跳转。如果需要表单跳转，去掉这段即可。
        });
    })
</script>
</body>
</html>
