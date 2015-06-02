package com.duan.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.Image;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.AbstractListModel;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import com.duan.model.*;
import com.duan.util.ActivityOptimizer;
import com.duan.util.ActivityReminder;

public class MainView extends JFrame implements ActionListener {

	private static final int WIDTH = 328;
	private static final int HEIGHT = 300;
	private MyData data;//
	private ActivityReminder reminder;
	private MyDay theDay;
	private Calendar today = Calendar.getInstance();
	private Timer timer;
	private DateFormat df = new SimpleDateFormat("HH : mm : ss  Y/M/d  E");
	private Font font = new Font(Font.SERIF, Font.PLAIN, 20);
	private JPanel mainPanel, jpForTime, jpForButton;
	private JLabel labelForTime;
	private JButton calVButton, actVButton, clockVButton, tableVButton,
			taskVButton, routineVButton;
	public MainView() {
		super("主界面");
		loadData();
	}
	public void loadView() {
		setSize(WIDTH, HEIGHT);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		jpForTime = new JPanel();
		labelForTime = new JLabel();
		labelForTime.setFont(font);
		labelForTime.setHorizontalAlignment(JLabel.CENTER);
		jpForTime.add(labelForTime);
		jpForTime.setPreferredSize(new Dimension(WIDTH, HEIGHT / 8));
		Image image=new ImageIcon("res/1.gif").getImage();
		ImageViewer iV=new ImageViewer(image);
		iV.setPreferredSize(new Dimension(328,185));
		jpForButton = new JPanel(new GridLayout(2, 3, 10, 5));
		calVButton = new JButton("日历视图");
		calVButton.addActionListener(this);
		actVButton = new JButton("最近事项");
		actVButton.addActionListener(this);
		clockVButton = new JButton("闹钟");
		clockVButton.addActionListener(this);
		tableVButton = new JButton("课程表");
		tableVButton.addActionListener(this);
		taskVButton = new JButton("任务");
		taskVButton.addActionListener(this);
		routineVButton = new JButton("日常");
		routineVButton.addActionListener(this);
		jpForButton.add(calVButton);
		jpForButton.add(clockVButton);
		jpForButton.add(tableVButton);
		jpForButton.add(actVButton);
		jpForButton.add(taskVButton);
		jpForButton.add(routineVButton);
		jpForButton.setPreferredSize(new Dimension(WIDTH, HEIGHT /6));
		mainPanel = new JPanel(new BorderLayout(4, 4));
		mainPanel.add(jpForTime, BorderLayout.NORTH);
		mainPanel.add(iV, BorderLayout.CENTER);
		mainPanel.add(jpForButton, BorderLayout.SOUTH);
		setContentPane(mainPanel);
		setLocationRelativeTo(null);
		setVisible(true);
		initTimer();
		this.addWindowListener(new MainViewClosing(this));
	}
	private void loadData() {
		data = new MyData();
		reminder=new ActivityReminder();
		data.addObserver(reminder);
		data.readData();
		ActivityOptimizer ao=new ActivityOptimizer();//
		if(ao.log()){
			ao.optimize(data.getTaskList());
			reminder.updataData(data);//日志
		}
		int year=today.get(Calendar.YEAR),
				month=today.get(Calendar.MONTH) + 1,
				day=today.get(Calendar.DAY_OF_MONTH);
		MyDayList dayList = data.getDayList();
		if (dayList != null)
			theDay = dayList.getDay(year, month, day);
		else
			dayList = new MyDayList();
		if (theDay == null) {
			theDay = new MyDay();
			theDay.getDate().set(year, month - 1, day);
			dayList.getDayList().add(theDay);
		}
	}
	private void saveData() {
		if (!data.saveData()) {
			JOptionPane.showMessageDialog(null, "Data Save Erro!", "Erro",
					JOptionPane.WARNING_MESSAGE);
		}
	}
	public void closingView() {
		saveData();
		JOptionPane.showMessageDialog(this, "GoodBye!", "Quit",
				JOptionPane.INFORMATION_MESSAGE);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		if (source == calVButton) {
			CalendarView cView = new CalendarView(data);
			cView.loadView();
		} else if (source == tableVButton) {
			MyCourseTable table=data.getTable();
			if(table==null){
				table=new MyCourseTable();
				data.setTable(table);
			}
			CourseTableView tView = new CourseTableView(this,table);//
			tView.courseTableRepaint();
		} else if (source == clockVButton) {
			ClockView cView = new ClockView(data.getClockList());
			cView.loadView();
		} else if (source == actVButton) {
			ActivityView aView = new ActivityView(data.getDayList(), 7);
			aView.activityView();
		} else if (source == taskVButton) {
			TaskView taskView = new TaskView(data);
			taskView.taskView();
		} else if (source == routineVButton) {
			RoutineView rView = new RoutineView(data);
			rView.routineView();
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
	class MainViewClosing extends WindowAdapter {
		MainView view;
		public MainViewClosing(MainView view) {
			this.view = view;
		}
		@Override
		public void windowClosing(WindowEvent e) {
			view.closingView();
			System.exit(0);
		}
	}
	class ImageViewer extends JPanel{
		private Image image;
		private int xCoordinate;
		private int yCoordinate;
		public ImageViewer(){
		}
		public ImageViewer(Image image){
			this.image=image;
		}
		public void setXCoordinate(int xCoordinate){
			this.xCoordinate=xCoordinate;
			repaint();
		}
		public void setYCoordinate(int yCoordinate){
			this.yCoordinate=yCoordinate;
			repaint();
		}
		protected void paintComponent(Graphics g){
			super.paintComponent(g);
			if(image!=null){
				g.drawImage(image,xCoordinate,yCoordinate,getSize().width,getSize().height,this);
			}
		}
	}
}	
