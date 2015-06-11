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

public class TaskView extends JDialog implements ActionListener,MyView{
	private static final int WIDTH = 330;
	private static final int HEIGHT = 400;
	private MyData data;
	private MyTaskList list;
	private JScrollPane jspList;
	private JPanel  jpForButton, mainPanel;
	private JList<String> taskJList;
	private JButton addButton, rmButton,backButton;
	public TaskView(MyData data) {
		setTitle("任务视图");
		setModal(true);
		this.data=data;
		this.list=data.getTaskList();
	}
	public void loadView(){
		setSize(WIDTH, HEIGHT);
		setLocationRelativeTo(null);
		taskJList=new JList<String>(new TaskDataModel());
		jspList=new JScrollPane(taskJList);
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
		addWindowListener(new MyViewAdapter(this));
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
			int index=taskJList.getSelectedIndex();
			if(index>=0&&index<list.getTaskList().size()){
				MyTask task=list.getTaskList().get(index);
				data.removeTask(task);
			}
			updateMyView();
		}else if(source==backButton){
			this.dispose();
		}
	}
	//用于显示任务
	class TaskDataModel extends AbstractListModel<String> {
		@Override
		public String getElementAt(int index) {
			if(list!=null)
				return (index + 1) + "." + list.getTaskList().get(index++);
			else
				return null;
		}
		@Override
		public int getSize() {
			if(list!=null)
				 return list.getTaskList().size();
			else
				return 0;
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