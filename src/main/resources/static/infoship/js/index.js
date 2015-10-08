function size(){
    //主页内容区域大小控制
    var h = document.documentElement.clientHeight;
    $("body").height(h);
    if ($("#hader").hasClass("none")) {
        //内容、菜单
        $("#content,.twoMenuList,.oneMenu").height(h);
        var lis = $(".pageTabs li");
        var h1 = h - 20;
        if (lis.length > 1) {
            h1 -= 36;
        }
        $("#iframeContent").height(h1);
    }
    else {
        //内容、菜单
        $("#content,.twoMenuList,.oneMenu").height(h - 59);
        var lis = $(".pageTabs li");
        var h1 = h - 79;
        if (lis.length > 1) {
            h1 -= 36;
        }
        $("#iframeContent").height(h1);
    }
}

function doServerLogout(){
    $("#logoutForm").submit();
}

$(window).bind("resize", function(){
    size();
}).bind("click", function(){
    $(".tblMorePlan dl,.contextmenu,.cItems,.postionItems").hide();
});


var USER_SKIN = "black";
$(document).ready(function(){
    try {
        //绑定所有的title说明
        $(".title").on("mouseover", function(){
            var coord = $(this).coord();
            if (coord.left > 500) {
                var w = $(".titleContent").html($(this).attr("titles")).width();
                $(".titleContent").css({
                    left: coord.left - w + coord.width / 2,
                    top: coord.top + coord.height + 20
                }).show();
            }
            else {
                $(".titleContent").css({
                    left: coord.left + coord.width + 10,
                    top: coord.top + coord.height / 2 - 15
                }).html($(this).attr("titles")).show();
            }
        });
        $(".title").on("mouseout", function(){
            $(".titleContent").hide();
        });
        //初始化加载系统内容
        $.jsonAjax({
            //url: "theme/gray/json/initializePublic.json",
            url: "rest/runtime/menu/mymenus",
            fn: function(json){
                if (json.skin && json.skin != "") {
                    USER_SKIN = json.skin;
                }
                //skin
                $("#user_skin li[did='" + USER_SKIN + "']").addClass("at");
                $('head').append('<link rel="stylesheet" href="theme/gray/css/skin/' + USER_SKIN + '/skin.css" type="text/css" />');
                //logo
                $(".logo").css({
                    "background-image": "url('" + json.logoUrl + "')"
                });
                //头部项目
                if (json.transaction && parseInt(json.transaction) > 0) {
                    $(".menu s.transaction").append("<i>" + json.transaction + "</i>");
                }
                if (json.messageCenter && parseInt(json.messageCenter) > 0) {
                    $(".menu s.messageCenter").append("<i>" + json.messageCenter + "</i>");
                }
                if (json.email && parseInt(json.email) > 0) {
                    $(".menu s.email").append("<i>" + json.email + "</i>");
                }
                //系统名称
                $(".top h1").css({
                    "background-image": "url('" + json.softNameUrl + "')"
                });
                //头像
                if (json.userHead) $("#userInfo img").attr("src", json.userHead);
                //用户名称
                $("#userInfo .uName").html(json.userName);
                //绑定一级菜单
                var oneMenu = $(".oneMenu");
                var twoMenu = $(".twoMenu .twoMenuList");
                //如果只有1个系统
                if (json.menus.length == 1) {
                    oneMenu.remove();
                    $("#pageLeft").width(200);
                }
                $.each(json.menus, function(index, item){
                    //如果第一个系统是外部系统
                    if (index == 0 && item.isExternal) {
                        //隐藏菜单
                        $(".twoMenu").hide();
                        $("#pageLeft").width(70);
                    }
                    var one = $("<dl href='" + item.href + "' mainpageid='" + item.mainpageid + "' mainpagename='" + item.mainpagename + "' did='" + item.id + "' isExternal='" + item.isExternal + "'></dl>").appendTo(oneMenu);
                    var dt = $("<dt></dt>").appendTo(one);
                    var span = $("<span></span>").appendTo(dt);
                    $("<dd>" + item.name + "</dd>").appendTo(one);
                    if (item.icon) {
                        span.addClass(item.icon);
                    }
                    else {
                        span.addClass("bar1");
                    }
                    var display = "none";
                    //默认选中
                    if (index == 0) {
                        one.addClass("at");
                        display = "block";
                        if (item.href) {
                            bindTabs(item.mainpageid, item.mainpagename, item.href, true);
                        }
                    }
                    //循环二级
                    var ul = $("<ul class='menu1' style='display:" + display + ";' did='" + item.id + "'></ul>").appendTo(twoMenu);
                    $.each(item.list, function(index1, item1){
                        var li = $("<li href='" + item1.href + "' did='" + item1.id + "'></li>").appendTo(ul);
                        var h2 = $("<h2></h2>").appendTo(li);
                        h2.click(function(){
                            if (!$(this.parentNode).hasClass("at")) {
                                var li = $(this.parentNode).removeClass("at");
                                var divChild = $("> div", this.parentNode).slideDown("fast");
                                $(this.parentNode).addClass("at");
                            }
                            else {
                                var divs = $("> div", this.parentNode).slideUp("fast");
                                var li = $(this.parentNode).removeClass("at");
                            }
                            var name = $("label", this).html();
                            var href = $(this).parent().attr("href");
                            var did = $(this).parent().attr("did");
                            if (href && href != "" && href != "null") {
                                bindTabs(did, name, href);
                            }
                        });
                        if (item1.icon) {
                            $("<s><img src=\"" + item.icon + "\"></s>").appendTo(h2);
                        }
                        $("<label pinyin='" + item1.pinyin + "' title='" + item1.name + "'>" + item1.name + "</label>").appendTo(h2);
                        //绑定下级菜单
                        if (item1.list && item1.list.length > 0) {
                            $("<i><em></em><span></span></i>").appendTo(h2);
                            var div = $("<div></div>").appendTo(li).show();
                            li.addClass("at");
                            bindMenu(item1.list, div, true);
                        }
                    });
                    if (item.isExternal != true) {
                        //系统点击事件
                        one.click(function(){
                            if (!$(this).hasClass("at")) {
                                $(".oneMenu dl").removeClass("at");
                                $(this).addClass("at");
                                $("ul.menu1").hide();
                                $("ul.menu1[did='" + ($(this).attr("did")) + "']").show();
                                //显示菜单
                                $(".twoMenu").show();
                                $("#pageLeft").width(270);
                            }
                            var name = $(this).attr("mainpagename");
                            var href = $(this).attr("href");
                            var did = $(this).attr("mainpageid");
                            if (href && href != "" && href != "null") {
                                bindTabs(did, name, href, true);
                            }
                        });
                    }
                    else {
                        //系统点击事件
                        one.click(function(){
                            if (!$(this).hasClass("at")) {
                                $(".oneMenu dl").removeClass("at");
                                $(this).addClass("at");
                                //隐藏菜单
                                $(".twoMenu").hide();
                                $("#pageLeft").width(70);
                            }
                            var name = $(this).attr("mainpagename");
                            var href = $(this).attr("href");
                            var did = $(this).attr("mainpageid");
                            if (href && href != "" && href != "null") {
                                bindTabs(did, name, href, true);
                            }
                        });
                    }
                });
                $(".twoMenuList").mCustomScrollbar({
                    theme: "dark-3"
                });
            }
        });
        //退出系统
        $("#systemExit").click(function(){
            doServerLogout();
        });
        //个人设置
        $("#settings").click(function(){
            openTabs('myprofile', '个人设置', "private/admin/profile.seam");
        });
        //绑定页签点击效果
        $(".pageTabs li").on("click", function(){
            $(".pageTabs li.at").removeClass("at");
            $(this).addClass("at");
            var did = $(this).attr("did");
            $("#iframeContent iframe").hide();
            $("#iframeContent iframe[did='" + did + "']").show();
            tabs_index(did, true);
        });
        //删除页签
        $(".pageTabs li i").on("click", function(){
			var did = $(this).parent().attr("did");
			var at = $(this).parent().hasClass("at");
			var name = $("label", this.parentNode).html();
			closeTabs(did, name);
			$(this).parent().remove();
			$("#iframeContent iframe[did='" + did + "']").remove();
			if (at) {
				var id1 = TABS_ARRAY[TABS_ARRAY.length - 1];
				var li = $(".pageTabs li[did='" + id1 + "']").first();
				li.addClass("at");
				var did1 = li.attr("did");
				$("#iframeContent iframe[did='" + id1 + "']").show();
				tabs_index(id1, true);
			}
			else if ($("#pageCenter .pageTabs ul li").length == 1) {
				var id1 = TABS_ARRAY[TABS_ARRAY.length - 1];
				var li = $(".pageTabs li[did='" + id1 + "']").first();
				li.addClass("at");
				var did1 = li.attr("did");
				$("#iframeContent iframe[did='" + id1 + "']").show();
				tabs_index(id1, true);
			}
			convertTbsSize();
			return false;
		});
        //更多页签
        $(".tblMorePlan dd").on("click", function(){
            moreTbsUpdate($(this));
            $(".tblMorePlan dl").hide();
            return false;
        });
        //下啦页签
        $(".tblMoreIco").click(function(){
            $(".tblMorePlan dl").slideToggle("fast");
            return false;
        });
        //菜单搜索
        $("#publicSearchText").click(function(){
            if ($(".cItems li").length > 0) {
                $(".cItems").show();
                return false;
            }
        }).blur(function(){
            setTimeout(function(){
                $(".cItems").hide();
            }, 500);
        });
        //菜单搜索
        $("#publicSearchText").keyup(function(e){
            var code = $.getCode(e);
            if (code != 40 && code != 38 && code != 13) {
                var txt = this.value.trim();
                $(".cItems").html("");
                var ul = $("<ul></ul>").appendTo(".cItems");
                if (txt.length > 0) {
                    $(".menu1 label").each(function(index, item){
                        var pinyin = $(item).attr("pinyin");
                        var p1 = false, p2 = false;
                        if (pinyin && pinyin != "") {
                            pinyin = pinyin.split(",");
                            if (pinyin.length == 2) {
                                if (pinyin[0].toLowerCase().indexOf(txt.toLowerCase()) != -1) {
                                    p1 = true;
                                }
                                if (pinyin[1].toLowerCase().indexOf(txt.toLowerCase()) != -1) {
                                    p2 = true;
                                }
                            }
                        }
                        if (item.innerHTML.indexOf(txt) != -1 || p1 || p2) {
                            var did = "", href = "";
                            if ($(this).parent().parent().attr("did")) {
                                did = $(this).parent().parent().attr("did");
                                href = $(this).parent().parent().attr("href");
                            }
                            else {
                                did = $(this).parent().parent().parent().attr("did");
                                href = $(this).parent().parent().parent().attr("href");
                            }
                            if (href && href != "" && href != "null") {
                                $("<li did='" + did + "' href='" + href + "'>" + item.innerHTML + "</li>").appendTo(ul);
                            }
                        }
                    });
                    if ($("li", ul).length > 0) {
                        $(".cItems").show();
                    }
                    else {
                        $(".cItems").html("<p>没有找到相关结果</p>").show();
                    }
                }
                else {
                    $(".cItems").hide();
                }
            }
            else if (code == 40) {
                var i = -1;
                var n = $(".cItems li").length;
                if (n > 0) {
                    $(".cItems li").each(function(index, item){
                        if (item.className == "at") {
                            i = index;
                        }
                    });
                    if (i + 1 >= n) {
                        i = 0;
                    }
                    else {
                        i++;
                    }
                    $(".cItems li").removeClass("at").eq(i).addClass("at");
                }
            }
            else if (code == 38 && $(".cItems li").length > 0) {
                var n = $(".cItems li").length;
                var i = n;
                if (n > 0) {
                    $(".cItems li").each(function(index, item){
                        if (item.className == "at") {
                            i = index;
                        }
                    });
                    if (i < 0) {
                        i = n - 1;
                    }
                    else {
                        i--;
                    }
                    $(".cItems li").removeClass("at").eq(i).addClass("at");
                }
            }
            else if (code == 13) {
                $(".cItems li.at").click();
                $(".cItems li.at").removeClass("at");
            }
        });
        //搜索结果点击事件
        $(".cItems li").on("click", function(){
            var name = $(this).html();
            var href = $(this).attr("href");
            var did = $(this).attr("did");
            if (href && href != "" && href != "null") {
                bindTabs(did, name, href);
            }
            $(".cItems").hide();
        });
        //页签右键
        $(".pageTabs ul li").on("contextmenu", function(e){
            var coord = $(this).coord();
            $(".contextmenu").css({
                top: coord.top + 35,
                left: coord.left
            }).show();
            window.pageTbs = this;
            return false;
        });
        $(".contextmenu p").click(function(){
            if (this.className == "refresh") {
                var li = $(window.pageTbs).click();
                $("iframe[did='" + li.attr("did") + "']").attr("src", li.attr("href"));
            }
            else if (this.className == "close") {
                $("i", window.pageTbs).click();
            }
            else if (this.className == "closeother") {
                var did = $(window.pageTbs).attr("did");
                //删除更多里面的
                $(".tblMorePlan dd").each(function(){
                    var did = $(this).attr("did");
                    var name = this.innerHTML;
                    $("iframe[did='" + did + "']").remove();
                    closeTabs(did, name);
                    $(this).remove();
                });
                $(".pageTabs ul li").each(function(index, item){
                    if (did != $(item).attr("did") && !$(item).hasClass("mainPage")) {
                        $("i", item).click();
                    }
                });
            }
            else if (this.className == "closeall") {
                //删除更多里面的
                $(".tblMorePlan dd").each(function(){
                    var did = $(this).attr("did");
                    var name = this.innerHTML;
                    $("iframe[did='" + did + "']").remove();
                    closeTabs(did, name);
                    $(this).remove();
                });
                $(".pageTabs ul li").each(function(index, item){
                    if (!$(item).hasClass("mainPage")) {
                        $("i", item).click();
                    }
                });
            }
            $(".contextmenu").hide();
            return false;
        });
        //点击账户
        $("#userInfo").click(function(){
            $("#userInfo .postionItems").toggle();
            return false;
        });
        //切换皮肤
        $("#user_skin").click(function(){
            $("#user_skin .postionItems").toggle();
            return false;
        });
        //切换皮肤
        $("#user_skin .postionItems li").click(function(){
            var did = $(this).attr("did");
            if (!$(this).hasClass("at")) {
                $.jsonAjax({
                    url: "rest/myskin/change",
                    data: {
                        skin: did
                    },
                    fn: function(json){
                        if (json.success) window.location.href = window.location.href;
                    }
                });
            }
        });
        //全屏
        $("#hader .maxPage").click(function(){
            if ($("#hader .maxPage").hasClass("maxPage_at")) {
                exit_max();
				$("#hader .maxPage").attr("titles","全屏");
            }
            else {
                max_page();
				$("#hader .maxPage").attr("titles","退出全屏");
            }
        });
    }
    catch (e) {
        alert(e.message);
    }
});
var MAXPAGE_TIGER = null;
//进入全屏
function max_page(){
    $("#pageLeft").addClass("none");
	$(".maxPage").addClass("maxPage_at");
    size();
	convertTbsSize();
//    $("#maxPage").show().animate({
//        top: 20
//    });
//    clearTimeout(MAXPAGE_TIGER);
//    MAXPAGE_TIGER = setTimeout(function(){
//        $("#maxPage").animate({
//            top: -50
//        }, function(){
//            $("#maxPage").hide();
//        });
//    }, 3000);
}

