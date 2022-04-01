<%--
  Created by IntelliJ IDEA.
  User: super007
  Date: 2021/9/11
  Time: 18:44
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" scope="page"/>
<html>
<head>
    <title>收入查询</title>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport"
          content="width=device-width,user-scalable=yes, minimum-scale=0.4, initial-scale=0.8"/>
    <meta http-equiv="Cache-Control" content="no-siteapp"/>
    <link rel="stylesheet" href="${ctx}/static/lib/layui/css/layui.css">
    <link rel="stylesheet" href="${ctx}/static/css/income.css">
    <!--[if lt IE 9]>
    <script src="https://cdn.staticfile.org/html5shiv/r29/html5.min.js"></script>
    <script src="https://cdn.staticfile.org/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
    <style>
        .MonthInput{
            float: left;
            margin-right: 5px;
            width: 95px;
            font-size: 12px;
        }
        .MonthInput label{
            text-align: center;
            width: 36px;
            background: #FF5722;
            float: left;
            padding: 3px;
            border-radius: 3px;
        }
        .Months input{
            margin-top: 5px;
            margin-left: 3px;
            text-align: center;
            border: none;
            outline: none;
            width: 50px;
            float: left;
            color: #01AAED;
            background: rgba(0,0,0,0);
        }
    </style>
</head>
<body>
<div class="layui-tab layui-tab-card">
    <ul class="layui-tab-title">
        <li class="layui-this" onclick="selectBerth(1)" value="1">柱状图数据分析</li>
        <li onclick="selectBerth(2)" value="2">文字数据分析</li>
    </ul>
    <div class="columnar clearfix" id="columnar" style="display: block;">
        <div id="main" class="main"></div>
        <div class="columnarText">
            <div class="layui-inline layui-show-xs-block">
                <label class="layui-form-label inputLabel" style="padding: 9px 5px">年份:</label>
                <div class="layui-input-inline layui-show-xs-block">
                    <input type="text" id="buyerName" placeholder="输入年份查询" class="layui-input" style="height: 40px" autocomplete="off">
                </div>
            </div>
            <div class="layui-input-inline layui-show-xs-block">
                <button class="layui-btn" type="button" id="btnSearch">
                    <i class="layui-icon layui-icon-search"></i>
                </button>
            </div>
            <div class="textarea">
                <textarea id="textareas" readonly>

                </textarea>
            </div>
        </div>
        <div class="MonthSum clearfix">
            <div class="MonthInput">
                <label>一月</label>
                <div class="Months">
                    <input type="text" value="99999" readonly id="Jan">
                </div>
            </div>
            <div class="MonthInput">
                <label>二月</label>
                <div class="Months">
                    <input type="text" value="99999" readonly id="Feb">
                </div>
            </div>
            <div class="MonthInput">
                <label>三月</label>
                <div class="Months">
                    <input type="text" value="99999" readonly id="Mar">
                </div>
            </div>
            <div class="MonthInput">
                <label>四月</label>
                <div class="Months">
                    <input type="text" value="99999" readonly id="Apr">
                </div>
            </div>
            <div class="MonthInput">
                <label>五月</label>
                <div class="Months">
                    <input type="text" value="99999" readonly id="May">
                </div>
            </div>
            <div class="MonthInput">
                <label>六月</label>
                <div class="Months">
                    <input type="text" value="99999" readonly id="June">
                </div>
            </div>
            <div class="MonthInput">
                <label>七月</label>
                <div class="Months">
                    <input type="text" value="99999" readonly id="July">
                </div>
            </div>
            <div class="MonthInput">
                <label>八月</label>
                <div class="Months">
                    <input type="text" value="99999" readonly id="Aug">
                </div>
            </div>
            <div class="MonthInput">
                <label>九月</label>
                <div class="Months">
                    <input type="text" value="99999" readonly id="Sept">
                </div>
            </div>
            <div class="MonthInput">
                <label>十月</label>
                <div class="Months">
                    <input type="text" value="99999" readonly id="OCT">
                </div>
            </div>
            <div class="MonthInput">
                <label>十一月</label>
                <div class="Months">
                    <input type="text" value="99999" readonly id="Nov">
                </div>
            </div>
            <div class="MonthInput">
                <label>十二月</label>
                <div class="Months">
                    <input type="text" value="99999" readonly id="Dece">
                </div>
            </div>
        </div>
    </div>
    <div class="character" style="display: none" id="characterBox">
        <div class="layui-card-body billTable">
            <table class="layui-hide" id="character" lay-filter="bill"></table>
        </div>
        <div class="StrTextarea">
            <textarea id="textareaA" readonly>

            </textarea>
        </div>
    </div>
