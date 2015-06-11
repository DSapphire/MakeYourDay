package com.duan.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import com.duan.model.*;
public class CalendarView extends JDialog implements ActionListener,MyView{
	private static final int WIDTH = 550;
	private static final int HEIGHT = 600;
	private MyData data;//数据
	private MyDayList dayList;
	private Calendar today = Calendar.getInstance();
	private Timer timer;//用于更新时间显示
	private ClickOnDate clickOnDate;//updated for straight click on date area to show dayview
	private int year,month;//update
	private DateFormat df = new SimpleDateFormat("HH : mm : ss  Y/M/d  E");
	private Font font = new Font(Font.SERIF, Font.PLAIN, 20);
	private Calendar cal = Calendar.getInstance();
	private JPanel mainPanel, jpForTime, jpForDay, jpForButton;
	private JLabel labelForTime, labelForMonth;
	private JButton todayButton, preButton, nextButton;
	private JLabel[] labelForWeek;
	private JTextArea[] textForDay;
	public CalendarView(MyData data) {
		setTitle("日历视图");
		setModal(true);
		this.data=data;
		this.dayList=data.getDayList();
		clickOnDate=new ClickOnDate();
	}
	public void loadView() {
		setSize(WIDTH, HEIGHT);
		setLocationRelativeTo(null);
		jpForDay = new JPanel();
		updateMyView();
		jpForTime = new JPanel(new BorderLayout());
		labelForTime = new JLabel();
		labelForTime.setFont(font);
		labelForTime.setHorizontalAlignment(JLabel.CENTER);
		todayButton = new JButton("今日");
		todayButton.addActionListener(this);
		jpForTime.add(todayButton, BorderLayout.WEST);
		jpForTime.add(labelForTime, BorderLayout.CENTER);
		jpForButton = new JPanel(new GridLayout(1, 5, 4, 0));
		preButton = new JButton("上个月");
		nextButton = new JButton("下个月");
		preButton.addActionListener(this);
		nextButton.addActionListener(this);
		labelForMonth = new JLabel();
		labelForMonth.setText(cal.get(Calendar.YEAR) + "年"
				+ (cal.get(Calendar.MONTH) + 1) + "月");
		labelForMonth.setFont(font);
		labelForMonth.setHorizontalAlignment(JLabel.CENTER);
		jpForButton.add(preButton);
		jpForButton.add(new JLabel());
		jpForButton.add(labelForMonth);
		jpForButton.add(new JLabel());
		jpForButton.add(nextButton);
		mainPanel = new JPanel(new BorderLayout());
		mainPanel.add(jpForDay, BorderLayout.CENTER);
		mainPanel.add(jpForTime, BorderLayout.NORTH);
		mainPanel.add(jpForButton, BorderLayout.SOUTH);
		setContentPane(mainPanel);
		initTimer();
		addWindowListener(new MyViewAdapter(this));
		setVisible(true);
	}
	public void updateMyView(){
		cal = Calendar.getInstance();
		drawDay(cal);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		if (source == todayButton) {
			cal = Calendar.getInstance();
			labelForMonth.setText(cal.get(Calendar.YEAR) + "年"
					+ (cal.get(Calendar.MONTH) + 1) + "月");
			drawDayView(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH) + 1,
					cal.get(Calendar.DAY_OF_MONTH));
		} else if (source == preButton) {
			cal.add(Calendar.MONTH, -1);
			labelForMonth.setText(cal.get(Calendar.YEAR) + "年"
					+ (cal.get(Calendar.MONTH) + 1) + "月");
			drawDay(cal);
		} else if (source == nextButton) {
			cal.add(Calendar.MONTH, 1);
			labelForMonth.setText(cal.get(Calendar.YEAR) + "年"
					+ (cal.get(Calendar.MONTH) + 1) + "月");
			drawDay(cal);
		}
	}
	private void initTimer() {
		timer = new Timer();
		timer.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				Calendar cal = Calendar.getInstance();
				labelForTime.setText(df.format(cal.getTime()));
			}
		}, 0, 1000L);
	}
	private void drawDay(Calendar cal) {
		jpForDay.removeAll();
		cal.set(Calendar.DAY_OF_MONTH, 1);
		int days = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		int first = cal.get(Calendar.DAY_OF_WEEK);
		int weeks = (days + first - 1 + 6) / 7;// +6
		month = cal.get(Calendar.MONTH)+1;
		year = cal.get(Calendar.YEAR);
		jpForDay.setLayout(new GridLayout(weeks + 1, 7, 2, 2));
		jpForDay.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		labelForWeek = new JLabel[7];
		MyCourse course = new MyCourse();
		for (int i = 0; i < 7; i++) {
			labelForWeek[i] = new JLabel(course.getStringDayOfWeek(i + 1));
			labelForWeek[i].setForeground(Color.WHITE);
			labelForWeek[i].setOpaque(true);
			labelForWeek[i].setBackground(Color.GRAY);
			labelForWeek[i].setFont(font);
			labelForWeek[i].setHorizontalAlignment(JLabel.CENTER);
			jpForDay.add(labelForWeek[i]);
		}
		for (int i = 0; i < first - 1; i++) {
			jpForDay.add(new JLabel());
		}
		textForDay = new JTextArea[days];
		for (int i = 0; i < days; i++) {
			textForDay[i] = new JTextArea("" + (i + 1));
			textForDay[i].setEditable(false);
			textForDay[i].setLineWrap(true);//
			drawTextDay(year, month, i);
			textForDay[i]
					.setBorder(BorderFactory.createLineBorder(Color.BLACK));
			textForDay[i].addMouseListener(clickOnDate);//update
			jpForDay.add(textForDay[i]);
		}
		jpForDay.revalidate();
	}
	private void drawTextDay(int year, int month, int i) {
		MyDay day = this.dayList.getDay(year, month, i + 1);
		if(day==null){
			day=new MyDay();
			day.getDate().set(year, month-1, i+1);
			this.dayList.getDayList().add(day);
		}
		day.addRoutine(data.getRoutineList());
		day.addCourse(data.getTable());
		int task = day.getTaskList().size();
		int course = day.getCourseList().size();
		int routine = day.getRoutineList().size();
		if(course>0)
			textForDay[i].append("\r\nCourse:" + course);
		if(task>0)
			textForDay[i].append("\r\nTask:" + task);
		if(routine>0)
			textForDay[i].append("\r\nRoutine:" + routine);
		if (i == today.get(Calendar.DAY_OF_MONTH) - 1
				&& month-1 == today.get(Calendar.MONTH)
				&& year == today.get(Calendar.YEAR)) {
			textForDay[i].setBackground(Color.RED);
		}
	}
	private void drawDayView(int year, int month, int day) {
		MyDay theDay=data.getDayList().getDay(year, month, day);
		MyView dayView = new DayView(this,theDay,data);
		dayView.loadView();
	}
	class ClickOnDate extends MouseAdapter{
		public ClickOnDate(){
			super();
		}
		public void mousePressed(MouseEvent e){
			Object o=e.getSource();
			for(int i=0;i<textForDay.length;i++){
				if(o==textForDay[i]){
					drawDayView(year,month,i+1);
					break;
				}
			}
		}
	}
	@Override
	public void closingView() {
		this.dispose();
	}
}