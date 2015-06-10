package com.duan.util;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Observable;
import java.util.Observer;
import java.util.Timer;
import java.util.TimerTask;

import com.duan.model.*;
import com.duan.view.ClockRingView;
import com.duan.view.ReminderView;
//观察者模式
public class ActivityReminder implements Observer{
	
	private ArrayList<MyTask> tList;
	private ArrayList<MyRoutine> rList;
	private ArrayList<MyCourse> cList;
	private MyClockList clock;//闹钟
	private Timer timer;
	private MyTimeTask task;//自定的任务线程，用于检查时间是否到了提醒用户
	private Calendar today=Calendar.getInstance();
	private int year,month,day;//年月日
	private int setAdvance=10;//默认的提前时间是10分钟
	private RingClock ring;//闹钟播放
	public ActivityReminder() {
		timer=new Timer();
		task=new MyTimeTask();
		ring=new RingClock();
	}
	//当data变化时，提醒的数据需要更新
	public void updateData(MyData data){
		year=today.get(Calendar.YEAR);
		month=today.get(Calendar.MONTH)+1;
		day=today.get(Calendar.DAY_OF_MONTH);
		MyDay myday=data.getDayList().getDay(year, month, day);
		if(myday!=null){
			tList=myday.getTaskList();
			rList=myday.getRoutineList();
			cList=myday.getCourseList();
			clock=data.getClockList();
			startReminder();
		}
	}
	//开始检查是否需要提醒
	private void startReminder(){
		task.stopRunning();//舍弃之前的
		timer.cancel();
		task=new MyTimeTask();//新建一个
		timer=new Timer();
		timer.scheduleAtFixedRate(task, 0, 60000L);//60 seconds检查一次
	}
	//检查时间是否到
	private boolean checkTime(MyTime time){
		today=Calendar.getInstance();
		int hour=today.get(Calendar.HOUR_OF_DAY);
		int min=today.get(Calendar.MINUTE);
		if(hour==time.getHour()&&min==time.getMinute()){
			return true;
		}
		return false;
	}
	//检查闹钟
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
					}else{
						if(myclock.getType()==0&&myclock.isRing()){
							myclock.setRing(false);
							return myclock;//单次的闹钟
						}
					}
				}
			}
		}
		return null;
	}
	//检查任务
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
	//检查课程
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
	//检查日常
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
	//自定的检查任务线程
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
					ring.startRing();
					ClockRingView crv=new ClockRingView(clock,ring);//
					crv.view();
				}
				MyTask task=checkTask();
				MyRoutine routine=checkRoutine();
				MyCourse course=checkCourse();
				ReminderView rv=null;
				if(course!=null){
					rv=new ReminderView(course,ring);
				}else if(task!=null){
					rv=new ReminderView(task,ring);
				}else if(routine!=null){
					rv=new ReminderView(routine,ring);
				}
				if(rv!=null){
					ring.startRing();
					rv.view();
				}
			}
		}
		
	}
	//当data变化时自动调用update
	@Override
	public void update(Observable o, Object arg) {
		if(o instanceof MyData){
			MyData data=(MyData)o;
			updateData(data);
		}
	}
}