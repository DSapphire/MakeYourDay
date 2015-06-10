package com.duan.model;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MyTask extends MyActivity implements Serializable{
	private Calendar deadLine;//截止日期
	private DateFormat df = new SimpleDateFormat("Y-M-d E");//显示截止时间的格式
	public Calendar getDeadLine() {
		return deadLine;
	}
	public void setDeadLine(Calendar deadLine) {
		this.deadLine = deadLine;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MyTask other = (MyTask) obj;
		if (!df.format(deadLine.getTime()).equals(df.format(other.getDeadLine().getTime())))
			return false;
		else if(!this.getEndTime().equals(other.getEndTime()))
			return false;
		else if(!this.getStartTime().equals(other.getStartTime()))
			return false;
		else if(!this.getName().equals(other.getName()))
			return false;
		return true;
	}
	public String toString(){
		String s=getName()+"  "+getStartTime()+" - "
				+this.getEndTime()+" 截止日期 "+df.format(deadLine.getTime());
		return s;
	}
}
