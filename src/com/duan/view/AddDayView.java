package com.duan.view;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Calendar;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import com.duan.model.*;
public class AddDayView extends JDialog implements KeyListener,ActionListener {
	private static final int WIDTH =300;
	private static final int HEIGHT =140;
	private MyData data;//数据
	private MyView frame=null;
	private Calendar today=Calendar.getInstance();
	private int year,month,day;//年月日
	private JPanel mainPanel,jpForDate,jpForButton;
	private JLabel labelForDay,labelForMonth,labelForYear,title;
	private JTextField textDay, textMonth,textYear;
	private JButton okButton,cancelButton;
	public AddDayView(MyView frame,MyData data) {
		setTitle("选择日期");
        setModal(true);
        this.frame=frame;
        this.data=data;
	}
	public void loadView(){
		setSize(WIDTH, HEIGHT);
		jpForDate=new JPanel();
		textYear=new JTextField(4);
		textMonth=new JTextField(2);
		textDay=new JTextField(2);
		labelForYear=new JLabel("年");
		labelForMonth=new JLabel("月");
		labelForDay=new JLabel("日");
		jpForDate.add(textYear);
		jpForDate.add(labelForYear);
		jpForDate.add(textMonth);
		jpForDate.add(labelForMonth);
		jpForDate.add(textDay);
		jpForDate.add(labelForDay);
		textYear.addKeyListener(this);
		textMonth.addKeyListener(this);
		textDay.addKeyListener(this);
		textYear.setText(today.get(Calendar.YEAR)+"");
		textMonth.setText((today.get(Calendar.MONTH)+1)+"");
		textDay.setText(today.get(Calendar.DAY_OF_MONTH)+"");
		okButton=new JButton("确定");
		okButton.addActionListener(this);
		cancelButton=new JButton("取消");
		cancelButton.addActionListener(this);
		jpForButton=new JPanel();
		jpForButton.add(okButton);
		jpForButton.add(cancelButton);
		title=new JLabel("请输入日期：");
		mainPanel=new JPanel(new BorderLayout());
		mainPanel.add(jpForDate,BorderLayout.CENTER);
		mainPanel.add(jpForButton,BorderLayout.SOUTH);
		mainPanel.add(title,BorderLayout.NORTH);
		setContentPane(mainPanel);
		setLocationRelativeTo(null);
		setVisible(true);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		Object source=e.getSource();
		if(source==okButton){
			try {
                year = Integer.parseInt(textYear.getText());
                month = Integer.parseInt(textMonth.getText());
                day = Integer.parseInt(textDay.getText());
                if(checkValid()){
                	MyDay theDay=data.getDayList().getDay(year, month, day);
                	if(theDay==null){
                		theDay=new MyDay();
                		theDay.getDate().set(year, month-1, day);//month-1
                		data.getDayList().getDayList().add(theDay);
                	}
                	DayView dayView = new DayView(frame,theDay,data);//
                	dispose();
            		dayView.loadView();
                    
                }else{
                	JOptionPane.showMessageDialog(null, "请输入一个合理的日期");
                }
            } catch (NumberFormatException e1) {
            	JOptionPane.showMessageDialog(null, "输入日期格式不正确");
            }
		}else if(source==cancelButton){
			dispose();
		}
	}
	@Override
	public void keyTyped(KeyEvent e) {
		int keyChar = e.getKeyChar();                 
        if(keyChar >= KeyEvent.VK_0 && keyChar <= KeyEvent.VK_9){
        }else{ // 如果输入的不是数字则屏蔽输入
            e.consume(); //关键，屏蔽掉非法输入  
        } 
	}
	@Override
	public void keyPressed(KeyEvent e) {
	}
	@Override
	public void keyReleased(KeyEvent e) {
	}
	//简单的检查有效性
	private boolean checkValid(){
		today.set(Calendar.YEAR, year);
		if(month<1||month>today.getActualMaximum(Calendar.MONTH)+1){
			return false;
		}
		today.set(Calendar.MONTH, month-1);
		if(day<1||day>today.getActualMaximum(Calendar.DAY_OF_MONTH)){
			return false;
		}
		return true;
	}
}