package com.duan.model;

import java.io.Serializable;
import java.util.ArrayList;

public class MyCourseTable implements Serializable {

	private ArrayList<ArrayList<MyCourse>> courseTable;//一周的课程
	public MyCourseTable() {
		this.courseTable = new ArrayList<ArrayList<MyCourse>>();
		for (int i = 0; i < 7; i++){
			courseTable.add(new ArrayList<MyCourse>());
		}
	}
	public ArrayList<ArrayList<MyCourse>> getTable() {
		return this.courseTable;
	}
	//添加课程
	public boolean addCourse(MyCourse course) {
		if (isValid(course)) {
			this.courseTable.get(course.getDayOfWeek() - 1).add(course);
			return true;
		} 
		return false;
	}
	//删除课程
	public boolean removeCourse(MyCourse course) {
		return this.courseTable.get(course.getDayOfWeek() - 1).remove(course);
	}
	public boolean removeCourse(int dayOfWeek,int tableId) {
		for(MyCourse course:this.courseTable.get(dayOfWeek - 1)){
			if(course.getTableId()==tableId){
				return this.courseTable.get(dayOfWeek - 1).remove(course);
			}
		}
		return false;
	}
	public boolean removeCourse(String name) {
		for(int i=0;i<7;i++){
			for(MyCourse course:this.courseTable.get(i)){
				if(course.getName().equals(name)){
					return this.courseTable.get(i).remove(course);
				}
			}
		}
		return false;
	}
	//根据课程唯一的key确定课程是否可以添加
	public boolean isValid(MyCourse course) {
		boolean valid = true;
		ArrayList<MyCourse> list = this.courseTable.get(course.getDayOfWeek() - 1);
		if (!list.isEmpty()) {
			for (int i = 0; i < list.size(); i++) {
				if (list.get(i).getKey() == course.getKey()) {
					valid = false;
					break;
				}
			}
		}
		return valid;
	}
	public MyCourse getCourseByTime(int dayOfWeek,int tableId){
		ArrayList<MyCourse> list = this.courseTable.get(dayOfWeek - 1);
		if (!list.isEmpty())
			for(MyCourse course:list){
				if(course.getTableId()==tableId){
					return course;
				}
			}
		return null;
	}
}
