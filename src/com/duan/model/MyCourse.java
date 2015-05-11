package com.duan.model;

import java.io.Serializable;

import javax.swing.JOptionPane;

public class MyCourse extends MyActivity implements Serializable{
	
	private String cname;
	private String teacher;

	private String ctype;
	private String weekType;
	
	private int tableId;
	private int dayOfWeek;
	private int key;
	public MyCourse(){
		
	}
	public MyCourse(String name){
		this.cname=name;
	}
	public MyCourse(String name,int tableId,int dayOfWeek){
		this.cname=name;
		this.tableId=tableId;
		this.dayOfWeek=dayOfWeek;
		this.key=this.dayOfWeek*100+this.tableId;
	}
	public String getCname() {
		return cname;
	}
	public void setCname(String cname) {
		this.cname = cname;
	}
	public int getDayOfWeek() {
		return dayOfWeek;
	}
	public void setDayOfWeek(int dayOfWeek) {
		this.dayOfWeek = dayOfWeek;
	}
	public int getTableId() {
		return tableId;
	}
	public void setTableId(int tableId) {
		this.tableId = tableId;
	}
	public String getTeacher() {
		return teacher;
	}
	public void setTeacher(String teacher) {
		this.teacher = teacher;
	}
	public String getCtype() {
		return ctype;
	}
	public void setCtype(String ctype) {
		this.ctype = ctype;
	}
	private void updateKey(){
		this.key=this.dayOfWeek*100+this.tableId;
	}
	public int getKey() {
		updateKey();
		return key;
	}
	
	public String getWeekType() {
		return weekType;
	}
	public void setWeekType(String weekType) {
		this.weekType = weekType;
	}
	private String getStringDayOfWeek(){
		String s=null;
		switch(this.dayOfWeek){
			case 1:
				s="����";
				break;
			case 2:
				s="��һ";
				break;
			case 3:
				s="�ܶ�";
				break;
			case 4:
				s="����";
				break;
			case 5:
				s="����";
				break;
			case 6:
				s="����";
				break;
			case 7:
				s="����";
				break;
		}
		return s;
	}
	public String getStringDayOfWeek(int i){
		String s=null;
		switch(i){
			case 0:
				s=" ";
				break;
			case 1:
				s="����";
				break;
			case 2:
				s="��һ";
				break;
			case 3:
				s="�ܶ�";
				break;
			case 4:
				s="����";
				break;
			case 5:
				s="����";
				break;
			case 6:
				s="����";
				break;
			case 7:
				s="����";
				break;
		}
		return s;
	}
	public boolean setTime(String time){
		try{
			int temp=Integer.parseInt(time);
			tableId=temp%10;
			temp/=10;
			if(temp==7){
				dayOfWeek=1;
			}else{
				dayOfWeek=temp+1;
			}
			updateTime();
			return true;
		}catch(Exception e){
			JOptionPane.showMessageDialog(null, "ʱ���������");
			return false;
		}
	}
	public void updateTime(){
		setStartTime(MyTime.getCourseStartTime(tableId));
		setEndTime(MyTime.getCourseEndTime(tableId));
	}
	public String toPlainText(){
		return cname + "(" + teacher + ","
				+ ctype +","+weekType+ ","+this.getPlace()+")";
	}
	@Override
	public String toString() {
		return "\n" + cname + "\n�ον�ʦ=" + teacher + "\n�γ����ͣ�"
				+ ctype + "\n�ϿνڴΣ�" +weekType+getStringDayOfWeek()+"��" +tableId+
				"��"+"\n�Ͽ�ʱ��"+this.getStartTime()+" - "+this.getEndTime()+
				"\n�Ͽεص㣺"+this.getPlace();
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MyCourse other = (MyCourse) obj;
		if(this.key==other.key)
			return true;
		return false;
	}
}
