<%--
  Created by IntelliJ IDEA.
  User: super007
  Date: 2021/9/5
  Time: 15:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" scope="page"/>
<html>
<head>
    <title>长者信息</title>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport"
          content="width=device-width,user-scalable=yes, minimum-scale=0.4, initial-scale=0.8"/>
    <meta http-equiv="Cache-Control" content="no-siteapp"/>
    <link rel="stylesheet" href="${ctx}/static/lib/layui/css/layui.css">
    <!--[if lt IE 9]>
    <script src="https://cdn.staticfile.org/html5shiv/r29/html5.min.js"></script>
    <script src="https://cdn.staticfile.org/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
    <style>
        body{
            height: 100%;
        }
        .inputBox{
            padding: 20px 5px;
        }
        .inputLabel{
            padding: 10px 0;
            margin-right: 10px;
        }
        .layui-table-cell .layui-form-radio[lay-type="layTableRadio"]{
            top: 50%;
            transform: translateY(-50%);
        }
        .dataInput{
            height: 45px;
        }
        .layui-form-label{
            padding: 11px 5px;
        }
        .layui-container{
            width: 100%;
        }
    </style>
</head>
<body>
<div>
    <ul class="layui-tab-title">
        <li class="home">
            <i class="layui-icon">&#xe656;</i>长者详情
        </li>
    </ul>
</div>
<%--主容器布局--%>
<div class="layui-fluid bigBox">
    <div class="layui-row layui-col-space15">
        <div class="layui-col-md12">
            <div class="layui-card">
                <%--搜索栏--%>
                <div class="layui-card-body ">
                    <form class="layui-form layui-col-space5">
                        <div class="layui-inline layui-show-xs-block">
                            <label class="layui-form-label inputLabel">姓名</label>
                            <div class="layui-input-inline layui-show-xs-block">
                                <input type="text" id="elderName" placeholder="姓名" class="layui-input inputBox">
                            </div>
                        </div>
                        <div class="layui-inline layui-show-xs-block">
                            <label class="layui-form-label inputLabel">床位号:</label>
                            <div class="layui-input-inline layui-show-xs-block">
                                <input type="text" id="berthNumber" placeholder="床位号" class="layui-input inputBox">
                            </div>
                        </div>
                        <div class="layui-input-inline layui-show-xs-block">
                            <button class="layui-btn" type="button" id="btnSearch">
                                <i class="layui-icon layui-icon-search"></i>
                            </button>
                        </div>
                    </form>
                </div>
                <%--操作栏--%>
                <div class="layui-card-header">
                    <button class="layui-btn layui-btn-normal" id="btnEdit">
                        <i class="layui-icon layui-icon-edit"></i>修改
                    </button>
                    <button class="layui-btn layui-btn-danger" id="btnDelete">
                        <i class="layui-icon layui-icon-delete"></i>删除
                    </button>
                </div>
                <%--表格--%>
                <div class="layui-card-body ">
                    <table class="layui-hide" id="tableElder" lay-filter="tableRoleEvent"></table>
                </div>
            </div>
        </div>
    </div>
