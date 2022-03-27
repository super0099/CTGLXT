
/**
   下拉框元素的Id,下拉框数据的Url，参数，下拉框默认值
*/
function createH5Select(selectId, url, params, defaultValue,callBack) {
    // console.log(params);
    //1.清空以前的
    //判断是否是数组
    $("#" + selectId).empty();
    $("#" + selectId).append(' <option value="">---请选择----</option>');

    //请求 生成option
    $.post(url, params, function (jsonObj) {
        for (var i = 0; i < jsonObj.length; i++) {
            var strOption = '<option value="' + jsonObj[i].value + '">' + jsonObj[i].text + '</option>';
            $("#" + selectId).append(strOption);
        }

        //判断是否要设置初始值
        if (defaultValue != undefined && defaultValue != null && defaultValue != "") {
            $("#" + selectId).val(defaultValue);
        }
        if (typeof callBack == 'function'){
            callBack();
        }
    },'json');
}

/**
 * 工具类 获取Layui树形控件选中的id
 * @param arrayCheckData layuiTree getChecked()方法获取的数据
 * @returns {[]} 数组 Layui树形控件选中的id
 */
function getLayuiTreeCheckId(arrayCheckData){
    var rArray=[];
    if (Array.isArray(arrayCheckData)){//判断是否是数组
        //遍历数组 获取id
        for(var index=0;index<arrayCheckData.length;index++){
            //把id添加到 isArray
            rArray.push(arrayCheckData[index].id);
            //判断是否存在 子节点children ；存在就递归调用 获取出子节点的id
            if (arrayCheckData[index].children){
                rArray=rArray.concat(getLayuiTreeCheckId(arrayCheckData[index].children));
            }
        }
    }
    return rArray;
}