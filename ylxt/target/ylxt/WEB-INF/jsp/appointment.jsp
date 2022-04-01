<%--
  Created by IntelliJ IDEA.
  User: super007
  Date: 2021/9/4
  Time: 9:56
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" scope="page"/>
<html>
<head>
    <title>预约</title>
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
            <i class="layui-icon">&#xe62d;</i>办理预约
        </li>
    </ul>
</div>
<form class="layui-form" action="" autocomplete="off">
    <div class="elderData clearfix">
        <div class="elderTitle">
            <i class="layui-icon">&#xe770;</i>入住人信息
        </div>
        <div class="BigBox">
            <div class="elderImg">
                <img src="${ctx}/static/img/uploadImg.png" alt="照片" id="userPicture">
                <input type="file" id="elderImg" hidden onchange="loadUImg()">
            </div>
            <div class="elderMessage layui-row">
                <div class="dataInput">
                    <label class="layui-form-label">入住号:</label>
                    <div class="layui-input-block">
                        <input type="text" name="checkInCode" class="layui-input" id="checkInCode" readonly value="${count}">
                    </div>
                </div>
                <div class="dataInput">
                    <label class="layui-form-label">姓 名:</label>
                    <div class="layui-input-block">
                        <input type="text" name="elderName" class="layui-input" id="elderName" lay-verify="required">
                    </div>
                </div>
                <div class="dataInput">
                    <label class="layui-form-label">性 别:</label>
                    <div class="layui-input-block">
                        <select name="gender" id="gender">
                            <option value="1">男</option>
                            <option value="2">女</option>
                        </select>
                    </div>
                </div>
                <div class="dataInput">
                    <label class="layui-form-label">年 龄:</label>
                    <div class="layui-input-block">
                        <input type="number" name="age" class="layui-input" id="age" lay-verify="required">
                    </div>
                </div>
            </div>
            <div class="elderMessage">
                <div class="dataInput">
                    <label class="layui-form-label">籍 贯:</label>
                    <div class="layui-input-block">
                        <input type="text" name="nativePlace" class="layui-input" id="nativePlace" lay-verify="required">
                    </div>
                </div>
                <div class="dataInput">
                    <label class="layui-form-label">民 族:</label>
                    <div class="layui-input-block">
                        <input type="text" name="nation" class="layui-input" id="nation" lay-verify="required">
                    </div>
                </div>
                <div class="dataInput">
                    <label class="layui-form-label">婚姻状况:</label>
                    <div class="layui-input-block">
                        <select name="marriage" id="marriage">
                            <option value="1">已婚</option>
                            <option value="2">未婚</option>
                        </select>
                    </div>
                </div>
                <div class="dataInput">
                    <label class="layui-form-label">出生日期:</label>
                    <div class="layui-input-block">
                        <input type="date" name="birth" class="layui-input" id="birth" lay-verify="required">
                    </div>
                </div>
            </div>
            <div class="elderMessage">
                <div class="dataInput">
                    <label class="layui-form-label">医保类型:</label>
                    <div class="layui-input-block">
                        <select name="healthInsuranceId" id="healthInsurance">
                            <option value="0">没有医保</option>
                            <option value="1">职工医保</option>
                            <option value="2">居民医保</option>
                        </select>
                    </div>
                </div>
                <div class="dataInput">
                    <label class="layui-form-label">当前状态:</label>
                    <div class="layui-input-block">
                        <select name="state" id="state">
                            <option value="1">预约中</option>
                        </select>
                    </div>
                </div>
                <div class="dataInput">
                    <label class="layui-form-label">护理类型:</label>
                    <div class="layui-input-block">
                        <select name="nurseType" lay-filter="demo" id="nurseType">
                        </select>
                    </div>
                </div>
                <div class="dataInput">
                    <label class="layui-form-label">入住地点:</label>
                    <div class="layui-input-block">
                        <select name="site" lay-verify="required" id="site">
                        </select>
                    </div>
                </div>
            </div>
            <div class="elderMessage">
                <div class="dataInput">
                    <label class="layui-form-label">现住址:</label>
                    <div class="layui-input-block">
                        <input type="text" name="residence" class="layui-input" id="residence" lay-verify="required">
                    </div>
                </div>
                <div class="dataInput">
                    <label class="layui-form-label">家庭电话:</label>
                    <div class="layui-input-block">
                        <input type="text" name="homePhone" class="layui-input" id="homePhone" lay-verify="phone">
                    </div>
                </div>
                <div class="dataInput">
                    <label class="layui-form-label">证件类别:</label>
                    <div class="layui-input-block">
                        <select name="credentialsType" id="credentialsType">
                            <option value="1">居民身份证</option>
                            <option value="2">军人证件</option>
                        </select>
                    </div>
                </div>
                <div class="dataInput">
                    <label class="layui-form-label">证件号:</label>
                    <div class="layui-input-block">
                        <input type="text" name="IDCode" class="layui-input" id="IDCode" lay-verify="identity">
                    </div>
                </div>
            </div>
            <div class="elderMessage">
                <div class="dataInput">
                    <label class="layui-form-label">开始时间:</label>
                    <div class="layui-input-block">
                        <input type="date" name="startDate" class="layui-input" id="startDate" lay-verify="required">
                    </div>
                </div>
                <div class="dataInput">
                    <label class="layui-form-label">结束时间:</label>
                    <div class="layui-input-block">
                        <input type="date" name="endDate" class="layui-input" id="endDate" lay-verify="required">
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="bottomData clearfix">
        <div class="guarantee">
            <div class="guaranteeTitle">
                <i class="layui-icon">&#xe612;</i>担保人信息1
            </div>
            <div class="elderMessage layui-row">
                <div class="dataInput">
                    <label class="layui-form-label">担保人姓名:</label>
                    <div class="layui-input-block">
                        <input type="text" name="bondsmanName1" class="layui-input" id="bondsmanName1" lay-verify="required">
                    </div>
                </div>
                <div class="dataInput">
                    <label class="layui-form-label">关系:</label>
                    <div class="layui-input-block">
                        <select name="relation1" id="relation1" lay-verify="required">
                            <option value="1">父子</option>
                            <option value="2">父女</option>
                            <option value="3">母子</option>
                            <option value="4">母女</option>
                            <option value="5">伴侣</option>
                        </select>
                    </div>
                </div>
                <div class="dataInput">
                    <label class="layui-form-label">身份证号:</label>
                    <div class="layui-input-block">
                        <input type="text" name="IDNumber1" class="layui-input" id="IDNumber1" lay-verify="identity">
                    </div>
                </div>
                <div class="dataInput">
                    <label class="layui-form-label">工作单位:</label>
                    <div class="layui-input-block">
                        <input type="text" name="workUnit1" class="layui-input" id="workUnit1" lay-verify="required">
                    </div>
                </div>
                <div class="dataInput">
                    <label class="layui-form-label">家庭地址:</label>
                    <div class="layui-input-block">
                        <input type="text" name="homeAddress1" class="layui-input" id="homeAddress1" lay-verify="required">
                    </div>
                </div>
                <div class="dataInput">
                    <label class="layui-form-label">手机号码:</label>
                    <div class="layui-input-block">
                        <input type="text" name="phone1" class="layui-input" id="phone1" lay-verify="phone">
                    </div>
                </div>
            </div>
        </div>
        <div class="guarantee">
            <div class="guaranteeTitle">
                <i class="layui-icon">&#xe612;</i>担保人信息2
            </div>
            <div class="elderMessage layui-row">
                <div class="dataInput">
                    <label class="layui-form-label">担保人姓名:</label>
                    <div class="layui-input-block">
                        <input type="text" name="bondsmanName2" class="layui-input" id="bondsmanName2" lay-verify="required">
                    </div>
                </div>
                <div class="dataInput">
                    <label class="layui-form-label">关系:</label>
                    <div class="layui-input-block">
                        <select name="relation2" id="relation2" lay-verify="required">
                            <option value="1">父子</option>
                            <option value="2">父女</option>
                            <option value="3">母子</option>
                            <option value="4">母女</option>
                            <option value="5">伴侣</option>
                        </select>
                    </div>
                </div>
                <div class="dataInput">
                    <label class="layui-form-label">身份证号:</label>
                    <div class="layui-input-block">
                        <input type="text" name="IDNumber2" class="layui-input" id="IDNumber2" lay-verify="identity">
                    </div>
                </div>
                <div class="dataInput">
                    <label class="layui-form-label">工作单位:</label>
                    <div class="layui-input-block">
                        <input type="text" name="workUnit2" class="layui-input" id="workUnit2" lay-verify="required">
                    </div>
                </div>
                <div class="dataInput">
                    <label class="layui-form-label">家庭地址:</label>
                    <div class="layui-input-block">
                        <input type="text" name="homeAddress2" class="layui-input" id="homeAddress2" lay-verify="required">
                    </div>
                </div>
                <div class="dataInput">
                    <label class="layui-form-label">手机号码:</label>
                    <div class="layui-input-block">
                        <input type="text" name="phone2" class="layui-input" id="phone2" lay-verify="phone">
                    </div>
                </div>
            </div>
        </div>
        <div class="nurseType">
            <div class="nurseTypeTitle">
                <i class="layui-icon">&#xe656;</i>护理级别费用标准
            </div>
            <div class="elderMessage layui-row">
                <div class="dataInput">
                    <label class="layui-form-label">生活设施费:</label>
                    <div class="layui-input-block">
                        <input type="number" name="alimony" class="layui-input" id="alimony" readonly value="0">
                    </div>
                </div>
                <div class="dataInput">
                    <label class="layui-form-label">床位费:</label>
                    <div class="layui-input-block">
                        <input type="number" name="berth" class="layui-input" id="berth" readonly value="0">
                    </div>
                </div>
                <div class="dataInput">
                    <label class="layui-form-label">伙食费:</label>
                    <div class="layui-input-block">
                        <input type="number" name="boardWages" class="layui-input" id="boardWages" readonly value="0">
                    </div>
                </div>
                <div class="dataInput">
                    <label class="layui-form-label">护理费:</label>
                    <div class="layui-input-block">
                        <input type="number" name="serviceCharge" class="layui-input" id="serviceCharge" readonly value="0">
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
    var form,layer;
    $(function (){
        createH5Select("nurseType",'${ctx}/reception/selectNurseTypeBerth',null,null,function (){
            form.render('select'); //刷新select选择框渲染
        });
    })
    layui.use(function () {

        var $ = layui.jquery;
        layer = layui.layer,
            form = layui.form;
        form.on('select(demo)', function(data){
            if(data.value==0){
                $("#alimony").val(0);
                $("#berth").val(0);
                $("#boardWages").val(0);
                $("#serviceCharge").val(0);
            }else {
                $.post("${ctx}/reception/NurseType",{id:data.value},function (jsonData){
                    if(jsonData.state){
                        $("#alimony").val(jsonData.data.alimony);
                        $("#berth").val(jsonData.data.berth);
                        $("#boardWages").val(jsonData.data.boardWages);
                        $("#serviceCharge").val(jsonData.data.serviceCharge);
                    }
                })
                createH5Select("site",'${ctx}/reception/selectBerth',{grade:data.value},null,function (){
                    form.render('select'); //刷新select选择框渲染
                });
            }
        });
        //新增、修改表单提交事件  submit(提交按钮的lay-filter属性的值)
        form.on('submit(formUserSubmit)', function(fromData){
            var starDate = fromData.field.startDate;
            var endDate = fromData.field.endDate;
            if(judge(starDate,endDate)!=true){
                layer.msg("结束日期不能小于开始日期");
                return false;
            }
            var upFormData=new FormData();
            for (var x in fromData.field) {
                upFormData.append(x,fromData.field[x])
            }
            //把文件添加到upFormData
            var file = $("#elderImg").get(0).files[0];
            upFormData.append("portraitFile",file);
            // 提交表单
            var layerIndex=layer.load();
            //"${ctx}/reception/insertAppointment"
            $.ajax({
                type: "POST",//文件上传 只能是post
                url: "${ctx}/reception/insertAppointment",
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
    });
    //图片上传部分
    //双击图片弹出文件选择框
    $("#userPicture").dblclick(function () {
        $("#elderImg").click();
    });
    //图片文件 正则表达式过滤image/jpeg,image/png,image/jpg,image/gif,image/bmp
    var regexImageFilter = /^(?:image\/bmp|image\/gif|image\/jpg|image\/jpeg|image\/png)$/i;
    var imgReader = new FileReader();

    //文件读取器读取到文件后的回调事件
    imgReader.onload = function (event) {
        //显示图片 base64编码的图片
        $("#userPicture").attr("src", event.target.result);
    }

    function loadUImg(){
        //获取出文件选择器中的第一个文件
        var file = $("#elderImg").get(0).files[0];
        //判断文件选择类型
        if (regexImageFilter.test(file.type)) {
            //读取文件转换成URL把图片转为Base64编码
            imgReader.readAsDataURL(file);
        } else {
            layer.alert("请选择图片");
        }
    };
    function judge(starDate,endDate){
        var sd = new Date(starDate);
        var ed = new Date(endDate);
        if(sd>ed){
            return false;
        }else if(sd<ed){
            return true;
        }
    }
</script>
</body>
</html>
