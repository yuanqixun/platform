package com.superbpm.platform.utils;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Page<T> {
    private int start;              // 当前页首条记录位置
    private int end;                // 当前页尾条记录位置
    private int limit;              // 每页记录数
    private int page;               // 当前页
    private int total;              // 总记录数
    private int totalPage;          // 总页数
    private String sortName;        // 排序列
    private String sortOrder;       // 排序方式
    private Map<String, String> params = new HashMap<String, String>();   // 请求参数
    private List<T> rows = new ArrayList<T>();               // 记录

    public Page(HttpServletRequest request) {
        String param = null;
        String value = null;

        param = "page";
        value = request.getParameter(param);
        if (value != null && value.length() > 0) {
            this.page = Integer.parseInt(value);
        } else {
            this.page = 0;
        }

        param = "limit";
        value = request.getParameter(param);
        if (value != null && value.length() > 0) {
            this.limit = Integer.parseInt(value);
        } else {
            this.limit = 0;
        }

        param = "sortname";
        value = request.getParameter(param);
        if (value != null && value.length() > 0) {
            this.sortName = value;
        }

        param = "sortorder";
        value = request.getParameter(param);
        if (value != null && value.length() > 0) {
            this.sortOrder = value;
        } else {
            this.sortOrder = "asc";
        }

    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public String getSortName() {
        return sortName;
    }

    public void setSortName(String sortName) {
        this.sortName = sortName;
    }

    public String getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(String sortOrder) {
        this.sortOrder = sortOrder;
    }

    public Map<String, String> getParams() {
        return params;
    }

    public void setParams(Map<String, String> params) {
        this.params = params;
    }

    public List<T> getRows() {
        return rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }
}
