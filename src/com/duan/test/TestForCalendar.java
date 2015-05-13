package com.duan.test;

import java.util.Calendar;

import com.duan.model.MyDay;
import com.duan.model.MyDayList;
import com.duan.view.ActivityView;

public class TestForCalendar {
	public static void main(String[] args) {
//		MyDay day1,day2;
//		day1=new MyDay();
//		day2=new MyDay();
//		day1.setDate(Calendar.getInstance());
//		Calendar date=Calendar.getInstance();
//		date.add(Calendar.DAY_OF_MONTH, 1);
//		day2.setDate(date);
//		System.out.println(day2.compareTo(day1));
		ActivityView view=
		new ActivityView(new MyDayList(), 7);
		view.activityView();
	}

}