//移动出现提示
document.onmousemove = function(e){
    var event = e || window.event;
    var width = document.body.clientWidth;
    if ($("#hader").hasClass("none")) {
        if (event.clientY <= 10) {
            if ($("#hader").hasClass("none") && $("#maxPage")[0].style.display == "none") {
                $("#maxPage").show().animate({
                    top: 20
                });
                clearTimeout(MAXPAGE_TIGER);
                MAXPAGE_TIGER = setTimeout(function(){
                    $("#maxPage").animate({
                        top: -50
                    }, function(){
                        $("#maxPage").hide();
                    });
                }, 2000);
            }
        }
        if (event.clientY <= 35) {
            clearTimeout(MAXPAGE_TIGER);
        }
        else {
            clearTimeout(MAXPAGE_TIGER);
            MAXPAGE_TIGER = setTimeout(function(){
                $("#maxPage").animate({
                    top: -50
                }, function(){
                    $("#maxPage").hide();
                });
            }, 2000);
        }
    }
}
//退出
$("#maxPage").mouseout(function(){
    clearTimeout(MAXPAGE_TIGER);
    MAXPAGE_TIGER = setTimeout(function(){
        $("#maxPage").animate({
            top: -50
        }, function(){
            $("#maxPage").hide();
        });
    }, 3000);
});
//停止
$("#maxPage").mouseover(function(){
    clearTimeout(MAXPAGE_TIGER);
});
//退出全屏
function exit_max(){
    $("#hader,#pageLeft").removeClass("none");
    size();
	$(".maxPage").removeClass("maxPage_at");
	convertTbsSize();
//    $("#maxPage").animate({
//        top: -50
//    }, function(){
//        $("#maxPage").hide();
//    });
}

