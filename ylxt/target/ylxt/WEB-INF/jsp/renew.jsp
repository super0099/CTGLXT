<%--
  Created by IntelliJ IDEA.
  User: super007
  Date: 2021/9/17
  Time: 14:33
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" scope="page"/>
<html>
<head>
    <title>办理续约</title>
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
        .leftTitle{
            background: #4476A7;
        }
        .record{
            height: 84%;
        }
        .recordBut textarea{
            width: 100%;
            resize: none;
            height: 10%;
            border: 1px solid #4476A7;
            color: #01AAED;
            border-radius: 5px;
        }
    </style>
</head>
<body>
<div class="layui-inline title">
    <ul class="layui-tab-title">
        <li class="home">
            <i class="layui-icon">&#xe665;</i>办理续约
        </li>
    </ul>
</div>
<div class="bigBox">
    <div class="leftBox">
        <div class="leftTitle"><span>长者详细</span></div>
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
        <div class="record">
            <h4>续约办理</h4>
            <div class="layui-card-body recordBut">
                <textarea id="hint" readonly></textarea>
            </div>
            <div class="layui-card-body recordBut">
                <div class="dataInput" style="width: 30%">
                    <label class="layui-form-label">到期时间:</label>
                    <div class="layui-input-block">
                        <input type="date" name="endDate" class="layui-input" id="endDates" lay-verify="required">
                    </div>
                </div>
            </div>
            <div class="layui-card-body recordBut" style="margin-top: 28%">
                <div class="layui-btn-container" style="text-align: end" id="unBtn">
                </div>
            </div>
        </div>
    </div>
</div>
<script src="${ctx}/static/lib/layui/layui.js" charset="UTF-8"></script>
<script src="${ctx}/static/js/jquery.min.js" charset="UTF-8"></script>
<script>
    $(function (){
        //已到期
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
    })
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
            var hint = document.getElementById("hint");
            hint.innerText+="尊敬的"+jsonData.elderName+"长者您好,感谢你选择了安乐居,但是由于你签约的期限从 "+parsing(jsonData.startDate)+" 到 "+parsing(jsonData.endDate)+" 目前已经到期,请问你需要办理续约吗?如果是请在下面选择你最新的到期日期,祝你生活愉快";
            var unBtn = document.getElementById("unBtn");
            var unBtnChild = unBtn.childNodes;
            for (var i=unBtnChild.length-1;i >= 0; i--){
                unBtn.removeChild(unBtnChild[i]);
            }
            unBtn.innerHTML+='<button type="button" class="layui-btn" onclick="contract('+jsonData.checkInCode+')">续约</button>';
        })


    }
    function contract(checkInCode){
        var endDate = $("#endDates").val();
        var endDates = $("#endDate").val();
        if (endDate==null||endDate==""||endDate==undefined){
            layer.msg("请选择合约的日期");
            return;
        }
        var ed = new Date(endDate);
        if(judge(endDates,ed.getFullYear()+"-"+(ed.getMonth()+1)+"-"+ed.getDate())!=true){
            layer.msg("日期选择不正确");
            return;
        }
        layer.confirm('该长者确定要续约吗?', {icon: 3, title:'提示'}, function(index){
            layer.close(index);//关闭询问框
            //发送请求
            var layerIndex=layer.load();//打开加载层
            $.post('${ctx}/unsubscribe/contract',{checkInCode:checkInCode,endDate:endDate},function (jsonMsg){
                layer.close(layerIndex);//关闭加载层
                if (jsonMsg.state){//正常
                    layer.msg(jsonMsg.msg,{icon:6});
                    window.location.reload();
                }else {
                    layer.msg(jsonMsg.msg,{icon:5});
                }
            });
        });
    }
    function parsing(rowData){
        var date=new Date(rowData);
        return date.getFullYear()+"-"+(date.getMonth()+1)+"-"+date.getDate();
    }
    function judge(starDate,endDate){
        var sd = new Date(starDate);
        var ed = new Date(endDate);
        if(sd>ed){
            return false;
        }else if(sd<ed){
            return true;
        }else {
            return false;
        }
    }
</script>
</body>
</html>
