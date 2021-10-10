package com.todo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Scanner;

import com.todo.dao.TodoList;
import com.todo.menu.Menu;
import com.todo.service.TodoUtil;

public class TodoMain {
	
	static Connection con = null;
	
	public static void start() {
	
		connection();
		Scanner sc = new Scanner(System.in);
		TodoList l = new TodoList();
		boolean isList = false;
		boolean quit = false;
		Menu.displaymenu();
		
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
				l.sortByName();
				System.out.println("이름 오름차순 정렬 되었습니다.");
				isList = true;
				break;

			case "ls_name_desc":
				l.sortByName();
				l.reverseList();
				System.out.println("이름 내림차순 정렬 되었습니다.");
				isList = true;
				break;
				
			case "ls_date":
				l.sortByDate();
				System.out.println("날짜 정렬 되었습니다.");
				isList = true;
				break;

			case "find":
				l.find(schoice);
				break;
			
			case "find_cate":
				l.find_cate(schoice);
				break;
			
			case "ls_cate":
				l.ls_cate();
				break;
				
			case "ls_date_desc":
				l.sortByDate();
				l.reverseList();
				System.out.println("날짜 역순 정렬 되었습니다.");
				isList = true;
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
		TodoUtil.saveList(l, "todolist.txt");
		sc.close();
	}


	public static void connection() {
		try {
			Class.forName("org.sqlite.JDBC");
			String dbFile = "Todolist.db";
			con = DriverManager.getConnection("jdbc:sqlite:"+dbFile);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			// TODO Auto-generated catch block
			if(con!=null) {
				try {
					con.close();
				} catch(Exception e) {}
			}
		}
	}

}