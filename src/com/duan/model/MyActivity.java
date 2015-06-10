package com.duan.model;

import java.io.Serializable;
import java.util.Calendar;

public class MyActivity implements Serializable{
	protected String name;
	protected int type;//类型
	protected String place;
	protected MyPriority priority;//优先级
	protected MyTime startTime;//开始时间
	protected MyTime endTime;//结束时间
	protected boolean isFinished;//完成
	public MyActivity(){
		
	}
	public MyActivity(String name){
		this.name=name;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getPlace() {
		return place;
	}
	public void setPlace(String place) {
		this.place = place;
	}
	public MyPriority getPriority() {
		return priority;
	}
	public void setPriority(MyPriority priority) {
		this.priority = priority;
	}
	public boolean isFinished() {
		return isFinished;
	}
	public void setFinished(boolean isFinished) {
		this.isFinished = isFinished;
	}
	//设置提醒时间，参数是比开始时间提前的分钟数
	public MyTime getRemindTime(int min){
		MyTime time=new MyTime(startTime.getHour(),startTime.getMinute());
		time.setAdvance(min);
		return time;
	}
	public MyTime getStartTime() {
		return startTime;
	}
	public void setStartTime(MyTime startTime) {
		this.startTime = startTime;
	}
	public MyTime getEndTime() {
		return endTime;
	}
	public void setEndTime(MyTime endTime) {
		this.endTime = endTime;
	}
}
