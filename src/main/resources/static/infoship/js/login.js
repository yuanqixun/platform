var LOGIN_VALIDATECODEURL = "validatecode.jpg";//验证码请求地址
//var LOGIN_DOLOGINURL = "theme/gray/json/login.json";//登录地址
//var LOGIN_DOLOGINURL = "rest/api/login/dologin";//登录地址
try {
    //内部页面会话失效 重新回到登录
    if (parent && parent.doServerLogout) {
        parent.doServerLogout();
    }
}
catch (e) {
    alert(message)
}
var PUBLIC_FORM_SUBMIT = true;
$(document).ready(function(){
    try {
        $("input[name='loginName']").focus(function(){
            $("#loginName_ms").html("");
        });
        $("input[name='loginPass']").focus(function(){
            $("#loginPass_ms").html("");
        });
        $("input[name='loginCode']").focus(function(){
            $("#loginPass_ms").html("");
        });
        loadValidateCode();
        $("#submit").click(function(){
            $("#sub_form").submit();
            //if (!$(this).hasClass("loading")) {
            //    submit_form();
            //    setTimeout(function(){
            //        if (PUBLIC_FORM_SUBMIT) {
            //            $("#sub_form").submit();
            //        }
            //    }, 250);
            //}
        });
        $(document).keyup(function(e){
            if ($.getCode(e) == 13) {
				submit_form();
				setTimeout(function(){
					if (PUBLIC_FORM_SUBMIT) {
						$("#sub_form").submit();
					}
				}, 250);
			}
        });
    }
    catch (e) {
        alert(e.message)
    }
});
/**
 * 表单提交
 */
function submit_form(){
    //$("#sub_form").submit();
    //$("#sub_form").unbind("submit");
    //$("#sub_form").submit(function(){
    //    var options = {
    //        type: "post",
    //        url: LOGIN_DOLOGINURL,
    //        dataType: "json",
    //        success: function(json){
    //            PUBLIC_FORM_SUBMIT = true;
    //            if (json.success) {
    //                window.location.href = json.sucessUrl;
    //            }
    //            else {
    //                if (json.loginName == false) {
    //                    $("#loginName_ms").html(json.errMessage);
    //                }
    //                else if (json.loginPass == false) {
    //                    $("#loginPass_ms").html(json.errMessage);
    //                }
    //                else if (json.loginCode == false) {
    //                    $("#loginCode_ms").html(json.errMessage);
    //                }
    //                loadValidateCode();
    //            }
    //            $("#submit").removeClass("loading");
    //        }
    //    }
    //    if (va_user() & va_pwd() & va_code()) {
    //        PUBLIC_FORM_SUBMIT = false;
    //        $("#submit").addClass("loading");
    //        $("#sub_form").ajaxSubmit(options);
    //    }
    //    else {
    //        $("#submit").removeClass("loading");
    //    }
    //    return false;
    //});
}

/**
 * 验证用户
 */
function va_user(){
    var loginName = $("input[name='loginName']");
    if ($.trim(loginName.val()) == "") {
        $("#loginName_ms").html("请输入用户名");
        return false;
    }
    else {
        $("#loginName_ms").html("");
        return true;
    }
}

/**
 * 验证密码
 */
function va_pwd(){
    var loginPass = $("input[name='loginPass']");
    if ($.trim(loginPass.val()) == "") {
        $("#loginPass_ms").html("请输入密码");
        return false;
    }
    else {
        $("#loginPass_ms").html("");
        return true;
    }
}

/**
 * 验证码
 */
function va_code(){
    return true;//TODO 暂时去掉
    var loginCode = $("input[name='loginCode']");
    if ($.trim(loginCode.val()) == "") {
        $("#loginCode_ms").html("请输验证码");
        return false;
    }
    else {
        $("#loginCode_ms").html("");
        return true;
    }
}

/**
 * 获取验证码
 */
function loadValidateCode(){
    return;//TODO 暂时去掉
    var date = new Date();
    var timeToken = date.getTime();
    $("input[name='timeToken']").val(timeToken);
    if ($("#t_imgCode img").length != 0) {
        $("#t_imgCode img").attr("src", LOGIN_VALIDATECODEURL + "?timeToken=" + timeToken);
    }
    else {
        var img = $("<img alt='验证码' src='" + LOGIN_VALIDATECODEURL + "?timeToken=" + timeToken + "' width='80' height='30'></img>");
        $("#t_imgCode").append(img);
    }
}