</div>
<div>
    <div style="display: none" id="upPortraitDiv">
        <div>
            <input type="file" hidden name="portrait" id="upPortrait"><%--头像上传文本框--%>
        </div>
    </div>
    <form class="layui-form" id="formUser" lay-filter="formUser" style="display: none;margin: 20px;"
          autocomplete="off">
        <%--修改时存放主键id--%>
        <input type="hidden" name="id">
        <div class="layui-container">
            <div class="layui-row">
                <div class="layui-col-md3">
                    <img src="${ctx}/static/images/uploadImg.png" width="221" id="userPicture" alt="头像"
                         style="max-height: 340px;">
                </div>
                <div class="layui-col-md9">
                    <div class="layui-form-item">
                        <div class="layui-inline">
                            <label class="layui-form-label">姓名</label>
                            <div class="layui-input-block">
                                <input type="text" name="elderName" lay-verify="required" class="layui-input dataInput">
                            </div>
                        </div>
                        <div class="layui-inline">
                            <label class="layui-form-label">性别</label>
                            <div class="layui-input-block">
                                <input type="radio" name="elderGender" value="1" lay-verify="requiredRadioCheckbox"
                                       title="男">
                                <input type="radio" name="elderGender" value="2" lay-verify="requiredRadioCheckbox"
                                       title="女">
                            </div>
                        </div>
                    </div>
                    <div class="layui-form-item">
                        <div class="layui-inline">
                            <label class="layui-form-label">生日</label>
                            <div class="layui-input-block">
                                <input type="text" name="birthday" id="birthday" lay-verify="birthday"
                                       class="layui-input dataInput">
                            </div>
                        </div>
                        <div class="layui-inline">
                            <label class="layui-form-label">身份证号</label>
                            <div class="layui-input-block">
                                <input type="text" id="idnumber" name="idnumber" class="layui-input dataInput"
                                       lay-verify="required" lay-filter="departmentIdTreeSelect">
                            </div>
                        </div>
                    </div>
                    <div class="layui-form-item">
                        <div class="layui-inline">
                            <label class="layui-form-label">家庭地址</label>
                            <div class="layui-input-block">
                                <input type="text" id="residence" name="residence" class="layui-input dataInput"
                                       lay-verify="required" lay-filter="departmentIdTreeSelect">
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="layui-btn-container" style="text-align: end">
            <button type="submit" class="layui-btn" lay-submit lay-filter="formUserSubmit">提交</button>
            <button type="reset" class="layui-btn layui-btn-warm">重置</button>
        </div>
    </form>
