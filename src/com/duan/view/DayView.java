package com.duan.view;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import javax.swing.AbstractListModel;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import com.duan.model.*;

public class DayView extends JDialog implements ActionListener,MyView{
	private static final int WIDTH = 300;
	private static final int HEIGHT = 400;
	private MyData data;//����
	private MyDay theDay=null;//����
	private MyView frame;//
	private JPanel mainPanel, jpForTime, jpForButton, jpForList;
	private JScrollPane jspForCourse, jspForTaskAndRoutine;
	private JList<String> listCourse, listTaskAndRoutine;
	private JButton backButton, addButton, rmButton;
	private JLabel labelForTime;
	public DayView(MyView frame,MyDay theDay,MyData data) {
		setModal(true);
		setTitle("����ͼ");
		this.data=data;
		this.frame = frame;
		this.theDay=theDay;
	}
	public void updateMyView(){
		loadView();
	}
	public void loadView(){
		setSize(WIDTH, HEIGHT);
		if(theDay==null){
			theDay=new MyDay();
			data.getDayList().getDayList().add(theDay);
		}
		dayView(theDay.getDate().get(Calendar.YEAR),
				theDay.getDate().get(Calendar.MONTH)+1,
				theDay.getDate().get(Calendar.DAY_OF_MONTH));
	}
	private void dayView(int year, int month, int day) {
		if(mainPanel!=null)
			mainPanel.removeAll();
		jpForTime = new JPanel();
		labelForTime = new JLabel();
		labelForTime.setText(year + " �� " + month + " �� " + day + " ��");
		labelForTime.setHorizontalAlignment(JLabel.CENTER);
		labelForTime.setFont(new Font(Font.SERIF, Font.PLAIN, 18));
		jpForTime.add(labelForTime);
		jpForList = new JPanel();
		jpForList.setLayout(new GridLayout(2, 1));
		listCourse = new JList<String>(new CourseDataModel());//
		listCourse.setBorder(BorderFactory.createTitledBorder("���տγ�"));
		listTaskAndRoutine = new JList<String>(new TaskAndRoutineDataModel());//
		listTaskAndRoutine.setBorder(BorderFactory.createTitledBorder("��������"));
		jspForCourse = new JScrollPane(listCourse);
		jspForTaskAndRoutine = new JScrollPane(listTaskAndRoutine);
		jpForList.add(jspForCourse);
		jpForList.add(jspForTaskAndRoutine);
		jpForButton = new JPanel(new GridLayout(1, 3, 3, 3));
		backButton = new JButton("����");
		backButton.addActionListener(this);
		addButton = new JButton("�������");
		addButton.addActionListener(this);
		rmButton = new JButton("ɾ������");
		rmButton.addActionListener(this);
		jpForButton.add(backButton);
		jpForButton.add(addButton);
		jpForButton.add(rmButton);
		mainPanel = new JPanel(new BorderLayout());
		mainPanel.add(jpForButton, BorderLayout.SOUTH);
		mainPanel.add(jpForList, BorderLayout.CENTER);
		mainPanel.add(jpForTime, BorderLayout.NORTH);
		setContentPane(mainPanel);
		setLocationRelativeTo(null);
		setPreferredSize(getPreferredSize());
		setVisible(true);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		if (source == backButton) {
			if(frame!=null)
				frame.updateMyView();//
			dispose();
		} else if (source == addButton) {
			AddActivityInDayView addView=new AddActivityInDayView(this,this.theDay,data);
			addView.loadView();
		} else if (source == rmButton) {
			int index=listCourse.getSelectedIndex();
			if(index>0&&index<theDay.getCourseList().size()){
				JOptionPane.showMessageDialog(null, "ɾ���γ���Ҫǰ���γ̱�ɾ����");
			}
			index = listTaskAndRoutine.getSelectedIndex();
			int max = theDay.getTaskList().size()
					+ theDay.getRoutineList().size();
			if (index >= 0 && index < max) {
				int op = JOptionPane.showConfirmDialog(null, "ȷ��Ҫɾ�������еĵ�"
						+ (index + 1) + "�", "ȷ��", JOptionPane.YES_NO_OPTION,
						JOptionPane.WARNING_MESSAGE);
				if(op==JOptionPane.YES_OPTION){
					int mid = theDay.getTaskList().size();
					if (index < mid){
						MyTask task=theDay.getTaskList().get(index);
						data.removeTask(theDay, task);
						//theDay.removeTask(task);
					}else{
						MyRoutine routine=theDay.getRoutineList().get(index-mid);
						data.removeRoutine(theDay, routine);
						//theDay.removeRoutine(routine);
					}
					dayView(theDay.getDate().get(Calendar.YEAR),
							theDay.getDate().get(Calendar.MONTH)+1,
							theDay.getDate().get(Calendar.DAY_OF_MONTH));
				}
			}
		}
	}
	//������ʾ�γ���Ϣ
	class CourseDataModel extends AbstractListModel<String> {
		@Override
		public String getElementAt(int index) {
			return (index + 1) + "."
					+ theDay.getCourseList().get(index++).toPlainText();
		}
		@Override
		public int getSize() {
			return theDay.getCourseList().size();
		}
	}
	//������ʾ������ճ�
	class TaskAndRoutineDataModel extends AbstractListModel<String> {
		@Override
		public String getElementAt(int index) {
			int mid = theDay.getTaskList().size();
			if (index < mid)
				return (index + 1) + "." + theDay.getTaskList().get(index++);
			else
				return (index + 1) + "."
						+ theDay.getRoutineList().get((index++) - mid);
		}
		@Override
		public int getSize() {
			return theDay.getTaskList().size() + theDay.getRoutineList().size();
		}
	}
}