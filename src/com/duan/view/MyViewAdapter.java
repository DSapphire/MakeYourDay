package com.duan.view;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class MyViewAdapter extends WindowAdapter {
	MyView view;
	public MyViewAdapter(MyView view) {
		this.view = view;
	}
	@Override
	public void windowClosing(WindowEvent e) {
		if(view!=null)
			view.closingView();
	}
}
