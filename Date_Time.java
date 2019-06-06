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
* @author ���� E-mail:
* @version ����ʱ�䣺2019��5��31�� ����11:35:03
* ��˵��
*/
public class Date_Time {
//����洢date-time����(����)����Remind.txt�ļ���
	String date;
	Timing time;
	static File remindf=new File("Reminders.txt");
	//���캯��
	public Date_Time(String D,Timing T) throws IOException, ParseException {
		if(!remindf.exists()) {
			remindf.createNewFile();//�������򴴽�
		}
		date=D;
		time=T;
		add();//ÿ����һ�����󣬽�������������remindtxt�ļ���
		if(getTiming().equalnow()) {//������õ�ʱ����ǵ�ǰ�ķ���
			Date_Time dt=new Date_Time(date);
		    if(dt.Exist()) {
				Text t;
				t = new Text(date);
				String mesg =t.getcontent();
				new RemindFrame(mesg);
		    }
		    return ;//��ִ�к����
		}
		//����߳�
		SimpleDateFormat formatter =new SimpleDateFormat ("yy/MM/dd hh:mm:ss");
	    String second=":00";
	    String aLine;
	    aLine=getTiming().toString();
    	aLine=aLine+second;
    	Date DATE=formatter.parse(aLine);
    	Date nowtime=new Date();
    	long interval=DATE.getTime()-nowtime.getTime()+1;//���ʱ����
		Runnable runnable = new Runnable() {
		   public void run() {
				 try {
					    Thread.sleep(interval);
					    //����ھ���ʱ����֮���ı��ļ���û�м�¼�Ͳ�ִ��
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
	public Date_Time(String D) throws IOException {//ר��ΪExist���õĹ��췽��
		if(!remindf.exists()) {
			remindf.createNewFile();//�������򴴽�
		}
		date=D;
	}
	private void add() throws IOException {//��Remind.txt�ļ������Date_time
		if(Exist()) write("addold");//���ɵ��滻
		else write("addnew");//д���µ�
	}
	public void delete() throws IOException{//�ҵ�date���ڵ���һ�в���ɾ����һ�е�����
		write("delete");
	}
	static public String[] DateList() throws IOException {//��ȡ���е�date���ҷ����ַ����б���
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(remindf)));
        String str=null;
        ArrayList<String> list = new ArrayList<>();
        while((str = br.readLine()) != null)
        {  
        	 String []split=str.split(" ");
        	 list.add(split[0]);//ѡ��ǰ���date�洢
        }
        br.close();
        int l=list.size();
        String []L=new String[l];
        for(int i=0;i<l;i++) {
        	L[i]=list.get(i);
        }
        return L;
	}
	public void write(String choose) throws IOException {//��date�� ����д��time
		// TODO Auto-generated method stub
		FileInputStream fis=new FileInputStream(remindf);
		InputStreamReader isr=new InputStreamReader(fis);
        BufferedReader br = new BufferedReader(isr);
        String str=null;
        String S=date;//����ֻ֪��date�����Թ��캯��ֻ������date
        if(time!=null) S=date+" "+time.toString();
        //System.out.println(S);
        ArrayList<String> list = new ArrayList<>();
        if(choose.equalsIgnoreCase("addnew"))//addnew�����ԭ��û�оͷ����ļ���ǰ��
        	list.add(S);
        while((str = br.readLine()) != null)  //Ҫ�����ԭ��name��name֮�������
        {  
        	if(str.startsWith(date)) {
        		if(choose.equalsIgnoreCase("addold"))//addold�������һ����date��ͷ����ı���һ�е�����
        			list.add(S);
        		else if(choose.equalsIgnoreCase("delete")) ;//delete�������Ҫɾ���Ͳ������һ��
        	}
        	else
        		list.add(str);
        }
        fis.close();
        rewrite(list);
	}
	private void rewrite(ArrayList<String> list) throws IOException {//��ArrayList�е���������д���ı�
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
	public boolean Exist() throws IOException {//���date��һ�����������
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
	public Timing getTiming() throws IOException {//����date��Ӧ��Timing����(�Ѿ�ȷ����date��Ӧ������)
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(remindf)));
        String str=null;
        while((str = br.readLine()) != null)
        {  
        	if(str.startsWith(date)) {
        		break;
        	}
        }
        br.close();
        //��str���д�����ȡ�����е�Timing��һ�в���ת��ΪTiming����
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