/**
 * 添加三级及以上菜单
 * @param {Object} list
 * @param {Object} paret
 */
function bindMenu(list, parent, isThree){
    $.each(list, function(index, item){
        var dl = $("<dl did='" + item.id + "' href='" + item.href + "'></dl>").appendTo(parent);
        if (isThree) {
            dl.addClass("threeLine");
        }
        var dt = $("<dt></dt>").appendTo(dl);
        var h3 = $("<h3></h3>").appendTo(dt);
        h3.click(function(){
            if (!$(this.parentNode).hasClass("at")) {
                var divChild = $("> div", this.parentNode.parentNode).slideDown("fast");
                $(this.parentNode).addClass("at");
            }
            else {
                var divs = $("> div", this.parentNode.parentNode).hide();
                var lis = $(this.parentNode).removeClass("at");
            }
            var name = $("label", this).html();
            var href = $(this).parent().parent().attr("href");
            var did = $(this).parent().parent().attr("did");
            if (href && href != "" && href != "null") {
                bindTabs(did, name, href);
            }
        }).mouseover(function(){
            var coord = $(this).coord();
            $(".twoMenuBg").css({
                top: coord.top - 60
            }).show();
        }).mouseout(function(){
            $(".twoMenuBg").hide();
        });
        if (item.icon) {
            $("<s><img src=\"" + item.icon + "\"></s>").appendTo(h3);
        }
        $("<label pinyin='" + item.pinyin + "' title='" + item.name + "'>" + item.name + "</label>").appendTo(h3);
        if (item.list && item.list.length > 0) {
            $("<i><em></em><span></span></i>").appendTo(h3);
            var div = $("<div></div>").appendTo(dl);
            bindMenu(item.list, div);
        }
    });
}

