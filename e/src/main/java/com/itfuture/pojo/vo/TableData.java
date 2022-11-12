package com.itfuture.pojo.vo;

import java.util.ArrayList;
import java.util.List;

//TODO：bootstrap-table支持的对象
public class TableData<T> {
    //每次查询的数据集合
    private List<T> rows = new ArrayList<>();
    //总数量
    private int total;

    public List<T> getRows() {
        return rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
