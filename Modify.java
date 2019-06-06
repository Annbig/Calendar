package com.calendar;

import java.awt.Choice;
import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.Calendar;

import javax.swing.BorderFactory;
import javax.swing.JButton;

import com.calendar.Modify;
import com.calendar.Timing;

/**
* @author 作者 E-mail:
* @version 创建时间：2019年5月30日 下午8:24:27
* 类说明
*/
//问题是调整完时间之后weekFrame窗口上并没有显示
public class Modify extends Frame{//每个weekFrame绑定一个Modify
	int Year;
	int Month;
	int Day;//设置的是哪一天的提醒
	Label title=new Label("Reminders");
	JButton OK=new JButton();//
	JButton cancel=new JButton();//取消重新设置
	Choice year =new Choice();//年份选择
	Choice month =new Choice();//月份选择
	Choice day=new Choice();//日期选择
	Choice hour=new Choice();//小时选择
	Choice minute=new Choice();//分钟选择
	Label YEAR=new Label("YEAR");
	Label MONTH=new Label("MONTH");
	Label DAY=new Label("DAY");
	Label HOUR=new Label("HOUR");
	Label MINUTE=new Label("MINUTE");
	public void ok() {
		OK.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				int Y,M,D,h,m;
				Y=Integer.parseInt(year.getSelectedItem());
				M=Integer.parseInt(month.getSelectedItem());
				D=Integer.parseInt(day.getSelectedItem());
				h=Integer.parseInt(hour.getSelectedItem());
				m=Integer.parseInt(minute.getSelectedItem());
				Timing time=new Timing(Y,M,D,h,m);
				String date=Year+""+Month+""+Day;
				try {
					new Date_Time(date,time);
					MyCalendar.Setremind();//更新备忘录的内容
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				action(e);
			}
		});
	}
	public void Timenow() throws IOException {//获取对应的提醒时间显示在四个下拉列表中，如果没有就显示系统时间
		String date=Year+""+Month+""+Day;
		Date_Time dt=new Date_Time(date);
		if(dt.Exist()) {
			Timing time =dt.getTiming();
			if(time.year>=Calendar.getInstance().get(Calendar.YEAR))
				year.select(time.year-Calendar.getInstance().get(Calendar.YEAR));//由于只能取下标，所以取差值
			else
				year.select(0);
			month.select(time.month-1);
			day.select(time.day-1);
			hour.select(time.hour);
			minute.select(time.minute);
		}
		else {
			//如果没有
			year.select(0);
			month.select(Calendar.getInstance().get(Calendar.MONTH));
			day.select(Calendar.getInstance().get(Calendar.DATE)-1);
			hour.select(Calendar.getInstance().get(Calendar.HOUR_OF_DAY));
			minute.select(Calendar.getInstance().get(Calendar.MINUTE));
		}
	}
	public void TimeSet() {
		int inityear=Calendar.getInstance().get(Calendar.YEAR);
		for(int i=1;i<=5;i++) {
			year.add(String.valueOf(inityear++));//可以选择现在开始五年以内的时间
		}
		for(int i=1;i<=12;i++) {
			month.add(String.valueOf(i));
		}
		for(int i=1;i<=31;i++) {//此处有bug待处理
			day.add(String.valueOf(i));
		}
		for(int i=0;i<=23;i++) {
			hour.add(String.valueOf(i));
		}
		for(int i=0;i<=59;i++) {
			minute.add(String.valueOf(i));
		}
	}
	public void Exit() {
		cancel.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				action(e);
			}
			
		});
	}
	public void action(ActionEvent e) {
		this.setVisible(false);
	}
	public Modify(int Y,int M,int D) throws IOException {
		Year=Y;
		Month=M;
		Day=D;
		init();
		this.addWindowListener(new WindowAdapter() {//给右上角的X添加事件
			public void windowClosing(WindowEvent e) {
			    closeaction(e);
			}
		});
	}
	public void closeaction(WindowEvent e) {
		this.setVisible(false);
	}
	public void design() {
		this.setLayout(null);
		OK.setText("OK");
		cancel.setText("CANCEL");
		title.setBounds(75,20,100,40);
		
		YEAR.setBounds(95,60,60,30);
		MONTH.setBounds(60,120,60,30);
		DAY.setBounds(130,120,60,30);
		HOUR.setBounds(60,180,60,30);
		MINUTE.setBounds(130,180,60,30);
	
		year.setBounds(95,90,60,30);
		month.setBounds(60,150,60,30);
		day.setBounds(130,150,60,30);
		hour.setBounds(60,210,60,30);
		minute.setBounds(130,210,60,30);
		
		OK.setBounds(0, 260, 125, 39);
		cancel.setBounds(124,260,125,39);
		
		year.setFont(new Font(Font.DIALOG,Font.ROMAN_BASELINE,15));
		month.setFont(new Font(Font.DIALOG,Font.ROMAN_BASELINE,15));
		day.setFont(new Font(Font.DIALOG,Font.ROMAN_BASELINE,15));
		hour.setFont(new Font(Font.DIALOG,Font.ROMAN_BASELINE,15));
		minute.setFont(new Font(Font.DIALOG,Font.ROMAN_BASELINE,15));
		title.setFont(new Font(Font.DIALOG,Font.HANGING_BASELINE,17));
		OK.setFont(new Font(Font.DIALOG,Font.ROMAN_BASELINE,17));
		cancel.setFont(new Font(Font.DIALOG,Font.ROMAN_BASELINE,17));
		OK.setBackground(Color.white);
		OK.setBorder(BorderFactory.createMatteBorder(1, 1, 1,1, Color.LIGHT_GRAY));
		cancel.setBackground(Color.white);
		cancel.setBorder(BorderFactory.createMatteBorder(1, 1, 1,1, Color.LIGHT_GRAY));
	}
	public void init() throws IOException {
		design();
		TimeSet();
		Timenow();
		ok();
		Exit();
		this.add(title);
		this.add(OK);
		this.add(cancel);
		
		this.add(year);
		this.add(month);
		this.add(day);
		this.add(hour);
		this.add(minute);
		
		this.add(YEAR);
		this.add(MONTH);
		this.add(DAY);
		this.add(HOUR);
		this.add(MINUTE);
		
		this.setLocation(440, 200);
		this.setTitle("Modify");
		this.setSize(250,300);
		this.setVisible(true);
		this.setResizable(false);
	}
}