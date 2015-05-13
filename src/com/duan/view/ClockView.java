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
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import com.duan.model.*;

public class ClockView extends JFrame implements ActionListener {

	private static final int WIDTH = 300;
	private static final int HEIGHT = 400;
	private JScrollPane jspForClockList;
	private JPanel jpForTime, jpForButton, mainPanel;
	private JList<String> clockJList;
	private JLabel labelForTime;
	private JButton addClockButton, rmClockButton;
	private Timer timer;
	private DateFormat df = new SimpleDateFormat("HH : mm : ss  Y/M/d  E");
	private MyClockList list;

	public ClockView(MyClockList list) {
		super("ƒ÷÷”");
		this.list = list;
	}
	public void setList(MyClockList list) {
		this.list = list;
	}

	public void clockViewRepaint() {
		setSize(WIDTH, HEIGHT);
		setLocationRelativeTo(null);
		
		jpForTime = new JPanel();
		ClockDataModel model = new ClockDataModel();
		clockJList = new JList<String>(model);
		clockJList.setBorder(BorderFactory.createTitledBorder("ƒ÷÷”¡–±Ì"));
		jspForClockList = new JScrollPane(clockJList);
		jspForClockList.setSize(WIDTH, HEIGHT * 3 / 4);

		labelForTime = new JLabel();
		labelForTime.setSize(WIDTH, HEIGHT / 8);
		Font font = new Font(Font.SERIF, Font.PLAIN, 20);
		labelForTime.setFont(font);
		labelForTime.setHorizontalAlignment(JLabel.CENTER);
		jpForTime.setLayout(new BorderLayout());
		jpForTime.add(labelForTime, BorderLayout.CENTER);
		jpForTime.setSize(WIDTH, HEIGHT / 8);

		addClockButton = new JButton("ÃÌº”ƒ÷÷”");
		addClockButton.setFont(font);
		addClockButton.setHorizontalAlignment(JLabel.CENTER);
		addClockButton.setSize(WIDTH, HEIGHT / 8);
		addClockButton.addActionListener(this);
		rmClockButton = new JButton("…æ≥˝ƒ÷÷”");
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
				int op=JOptionPane.showConfirmDialog(null, "»∑»œ…æ≥˝À˘—°ƒ÷÷”£ø", "»∑»œ",
						JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
				if(op==JOptionPane.YES_OPTION){
					list.getClockList().remove(index);//
					this.clockViewRepaint();
				}
			}
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
}
