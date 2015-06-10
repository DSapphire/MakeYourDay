package com.duan.model;
import java.io.IOException;
import java.util.Calendar;
import java.util.Observable;

import com.duan.util.ActivityReminder;
import com.duan.util.SaveAndRead;
public class MyData extends Observable{
	//��Ҫ��������ݣ���view֮�䴫��
	private MyClockList clockList;//����
	private MyDayList dayList;//�ճ�
	private MyCourseTable table;//�γ̱�
	private MyTaskList taskList;//����
	private MyRoutineList routineList;//�ճ�
	public MyClockList getClockList() {
		return clockList;
	}
	public void setClockList(MyClockList clockList) {
		this.clockList = clockList;
	}
	public MyDayList getDayList() {
		return dayList;
	}
	public void setDayList(MyDayList dayList) {
		this.dayList = dayList;
	}
	public MyCourseTable getTable() {
		return table;
	}
	public void setTable(MyCourseTable table) {
		this.table = table;
	}
	public MyTaskList getTaskList() {
		return taskList;
	}
	public void setTaskList(MyTaskList taskList) {
		this.taskList = taskList;
	}
	public MyRoutineList getRoutineList() {
		return routineList;
	}
	public void setRoutineList(MyRoutineList routineList) {
		this.routineList = routineList;
	}
	//���ļ���ȡ����
	public void readData(){
		this.clockList=SaveAndRead.readClockList();
		this.table=SaveAndRead.readCourseTable();
		this.dayList=SaveAndRead.readDayList();
		this.routineList=SaveAndRead.readRoutineList();
		this.taskList=SaveAndRead.readTaskList();
		if(dayList==null){
			dayList=new MyDayList();
		}
		if(table==null){
			table=new MyCourseTable();
		}
		if(clockList==null){
			clockList=new MyClockList();
		}
		if(taskList==null){
			taskList=new MyTaskList();
		}
		if(routineList==null){
			routineList=new MyRoutineList();
		}
	}
	//��������
	public boolean saveData(){
		try {
			SaveAndRead.saveClockList(this.clockList);
			SaveAndRead.saveCourseTable(this.table);
			SaveAndRead.saveDayList(this.dayList);
			SaveAndRead.saveRoutineList(this.routineList);
			SaveAndRead.saveTaskList(this.taskList);
			return true;
		} catch (IOException e) {
			return false;
		}
	}
	//��������ӵ������Լ������б���
	public boolean addTask(MyDay theDay, MyTask task){
		if(theDay.addTask(task)){
			taskList.getTaskList().add(task);
			return true;
		}
		return false;
	}
	//���ճ����ճ��б���ӵ��ճ���
	public void addRoutine(){
		for(MyDay day:dayList.getDayList()){
			day.addRoutine(routineList);
		}
	}
	//���ճ���ӵ������Լ��ճ��б���
	public boolean addRoutine(MyDay theDay,MyRoutine routine){
		if(routine.isTodayIncluded(theDay.getDate().get(Calendar.DAY_OF_WEEK)))
			if(!theDay.addRoutine(routine))
				return false;
		for(MyDay day:dayList.getDayList()){
			if(routine.isTodayIncluded(day.getDate().get(Calendar.DAY_OF_WEEK))){
				day.addRoutine(routine);
			}
		}
		routineList.getRoutineList().add(routine);
		return true;
	}
	//�������б�ɾ������
	public void removeTask(MyTask task){
		for(MyDay day:dayList.getDayList()){
			day.removeTask(task);
		}
		taskList.getTaskList().remove(task);
	}
	//�ӵ����Լ������б���ɾ������
	public void removeTask(MyDay theDay,MyTask task){
		theDay.removeTask(task);
		taskList.getTaskList().remove(task);
	}
	//���ճ��б�ɾ���ճ�
	public void removeRoutine(MyRoutine routine){
		for(MyDay day:dayList.getDayList()){
			day.removeRoutine(routine);
		}
		routineList.getRoutineList().remove(routine);
	}
	//�ӵ����Լ��ճ��б���ɾ���ճ�
	public void removeRoutine(MyDay theDay,MyRoutine routine){
		theDay.removeRoutine(routine);
		routineList.getRoutineList().remove(routine);
	}
}
