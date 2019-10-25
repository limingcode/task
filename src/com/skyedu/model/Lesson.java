package com.skyedu.model;

import java.util.List;

public class Lesson {

	private List<Work> workList;
	
	private List<ResultCard> resultCardList;

	public List<Work> getWorkList() {
		return workList;
	}

	public void setWorkList(List<Work> workList) {
		this.workList = workList;
	}

	public List<ResultCard> getResultCardList() {
		return resultCardList;
	}

	public void setResultCardList(List<ResultCard> resultCardList) {
		this.resultCardList = resultCardList;
	}

}
