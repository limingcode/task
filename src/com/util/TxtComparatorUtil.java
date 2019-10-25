package com.util;

import java.util.Comparator;

import com.skyedu.model.TxtComparatorModel;

public class TxtComparatorUtil implements Comparator<TxtComparatorModel> {


	@Override
	public int compare(TxtComparatorModel a, TxtComparatorModel b) {
		// TODO Auto-generated method stub
		return a.getIndex() - b.getIndex();
	}

}
