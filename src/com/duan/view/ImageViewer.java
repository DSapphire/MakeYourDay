package com.duan.view;

import java.awt.Graphics;
import java.awt.Image;
import javax.swing.JPanel;

	//”√”⁄œ‘ æÕº∆¨
public class ImageViewer extends JPanel{
	private Image image;
	private int xCoordinate;
	private int yCoordinate;
	public ImageViewer(){
	}
	public ImageViewer(Image image){
		this.image=image;
	}
	public void setXCoordinate(int xCoordinate){
		this.xCoordinate=xCoordinate;
		repaint();
	}
	public void setYCoordinate(int yCoordinate){
		this.yCoordinate=yCoordinate;
		repaint();
	}
	protected void paintComponent(Graphics g){
		super.paintComponent(g);
		if(image!=null){
			g.drawImage(image,xCoordinate,yCoordinate,getSize().width,getSize().height,this);
		}
	}
}
