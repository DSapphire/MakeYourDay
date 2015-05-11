package com.duan.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;

public class MyDay implements Serializable{
	
	private ArrayList<MyTime> timeList;
	private ArrayList<MyCourse> courseList;
	private ArrayList<MyTask> taskList;
	private ArrayList<MyRoutine> routineList;
	private Calendar date;
	
	public MyDay() {
		timeList = new ArrayList<MyTime>();
		courseList = new ArrayList<MyCourse>();
		taskList = new ArrayList<MyTask>();
		routineList = new ArrayList<MyRoutine>();
		date = Calendar.getInstance();
	}
	public void addCourse(MyCourseTable table) {
		int day = date.get(Calendar.DAY_OF_WEEK);
		this.courseList.addAll(table.getTable().get(day - 1));
		for (MyCourse course : this.courseList) {
			this.timeList.add(course.getStartTime());
			this.timeList.add(course.getEndTime());
		}
		this.timeList.sort(new MyTimeComparator());
	}

	public void addTask(MyTask task){
		MyTime startTime=task.getStartTime();
		MyTime endTime=task.getEndTime();
		if(checkValid(startTime)&&checkValid(endTime)){
			this.taskList.add(task);
			this.timeList.add(startTime);
			this.timeList.add(endTime);
			this.timeList.sort(new MyTimeComparator());
		}else{
			//
			System.out.println("Erro ");
		}
	}

	public void addRoutine(MyRoutine routine) {
		MyTime startTime=routine.getStartTime();
		MyTime endTime=routine.getEndTime();
		if(checkValid(startTime)&&checkValid(endTime)){
			this.routineList.add(routine);
			this.timeList.add(startTime);
			this.timeList.add(endTime);
			this.timeList.sort(new MyTimeComparator());
		}else{
			//
			System.out.println("Erro ");
		}
	}

	public boolean checkValid(MyTime time) {
		boolean valid = true;
		if (!this.timeList.isEmpty()) {
			for (int i = 0; i < this.timeList.size() - 1; i += 2) {
				if (this.timeList.get(i).before(time)
						&& !this.timeList.get(i + 1).before(time)) {
					valid = false;
					break;
				}
			}
		}

		return valid;
	}
	public ArrayList<MyTime> getTimeList() {
		return timeList;
	}
	public ArrayList<MyCourse> getCourseList() {
		return courseList;
	}
	public ArrayList<MyTask> getTaskList() {
		return taskList;
	}
	public ArrayList<MyRoutine> getRoutineList() {
		return routineList;
	}
	
	public Calendar getDate() {
		return date;
	}
	public void setDate(Calendar date) {
		this.date = date;
	}
}