/**
 * 关闭页签回调
 * @param {Object} did
 * @param {Object} name
 */
function closeTabs(did, name){
	killConversation(did);
	TABS_ARRAY.remove(did);
}
/**
 * 外调关闭页签
 */
function closeTabByTabId(did){
	var tab = $(".pageTabs li.at");
	if (did) {
		tab = $(".pageTabs li[did='" + did + "']");
	}
	if (did.length > 0) {
		var at = tab.hasClass("at");
		var name = $("label", tab).html();
		closeTabs(did, name);
		tab.remove();
		$("#iframeContent iframe[did='" + did + "']").remove();
		if (at) {
			var id1 = TABS_ARRAY[TABS_ARRAY.length - 1];
			var li = $(".pageTabs li[did='" + id1 + "']").first();
			li.addClass("at");
			var did1 = li.attr("did");
			$("#iframeContent iframe[did='" + id1 + "']").show();
			tabs_index(id1, true);
		}
		else if ($("#pageCenter .pageTabs ul li").length == 1) {
			var id1 = TABS_ARRAY[TABS_ARRAY.length - 1];
			var li = $(".pageTabs li[did='" + id1 + "']").first();
			li.addClass("at");
			var did1 = li.attr("did");
			$("#iframeContent iframe[did='" + id1 + "']").show();
			tabs_index(id1, true);
		}
		convertTbsSize();
	}
}

