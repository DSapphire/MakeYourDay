package com.duan.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
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
import javax.swing.JRadioButton;
import javax.swing.JSplitPane;
import javax.swing.JTextField;

import com.duan.model.MyClock;
import com.duan.model.MyClockList;
import com.duan.model.MyTime;

public class AddClockView extends JDialog implements KeyListener, ActionListener{
	private static final int WIDTH=300;
	private static final int HEIGHT=530;
	
	private JSplitPane jpForTime;
	private JLabel labelHour, labelMin,label,labelMemo;
    private JTextField textHour, textMin,textMemo;
    private JPanel leftJp,rightJp;
    private JPanel  mainPanel,setTypePanel,jrButtonPanel, buttonPanel;
    private JButton btnOk, btnCancel, btnBack;

    private JRadioButton[] jr={new JRadioButton("周一"),
    		new JRadioButton("周二"),new JRadioButton("周三"),
    		new JRadioButton("周四"),new JRadioButton("周五"),
    		new JRadioButton("周六"),new JRadioButton("周日"),
    		new JRadioButton("单次",true)
    };
    private Font font=new Font(Font.SERIF, Font.PLAIN, 18);
    private Calendar cal = Calendar.getInstance();
    
    private MyClockList list;
    private ClockView frame;
	public AddClockView(ClockView sframe,MyClockList list){
		super(sframe);
		setTitle("闹钟设置");
        setModal(true); // 设置为模窗口，就是说在本弹窗未消失时不允许点击主界面。
        this.list=list;
        this.frame=sframe;
        setSize(WIDTH, HEIGHT);
        mainPanel=new JPanel(new BorderLayout(2,2));
        jpForTime=new JSplitPane();
        jpForTime.setOneTouchExpandable(true);
        jpForTime.setContinuousLayout(true);
        jpForTime.setPreferredSize(new Dimension(WIDTH, HEIGHT/8));
        labelHour = new JLabel("时");labelHour.setFont(font);
        labelMin = new JLabel("分");labelMin.setFont(font);
        labelHour.setHorizontalAlignment(JLabel.CENTER);
        labelMin.setHorizontalAlignment(JLabel.CENTER);
        textHour = new JTextField();
        textMin = new JTextField();
        int hour = cal.get(Calendar.HOUR_OF_DAY);
        int min = cal.get(Calendar.MINUTE);
        textHour.setText(String.format("%02d", hour));
        textMin.setText(String.format("%02d", min));
        leftJp=new JPanel(new GridLayout(2, 1));
        rightJp=new JPanel(new GridLayout(2, 1));
        leftJp.add(labelHour);
        leftJp.add(textHour);
        rightJp.add(labelMin);
        rightJp.add(textMin);
        jpForTime.setLeftComponent(leftJp);
        jpForTime.setRightComponent(rightJp);
        jpForTime.setDividerSize(3);
        jpForTime.setDividerLocation(WIDTH/2);
        textHour.addKeyListener(this);
        textMin.addKeyListener(this);
        btnOk = new JButton("确定");
        btnCancel = new JButton("取消");
        btnBack = new JButton("返回");
        buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(btnBack);
        buttonPanel.add(btnCancel);
        buttonPanel.add(btnOk);
        btnBack.addActionListener(this);
        btnOk.addActionListener(this);
        btnCancel.addActionListener(this);
        setTypePanel=new JPanel(new GridLayout(4, 1));
        label=new JLabel("重复");
        label.setFont(font);
        labelMemo=new JLabel("名称");
        labelMemo.setFont(font);
        jrButtonPanel=new JPanel(new GridLayout(2, 4));
        textMemo=new JTextField("闹钟");
        for(int i=0;i<jr.length;i++){
        	jrButtonPanel.add(jr[i]);
        }
        setTypePanel.add(label);
        setTypePanel.add(jrButtonPanel);
        setTypePanel.add(labelMemo);
        setTypePanel.add(textMemo);
        mainPanel.add(jpForTime,BorderLayout.NORTH);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        mainPanel.add(setTypePanel, BorderLayout.CENTER);
        setContentPane(mainPanel);
		setLocationRelativeTo(frame);
		setVisible(true);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
        if(source == btnOk) { // 如果点击了确定按钮，则开始计时
            int hour = 0, min = 0;
            try {
                hour = Integer.parseInt(textHour.getText());
            } catch (NumberFormatException e1) {
            }
            try {
                min = Integer.parseInt(textMin.getText());
            } catch (NumberFormatException e1) {
            	JOptionPane.showMessageDialog(null,"时间输入有误！");
            }
            if(hour>24||min>60){
            	while(hour>24){
            		hour-=24;
            	}
            	while(min>60){
            		min-=60;
            		hour++;
            	}
            	JOptionPane.showMessageDialog(null, "时间输入错误，自动纠正为"+hour+":"+min);
            }
            MyClock clock=new MyClock();
            MyTime time=new MyTime(hour, min);
            clock.setTime(time);
            String memo=textMemo.getText();
            clock.setMemo(memo);
            int type=getTypeForClock();
            clock.setType(type);
            clock.updateType();
            this.list.getClockList().add(clock);//
            this.frame.loadView();
            this.dispose();
        } else if(source == btnCancel) { // 点击取消按钮时取消计时
        	this.dispose();
        } else if(source == btnBack) { // 点击返回按钮时什么也不做，直接关闭设置界面
        	this.dispose();
        }
	}
	@Override
	public void keyPressed(KeyEvent arg0) {
	}
	@Override
	public void keyReleased(KeyEvent arg0) {
	}
	@Override
	public void keyTyped(KeyEvent e) {
		int keyChar = e.getKeyChar();                 
        if(keyChar >= KeyEvent.VK_0 && keyChar <= KeyEvent.VK_9){
        }else{ // 如果输入的不是数字则屏蔽输入
            e.consume(); //关键，屏蔽掉非法输入  
        } 
	}
	private int getTypeForClock(){
		int type=0;
		int cnt=1;
		for(int i=0;i<jr.length-1;i++){
			if(jr[i].isSelected())
				type+=cnt;
			cnt*=2;
		}
		return type;
	}
}