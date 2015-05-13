package com.duan.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import com.duan.model.*;

public class SaveAndRead {
	private static boolean checkFile(String file) {
		File tfile = new File(file);
		if (!tfile.exists()) {
			return false;
		}
		return true;
	}
	public static void saveCourseTable(MyCourseTable table) throws IOException {
		final String dir = "res/activity";
		final String file = "res/activity/table.dat";
		File tdir = new File(dir);
		File tfile = new File(file);
		if (!tdir.exists()) {
			tdir.mkdirs();
		}
		if (!tfile.exists()) {
			tfile.createNewFile();
		}
		ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(
				file));
		oos.writeObject(table);
		oos.flush();
		oos.close();
	}
	public static MyCourseTable readCourseTable(){
		final String file = "res/activity/table.dat";
		ObjectInputStream ois;
		MyCourseTable table;
		if(checkFile(file)){
			try {
				ois = new ObjectInputStream(new FileInputStream(file));
				table = (MyCourseTable) ois.readObject();
				ois.close();
				return table;
			} catch (IOException e) {
			}catch (ClassNotFoundException e1) {
			}
			
		}
		return null;
	}
	
	public static void saveClockList(MyClockList clockList) throws IOException {
		final String dir = "res/activity";
		final String file = "res/activity/clockList.dat";
		File tdir = new File(dir);
		File tfile = new File(file);
		if (!tdir.exists()) {
			tdir.mkdirs();
		}
		if (!tfile.exists()) {
			tfile.createNewFile();
		}
		ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(
				file));
		oos.writeObject(clockList);
		oos.flush();
		oos.close();
	}
	public static MyClockList readClockList(){
		final String file = "res/activity/clockList.dat";
		ObjectInputStream ois;
		if(checkFile(file)){
			try{
				ois = new ObjectInputStream(new FileInputStream(file));
				MyClockList clockList = (MyClockList) ois.readObject();
				ois.close();
				return clockList;
			}catch (IOException e) {
			}catch (ClassNotFoundException e1) {
			}
		}
		return null;
	}
	
	public static void saveDayList(MyDayList dayList) throws IOException {
		final String dir = "res/activity";
		final String file = "res/activity/dayList.dat";
		File tdir = new File(dir);
		File tfile = new File(file);
		if (!tdir.exists()) {
			tdir.mkdirs();
		}
		if (!tfile.exists()) {
			tfile.createNewFile();
		}
		ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(
				file));
		oos.writeObject(dayList);
		oos.flush();
		oos.close();
	}
	public static MyDayList readDayList(){
		final String file = "res/activity/dayList.dat";
		ObjectInputStream ois;
		if(checkFile(file)){
			try{
				ois = new ObjectInputStream(new FileInputStream(file));
				MyDayList dayList = (MyDayList) ois.readObject();
				ois.close();
				return dayList;
			}catch (IOException e) {
			}catch (ClassNotFoundException e1) {
			}
		}
		return null;
	}
	
	public static void saveTaskList(MyTaskList taskList) throws FileNotFoundException, IOException{
		final String file = "res/activity/task.dat";
		final String dir="res/activity";
		File tdir=new File(dir);
		File tfile=new File(file);
		if(!tdir.exists()){
			tdir.mkdirs();
		}
		if(!tfile.exists()){
			tfile.createNewFile();
		}
		ObjectOutputStream oos = new ObjectOutputStream(
				new FileOutputStream(file));
		oos.writeObject(taskList);
		oos.flush();
		oos.close();
	}
	public static MyTaskList readTaskList(){
		final String file = "res/activity/task.dat";
		MyTaskList taskList;
		ObjectInputStream ois;
		if(checkFile(file)){
			try{
				ois = new ObjectInputStream(
						new FileInputStream(file));
				taskList=(MyTaskList)ois.readObject();
				ois.close();
			return taskList;
			}catch (IOException e) {
			}catch (ClassNotFoundException e1) {
			}
		}
		return null;
		// try{
		// 	while(true){
		// 		MyTask task = (MyTask)ois.readObject();
		// 		list.add(task);
		// 	}
		// }catch(EOFException e){
			
		// }
	}
	public static void saveRoutineList(MyRoutineList routineList) throws FileNotFoundException, IOException{
		final String file = "res/activity/routine.dat";
		final String adir="res/activity";
		File tdir=new File(adir);
		File tfile=new File(file);
		if(!tdir.exists()){
			tdir.mkdirs();
		}
		if(!tfile.exists()){
			tfile.createNewFile();
		}
		ObjectOutputStream oos = new ObjectOutputStream(
				new FileOutputStream(file));
		oos.writeObject(routineList);
		oos.flush();
		oos.close();
	}
	public static MyRoutineList readRoutineList(){
		final String file = "res/activity/routine.dat";
		MyRoutineList routineList;
		ObjectInputStream ois;
		if(checkFile(file)){
			try{
				ois = new ObjectInputStream(
						new FileInputStream(file));
				routineList = (MyRoutineList)ois.readObject();
				ois.close();
				return routineList;
			}catch (IOException e) {
			}catch (ClassNotFoundException e1) {
			}
		}
		return null;
	}
}