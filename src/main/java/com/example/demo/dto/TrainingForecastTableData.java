package com.example.demo.dto;

import java.util.ArrayList;
import java.util.List;

public class TrainingForecastTableData extends GenericTableData {


    private List<MyEntry<String, Boolean>> months = new ArrayList<>();

	public List<MyEntry<String, Boolean>> getMonths() {
		return months;
	}

	public void setMonths(List<MyEntry<String, Boolean>> months) {
		this.months = months;
	}



}