</div>
<script src="${ctx}/static/js/jquery.min.js" charset="UTF-8"></script>
<script src="${ctx}/static/lib/layui/layui.js" charset="UTF-8"></script>
<script src="${ctx}/static/js/myUtils.js" charset="utf-8"></script>
<script src="${ctx}/static/js/layuiVerifyExt.js" charset="utf-8"></script>
<script>
    var layerFormIndex;
    layui.use(function(){
        var table = layui.table,
            $ = layui.$,laydate = layui.laydate,
            layer = layui.layer,
            form = layui.form,
            tree=layui.tree;
        // 菜单树形table初始化
        var tableRole = table.render({
            elem: '#tableElder',//table元素
            id: 'tableElder',
            url: '${ctx}/reception/selectElderAll',//数据url
            page: true,//分页
            cols: [[
                {type: 'radio'},//单选框
                {field: 'elderName', title: '姓名', width: 120,},
                {field: 'id', title: 'id', event: 'id', width: 120, hide: true},
                {field: 'elderGender', title: '性别',width: 60,templet:function (rowData){
                    if (rowData.elderGender === 1) {//男
                        return '男';
                    } else {
                        return '女';
                    }
                }},
                {field: 'berthNumber', title: '床位号', width: 120,},
                {field: 'birthday', title: '生日', event: 'birthday', width: 160, templet: function (rowData) {
                        var dt = new Date(rowData.birthday);
                        return dt.getFullYear() + "-" + (dt.getMonth() + 1) + "-" + dt.getDate();
                    }
                },
                {field: 'idnumber', title: '身份证号', event: 'idnumber'},
                {field: 'appointmentId', title: '入住号', event: 'appointmentId'},
                {field: 'portrait', title: '照片', event: 'portrait', width: 130, templet: function (rowData) {
                        return '<img src="${ctx}/home/getPortraitImage?imgName=' + rowData.portrait + '" class="portraitImg">';
                    }
                },
                {field: 'residence', title: '家庭地址', event: 'residence'}
            ]],
            limit: 10,//每页页数
            limits: [5, 10, 15, 20, 25, 30, 35, 40, 45, 50]//定义每页页数选择下拉框
        });
        //查询按钮
        $("#btnSearch").click(function (){
            var elderName=$("#elderName").val();
            var berthNumber=$("#berthNumber").val();
            //表格重载
            table.reload('tableElder', {
                where: {
                    elderName:elderName,
                    berthNumber:berthNumber
                } //设定异步数据接口的额外参数
            });
        });

        //打开修改弹窗
        $("#btnEdit").click(function (){
            //清空文件选择框
            document.getElementById("upPortrait").outerHTML=document.getElementById("upPortrait").outerHTML;
            $('#formUser [type="reset"]').click();//重置表单
            //获取table中勾选的数据
            var selectData= table.checkStatus('tableElder');
            if (selectData.data.length>0) {
                var elderId = selectData.data[0].id;
                //加载被修改数据
                $.post('${ctx}/reception/selectElderById',{elderId:elderId},function (jsonMsg){
                    if (jsonMsg.state) {//正常}
                        //回填表单数据
                        form.val("formUser",jsonMsg.data);
                        //显示图片
                        if (jsonMsg.data.portrait!=undefined && jsonMsg.data.portrait!=null && jsonMsg.data.portrait!=""){
                            $("#userPicture").prop("src",'${ctx}/home/getPortraitImage?imgName='+jsonMsg.data.portrait);
                        }else {
                            $("#userPicture").prop("src",'${ctx}/static/images/uploadImg.png');
                        }
                        var dt = new Date(jsonMsg.data.birthday);
                        var strDate=dt.getFullYear() + "-" + ((dt.getMonth() + 1)<10?"0":"")+(dt.getMonth() + 1) + "-" + (dt.getDate()<10?"0":"")+dt.getDate();
                        //表单 生日日期控件
                        laydate.render({
                            elem: '#birthday', //指定元素
                            type: 'date',
                            value:strDate
                        });
                        layerFormIndex = layer.open({
                            type: 1,//0（信息框，默认）1（页面层）2（iframe层）3（加载层）4（tips层）
                            skin:'layui-layer-molv',
                            area: ['900px', '500px'],
                            title:'修改长者信息',
                            content:$("#formUser"),
                            cancel:function (){
                                layer.closeAll();
                            }
                        });
                    }else {
                        layer.msg(jsonMsg.msg,{icon:5});
                    }
                });
            }else {
                layer.msg("请选择需要修改的数据",{icon:5});
            }
        });
        //提交
        form.on('submit(formUserSubmit)', function(fromData){
            var upFormData=new FormData();
            for (var x in fromData.field) {
                //append(名称，值)
                upFormData.append(x,fromData.field[x])
            }

            //把文件添加到upFormData
            var file = $("#upPortrait").get(0).files[0];
            upFormData.append("portraitFile",file);
            // 提交表单
            var layerIndex=layer.load();
            $.ajax({
                type: "POST",//文件上传 只能是post
                url: "${ctx}/reception/updateElder",
                data: upFormData,
                cache:false,
                processData:false,//禁止jquery对上传的数据进行处理
                contentType: false,
                dataType:'json',
                success: function(jsonMsg){
                    layer.close(layerIndex);
                    if (jsonMsg.state){
                        layer.msg(jsonMsg.msg,{icon:6});
                        layer.close(layerFormIndex);//关闭弹窗
                        table.reload('tableElder',{});//表格的重载
                    }else{
                        layer.msg(jsonMsg.msg,{icon:5});
                    };
                }
            });
            return false; //阻止表单跳转。如果需要表单跳转，去掉这段即可。
        });

        //删除
        $("#btnDelete").click(function (){
            //获取table中勾选的数据
            var selectData= table.checkStatus('tableElder');
            if (selectData.data.length>0){
                var elderId=selectData.data[0].id;
                layer.confirm('您确定要删除该长者吗?', {icon: 3, title:'提示'}, function(index){
                    layer.close(index);//关闭询问框
                    //发送请求
                    var layerIndex=layer.load();//打开加载层
                    $.post('${ctx}/reception/deleteElderById',{elderId:elderId},function (jsonMsg){
                        layer.close(layerIndex);//关闭加载层
                        if (jsonMsg.state){//正常
                            layer.msg(jsonMsg.msg,{icon:6});
                            table.reload('tableElder',{});//表格的重载
                        }else {
                            layer.msg(jsonMsg.msg,{icon:5});
                        }
                    });
                });
            }else {
                layer.msg("请选择需要删除的数据");
            }
        });

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

        $("#upPortraitDiv").on('change','input[type="file"]',function () {
            //获取出文件选择器中的第一个文件
            var file = $("#upPortrait").get(0).files[0];
            //判断文件选择类型
            if (regexImageFilter.test(file.type)) {
                //读取文件转换成URL把图片转为Base64编码
                imgReader.readAsDataURL(file);
            } else {
                layer.alert("请选择图片");
            }
        });
    });
</script>
</body>
</html>
