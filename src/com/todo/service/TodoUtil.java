package com.todo.service;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.*;

import com.todo.dao.TodoItem;
import com.todo.dao.TodoList;

public class TodoUtil {
	
	public static void createItem(TodoList list) {
		
		String category,title, desc,due_date;
		Scanner sc = new Scanner(System.in);
		
		System.out.print("========== 리스트 추가하기 =========\n"+"카테고리 > ");
		category = sc.next();
		sc.nextLine();
		
		System.out.print("제목 > ");
		title = sc.nextLine();
		if (list.isDuplicate(title)) {
			System.out.println("제목은 중복될수 없습니다.");
			return;
		}
		
		System.out.print("내용 > ");
		desc = sc.nextLine().trim();
		
		System.out.print("마감일자(yyyy/mm/dd) > ");
		due_date = sc.nextLine().trim();
		
		TodoItem t = new TodoItem(category,title,desc,due_date);
		list.addItem(t);
		System.out.println("추가되었습니다.");
	}

	public static void deleteItem(TodoList l) {
		
		Scanner sc = new Scanner(System.in);
		
		System.out.println("\n"
				+ "========== 리스트 삭제하기 ==========\n"
				+ "삭제할 항목의 번호를 입력하세요\n");
		
		int num  = sc.nextInt();
		TodoItem t = l.getList().get(num-1);
		System.out.println(num+". "+t.toString());
		System.out.println("위 항목을 삭제하시겠습니까? (y/n) > ");
		
		String ans = sc.next();
		if(ans.equals("y")) {
			l.deleteItem(t);
			System.out.println("삭제되었습니다.");
		} else {
			System.out.println("삭제를 취소했습니다.");
		}
	}


	public static void updateItem(TodoList l) {
		
		Scanner sc = new Scanner(System.in);
		
		System.out.println("\n"
				+ "========== 리스트 수정하기 ==========\n"
				+ "수정할 항목의 번호를 입력하세요\n");
		
		int num  = sc.nextInt();
		TodoItem t = l.getList().get(num-1);
		System.out.println(num+". "+t.toString());
		
		System.out.print("새 카테고리 > ");
		String category = sc.next();
		sc.nextLine();
		
		System.out.print("새 제목 > ");
		String title = sc.nextLine().trim();
		if (l.isDuplicate(title)) {
			System.out.println("제목은 중복될수 없습니다.");
			return;
		}
		
		System.out.print("새 내용 > ");
		String new_description = sc.nextLine().trim(); 

		System.out.print("새 마감일자(yyyy/mm/dd) > ");
		String due_date = sc.nextLine().trim();
		
		l.deleteItem(t);
		TodoItem item = new TodoItem(category,title,new_description,due_date);
		l.addItem(item);
		System.out.println("수정되었습니다.");
	}

	public static void listAll(TodoList l) {
		System.out.println("전체 리스트, 총 "+l.getlsize()+"개");
		int ct = 1;
		for (TodoItem item : l.getList()) {
			System.out.println(ct+". "+item.toString());
			ct++;
		}
	}
	
	public static void saveList(TodoList l,String filename) {
		
		try {
			Writer w = new FileWriter(filename);
			
			for(TodoItem item:l.getList()) {
				w.write(item.toSaveString());
			}
			w.close();
			System.out.print("정보 저장 되었습니다.");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void loadList(TodoList l,String filename) {
		
		try {
			BufferedReader br = new BufferedReader(new FileReader(filename));
			String line;
			
			while((line = br.readLine())!=null) {
				StringTokenizer st = new StringTokenizer(line,"##");
				String category = st.nextToken();
				String title = st.nextToken();
				String desc = st.nextToken();
				String due_date = st.nextToken();
				String current_date = st.nextToken();
				
				TodoItem t = new TodoItem(category,title,desc,due_date);
				t.setCurrent_date(current_date);
				l.addItem(t);
			}
			br.close();
			System.out.println("정보 로드 완료");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("파일을 찾을수 없으니 새로 만들겠습니다.");
			//e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
