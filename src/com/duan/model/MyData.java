package com.duan.model;

import java.io.IOException;
import com.duan.util.SaveAndRead;

public class MyData {
	//需要载入的数据
	private MyClockList clockList;
	private MyDayList dayList;
	private MyCourseTable table;
	private MyTaskList taskList;
	private MyRoutineList routineList;
	public MyData() {
		this.clockList=new MyClockList();
		this.dayList=new MyDayList();
		this.routineList=new MyRoutineList();
		this.taskList=new MyTaskList();
		this.table=new MyCourseTable();
	}
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
	public void readData(){
		this.clockList=SaveAndRead.readClockList();
		this.table=SaveAndRead.readCourseTable();
		this.dayList=SaveAndRead.readDayList();
		this.routineList=SaveAndRead.readRoutineList();
		this.taskList=SaveAndRead.readTaskList();
	}
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
	public void update(int updateType ){
		
	}
}
