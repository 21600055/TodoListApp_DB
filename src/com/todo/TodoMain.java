package com.todo;

import java.util.Scanner;

import com.todo.dao.TodoList;
import com.todo.menu.Menu;
import com.todo.service.TodoUtil;

public class TodoMain {
	
	public static void start() {
		
		Scanner sc = new Scanner(System.in);
		TodoList l = new TodoList();
		boolean isList = false;
		boolean quit = false;
		Menu.displaymenu();
		if(l.getCount()==0) l.importdata("todolist.txt"); 
		
		do {
			Menu.prompt();
			isList = false;
			
			String choice = sc.nextLine();
			String[] schoice = choice.split(" ");
			switch (schoice[0]) {

			case "add":
				TodoUtil.createItem(l);
				break;
			
			case "del":
				TodoUtil.deleteItem(l);
				break;
				
			case "edit":
				TodoUtil.updateItem(l);
				break;
				
			case "ls":
				TodoUtil.listAll(l);
				break;

			case "ls_name_asc":
				System.out.println("이름 오름차순 정렬 되었습니다.");
				TodoUtil.listAll(l,"title",1);
				break;

			case "ls_name_desc":
				System.out.println("이름 내림차순 정렬 되었습니다.");
				TodoUtil.listAll(l,"title",0);
				break;
				
			case "ls_date":
				System.out.println("날짜순 정렬 되었습니다.");
				TodoUtil.listAll(l,"due_date",1);
				break;
				
			case "ls_date_desc":
				System.out.println("날짜 내림차순 정렬 되었습니다.");
				TodoUtil.listAll(l,"due_date",0);
				break;
				
			case "find":
				String keyword = schoice[1];
				TodoUtil.findList(l,keyword);
				break;
			
			case "find_cate":
				TodoUtil.findCateList(l,schoice[1]);
				break;
			
			case "ls_cate":
				TodoUtil.listCateAll(l);
				break;
				
			case "comp":
				TodoUtil.comp(l,schoice[1]);
				break;
			case "ls_comp":
				TodoUtil.listComp(l);
				break;
				
			case "help":
				Menu.displaymenu();
				break;
				
			case "exit":
				quit = true;
				break;

			default:
				System.out.println("메뉴중 하나를 선택해주세요 - 도움말( help )");
				break;
			}
			
			if(isList) l.listAll();
		} while (!quit);
		sc.close();
	}
}