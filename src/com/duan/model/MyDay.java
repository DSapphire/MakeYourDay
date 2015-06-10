package com.duan.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;

public class MyDay implements Serializable{
	
	private ArrayList<MyTime> timeList;
	private ArrayList<MyCourse> courseList;
	private ArrayList<MyTask> taskList;
	private ArrayList<MyRoutine> routineList;
	private Calendar date;
	private boolean activityAdded=false;
	public MyDay() {
		timeList = new ArrayList<MyTime>();
		courseList = new ArrayList<MyCourse>();
		taskList = new ArrayList<MyTask>();
		routineList = new ArrayList<MyRoutine>();
		date = Calendar.getInstance();
	}
	//添加课程
	public void addCourse(MyCourseTable table) {
		int day = date.get(Calendar.DAY_OF_WEEK);
		removeCourse();
		this.courseList.addAll(table.getTable().get(day - 1));
		for (MyCourse course : this.courseList) {
			this.timeList.add(course.getStartTime());
			this.timeList.add(course.getEndTime());
		}
		this.timeList.sort(new MyTimeComparator());
	}
	//删除所有课程
	private void removeCourse(){
		if(!courseList.isEmpty()||courseList!=null)
			for (int i=courseList.size();i>0;i--){
				MyCourse course=courseList.get(i-1);
				removeTime(course.getStartTime());
				removeTime(course.getEndTime());
				this.courseList.remove(i-1);
			}
	}
	//添加任务
	public boolean addTask(MyTask task){
		MyTime startTime=task.getStartTime();
		MyTime endTime=task.getEndTime();
		//if(checkValid(startTime)&&checkValid(endTime)){
		if(checkValid(startTime,endTime)){
			this.taskList.add(task);
			this.timeList.add(startTime);
			this.timeList.add(endTime);
			this.timeList.sort(new MyTimeComparator());
			return true;
		}
		return false;
	}
	//添加单个日常
	public boolean addRoutine(MyRoutine routine) {
		MyTime startTime=routine.getStartTime();
		MyTime endTime=routine.getEndTime();
		if(checkValid(startTime,endTime)){
			this.routineList.add(routine);
			this.timeList.add(startTime);
			this.timeList.add(endTime);
			this.timeList.sort(new MyTimeComparator());
			return true;
		}
		return false;
	}
	//添加整个日常列表中当天需要处理的
	public void addRoutine(MyRoutineList routineList){
		removeRoutine();
		for(int i=0;i<routineList.getRoutineList().size();i++){
			MyRoutine routine=routineList.getRoutineList().get(i);
			if(routine.isTodayIncluded(date.get(Calendar.DAY_OF_WEEK)))
				addRoutine(routine);
		}
	}
	//删除所有日常
	private void removeRoutine(){
		if(routineList!=null&&!routineList.isEmpty())
			for(int i=routineList.size();i>0;i--){
				removeRoutine(routineList.get(i-1));
			}
	}
	//删除单个任务
	public void removeTask(MyTask task){
		if(taskList.remove(task)){
			removeTime(task.getStartTime());
			removeTime(task.getEndTime());
		}
	}
	//删除单个日常
	public void removeRoutine(MyRoutine routine){
		if(routineList.remove(routine)){
			removeTime(routine.getStartTime());
			removeTime(routine.getEndTime());
		}
	}
	//删除时间列
	private void removeTime(MyTime time){
		if(timeList!=null&&!timeList.isEmpty())
			for(int i=0;i<this.timeList.size();i++){
				MyTime thistime=this.timeList.get(i);
				if(time.equals(thistime)){
					this.timeList.remove(i);
					break;
				}
			}
	}
	//检查时间是否冲突，这个是过时检查方法，有误
	public boolean checkValid(MyTime time) {
		boolean valid = true;
		if(this.taskList.isEmpty()&&this.routineList.isEmpty()
				&&this.courseList.isEmpty()){
			this.timeList=new ArrayList<>();
		}
		if (!this.timeList.isEmpty()) {
			for (int i = 0; i < this.timeList.size()-1; i += 2) {
				if (this.timeList.get(i).before(time)
						&& !this.timeList.get(i + 1).before(time)) {
					valid = false;
					break;
				}
			}
		}
		return valid;
	}
	public boolean checkValid(MyTime time1,MyTime time2) {
		boolean valid = true;
		if(this.taskList.isEmpty()&&this.routineList.isEmpty()
				&&this.courseList.isEmpty()){
			this.timeList=new ArrayList<>();
		}
		if(time2.before(time1))
			return false;
		else if (!this.timeList.isEmpty()){
			//检查开始时间
			for(int i=0;i<this.timeList.size()-1;i+=2){
				MyTime time=timeList.get(i);
				if(time1.before(time)&&!time2.before(time)){
					valid=false;
					break;
				}
			}
			//检查结束时间
			for(int i=1;i<this.timeList.size();i+=2){
				MyTime time=timeList.get(i);
				if(time.before(time2)&&!time.before(time1)){
					valid=false;
					break;
				}
			}
		}
		return valid;
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
	public boolean isActivityAdded() {
		return activityAdded;
	}
	public void setActivityAdded(boolean activityAdded) {
		this.activityAdded = activityAdded;
	}
	
}