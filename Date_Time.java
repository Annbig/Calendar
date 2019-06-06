package com.calendar;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.RandomAccessFile;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
* @author 作者 E-mail:
* @version 创建时间：2019年5月31日 上午11:35:03
* 类说明
*/
public class Date_Time {
//该类存储date-time对象(索引)，和Remind.txt文件绑定
	String date;
	Timing time;
	static File remindf=new File("Reminders.txt");
	//构造函数
	public Date_Time(String D,Timing T) throws IOException, ParseException {
		if(!remindf.exists()) {
			remindf.createNewFile();//不存在则创建
		}
		date=D;
		time=T;
		add();//每创建一个对象，将这个对象添加如remindtxt文件中
		if(getTiming().equalnow()) {//如果设置的时间就是当前的分钟
			Date_Time dt=new Date_Time(date);
		    if(dt.Exist()) {
				Text t;
				t = new Text(date);
				String mesg =t.getcontent();
				new RemindFrame(mesg);
		    }
		    return ;//不执行后面的
		}
		//添加线程
		SimpleDateFormat formatter =new SimpleDateFormat ("yy/MM/dd hh:mm:ss");
	    String second=":00";
	    String aLine;
	    aLine=getTiming().toString();
    	aLine=aLine+second;
    	Date DATE=formatter.parse(aLine);
    	Date nowtime=new Date();
    	long interval=DATE.getTime()-nowtime.getTime()+1;//求出时间间隔
		Runnable runnable = new Runnable() {
		   public void run() {
				 try {
					    Thread.sleep(interval);
					    //如果在经过时间间隔之后，文本文件中没有记录就不执行
					    Date_Time dt=new Date_Time(date);
					    if(dt.Exist()) {
							Text t;
							t = new Text(date);
							String mesg =t.getcontent();
							new RemindFrame(mesg);
					    }
				    } catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				    }
			}
		  };
		 Thread thread = new Thread(runnable);
		 if(interval>0)
			 thread.start();
	}
	public Date_Time(String D) throws IOException {//专门为Exist设置的构造方法
		if(!remindf.exists()) {
			remindf.createNewFile();//不存在则创建
		}
		date=D;
	}
	private void add() throws IOException {//向Remind.txt文件中添加Date_time
		if(Exist()) write("addold");//将旧的替换
		else write("addnew");//写入新的
	}
	public void delete() throws IOException{//找到date所在的那一行并且删除哪一行的内容
		write("delete");
	}
	static public String[] DateList() throws IOException {//提取所有的date并且放在字符串列表中
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(remindf)));
        String str=null;
        ArrayList<String> list = new ArrayList<>();
        while((str = br.readLine()) != null)
        {  
        	 String []split=str.split(" ");
        	 list.add(split[0]);//选最前面的date存储
        }
        br.close();
        int l=list.size();
        String []L=new String[l];
        for(int i=0;i<l;i++) {
        	L[i]=list.get(i);
        }
        return L;
	}
	public void write(String choose) throws IOException {//在date处 重新写入time
		// TODO Auto-generated method stub
		FileInputStream fis=new FileInputStream(remindf);
		InputStreamReader isr=new InputStreamReader(fis);
        BufferedReader br = new BufferedReader(isr);
        String str=null;
        String S=date;//可能只知道date，所以构造函数只创建了date
        if(time!=null) S=date+" "+time.toString();
        //System.out.println(S);
        ArrayList<String> list = new ArrayList<>();
        if(choose.equalsIgnoreCase("addnew"))//addnew：如果原来没有就放在文件最前面
        	list.add(S);
        while((str = br.readLine()) != null)  //要求替代原来name和name之间的内容
        {  
        	if(str.startsWith(date)) {
        		if(choose.equalsIgnoreCase("addold"))//addold：如果这一行以date开头，则改变这一行的内容
        			list.add(S);
        		else if(choose.equalsIgnoreCase("delete")) ;//delete：如果是要删除就不添加这一行
        	}
        	else
        		list.add(str);
        }
        fis.close();
        rewrite(list);
	}
	private void rewrite(ArrayList<String> list) throws IOException {//将ArrayList中的内容重新写回文本
        FileOutputStream fos = new FileOutputStream(remindf); 
        OutputStreamWriter osw = new OutputStreamWriter(fos);
        BufferedWriter bw = new BufferedWriter(osw); 
        if(list.size() != 0)
            for (String string : list) {
                bw.write(string);
                bw.newLine();
            }
        else if(list.size() == 0) {
            bw.write("");
        }
        bw.flush();
        bw.close();
	}
	public boolean Exist() throws IOException {//如果date这一天的索引存在
		// TODO Auto-generated method stub
		FileInputStream fis=new FileInputStream(remindf);
		InputStreamReader isr=new InputStreamReader(fis);
        BufferedReader br = new BufferedReader(isr);
        String str=null;
        while((str = br.readLine()) != null)
        {  
        	if(str.startsWith(date)) {
        		br.close();
        		fis.close();
        		return true;
        	}
        }
        fis.close();
		return false;
	}
	public Timing getTiming() throws IOException {//返回date对应的Timing对象(已经确认有date对应的行了)
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(remindf)));
        String str=null;
        while((str = br.readLine()) != null)
        {  
        	if(str.startsWith(date)) {
        		break;
        	}
        }
        br.close();
        //对str进行处理，提取出其中的Timing那一行并且转化为Timing对象
        String []s=str.split(" ");
        String yearmonthday=s[1];
        String hourminute=s[2];
        String []MD=yearmonthday.split("/");// 2016/6/3
        String []hm=hourminute.split(":");// 12:53
        int Y,M,D,h,m;
        Y=Integer.parseInt(MD[0]);
		M=Integer.parseInt(MD[1]);
		D=Integer.parseInt(MD[2]);
		h=Integer.parseInt(hm[0]);
		m=Integer.parseInt(hm[1]);
		Timing time=new Timing(Y,M,D,h,m);
		return time;
	}
}
