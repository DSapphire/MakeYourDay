package com.duan.model;

import java.io.Serializable;

public class MyRoutine extends MyActivity implements Serializable,MyType {
	private String sType;//类型的字符表示方法
	private boolean[] bType;//bType[i]表示一周中当天是否会响
	public String getsType() {
		return sType;
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
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MyRoutine other = (MyRoutine) obj;
		if (this.getType()!=other.getType())
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
		updateType();
		String s=getName()+" "+getStartTime()+" - "+getEndTime()+" "+sType;
		return s;
	}
}