/**
 * 设置页签
 * @param {Object} did 页签标识
 * @param {Object} name 页面名称
 * @param {Object} href 页签地址
 * @param {Object} mainPage 是否是一个新的主页
 */
function bindTabs(did, name, href, mainPage){
    var tbs = $(".pageTabs ul");
    //查找是否已经存在
    var li = $("li[did='" + did + "']", tbs);
    var dd = $(".tblMorePlan dd[did='" + did + "']");
    if (li.length > 0) {
        $("li.at", tbs).removeClass("at");
        li.addClass("at");
        $("#iframeContent iframe").hide();
        $("#iframeContent iframe[did='" + did + "']").show();
    }
    else if (dd.length > 0) {
        //是否存在更多页签里面
        moreTbsUpdate(dd);
    }
    else {
        $("li.at", tbs).removeClass("at");
        $("#iframeContent iframe").hide();
        //传入的页面没有传入相关参数
        if(href.indexOf("tabId")==-1){
            if (href.indexOf("?") != -1) {
                href += "&tabId=" + did + "&tabName=" + name + "&menuUuid=" + did + "&menuName=" + name;
            }
            else {
                href += "?tabId=" + did + "&tabName=" + name + "&menuUuid=" + did + "&menuName=" + name;
            }
        }
        if (mainPage) {
            var dmain = $("li.mainPage", tbs);
            if (dmain.length > 0) {
                $("li.at", tbs).removeClass("at");
                var odid = dmain.attr("did");
                dmain.attr("href", href).attr("did", did).addClass("at").html("<label title='" + name + "'>" + name + "</label>");
                //删除原来的iframe
                $("#iframeContent iframe[did='" + odid + "']").remove();
            }
            else {
                li = $("<li class='at mainPage' href='" + href + "' did='" + did + "'><label title='" + name + "'>" + name + "</label></li>").appendTo(tbs);
            }
        }
        else {
            li = $("<li class='at' href='" + href + "' did='" + did + "'><label title='" + name + "'>" + name + "</label><i></i></li>").appendTo(tbs);
        }
        var iframe = $("<iframe frameborder='no' width=\"100%\" did='" + did + "' height=\"100%\" style=\"border: none; float: left;\" src='" + href + "'></iframe>");
        iframe.appendTo("#iframeContent");
        if ($("#iframeContent iframe").length > 1) {
            $(".pageTabs").show();
            $("#iframeContent").css({
                "border-width": "0px"
            });
        }
        else {
            $(".pageTabs").hide();
            $("#iframeContent").css({
                "border-width": "1px"
            });
        }
        size();
        convertTbsSize();
    }
    tabs_index(did);
}

