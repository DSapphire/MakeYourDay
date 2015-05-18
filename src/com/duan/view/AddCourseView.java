package com.duan.view;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import com.duan.model.*;
public class AddCourseView extends JDialog implements ActionListener{
	private static final int WIDTH=300;
	private static final int HEIGHT=500;
	private CourseTableView frame;
	private MyCourseTable table;
	private int max=6;
	private JPanel jpForButton,jpForCourse,mainPanel;
	private JPanel jpForName,jpForPlace,jpForTeacher,jpForType,jpForBox,jpForWeekType,jpForCtype;
	private JLabel labelForName, labelForPlace,labelForTeacher,title;
    private JTextField textForName, textForPlace,textForTeacher;
    private JRadioButton[] jrForType,jrForWeekType;
    private ButtonGroup bgForType,bgForWeekType;
    private JComboBox<String> boxForDayOfWeek,boxForId;
	private JButton addButton,cancelButton;
	private Font font = new Font(Font.SERIF, Font.PLAIN, 20);
    private String[] dayOfWeek,id;
	public AddCourseView(CourseTableView sframe,MyCourseTable table,int max) {
		super(sframe);
		setTitle("添加课程");
        setModal(true);
        this.frame=sframe;
        this.table=table;
        this.max=max;
	}
	public void addCourseView(){
		setSize(WIDTH, HEIGHT);
		labelForName=new JLabel("课程名：");
        textForName=new JTextField(10);
        jpForName=new JPanel();
        jpForName.add(labelForName);
        jpForName.add(textForName);
        labelForTeacher=new JLabel("任课教师");
        textForTeacher=new JTextField(10);
        jpForTeacher=new JPanel();
        jpForTeacher.add(labelForTeacher);
        jpForTeacher.add(textForTeacher);
        labelForPlace=new JLabel("上课地点");
        textForPlace=new JTextField(10);
        jpForPlace=new JPanel();
        jpForPlace.add(labelForPlace);
        jpForPlace.add(textForPlace);
        jpForType=new JPanel(new GridLayout(2, 1,10, 10));
        jpForWeekType=new JPanel(new GridLayout(2,3,10, 10));
        jpForCtype=new JPanel(new GridLayout(1,3,10, 10));
        jpForType.add(jpForCtype);
        jpForType.add(jpForWeekType);
        jrForType=new JRadioButton[3];
        jrForWeekType=new JRadioButton[5];
        bgForType=new ButtonGroup();
        bgForWeekType=new ButtonGroup();
        jrForType[0]=new JRadioButton("必修",true);
        jrForType[1]=new JRadioButton("限选");
        jrForType[2]=new JRadioButton("任选");
        jrForWeekType[0]=new JRadioButton("全周",true);
        jrForWeekType[1]=new JRadioButton("单周");
        jrForWeekType[2]=new JRadioButton("双周");
        jrForWeekType[3]=new JRadioButton("前八周");
        jrForWeekType[4]=new JRadioButton("后八周");
        for(int i=0;i<jrForType.length;i++){
        	jpForCtype.add(jrForType[i]);
        	bgForType.add(jrForType[i]);
        }
        for(int i=0;i<jrForWeekType.length;i++){
        	jpForWeekType.add(jrForWeekType[i]);
        	bgForWeekType.add(jrForWeekType[i]);
        }
        updateTime();
        boxForDayOfWeek=new JComboBox<String>(dayOfWeek);
        boxForId=new JComboBox<String>(id);
        jpForBox=new JPanel(new GridLayout(1,5,5,3));
        jpForBox.setBorder(BorderFactory.createTitledBorder("上课时间"));
        jpForBox.add(new JLabel(" "));
        jpForBox.add(boxForDayOfWeek);
        jpForBox.add(boxForId);
        jpForBox.add(new JLabel(" "));
        jpForCourse=new JPanel();
        jpForCourse.setLayout(new BoxLayout(jpForCourse, BoxLayout.Y_AXIS));
        jpForCourse.add(jpForName);
        jpForCourse.add(jpForTeacher);
        jpForCourse.add(jpForPlace);
        jpForCourse.add(jpForType);
        jpForCourse.add(jpForBox);
        addButton=new JButton("添加");
        addButton.addActionListener(this);
        cancelButton=new JButton("取消");
        cancelButton.addActionListener(this);
        jpForButton=new JPanel();
        jpForButton.add(addButton);
        jpForButton.add(cancelButton);
        title=new JLabel("添加课程");
        title.setFont(font);
        title.setHorizontalAlignment(JLabel.CENTER);
        mainPanel=new JPanel(new BorderLayout(2,2));
        mainPanel.add(title,BorderLayout.NORTH);
        mainPanel.add(jpForCourse,BorderLayout.CENTER);
        mainPanel.add(jpForButton,BorderLayout.SOUTH);
        setContentPane(mainPanel);
        setLocationRelativeTo(frame);
		setVisible(true);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		if(source==addButton){
			if(checkValid()){
				if(addCourse()){
					this.frame.updateView();//
					dispose();
				}else{
					JOptionPane.showMessageDialog(null, "课程时间冲突！");
				}
			}else{
				JOptionPane.showMessageDialog(null, "请填写必要信息！");
			}
		}else if(source==cancelButton)
			dispose();
	}
	private  boolean addCourse(){
		MyCourse course=getCourseFromView();
		if(table.addCourse(course))
			return true;
		return false;
	}
	private MyCourse getCourseFromView(){
		MyCourse course=new MyCourse();
		course.setName(textForName.getText());
		course.setTeacher(textForPlace.getText());
		course.setPlace(textForPlace.getText());
		for(int i=0;i<jrForType.length;i++){
			if(jrForType[i].isSelected())
				course.setCtype(jrForType[i].getText());
		}
		for(int i=0;i<jrForWeekType.length;i++){
			if(jrForWeekType[i].isSelected())
				course.setWeekType(jrForWeekType[i].getText());
		}
		course.setTableId(boxForId.getSelectedIndex()+1);
		course.setDayOfWeek(boxForDayOfWeek.getSelectedIndex()+1);
		course.updateTime();
		return course;
	}
	private boolean checkValid(){
		if(textForName.getText().equals(""))
			return false;
		if(textForPlace.getText().equals(""))
			return false;
		if(textForTeacher.getText().equals(""))
			return false;
		return true;
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
}