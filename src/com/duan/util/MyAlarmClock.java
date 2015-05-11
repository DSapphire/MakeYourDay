package com.duan.util;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.JToggleButton;

import com.duan.model.MyClock;
import com.duan.model.MyTime;

public class MyAlarmClock extends JFrame implements ActionListener{
	
	private static final int WIDTH=300;
	private static final int HEIGHT=530;
	private static final int LOOP_COUNT = 5;

	private JPanel jpForTime;
	private JPanel jpForClockList;//JScrollPane
	private JPanel jpForClock;//FlowLayout
	
	private JLabel labelForTime;
	private JButton setButton;
	private JRadioButton buttonForClock;
	private JLabel labelForClock;
	private SetDialogForClock setDialog;
	private RingDailogForClock ringDialog;
	private Timer timer;
    private Clip clip;
    private Calendar calendar;
	private DateFormat df = new SimpleDateFormat("HH : mm : ss    E    M/d/Y");
	
	private ArrayList<MyClock> clockList;
	
	public MyAlarmClock() {
		super("����");
		clockList=new ArrayList<MyClock>();
	}
	public void clockStart(){
		setSize(WIDTH, HEIGHT);
        //
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        Toolkit kit=Toolkit.getDefaultToolkit();
		Dimension screenSize=kit.getScreenSize();
		int width=screenSize.width;
		int height=screenSize.height;
		int x=(width-WIDTH)/2;
		int y=(height-HEIGHT)/2;
		setLocation(x, y);
		
		getContentPane().setLayout(new BorderLayout(2,2));
		jpForTime=new JPanel();
		jpForClockList=new JPanel();
		jpForClockList.setLayout(new BoxLayout(jpForClockList,BoxLayout.Y_AXIS));
		labelForTime=new JLabel();labelForTime.setSize(WIDTH, HEIGHT/8);
		Font font = new Font(Font.SERIF, Font.PLAIN, 20);
		labelForTime.setFont(font);
		labelForTime.setHorizontalAlignment(JLabel.CENTER);
		jpForTime.setLayout(new BorderLayout());
		jpForTime.add(labelForTime,BorderLayout.CENTER);
		jpForTime.setSize(WIDTH, HEIGHT/8);
		
		jpForClockList.setSize(WIDTH, HEIGHT*3/4);
		
		
		setButton=new JButton("�������");
		setButton.setFont(font);
		setButton.setHorizontalAlignment(JLabel.CENTER);
		setButton.setSize(WIDTH, HEIGHT/8);
		
		
		getContentPane().add(setButton,BorderLayout.SOUTH);
		getContentPane().add(jpForClockList,BorderLayout.CENTER);
		getContentPane().add(jpForTime,BorderLayout.NORTH);
		setVisible(true);
		
		setButton.addActionListener(this);
		setDialog = new SetDialogForClock(this);
		//ringDialog=new RingDailogForClock(this);
		
		try { // ��ʼ����������
            // Ŀǰ����wav��ʽ���ļ��ǿ���֧�ֵģ�mp3��֧��
            AudioInputStream ais = AudioSystem.getAudioInputStream(
                    new File("res/alarm.wav"));
            clip = AudioSystem.getClip();
            clip.open(ais);
            ais.close();
            int loop = LOOP_COUNT <= 0 ? 1 : LOOP_COUNT;
            final long totalFrames = ais.getFrameLength() * loop;
            clip.addLineListener(new LineListener() {
                @Override
                public void update(LineEvent e) {
                    // ���������ֲ��Ž���ʱ���Զ����ض�����ʾ��
                    if(e.getFramePosition() >= totalFrames) {
                        //stopAlarm();
                    }
                }
            });
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }
		
		initTimer();
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		if(source==setButton){
			setDialog.setVisible(true);
		}
		
	}
	
