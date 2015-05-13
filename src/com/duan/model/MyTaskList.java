package com.duan.model;

import java.io.Serializable;
import java.util.ArrayList;

public class MyTaskList implements Serializable {
	private ArrayList<MyTask> taskList;
	public MyTaskList() {
		taskList=new ArrayList<MyTask>();
	}
	public ArrayList<MyTask> getTaskList() {
		return taskList;
	}
	public void setTaskList(ArrayList<MyTask> taskList) {
		this.taskList = taskList;
	}
}