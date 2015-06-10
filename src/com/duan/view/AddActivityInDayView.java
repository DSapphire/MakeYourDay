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
	private MyData data;//����
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
	private JRadioButton[] jr={new JRadioButton("����",true),
			new JRadioButton("����"),new JRadioButton("��һ"),
    		new JRadioButton("�ܶ�"),new JRadioButton("����"),
    		new JRadioButton("����"),new JRadioButton("����"),
    		new JRadioButton("����"),
    };
	public AddActivityInDayView(MyView frame, MyDay theDay,MyData data) {
		setModal(true);
		setTitle("�������");
		this.data=data;
		this.dayList = data.getDayList();
		this.frame = frame;
		this.theDay=theDay;
	}
	public void loadView(){
		setSize(WIDTH, HEIGHT);
		jpForDate=new JPanel();
		jpForDate.setBorder(BorderFactory.createTitledBorder("��������"));
		textDay=new JTextField(4);
		textMonth=new JTextField(2);
		textYear=new JTextField(4);
		textDay.setText(""+theDay.getDate().get(Calendar.DAY_OF_MONTH));
		textMonth.setText(""+(theDay.getDate().get(Calendar.MONTH)+1));
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
		textHourS.setText("8");
		textMinS=new JTextField(2);
		textMinS.setText("00");
		jpForTimeS.add(textHourS);
		jpForTimeS.add(new JLabel(" : "));
		jpForTimeS.add(textMinS);
		jpForTimeE=new JPanel();
		jpForTimeE.setBorder(BorderFactory.createTitledBorder("����ʱ��"));
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
		ddlDay.setText("");
		ddlMonth.setText("");
		ddlYear.setText("");
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
	public void actionPerformed(ActionEvent e) {///
		Object source=e.getSource();
		if(source==cancelButton){
			this.dispose();
		}else if(source==okButton){
			if(checkValid()){//��Ч�����
				try{
					boolean ifGet=getTheDay();//������д��ȡ����
					int type=getTypeForRoutine();//��ȡ�ճ�
					boolean added=false;
					if(0==type){
						MyTask task=getTask();//˵����������ӵ�������
						task.setStartTime(getTime(0));
						task.setEndTime(getTime(1));
						added=data.addTask(theDay, task);
					}else{
						MyRoutine routine=getRoutine();//��������ճ�
						routine.setType(type);
						routine.setStartTime(getTime(0));
						routine.setEndTime(getTime(1));
						added=data.addRoutine(theDay, routine);
					}
					if(!ifGet){
						dayList.getDayList().add(theDay);//���û�еõ����ڣ����½���������ӵ�dayList����
					}
					if(added){
						if(frame!=null)
							frame.updateMyView();//��Ӻ��˷���
						this.dispose();
					}else{
						JOptionPane.showMessageDialog(null, "ʱ���ͻ����Ҫ������");
					}
				}catch(Exception e1){
					JOptionPane.showMessageDialog(null,"������������");
				}
			}else{
				JOptionPane.showMessageDialog(null, "�������Ʊ��");
			}
		}
	}
	//��ȡ�ճ�����
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
	//����Ƿ���д
	private boolean checkValid(){
		if(textName.getText().equals(""))
			return false;
		return true;
	}
	//��ȡ��д������
	private MyTask getTask(){
		MyTask task=new MyTask();
		task.setName(textName.getText());
		task.setPlace(textPlace.getText());
		task.setPriority(getPriority());
		task.setDeadLine(getDDL());
		return task;
	}
	//��ȡ�ճ�
	private MyRoutine getRoutine(){
		MyRoutine routine=new MyRoutine();
		routine.setName(textName.getText());
		routine.setPlace(textPlace.getText());
		routine.setPriority(getPriority());
		return routine;
	}
	//��ȡ����
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
	//��ȡ���ȼ�
	private MyPriority getPriority(){
		MyPriority priority=new MyPriority();
		priority.setImportance(Integer.parseInt((String)textImp.getSelectedItem()));
		priority.setUrgency(Integer.parseInt((String)textUrg.getSelectedItem()));
		return priority;
	}
	//��ȡdeadline
	private Calendar getDDL(){
		Calendar deadLine=Calendar.getInstance();
		try{
			deadLine.set(Integer.parseInt(ddlYear.getText()),
					Integer.parseInt(ddlMonth.getText()) - 1,
					Integer.parseInt(ddlDay.getText()));
		}catch(Exception e1) {
        	JOptionPane.showMessageDialog(null,"��ֹ������������Ĭ��������������ͬ��");
        	deadLine.set(year, month-1, day);
        }
		return deadLine;	
	}
	//��ȡʱ�䣬��ʼʱ�䣬����ʱ��
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