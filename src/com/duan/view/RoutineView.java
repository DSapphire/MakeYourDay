package com.duan.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import com.duan.model.*;

public class RoutineView extends JDialog implements ActionListener,MyView{
	private static final int WIDTH = 330;
	private static final int HEIGHT = 400;
	private MyData data;//数据
	private MyRoutineList list;
	private JScrollPane jspList;
	private JPanel  jpForButton, mainPanel;
	private JList<String> routineJList;
	private JButton addButton, rmButton,backButton;
	public RoutineView(MyData data) {
		setTitle("日常视图");
		setModal(true);
		this.data=data;
		this.list=data.getRoutineList();
	}
	public void loadView(){
		setSize(WIDTH, HEIGHT);
		setLocationRelativeTo(null);
		routineJList=new JList<String>(new RoutineDataModel());
		jspList=new JScrollPane(routineJList);
		jpForButton=new JPanel(new GridLayout(1,3,10,5));
		jpForButton.setPreferredSize(new Dimension(WIDTH, HEIGHT/16));
		addButton=new JButton("添加");
		addButton.addActionListener(this);
		rmButton=new JButton("删除");
		rmButton.addActionListener(this);
		backButton=new JButton("返回");
		backButton.addActionListener(this);
		jpForButton.add(addButton);
		jpForButton.add(rmButton);
		jpForButton.add(backButton);
		mainPanel=new JPanel(new BorderLayout());
		mainPanel.add(jspList,BorderLayout.CENTER);
		mainPanel.add(jpForButton,BorderLayout.SOUTH);
		setContentPane(mainPanel);
		setVisible(true);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		Object source=e.getSource();
		if(source==addButton){
			MyDay theDay=new MyDay();
			AddActivityInDayView addView=new AddActivityInDayView(this,theDay,data);
			addView.loadView();
		}else if(source==rmButton){
			int index=routineJList.getSelectedIndex();
			if(index>=0&&index<list.getRoutineList().size()){
				MyRoutine routine=list.getRoutineList().get(index);//update
				data.removeRoutine(routine);
			}
			updateMyView();
		}else if(source==backButton){
			this.dispose();
		}
	}
	//用于显示日常
	class RoutineDataModel extends AbstractListModel<String> {
		@Override
		public String getElementAt(int index) {
			if(list!=null)
				return (index + 1) + "." + list.getRoutineList().get(index++);
			else
				return null;
		}
		@Override
		public int getSize() {
			if(list!=null)
				 return list.getRoutineList().size();
			else
				return 0;
		}
	}
	@Override
	public void updateMyView() {
		loadView();
	}
}