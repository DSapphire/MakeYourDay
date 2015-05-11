package com.duan.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;

public class MyDayList implements Serializable{
	private ArrayList<MyDay> dayList;
	public MyDayList() {
		dayList=new ArrayList<MyDay>();
	}
	public ArrayList<MyDay> getDayList() {
		return dayList;
	}
	public void setDayList(ArrayList<MyDay> dayList) {
		this.dayList = dayList;
	}
	public MyDay getDay(int year,int month,int day){
		Calendar cal;
		for(int i=0;i<this.dayList.size();i++){
			cal=this.dayList.get(i).getDate();
			if(day==cal.get(Calendar.DAY_OF_MONTH)&&
					month==cal.get(Calendar.MONTH)+1&&
					year==cal.get(Calendar.YEAR)){
				return this.dayList.get(i);
			}
		}
		return null;
	}
}
