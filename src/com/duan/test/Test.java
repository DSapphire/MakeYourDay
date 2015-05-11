package com.duan.test;

import java.util.Calendar;

import com.duan.model.MyClock;
import com.duan.model.MyClockList;
import com.duan.model.MyTime;
import com.duan.util.CreateAndRemove;
import com.duan.view.ClockView;

public class Test {

	public static void main(String[] args) {
//		
//		Calendar cl=Calendar.getInstance();
//		System.out.println(cl.getTime());
//		System.out.println(cl.get(Calendar.DAY_OF_MONTH));
//		System.out.println(cl.get(Calendar.DAY_OF_WEEK));
//		
//
//		Calendar cl2=Calendar.getInstance();
//		
//		cl2.set(2015, 3, 18, 12, 20, 30);
//		System.out.println(cl2.getTime());
//		System.out.println(cl2.get(Calendar.DAY_OF_MONTH));
//		System.out.println(Calendar.MONDAY);
//		cl2.setFirstDayOfWeek(2);
//		//System.out.println(cl2);
//		
//		CreateAndRemove.createCourseByDialog();
		
		MyClockList list=new MyClockList();
		MyClock clock=new MyClock();
		clock.setTime(new MyTime(12, 30));
		clock.setMemo("AAAa");
		clock.setType(65);
		list.getClockList().add(clock);
		//list.addClock(clock);
		ClockView newView;
		newView=new ClockView(list);
		newView.clockViewRepaint();
		list.getClockList().add(clock);
		list.getClockList().add(clock);
		//newView=new ClockView(list);
		newView.clockViewRepaint();
	}

}
