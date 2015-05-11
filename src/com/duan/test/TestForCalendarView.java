package com.duan.test;

import java.util.Calendar;

import com.duan.model.MyDayList;
import com.duan.view.CalendarView;

public class TestForCalendarView {

	public static void main(String[] args) {
		CalendarView view=new CalendarView(new MyDayList());
		view.calendarViewRepaint();
		
		Calendar cal=Calendar.getInstance();
		int days=cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		cal.set(Calendar.DAY_OF_MONTH,1);
		int first=cal.get(Calendar.DAY_OF_WEEK);
		int week=days+first-1;
		week/=7;
		System.out.println(days+":"+first+":"+week);
		System.out.println(cal.getTime());
	}

}
