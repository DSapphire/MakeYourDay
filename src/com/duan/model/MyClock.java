package com.duan.model;

import java.io.Serializable;
import java.util.Calendar;

public class MyClock implements Serializable,MyType{
	private String memo;//备忘名称
	private MyTime time;//时间
	private int type;//类型，表示周一到周日哪天会响，分别用1 2 4 8 16 32 64表示某一天
	private boolean[] bType;//bType[i]表示一周中当天是否会响
	private String sType;//类型的字符表示方法
	private boolean ring=true;
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
	//通过type更新bType和sType
	public void updateType(){
		if(bType==null)
			bType=new boolean[7];
		int i=getType();
		int j=0;
		while(i>0&&j<7){
			if(i%2==1)
				bType[j]=true;
			else
				bType[j]=false;
			i/=2;
			j++;
		}
		if(this.getType()==127){
			sType="每天";
		}else if(this.getType()==0){
			sType="单次";
		}else{
			StringBuffer sb=new StringBuffer();
			String s[]={
					"周日 ","周一 ","周二 ","周三 ","周四 ","周五 ","周六 "};
			for(int k=0;k<bType.length;k++){
				if(bType[k]){
					sb.append(s[k]);
				}
			}
			sType=sb.toString();
		}
	}
	//一周中的当天是否包含在类型当中
	public boolean isTodayIncluded(int dayOfWeek){
		updateType();
		return bType[dayOfWeek-1];
	}
	@Override
	public String toString(){
		updateType();
		return this.memo+"  "+time.toString()+"  "+sType;
	}
}
