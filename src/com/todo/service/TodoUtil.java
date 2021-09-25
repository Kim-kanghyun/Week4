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
		
		System.out.print("[항목 추가]\n"
				+ "제목 > ");
		
		title = sc.next();
		if (list.isDuplicate(title)) {
			System.out.println("제목이 중복됩니다!");
			return;
		}
		
		System.out.println("카테고리 > ");
		category=sc.next();
		sc.nextLine();
		System.out.print("내용 > ");
		desc = sc.nextLine().trim();
		System.out.println("마감일자 > ");
		due_date=sc.next();
		TodoItem t = new TodoItem(title, desc, category,due_date);
		list.addItem(t);
		System.out.println("추가되었습니다.");
	}

	public static void deleteItem(TodoList l) {
		
		Scanner sc = new Scanner(System.in);
		
		
		System.out.print("[항목 삭제]\n"
				+ "삭제할 항목의 번호를 입력하시오 > ");
		int num = sc.nextInt();
		for (TodoItem item : l.getList()) {
			if (l.indexOf(item)==num-1) {
				l.deleteItem(item);
				System.out.println("삭제되었습니다.");
				break;
			}
		}
	}


	public static void updateItem(TodoList l) {
		
		Scanner sc = new Scanner(System.in);
		System.out.print("[항목 수정]\n"
				+ "수정할 항목의 번호를 입력하시오 > ");
		int num= sc.nextInt();

		System.out.println("새 제목 > ");
		String new_title = sc.next();
		if (l.isDuplicate(new_title)) {
			System.out.println("제목이 중복됩니다!");
			return;
		}
		System.out.println("새 카테고리 > ");
		String new_category = sc.next();
		sc.nextLine();
		System.out.println("새 내용 > ");
		String new_description = sc.nextLine().trim();
		System.out.println("새 마감일자 > ");
		String new_due_date = sc.next().trim();
		for (TodoItem item : l.getList()) {
			if (l.indexOf(item)==num-1) {
				l.deleteItem(item);
				TodoItem t = new TodoItem(new_title, new_description,new_category,new_due_date);
				l.addItem(t);
				System.out.println("수정되었습니다.");
			}
		}

	}

	public static void listAll(TodoList l) {
		System.out.println("[전체 목록, 총 "+l.getSize()+"개]");
		
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
		System.out.println("총 "+i+"개의 카테고리가 등록되어 있습니다.");
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
			System.out.println(count+"개의 항목을 읽었습니다.");
		}
		catch(FileNotFoundException e){
			System.out.println(filename+"파일이 없습니다.");
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		
	}
	
    
	
}
