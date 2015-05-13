package com.duan.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.AbstractListModel;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import com.duan.model.*;

public class RoutineView extends JFrame implements ActionListener{
	private static final int WIDTH = 300;
	private static final int HEIGHT = 400;
	private MyDayList dayList;
	private ArrayList<MyRoutine> list;
	
	private JScrollPane jspList;
	private JPanel  jpForButton, mainPanel;
	private JList<String> routineJList;
	private JButton addButton, rmButton,backButton;
	
	
	public RoutineView(MyDayList dayList) {
		super("日常视图");
		this.dayList=dayList;
	}
	public void routineView(){
		setSize(WIDTH, HEIGHT);
		setLocationRelativeTo(null);
		loadData();
		
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
			AddDayView addView=new AddDayView(dayList);
			addView.addDayView();
		}else if(source==rmButton){
			int index=routineJList.getSelectedIndex();
			if(index>=0&&index<list.size()){
				list.remove(index);
			}
			routineView();
		}else if(source==backButton){
			this.dispose();
		}
	}
	private boolean loadData(){
		return false;
	}
	class RoutineDataModel extends AbstractListModel<String> {
		@Override
		public String getElementAt(int index) {
			if(list!=null)
				return (index + 1) + "." + list.get(index++);
			else
				return null;
		}

		@Override
		public int getSize() {
			if(list!=null)
				 return list.size();
			else
				return 0;
		}
	}
}
