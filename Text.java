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
* @author ���� E-mail:
* @version ����ʱ�䣺2019��6��1�� ����4:26:59
* ��˵��
*/
public class Text {
	//�������ڼ�¼ÿһ���������ڶ�Ӧ�ļ�¼����,ÿһ�������Ӧһ������-ʱ�����,��Date-Time�����һ�ޣ����洢��ʽ��ͬ
	String date;//����
	String content;//����
	static File f=new File("Calendar.txt");
	public Text(String D,String C) throws IOException {
		if(!f.exists()) {
			f.createNewFile();//�������򴴽�
		}
		date=D;
		content=C;
		if(C.equalsIgnoreCase("") || C.equalsIgnoreCase("��ǰû����Ҫ���ѵ��¼�")) delete();
		else add();//ÿ����һ�����󣬽�������������calendartxt�ļ���
	}
	public Text(String D) throws IOException {//ר��ΪExist���õĹ��췽��
		if(!f.exists()) {
			f.createNewFile();//�������򴴽�
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
		if(Exist()) write("addold");//���ɵ��滻
		else write("addnew");//д���µ�
	}
	public void delete() throws IOException{//�ҵ�date���ڵ���һ�������ɾ������
		write("delete");
	}
	public void write(String choose) throws IOException {
		// TODO Auto-generated method stub
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(f)));
		String str=null;
        String S=date;//����ֻ֪��date�����Թ��캯��ֻ������date
        ArrayList<String> list = new ArrayList<>();
        if(choose.equalsIgnoreCase("addnew")) {
        	//addnew�����ԭ��û�оͷ����ļ���ǰ��
        	list.add(S);
        	list.add(content);
        	list.add(S);
        }
        int count=1;
        while((str = br.readLine()) != null)  //Ҫ�����ԭ��date��date֮�������
        {  
        	if(!str.equalsIgnoreCase(date) && count==1)
        		list.add(str);
        	else if(str.equalsIgnoreCase(date) && count==1) {//��һ���ҵ�date
        		if(choose.equalsIgnoreCase("addold")) {//���Ҫ�ھɵ�����Ķ�
        			list.add(date);
        			list.add(content);
        			count=2;
        			continue;
        		}
        		else if(choose.equalsIgnoreCase("delete")) count=2;//���Ҫɾ���˼�¼,��ʲô������
        	}
        	else if(str.equalsIgnoreCase(date) && count==2) {//�ڶ����ҵ�date
        		if(choose.equalsIgnoreCase("addold")) {//���Ҫ�ھɵ�����Ķ�
        			list.add(date);
        			count=1;
        		}
        		else if(choose.equalsIgnoreCase("delete")) count=1;//���Ҫɾ���˼�¼,��ʲô������
        	}
        }
        br.close();
        rewrite(list);
	}
	private void rewrite(ArrayList<String> list) throws IOException {//��ArrayList�е���������д���ı�
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
	public String getcontent() throws IOException {//����date��Ӧ������(�Ѿ�ȷ����date��Ӧ������)
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(f)));
        String s="";
        String str=null;
        int count=1;
        while((str = br.readLine()) != null)  
        {  
            //���������name
            if(count==1 && str.equalsIgnoreCase(date)) {//��һ������date
            	count=2;
            }
            else if(count==2) {
            	if(str.equalsIgnoreCase(date)) {//�ڶ�������date
            		if(s==null || s=="\n") return "";//������name�м�û������
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
