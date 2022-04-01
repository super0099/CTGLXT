<%--
  Created by IntelliJ IDEA.
  User: super007
  Date: 2021/9/18
  Time: 10:54
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" scope="page"/>
<html>
<head>
    <title>每月长者数据统计</title>
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
        .bigBox{
            background: #dcedff;
        }
        .leftTitle{
            background: #4476A7;
        }
        .main{
            float: left;
            margin-top: 5%;
            width: 550px;
            height: 550px;
            margin-left: 5%;
        }
        .rightTextarea{
            margin-top: 14%;
            float: left;
        }
        .rightTextarea textarea{
            width: 402px;
            height: 300px;
            resize: none;
            border: 1px solid #FF00FF;
        }
    </style>
</head>
<body>
<div class="layui-inline title">
    <ul class="layui-tab-title">
        <li class="home">
            <i class="layui-icon">&#xe63c;</i>每月长者数据统计
        </li>
    </ul>
</div>
<div class="bigBox">
    <div class="leftBox">
        <div class="leftTitle"><span>在住长者</span></div>
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
        <div class="main" id="main"></div>
        <div class="rightTextarea">
            <textarea id="rightTextarea" readonly style="display: none;"></textarea>
        </div>
    </div>
</div>
<script src="${ctx}/static/lib/layui/layui.js" charset="UTF-8"></script>
<script src="${ctx}/static/js/jquery.min.js" charset="UTF-8"></script>
<script src="${ctx}/static/js/echarts.js" charset="UTF-8"></script>
<script>
    $(function (){
        //未到期
        $.post("${ctx}/unsubscribe/selectExpireElder",{},function (jsonData){
            var leftBodyBody = document.getElementById("leftBodyBody");
            var leftBodyBodyChild = leftBodyBody.childNodes;
            for (var i=leftBodyBodyChild.length-1;i >= 0; i--){
                leftBodyBody.removeChild(leftBodyBodyChild[i]);
            }

            for (var i =0;i<jsonData.length;i++){
                console.log(jsonData[i])
                leftBodyBody.innerHTML+='<ul onclick="selectElders('+jsonData[i].appointmentId+')" class="clearfix"><li class="layui-col-md1"><i class="layui-icon">&#xe602;</i></li><li class="layui-col-md4">'+jsonData[i].berthId+'</li><li class="layui-col-md3">'+jsonData[i].elderName+'</li><li class="layui-col-md4">&nbsp;</li></ul>';
            }
        })
    })

    function selectElders(checkInCode){
        var chartDom = document.getElementById('main');
        var rightTextarea = document.getElementById('rightTextarea');
        rightTextarea.style.display="block";
        console.log(checkInCode);
        $.post("${ctx}/consume/selectNurseTypeCost",{appointmentId:checkInCode},function (jsonData){
            var myChart = echarts.init(chartDom);
            var option;
            option = {
                tooltip: {
                    trigger: 'item'
                },
                legend: {
                    top: '5%',
                    left: 'center'
                },
                series: [
                    {
                        name: '费用来源',
                        type: 'pie',
                        radius: ['40%', '70%'],
                        avoidLabelOverlap: false,
                        itemStyle: {
                            borderRadius: 10,
                            borderColor: '#fff',
                            borderWidth: 2
                        },
                        label: {
                            show: false,
                            position: 'center'
                        },
                        emphasis: {
                            label: {
                                show: true,
                                fontSize: '40',
                                fontWeight: 'bold'
                            }
                        },
                        labelLine: {
                            show: false
                        },
                        data: [
                            {value: jsonData.boardWages, name: '伙食费'},
                            {value: jsonData.berth, name: '床位费'},
                            {value: jsonData.serviceCharge, name: '护理费'},
                            {value:jsonData.alimony, name: '设施费'}
                        ]
                    }
                ]
            };
            option && myChart.setOption(option);
            document.getElementById("rightTextarea").innerText="该长者的床位费为"+jsonData.berth+",伙食费为"+jsonData.boardWages+",护理费为"+jsonData.serviceCharge+",设施费为"+jsonData.alimony+"。";
        })
    }

</script>
</body>
</html>
