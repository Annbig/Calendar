package com.calendar;

import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
* @author 作者 E-mail:
* @version 创建时间：2019年5月28日 下午10:35:07
* 类说明
*/

public class MyCalendar extends Frame{
	JScrollPane scrollPane_1 = new JScrollPane();
	JTextArea dateofremove=new JTextArea();//内有显示提醒，输入对应date删除提醒
	JButton reminder=new JButton();//提醒项
	JButton exit=new JButton();//退出按钮
	JButton hide=new JButton();//隐藏提示项
	JButton remove=new JButton();//删除提示项
	Label time=new Label();//时间
	Label MonthDay=new Label();//左上角的时间（月天）
	Label []WeekLabel=new Label[7];//s m t .....
	Label remindtitle=new Label();//提醒项的标题
	JButton[]day=new JButton[42];//显示每个月的每一天
	static JTextArea remindtext=new JTextArea();//显示提示项
	static JButton nextMonth=new JButton();
	static JButton lastMonth=new JButton();
	int mymonth;//当前所在月份
	int myyear;//当前所在年份
	int []runyear= {31,29,31,30,31,30,31,31,30,31,30,31};
	int []nonrunyear= {31,28,31,30,31,30,31,31,30,31,30,31};
	public void Center() {
		mymonth=Calendar.getInstance().get(Calendar.MONTH)+1;
		myyear=Calendar.getInstance().get(Calendar.YEAR);
		Daylayout();
		Day(mymonth,myyear);
		dayoperation();
		
	}
	public void dayoperation() {//点击每一个day按钮出来一个框
		String []WEEK= {"MONDAY","TUESDAY","WEDNESDAY","THURSDAY","FRIDAY","SATURDAY","SUNDAY"};
		for(int i=0;i<42;i++) {
			final int j=i;
			day[i].addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					int year=myyear;
					int month=mymonth;
					String s=day[j].getText();
					int day=Integer.parseInt(s);
					new WeekFrame(WEEK[(j+6)%7],year,month,day);
				}
			});
		}
	}
	public void Daylayout() {//每一个格子的布局
		int t=0;
		for(int i=0;i<6;i++) {
			for(int j=0;j<7;j++) {
				day[t]=new JButton();
				day[t].setBackground(Color.pink);
				day[t].setBorder(BorderFactory.createMatteBorder(1, 1, 1,1, Color.white));
				day[t].setBounds(20+j*60,110+i*40,60,40);
				day[t].setFont(new Font(Font.DIALOG,Font.ROMAN_BASELINE,13));
			    this.add(day[t]);
			    t++;
			}
		}
	}
	public void Day(int month,int year) {
		Timing T=new Timing(month);
		T.Setyear(year);
		int first=T.firstWeekdayofMonth();//上月的第一天所在的周几
		int monthday=(year%4==0)?runyear[(month+11)%12]:nonrunyear[(month+11)%12];//该月的天数
		//第一行上个月
		int lastmonthday=(year%4==0)?runyear[(month+10)%12]:nonrunyear[(month+10)%12];//上个月的天数
		for(int i=first;i>=0;i--) {
			if(i!=first) {
				String s=""+lastmonthday--;
				day[i].setText(s);
				day[i].setEnabled(false);
			}
		}
		//第一行这个月开始
		int dayofmonth=1;
		for(int i=first;i<(first+monthday);i++) {
			String s=""+dayofmonth++;
			day[i].setText(s);
			day[i].setEnabled(true);
		}
		//下个月的行
		int t=1;
		for(int i=first+monthday;i<42;i++) {
			String s=""+t++;
			day[i].setText(s);
			day[i].setEnabled(false);
		}
	}
	public void nextm() {
		nextMonth.setText("Nextmonth");
		nextMonth.setFont(new Font(Font.DIALOG,Font.PLAIN,13));
		nextMonth.setBounds(470, 250, 100, 50);
		nextMonth.setBackground(Color.white);
		nextMonth.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(mymonth+1==13) {
					mymonth=1;
					myyear++;
					Day(mymonth,myyear);
					monthday(mymonth,myyear);//左上角跟着改变
				}
				else {
					Day(++mymonth,myyear);
					monthday(mymonth,myyear);
				}
			}
			
		});
		this.add(nextMonth);
	}
	public void lastm(){
		lastMonth.setText("Lastmonth");
		lastMonth.setFont(new Font(Font.DIALOG,Font.ROMAN_BASELINE,13));
		lastMonth.setBounds(470, 150, 100, 50);
		lastMonth.setBackground(Color.white);
		lastMonth.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(mymonth-1==0) {
					mymonth=12;
					myyear--;
					Day(mymonth,myyear);
					monthday(mymonth,myyear);
				}
				else {
					Day(--mymonth,myyear);
					monthday(mymonth,myyear);
				}
			}
			
		});
		this.add(lastMonth);
	}
	public void monthday() {//左上方最初显示
		String s="|  "+new Timing(Calendar.getInstance().get(Calendar.MONTH)+1).monthday();//“|”是为了美观
		s+=" "+Calendar.getInstance().get(Calendar.YEAR);
		MonthDay.setText(s);
	}
	public void monthday(int month,int year) {//左上方显示
		String s="|  "+new Timing(month).monthday();//“|”是为了美观
		s+=" "+year;
		MonthDay.setText(s);
	}
	public void weeklabel() {
		String[] S= {"S","M","T","W","T","F","S"};
		for(int i=0;i<7;i++) {
			WeekLabel[i]=new Label(S[i]);
			WeekLabel[i].setBounds(40+i*60,80,60,30);
		}
	}
	//下面两段代码实时更新时间
	public void Time() throws InterruptedException {//time按钮实时显示当前日期：哪一年 几月几日 星期几
		Timer timer=new Timer();
		Date nowtime=new Date();
		timer.scheduleAtFixedRate(new TimeUpdate(),nowtime,1000);
	}
	class TimeUpdate extends TimerTask {
		SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		public void run() {
			// 刷新时间
			String Time = dateFormatter.format(Calendar.getInstance().getTime());
			time.setText(Time);
		}
	}
	public void Top() {
		try {
			Time();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		monthday();
		MonthDay.setBounds(20,20,200,60);
		MonthDay.setFont(new Font(Font.DIALOG,Font.ROMAN_BASELINE,20));//字体需要变大
		time.setBounds(400,20,200,70);
		time.setFont(new Font(Font.DIALOG,Font.ROMAN_BASELINE,15));//字体需要变大
		this.add(MonthDay);
		this.add(time);
	}
	
	public void Reminder() {//提醒项
		//添加滚动条  
	    scrollPane_1.setViewportView(remindtext);
		reminder.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				try {
					remindaction(e);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
	}
	public void remindaction(ActionEvent e) throws IOException {
		this.setSize(900,450);
		Setremind();
	}
	public static void Setremind() throws IOException{
		//显示出所有的提醒事项
		String str="";
		String []list=Date_Time.DateList();
		int l=list.length;
		for(int i=0;i<l;i++) {
			Date_Time dt=new Date_Time(list[i]);
			Text t=new Text(list[i]);
			str+=list[i]+"————";
			str+="[alarm at ";
			str+=dt.getTiming().toString();
			str+="]\n";
			str+=t.getcontent();
			//str+="\n";
		}
		remindtext.setText(str);
	}
	public void Exit() {
		exit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				System.exit(0);
			}
		});
	}
	public void Hide() {
		hide.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				try {
					hideaction(e);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
	}
	public void hideaction(ActionEvent e) throws IOException {
		this.setSize(600,450);
	}
	public void Remove() {//remove按钮的功能 ，待编辑
		remove.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String str=dateofremove.getText();
				Date_Time dt;
				try {
					dt = new Date_Time(str);
					dt.delete();
					Setremind();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
		});
	}
	public void Bottom() throws IOException {
		Hide();
		Exit();
		Reminder();
		Remove();
		dateofremove.setText("input date like 201862:");
		dateofremove.setBounds(765,385,120,20);
		dateofremove.setBackground(Color.white);
		dateofremove.setBorder(BorderFactory.createMatteBorder(1,1,1,1, Color.pink));
		remove.setText("remove");//编辑按钮
		remove.setBounds(700,380,60,30);
		remove.setBackground(Color.white);
		remove.setFont(new Font(Font.DIALOG,Font.ROMAN_BASELINE,12));
		remove.setBorder(BorderFactory.createMatteBorder(1, 1, 1,1, Color.pink));
		hide.setText("Hide");//编辑按钮
		hide.setBounds(620,380,60,30);
		hide.setBackground(Color.white);
		hide.setFont(new Font(Font.DIALOG,Font.ROMAN_BASELINE,12));
		hide.setBorder(BorderFactory.createMatteBorder(1, 1, 1,1, Color.pink));
		remindtitle.setText("Reminders are as Below");
		remindtitle.setBounds(660, 30, 150, 30);
		remindtitle.setFont(new Font(Font.DIALOG,Font.PLAIN,13));
		remindtext.setBounds(610, 60, 270, 300);
		remindtext.setBackground(Color.white);
		remindtext.setBorder(BorderFactory.createMatteBorder(1,1,1,1, Color.pink));
		remindtext.setEditable(false);
		reminder.setText("REMINDERS");//提醒按钮
		reminder.setBounds(50,380,180,40);
		reminder.setBackground(Color.white);
		reminder.setFont(new Font(Font.DIALOG,Font.ROMAN_BASELINE,12));
		reminder.setBorder(BorderFactory.createMatteBorder(1, 1, 1,1, Color.pink));
		exit.setText("EXIT");
		exit.setBounds(280,380,180,40);
		exit.setBackground(Color.white);
		exit.setFont(new Font(Font.DIALOG,Font.ROMAN_BASELINE,12));
		exit.setBorder(BorderFactory.createMatteBorder(1, 1, 1,1, Color.pink));
		scrollPane_1.setBounds(610, 60, 270, 301);
		scrollPane_1.setBackground(Color.white);
	    this.add(scrollPane_1);     
		this.add(remindtitle);
		this.add(hide);
		this.add(remove);
		this.add(exit);
		this.add(reminder);
		this.add(dateofremove);
	}
	private void init() throws IOException {
		this.setLayout(null);
		weeklabel();
		Top();
		Center();
		Bottom();
		nextm();
		lastm();
		this.setBackground(Color.white);
		this.setLocation(300, 50);
		for(int i=0;i<7;i++) {
			this.add(WeekLabel[i]);
		}
	}
	public MyCalendar() {
		try {
			init();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.addWindowListener(new WindowAdapter() {//给右上角的X添加事件
			public void windowClosing(WindowEvent e) {
			    System.exit(0);
			}
		});
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MyCalendar mc=new MyCalendar();
		mc.setTitle("Calendar");
		mc.setSize(600,450);
		mc.setVisible(true);
		mc.setResizable(false);
	}
}
