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

import com.duan.model.MyDay;
import com.duan.model.MyDayList;
import com.duan.model.MyPriority;
import com.duan.model.MyRoutine;
import com.duan.model.MyTask;
import com.duan.model.MyTime;

public class AddActivityInDayView extends JDialog implements ActionListener {
	private static final int WIDTH = 320;
	private static final int HEIGHT = 500;
	private MyDayList dayList;
	private MyDay theDay;
	private DayView frame;
	
	private int year,month,day;
	
	private JPanel mainPanel,jpForDate,jpForButton,jpForRButton,
					jpForPriority,jpForDDL,jpForAdd,jpForTimeS,jpForTimeE;
	private JLabel labelForDay,labelForMonth,labelForYear;
	private JComboBox<String> textImp,textUrg;
	private JTextField textDay, textMonth,textYear,ddlDay, ddlMonth,ddlYear,
						textName,textPlace,textHourS,textMinS,textHourE,textMinE;
	private JButton okButton,cancelButton;
	
	private JRadioButton[] jr={new JRadioButton("����",true),
			new JRadioButton("��һ"),
    		new JRadioButton("�ܶ�"),new JRadioButton("����"),
    		new JRadioButton("����"),new JRadioButton("����"),
    		new JRadioButton("����"),new JRadioButton("����"),
    };
	
