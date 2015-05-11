package com.duan.model;

import java.io.Serializable;
import java.util.Calendar;

public class MyClock implements Serializable{

	//type
	private String memo;
	private Calendar date;
	private MyTime time;
	private int type;//1 2 4 8 16 32 64
	private String sType;
	private int timesOfRing;
	private int intervalMinute;
	private boolean ring;
	public boolean isRing() {
		return ring;
	}
	public void setRing(boolean ring) {
		this.ring = ring;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public Calendar getDate() {
		return date;
	}
	public void setDate(Calendar date) {
		this.date = date;
	}
	public MyTime getTime() {
		return time;
	}
	public void setTime(MyTime time) {
		this.time = time;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getTimesOfRing() {
		return timesOfRing;
	}
	public void setTimesOfRing(int timesOfRing) {
		this.timesOfRing = timesOfRing;
	}
	public int getIntervalMinute() {
		return intervalMinute;
	}
	public void setIntervalMinute(int intervalMinute) {
		this.intervalMinute = intervalMinute;
	}
	public void updateType(){
		if(type==127){
			sType="每天";
		}else if(type==0){
			sType="单次";
		}else{
			StringBuffer sb=new StringBuffer();
			String s[]={
					"周一 ","周二 ","周三 ","周四 ","周五 ","周六 ","周日 "};
			int i=type;
			int j=0;
			while(i>0){
				if(i%2==1){
					sb.append(s[j]);
				}
				j++;
				i/=2;
			}
			sType=sb.toString();
		}
	}
	public String toString(){
		updateType();
		return this.memo+"  "+time.toString()+"  "+sType;
	}
}
