/**
 * jquery 根据json对象填充form表单
 * @author en
 * @param fromId form表单id
 * @param jsonDate json对象
 */
function loadDatatoForm(fromId, jsonDate) {
    var obj = jsonDate;
    var key, value, tagName, type, arr;
    for (x in obj) {//循环json对象
        key = x;
        value = obj[x];
        //$("[name='"+key+"'],[name='"+key+"[]']").each(function(){
        //更加form表单id 和 json对象中的key查找 表单控件
        $("#" + fromId + " [name='" + key + "'],#" + fromId + " [name='" + key + "[]']").each(function () {
            tagName = $(this)[0].tagName;
            type = $(this).attr('type');
            if (tagName == 'INPUT') {
                if (type == 'radio') {
                    $(this).attr('checked', $(this).val() == value);
                } else if (type == 'checkbox') {
                    try {
                        //数组
                        arr = value.split(',');
                        for (var i = 0; i < arr.length; i++) {
                            if ($(this).val() == arr[i]) {
                                $(this).attr('checked', true);
                                break;
                            }
                        }
                    } catch (e) {
                        //单个
                        $(this).attr('checked', value);
                    }
                } else {
                    $(this).val(value);
                }
            } else if (tagName == 'TEXTAREA') {
                $(this).val(value);
            } else if (tagName == 'SELECT') {
                //console.log($(this).hasClass("select2"));
                if ($(this).hasClass("select2")) {
                    //select2 插件的赋值方法
                    $(this).val(value).trigger("change");
                } else {
                    $(this).val(value);
                }

            }

        });
    }
}

/**
 * 按照数字顺序排序的排序函数 array[] 数字排序 从小到大
 * @param {} a 
 * @param {} b 
 * @returns {} 
 */
function numberorderAsc(a, b) { return a - b; }

/**
 * 按照数字顺序排序的排序函数  array[] 数字排序 从大到小
 * @param {} a 
 * @param {} b 
 * @returns {} 
 */
function numberorderDesc(a, b) { return b - a; }

/**
 * jquery 根据json对象创建Select下拉框
 * @author en
 * @param fromId form表单id
 * @param jsonDate json对象
 */
function selectLoadData(selectId, jsonData) {
    if (selectId.indexOf('#') != 0) {
        selectId = '#' + selectId;
    }
    $(selectId).empty();//清空该元素
    //内部前置内容
    $(selectId).prepend('<option value="' + 0 + '">' + "--请选择--" + '</option>');
    for (k in jsonData) {
        $(selectId).append('<option value="' + jsonData[k].id + '">' + jsonData[k].text + '</option>');
    }
}

/**
 * jquery 根据url创建下拉框
 * @author en
 * @param fromId form表单id
 * @param url 查询下拉框的url
 * @param value 设置值
 */
function createSelect(selectId, url, value) {
    //console.log(defaultValue);
    $.post(url, function (jsonData) {
        if (selectId.indexOf('#') != 0) {
            selectId = '#' + selectId;
        }
        $(selectId).empty();//清空该元素
        //内部前置内容
        $(selectId).prepend('<option value="' + 0 + '">' + "--请选择--" + '</option>');
        //创建option
        for (k in jsonData) {
            $(selectId).append('<option value="' + jsonData[k].id + '">' + jsonData[k].text + '</option>');
        }
        //设置选中值
        if (value != undefined && value != null && value != '') {
            $(selectId).val(value);
        }
    });
}


/**
 * jquery 验证身份证合法性的js
 *分别对15和18位的身份证号进行验证，非常严格。
 * @param value 身份证号
 */
