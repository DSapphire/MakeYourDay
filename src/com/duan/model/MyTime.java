package com.duan.model;

import java.io.Serializable;

public class MyTime implements Serializable,Comparable<MyTime>{
	private int hour;
	private int minute;
	private int second;
	private int weekday;//周几
	//跟课程相关的时间的默认值
	private static int[] courseStartHours={8,9,13,15,17,19};
	private static int[] courseStartMinutes={0,50,30,20,55,20};
	private static int[] courseEndHours={9,12,15,17,18,21};
	private static int[] courseEndMinutes={35,15,5,40,45,5};
	public MyTime() {
	}
	public MyTime(int hour,int minute) {
		this.hour=hour;
		this.minute=minute;
	}
	public int getWeekday() {
		return weekday;
	}
	public void setWeekday(int weekday) {
		this.weekday = weekday;
	}
	public int getHour() {
		return hour;
	}
	public void setHour(int hour) {
		this.hour = hour;
	}
	public int getMinute() {
		return minute;
	}
	public void setMinute(int minute) {
		this.minute = minute;
	}
	public int getSecond() {
		return second;
	}
	public void setSecond(int second) {
		this.second = second;
	}
	public void setAdvance(int min){
		if(minute>min){
			minute-=min;
		}else{
			int h=(min-minute)/60+1;
			minute=minute+h*60-min;
			hour-=h;
		}
	}
	//得到默认的课程开始时间
	public static MyTime getCourseStartTime(int tableId){
		MyTime time=new MyTime();
		if(tableId<7){
			time.setHour(courseStartHours[tableId-1]);
			time.setMinute(courseStartMinutes[tableId-1]);
		}
		return time;
	}
	//得到默认的课程结束时间
	public static MyTime getCourseEndTime(int tableId){
		MyTime time=new MyTime();
		if(tableId<7){
			time.setHour(courseEndHours[tableId-1]);
			time.setMinute(courseEndMinutes[tableId-1]);
		}
		return time;
	}
	public String toPlainText(){
		return String.format("%02d", hour)+" : "+String.format("%02d", minute)+" : "+String.format("%02d", second);
	}
	@Override
	public String toString() {
		return String.format("%02d", hour)+" : "+String.format("%02d", minute);
	}
	@Override
	public int compareTo(MyTime t) {
		int t1=this.hour*60+this.minute;
		int t2=t.getHour()*60+t.getMinute();
		return new Integer(t1).compareTo(new Integer(t2));
		
	}
	//是否更早与t
	public boolean before(MyTime t){
		boolean isBefore=true;
		if(this.compareTo(t)>0){
			isBefore=false;
		}
		return isBefore;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MyTime other = (MyTime) obj;
		if (hour != other.hour)
			return false;
		if (minute != other.minute)
			return false;
		return true;
	}
}
