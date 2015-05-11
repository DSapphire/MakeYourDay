package com.duan.model;

import java.io.Serializable;
import java.util.Calendar;

public class MyActivity implements Serializable{

	private String name;
	private int type;
	private String place;
	
	private MyPriority priority;
	private MyTime startTime;
	private MyTime endTime;
	
	boolean isFinished;
	boolean setAlarmed;
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
	public boolean isSetAlarmed() {
		return setAlarmed;
	}
	public void setSetAlarmed(boolean setAlarmed) {
		this.setAlarmed = setAlarmed;
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
