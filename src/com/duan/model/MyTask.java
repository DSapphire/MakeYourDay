package com.duan.model;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MyTask extends MyActivity implements Serializable{
	
	private Calendar deadLine;
	private MyTime extraTime;
	private MyTime interval;
	private DateFormat df = new SimpleDateFormat(" Y-M-d E");
	public MyTime getInterval() {
		return interval;
	}
	public void setInterval(MyTime interval) {
		this.interval = interval;
	}
	public Calendar getDeadLine() {
		return deadLine;
	}
	public void setDeadLine(Calendar deadLine) {
		this.deadLine = deadLine;
	}
	public MyTime getExtraTime() {
		return extraTime;
	}
	public void setExtraTime(MyTime extraTime) {
		this.extraTime = extraTime;
	}
	public String toString(){
		String s=getName()+"  "+getStartTime()+" - "
				+this.getEndTime()+" ½ØÖ¹ÈÕÆÚ "+df.format(deadLine.getTime());
		return s;
	}
}
