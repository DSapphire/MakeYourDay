package com.duan.view;

import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Vector;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import com.duan.model.*;

public class ActivityView extends JFrame {
	private static final int WIDTH = 400;
	private static final int HEIGHT = 400;
	private MyDayList dayList;
	private Calendar today = Calendar.getInstance();
	private ArrayList<MyDay> list;
	private int days=7;
	private JPanel mainPanel;
	private JScrollPane jspForImpAndUrg, jspForNImpUrg, jspForImpNUrg,
			jspForNImpNUrg;
	private JList<String> listForImpAndUrg, lisForNImpUrg, lisForImpNUrg,
			lisForNImpNUrg;

	public ActivityView(MyDayList dayList,int day) {
		super(day+"天内代办事项");
		this.dayList = dayList;
		this.days=day;
		this.list=new ArrayList<>();
	}

	public void activityView() {
		setSize(WIDTH, HEIGHT);
		listForImpAndUrg = new JList<>(getVectorForList(true, true));
		lisForImpNUrg = new JList<>(getVectorForList(true, false));
		lisForNImpUrg = new JList<>(getVectorForList(false, true));
		lisForNImpNUrg = new JList<>(getVectorForList(false,false));

		jspForImpAndUrg = new JScrollPane(listForImpAndUrg);
		jspForImpAndUrg.setBorder(BorderFactory.createTitledBorder("重要且紧急事项"));
		jspForImpNUrg = new JScrollPane(lisForImpNUrg);
		jspForImpNUrg.setBorder(BorderFactory.createTitledBorder("重要非紧急事项"));
		jspForNImpUrg = new JScrollPane(lisForNImpUrg);
		jspForNImpUrg.setBorder(BorderFactory.createTitledBorder("紧急非重要事项"));
		jspForNImpNUrg = new JScrollPane(lisForNImpNUrg);
		jspForNImpNUrg.setBorder(BorderFactory.createTitledBorder("非紧急非重要事项"));
		mainPanel = new JPanel(new GridLayout(2, 2, 5, 5));
		mainPanel.add(jspForImpAndUrg);
		mainPanel.add(jspForNImpUrg);
		mainPanel.add(jspForImpNUrg);
		mainPanel.add(jspForNImpNUrg);
		setContentPane(mainPanel);
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	private Vector<String> getVectorForList(boolean imp, boolean urg) {
		Vector<String> vector = new Vector<>();
		getList(days);
		for(int i=0;i<list.size();i++){
			ArrayList<MyTask> task=list.get(i).getTaskList();
			ArrayList<MyRoutine> routine=list.get(i).getRoutineList();
			if(!task.isEmpty()){
				for(int j=0;j<task.size();j++){
					MyPriority priority=task.get(j).getPriority();
					if(imp==priority.isImp(3)&&urg==priority.isUrg(3)){
						vector.addElement(task.get(i).toString());
					}
				}
			}
			if(!routine.isEmpty()){
				for(int j=0;j<routine.size();j++){
					MyPriority priority=routine.get(j).getPriority();
					if(imp==priority.isImp(3)&&urg==priority.isUrg(3)){
						vector.addElement(routine.get(i).toString());
					}
				}
			}
		}
		return vector;
	}
	private void getList(int day){
		ArrayList<MyDay> daylist = dayList.getDayList();
		int comp = today.get(Calendar.DAY_OF_YEAR);
		for (int i = 0; i < daylist.size(); i++) {
			Calendar date = daylist.get(i).getDate();
			if (date.get(Calendar.YEAR) - today.get(Calendar.YEAR) < 2) {
				int comp1 = date.get(Calendar.DAY_OF_YEAR)
						+ today.getActualMaximum(Calendar.DAY_OF_YEAR)
						* (date.get(Calendar.YEAR) - today
								.get(Calendar.YEAR));
				if(comp1-comp<day&&comp1>=comp){
					list.add(daylist.get(i));
				}
			}
		}
	}
}