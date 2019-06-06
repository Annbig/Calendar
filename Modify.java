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
* @author ���� E-mail:
* @version ����ʱ�䣺2019��5��30�� ����8:24:27
* ��˵��
*/
//�����ǵ�����ʱ��֮��weekFrame�����ϲ�û����ʾ
public class Modify extends Frame{//ÿ��weekFrame��һ��Modify
	int Year;
	int Month;
	int Day;//���õ�����һ�������
	Label title=new Label("Reminders");
	JButton OK=new JButton();//
	JButton cancel=new JButton();//ȡ����������
	Choice year =new Choice();//���ѡ��
	Choice month =new Choice();//�·�ѡ��
	Choice day=new Choice();//����ѡ��
	Choice hour=new Choice();//Сʱѡ��
	Choice minute=new Choice();//����ѡ��
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
					MyCalendar.Setremind();//���±���¼������
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				action(e);
			}
		});
	}
	public void Timenow() throws IOException {//��ȡ��Ӧ������ʱ����ʾ���ĸ������б��У����û�о���ʾϵͳʱ��
		String date=Year+""+Month+""+Day;
		Date_Time dt=new Date_Time(date);
		if(dt.Exist()) {
			Timing time =dt.getTiming();
			if(time.year>=Calendar.getInstance().get(Calendar.YEAR))
				year.select(time.year-Calendar.getInstance().get(Calendar.YEAR));//����ֻ��ȡ�±꣬����ȡ��ֵ
			else
				year.select(0);
			month.select(time.month-1);
			day.select(time.day-1);
			hour.select(time.hour);
			minute.select(time.minute);
		}
		else {
			//���û��
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
			year.add(String.valueOf(inityear++));//����ѡ�����ڿ�ʼ�������ڵ�ʱ��
		}
		for(int i=1;i<=12;i++) {
			month.add(String.valueOf(i));
		}
		for(int i=1;i<=31;i++) {//�˴���bug������
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
		this.addWindowListener(new WindowAdapter() {//�����Ͻǵ�X����¼�
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