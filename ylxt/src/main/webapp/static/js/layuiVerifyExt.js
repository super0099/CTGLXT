/*
layui form表单校验扩展
sxj 2021年3月10日
 */
var formVerifyExt = {
    //layui form自带的规则的可为空校验扩展
    phoneNr: [/(^$)|^1\d{10}$/, "请输入正确的手机号(可为空)"],
    emailNr: [/(^$)|^([a-zA-Z0-9_.\-])+@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/, "邮箱格式不正确(可为空)"],
    urlNr: [/(^$)|(^#)|(^http(s*):\/\/[^\s]+\.[^\s]+)/, "链接格式不正确(可为空)"],
    numberNr: function (e) {
        if (e && isNaN(e)) return "只能填写数字(可为空)"
    },
    dateNr: [/(^$)|^(\d{4})[-\/](\d{1}|0\d{1}|1[0-2])([-\/](\d{1}|0\d{1}|[1-2][0-9]|3[0-1]))*$/, "日期格式不正确(可为空)"],
    identityNr: [/(^$)|(^\d{15}$)|(^\d{17}(x|X|\d)$)/, "请输入正确的身份证号(可为空)"],
    //扩展
    integer: [/^\d*$/, "只能填写整数"],
    birthday: [/^\d{4}-\d{2}-\d{2}$/, "请选择生日"],
    loginName: [/^[a-zA-z][a-zA-Z0-9]{4,19}$/, "登录名由字母和数字组成，只能字母开头，5~20位"],
    loginPassword: [/^[a-zA-Z0-9@#$%^&*()-_+=]{6,16}$/, "密码由字母和数字@#$%^&*()-_+=组成，6~16位"],
    /**
     * 正整数校验
     */
    integerGt0: function (value, item) {
        if (/^\d*$/.test(value)) {
            if (parseInt(value) <= 0) {
                if ($(item).attr("lay-reqText")) return $(item).attr("lay-reqText");
                return "只能输入正整数";
            }
        } else {
            if ($(item).attr("lay-reqText")) return $(item).attr("lay-reqText");
            return "只能输入正整数";
        }
    },
    //单选 复选 必选
    requiredRadioCheckbox: function (value, item) {
        var $ = layui.$;
        var verifyName = $(item).attr('name')
            , verifyType = $(item).attr('type')
            , formElem = $(item).parents('.layui-form')//获取当前所在的form元素，如果存在的话
            , verifyElem = formElem.find('input[name=' + verifyName + ']')//获取需要校验的元素
            , isTrue = verifyElem.is(':checked')//是否命中校验
            , focusElem = verifyElem.next().find('i.layui-icon');//焦点元素
        if (!isTrue || !value) {
            //定位焦点
            focusElem.css(verifyType == 'radio' ? {"color": "#FF5722"} : {"border-color": "#FF5722"});
            //对非输入框设置焦点
            focusElem.first().attr("tabIndex", "1").css("outline", "0").blur(function () {
                focusElem.css(verifyType == 'radio' ? {"color": ""} : {"border-color": ""});
            }).focus();
            if ($(item).attr("lay-reqText")) return $(item).attr("lay-reqText");
            return '必填项不能为空';
        }
    },
    //身份证校验 严格
    idCard: function (value) {
        //验证身份证号方法
        var area = {
            11: "北京", 12: "天津", 13: "河北", 14: "山西", 15: "内蒙古",
            21: "辽宁", 22: "吉林", 23: "黑龙江",
            31: "上海", 32: "江苏", 33: "浙江", 34: "安徽", 35: "福建", 36: "江西", 37: "山东",
            41: "河南", 42: "湖北", 43: "湖南", 44: "广东", 45: "广西", 46: "海南",
            50: "重庆", 51: "四川", 52: "贵州", 53: "云南", 54: "西藏",
            61: "陕西", 62: "甘肃", 63: "青海", 64: "宁夏", 65: "新疆",
            71: "台湾", 81: "香港", 82: "澳门", 91: "国外"
        }
        var Y, JYM, eReg;
        var strIdCard = value;
        var S, M;
        var idCard_array = strIdCard.split("");
        if (area[parseInt(strIdCard.substr(0, 2))] == null) return false;
        switch (strIdCard.length) {
            case 15:
                if ((parseInt(strIdCard.substr(6, 2)) + 1900) % 4 === 0 ||
                    ((parseInt(strIdCard.substr(6, 2)) + 1900) % 100 === 0
                        && (parseInt(strIdCard.substr(6, 2)) + 1900) % 4 === 0)) {
                    eReg = /^[1-9][0-9]{5}[0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|[1-2][0-9]))[0-9]{3}$/; //测试出生日期的合法性
                } else {
                    eReg = /^[1-9][0-9]{5}[0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|1[0-9]|2[0-8]))[0-9]{3}$/; //测试出生日期的合法性
                }
                if (!eReg.test(strIdCard)) {
                    return "请输入正确的身份证号";
                }
                break;
            case 18:
                if (parseInt(strIdCard.substr(6, 4)) % 4 === 0 ||
                    (parseInt(strIdCard.substr(6, 4)) % 100 === 0 &&
                        parseInt(strIdCard.substr(6, 4)) % 4 === 0)) {
                    eReg = /^[1-9][0-9]{5}(19|20)[0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|[1-2][0-9]))[0-9]{3}[0-9Xx]$/; //闰年出生日期的合法性正则表达式
                } else {
                    eReg = /^[1-9][0-9]{5}(19|20)[0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|1[0-9]|2[0-8]))[0-9]{3}[0-9Xx]$/; //平年出生日期的合法性正则表达式
                }
                if (eReg.test(strIdCard)) {
                    S = (parseInt(idCard_array[0]) + parseInt(idCard_array[10])) * 7 + (parseInt(idCard_array[1]) + parseInt(idCard_array[11])) * 9 + (parseInt(idCard_array[2]) + parseInt(idCard_array[12])) * 10 + (parseInt(idCard_array[3]) + parseInt(idCard_array[13])) * 5 + (parseInt(idCard_array[4]) + parseInt(idCard_array[14])) * 8 + (parseInt(idCard_array[5]) + parseInt(idCard_array[15])) * 4 + (parseInt(idCard_array[6]) + parseInt(idCard_array[16])) * 2 + parseInt(idCard_array[7]) + parseInt(idCard_array[8]) * 6 + parseInt(idCard_array[9]) * 3;
                    Y = S % 11;
                    M = "F";
                    JYM = "10X98765432";
                    M = JYM.substr(Y, 1);
                    if (M != idCard_array[17]) {
                        return "请输入正确的身份证号";
                    }
                } else {
                    return "请输入正确的身份证号";
                }
                break;
            default:
                return "请输入正确的身份证号";
        }
    }
};