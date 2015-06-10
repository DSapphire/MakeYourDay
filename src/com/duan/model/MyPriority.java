package com.duan.model;

import java.io.Serializable;

public class MyPriority implements Comparable<MyPriority>, Serializable {
	private final int MAXU=5;
	private final int MAXI=5;
	private int urgency;//�����̶�
	private int importance;//��Ҫ�̶�
	private int mutilForUrgence=1;//�����̶ȵĳ���
	public int getUrgency() {
		return urgency;
	}
	public void setUrgency(int urgency) {
		this.urgency = urgency;
	}
	public int getImportance() {
		return importance;
	}
	public void setImportance(int importance) {
		this.importance = importance;
	}
	public int getMutilForUrgence() {
		return mutilForUrgence;
	}
	public void setMutilForUrgence(int mutilForUrgence) {
		this.mutilForUrgence = mutilForUrgence;
	}
	//���ؿγ̵�Ĭ�����ȼ�
	public static MyPriority getCoursePriority(int type){
		MyPriority priority=new MyPriority();
		priority.setUrgency(3);
		priority.setImportance(type*2);
		return priority;
	}
	//�����̶�����
	public void addE(int add){
		this.urgency+=add;
		if(this.urgency>MAXU){
			this.urgency=MAXU;
		}
	}
	//��Ҫ�̶�����
	public void addI(int add){
		this.importance+=add;
		if(this.importance>MAXI){
			this.importance=MAXI;
		}
	}
	//�����Ƿ�������imp����Ҫ
	public boolean isImp(int imp){
		return this.importance>imp;
	}
	//�����Ƿ�������urg������
	public boolean isUrg(int urg){
		return this.urgency>urg;
	}
	//�Ƚ��������ȼ��Ǹ�������
	@Override
	public int compareTo(MyPriority p) {
		int p1=this.mutilForUrgence*this.urgency+this.importance;
		int p2=p.getImportance()+p.getMutilForUrgence()*p.getUrgency();
		return new Integer(p1).compareTo(new Integer(p2));
	}

}
