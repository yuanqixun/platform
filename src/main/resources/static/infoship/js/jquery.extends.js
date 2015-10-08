/**
 * Created by yqx on 10/6/15.
 */
/*追加自定义方法*/
jQuery.fn.extend({
    coord: function(e){
        if (this.length == 0) {
            return param = {
                top: 0,
                left: 0,
                width: 0,
                height: 0
            }
        }
        else {
            var param = {};
            var obj = this.get(0);
            var param = {
                height: parseInt(obj.clientHeight),
                width: parseInt(obj.clientWidth),
                top: 0,
                left: 0
            };
            do {
                param.top += obj.offsetTop || 0;
                param.left += obj.offsetLeft || 0;
                obj = obj.offsetParent
            }
            while (obj);
            return param
        }
    }
});
//兼容indexof方法
if (!Array.prototype.indexOf)
{
    Array.prototype.indexOf = function(elt /*, from*/)
    {
        var len = this.length >>> 0;

        var from = Number(arguments[1]) || 0;
        from = (from < 0)
            ? Math.ceil(from)
            : Math.floor(from);
        if (from < 0)
            from += len;

        for (; from < len; from++)
        {
            if (from in this &&
                this[from] === elt)
                return from;
        }
        return -1;
    };
}

/**
 * 错误信息
 * @param {Object} text
 * @param {Object} type
 */
function show_message(param){
    var t = param.type ? param.type : "err";
    var tt = param.title ? param.title : "提示";
    $("#err_message_plan").remove();
    var plan = $("<div id='err_message_plan' style='position:fixed;width:100%;height:100%;top:0;left:0;z-index:999'></div>").appendTo("body");
    var bg = $("<div style='float:left;width:100%;height:100%;background:#000;opacity:0.7;'></div>").appendTo(plan);
    var content = $("<div style='position:fixed;width:300px;border:1px #ddd solid;background:#fff;padding:10px;top:50%;left:50%;margin-left:-180px;margin-top:-125px;'></div>").appendTo(plan);
    var title = $("<div style='border-bottom:1px #ddd solid;height:35px;line-height:35px;font-size:18px;color:#333;'>" + tt + "</div>").appendTo(content);
    var close = $("<span style='cursor:pointer;float:right;width:35px;height:35px;background:url(theme/gray/css/skin/black/images/infoship.png) -85px -125px no-repeat;'></span>").appendTo(title);
    close.mouseover(function(){
        $(this).css({
            "background-position": "-124px -125px"
        })
    }).mouseout(function(){
        $(this).css({
            "background-position": "-85px -125px"
        })
    }).click(function(){
        $("#err_message_plan").remove();
    })
    var dl = $("<dl style='float:left;height:88px;width:100%;margin-bottom:20px;'></dl>").appendTo(content);
    var dt = $("<dt style='float:left;width:40px;height:40px;margin:24px 20px;background:url(theme/gray/css/skin/black/images/infoship.png) -159px -119px no-repeat;'></dt>").appendTo(dl);
    if(t == "info"){
        dt.css({
            "background-position": "-200px -119px"
        });
    }
    else if(t == "warning"){
        dt.css({
            "background-position": "-240px -119px"
        });
    }else if(t == "question"){
        //询问类型 TODO
        dt.css({
            "background-position": "-240px -240px"
        });
    }
    var dd = $("<dd style='float:270px;overflow-y:auto;padding-top:20px;max-height:70px;'>" + param.text + "</dd>").appendTo(dl);

    //错误、消息、警告
    if (t == "err" || t == "info" || t == "warning") {
        var p = $("<p style='float:left;height:40px;line-height:40px;width:100%;text-align:center;color:#fff;font-size:16px;35px;background:#5cb05c;cursor:pointer;'>确定</p>").appendTo(content);
        p.click(function(){
            $("#err_message_plan").remove();
            if (param.fn) {
                param.fn();
            }
        });
    }
    else {
        //询问类型，有回调
        var p = $("<p style='float:left;height:40px;line-height:40px;width:143px;text-align:center;color:#333;font-size:16px;35px;background:#fff;border:1px #ddd solid;cursor:pointer;'>取消</p>").appendTo(content);
        p.click(function(){
            $("#err_message_plan").remove();
        });
        var p1 = $("<p style='float:right;height:40px;line-height:40px;width:143px;text-align:center;color:#fff;font-size:16px;35px;background:#5cb05c;border:1px #51a251 solid;cursor:pointer;'>确定</p>").appendTo(content);
        p1.click(function(){
            $("#err_message_plan").remove();
            if (param.fn) {
                param.fn();
            }
        });
    }
}