	public AddActivityInDayView(DayView frame, MyDayList dayList) {
		setModal(true);
		setTitle("�������");
		this.dayList = dayList;
		this.frame = frame;
	}
	public void addView(){
		setSize(WIDTH, HEIGHT);
		theDay=frame.getTheDay();
		
		jpForDate=new JPanel();
		jpForDate.setBorder(BorderFactory.createTitledBorder("��������"));
		textDay=new JTextField(4);
		textDay.setText(""+theDay.getDate().get(Calendar.DAY_OF_MONTH));
		textMonth=new JTextField(2);
		textMonth.setText(""+(theDay.getDate().get(Calendar.MONTH)+1));
		textYear=new JTextField(4);
		textYear.setText(""+theDay.getDate().get(Calendar.YEAR));
		labelForYear=new JLabel("��");
		labelForMonth=new JLabel("��");
		labelForDay=new JLabel("��");
		jpForDate.add(textYear);
		jpForDate.add(labelForYear);
		jpForDate.add(textMonth);
		jpForDate.add(labelForMonth);
		jpForDate.add(textDay);
		jpForDate.add(labelForDay);
		
		jpForAdd=new JPanel(new GridLayout(3,2));
		textName=new JTextField();
		textName.setBorder(BorderFactory.createTitledBorder("��������"));
		textPlace=new JTextField();
		textPlace.setBorder(BorderFactory.createTitledBorder("�ص�"));
		jpForTimeS=new JPanel();
		jpForTimeS.setBorder(BorderFactory.createTitledBorder("��ʼʱ��"));
		textHourS=new JTextField(2);
		textHourS.setText("12");
		textMinS=new JTextField(2);
		textMinS.setText("00");
		jpForTimeS.add(textHourS);
		jpForTimeS.add(new JLabel(" : "));
		jpForTimeS.add(textMinS);
		jpForTimeE=new JPanel();
		jpForTimeE.setBorder(BorderFactory.createTitledBorder("����ʱ��"));
		textHourE=new JTextField(2);
		textHourE.setText("13");
		textMinE=new JTextField(2);
		textMinE.setText("00");
		jpForTimeE.add(textHourE);
		jpForTimeE.add(new JLabel(" : "));
		jpForTimeE.add(textMinE);
		jpForPriority=new JPanel(new GridLayout(1,2));
		String[] proirity={"1","2","3","4","5"};
		textImp=new JComboBox<String>(proirity);
		textImp.setBorder(BorderFactory.createTitledBorder("��Ҫ�ȼ�"));
		textUrg=new JComboBox<String>(proirity);
		textUrg.setBorder(BorderFactory.createTitledBorder("�����̶�"));
		jpForPriority.add(textImp);
		jpForPriority.add(textUrg);
		jpForAdd.add(textName);
		jpForAdd.add(jpForTimeS);
		jpForAdd.add(textPlace);
		jpForAdd.add(jpForTimeE);
		jpForAdd.add(new JLabel());
		jpForAdd.add(jpForPriority);

		jpForDDL=new JPanel();
		jpForDDL.setBorder(BorderFactory.createTitledBorder("��ֹ����"));
		ddlDay=new JTextField(2);
		ddlMonth=new JTextField(2);
		ddlYear=new JTextField(4);
		ddlDay.setText(""+theDay.getDate().get(Calendar.DAY_OF_MONTH));
		ddlMonth.setText(""+(theDay.getDate().get(Calendar.MONTH)+1));
		ddlYear.setText(""+theDay.getDate().get(Calendar.YEAR));
		jpForDDL.add(ddlYear);
		jpForDDL.add(new JLabel("��"));
		jpForDDL.add(ddlMonth);
		jpForDDL.add(new JLabel("��"));
		jpForDDL.add(ddlDay);
		jpForDDL.add(new JLabel("��"));
		
		jpForRButton=new JPanel(new GridLayout(2,4));
		for(int i=0;i<jr.length;i++){
			jpForRButton.add(jr[i]);
		}
		jpForButton=new JPanel();
		okButton=new JButton("���");
		okButton.addActionListener(this);
		cancelButton=new JButton("ȡ��");
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
	public void actionPerformed(ActionEvent e) {
		Object source=e.getSource();
		if(source==cancelButton){
			this.dispose();
		}else if(source==okButton){
			if(checkValid()){
				try{
					getTheDay();
					int type=getTypeForRoutine();
					if(0==type){
						MyTask task=getTask();
						task.setStartTime(getTime(0));
						task.setEndTime(getTime(1));
						theDay.getTaskList().add(task);
					}else{
						MyRoutine routine=getRoutine();
						routine.setType(type);
						routine.setStartTime(getTime(0));
						routine.setEndTime(getTime(1));
						theDay.getRoutineList().add(routine);
					}
					dayList.getDayList().add(theDay);
					frame.dayView(year, month, day);
					this.dispose();
				}catch(Exception e1){
					JOptionPane.showMessageDialog(null,"������������");
				}
			}else{
				JOptionPane.showMessageDialog(null, "�������Ʊ��");
			}
		}
	}
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
	private boolean checkValid(){
		if(textName.getText().equals(""))
			return false;
		return true;
	}
	private MyTask getTask(){
		MyTask task=new MyTask();
		task.setName(textName.getText());
		task.setPlace(textPlace.getText());
		task.setPriority(getPriority());
		task.setDeadLine(getDDL());
		return task;
	}
	private MyRoutine getRoutine(){
		MyRoutine routine=new MyRoutine();
		routine.setName(textName.getText());
		routine.setPlace(textPlace.getText());
		routine.setPriority(getPriority());
		return routine;
	}
	private void getTheDay() throws NumberFormatException{
		year=Integer.parseInt(textYear.getText());
		month=Integer.parseInt(textMonth.getText());
		day=Integer.parseInt(textDay.getText());
		theDay=dayList.getDay(year, month, day);
		if(theDay==null){
			theDay=new MyDay();
			theDay.getDate().set(year, month-1, day);
		}
		theDay.getDate().set(year, month-1, day);
	}
	private MyPriority getPriority(){
		MyPriority priority=new MyPriority();
		priority.setImportance(Integer.parseInt((String)textImp.getSelectedItem()));
		priority.setUrgency(Integer.parseInt((String)textUrg.getSelectedItem()));
		return priority;
	}
	private Calendar getDDL(){
		Calendar deadLine=Calendar.getInstance();
		try{
			deadLine.set(Integer.parseInt(ddlYear.getText()),
					Integer.parseInt(ddlMonth.getText()) - 1,
					Integer.parseInt(ddlDay.getText()));
		}catch(Exception e1) {
        	JOptionPane.showMessageDialog(null,"��ֹ������������Ĭ��������������ͬ��");
        	deadLine.set(year, month, day);
        }
		return deadLine;	
	}
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
