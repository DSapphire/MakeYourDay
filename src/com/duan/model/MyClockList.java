package com.duan.model;

import java.io.Serializable;
import java.util.ArrayList;

public class MyClockList implements Serializable{
	private ArrayList<MyClock> clockList;
	public MyClockList() {
		clockList=new ArrayList<MyClock>();
	}
	public ArrayList<MyClock> getClockList() {
		return clockList;
	}
	public void setClockList(ArrayList<MyClock> clockList) {
		this.clockList = clockList;
	}
	
}
