<!DOCTYPE html>
<head>
    <title>信舟科技综合管理平台</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta name="renderer" content="webkit|ie-comp|ie-stand">
    <meta http-equiv="X-UA-Compatible" content="IE=EDGE"/>
    <link href="static/infoship/css/basic.css" rel="stylesheet" type="text/css"/>
    <link href="static/infoship/css/login.css" rel="stylesheet" type="text/css"/>
    <link type="image/x-icon" rel="shortcut icon" href="static/infoship/img/favicon.ico"/>
</head>
<body>
<form method="post" id="sub_form">
    <div id="top">
        <div class="main"><img src="static/infoship/img/infoship_top.png"/></div>
    </div>
    <div id="content">
        <div class="main">
            <div class="navLeft">
                <img src="static/infoship/img/login_bg.png"/>
            </div>
            <div class="loginInfo">
                <table>
                    <tr>
                        <td height="400">
                            <div class="dList">
                                <div class="p1">
                                    <h1>登录</h1><strong>login</strong>
                                    <span>没有账号？ <a href="">立即注册</a></span>
                                </div>
                                <dl>
                                <#if error??>
                                    <p class="errMessage">${error}</p>
                                </#if>
                                </dl>
                                <dl>
                                    <dt><@spring.message "pl.login.username"/>：</dt>
                                    <dd>
                                        <div class="cBorder"><input type="text" name="loginName" value="${username}"
                                                                    placeholder="用户名"/></div>
                                    </dd>
                                </dl>
                                <p class="errMessage" id="loginName_ms"></p>
                                <dl>
                                    <dt><@spring.message "pl.login.password"/>：</dt>
                                    <dd>
                                        <div class="cBorder"><input type="password" name="loginPass" placeholder="密码"/>
                                        </div>
                                    </dd>
                                </dl>
                                <p class="errMessage" id="loginPass_ms"></p>

                                <div style="display:none;"><!--验证码-->
                                    <dl>
                                        <dt>验证码：</dt>
                                        <input type="hidden" name="timeToken"/>
                                        <dd>
                                            <div class="cBorder">
                                                <input type="text" name="loginCode" style="width:107px"/>
                                            </div>
                                            <div id="t_imgCode"></div>
                                        </dd>
                                    </dl>
                                    <p class="errMessage" id="loginCode_ms"></p>
                                </div>
                                <dl>
                                    <dt>&nbsp;</dt>
                                    <dd>
                                        <button id="submit"><@spring.message "pl.login.dologin"/></button>
                                    </dd>
                                </dl>
                                <div class="links"><a href="">忘记密码？</a></div>
                            </div>
                        </td>
                    </tr>
                </table>
            </div>
        </div>
    </div>
    <div id="bottom">
        <div class="main">
            <dl class="cLeft">
                <dt>
                    <label>联系电话：010-59468828</label>
                    <label>邮箱:market@infoship.cn</label>
                    <label>地址：北京市朝阳区建外SOHO西区16号楼7层</label>
                </dt>
                <dd><label>Copyright@2015 信舟科技 版权所有</label></dd>
            </dl>
            <dl class="cRight">
                <dt><img src="static/infoship/img/infoship_bottom.png"/></dt>
                <dd>信舟科技发展有限公司</dd>
            </dl>
        </div>
    </div>
</form>

<script type="text/javascript" src="static/jquery/jquery.min.js"></script>
<script type="text/javascript" src="static/third/js/jquery.form.js"></script>
<script type="text/javascript" src="static/infoship/js/jquery.extends.js"></script>
<script type="text/javascript" src="static/infoship/js/login.js"></script>
</body>
</html>
