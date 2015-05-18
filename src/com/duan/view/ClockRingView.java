package com.duan.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JDialog;

import com.duan.model.MyClock;

public class ClockRingView extends JDialog implements ActionListener {
	private static final int WIDTH = 328;
	private static final int HEIGHT = 400;
	private MyClock clock;
	public ClockRingView(MyClock clock) {
		setModal(true);
		setTitle("ʱ�䵽");
		this.clock=clock;
	}
	public void view(){
		
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

	}

}
