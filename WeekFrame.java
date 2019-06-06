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
* @author ���� E-mail:
* @version ����ʱ�䣺2019��5��30�� ����7:48:06
* ��˵��
*/
//�޸ģ�ɾ��������i�����캯��Ҳɾ���˶�Ӧ��i
public class WeekFrame extends Frame{
		int year;
		int month;
		int day;
		String name;
		JButton save=new JButton();//���水ť
		JButton modify=new JButton();//�������Ӱ�ť
		JButton delete=new JButton();//ɾ�����ݰ�ť
		JButton exit=new JButton();//�˳��ӽ��水ť
		JButton cancel=new JButton();//ȡ�����Ѱ�ť����������ʱ�����
		TextArea textarea=new TextArea();//�ı�������ʾ���ݲ������޸�
		Label remindtime=new Label();//�������������ʾ
		public void time() throws IOException {
			cancel.setVisible(false);//����Ϊ
			remindtime.setText("");
			String str;
			String date=year+""+month+""+day;
			Date_Time dt=new Date_Time(date);
			if(dt.Exist()) {
				//�����date��¼
				str ="alarm: "+dt.getTiming().toString();
				remindtime.setText(str);
				cancel.setVisible(true);
			}
			else //���û��
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
						MyCalendar.Setremind();//���±���¼������
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					//action(e);//�رյ�ǰ����
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
			MyCalendar.nextMonth.setEnabled(true);//����������ť�ָ�����
			MyCalendar.lastMonth.setEnabled(true);
			this.setVisible(false);
		}
		public void Modify() {
			modify.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					String str=textarea.getText();
					if(str.equalsIgnoreCase("") || str.equalsIgnoreCase("��ǰû����Ҫ���ѵ��¼�"))//���û��д�����Ļ�
						JOptionPane.showMessageDialog(null, "No Content!", "Warning!", JOptionPane.PLAIN_MESSAGE);//����
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
	//�ı�����ͺ�̨��¼��Ҫ���
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					textarea.setText("��ǰû����Ҫ���ѵ��¼�");
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
				textarea.setText("��ǰû����Ҫ���ѵ��¼�");
			else textarea.setText(s);
		}
		public void Cancel() throws IOException {//ȡ�����յ�����
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
					cancel.setVisible(false);//Ȼ��ť��������
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
			textarea.setText("��ǰû����Ҫ���ѵ��¼�");
			
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
			//���������weekFrame���ڣ�������mycalendar��nextmonth��lastmonth��ť��Ч
			MyCalendar.nextMonth.setEnabled(false);
			MyCalendar.lastMonth.setEnabled(false);
			this.setLayout(null);
			cancel.setVisible(false);//�����ʼ��Ӱ�����
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
			this.addWindowListener(new WindowAdapter() {//�����Ͻǵ�X����¼�
				public void windowClosing(WindowEvent e) {
				    closeaction(e);
				}
			});
		}
		public void closeaction(WindowEvent e) {
			this.setVisible(false);
		}
}