function idCardNo(value) {
    //验证身份证号方法
    var area = { 11: "北京", 12: "天津", 13: "河北", 14: "山西", 15: "内蒙古", 21: "辽宁", 22: "吉林", 23: "黑龙江", 31: "上海", 32: "江苏", 33: "浙江", 34: "安徽", 35: "福建", 36: "江西", 37: "山东", 41: "河南", 42: "湖北", 43: "湖南", 44: "广东", 45: "广西", 46: "海南", 50: "重庆", 51: "四川", 52: "贵州", 53: "云南", 54: "西藏", 61: "陕西", 62: "甘肃", 63: "青海", 64: "宁夏", 65: "xinjiang", 71: "台湾", 81: "香港", 82: "澳门", 91: "国外" }
    var idcard, Y, JYM;
    var idcard = value;
    var S, M;
    var idcard_array = new Array();
    idcard_array = idcard.split("");
    if (area[parseInt(idcard.substr(0, 2))] == null) return false;
    switch (idcard.length) {
        case 15:
            if ((parseInt(idcard.substr(6, 2)) + 1900) % 4 == 0 || ((parseInt(idcard.substr(6, 2)) + 1900) % 100 == 0 && (parseInt(idcard.substr(6, 2)) + 1900) % 4 == 0)) {
                ereg = /^[1-9][0-9]{5}[0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|[1-2][0-9]))[0-9]{3}$/; //测试出生日期的合法性
            }
            else {
                ereg = /^[1-9][0-9]{5}[0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|1[0-9]|2[0-8]))[0-9]{3}$/; //测试出生日期的合法性
            }
            if (ereg.test(idcard))
                //return Errors[0];
                var res = true;
            else
                //return Errors[2];
                var res = false;
            return res;
            break;
        case 18:
            if (parseInt(idcard.substr(6, 4)) % 4 == 0 || (parseInt(idcard.substr(6, 4)) % 100 == 0 && parseInt(idcard.substr(6, 4)) % 4 == 0)) {
                ereg = /^[1-9][0-9]{5}(19|20)[0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|[1-2][0-9]))[0-9]{3}[0-9Xx]$/; //闰年出生日期的合法性正则表达式 
            } else {
                ereg = /^[1-9][0-9]{5}(19|20)[0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|1[0-9]|2[0-8]))[0-9]{3}[0-9Xx]$/; //平年出生日期的合法性正则表达式 
            }
            if (ereg.test(idcard)) {
                S = (parseInt(idcard_array[0]) + parseInt(idcard_array[10])) * 7 + (parseInt(idcard_array[1]) + parseInt(idcard_array[11])) * 9 + (parseInt(idcard_array[2]) + parseInt(idcard_array[12])) * 10 + (parseInt(idcard_array[3]) + parseInt(idcard_array[13])) * 5 + (parseInt(idcard_array[4]) + parseInt(idcard_array[14])) * 8 + (parseInt(idcard_array[5]) + parseInt(idcard_array[15])) * 4 + (parseInt(idcard_array[6]) + parseInt(idcard_array[16])) * 2 + parseInt(idcard_array[7]) * 1 + parseInt(idcard_array[8]) * 6 + parseInt(idcard_array[9]) * 3;
                Y = S % 11;
                M = "F";
                JYM = "10X98765432";
                M = JYM.substr(Y, 1);
                if (M == idcard_array[17])
                    //return Errors[0];
                    var res = true;
                else
                    //return Errors[3];
                    var res = false;
            }
            else
                //return Errors[2];
                res = false;
            return res;
            break;
        default:
            res = false;
            return res;
            break;
    };
}

//根据身份证号获取年龄
function getAgeByIdCard(identityCard) {
    var len = (identityCard + "").length;
    var strBirthday = "";
    if (len == 18)//处理18位的身份证号码从号码中得到生日和性别代码
    {
        strBirthday = identityCard.substr(6, 4) + "/" + identityCard.substr(10, 2) + "/" + identityCard.substr(12, 2);
    }
    if (len == 15) {
        var birthdayValue = '';
        birthdayValue = identityCard.charAt(6) + identityCard.charAt(7);
        if (parseInt(birthdayValue) < 10) {
            strBirthday = "20" + identityCard.substr(6, 2) + "/" + identityCard.substr(8, 2) + "/" + identityCard.substr(10, 2);
        } else {
            strBirthday = "19" + identityCard.substr(6, 2) + "/" + identityCard.substr(8, 2) + "/" + identityCard.substr(10, 2);
        }

    }
    //时间字符串里，必须是“/”
    var birthDate = new Date(strBirthday);
    var nowDateTime = new Date();
    var age = nowDateTime.getFullYear() - birthDate.getFullYear();
    //再考虑月、天的因素;.getMonth()获取的是从0开始的，这里进行比较，不需要加1
    if (nowDateTime.getMonth() < birthDate.getMonth() || (nowDateTime.getMonth() == birthDate.getMonth() && nowDateTime.getDate() < birthDate.getDate())) {
        age--;
    }
    return age;
}

/**
 * 自定义alert弹窗，尝试调用最外层的layui弹窗方法，如果没有就调用JavaScript的alert
 * @param {string} content 内容
 * @param {string} title 标题 - 可选
 * @param {number} icon  - 可选
 * @returns {} 
 */
function myAlert(content, title, icon) {
    var options = {};
    if (icon == undefined || icon === '' || isNaN(parseInt(icon))) {
        options.icon = 0;
    } else {
        options.icon = parseInt(icon);
    }
    if (title != undefined && title != '') {
        options.title = title;
    } else {
        options.title = '提示';
    }
    //尝试调用top窗口的layer，如果没有就调用JavaScript的alert
    try {
        top.layer.alert(content, options);
    } catch (e) {
        top.alert(content);
    }
}



/**
 * 航班起飞时间 到达时间 转字符串
 *
 * @param {} timeStart 起飞时间
 * @param {} timeEnd 到达时间
 * @returns {} 
 */
function flightTimeToStr(timeStart, timeEnd) {
    var str = "";
    //起飞时间 小数
    str += timeStart.Hours < 10 ? "0" : "";
    str += timeStart.Hours + ":";
    //起飞时间 分钟
    str += timeStart.Minutes < 10 ? "0" : "";
    str += timeStart.Minutes;
    str += " - ";
    //降落时间 小数
    str += timeEnd.Hours < 10 ? "0" : "";
    str += timeEnd.Hours + ":";
    //降落时间 分钟
    str += timeEnd.Minutes < 10 ? "0" : "";
    str += timeEnd.Minutes;

    if (timeEnd.Ticks < timeStart.Ticks) {
        str += '<sup class="text-danger">+1</sup>';
    }

    return str;
}


