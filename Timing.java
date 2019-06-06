package com.calendar;
import java.util.Date; 
import java.util.Calendar;
import java.awt.Button;
import java.awt.Color;
import java.awt.Frame;
import java.awt.Label;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.text.SimpleDateFormat; 

/**
* @author 作者 E-mail:
* @version 创建时间：2019年5月16日 下午9:36:00
* 类说明
*/
public class Timing{
	  int year,month,day,hour,minute;
	  public Timing() {
		  month=0;
		  day=0;
		  hour=0;
		  minute=0;
		  year=0;
	  }
	  public void Setyear(int Y) {
		  year=Y;
	  }
	  public Timing(int M) {
		  month=M;
	  }
	  public Timing(int M,int D) {
		  month=M;
		  day=D;
	  }
	  public Timing(int Y,int M,int D) {
		  year=Y;
		  month=M;
		  day=D;
	  }
	  public Timing(int M,int D,int h,int m) {
		  month=M;
		  day=D;
		  hour=h;
		  minute=m;
	  }
	  public Timing(int Y,int M,int D,int h,int m) {
		  year=Y;
		  month=M;
		  day=D;
		  hour=h;
		  minute=m;
	  }
	  public static Timing StrToTiming(String s) {//将字符串转化为Timing对象
	  	int M,D,h,m;
		String []split=s.split("/");
		M=Integer.parseInt(split[0]);
		split=split[1].split(" ");
		D=Integer.parseInt(split[0]);
		split=split[1].split(":");
		h=Integer.parseInt(split[0]);
		m=Integer.parseInt(split[1]);
		Timing time=new Timing(M,D,h,m);
		return time;
	  }
	  public boolean equalnow() {
		  if(year==Calendar.getInstance().get(Calendar.YEAR))
			  if(month==(Calendar.getInstance().get(Calendar.MONTH)+1))
				  if(day==Calendar.getInstance().get(Calendar.DATE))
					  if(hour==Calendar.getInstance().get(Calendar.HOUR))
						  if(minute==Calendar.getInstance().get(Calendar.MINUTE))
							  return true;
		  return false;
	  }
	  public int firstWeekdayofMonth() {//月初所在的星期几
		  Calendar cal=Calendar.getInstance();
		  cal.set(year, month+1, 1);
		  return cal.get(Calendar.DAY_OF_WEEK)+1;
	  }
	  public String toString(){
		String s=year+"/"+month+"/"+day+" "+hour+":"+minute;
		return s;
	  }
      public static  String getTime() {
    	  SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
  		  return df.format(new Date());// new Date()为获取当前系统时间
      }
	  public String monthday() {
		// TODO Auto-generated method stub
		String []Month= {"January","February","March","April","May","June","July","Aguest","September","Octorber","November","December"};
		String s=Month[month-1];
		return s;
	  }
}
class RemindFrame extends Frame{//当到时间会跳出来的提示窗口
	String content;
	TextArea text=new TextArea();//显示注意
	Button exit=new Button();
	public void TEXT() throws IOException {
		text.setText(content);
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
		this.setVisible(false);
	}
	public void design() {
		text.setText("Error!!!");
		text.setBounds(20,40,360,100);
		text.setBackground(Color.white);
		text.setEditable(false);
		exit.setLabel("exit");
		exit.setBounds(300,150,60,30);
		exit.setBackground(Color.pink);
	}
	private void init() throws IOException {
		this.setLayout(null);
		design();
		TEXT();
		Exit();
		this.add(text);
		this.add(exit);
		this.setLocation(440, 300);
		this.setTitle("Reminding!");
		this.setSize(400,200);
		this.setVisible(true);
		this.setResizable(false);
	}
	public RemindFrame() {
		this.content="Error!";
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
	public RemindFrame(String content) {
		this.content=content;
		try {
			init();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}