$.support.cors = true;

jQuery.extend({
    appendProperty: function(object, param){
        var property = param.split("=");
        var pro_name = property[0];
        var pro_value = property[1] == "" ? null : property[1];

        //判断是否是数组
        var b = pro_name.indexOf("[]");
        if (b != -1)
        {
            pro_name = pro_name.replace(/]/g, "");
            pro_name = pro_name.substring(0, pro_name.length - 1);
            var pro_a = pro_name.split("[");
            var temp = object;
            for (var i = 0; i < pro_a.length; i++)
            {
                if (i == pro_a.length - 1)
                {
                    if (temp[pro_a[i]])
                    {
                        if ($.isArray(temp[pro_a[i]]))
                        {
                            temp[pro_a[i]].push(pro_value);
                        }
                        else
                        {
                            var temp_array = [];
                            temp_array.push(temp[pro_a[i]]);
                            temp[pro_a[i]] = temp_array;
                            temp[pro_a[i]].push(pro_value);
                        }
                    }
                    else
                    {
                        temp[pro_a[i]] = new Array();
                        temp[pro_a[i]].push(pro_value);
                    }
                }
                else
                {
                    if (!temp[pro_a[i]])
                    {
                        temp[pro_a[i]] = {};
                    }
                    temp = temp[pro_a[i]];
                }
            }
        }
        else
        {
            pro_name = pro_name.replace(/]/g, "");
            var pro_a = pro_name.split("[");
            var temp = object;
            for (var i = 0; i < pro_a.length; i++)
            {
                if (i == pro_a.length - 1)
                {
                    if (temp[pro_a[i]])
                    {
                        if ($.isArray(temp[pro_a[i]]))
                        {
                            temp[pro_a[i]].push(pro_value);
                        }
                        else
                        {
                            var temp_array = [];
                            temp_array.push(temp[pro_a[i]]);
                            temp[pro_a[i]] = temp_array;
                            temp[pro_a[i]].push(pro_value);
                        }
                    }
                    else
                    {
                        temp[pro_a[i]] = pro_value;
                    }
                }
                else
                {
                    if (!temp[pro_a[i]])
                    {
                        temp[pro_a[i]] = {};
                    }
                    temp = temp[pro_a[i]];
                }
            }
        }
    },
    unparam: function(param, key){
        try
        {
            if (!param) return null;
            var object = null;
            if ($.type(param) == "string")
            {
                object = {};
                var param_array = param.split("&");
                for (var i = 0; i < param_array.length; i++)
                {
                    $.appendProperty(object, decodeURIComponent(param_array[i]));
                }
            }
            else
            {
                object = param;
            }
            if (!object) return;

            if (key)
            {
                return object[key];
            }
            else
            {
                return object;
            }
        }
        catch (e)
        {
            return null;
        }
    },
    getCode: function(e){
        if (!e) var e = window.event;
        if (e.keyCode) return e.keyCode;
        else if (e.which) return e.which
    },
    getTime: function(param){
        if (!param) var param = {}
        var myDate = new Date();
        if (param.dateTime) myDate = param.dateTime;
        if (param.time) myDate = new Date(Date.parse(param.time.toString().replace(/-/g, '/')));
        if (param.year) myDate.setFullYear(param.year + myDate.getFullYear());
        if (param.month) myDate.setMonth(param.month + myDate.getMonth());
        if (param.date) myDate.setDate(param.date + myDate.getDate());
        if (param.hour) myDate.setHours(param.hour);
        if (param.minute) myDate.setMinutes(param.minute);
        var year = myDate.getFullYear();
        var month = myDate.getMonth() + 1;
        var day = myDate.getDate();
        //格式错误
        if (!year && !month && !day)
        {
            myDate = new Date();
            year = myDate.getFullYear();
            month = myDate.getMonth() + 1;
            day = myDate.getDate();
        }
        if (month < 10) month = "0" + month;
        if (day < 10) day = "0" + day;
        var hours = myDate.getHours();
        if (hours < 10) hours = "0" + hours;
        var minute = myDate.getMinutes();
        if (minute < 10) minute = "0" + minute;
        var unDate = year + "-" + month + "-" + day;
        if (param.type)
        {
            switch (param.type) {
                case "year":
                    unDate = year;
                    break;
                case "month":
                    unDate = year + "/" + month;
                    break;
                case "day":
                    unDate = unDate = year + "/" + month + "/" + day;
                    break;
                case "minute":
                    unDate = unDate = year + "/" + month + "/" + day + " " + hours + ":" + minute;
                    break;
                case "week":
                    var weekDate = $.getYearWeek(year, month, day);
                    unDate = weekDate.year + "/" + weekDate.week;
                    break;
                case "weekDay":
                    unDate = myDate.getDay();
            }
        }
        return unDate;
    },
    jsonAjax: function(param){
        $.ajax({
            type: "post",
            url: param.url,
            data: param.data,
            dataType: "json",
            success: function(json){
                if(typeof json != "object")
                    json = eval("(" + json + ")");
                if (param.fn) param.fn(json);
            },
            error: function(e){
                //window.location.href="logout";
                console.log(e);
            }
        });
    }
});

