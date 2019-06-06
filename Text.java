package com.calendar;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

/**
* @author 作者 E-mail:
* @version 创建时间：2019年6月1日 下午4:26:59
* 类说明
*/
public class Text {
	//该类用于记录每一个具体日期对应的记录事项,每一个对象对应一个日期-时间对象,与Date-Time类如出一辙，但存储形式不同
	String date;//日期
	String content;//内容
	static File f=new File("Calendar.txt");
	public Text(String D,String C) throws IOException {
		if(!f.exists()) {
			f.createNewFile();//不存在则创建
		}
		date=D;
		content=C;
		if(C.equalsIgnoreCase("") || C.equalsIgnoreCase("当前没有需要提醒的事件")) delete();
		else add();//每创建一个对象，将这个对象添加如calendartxt文件中
	}
	public Text(String D) throws IOException {//专门为Exist设置的构造方法
		if(!f.exists()) {
			f.createNewFile();//不存在则创建
		}
		date=D;
	}
	public boolean Exist() throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(f)));
        String str=null;
        while((str = br.readLine()) != null)
        {  
        	if(str.startsWith(date)) {
        		br.close();
        		return true;
        	}
        }
        br.close();
		return false;
	}
	public void add() throws IOException {
		// TODO Auto-generated method stub
		if(Exist()) write("addold");//将旧的替换
		else write("addnew");//写入新的
	}
	public void delete() throws IOException{//找到date所在的那一块儿并且删除内容
		write("delete");
	}
	public void write(String choose) throws IOException {
		// TODO Auto-generated method stub
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(f)));
		String str=null;
        String S=date;//可能只知道date，所以构造函数只创建了date
        ArrayList<String> list = new ArrayList<>();
        if(choose.equalsIgnoreCase("addnew")) {
        	//addnew：如果原来没有就放在文件最前面
        	list.add(S);
        	list.add(content);
        	list.add(S);
        }
        int count=1;
        while((str = br.readLine()) != null)  //要求替代原来date和date之间的内容
        {  
        	if(!str.equalsIgnoreCase(date) && count==1)
        		list.add(str);
        	else if(str.equalsIgnoreCase(date) && count==1) {//第一次找到date
        		if(choose.equalsIgnoreCase("addold")) {//如果要在旧的上面改动
        			list.add(date);
        			list.add(content);
        			count=2;
        			continue;
        		}
        		else if(choose.equalsIgnoreCase("delete")) count=2;//如果要删除此记录,就什么都不做
        	}
        	else if(str.equalsIgnoreCase(date) && count==2) {//第二次找到date
        		if(choose.equalsIgnoreCase("addold")) {//如果要在旧的上面改动
        			list.add(date);
        			count=1;
        		}
        		else if(choose.equalsIgnoreCase("delete")) count=1;//如果要删除此记录,就什么都不做
        	}
        }
        br.close();
        rewrite(list);
	}
	private void rewrite(ArrayList<String> list) throws IOException {//将ArrayList中的内容重新写回文本
        FileOutputStream fos = new FileOutputStream(f); 
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
	public String getcontent() throws IOException {//返回date对应的内容(已经确认有date对应的行了)
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(f)));
        String s="";
        String str=null;
        int count=1;
        while((str = br.readLine()) != null)  
        {  
            //如果改行是name
            if(count==1 && str.equalsIgnoreCase(date)) {//第一次碰到date
            	count=2;
            }
            else if(count==2) {
            	if(str.equalsIgnoreCase(date)) {//第二次碰到date
            		if(s==null || s=="\n") return "";//在两个name中间没有内容
            		count=1;
            		break;
            	}
            	else
            		s+=str+"\n";
            }
        }
        br.close();
        return s;
	}

}
