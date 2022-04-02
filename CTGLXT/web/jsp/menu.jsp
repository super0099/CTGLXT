<%@ page import="com.yxm.po.dbUser" %><%--
  Created by IntelliJ IDEA.
  User: super007
  Date: 2021/5/13
  Time: 11:28
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><!--需要引入jstl-1.2.jar包-->
<c:set var="ctx" value="${pageContext.request.contextPath}" scope="page"/><!--需要引入jstl-1.2.jar包-->
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" href="${ctx}/static/css/menu.css">
</head>
<body>
<div class="menu">
    <div class="menu-top">
        <p>我的收藏</p>
    </div>
    <div class="menu-body clearfix" id="menuCont">

    </div>
</div>
<script src="${ctx}/static/js/jquery.min.js" charset="utf-8"></script>
<script src="${ctx}/static/lib/layui/layui.js" charset="utf-8"></script>
<script>
    <%dbUser LoginUser = (dbUser)request.getAttribute("dbuser"); %>
    $(function (){
        var userId = <%=LoginUser.getId()%>
        $.post("${ctx}/homePage?mothed=selectCollect",{userId:userId},function (jsonMsg){
            var menuCont = document.getElementById("menuCont");
            for(var i = 0;i<jsonMsg.length;i++){
                menuCont.innerHTML+='<div class="menuCont fl"><div class="clearfix"><span class="fl">'+jsonMsg[i].menuName+'</span><a class="fr" onclick="removeMenu('+jsonMsg[i].id+')">移除</a></div><img src="${ctx}/modification?mothed=getPortraitImage&imgName='+jsonMsg[i].picture+'" onclick="openMenu('+jsonMsg[i].menuId+')"></div>';
            }
        });
    })
    function openMenu(o){
        window.parent.location.replace("${ctx}/particular?mothed=index&t="+o);
    }
    function removeMenu(o){
        layer.confirm("是否将该菜品移出收藏",{icon:3,title:"提示"},function (index){
            $.post("${ctx}/homePage?mothed=removeMenu",{Id:o},function (jsonMsg){
                if(jsonMsg.state){
                    layer.msg(jsonMsg.msg);
                    setTimeout(function (){
                        window.location.reload();
                    },500)
                }else {
                    layer.msg(jsonMsg.msg);
                }
            })
        })
    }
</script>
</body>
</html>