	public void initTimer(){
		timer=new Timer();
		timer.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				Calendar cal = Calendar.getInstance();
                labelForTime.setText(df.format(cal.getTime()));
                
			}
		}, 0, 1000L);
	}
	public void addClock(MyClock clock){
		calendar=clock.getDate();
		jpForClock=new JPanel(new BorderLayout());
		buttonForClock=new JRadioButton("",true);
		labelForClock=new JLabel();
		//
		jpForClockList.setLayout(jpForClockList.getLayout());
		labelForClock.setText(clock.toString());
		jpForClock.add(labelForClock,BorderLayout.WEST);
		jpForClock.add(buttonForClock,BorderLayout.EAST);
		jpForClockList.add(jpForClock);
		jpForClockList.revalidate();
		setVisible(true);
		clockList.add(clock);
	}
	private void ringAlarm(MyClock clock){
		if(clip!=null){
			clip.setFramePosition(0); // ����Ƶ֡����Ϊ��0֡
	        clip.loop(LOOP_COUNT);
	        ringDialog=new RingDailogForClock(this, clock);
		}
	}
	private void stopAlarm(){
		if(null != clip && clip.isRunning()) {
            clip.stop(); // ��������
        }
	}
	
	
	class SetDialogForClock extends JDialog implements KeyListener, ActionListener{
		private static final int WIDTH=300;
		private static final int HEIGHT=530;
		
		private JSplitPane jpForTime;
		private JLabel labelHour, labelMin,label,labelMemo;
	    private JTextField textHour, textMin,textMemo;
	    private JPanel leftJp,rightJp;
	    
	    private JPanel  mainPanel,setTypePanel,jrButtonPanel, buttonPanel;
	    private JButton btnOk, btnCancel, btnBack;

	    private JRadioButton[] jr={new JRadioButton("��һ"),
	    		new JRadioButton("�ܶ�"),new JRadioButton("����"),
	    		new JRadioButton("����"),new JRadioButton("����"),
	    		new JRadioButton("����"),new JRadioButton("����"),
	    		new JRadioButton("����",true)
	    };
	    private Font font=new Font(Font.SERIF, Font.PLAIN, 18);
	    private Calendar cal = Calendar.getInstance();
	    
		public SetDialogForClock(JFrame frame){
			super(frame);
			setTitle("��������");
	        setModal(true); // ����Ϊģ���ڣ�����˵�ڱ�����δ��ʧʱ�������������档
	        setSize(WIDTH, HEIGHT);
			
	        mainPanel=new JPanel(new BorderLayout(2,2));
	        
	        jpForTime=new JSplitPane();
	        jpForTime.setOneTouchExpandable(true);
	        jpForTime.setContinuousLayout(true);
	        jpForTime.setPreferredSize(new Dimension(WIDTH, HEIGHT/8));
	        labelHour = new JLabel("ʱ");labelHour.setFont(font);
	        labelMin = new JLabel("��");labelMin.setFont(font);
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
	        
	        btnOk = new JButton("ȷ��");
	        btnCancel = new JButton("ȡ��");
	        btnBack = new JButton("����");
	        buttonPanel = new JPanel(new FlowLayout());
	        buttonPanel.add(btnBack);
	        buttonPanel.add(btnCancel);
	        buttonPanel.add(btnOk);
	        btnBack.addActionListener(this);
	        btnOk.addActionListener(this);
	        btnCancel.addActionListener(this);
	        
	        setTypePanel=new JPanel(new GridLayout(4, 1));
	        label=new JLabel("�ظ�");
	        label.setFont(font);
	        labelMemo=new JLabel("����");
	        labelMemo.setFont(font);
	        jrButtonPanel=new JPanel(new GridLayout(2, 4));
	        textMemo=new JTextField();
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
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			Object source = e.getSource();
	        if(source == btnOk) { // ��������ȷ����ť����ʼ��ʱ
	            int hour = 0, min = 0;
	            try {
	                hour = Integer.parseInt(textHour.getText());
	            } catch (NumberFormatException e1) {
	            }
	            try {
	                min = Integer.parseInt(textMin.getText());
	            } catch (NumberFormatException e1) {
	            }
	            if(hour>24||min>60){
	            	while(hour>24){
	            		hour-=24;
	            	}
	            	while(min>60){
	            		min-=60;
	            		hour++;
	            	}
	            	JOptionPane.showMessageDialog(null, "ʱ����������Զ�����Ϊ"+hour+":"+min);
	            }
	            MyClock clock=new MyClock();
	            MyTime time=new MyTime(hour, min);
	            clock.setTime(time);
	            String memo=textMemo.getText();
	            clock.setMemo(memo);
	            int type=getTypeForClock();
	            clock.setType(type);
	            clock.updateType();
	            addClock(clock);
	            setVisible(false);
	        } else if(source == btnCancel) { // ���ȡ����ťʱȡ����ʱ
	            setVisible(false);
	        } else if(source == btnBack) { // ������ذ�ťʱʲôҲ������ֱ�ӹر����ý���
	            setVisible(false);
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
	        }else{ // �������Ĳ�����������������
	            e.consume(); //�ؼ������ε��Ƿ�����  
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
	
	class RingDailogForClock extends JDialog implements ActionListener{
		private static final int WIDTH=200;
		private static final int HEIGHT=100;
		private JButton btnOk, btnDelay;
		private JLabel labelMemo,labelTime;
		private JPanel mainPanel,jpForLabel,jpForButton;
		public RingDailogForClock(JFrame frame,MyClock clock){
			super(frame);
			setTitle("��������");
	        setModal(true); // ����Ϊģ���ڣ�����˵�ڱ�����δ��ʧʱ�������������档
	        setSize(WIDTH, HEIGHT);
	        btnOk=new JButton("ȷ��");
	        btnDelay=new JButton("�ӳ�");
	        jpForButton=new JPanel(new BorderLayout());
	        jpForButton.add(btnOk,BorderLayout.WEST);
	        jpForButton.add(btnDelay,BorderLayout.EAST);
	        
	        labelMemo=new JLabel(clock.getMemo());
	        labelMemo.setHorizontalAlignment(JLabel.CENTER);
	        labelTime=new JLabel(clock.getTime().toString());
	        labelTime.setHorizontalAlignment(JLabel.CENTER);
	        jpForLabel=new JPanel(new BorderLayout());
	        jpForLabel.add(labelMemo,BorderLayout.NORTH);
	        jpForLabel.add(labelTime,BorderLayout.SOUTH);
	        
	        mainPanel=new JPanel(new BorderLayout());
	        mainPanel.add(jpForLabel,BorderLayout.NORTH);
	        mainPanel.add(jpForButton,BorderLayout.SOUTH);
	        setContentPane(mainPanel);
	        
	        setLocationRelativeTo(frame);
			btnDelay.addActionListener(this);
			btnOk.addActionListener(this);
			setVisible(true);
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			Object source = e.getSource();
	        if(source == btnOk){//
	        	setVisible(false);
	        	
	        }else if(source==btnDelay){//
	        	setVisible(false);
	        }
		}
	}

	public static void main(String[] args) {
		new MyAlarmClock().clockStart();
	}
}
