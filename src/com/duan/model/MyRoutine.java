package com.duan.model;

import java.io.Serializable;

public class MyRoutine extends MyActivity implements Serializable {
	private String sType;
	
	public String getsType() {
		return sType;
	}
	public void setsType(String sType) {
		this.sType = sType;
	}

	public void updateType(){
		if(this.getType()==127){
			sType="ÿ��";
		}else if(this.getType()==0){
			sType="����";
		}else{
			StringBuffer sb=new StringBuffer();
			String s[]={
					"��һ ","�ܶ� ","���� ","���� ","���� ","���� ","���� "};
			int i=this.getType();
			int j=0;
			while(i>0&&j<s.length){
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
		String s=getName()+" "+getStartTime()+" - "+getEndTime()+" "+sType;
		return s;
	}
}
