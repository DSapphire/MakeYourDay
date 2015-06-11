package com.duan.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.duan.model.MyClock;
import com.duan.util.RingClock;

public class ClockRingView extends JFrame implements ActionListener,MyView{
	private static final int WIDTH =200;
	private static final int HEIGHT =150;
	private MyClock clock;
	private JButton okButton;
	private JLabel label1,label2;
	private JPanel jp;
	private RingClock ring;
	public ClockRingView(MyClock clock,RingClock ring) {
		setTitle("时间到");
		this.clock=clock;
		this.ring=ring;
	}
	public void loadView(){
		setSize(WIDTH, HEIGHT);
		label1=new JLabel();
		label1.setText(clock.getTime().toString());
		label1.setHorizontalAlignment(JLabel.CENTER);
		Font font = new Font(Font.SERIF, Font.PLAIN,30);
		label1.setFont(font);
		label2=new JLabel();
		label2.setText(clock.getMemo());
		label2.setHorizontalAlignment(JLabel.CENTER);
		label2.setFont(font);
		okButton=new JButton("确定");
		okButton.addActionListener(this);
		okButton.setPreferredSize(new Dimension(WIDTH/4, HEIGHT/5));
		jp=new JPanel(new BorderLayout());
		jp.add(label1,BorderLayout.NORTH);
		jp.add(label2,BorderLayout.CENTER);
		jp.add(okButton,BorderLayout.SOUTH);
		setContentPane(jp);
		setVisible(true);
		this.addWindowListener(new MyViewAdapter(this));
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		Object o=e.getSource();
		if(o==okButton){
			ring.stopRing();
			dispose();
		}
	}
	public void closingView(){
		ring.stopRing();
		this.dispose();
	}
	@Override
	public void updateMyView() {
		loadView();
	}
}
