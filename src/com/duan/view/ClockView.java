package com.duan.view;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

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
public class ClockView extends JDialog implements ActionListener,MyView {
	private static final int WIDTH = 300;
	private static final int HEIGHT = 400;
	private JScrollPane jspForClockList;
	private JPanel jpForTime, jpForButton, mainPanel;
	private JList<String> clockJList;
	private JLabel labelForTime;
	private JButton addClockButton, rmClockButton;
	private Timer timer;
	private DateFormat df = new SimpleDateFormat("HH : mm : ss  Y/M/d  E");
	private MyClockList list;//闹钟列表
	public ClockView(MyClockList list) {
		setModal(true);
		setTitle("闹钟");
		this.list = list;
	}
	public void setList(MyClockList list) {
		this.list = list;
	}
	public void loadView() {
		setSize(WIDTH, HEIGHT);
		setLocationRelativeTo(null);
		jpForTime = new JPanel();
		clockJList = new JList<String>(new ClockDataModel());
		jspForClockList = new JScrollPane(clockJList);
		jspForClockList.setSize(WIDTH, HEIGHT * 3 / 4);
		jspForClockList.setBorder(BorderFactory.createTitledBorder("闹钟列表"));
		labelForTime = new JLabel();
		labelForTime.setSize(WIDTH, HEIGHT / 8);
		Font font = new Font(Font.SERIF, Font.PLAIN, 20);
		labelForTime.setFont(font);
		labelForTime.setHorizontalAlignment(JLabel.CENTER);
		jpForTime.setLayout(new BorderLayout());
		jpForTime.add(labelForTime, BorderLayout.CENTER);
		jpForTime.setSize(WIDTH, HEIGHT / 8);
		addClockButton = new JButton("添加闹钟");
		addClockButton.setFont(font);
		addClockButton.setHorizontalAlignment(JLabel.CENTER);
		addClockButton.setSize(WIDTH, HEIGHT / 8);
		addClockButton.addActionListener(this);
		rmClockButton = new JButton("删除闹钟");
		rmClockButton.setFont(font);
		rmClockButton.setHorizontalAlignment(JLabel.CENTER);
		rmClockButton.setSize(WIDTH, HEIGHT / 8);
		rmClockButton.addActionListener(this);
		jpForButton = new JPanel();
		jpForButton.add(addClockButton);
		jpForButton.add(rmClockButton);
		mainPanel = new JPanel(new BorderLayout(2, 2));
		mainPanel.add(jpForButton, BorderLayout.SOUTH);
		mainPanel.add(jspForClockList, BorderLayout.CENTER);
		mainPanel.add(jpForTime, BorderLayout.NORTH);
		setContentPane(mainPanel);
		addWindowListener(new MyViewAdapter(this));
		setVisible(true);
		initTimer();
	}
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		if (source == addClockButton) {
			AddClockView addView = new AddClockView(this, list);
		} else if (source == rmClockButton) {
			int index = clockJList.getSelectedIndex();
			if (index >=0 && index < list.getClockList().size()){
				int op=JOptionPane.showConfirmDialog(null, "确认删除所选闹钟？", "确认",
						JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
				if(op==JOptionPane.YES_OPTION){
					list.getClockList().remove(index);//
					this.updateMyView();//
				}
			}
		}
	}
	//更新时间
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
	//用于列表显示闹钟列表
	class ClockDataModel extends AbstractListModel<String> {
		@Override
		public String getElementAt(int index) {
			return (index + 1) + "." + list.getClockList().get(index++);
		}

		@Override
		public int getSize() {
			return list.getClockList().size();
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