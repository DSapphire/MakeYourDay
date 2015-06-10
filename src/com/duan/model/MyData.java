package com.duan.model;
import java.io.IOException;
import java.util.Calendar;
import java.util.Observable;

import com.duan.util.ActivityReminder;
import com.duan.util.SaveAndRead;
public class MyData extends Observable{
	//需要载入的数据，在view之间传递
	private MyClockList clockList;//闹钟
	private MyDayList dayList;//日程
	private MyCourseTable table;//课程表
	private MyTaskList taskList;//任务
	private MyRoutineList routineList;//日常
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
	//从文件读取数据
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
	//保存数据
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
	//把任务添加到当天以及任务列表中
	public boolean addTask(MyDay theDay, MyTask task){
		if(theDay.addTask(task)){
			taskList.getTaskList().add(task);
			return true;
		}
		return false;
	}
	//把日常从日常列表添加到日程中
	public void addRoutine(){
		for(MyDay day:dayList.getDayList()){
			day.addRoutine(routineList);
		}
	}
	//把日常添加到当天以及日常列表中
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
	//从任务列表删除任务
	public void removeTask(MyTask task){
		for(MyDay day:dayList.getDayList()){
			day.removeTask(task);
		}
		taskList.getTaskList().remove(task);
	}
	//从当天以及任务列表中删除任务
	public void removeTask(MyDay theDay,MyTask task){
		theDay.removeTask(task);
		taskList.getTaskList().remove(task);
	}
	//从日常列表删除日常
	public void removeRoutine(MyRoutine routine){
		for(MyDay day:dayList.getDayList()){
			day.removeRoutine(routine);
		}
		routineList.getRoutineList().remove(routine);
	}
	//从当天以及日常列表中删除日常
	public void removeRoutine(MyDay theDay,MyRoutine routine){
		theDay.removeRoutine(routine);
		routineList.getRoutineList().remove(routine);
	}
}