var TABS_ARRAY = [];
/**
 * 重新排列页签打开顺序
 * @param {Object} did
 */
function tabs_index(did, select){
    var rt = true;
    for (var i = 0; i < TABS_ARRAY.length; i++) {
        if (TABS_ARRAY[i] == did) {
            rt = true;
        }
    }
    if (rt) {
        TABS_ARRAY.remove(did);
    }
    TABS_ARRAY.push(did);
    //重新选中
    if (select) {
        var iframe = $("#iframeContent iframe[did='" + did + "']");
        if (iframe.length > 0) {
            iframe = iframe[0];
            var content = iframe.contentWindow;
            if (content) {
                var fun = content.onTabSelected;
                if ($.isFunction(fun)) {
                    fun();
                }
            }
        }
    }
}

Array.prototype.indexOf = function(val){
    for (var i = 0; i < this.length; i++) {
        if (this[i] == val) return i;
    }
    return -1;
};
Array.prototype.remove = function(val){
    var index = this.indexOf(val);
    if (index > -1) {
        this.splice(index, 1);
    }
};
/**
 * 计算页签显示状态
 */
function convertTbsSize(){
    var pageTabs = $(".pageTabs");
    var ul = $(".pageTabs ul");
    if ($("li", ul).length > 1) {
        if ((pageTabs.width() - 35) < $("li", ul).length * 141) {
            $(".tblMorePlan").show();
            //倒数第二个
            var lis = $("li", ul);
            var li = lis.eq(lis.length - 2);
            var dd = $("<dd did='" + li.attr("did") + "' href='" + li.attr("href") + "' title='" + $("label", li).html() + "'>" + $("label", li).html() + "</dd>").appendTo(".tblMorePlan dl");
            li.remove();
			if((pageTabs.width() - 35) < $("li", ul).length * 141){
				convertTbsSize();
			}
        }
        else if ($(".tblMorePlan dd").length > 0 && (pageTabs.width() - 155) > $("li", ul).length * 141) {
            var dd = $(".tblMorePlan dd").first();
            $("<li did='" + dd.attr("did") + "' href='" + dd.attr("href") + "'><label title='" + dd.html() + "'>" + dd.html() + "</label><i></i></li>").appendTo(ul);
            dd.remove();
            if ($(".tblMorePlan dd").length == 0) {
                $(".tblMorePlan").hide();
            }
			if($(".tblMorePlan dd").length > 0 && (pageTabs.width() - 155) > $("li", ul).length * 141){
				convertTbsSize();
			}
        }
        else {
            $(".tblMorePlan").hide();
        }
    }
    else {
        $(".pageTabs").hide();
        $("#iframeContent").css({
            "border-width": "1px"
        });
        $(".tblMorePlan").hide();
        size();
    }
}

/**
 * 将更多的页签存放到列表显示
 * @param {Object} dd
 */
function moreTbsUpdate(dd){
    //先删除掉最后一个
    var tbs = $(".pageTabs ul");
    $("li.at", tbs).removeClass("at");
    var did = dd.attr("did");
    var li = $("<li class='at' did='" + did + "' href='" + dd.attr("href") + "'><label>" + dd.html() + "</label><i></i></li>").appendTo(tbs);
    $("#iframeContent iframe").hide();
    $("#iframeContent iframe[did='" + did + "']").show();
    dd.remove();
    if ($(".tblMorePlan dd").length == 0) {
        $(".tblMorePlan").hide();
    }
    tabs_index(did, true);
    convertTbsSize(true);
}

/**
 * 为内部页面打开页签提供接口方法
 * @param tabid
 * @param title
 * @param url
 */
function openTabs(tabid, title, url){
    bindTabs(tabid, title, url, null);
}
