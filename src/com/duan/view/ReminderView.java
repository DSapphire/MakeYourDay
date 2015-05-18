package com.duan.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JDialog;

import com.duan.model.MyActivity;

public class ReminderView extends JDialog implements ActionListener {
	private static final int WIDTH = 328;
	private static final int HEIGHT = 400;
	private MyActivity act;
	public ReminderView(MyActivity act) {
		setModal(true);
		setTitle("Ьсаб");
		this.act=act;
	}
	public void view(){
		
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

	}

}
