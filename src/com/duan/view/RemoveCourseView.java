package com.duan.view;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import com.duan.model.*;

public class RemoveCourseView extends JDialog implements ActionListener {

	private static final int WIDTH=300;
	private static final int HEIGHT=150;
	private CourseTableView frame;
	private MyCourseTable table;
	private int max=6;
	
	private String[] dayOfWeek,id;
	private JPanel jpForButton,jpForCourse,mainPanel;
	private JLabel title;
	private JButton rmButton,backButton;
	private JComboBox<String> boxForDayOfWeek,boxForId;
	
	public RemoveCourseView(CourseTableView sframe,MyCourseTable table,int max) {
		setTitle("删除课程");
        setModal(true);
		this.table=table;
		this.frame=sframe;
		this.max=max;
	}
	public void removeCourseView(){
		setSize(WIDTH, HEIGHT);
		jpForCourse=new JPanel();
		updateTime();
        boxForDayOfWeek=new JComboBox<String>(dayOfWeek);
        boxForId=new JComboBox<String>(id);
        jpForCourse.add(boxForDayOfWeek);
        jpForCourse.add(boxForId);
		
		rmButton=new JButton("删除");
		rmButton.addActionListener(this);
		backButton=new JButton("返回");
		backButton.addActionListener(this);
		jpForButton=new JPanel();
		jpForButton.add(rmButton);
		jpForButton.add(backButton);
		title=new JLabel("请选择需要删除的课程");
		Font font = new Font(Font.SERIF, Font.PLAIN, 18);
		title.setFont(font);
		title.setHorizontalAlignment(JLabel.CENTER);
		mainPanel=new JPanel(new BorderLayout());
		mainPanel.add(jpForCourse,BorderLayout.CENTER);
		mainPanel.add(jpForButton,BorderLayout.SOUTH);
		mainPanel.add(title,BorderLayout.NORTH);
		setContentPane(mainPanel);
		setLocationRelativeTo(frame);
		setVisible(true);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		if (source == backButton){
			frame.courseTableRepaint();
			dispose();
		}else if(rmButton==source){
			if(rmCourse()){
				frame.courseTableRepaint();
			}else{
				JOptionPane.showMessageDialog(null, "没有对应课程，请重新选择");
			}
		}
	}
	private void updateTime(){
		MyCourse course=new MyCourse();
        dayOfWeek=new String[7];
        id=new String[max];
        for(int i=0;i<dayOfWeek.length;i++){
        	dayOfWeek[i]=course.getStringDayOfWeek(i+1);
        }
        for(int i=0;i<id.length;i++){
        	id[i]="第"+(i+1)+"节";
        }
	}
	private boolean rmCourse(){
		int tableId=boxForId.getSelectedIndex()+1;
		int day=boxForDayOfWeek.getSelectedIndex()+1;
		return table.removeCourse(day, tableId);
	}
}