/*
 * @desc   判断浏览器的版本以及浏览器内核
 */
var browser = function(){
    var agent = navigator.userAgent.toLowerCase(), opera = window.opera, browser = {
        //检测当前浏览器是否为IE
        ie: /(msie\s|trident.*rv:)([\w.]+)/.test(agent),
        //检测当前浏览器是否为Opera
        opera: (!!opera && opera.version),
        //检测当前浏览器是否是webkit内核的浏览器
        webkit: (agent.indexOf(' applewebkit/') > -1),
        //检测当前浏览器是否是运行在mac平台下
        mac: (agent.indexOf('macintosh') > -1),
        //检测当前浏览器是否处于“怪异模式”下
        quirks: (document.compatMode == 'BackCompat')
    };
    //检测当前浏览器内核是否是gecko内核
    browser.gecko = (navigator.product == 'Gecko' && !browser.webkit && !browser.opera && !browser.ie);
    var version = 0;
    // Internet Explorer 6.0+
    if (browser.ie) {
        var v1 = agent.match(/(?:msie\s([\w.]+))/);
        var v2 = agent.match(/(?:trident.*rv:([\w.]+))/);
        if (v1 && v2 && v1[1] && v2[1]) {
            version = Math.max(v1[1] * 1, v2[1] * 1);
        }
        else if (v1 && v1[1]) {
            version = v1[1] * 1;
        }
        else if (v2 && v2[1]) {
            version = v2[1] * 1;
        }
        else {
            version = 0;
        }
        //检测浏览器模式是否为 IE11 兼容模式
        browser.ie11Compat = document.documentMode == 11;
        //检测浏览器模式是否为 IE9 兼容模式
        browser.ie9Compat = document.documentMode == 9;
        //检测浏览器模式是否为 IE10 兼容模式
        browser.ie10Compat = document.documentMode == 10;
        //检测浏览器是否是IE8浏览器
        browser.ie8 = !!document.documentMode;
        //检测浏览器模式是否为 IE8 兼容模式
        browser.ie8Compat = document.documentMode == 8;
        //检测浏览器模式是否为 IE7 兼容模式
        browser.ie7Compat = ((version == 7 && !document.documentMode) || document.documentMode == 7);
        //检测浏览器模式是否为 IE6 模式 或者怪异模式
        browser.ie6Compat = (version < 7 || browser.quirks);
        browser.ie9above = version > 8;
        browser.ie9below = version < 9;
    }
    // Gecko.
    if (browser.gecko) {
        var geckoRelease = agent.match(/rv:([\d\.]+)/);
        if (geckoRelease) {
            geckoRelease = geckoRelease[1].split('.');
            version = geckoRelease[0] * 10000 + (geckoRelease[1] || 0) * 100 + (geckoRelease[2] || 0) * 1;
        }
    }
    //检测当前浏览器是否为Chrome, 如果是，则返回Chrome的大版本号
    if (/chrome\/(\d+\.\d)/i.test(agent)) {
        browser.chrome = +RegExp['\x241'];
    }
    //检测当前浏览器是否为Safari, 如果是，则返回Safari的大版本号
    if (/(\d+\.\d)?(?:\.\d)?\s+safari\/?(\d+\.\d+)?/i.test(agent) && !/chrome/i.test(agent)) {
        browser.safari = +(RegExp['\x241'] || RegExp['\x242']);
    }
    // Opera 9.50+
    if (browser.opera) version = parseFloat(opera.version());
    // WebKit 522+ (Safari 3+)
    if (browser.webkit) version = parseFloat(agent.match(/ applewebkit\/(\d+)/)[1]);
    //检测当前浏览器版本号
    browser.version = version;
    return browser;
}();
