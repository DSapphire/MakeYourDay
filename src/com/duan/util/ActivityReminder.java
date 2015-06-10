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
//�۲���ģʽ
public class ActivityReminder implements Observer{
	
	private ArrayList<MyTask> tList;
	private ArrayList<MyRoutine> rList;
	private ArrayList<MyCourse> cList;
	private MyClockList clock;//����
	private Timer timer;
	private MyTimeTask task;//�Զ��������̣߳����ڼ��ʱ���Ƿ��������û�
	private Calendar today=Calendar.getInstance();
	private int year,month,day;//������
	private int setAdvance=10;//Ĭ�ϵ���ǰʱ����10����
	private RingClock ring;//���Ӳ���
	public ActivityReminder() {
		timer=new Timer();
		task=new MyTimeTask();
		ring=new RingClock();
	}
	//��data�仯ʱ�����ѵ�������Ҫ����
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
	//��ʼ����Ƿ���Ҫ����
	private void startReminder(){
		task.stopRunning();//����֮ǰ��
		timer.cancel();
		task=new MyTimeTask();//�½�һ��
		timer=new Timer();
		timer.scheduleAtFixedRate(task, 0, 60000L);//60 seconds���һ��
	}
	//���ʱ���Ƿ�
	private boolean checkTime(MyTime time){
		today=Calendar.getInstance();
		int hour=today.get(Calendar.HOUR_OF_DAY);
		int min=today.get(Calendar.MINUTE);
		if(hour==time.getHour()&&min==time.getMinute()){
			return true;
		}
		return false;
	}
	//�������
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
							return myclock;//���ε�����
						}
					}
				}
			}
		}
		return null;
	}
	//�������
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
	//���γ�
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
	//����ճ�
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
	//�Զ��ļ�������߳�
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
	//��data�仯ʱ�Զ�����update
	@Override
	public void update(Observable o, Object arg) {
		if(o instanceof MyData){
			MyData data=(MyData)o;
			updateData(data);
		}
	}
}