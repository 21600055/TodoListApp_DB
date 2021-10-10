package com.todo.service;

import java.util.*;

import com.todo.dao.TodoItem;
import com.todo.dao.TodoList;

public class TodoUtil {
	
	public static void createItem(TodoList list) {
		
		System.out.println("[항목 추가]");
		
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
		
		if(list.addItem(t)>0)
			System.out.println("추가되었습니다.");
	}

	public static void deleteItem(TodoList l) {
		
		Scanner sc = new Scanner(System.in);
		
		System.out.println("\n"
				+ "========== 리스트 삭제하기 ==========\n"
				+ "삭제할 항목의 번호를 입력하세요\n");
		
		int num  = sc.nextInt();
		if(l.deleteItem(num)>0)
			System.out.println("삭제되었습니다.");
	}


	public static void updateItem(TodoList l) {
		
		Scanner sc = new Scanner(System.in);
		
		System.out.println("\n"
				+ "========== 리스트 수정하기 ==========\n"
				+ "수정할 항목의 번호를 입력하세요 >\n");
		
		int num  = sc.nextInt();
		
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
		
		TodoItem item = new TodoItem(category,title,new_description,due_date);
		item.setId(num);
		if(l.editItem(item)>0)
		System.out.println("수정되었습니다.");
	}

	public static void listAll(TodoList l) {
		System.out.println("전체 리스트, 총 "+l.getCount()+"개");
		
		for (TodoItem item : l.getList()) {
			System.out.println(item.toString());
		}
	}
	
	public static void listAll(TodoList l,String order,int ordering) {
		System.out.println("전체 리스트, 총 "+l.getCount()+"개");
		
		for (TodoItem item : l.getOrderedList(order,ordering)) {
			System.out.println(item.toString());
		}
	}
	
	public static void findList(TodoList l,String keyword) {
		int count = 0;
		for(TodoItem item: l.getList(keyword)) {
			System.out.println(item.toString());	
			count++;
		}
		System.out.printf("총 %d개의 항목을 찾았습니다.\n",count);
	}
	
	public static void listCateAll(TodoList l) {
		int count = 0;
		for(String item:l.getCategories()) {
			System.out.print(item+" ");
			count++;
		}
		System.out.println();
		System.out.println("총 "+count+"개의 카테고리가 등록되어 있습니다.");
	}
	
	public static void findCateList(TodoList l,String cate) {
		int count = 0;
		for (TodoItem item : l.getListCategory(cate)) {
			System.out.println(item.toString());
			count++;
		}
		System.out.println("총"+count+"개의 항목을 찾았습니다.");
	}
	
	public static void comp(TodoList l,String number) {
		if(l.complete(number)>0)
			System.out.println("완료 체크했습니다.");
	}
	
	public static void listComp(TodoList l) {
		for (TodoItem item : l.getComp()) {
			System.out.println(item.toString());
		}
	}
}
