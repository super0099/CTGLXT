<%--
  Created by IntelliJ IDEA.
  User: super007
  Date: 2021/9/18
  Time: 11:25
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" scope="page"/>
<html>
<head>
    <title>月度结算</title>
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
        .leftBox{
            float: left;
            width: 50%;
            height: 96%;
        }
        .main{
            margin-top: 2.5%;
            width: 100%;
            height: 95%;
        }
        .rightBox{
            width: 46%;
            height: 96%;
            float: left;
        }
        .columnarText{
            width: 100%;
            margin-top: 10%;
            float: left;
        }

        .textarea{
            margin-top: 2%;
            width: 100%;
            height: 500px;
            padding: 1%;
        }
        .textarea textarea{
            width: 98%;
            height: 70%;
            resize: none;
            font-size: 16px;
            color: #01AAED;
            border-radius: 5px;
        }
        .StrTextarea textarea{
            width: 50%;
            height: 50%;
            resize: none;
            font-size: 16px;
            color: #01AAED;
            border-radius: 5px;
        }
    </style>
</head>
<body>
<div class="layui-inline title">
    <ul class="layui-tab-title">
        <li class="home">
            <i class="layui-icon">&#xe6b2;</i>月度结算
        </li>
    </ul>
</div>
<div class="bigBox clearfix">
    <div class="leftBox">
        <div class="main" id="main"></div>
    </div>
    <div class="rightBox">
        <div class="columnarText">
            <div class="layui-inline layui-show-xs-block">
                <label class="layui-form-label inputLabel" style="padding: 9px 5px">月份:</label>
                <div class="layui-input-inline layui-show-xs-block">
                    <input type="number" id="buyerName" placeholder="输入月份查询" class="layui-input" style="height: 40px" autocomplete="off">
                </div>
            </div>
            <div class="layui-input-inline layui-show-xs-block">
                <button class="layui-btn" type="button" id="btnSearch">
                    <i class="layui-icon layui-icon-search"></i>
                </button>
            </div>
            <div class="textarea">
                <textarea id="textarea" readonly>

                </textarea>
            </div>
        </div>
    </div>
</div>
<script src="${ctx}/static/lib/layui/layui.js" charset="UTF-8"></script>
<script src="${ctx}/static/js/jquery.min.js" charset="UTF-8"></script>
<script src="${ctx}/static/js/echarts.js" charset="UTF-8"></script>
<script>
    $(function (){
        var dt = new Date();
        var month = (dt.getMonth()+1);
        var year = dt.getFullYear();
        $.post("${ctx}/month/selectNurseTypeCost",{month:month,year:year},function (jsonData){
            $.post("${ctx}/month/selectIncomeCost",{month:month,year:year},function (obj){
                var myChart = echarts.init(document.getElementById('main'));
                var option;
                option = {
                    title: {
                        text: '当月收入类型总结',
                        subtext: month+'月份',
                        left: 'center'
                    },
                    tooltip: {
                        trigger: 'item'
                    },
                    legend: {
                        orient: 'vertical',
                        left: 'left',
                    },
                    series: [
                        {
                            name: '访问来源',
                            type: 'pie',
                            radius: '50%',
                            data: [
                                {value: jsonData.berth==undefined?0:jsonData.berth, name: '床位费'},
                                {value: jsonData.boardWages==undefined?0:jsonData.boardWages, name: '伙食费'},
                                {value: jsonData.alimony==undefined?0:jsonData.alimony, name: '设施费'},
                                {value: jsonData.serviceCharge==undefined?0:jsonData.serviceCharge, name: '护理费'},
                                {value: obj==undefined?0:obj, name: '政府津贴'}
                            ],
                            emphasis: {
                                itemStyle: {
                                    shadowBlur: 10,
                                    shadowOffsetX: 0,
                                    shadowColor: 'rgba(0, 0, 0, 0.5)'
                                }
                            }
                        }
                    ]
                };
                option && myChart.setOption(option);
                $.post("${ctx}/month/selectIncomeCount",{month:month,year:year},function (data){
                    var textarea = document.getElementById("textarea");
                    if(jsonData==undefined||jsonData==""||jsonData==null){
                        textarea.innerText="当月一共有"+data+"人付款,其中床位费总收入为"+0+"元;伙食费总收入为"+0+"元;设施费总收入为"+0+"元;护理费总收入为"+0+"元;政府津贴为"+obj+"元;"
                    }else {
                        textarea.innerText="当月一共有"+data+"人付款,其中床位费总收入为"+jsonData.berth+"元;伙食费总收入为"+jsonData.boardWages+"元;设施费总收入为"+jsonData.alimony+"元;护理费总收入为"+jsonData.serviceCharge+"元;政府津贴为"+obj+"元;"
                    }
                })
            })
        })
    })

    $("#btnSearch").click(function () {
        var months;
        var year = new Date().getFullYear();
        var month = $("#buyerName").val();
        if (month == null || month == "") {
            months = new Date().getMonth()+1;
        } else {
            months = month;
        }
        $.post("${ctx}/month/selectNurseTypeCost",{month:months,year:year},function (jsonData){
            console.log(jsonData);
            $.post("${ctx}/month/selectIncomeCost",{month:months,year:year},function (obj){
                var myChart = echarts.init(document.getElementById('main'));
                var option;
                option = {
                    title: {
                        text: '当月收入类型总结',
                        subtext: months+'月份',
                        left: 'center'
                    },
                    tooltip: {
                        trigger: 'item'
                    },
                    legend: {
                        orient: 'vertical',
                        left: 'left',
                    },
                    series: [
                        {
                            name: '访问来源',
                            type: 'pie',
                            radius: '50%',
                            data: [
                                {value: jsonData.berth==undefined?0:jsonData.berth, name: '床位费'},
                                {value: jsonData.boardWages==undefined?0:jsonData.boardWages, name: '伙食费'},
                                {value: jsonData.alimony==undefined?0:jsonData.alimony, name: '设施费'},
                                {value: jsonData.serviceCharge==undefined?0:jsonData.serviceCharge, name: '护理费'},
                                {value: obj==undefined?0:obj, name: '政府津贴'}
                            ],
                            emphasis: {
                                itemStyle: {
                                    shadowBlur: 10,
                                    shadowOffsetX: 0,
                                    shadowColor: 'rgba(0, 0, 0, 0.5)'
                                }
                            }
                        }
                    ]
                };
                option && myChart.setOption(option);
                $.post("${ctx}/month/selectIncomeCount",{month:month,year:year},function (data){
                    var textarea = document.getElementById("textarea");
                    if(jsonData==undefined||jsonData==""||jsonData==null){
                        textarea.innerText="当月一共有"+data+"人付款,其中床位费总收入为"+0+"元;伙食费总收入为"+0+"元;设施费总收入为"+0+"元;护理费总收入为"+0+"元;政府津贴为"+obj+"元;"
                    }else {
                        textarea.innerText="当月一共有"+data+"人付款,其中床位费总收入为"+jsonData.berth+"元;伙食费总收入为"+jsonData.boardWages+"元;设施费总收入为"+jsonData.alimony+"元;护理费总收入为"+jsonData.serviceCharge+"元;政府津贴为"+obj+"元;"
                    }
                })
            })
        })
    });
</script>
</body>
</html>
