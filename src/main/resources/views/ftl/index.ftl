<!DOCTYPE html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=EDGE"/>

    <link href="static/infoship/css/basic.css" rel='stylesheet' type='text/css'/>
    <link href="static/infoship/css/index.css" rel='stylesheet' type='text/css'/>
    <link href="static/infoship/css/skin/black/skin.css" rel='stylesheet' type='text/css'/>
    <link href="static/third/css/jquery.scrollbar.min.css" rel='stylesheet' type='text/css'/>
    <link href="static/third/css/jquery.jgrowl.msg.css" rel='stylesheet' type='text/css'/>

</head>
<body onload="size()">
<form id="logoutForm" action="logout"  method="post" style="display: none">
</form>
<div id="hader">
    <div class="top">
        <div class="logo"></div>
        <strong class="logo_x"></strong>

        <h1></h1>
        <ul class="menu">
            <li>
                <div class="publicSearch">
                    <div class="cBorder">
                        <i></i>
                        <input type="text" id="publicSearchText" placeholder="菜单搜索"/>
                    </div>
                    <div class="cItems none"></div>
                </div>
            </li>
            <li class="br"><s class="maxPage title" titles="全屏"></s></li>
            <li class="br none"><s class="transaction" titles="事项处理"></s></li>
            <li class="br none"><s class="messageCenter" titles="消息中心"></s></li>
            <li class="br none"><s class="email" titles="邮件"></s></li>
            <li class="br" id="user_skin">
                <s class="skin" titles="皮肤"></s>

                <div class="postionItems">
                    <strong></strong>
                    <ul>
                        <li did="black">黑色经典</li>
                        <li did="blue">天空蓝</li>
                    </ul>
                </div>
            </li>
            <li id="userInfo">
                <a href="javascript:void(0)"><img alt="" src=""/><label class="uName"></label><i><em></em></i></a>

                <div class="postionItems">
                    <strong></strong>
                    <ul>
                        <li id="settings"><s class="settings"></s>个人设置</li>
                        <li id="systemExit"><s class="exit"></s>退出</li>
                    </ul>
                </div>
            </li>
        </ul>
    </div>
</div>
<table id="content" width="100%" style="height:1000px;">
    <tr>
        <td id="pageLeft" valign="top">
            <div class="oneMenu"></div>
            <div class="twoMenu">
                <div class="twoMenuList"></div>
                <div class="twoMenuBg"></div>
            </div>
        </td>
        <td id="pageCenter" valign="top">
            <div class="pageTabs">
                <ul></ul>
                <div class="tblMorePlan">
                    <span class="tblMoreIco"></span>
                    <dl style="display:none;"></dl>
                </div>
                <div class="contextmenu">
                    <p class="refresh">刷新</p>

                    <p class="close">关闭标签页</p>

                    <p class="closeother">关闭其他标签页</p>

                    <p class="closeall">关闭所有标签页</p>
                </div>
            </div>
            <table width="100%">
                <tr>
                    <td id="iframeContent" valign="top"></td>
                </tr>
            </table>
        </td>
    </tr>
</table>
<div id="hiddeConten" style="height:0;float:left;width:0;overflow:hidden;">
    <p class="titleContent none"></p>

    <div id="maxPage" style="display:none;">您已进入全屏模式。<a href="javascript:void(0)" onclick="exit_max()">退出全屏模式</a></div>
</div>
<script type="text/javascript" src="static/jquery/jquery.min.js"></script>
<script type="text/javascript" src="static/third/js/jquery.scrollbar.min.js"></script>
<!--socket.io.js 推送消息支持-->
<script type="text/javascript" src="static/third/js/socket.io.js"></script>
<script type="text/javascript" src="static/third/js/socket.io.init.js?v=2"></script>
<script type="text/javascript" src="static/third/js/jquery.utils.js"></script>
<script type="text/javascript" src="static/third/js/jquery.jgrowl.js"></script>
<script type="text/javascript" src="static/infoship/js/jquery.extends.js"></script>
<script type="text/javascript" src="static/infoship/js/index.js"></script>

</body>
</html>
