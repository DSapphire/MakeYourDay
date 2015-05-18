package com.duan.util;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

import com.duan.model.*;
import com.duan.view.ClockRingView;
import com.duan.view.ReminderView;

public class ActivityReminder{
	private ArrayList<MyTask> tList;
	private ArrayList<MyRoutine> rList;
	private ArrayList<MyCourse> cList;
	private MyClockList clock;
	private Timer timer;
	private MyTimeTask task;
	private Calendar today=Calendar.getInstance();
	private int year,month,day;
	private int setAdvance=10;
	public ActivityReminder() {
		timer=new Timer();
		task=new MyTimeTask();
	}
	public void updataData(MyData data){
		year=today.get(Calendar.YEAR);
		month=today.get(Calendar.MONTH)+1;
		day=today.get(Calendar.DAY_OF_MONTH);
		tList=data.getDayList().getDay(year, month, day).getTaskList();
		rList=data.getDayList().getDay(year, month, day).getRoutineList();
		cList=data.getDayList().getDay(year, month, day).getCourseList();
		clock=data.getClockList();
		startReminder();
	}
	private void startReminder(){
		task.stopRunning();
		timer.cancel();
		task=new MyTimeTask();
		timer=new Timer();
		timer.scheduleAtFixedRate(task, 0, 59000L);
	}
	private boolean checkTime(MyTime time){
		today=Calendar.getInstance();
		int hour=today.get(Calendar.HOUR_OF_DAY);
		int min=today.get(Calendar.MINUTE);
		if(hour==time.getHour()&&min==time.getMinute()){
			return true;
		}
		return false;
	}
	private MyClock checkClock(){
		int dayOfWeek=today.get(Calendar.DAY_OF_WEEK);
		if(clock!=null){
			ArrayList<MyClock> list=clock.getClockList();
			if(!list.isEmpty()){
				for(int i=0;i<list.size();i++){
					MyClock myclock=list.get(i);
					if(myclock.isTodayIncluded(dayOfWeek)){
						MyTime time=myclock.getTime();
						if(checkTime(time)){
							return myclock;
						}
					}
				}
			}
		}
		return null;
	}
	private MyTask checkTask(){
		if(tList!=null&&!tList.isEmpty()){
			for(int i=0;i<tList.size();i++){
				MyTime time=tList.get(i).getRemindTime(setAdvance);
				if(checkTime(time))
					return tList.get(i);
			}
		}
		return null;
	}
	private MyCourse checkCourse(){
		if(cList!=null&&!cList.isEmpty()){
			for(int i=0;i<cList.size();i++){
				MyTime time=tList.get(i).getRemindTime(setAdvance);
				if(checkTime(time))
					return cList.get(i);
			}
		}
		return null;
	}
	private MyRoutine checkRoutine(){
		int dayOfWeek=today.get(Calendar.DAY_OF_WEEK);
		if(rList!=null&&!rList.isEmpty()){
			for(int i=0;i<rList.size();i++){
				MyRoutine routine=rList.get(i);
				if(routine.isTodayIncluded(dayOfWeek)){
					MyTime time=routine.getRemindTime(setAdvance);
					if(checkTime(time))
						return routine;
				}
			}
		}
		return null;
	}
	class MyTimeTask extends TimerTask{
		private boolean running;
		public MyTimeTask() {
			this.running=true;
		}
		public void stopRunning(){
			this.running=false;
		}
		@Override
		public void run() {
			if(this.running){
				MyClock clock=checkClock();
				if(clock!=null){
					ClockRingView crv=new ClockRingView(clock);
					crv.view();
				}
				MyTask task=checkTask();
				MyRoutine routine=checkRoutine();
				MyCourse course=checkCourse();
				ReminderView rv=null;
				if(course!=null)
					rv=new ReminderView(course);
				else if(task!=null)
					rv=new ReminderView(task);
				else if(routine!=null)
					rv=new ReminderView(routine);
				if(rv!=null)
					rv.view();
			}
		}
		
	}
}