<%@ page import="com.yxm.po.dbUser" %><%--
  Created by IntelliJ IDEA.
  User: super007
  Date: 2021/5/13
  Time: 11:23
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><!--需要引入jstl-1.2.jar包-->
<c:set var="ctx" value="${pageContext.request.contextPath}" scope="page"/><!--需要引入jstl-1.2.jar包-->
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" href="${ctx}/static/css/site.css">
</head>
<body>
<div class="site">
    <div class="site-top">
        <p>我的地址</p>
    </div>
    <div class="site-body clearfix" id="site">
<%--        <div class="site-content">--%>
<%--            <div class="site-content-top clearfix">--%>
<%--                <span class="fl" style="margin-left: 15px;"><b>小明</b>&nbsp;先生</span>--%>
<%--                <span class="fr" style="margin-right: 15px;"><a onclick="alter()">修改</a>&nbsp;&nbsp;&nbsp;<a onclick="deletes()">删除</a></span>--%>
<%--            </div>--%>
<%--            <div class="site-content-con">--%>
<%--                <p>广东省广州市南沙区</p>--%>
<%--                <span>13126033786</span>--%>
<%--            </div>--%>
<%--        </div>--%>
        <div class="site-contents" onclick="openEditor()" id="addSite">
            <p><b>+</b>&nbsp;&nbsp;&nbsp;添加地址</p>
        </div>
    </div>
    <%--添加地址弹出层--%>
    <div class="popups" id="popups" style="display: none;">
        <div class="popups-content">
            <div class="popups-headline clearfix">
                <h6 class="fl">添加地址信息</h6>
                <i class="fr" id="close"></i>
            </div>
            <div class="popups-body">
                <form action="">
                    <div class="popupsName">
                        <input type="text" placeholder="请输入签收人" id="addressee"><br>
                        <input type="text" placeholder="请输入地址" id="Message"><br>
                        <input type="text" placeholder="请输入手机号" id="phone"><br>
                        <button type="reset" id="rest" hidden></button>
                        <button type="button" onclick="submits()">提交</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
    <%--修改弹出层--%>
    <div class="modification" id="modification" style="display: none;">
        <div class="popups-content">
            <div class="popups-headline clearfix">
                <h6 class="fl">修改地址信息</h6>
                <i class="fr" id="closes"></i>
            </div>
            <div class="popups-body">
                <form action="">
                    <div class="popupsName">
                        <input type="text" hidden id="SiteId" value="">
                        <input type="text" placeholder="请输入签收人" id="addressees"><br>
                        <input type="text" placeholder="请输入地址" id="Messages"><br>
                        <input type="text" placeholder="请输入手机号" id="phones"><br>
                        <button type="reset" id="rests" hidden></button>
                        <button type="button" onclick="compile()">修改</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
<script src="${ctx}/static/js/jquery.min.js" charset="utf-8"></script>
<script src="${ctx}/static/lib/layui/layui.js" charset="utf-8"></script>
<script>
    <%dbUser dbuser = (dbUser) request.getAttribute("dbuser");%>
    $(function (){
        var userId = <%=dbuser.getId()%>;
        $.post("${ctx}/site?mothed=selectSite",{userId:userId},function (data){
            var site = document.getElementById("site");
            var addSite = document.getElementById("addSite");
            console.log(data);
            for (var i = 0;i<data.length;i++){
                var div = document.createElement("div");
                div.className ="site-content";
                div.innerHTML='<div class="site-content-top clearfix"><span class="fl" style="margin-left: 15px;"><b>'+data[i].linkman+'</b>&nbsp;先生</span><span class="fr" style="margin-right: 15px;"><a onclick="alter('+data[i].id+')">修改</a>&nbsp;&nbsp;&nbsp;<a onclick="deletes('+data[i].id+')">删除</a></span></div><div class="site-content-con"><p>'+data[i].site+'</p><span>'+data[i].phone+'</span></div>';
                site.insertBefore(div,addSite);//往元素前添加元素
            }
        })
    })
    //
    $("#close").click(function (){
        $("#rest").click();
        $("#popups").prop("style","display: none;");
    })
    $("#closes").click(function (){
        $("#rests").click();
        $("#modification").prop("style","display: none;");
    })
    //打开新增弹窗
    function openEditor(){
        $("#popups").prop("style","display: block;");
    }
    function submits(){
        var phones = /^(13[0-9]|14[5|7]|15[0|1|2|3|5|6|7|8|9]|18[0|1|2|3|5|6|7|8|9])\d{8}$/;
        var phone = $("#phone").val();
        var Message = $("#Message").val();
        var addressee = $("#addressee").val();
        if(addressee==null||addressee==undefined||addressee==""){
            layer.msg("请输入联系人信息");
            return;
        }
        if(Message==null||Message==undefined||Message==""){
            layer.msg("请输入联系地址");
            return;
        }
        if(!phones.test(phone)){
            layer.msg("请输入正确的手机号");
            return;
        }
        var userId = <%=dbuser.getId()%>;
        $.post("${ctx}/site?mothed=addSite",{
            Phone:phone,
            Message:Message,
            addressee:addressee,
            userId:userId
        },function (jsonMsg){
            if(jsonMsg.state){
                layer.msg(jsonMsg.msg);
                $("#rest").click();
                $("#popups").prop("style","display: none;");
                setTimeout(function (){
                    window.location.reload();
                },500)
            }else {
                layer.msg(jsonMsg.msg);
            }
        })
    }
    //修改
    function alter(o){
        $("#modification").prop("style","display: block;");
        $.post("${ctx}/site?mothed=selectSites",{
            siteId:o
        },function (jsonMsg){
            $("#phones").val(jsonMsg.phone);
            $("#Messages").val(jsonMsg.site);
            $("#addressees").val(jsonMsg.linkman);
            $("#SiteId").val(jsonMsg.id);
        })
    }
    function compile(){
        var phones = /^(13[0-9]|14[5|7]|15[0|1|2|3|5|6|7|8|9]|18[0|1|2|3|5|6|7|8|9])\d{8}$/;
        var siteId=$("#SiteId").val()
        var phone=$("#phones").val();
        var Message=$("#Messages").val();
        var addressee=$("#addressees").val();
        if(addressee==null||addressee==undefined||addressee==""){
            layer.msg("请输入联系人信息");
            return;
        }
        if(Message==null||Message==undefined||Message==""){
            layer.msg("请输入联系地址");
            return;
        }
        if(!phones.test(phone)){
            layer.msg("请输入正确的手机号");
            return;
        }
        $.post("${ctx}/site?mothed=compileSite",{
            siteId:siteId,
            Phone:phone,
            Message:Message,
            addressee:addressee
        },function (jsonMsg){
            if(jsonMsg.state){
                layer.msg(jsonMsg.msg);
                setTimeout(function (){
                    window.location.reload();
                },500)
            }else {
                layer.msg(jsonMsg.msg);
            }
        })
    }
    //删除
    function deletes(o){
        layer.confirm("是否删除",{icon:3,title:'提示'},function (index){
            $.post("${ctx}/site?mothed=deleteSite",{
                siteId:o
            },function (jsonMsg){
                if(jsonMsg.state){
                    layer.msg(jsonMsg.msg);
                    setTimeout(function (){
                        window.location.reload();
                    },500)
                }else {
                    layer.msg(jsonMsg.msg);
                }
            });
        })
    }
</script>
</body>
</html>
