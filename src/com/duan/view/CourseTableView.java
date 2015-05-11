package com.duan.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import com.duan.model.*;

public class CourseTableView extends JFrame implements ActionListener {
	private static final int WIDTH = 1000;
	private static final int HEIGHT = 500;
	private MyCourseTable table;
	private int max = 6;

	private JPanel jpForCourse, mainPanel, jpForTitle, jpForButton, jpForTable,
			jpForId;
	private JLabel title;
	private JTextArea[][] buttonForCourse;
	private JButton addButton, rmButton;
	private JLabel[] buttonForDayOfWeek, buttonForId;

	public CourseTableView(MyCourseTable table) {
		super("课程表");
		this.table = table;
	}

	public void courseTableRepaint() {
		setSize(WIDTH, HEIGHT);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);

		jpForCourse = new JPanel(new GridLayout(7, 7));
		jpForId = new JPanel(new GridLayout(7, 1));
		buttonForDayOfWeek = new JLabel[8];
		MyCourse course = new MyCourse();
		for (int i = 1; i < 8; i++) {
			buttonForDayOfWeek[i] = new JLabel(course.getStringDayOfWeek(i));
			buttonForDayOfWeek[i].setBorder(BorderFactory
					.createLineBorder(Color.BLACK));
			buttonForDayOfWeek[i].setHorizontalAlignment(JLabel.CENTER);
			jpForCourse.add(buttonForDayOfWeek[i]);
		}
		buttonForDayOfWeek[0] = new JLabel();
		jpForId.add(buttonForDayOfWeek[0]);
		buttonForDayOfWeek[0].setBorder(BorderFactory
				.createLineBorder(Color.BLACK));
		buttonForId = new JLabel[max];
		for (int i = 0; i < buttonForId.length; i++) {
			course.setTableId(i + 1);
			course.updateTime();
			String time = "第" + (i + 1) + "节课\n\n" + course.getStartTime()
					+ "-" + course.getEndTime();
			buttonForId[i] = new JLabel(time);
			buttonForId[i].setBorder(BorderFactory
					.createLineBorder(Color.BLACK));
			jpForId.add(buttonForId[i]);
		}
		buttonForCourse = new JTextArea[6][7];
		for (int i = 0; i < 6; i++) {
			for (int j = 0; j < 7; j++) {
				course = table.getCourseByTime(j + 1, i + 1);
				if (course != null) {
					buttonForCourse[i][j] = new JTextArea(course.toPlainText());
				} else {
					buttonForCourse[i][j] = new JTextArea();
				}
				buttonForCourse[i][j].setBorder(BorderFactory
						.createLineBorder(Color.BLACK));
				buttonForCourse[i][j].setLineWrap(true);
				buttonForCourse[i][j].setEditable(false);
				jpForCourse.add(buttonForCourse[i][j]);
			}
		}
		jpForTable = new JPanel(new BorderLayout());
		jpForTable.add(jpForCourse, BorderLayout.CENTER);
		jpForTable.add(jpForId, BorderLayout.WEST);

		addButton = new JButton("添加课程");
		rmButton = new JButton("删除课程");
		jpForButton = new JPanel();
		jpForButton.add(addButton);
		jpForButton.add(rmButton);
		addButton.addActionListener(this);
		rmButton.addActionListener(this);
		title = new JLabel("课程表");
		Font font = new Font(Font.SERIF, Font.PLAIN, 24);
		title.setFont(font);
		title.setHorizontalAlignment(JLabel.CENTER);
		jpForTitle = new JPanel();
		jpForTitle.add(title);

		mainPanel = new JPanel(new BorderLayout(2, 2));
		mainPanel.add(jpForButton, BorderLayout.SOUTH);
		mainPanel.add(jpForTable, BorderLayout.CENTER);
		mainPanel.add(jpForTitle, BorderLayout.NORTH);
		setContentPane(mainPanel);
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		if (source == addButton) {
			AddCourseView addView = new AddCourseView(this, table, max);
			addView.addCourseView();
		} else if (source == rmButton) {
			RemoveCourseView rmView=new RemoveCourseView(this, table,max);
			rmView.removeCourseView();
		}
	}

}
