<%--
  Created by IntelliJ IDEA.
  User: super007
  Date: 2021/9/16
  Time: 16:34
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" scope="page"/>
<html>
<head>
    <title>到期提醒</title>
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
        .guarantee{
            margin: 2% 0.5% 0 0.5%;
            width: 48%;
            background: #C2BE9E;
            border-radius: 5px;
            padding: 0.5%;
            float: left;
        }
        .guaranteeTitle{
            border-radius: 3px;
            background: #FFB800;
            margin-bottom: 10px;
        }
        .BMessage{
            float: left;
            margin: 5px;
        }
        .dataInput{
            width: 50%;
            display: inline-block;
        }
    </style>
</head>
<body>
<div class="layui-inline title">
    <ul class="layui-tab-title">
        <li class="home">
            <i class="layui-icon">&#xe645;</i>到期提醒
        </li>
    </ul>
</div>
<div class="bigBox">
    <div class="leftBox">
        <div class="leftTitle"><span>到期长者详细</span></div>
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
        <div class="rightBoxBody clearfix" style="height: 84%">
            <h4>长者信息</h4>
            <div class="elderImg">
                <img src="${ctx}/static/img/uploadImg.png" alt="照片" id="userPicture">
            </div>
            <h4>担保人信息</h4>
            <div class="bottomData clearfix">
                <div class="guarantee">
                    <div class="guaranteeTitle">
                        <i class="layui-icon">&#xe612;</i>担保人信息1
                    </div>
                    <div class="BMessage layui-row">
                        <div class="dataInput">
                            <label class="layui-form-label">担保人姓名:</label>
                            <div class="layui-input-block">
                                <input type="text" name="bondsmanName1" class="layui-input" id="bondsmanName1"   readonly>
                            </div>
                        </div>
                        <div class="dataInput">
                            <label class="layui-form-label">身份证号:</label>
                            <div class="layui-input-block">
                                <input type="text" name="IDNumber1" class="layui-input" id="IDNumber1" readonly>
                            </div>
                        </div>
                        <div class="dataInput">
                            <label class="layui-form-label">工作单位:</label>
                            <div class="layui-input-block">
                                <input type="text" name="workUnit1" class="layui-input" id="workUnit1"   readonly>
                            </div>
                        </div>
                        <div class="dataInput">
                            <label class="layui-form-label">家庭地址:</label>
                            <div class="layui-input-block">
                                <input type="text" name="homeAddress1" class="layui-input" id="homeAddress1"   readonly>
                            </div>
                        </div>
                        <div class="dataInput">
                            <label class="layui-form-label">手机号码:</label>
                            <div class="layui-input-block">
                                <input type="text" name="phone1" class="layui-input" id="phone1"  readonly>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="guarantee">
                    <div class="guaranteeTitle">
                        <i class="layui-icon">&#xe612;</i>担保人信息2
                    </div>
                    <div class="BMessage layui-row">
                        <div class="dataInput">
                            <label class="layui-form-label">担保人姓名:</label>
                            <div class="layui-input-block">
                                <input type="text" name="bondsmanName2" class="layui-input" id="bondsmanName2"   readonly>
                            </div>
                        </div>
                        <div class="dataInput">
                            <label class="layui-form-label">身份证号:</label>
                            <div class="layui-input-block">
                                <input type="text" name="IDNumber2" class="layui-input" id="IDNumber2" readonly>
                            </div>
                        </div>
                        <div class="dataInput">
                            <label class="layui-form-label">工作单位:</label>
                            <div class="layui-input-block">
                                <input type="text" name="workUnit2" class="layui-input" id="workUnit2"   readonly>
                            </div>
                        </div>
                        <div class="dataInput">
                            <label class="layui-form-label">家庭地址:</label>
                            <div class="layui-input-block">
                                <input type="text" name="homeAddress2" class="layui-input" id="homeAddress2"   readonly>
                            </div>
                        </div>
                        <div class="dataInput">
                            <label class="layui-form-label">手机号码:</label>
                            <div class="layui-input-block">
                                <input type="text" name="phone2" class="layui-input" id="phone2"  readonly>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<script src="${ctx}/static/js/jquery.min.js" charset="UTF-8"></script>
<script src="${ctx}/static/lib/layui/layui.js" charset="UTF-8"></script>
<script>
    $(function (){
        $.post("${ctx}/expire/selectExpireElder",{},function (jsonData){
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
            $("#userPicture").prop("src","${ctx}/home/getPortraitImage?imgName="+jsonData.portrait);
            $.post("${ctx}/expire/selectElderBondsman",{appointmentId:jsonData.checkInCode},function (obj){
                for(var i = 0;i<obj.length;i++){
                    if (i==0){
                        $("#bondsmanName1").val(obj[i].bondsmanName);
                        $("#IDNumber1").val(obj[i].idnumber);
                        $("#workUnit1").val(obj[i].workUnit);
                        $("#homeAddress1").val(obj[i].homeAddress);
                        $("#phone1").val(obj[i].phone);
                    }
                    if (i==1){
                        $("#bondsmanName2").val(obj[i].bondsmanName);
                        $("#IDNumber2").val(obj[i].idnumber);
                        $("#workUnit2").val(obj[i].workUnit);
                        $("#homeAddress2").val(obj[i].homeAddress);
                        $("#phone2").val(obj[i].phone);
                    }
                }
            })
        })
    }
    function parsing(rowData){
        var date=new Date(rowData);
        return date.getFullYear()+"-"+(date.getMonth()+1)+"-"+date.getDate();
    }
</script>
</body>
</html>
