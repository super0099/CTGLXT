<%@ page import="com.yxm.po.dbUser" %><%--
  Created by IntelliJ IDEA.
  User: super007
  Date: 2021/5/13
  Time: 10:01
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><!--需要引入jstl-1.2.jar包-->
<c:set var="ctx" value="${pageContext.request.contextPath}" scope="page"/><!--需要引入jstl-1.2.jar包-->
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" href="${ctx}/static/css/myData.css">
</head>
<body>
<div class="myData">
    <div class="myData-top">
        <p>个人资料</p>
    </div>
    <div class="myData-body clearfix">
        <div class="myImg fl">
            <img src="" id="myUserImg">
        </div>
        <div class="myNickname fl">
            <span>昵&nbsp;&nbsp;&nbsp;称:</span>
            <b id="myNickname"></b><br>
            <span>用户名:</span>
            <a id="myUserName"></a>
        </div>
        <div class="editor fl">
            <button type="button" onclick="openEditor()">编辑</button>
        </div>
    </div>
    <div class="myEvaluate">
        <div class="myEvaluate-top">
            <p>我的评论</p>
        </div>
    </div>
    <%--修改个人信息弹出层--%>
    <div class="popups" id="popups" style="display: none;">
        <div class="popups-content">
            <div class="popups-headline clearfix">
                <h6 class="fl">修改个人信息</h6>
                <i class="fr" id="close"></i>
            </div>
            <div class="popups-body">
                <form autocomplete="off">
                    <div class="popupsImg fl">
                        <img src="" id="userPicture">
                        <input type="file" hidden id="upPortrait" onchange="loadUImgToImg()">
                    </div>
                    <div class="popupsName fl">
                        <input type="text" placeholder="请输入昵称" id="popupsName"><br>
                        <button type="reset" id="rest" hidden></button>
                        <button type="button" onclick="submits()">提交</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
<script src="${ctx}/static/js/jquery.min.js" charset="utf-8"></script>
<script src="${ctx}/static/lib/layui/layui.js" charset="utf-8"></script>
<script>
    <%dbUser LoginUser = (dbUser)request.getAttribute("dbuser"); %>
    $(function (){
        var userId = <%=LoginUser.getId()%>
        $.post("${ctx}/modification?mothed=selectUser",{userId:userId},function (jsonMsg){
            console.log(jsonMsg)
            if(jsonMsg.state){
                var data = jsonMsg.data;
                $("#myUserImg").prop("src","${ctx}/modification?mothed=getPortraitImage&imgName="+data.portrait);
                $("#myNickname").text(data.nickname);
                $("#myUserName").text(data.userName);
            }else {
                layer.msg(jsonMsg.msg,{icon:5})
            }
        });
    });
    $("#close").click(function (){
        $("#rest").click();
        $("#popups").prop("style","display: none;");
    })
    function openEditor(){
        $("#popups").prop("style","display: block;");
    }
    //编辑用户信息
    function submits(){
        var formData = new FormData();
        var file = $("#upPortrait").get(0).files[0];
        var name = $("#popupsName").val();
        formData.append("portrait",file);
        formData.append("nickname",name);
        formData.append("userId",'<%=LoginUser.getId()%>');
        var xhm = null;
        if (window.XMLHttpRequest) {
            xhm = new XMLHttpRequest();
        } else {
            xhm = new ActiveXObject("Microsoft.XMLHTTP");
        }
        xhm.open("post", "${ctx}/modification?mothed=modification");
        xhm.send(formData);
        xhm.onreadystatechange = function (jsonMsg) {
            if (xhm.readyState == 4 && xhm.status == 200) {
                var obj = JSON.parse(jsonMsg.currentTarget.responseText);
                if(obj.state){
                    layer.msg(obj.msg,{icon:6});
                    $("#popups").prop("style","display: none;");
                    window.location.reload();
                }else {
                    layer.msg(obj.msg,{icon:5});
                }
            }
        }
        <%--$.ajax({--%>
        <%--    type: "POST",//文件上传 只能是post--%>
        <%--    url: "${ctx}/modification?mothed=modification",--%>
        <%--    data: formData,--%>
        <%--    cache:false,//关闭缓存--%>
        <%--    processData:false,//禁止jquery对上传的数据进行处理--%>
        <%--    contentType: false,--%>
        <%--    dataType:'json',--%>
        <%--    success: function(jsonMsg){--%>
        <%--        if (jsonMsg.state){--%>
        <%--            layer.msg(jsonMsg.msg,{icon:6});--%>
        <%--        }else{--%>
        <%--            layer.msg(jsonMsg.msg,{icon:5});--%>
        <%--        };--%>
        <%--    }--%>
        <%--});--%>
    }




    //图片上传部分
    //双击图片弹出文件选择框
    $("#userPicture").dblclick(function () {
        $("#upPortrait").click();
    });
    //图片文件 正则表达式过滤image/jpeg,image/png,image/jpg,image/gif,image/bmp
    var regexImageFilter = /^(?:image\/bmp|image\/gif|image\/jpg|image\/jpeg|image\/png)$/i;
    var imgReader = new FileReader();

    //文件读取器读取到文件后的回调事件
    imgReader.onload = function (event) {
        //显示图片 base64编码的图片
        $("#userPicture").attr("src", event.target.result);
    }
    function loadUImgToImg() {
        //获取出文件选择器中的第一个文件
        var file = $("#upPortrait").get(0).files[0];
        //判断文件选择类型
        if (regexImageFilter.test(file.type)) {
            //读取文件转换成URL把图片转为Base64编码
            imgReader.readAsDataURL(file);
        } else {
            layer.alert("请选择图片");
        }
    }
</script>
</body>
</html>
