package com.duan.view;
import java.awt.GridLayout;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import com.duan.model.*;
public class ActivityView extends JDialog implements MyView{
	private static final int WIDTH = 400;
	private static final int HEIGHT = 400;
	private MyDayList dayList;//������������
	private Calendar today = Calendar.getInstance();
	private ArrayList<MyDay> list;//��Ҫ��ʾ��Щ����ճ�
	private int days=7;//Ĭ����ʾ7���ڵ�����
	private JPanel mainPanel;
	private JScrollPane jspForImpAndUrg, jspForNImpUrg, jspForImpNUrg,
			jspForNImpNUrg;
	private JList<String> listForImpAndUrg, lisForNImpUrg, lisForImpNUrg,
			lisForNImpNUrg;
	public ActivityView(MyDayList dayList,int day) {
		setTitle(day+"���ڴ�������");
		setModal(true);
		this.dayList = dayList;
		this.days=day;
		this.list=new ArrayList<>();
	}
	public void loadView() {
		setSize(WIDTH, HEIGHT);
		getList(days);
		listForImpAndUrg = new JList<>(getVectorForList(true, true));
		lisForImpNUrg = new JList<>(getVectorForList(true, false));
		lisForNImpUrg = new JList<>(getVectorForList(false, true));
		lisForNImpNUrg = new JList<>(getVectorForList(false,false));
		jspForImpAndUrg = new JScrollPane(listForImpAndUrg);
		jspForImpAndUrg.setBorder(BorderFactory.createTitledBorder("��Ҫ�ҽ�������"));
		jspForImpNUrg = new JScrollPane(lisForImpNUrg);
		jspForImpNUrg.setBorder(BorderFactory.createTitledBorder("��Ҫ�ǽ�������"));
		jspForNImpUrg = new JScrollPane(lisForNImpUrg);
		jspForNImpUrg.setBorder(BorderFactory.createTitledBorder("��������Ҫ����"));
		jspForNImpNUrg = new JScrollPane(lisForNImpNUrg);
		jspForNImpNUrg.setBorder(BorderFactory.createTitledBorder("�ǽ�������Ҫ����"));
		mainPanel = new JPanel(new GridLayout(2, 2, 5, 5));
		mainPanel.add(jspForImpAndUrg);
		mainPanel.add(jspForNImpUrg);
		mainPanel.add(jspForImpNUrg);
		mainPanel.add(jspForNImpNUrg);
		setContentPane(mainPanel);
		setLocationRelativeTo(null);
		addWindowListener(new MyViewAdapter(this));
		setVisible(true);
	}
	//���������б���ʾ��Ԫ�ؼ���,�����б���ʾ
	private Vector<String> getVectorForList(boolean imp, boolean urg) {
		Vector<String> vector = new Vector<>();
		DateFormat df = new SimpleDateFormat("Y/M/d ");
		int k=0;
		for(int i=0;i<list.size();i++){
			ArrayList<MyTask> task=list.get(i).getTaskList();
			ArrayList<MyRoutine> routine=list.get(i).getRoutineList();
			String s=df.format(list.get(i).getDate().getTime())+":";
			if(task!=null&&!task.isEmpty()){
				for(int j=0;j<task.size();j++){
					MyPriority priority=task.get(j).getPriority();
					if(imp==priority.isImp(3)&&urg==priority.isUrg(3)){
						vector.addElement((k+1)+"."+s+task.get(j).toString());
						k++;
					}
				}
			}
			if(routine!=null&&!routine.isEmpty()){
				for(int j=0;j<routine.size();j++){
					MyPriority priority=routine.get(j).getPriority();
					if(imp==priority.isImp(3)&&urg==priority.isUrg(3)){
						vector.addElement((k+1)+"."+s+routine.get(j).toString());
						k++;
					}
				}
			}
		}
		return vector;
	}
	//��dayList�л�ȡ���Ĭ��������MyDay��list
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
	@Override
	public void updateMyView() {
		loadView();
	}
	@Override
	public void closingView() {
		this.dispose();
	}
}