package com.duan.model;

import java.util.Comparator;


public class MyTimeComparator implements Comparator<MyTime> {

	@Override
	public int compare(MyTime mt1, MyTime mt2) {
		int t1=mt1.getHour()*60+mt1.getMinute();
		int t2=mt2.getHour()*60+mt2.getMinute();
		return new Integer(t1).compareTo(new Integer(t2));
	}

}
