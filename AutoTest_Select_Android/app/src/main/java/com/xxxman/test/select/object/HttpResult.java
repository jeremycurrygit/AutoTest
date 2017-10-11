package com.xxxman.test.select.object;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class HttpResult {
    private Map<String, String> head = new HashMap<String, String>(); //头标签
    private List<DataRow> list = new ArrayList<DataRow>(); //返回数据

    /**
     * 错误编号
     */
    public String getErrorNo() {
        return head.get("errorNo");
    }

    /**
     * 错误编号
     */
    public void setErrorNo(String errorNo) {
        if (errorNo != null) {
            head.put("errorNo", errorNo);
        }
    }

    /**
     * 错误信息
     */
    public String getErrorInfo() {
        return head.get("errorInfo");
    }

    /**
     * 错误信息
     */
    public void setErrorInfo(String errorInfo) {
        if (errorInfo != null) {
            head.put("errorInfo", errorInfo);
        }
    }

    /**
     * 获得单行数据
     */
    public DataRow getDataRow() {
        if (list.size() > 0) {
            return list.get(0);
        } else {
            return null;
        }
    }

    /**
     * 获得所有结果数据
     */
    public List<DataRow> getList() {
        return this.list;
    }

    /**
     * 添加结果
     *
     * @param list
     */
    public void setList(List<DataRow> list) {
        if (list != null) {
            this.list = list;
        }
    }

    /**
     * 添加结果
     */
    public void addDataRow(DataRow dataRow) {
        this.list.add(dataRow);
    }

    /**
     * toString方法
     */
    public String toString() {
        StringBuffer result = new StringBuffer("");
        for (DataRow dataRow : list) {
            if (dataRow != null) {
                result.append(dataRow.toString());
            }
        }
        return "errorNo=" + head.get("errorNo") + " ;errorInfo=" + head.get("errorInfo") + "  ;list<DataRow>=" + result.toString();
    }
}