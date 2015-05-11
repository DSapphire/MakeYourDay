package com.duan.util;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import com.duan.model.*;

public class SaveAndRead {

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

	public static MyCourseTable readCourseTable() throws IOException,
			ClassNotFoundException {
		final String file = "res/activity/table.dat";
		ObjectInputStream ois;
		ois = new ObjectInputStream(new FileInputStream(file));
		MyCourseTable table = (MyCourseTable) ois.readObject();
		// System.out.println(table);
		ois.close();
		return table;
	}
	
	public static void saveActivity(ArrayList<MyActivity> activityList) throws FileNotFoundException, IOException{
		final String afile = "res/activity/activity.dat";
		final String bfile= "res/activity/activityhistory.dat";
		final String adir="res/activity";
		File tdir=new File(adir);
		File tfile=new File(afile);
		File hfile=new File(bfile);
		if(!tdir.exists()){
			tdir.mkdirs();
		}
		if(!tfile.exists()){
			tfile.createNewFile();
		}
		if(!hfile.exists()){
			hfile.createNewFile();
		}
		//追加的方式
		ObjectOutputStream oos = new ObjectOutputStream(
				new FileOutputStream(afile));
		ObjectOutputStream oos2 = new ObjectOutputStream(
				new FileOutputStream(bfile,true));
		for(MyActivity activity:activityList){
			if(activity.isFinished())
				oos2.writeObject(activity);
			else
				oos.writeObject(activity);
			oos.flush();
			oos2.flush();
		}
		
		oos.close();
		oos2.close();
		
	}
	
	public static void saveTask(ArrayList<MyTask> taskList) throws FileNotFoundException, IOException{
		final String afile = "res/activity/task.dat";
		final String bfile= "res/activity/taskhistory.dat";
		final String adir="res/activity";
		File tdir=new File(adir);
		File tfile=new File(afile);
		File hfile=new File(bfile);
		if(!tdir.exists()){
			tdir.mkdirs();
		}
		if(!tfile.exists()){
			tfile.createNewFile();
		}
		if(!hfile.exists()){
			hfile.createNewFile();
		}
		ObjectOutputStream oos = new ObjectOutputStream(
				new FileOutputStream(afile));
		ObjectOutputStream oos2 = new ObjectOutputStream(
				new FileOutputStream(bfile,true));
		for(MyTask task:taskList){
			if(task.isFinished())
				oos2.writeObject(task);
			else
				oos.writeObject(task);
			oos.flush();
			oos2.flush();
		}
		
		oos.close();
		oos2.close();
		
	}

	public static void saveRoutine(ArrayList<MyRoutine> routineList) throws FileNotFoundException, IOException{
		final String afile = "res/activity/routine.dat";
		final String bfile= "res/activity/routinehistory.dat";
		final String adir="res/activity";
		File tdir=new File(adir);
		File tfile=new File(afile);
		File hfile=new File(bfile);
		if(!tdir.exists()){
			tdir.mkdirs();
		}
		if(!tfile.exists()){
			tfile.createNewFile();
		}
		if(!hfile.exists()){
			hfile.createNewFile();
		}
		ObjectOutputStream oos = new ObjectOutputStream(
				new FileOutputStream(afile));
		ObjectOutputStream oos2 = new ObjectOutputStream(
				new FileOutputStream(bfile,true));
		for(MyRoutine routine:routineList){
			if(routine.isFinished())
				oos2.writeObject(routine);
			else
				oos.writeObject(routine);
			oos.flush();
			oos2.flush();
		}
		
		oos.close();
		oos2.close();
		
	}

	public static ArrayList<MyActivity> readActivity() throws IOException, Throwable{
		final String file = "res/activity/table.dat";
		ArrayList<MyActivity> list=new ArrayList<MyActivity> ();
		ObjectInputStream ois;
		ois = new ObjectInputStream(
				new FileInputStream(file));
		
		try{
			while(true){
				MyActivity activity = (MyActivity)ois.readObject();
				list.add(activity);
			}
		}catch(EOFException e){
			
		}
		
		ois.close();
		return list;
		
	}

	public static ArrayList<MyTask> readTask() throws IOException, Throwable{
		final String file = "res/activity/task.dat";
		ArrayList<MyTask> list=new ArrayList<MyTask> ();
		ObjectInputStream ois;
		ois = new ObjectInputStream(
				new FileInputStream(file));
		
		try{
			while(true){
				MyTask task = (MyTask)ois.readObject();
				list.add(task);
			}
		}catch(EOFException e){
			
		}
		
		ois.close();
		return list;
		
	}

	public static ArrayList<MyRoutine> readRoutine() throws IOException, Throwable{
		final String file = "res/activity/routine.dat";
		ArrayList<MyRoutine> list=new ArrayList<MyRoutine> ();
		ObjectInputStream ois;
		ois = new ObjectInputStream(
				new FileInputStream(file));
		
		try{
			while(true){
				MyRoutine routine = (MyRoutine)ois.readObject();
				list.add(routine);
			}
		}catch(EOFException e){
			
		}
		
		ois.close();
		return list;
		
	}

	public static void saveDay(ArrayList<MyDay> dayList) throws FileNotFoundException, IOException{
		final String file = "res/activity/day.dat";
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
		for(MyDay day:dayList){
			oos.writeObject(day);
			oos.flush();
		}
		oos.close();
	}
	public static void saveDay(MyDay day) throws FileNotFoundException, IOException{
		final String file= "res/activity/dayhistory.dat";
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
				new FileOutputStream(file,true));
		oos.writeObject(day);
		oos.close();
	}

	public static ArrayList<MyDay> readDay() throws IOException, Throwable{
		final String file = "res/activity/day.dat";
		ArrayList<MyDay> list=new ArrayList<MyDay> ();
		ObjectInputStream ois;
		ois = new ObjectInputStream(
				new FileInputStream(file));
		
		try{
			while(true){
				MyDay day = (MyDay)ois.readObject();
				list.add(day);
			}
		}catch(EOFException e){
			
		}
		
		ois.close();
		return list;
		
	}

}