</div>

<script src="${ctx}/static/lib/layui/layui.js" charset="UTF-8"></script>
<script src="${ctx}/static/js/jquery.min.js" charset="UTF-8"></script>
<script src="${ctx}/static/js/echarts.js" charset="UTF-8"></script>
<script>
    var table;
    $(function (){
        var years = new Date().getFullYear();
        $.post('${ctx}/cost/selectIncomeColumnarVo',{years:years},function (jsonData){
            var myChart = echarts.init(document.getElementById('main'));
            // 使用刚指定的配置项和数据显示图表。
            myChart.setOption(jsonData.optionColumnarData);
            $("#textareas").val(jsonData.stringDate);
            $("#textareaA").val(jsonData.stringDate);
            $("#Jan").val(jsonData.sysMonthVo.jan);
            $("#Feb").val(jsonData.sysMonthVo.feb);
            $("#Mar").val(jsonData.sysMonthVo.mar);
            $("#Apr").val(jsonData.sysMonthVo.apr);
            $("#May").val(jsonData.sysMonthVo.may);
            $("#June").val(jsonData.sysMonthVo.june);
            $("#July").val(jsonData.sysMonthVo.july);
            $("#Aug").val(jsonData.sysMonthVo.aug);
            $("#Sept").val(jsonData.sysMonthVo.sept);
            $("#OCT").val(jsonData.sysMonthVo.oct);
            $("#Nov").val(jsonData.sysMonthVo.nov);
            $("#Dece").val(jsonData.sysMonthVo.dece);
        });

        $("#btnSearch").click(function (){
            var year = $("#buyerName").val();
            if(year==null||year==""){
                years = new Date().getFullYear();
            }else {
                years = year;
            }
            $.post('${ctx}/cost/selectIncomeColumnarVo',{years:years},function (jsonData){
                var myChart = echarts.init(document.getElementById('main'));
                // 使用刚指定的配置项和数据显示图表。
                myChart.setOption(jsonData.optionColumnarData);
                $("#textareas").val(jsonData.stringDate);
                $("#textareaA").val(jsonData.stringDate);
                $("#Jan").val(jsonData.sysMonthVo.jan);
                $("#Feb").val(jsonData.sysMonthVo.feb);
                $("#Mar").val(jsonData.sysMonthVo.mar);
                $("#Apr").val(jsonData.sysMonthVo.apr);
                $("#May").val(jsonData.sysMonthVo.may);
                $("#June").val(jsonData.sysMonthVo.june);
                $("#July").val(jsonData.sysMonthVo.july);
                $("#Aug").val(jsonData.sysMonthVo.aug);
                $("#Sept").val(jsonData.sysMonthVo.sept);
                $("#OCT").val(jsonData.sysMonthVo.oct);
                $("#Nov").val(jsonData.sysMonthVo.nov);
                $("#Dece").val(jsonData.sysMonthVo.dece);

            });
        })
    })
    layui.use(function () {
        table = layui.table;
        var $ = layui.$,
            layer = layui.layer,
            form = layui.form,
            tree = layui.tree;
    });
    function selectBerth(o){
        if(o==1){
            document.getElementById("columnar").style.display="block";
            document.getElementById("characterBox").style.display="none";
        }
        if (o==2){
            document.getElementById("columnar").style.display="none";
            document.getElementById("characterBox").style.display="block";
            var tableRole = table.render({
                elem: '#character',//table元素
                id: 'character',
                url: '${ctx}/cost/selectIncomeAll',//数据url
                page:true,
                cols: [[
                    {field: 'id', title: 'id', event: 'id',hide: true},
                    {field: 'incomeType',title: '收入类型'},
                    {field: 'sumMoney', title: '收入金额'},
                    {field: 'incomeDate',title: '到账时间', event: 'incomeDate',templet:function (rowData){
                            var dt = new Date(rowData.incomeDate);
                            var minute = dt.getMinutes()<10?"0"+dt.getMinutes():dt.getMinutes();
                            var second = dt.getSeconds()<10?"0"+dt.getSeconds():dt.getSeconds();
                            return dt.getFullYear() + "-" + (dt.getMonth() + 1) + "-" + dt.getDate()+" "+
                                dt.getHours()+":"+minute+":"+second;
                        }}
                ]],
                limit: 10,//每页页数
                limits: [5, 10]//定义每页页数选择下拉框
            });
        }
    }
</script>
</body>
</html>
