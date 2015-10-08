(function ($) {
    $.isBlank = function (obj) {
        if (!obj || $.trim(obj) === "")
            return true;
        if (obj.length && obj.length > 0)
            return false;

        for (var prop in obj)
            if (obj[prop])
                return false;
        return false;
    };

    $.isNotBlank = function (obj) {
        return !$.isBlank(obj);
    }
})(jQuery);

/**
 * Protect window.console method calls, e.g. console is not defined on IE
 * unless dev tools are open, and IE doesn't define console.debug
 */
(function () {
    if (!window.console) {
        window.console = {};
    }
    // union of Chrome, FF, IE, and Safari console methods
    var m = [
        "log", "info", "warn", "error", "debug", "trace", "dir", "group",
        "groupCollapsed", "groupEnd", "time", "timeEnd", "profile", "profileEnd",
        "dirxml", "assert", "count", "markTimeline", "timeStamp", "clear"
    ];
    // define undefined methods as noops to prevent errors
    for (var i = 0; i < m.length; i++) {
        if (!window.console[m[i]]) {
            window.console[m[i]] = function () {
            };
        }
    }
})();

/**
 * 设置指定form下的指定列显示或隐藏
 * @param formId 表单id
 * @param columnId 列id
 * @param visible 是否可见
 */
function setFormColumnVisible(formId, columnId, visible) {
    var column = $("#" + formId + " td[id*='" + columnId + "']");
    if (column) {
        if (visible)
            column.show();
        else
            column.hide();
    }
}

/**
 * 设置表单字段的必输性
 * @param formId 表单id
 * @param columnId 列id
 * @param required 是否必输
 */
function setFormColumnRequired(formId, columnId, required) {
    var columnLabel = $("#" + formId + "\\:" + columnId + "_label label");
    if (columnLabel) {
        if (required)
            columnLabel.addClass("required");
        else
            columnLabel.removeClass("required");
    }
}

/**
 * 获得表单的boolean值
 * @param formId
 * @param columnId
 * @returns {*|jQuery}
 */
function getFormColumnValueAsBoolean(formId, columnId) {
    var value = $("#" + formId + "\\:" + columnId+"_input").is(':checked');
    return value;
}