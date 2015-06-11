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

import com.duan.model.MyActivity;
import com.duan.util.RingClock;

public class ReminderView extends JFrame implements ActionListener,MyView{
	private static final int WIDTH =300;
	private static final int HEIGHT =200;
	private MyActivity act;
	private JButton okButton;
	private JLabel label1,label2;
	private JPanel jp;
	private RingClock ring;
	public ReminderView(MyActivity act,RingClock ring) {
		setTitle("Ã·–—");
		this.act=act;
		this.ring=ring;
	}
	public void loadView(){
		setSize(WIDTH, HEIGHT);
		label1=new JLabel();
		label1.setText(act.getStartTime().toString());
		label1.setHorizontalAlignment(JLabel.CENTER);
		Font font = new Font(Font.SERIF, Font.PLAIN,30);
		label1.setFont(font);
		label2=new JLabel();
		label2.setText(act.getName());
		label2.setHorizontalAlignment(JLabel.CENTER);
		label2.setFont(font);
		okButton=new JButton("»∑∂®");
		okButton.addActionListener(this);
		okButton.setPreferredSize(new Dimension(WIDTH/4, HEIGHT/5));
		jp=new JPanel(new BorderLayout());
		jp.add(label1,BorderLayout.NORTH);
		jp.add(label2,BorderLayout.CENTER);
		jp.add(okButton,BorderLayout.SOUTH);
		setContentPane(jp);
		addWindowListener(new MyViewAdapter(this));
		setVisible(true);
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
	public void updateMyView() {
		loadView();
	}
}
