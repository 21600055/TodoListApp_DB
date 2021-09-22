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
		
		String title, desc;
		Scanner sc = new Scanner(System.in);
		
		System.out.println("\n"
				+ "========== 리스트 추가하기\n"
				+ "제목을 입력하세요\n");
		
		title = sc.next();
		if (list.isDuplicate(title)) {
			System.out.println("제목은 중복될수 없습니다.");
			return;
		}
		
		sc.nextLine();
		System.out.println("내용을 입력하세요");
		desc = sc.nextLine().trim();
		
		TodoItem t = new TodoItem(title, desc);
		list.addItem(t);
		System.out.println("추가되었습니다.");
	}

	public static void deleteItem(TodoList l) {
		
		Scanner sc = new Scanner(System.in);
		
		System.out.println("\n"
				+ "========== 리스트 삭제하기\n"
				+ "삭제할 리스트의 제목을 입력하세요\n"
				+ "\n");
		
		String title = sc.next();
		
		for (TodoItem item : l.getList()) {
			if (title.equals(item.getTitle())) {
				l.deleteItem(item);
				System.out.println("삭제되었습니다.");
				break;
			}
		}
	}


	public static void updateItem(TodoList l) {
		
		Scanner sc = new Scanner(System.in);
		
		System.out.println("\n"
				+ "========== 리스트 수정하기\n"
				+ "업데이트 하고싶은 리스트의 제목을 입력하세요\n"
				+ "\n");
		String title = sc.next().trim();
		if (!l.isDuplicate(title)) {
			System.out.println("제목이 존재하지 않습니다.");
			return;
		}

		System.out.println("새로운 제목을 입력하세요");
		String new_title = sc.next().trim();
		if (l.isDuplicate(new_title)) {
			System.out.println("제목은 중복될수 없습니다.");
			return;
		}
		
		sc.nextLine();
		System.out.println("새로운 내용을 입력하세요 ");
		String new_description = sc.nextLine().trim(); 
		for (TodoItem item : l.getList()) {
			if (item.getTitle().equals(title)) {
				l.deleteItem(item);
				TodoItem t = new TodoItem(new_title, new_description);
				l.addItem(t);
				System.out.println("수정되었습니다.");
			}
		}
	}

	public static void listAll(TodoList l) {
		System.out.println("전체 리스트");
		for (TodoItem item : l.getList()) {
			System.out.println(item.toString());
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
				String title = st.nextToken();
				String desc = st.nextToken();
				String date = st.nextToken();
				
				TodoItem t = new TodoItem(title,desc);
				t.setCurrent_date(date);
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
