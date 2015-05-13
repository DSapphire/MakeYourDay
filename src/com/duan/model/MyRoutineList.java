package com.duan.model;

import java.io.Serializable;
import java.util.ArrayList;

public class MyRoutineList implements Serializable {
	private ArrayList<MyRoutine> routineList;
	public MyRoutineList() {
		routineList=new ArrayList<MyRoutine>();
	}
	public ArrayList<MyRoutine> getRoutineList() {
		return routineList;
	}
	public void setRoutineList(ArrayList<MyRoutine> routineList) {
		this.routineList = routineList;
	}
}