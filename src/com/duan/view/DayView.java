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

public class DayView extends JDialog implements ActionListener {
	private static final int WIDTH = 400;
	private static final int HEIGHT = 500;
	private MyDayList dayList;
	private MyDay theDay;
	private CalendarView frame;

	private JPanel mainPanel, jpForTime, jpForButton, jpForList;
	private JScrollPane jspForCourse, jspForTaskAndRoutine;
	private JList<String> listCourse, listTaskAndRoutine;
	private JButton backButton, addButton, rmButton;
	private JLabel labelForTime;

	public DayView(CalendarView frame, MyDayList dayList) {
		setModal(true);
		setTitle("日视图");
		this.dayList = dayList;
		this.frame = frame;
	}

	public void dayView(int year, int month, int day) {
		setSize(WIDTH, HEIGHT);
		getDay(year, month, day);
		jpForTime = new JPanel();
		labelForTime = new JLabel();
		labelForTime.setText(year + " 年 " + month + " 月 " + day + " 日");
		labelForTime.setHorizontalAlignment(JLabel.CENTER);
		labelForTime.setFont(new Font(Font.SERIF, Font.PLAIN, 18));
		jpForTime.add(labelForTime);

		jpForList = new JPanel();
		jpForList.setLayout(new GridLayout(2, 1));
		listCourse = new JList<String>(new CourseDataModel());//
		listCourse.setBorder(BorderFactory.createTitledBorder("今日课程"));
		listTaskAndRoutine = new JList<String>(new TaskAndRoutineDataModel());//
		listTaskAndRoutine.setBorder(BorderFactory.createTitledBorder("今日事项"));
		jspForCourse = new JScrollPane(listCourse);
		jspForTaskAndRoutine = new JScrollPane(listTaskAndRoutine);
		jpForList.add(jspForCourse);
		jpForList.add(jspForTaskAndRoutine);

		jpForButton = new JPanel(new GridLayout(1, 3, 3, 3));
		backButton = new JButton("返回");
		backButton.addActionListener(this);
		addButton = new JButton("添加任务");
		addButton.addActionListener(this);
		rmButton = new JButton("删除任务");
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
			frame.calendarViewRepaint();
			dispose();
		} else if (source == addButton) {
			AddActivityInDayView addView=new AddActivityInDayView(this,dayList);
			addView.addView();
		} else if (source == rmButton) {
			int index=listCourse.getSelectedIndex();
			if(index>0&&index<theDay.getCourseList().size()){
				JOptionPane.showMessageDialog(null, "删除课程需要前往课程表删除！");
			}
			index = listTaskAndRoutine.getSelectedIndex();
			int max = theDay.getTaskList().size()
					+ theDay.getRoutineList().size();
			if (index >= 0 && index < max) {
				int op = JOptionPane.showConfirmDialog(null, "确定要删除事项中的第"
						+ (index + 1) + "项？", "确认", JOptionPane.YES_NO_OPTION,
						JOptionPane.WARNING_MESSAGE);
				if(op==JOptionPane.YES_OPTION){
					int mid = theDay.getTaskList().size();
					if (index < mid){
						theDay.getTaskList().remove(index);
					}else{
						theDay.getRoutineList().remove(index - mid);
					}
					dayView(theDay.getDate().get(Calendar.YEAR),
							theDay.getDate().get(Calendar.MONTH)+1,
							theDay.getDate().get(Calendar.DAY_OF_MONTH));
				}
			}
		}
	}

	private MyDay getDay(int year, int month, int day) {
		theDay = dayList.getDay(year, month, day);
		if (theDay == null) {
			theDay = new MyDay();
			theDay.getDate().set(year, month-1, day);
			dayList.getDayList().add(theDay);
		}
		return theDay;
	}

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
	public MyDay getTheDay(){
		return this.theDay;
	}
}
