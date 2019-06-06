package com.calendar;

import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Label;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JOptionPane;

/**
* @author 作者 E-mail:
* @version 创建时间：2019年5月30日 下午7:48:06
* 类说明
*/
//修改：删除了索引i，构造函数也删除了对应的i
public class WeekFrame extends Frame{
		int year;
		int month;
		int day;
		String name;
		JButton save=new JButton();//保存按钮
		JButton modify=new JButton();//定制闹钟按钮
		JButton delete=new JButton();//删除内容按钮
		JButton exit=new JButton();//退出子界面按钮
		JButton cancel=new JButton();//取消提醒按钮，在有提醒时会出现
		TextArea textarea=new TextArea();//文本区域显示内容并可以修改
		Label remindtime=new Label();//如果有提醒则显示
		public void time() throws IOException {
			cancel.setVisible(false);//设置为
			remindtime.setText("");
			String str;
			String date=year+""+month+""+day;
			Date_Time dt=new Date_Time(date);
			if(dt.Exist()) {
				//如果有date记录
				str ="alarm: "+dt.getTiming().toString();
				remindtime.setText(str);
				cancel.setVisible(true);
			}
			else //如果没有
				remindtime.setText("");
		}
		public void Save() throws IOException {
			save.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					String str=textarea.getText();
					String date=year+""+month+""+day;
					try {
						new Text(date,str);
						MyCalendar.Setremind();//更新备忘录的内容
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					//action(e);//关闭当前窗口
				}
				
			});
			
		}
		public void Exit() {
			exit.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					action(e);
				}
				
			});
		}
		public void action(ActionEvent e) {
			MyCalendar.nextMonth.setEnabled(true);//让这两个按钮恢复功能
			MyCalendar.lastMonth.setEnabled(true);
			this.setVisible(false);
		}
		public void Modify() {
			modify.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					String str=textarea.getText();
					if(str.equalsIgnoreCase("") || str.equalsIgnoreCase("当前没有需要提醒的事件"))//如果没有写东西的话
						JOptionPane.showMessageDialog(null, "No Content!", "Warning!", JOptionPane.PLAIN_MESSAGE);//弹窗
					else {
						try {
							new Modify(year,month,day);
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
				}
				
			});
		}
		public void Delete() {
			delete.addActionListener(new ActionListener() {
	//文本区域和后台记录都要清空
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					textarea.setText("当前没有需要提醒的事件");
					String date=year+""+month+""+day;
					try {
						new Text(date).delete();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				
			});
		}
		public void Text() throws IOException {
			String date=year+""+month+""+day;
			Text t=new Text(date);
			String s=t.getcontent();
			if(s.equalsIgnoreCase("") || s.equalsIgnoreCase("\n")|| s.equalsIgnoreCase(" ")) 
				textarea.setText("当前没有需要提醒的事件");
			else textarea.setText(s);
		}
		public void Cancel() throws IOException {//取消当日的闹钟
			cancel.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					remindtime.setText("");
					String date=year+""+month+""+day;
					Date_Time dt;
					try {
						dt = new Date_Time(date);
						dt.delete();
					} catch (IOException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}
					cancel.setVisible(false);//然后按钮看不见了
				}
			});
			
		}
		public void design() {
			remindtime.setText("");
			cancel.setText("CANCEL");
			save.setText("SAVE");
			delete.setText("DELETE");
			modify.setText("MODIFY");
			exit.setText("EXIT");
			textarea.setText("当前没有需要提醒的事件");
			
			textarea.setBounds(20,80,260,260);
			cancel.setBounds(220,40,60,25);
			modify.setBounds(20,40,60,25);
			remindtime.setBounds(85,40,130,25);
			exit.setBounds(200,355,60,25);
			delete.setBounds(120,355,60,25);
			save.setBounds(40,355,60,25);
			
			textarea.setBackground(Color.white);
			remindtime.setFont(new Font(Font.DIALOG,Font.ROMAN_BASELINE,12));
			cancel.setBackground(Color.pink);
			cancel.setFont(new Font(Font.DIALOG,Font.ROMAN_BASELINE,12));
			cancel.setBorder(BorderFactory.createMatteBorder(1, 1, 1,1, Color.white));
			delete.setBackground(Color.pink);
			delete.setFont(new Font(Font.DIALOG,Font.ROMAN_BASELINE,12));
			delete.setBorder(BorderFactory.createMatteBorder(1, 1, 1,1, Color.white));
			save.setBackground(Color.pink);
			save.setFont(new Font(Font.DIALOG,Font.ROMAN_BASELINE,12));
			save.setBorder(BorderFactory.createMatteBorder(1, 1, 1,1, Color.white));
			modify.setBackground(Color.pink);
			modify.setFont(new Font(Font.DIALOG,Font.ROMAN_BASELINE,12));
			modify.setBorder(BorderFactory.createMatteBorder(1, 1, 1,1, Color.white));
			exit.setBackground(Color.pink);
			exit.setFont(new Font(Font.DIALOG,Font.ROMAN_BASELINE,12));
			exit.setBorder(BorderFactory.createMatteBorder(1, 1, 1,1, Color.white));
		}
		private void init() throws IOException {
			//如果出现了weekFrame窗口，则设置mycalendar的nextmonth和lastmonth按钮无效
			MyCalendar.nextMonth.setEnabled(false);
			MyCalendar.lastMonth.setEnabled(false);
			this.setLayout(null);
			cancel.setVisible(false);//放在最开始不影响后面
			design();
			Cancel();
			Save();
			Exit();
			Modify();
			Delete();
			Text();
			time();
			this.add(cancel);
			this.add(remindtime);
			this.add(save);
			this.add(modify);
			this.add(delete);
			this.add(exit);
			this.add(textarea);
			this.setTitle("Momo of "+year+"/"+month+"/"+day);
			this.setLocation(800, 50);
			this.setSize(300,400);
			this.setVisible(true);
			this.setResizable(false);
		}
		public WeekFrame(String name,int Y,int M,int D) {
			this.name=name;
			this.year=Y;
			this.month=M;
			this.day=D;
			try {
				init();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			this.addWindowListener(new WindowAdapter() {//给右上角的X添加事件
				public void windowClosing(WindowEvent e) {
				    closeaction(e);
				}
			});
		}
		public void closeaction(WindowEvent e) {
			this.setVisible(false);
		}
}
