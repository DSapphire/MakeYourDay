package com.duan.view;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import com.duan.model.*;

public class AddActivityInDayView extends JDialog implements ActionListener {
	private static final int WIDTH = 320;
	private static final int HEIGHT = 500;
	private MyData data;//数据
	private MyDayList dayList;
	private MyDay theDay=null;
	private MyView frame=null;
	private int year,month,day;
	private JPanel mainPanel,jpForDate,jpForButton,jpForRButton,
					jpForPriority,jpForDDL,jpForAdd,jpForTimeS,jpForTimeE;
	private JLabel labelForDay,labelForMonth,labelForYear;
	private JComboBox<String> textImp,textUrg;
	private JTextField textDay, textMonth,textYear,ddlDay, ddlMonth,ddlYear,
						textName,textPlace,textHourS,textMinS,textHourE,textMinE;
	private JButton okButton,cancelButton;
	private JRadioButton[] jr={new JRadioButton("单次",true),
			new JRadioButton("周日"),new JRadioButton("周一"),
    		new JRadioButton("周二"),new JRadioButton("周三"),
    		new JRadioButton("周四"),new JRadioButton("周五"),
    		new JRadioButton("周六"),
    };
	public AddActivityInDayView(MyView frame, MyDay theDay,MyData data) {
		setModal(true);
		setTitle("添加事项");
		this.data=data;
		this.dayList = data.getDayList();
		this.frame = frame;
		this.theDay=theDay;
	}
	public void loadView(){
		setSize(WIDTH, HEIGHT);
		jpForDate=new JPanel();
		jpForDate.setBorder(BorderFactory.createTitledBorder("日期设置"));
		textDay=new JTextField(4);
		textMonth=new JTextField(2);
		textYear=new JTextField(4);
		textDay.setText(""+theDay.getDate().get(Calendar.DAY_OF_MONTH));
		textMonth.setText(""+(theDay.getDate().get(Calendar.MONTH)+1));
		textYear.setText(""+theDay.getDate().get(Calendar.YEAR));
		labelForYear=new JLabel("年");
		labelForMonth=new JLabel("月");
		labelForDay=new JLabel("日");
		jpForDate.add(textYear);
		jpForDate.add(labelForYear);
		jpForDate.add(textMonth);
		jpForDate.add(labelForMonth);
		jpForDate.add(textDay);
		jpForDate.add(labelForDay);
		jpForAdd=new JPanel(new GridLayout(3,2));
		textName=new JTextField();
		textName.setBorder(BorderFactory.createTitledBorder("事项名称"));
		textPlace=new JTextField();
		textPlace.setBorder(BorderFactory.createTitledBorder("地点"));
		jpForTimeS=new JPanel();
		jpForTimeS.setBorder(BorderFactory.createTitledBorder("开始时间"));
		textHourS=new JTextField(2);
		textHourS.setText("8");
		textMinS=new JTextField(2);
		textMinS.setText("00");
		jpForTimeS.add(textHourS);
		jpForTimeS.add(new JLabel(" : "));
		jpForTimeS.add(textMinS);
		jpForTimeE=new JPanel();
		jpForTimeE.setBorder(BorderFactory.createTitledBorder("结束时间"));
		textHourE=new JTextField(2);
		textHourE.setText("10");
		textMinE=new JTextField(2);
		textMinE.setText("00");
		jpForTimeE.add(textHourE);
		jpForTimeE.add(new JLabel(" : "));
		jpForTimeE.add(textMinE);
		jpForPriority=new JPanel(new GridLayout(1,2));
		String[] proirity={"1","2","3","4","5"};
		textImp=new JComboBox<String>(proirity);
		textImp.setBorder(BorderFactory.createTitledBorder("重要等级"));
		textUrg=new JComboBox<String>(proirity);
		textUrg.setBorder(BorderFactory.createTitledBorder("紧急程度"));
		jpForPriority.add(textImp);
		jpForPriority.add(textUrg);
		jpForAdd.add(textName);
		jpForAdd.add(jpForTimeS);
		jpForAdd.add(textPlace);
		jpForAdd.add(jpForTimeE);
		jpForAdd.add(new JLabel());
		jpForAdd.add(jpForPriority);
		jpForDDL=new JPanel();
		jpForDDL.setBorder(BorderFactory.createTitledBorder("截止日期"));
		ddlDay=new JTextField(2);
		ddlMonth=new JTextField(2);
		ddlYear=new JTextField(4);
		ddlDay.setText("");
		ddlMonth.setText("");
		ddlYear.setText("");
		jpForDDL.add(ddlYear);
		jpForDDL.add(new JLabel("年"));
		jpForDDL.add(ddlMonth);
		jpForDDL.add(new JLabel("月"));
		jpForDDL.add(ddlDay);
		jpForDDL.add(new JLabel("日"));
		jpForRButton=new JPanel(new GridLayout(2,4));
		for(int i=0;i<jr.length;i++){
			jpForRButton.add(jr[i]);
		}
		jpForButton=new JPanel();
		okButton=new JButton("添加");
		okButton.addActionListener(this);
		cancelButton=new JButton("取消");
		cancelButton.addActionListener(this);
		jpForButton.add(okButton);
		jpForButton.add(cancelButton);
		mainPanel=new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel,BoxLayout.Y_AXIS));
		mainPanel.add(jpForDate);
		mainPanel.add(jpForAdd);
		mainPanel.add(jpForDDL);
		mainPanel.add(jpForRButton);
		mainPanel.add(jpForButton);
		setContentPane(mainPanel);
		setLocationRelativeTo(null);
		setVisible(true);
	}
	@Override
	public void actionPerformed(ActionEvent e) {///
		Object source=e.getSource();
		if(source==cancelButton){
			this.dispose();
		}else if(source==okButton){
			if(checkValid()){//有效则继续
				try{
					boolean ifGet=getTheDay();//根据填写获取日期
					int type=getTypeForRoutine();//获取日常
					boolean added=false;
					if(0==type){
						MyTask task=getTask();//说明是任务，添加到任务当中
						task.setStartTime(getTime(0));
						task.setEndTime(getTime(1));
						added=data.addTask(theDay, task);
					}else{
						MyRoutine routine=getRoutine();//否则添加日常
						routine.setType(type);
						routine.setStartTime(getTime(0));
						routine.setEndTime(getTime(1));
						added=data.addRoutine(theDay, routine);
					}
					if(!ifGet){
						dayList.getDayList().add(theDay);//如果没有得到日期，把新建的日期添加到dayList当中
					}
					if(added){
						if(frame!=null)
							frame.updateMyView();//添加好了返回
						this.dispose();
					}else{
						JOptionPane.showMessageDialog(null, "时间冲突，需要调整！");
					}
				}catch(Exception e1){
					JOptionPane.showMessageDialog(null,"输入有误！请检查");
				}
			}else{
				JOptionPane.showMessageDialog(null, "事项名称必填！");
			}
		}
	}
	//获取日常类型
	private int getTypeForRoutine(){
		int type=0;
		int cnt=1;
		for(int i=1;i<jr.length;i++){
			if(jr[i].isSelected())
				type+=cnt;
			cnt*=2;
		}
		return type;
	}
	//检查是否填写
	private boolean checkValid(){
		if(textName.getText().equals(""))
			return false;
		return true;
	}
	//获取填写的任务
	private MyTask getTask(){
		MyTask task=new MyTask();
		task.setName(textName.getText());
		task.setPlace(textPlace.getText());
		task.setPriority(getPriority());
		task.setDeadLine(getDDL());
		return task;
	}
	//获取日常
	private MyRoutine getRoutine(){
		MyRoutine routine=new MyRoutine();
		routine.setName(textName.getText());
		routine.setPlace(textPlace.getText());
		routine.setPriority(getPriority());
		return routine;
	}
	//获取日期
	private boolean getTheDay() throws NumberFormatException{
		year=Integer.parseInt(textYear.getText());
		month=Integer.parseInt(textMonth.getText());
		day=Integer.parseInt(textDay.getText());
		theDay=null;
		theDay=dayList.getDay(year, month, day);
		if(theDay==null){
			theDay=new MyDay();
			theDay.getDate().set(year, month-1, day);
			return false;
		}
		theDay.getDate().set(year, month-1, day);
		return true;
	}
	//获取优先级
	private MyPriority getPriority(){
		MyPriority priority=new MyPriority();
		priority.setImportance(Integer.parseInt((String)textImp.getSelectedItem()));
		priority.setUrgency(Integer.parseInt((String)textUrg.getSelectedItem()));
		return priority;
	}
	//获取deadline
	private Calendar getDDL(){
		Calendar deadLine=Calendar.getInstance();
		try{
			deadLine.set(Integer.parseInt(ddlYear.getText()),
					Integer.parseInt(ddlMonth.getText()) - 1,
					Integer.parseInt(ddlDay.getText()));
		}catch(Exception e1) {
        	JOptionPane.showMessageDialog(null,"截止日期输入有误！默认与日期设置相同！");
        	deadLine.set(year, month-1, day);
        }
		return deadLine;	
	}
	//获取时间，开始时间，结束时间
	private MyTime getTime(int i) throws Exception{
		if(i==0){
			return new MyTime(Integer.parseInt(textHourS.getText()),
					Integer.parseInt(textMinS.getText()));
		}else{
			return new MyTime(Integer.parseInt(textHourE.getText()),
					Integer.parseInt(textMinE.getText()));
		}
	}
}