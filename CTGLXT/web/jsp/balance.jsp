<%@ page import="com.yxm.po.dbUser" %><%--
  Created by IntelliJ IDEA.
  User: super007
  Date: 2021/5/13
  Time: 8:03
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><!--需要引入jstl-1.2.jar包-->
<c:set var="ctx" value="${pageContext.request.contextPath}" scope="page"/><!--需要引入jstl-1.2.jar包-->
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" href="${ctx}/static/css/balance.css">
</head>
<body>
<div class="balance">
    <div class="balance-top">
        <p>我的余额</p>
    </div>
    <div class="myBalance clearfix">
        <p class="fl">当前账户余额:&nbsp;&nbsp;&nbsp;<b id="balance"></b>&nbsp;元</p><button class="fl" onclick="openRecharge()">充值</button>
    </div>
    <div class="balance-body">
        <div class="condition clearfix">
            <span class="fl">时间:</span>
            <ul>
                <li onclick="chant(this)" class="liis" style="background: #1E9FFF;">全部</li>
            </ul>
        </div>
        <div class="headline clearfix">
            <ul>
                <li>创建时间</li>
                <li>交易类型</li>
                <li>金额更变</li>
                <li>余额</li>
            </ul>
        </div>
        <div class="information" id="information">

        </div>
    </div>
</div>
<div class="recharge" id="recharge" style="display: none;">
    <i onclick="closeRecharge()"></i>
    <div class="rechargeHeadline"><p>请输入你需要充值的金额</p></div>
    <img src="${ctx}/static/img/人民币.gif"><br>
    <input type="number" id="number"><br>
    <button type="button" onclick="recharge()">充 值</button>
</div>
<script src="${ctx}/static/js/jquery.min.js" charset="utf-8"></script>
<script src="${ctx}/static/lib/layui/layui.js" charset="utf-8"></script>
<script>
    <%dbUser dbUser = (dbUser)request.getAttribute("dbuser");%>
    var userId = <%=dbUser.getId()%>;
    $(function (){
        var userId =  '<%=dbUser.getId()%>';
        $.post("${ctx}/wallet?mothed=selectBalance",{userId:userId},function (data){
            $("#balance").text(data);
        });
        $.post("${ctx}/wallet?mothed=selectConsumption",{userId:userId},function (data){
            var information = document.getElementById("information");
            for (var i = 0;i<data.length;i++){
                var date = Dt(data[i].establishDate);
                information.innerHTML+='<div class="information-content"><ul><li><p>'+date+'</p></li><li><span>'+data[i].ctype+'</span></li><li><b>'+data[i].money+'</b>元</li><li><b>'+data[i].balance+'</b>元</li></ul></div>';
            }
        })
    });
    function Dt(o){
        var dt = new Date(o);
        return dt.getFullYear() + "-" + (dt.getMonth() + 1) + "-" + dt.getDate() + " " +
            dt.getHours() + ":" + dt.getMinutes() + ":" + dt.getSeconds();
    }

    function closeRecharge(){
        var recharge = document.getElementById("recharge");
        recharge.style.display="none";
    }
    function openRecharge(){
        var recharge = document.getElementById("recharge");
        recharge.style.display="block";
    }
    function recharge(){
        var rechargeNumber = $("#number").val();
        if(rechargeNumber==0||rechargeNumber<0||rechargeNumber==""){
            layer.msg("充值金额不能为0");
            return;
        }
        $.post("${ctx}/wallet?mothed=recharge",{
            userId:userId,
            rechargeNumber:rechargeNumber
        },function (data){
            if(data.state){
                layer.msg(data.msg);
                setTimeout(function (){
                    window.location.reload();
                },2000);
            }else {
                layer.msg(data.msg);
            }
        })
    }
</script>
</body>
</html>
