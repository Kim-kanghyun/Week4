package com.todo.service;

import java.util.*;
import java.io.FileWriter;
import java.io.IOException;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileNotFoundException;


import com.todo.dao.TodoItem;
import com.todo.dao.TodoList;

public class TodoUtil {
	
	public static void createItem(TodoList list) {
		
		String title, desc, category, due_date;
		Scanner sc = new Scanner(System.in);
		
		System.out.print("[�׸� �߰�]\n"
				+ "���� > ");
		
		title = sc.next();
		if (list.isDuplicate(title)) {
			System.out.println("������ �ߺ��˴ϴ�!");
			return;
		}
		
		System.out.println("ī�װ� > ");
		category=sc.next();
		sc.nextLine();
		System.out.print("���� > ");
		desc = sc.nextLine().trim();
		System.out.println("�������� > ");
		due_date=sc.next();
		TodoItem t = new TodoItem(title, desc, category,due_date);
		list.addItem(t);
		System.out.println("�߰��Ǿ����ϴ�.");
	}

	public static void deleteItem(TodoList l) {
		
		Scanner sc = new Scanner(System.in);
		
		
		System.out.print("[�׸� ����]\n"
				+ "������ �׸��� ��ȣ�� �Է��Ͻÿ� > ");
		int num = sc.nextInt();
		for (TodoItem item : l.getList()) {
			if (l.indexOf(item)==num-1) {
				l.deleteItem(item);
				System.out.println("�����Ǿ����ϴ�.");
				break;
			}
		}
	}


	public static void updateItem(TodoList l) {
		
		Scanner sc = new Scanner(System.in);
		System.out.print("[�׸� ����]\n"
				+ "������ �׸��� ��ȣ�� �Է��Ͻÿ� > ");
		int num= sc.nextInt();

		System.out.println("�� ���� > ");
		String new_title = sc.next();
		if (l.isDuplicate(new_title)) {
			System.out.println("������ �ߺ��˴ϴ�!");
			return;
		}
		System.out.println("�� ī�װ� > ");
		String new_category = sc.next();
		sc.nextLine();
		System.out.println("�� ���� > ");
		String new_description = sc.nextLine().trim();
		System.out.println("�� �������� > ");
		String new_due_date = sc.next().trim();
		for (TodoItem item : l.getList()) {
			if (l.indexOf(item)==num-1) {
				l.deleteItem(item);
				TodoItem t = new TodoItem(new_title, new_description,new_category,new_due_date);
				l.addItem(t);
				System.out.println("�����Ǿ����ϴ�.");
			}
		}

	}

	public static void listAll(TodoList l) {
		System.out.println("[��ü ���, �� "+l.getSize()+"��]");
		
		int count=1;
		for (TodoItem item : l.getList()) {
			System.out.print(count+". ");
			System.out.println(item.toString());
			count++;
		}
	}
	
	public static void cate(TodoList l) {
		Set hashSet=new HashSet();
		int i=0;
		for(TodoItem myitem : l.getList()) {
			hashSet.add(myitem.getCategory());
		}
		Iterator it=hashSet.iterator();
		while(it.hasNext()){
			System.out.print(it.next());
			if(it.hasNext()==false) {
				System.out.println();
			}
			else {
				System.out.print(" / ");
			}
			i++;
		}
		System.out.println("�� "+i+"���� ī�װ��� ��ϵǾ� �ֽ��ϴ�.");
	}

	public static void saveList(TodoList l,String filename) {
		for(TodoItem myitem : l.getList()) {
			try(FileWriter fo= new FileWriter(filename,true)){
				fo.write(myitem.toSaveString());
			}
			catch(IOException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	public static void findWord(TodoList l,String str) {
		for(TodoItem myitem : l.getList()) {
			if(myitem.toString().contains(str)) {
				System.out.print((l.indexOf(myitem)+1)+". ");
				System.out.println(myitem.toString());
			}
		}
	}
	public static void findCategory(TodoList l,String str) {
		for(TodoItem myitem : l.getList()) {
			if(myitem.getCategory().contains(str)) {
				System.out.print((l.indexOf(myitem)+1)+". ");
				System.out.println(myitem.toString());
			}
		}
	}
	
	
	public static void loadList(TodoList l,String filename) {
		try {
			BufferedReader reader = new BufferedReader(new FileReader(filename));
			String str;
			int count=0;
			while ((str = reader.readLine()) != null) {
				StringTokenizer st=new StringTokenizer(str,"##");
				String title, desc,current_date,category,due_date;
				title = st.nextToken();
				category=st.nextToken();
				desc= st.nextToken();
				due_date=st.nextToken();
				current_date=st.nextToken();
				TodoItem t = new TodoItem(title, desc,category,due_date);
				t.setCurrent_date(current_date);
				l.addItem(t);
				count++;
				}
			reader.close();
			System.out.println(count+"���� �׸��� �о����ϴ�.");
		}
		catch(FileNotFoundException e){
			System.out.println(filename+"������ �����ϴ�.");
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		
	}
	
    
	
}
