package com.duan.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import com.duan.model.*;

public class CalendarView extends JFrame implements ActionListener {

	private static final int WIDTH = 500;
	private static final int HEIGHT = 600;
	private MyDayList dayList;
	private Calendar today = Calendar.getInstance();
	private Timer timer;
	private DateFormat df = new SimpleDateFormat("HH : mm : ss    E    M/d/Y");
	private Font font = new Font(Font.SERIF, Font.PLAIN, 20);
	private Calendar cal = Calendar.getInstance();

	private JPanel mainPanel, jpForTime, jpForDay, jpForButton;
	private JLabel labelForTime, labelForMonth;
	private JButton addButton, lookButton, todayButton, preButton, nextButton;
	private JLabel[] labelForWeek;
	private JTextArea[] textForDay;

	public CalendarView(MyDayList dayList) {
		super("������ͼ");
		this.dayList = dayList;
	}

	public void calendarViewRepaint() {
		setSize(WIDTH, HEIGHT);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);

		jpForDay = new JPanel();
		cal = Calendar.getInstance();
		drawDay(cal);
		jpForTime = new JPanel(new BorderLayout());
		labelForTime = new JLabel();
		labelForTime.setFont(font);
		labelForTime.setHorizontalAlignment(JLabel.CENTER);

		todayButton = new JButton("����");
		todayButton.addActionListener(this);
		jpForTime.add(todayButton, BorderLayout.WEST);
		jpForTime.add(labelForTime, BorderLayout.CENTER);

		jpForButton = new JPanel(new GridLayout(1, 5, 4, 0));
		preButton = new JButton("�ϸ���");
		nextButton = new JButton("�¸���");
		preButton.addActionListener(this);
		nextButton.addActionListener(this);
		labelForMonth = new JLabel();
		labelForMonth.setText(cal.get(Calendar.YEAR) + "��"
				+ (cal.get(Calendar.MONTH) + 1) + "��");
		labelForMonth.setFont(font);
		addButton = new JButton("���");
		addButton.addActionListener(this);
		lookButton = new JButton("�鿴");
		lookButton.addActionListener(this);
		labelForMonth.setHorizontalAlignment(JLabel.CENTER);
		jpForButton.add(preButton);
		jpForButton.add(addButton);
		jpForButton.add(labelForMonth);
		jpForButton.add(lookButton);
		jpForButton.add(nextButton);

		mainPanel = new JPanel(new BorderLayout());
		mainPanel.add(jpForDay, BorderLayout.CENTER);
		mainPanel.add(jpForTime, BorderLayout.NORTH);
		mainPanel.add(jpForButton, BorderLayout.SOUTH);
		setContentPane(mainPanel);
		initTimer();
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		if (source == addButton) {
			AddDayView addView = new AddDayView(this);
			addView.addDayView();
		} else if (source == lookButton) {
			AddDayView addView = new AddDayView(this);
			addView.addDayView();
		} else if (source == todayButton) {
			cal = Calendar.getInstance();
			labelForMonth.setText(cal.get(Calendar.YEAR) + "��"
					+ (cal.get(Calendar.MONTH) + 1) + "��");
			drawDayView(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH) + 1,
					cal.get(Calendar.DAY_OF_MONTH));
		} else if (source == preButton) {
			cal.add(Calendar.MONTH, -1);
			labelForMonth.setText(cal.get(Calendar.YEAR) + "��"
					+ (cal.get(Calendar.MONTH) + 1) + "��");
			drawDay(cal);
		} else if (source == nextButton) {
			cal.add(Calendar.MONTH, 1);
			labelForMonth.setText(cal.get(Calendar.YEAR) + "��"
					+ (cal.get(Calendar.MONTH) + 1) + "��");
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
		int month = cal.get(Calendar.MONTH);
		int year = cal.get(Calendar.YEAR);

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
			jpForDay.add(new JTextArea(weeks + ""));
		}
		textForDay = new JTextArea[days];
		for (int i = 0; i < days; i++) {
			textForDay[i] = new JTextArea("" + (i + 1));
			textForDay[i].setEditable(false);
			textForDay[i].setLineWrap(true);//
			drawTextDay(year, month, i);
			textForDay[i]
					.setBorder(BorderFactory.createLineBorder(Color.BLACK));
			jpForDay.add(textForDay[i]);
		}
	}

	// /
	private void drawTextDay(int year, int month, int i) {
		MyDay day = this.dayList.getDay(year, month, i + 1);
		if (day != null) {
			int task = day.getTaskList().size();
			int course = day.getCourseList().size();
			int routine = day.getRoutineList().size();
			textForDay[i].append("\r\nTask:" + task);
			textForDay[i].append("\r\nCourse:" + course);
			textForDay[i].append("\r\nCourse:" + routine);
		}
		if (i == today.get(Calendar.DAY_OF_MONTH) - 1
				&& month == today.get(Calendar.MONTH)
				&& year == today.get(Calendar.YEAR)) {
			textForDay[i].setBackground(Color.RED);
		}
	}

	public void drawDayView(int year, int month, int day) {
		DayView dayView = new DayView(this, dayList);
		dayView.dayView(year, month, day);
	}
}
