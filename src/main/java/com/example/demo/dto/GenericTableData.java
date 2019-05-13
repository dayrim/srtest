package com.example.demo.dto;

import java.util.ArrayList;
import java.util.List;

public class GenericTableData {
    private List columns;
    private List<List> rows = new ArrayList<>();

    public GenericTableData() {
    }

    public GenericTableData(List columns) {
        this.columns = columns;
    }

    public GenericTableData(List columns, List<List> rows) {
        this.columns = columns;
        this.rows = rows;
    }

    public List getColumns() {
		return columns;
	}

	public void setColumns(List columns) {
		this.columns = columns;
	}

	public List<List> getRows() {
		return rows;
	}

	public void setRows(List<List> rows) {
		this.rows = rows;
	}

	public void addRow(MyEntry<Long, String> meta, List data) {
        List row = new ArrayList<>();
        row.add(meta);
        row.addAll(data);
        this.rows.add(row);
    }

    public void addRow(int index, MyEntry<Long, String> meta, List data) {
        List row = new ArrayList<>();
        row.add(meta);
        row.addAll(data);
        this.rows.add(index, row);
    }
}
