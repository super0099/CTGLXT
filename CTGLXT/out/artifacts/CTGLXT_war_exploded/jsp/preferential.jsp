<%@ page import="com.yxm.po.dbUser" %><%--
  Created by IntelliJ IDEA.
  User: super007
  Date: 2021/5/12
  Time: 21:29
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><!--需要引入jstl-1.2.jar包-->
<c:set var="ctx" value="${pageContext.request.contextPath}" scope="page"/><!--需要引入jstl-1.2.jar包-->
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" href="${ctx}/static/css/preferential.css">
</head>
<body>
<div class="preferential">
    <div class="preferential-top">
        <p>我的优惠卷</p>
    </div>
    <div class="myPreferential clearfix">
        <p class="fl">当前优惠卷:&nbsp;&nbsp;&nbsp;<b id="preferential">0</b>&nbsp;张</p><button class="fl" onclick="gain()">获取</button>
    </div>
    <div class="preferential-body clearfix" id="PreferentialCont">
    </div>
</div>
<script src="${ctx}/static/js/jquery.min.js" charset="utf-8"></script>
<script src="${ctx}/static/lib/layui/layui.js" charset="utf-8"></script>
<script>
    <%dbUser dbuser = (dbUser) request.getAttribute("dbuser");%>
    $(function (){
        var userId = '<%=dbuser.getId()%>';
        $.post("${ctx}/preferential?mothed=selectPreferential",{userId:userId},function (data){
            $("#preferential").text(data);
        })
        $.post("${ctx}/preferential?mothed=selectPreferentialList",{userId:userId},function (data){
            console.log(data);
            var PreferentialCont = document.getElementById("PreferentialCont");
            if(data.length==0){
                PreferentialCont.style.background="url('${ctx}/static/img/背景.jpg')";
                PreferentialCont.style.backgroundSize = "100%";
            }else {
                for(var i = 0;i<data.length;i++){
                    PreferentialCont.innerHTML+='<div class="content fl" onclick="openPreferential('+data[i].id+')"><input type="text" hidden id="Preferential'+i+'" value="'+data[i].id+'"><span>'+data[i].discountsName+'</span><input type="text" hidden id="diss" value="'+data[i].discount+'"></div>';
                }
            }
        })
    })
    function gain(){
        layer.msg("抱歉还没开放该功能",{icon:5});
    }
    function openPreferential(o){
        layer.msg("抱歉还没开放该功能",{icon:5});
    }
</script>
</body>
</html>
