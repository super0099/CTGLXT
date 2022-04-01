<%--
  Created by IntelliJ IDEA.
  User: super007
  Date: 2021/9/11
  Time: 8:23
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" scope="page"/>
<html>
<head>
    <title>欢迎页面</title>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport"
          content="width=device-width,user-scalable=yes, minimum-scale=0.4, initial-scale=0.8"/>
    <meta http-equiv="Cache-Control" content="no-siteapp"/>
    <link rel="stylesheet" href="${ctx}/static/lib/layui/css/layui.css">
    <link rel="stylesheet" href="${ctx}/static/css/welcome.css">
    <!--[if lt IE 9]>
    <script src="https://cdn.staticfile.org/html5shiv/r29/html5.min.js"></script>
    <script src="https://cdn.staticfile.org/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
    <style>
        .scoll{
            height: 40px;
            font-size: 16px;
            color: #FFB800;
        }
        .leftVideo{
            position: absolute;
            width: 30%;
            height: 67%;
            top: 20%;
            left: 40px;
        }
        .leftVideo video{
            width: 100%;
            height: 100%;
        }
    </style>
</head>
<body>
<div class="scoll" id="scoll">

</div>
<div class="leftVideo" >
    <h4>视频介绍:本视频由好看视频提供</h4>
    <video src="${ctx}/static/img/mda-kj64n1e4r5f99nsv.mp4" autoplay="autoplay" muted="muted"></video>
</div>
<script src="${ctx}/static/lib/layui/layui.js" charset="UTF-8"></script>
<script src="${ctx}/static/js/jquery.min.js" charset="UTF-8"></script>
<script>
    $(function (){
        var scoll = document.getElementById("scoll");
        var str = [];

        $.post("${ctx}/home/selectIssueAll",{},function (jsonData){
            for(var i = 0;i<jsonData.length;i++){
                str.push(jsonData[i].content)
            }
            var count = str.length;
            var i = 0;
            var scollChild = scoll.children;
            scoll.innerHTML+='<marquee behavior="scoll" direction="left"><span style="display: inline-block;"><b>'+str[i]+'</b></span></marquee>';
            window.setInterval(function (){
                if(scollChild!=0){
                    for (var j = scollChild.length - 1; j >= 0; j--) {
                        scoll.removeChild(scollChild[j]);
                    }
                }
                if(i<count-1||i==count-1){
                    scoll.innerHTML+='<marquee behavior="scoll" direction="left"><span style="display: inline-block;"><b>'+str[i]+'</b></span></marquee>';
                }
                if (i>count-1){
                    i=0;
                    scoll.innerHTML+='<marquee behavior="scoll" direction="left"><span style="display: inline-block;"><b>'+str[i]+'</b></span></marquee>';
                }
                i++;
            },23000)
        })
    })

</script>
</body>
</html>
