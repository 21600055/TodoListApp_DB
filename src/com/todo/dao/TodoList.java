package com.todo.dao;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

import com.todo.DbConnect;
import com.todo.service.TodoSortByDate;
import com.todo.service.TodoSortByName;

public class TodoList {
	private List<TodoItem> list;
	Connection con;

	public Connection getCon() {
		return con;
	}

	public TodoList() {
		this.list = new ArrayList<TodoItem>();
		this.con = DbConnect.getConnection("Tododb.db");
	}

	public int addItem(TodoItem t) {
		if (cateexist(t.getCategory()))
			t.setCate(getcateid(t.getCategory()));
		else {
			addcate(t.getCategory());
			t.setCate(getcateid(t.getCategory()));
		}
		String sql = "insert into Todolist (title,memo,current_date,due_date,cate,star)" + "values(?,?,?,?,?,?);";
		PreparedStatement pstm;
		int count = 0;
		try {
			pstm = con.prepareStatement(sql);
			pstm.setString(1, t.getTitle());
			pstm.setString(2, t.getDesc());
			pstm.setString(3, t.getCurrent_date());
			pstm.setString(4, t.getDue_date());
			pstm.setInt(5, t.getCate());
			pstm.setInt(6,t.getStar());
			count = pstm.executeUpdate();
			pstm.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return count;
	}

	public void addcate(String category) { // 추가 메소드
		String sql = "insert into Category (name)" + "values(?)";
		PreparedStatement pstm;
		try {
			pstm = con.prepareStatement(sql);
			pstm.setString(1, category);
			pstm.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public int deleteItem(int index) { //삭제 메소드
		String sql = "delete from Todolist where id=?;";
		PreparedStatement pstm;
		int count = 0;

		try {
			pstm = con.prepareStatement(sql);
			pstm.setInt(1, index);
			count = pstm.executeUpdate();
			pstm.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;
	}

	public int editItem(TodoItem t) { // Update 메소드
		String sql = "Update Todolist set title=?, memo=? ,current_date=?, due_date=?, cate=?, star=?" + "where id = ?;";
		PreparedStatement pstm;
		if (cateexist(t.getCategory()))
			t.setCate(getcateid(t.getCategory()));
		else {
			addcate(t.getCategory());
			t.setCate(getcateid(t.getCategory()));
		}
		int count = 0;
		try {
			pstm = con.prepareStatement(sql);
			pstm.setString(1, t.getTitle());
			pstm.setString(2, t.getDesc());
			pstm.setString(3, t.getCurrent_date());
			pstm.setString(4, t.getDue_date());
			pstm.setInt(5, t.getCate());
			pstm.setInt(6, t.getStar());
			pstm.setInt(7, t.getId());
			count = pstm.executeUpdate();
			pstm.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;
	}

	public ArrayList<TodoItem> getList() { // 전체 list 출력
		ArrayList<TodoItem> list = new ArrayList<TodoItem>();
		Statement stmt;
		try {
			stmt = con.createStatement();
			String sql = "Select * From Todolist;";
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				int id = rs.getInt("id");
				String title = rs.getString("title");
				String description = rs.getString("memo");
				String due = rs.getString("due_date");
				String current_date = rs.getString("current_date");
				int is_complete = rs.getInt("complete");
				int star = rs.getInt("star");
				String category = getcatename(rs.getInt("cate"));
				TodoItem t = new TodoItem(category, title, description, due,star);
				t.setId(id);
				t.setCurrent_date(current_date);
				t.setIs_complete(is_complete);
				t.setCate(rs.getInt("cate"));
				list.add(t);
			}
			stmt.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	public ArrayList<TodoItem> getList(String keyword) { // 해당 keyword를 가진 record들 출력
		ArrayList<TodoItem> list = new ArrayList<TodoItem>();
		PreparedStatement pstm;
		keyword = "%" + keyword + "%";

		try {
			String sql = "Select * From Todolist Where title Like ? or memo like ?;";
			pstm = con.prepareStatement(sql);
			pstm.setString(1, keyword);
			pstm.setString(2, keyword);
			ResultSet rs = pstm.executeQuery();
			while (rs.next()) {
				int id = rs.getInt("id");
				String title = rs.getString("title");
				String description = rs.getString("memo");
				String due = rs.getString("due_date");
				String current_date = rs.getString("current_date");
				int is_complete = rs.getInt("complete");
				int star = rs.getInt("star");
				String category = getcatename(rs.getInt("cate"));
				TodoItem t = new TodoItem(category, title, description, due,star);
				t.setId(id);
				t.setCurrent_date(current_date);
				t.setIs_complete(is_complete);
				t.setCate(rs.getInt("cate"));
				list.add(t);
			}
			pstm.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	public int getCount() { //db record 개수 return
		Statement stmt;
		int count = 0;
		try {
			stmt = this.getCon().createStatement();
			String sql = "SELECT count(id) FROM Todolist;";
			ResultSet rs = stmt.executeQuery(sql);
			rs.next();
			count = rs.getInt("count(id)");
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}

	public ArrayList<String> getCategories() { // category 항목들 출력
		ArrayList<String> list = new ArrayList<String>();
		Statement stmt;
		try {
			stmt = con.createStatement();
			String sql = "Select Distinct name From Category";
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				String category = rs.getString("name");
				list.add(category);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	public ArrayList<TodoItem> getListCategory(String keyword) { //해당 category를 가진 record들 출력
		ArrayList<TodoItem> list = new ArrayList<TodoItem>();
		PreparedStatement pstm;
		int cid = getcateid(keyword);
		
		String sql = "SELECT * FROM Todolist Where cate = ?";
		try {
			pstm = con.prepareStatement(sql);
			pstm.setInt(1, cid);
			ResultSet rs = pstm.executeQuery();
			while (rs.next()) {
				int id = rs.getInt("id");
				String title = rs.getString("title");
				String description = rs.getString("memo");
				String due = rs.getString("due_date");
				String current_date = rs.getString("current_date");
				int is_complete = rs.getInt("complete");
				String category = getcatename(rs.getInt("cate"));
				int star = rs.getInt("star");
				TodoItem t = new TodoItem(category, title, description, due,star);
				t.setId(id);
				t.setCurrent_date(current_date);
				t.setIs_complete(is_complete);
				t.setCate(rs.getInt("cate"));
				list.add(t);
			}
			pstm.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	public void sortByName() {
		Collections.sort(list, new TodoSortByName());
	}

	public void listAll() {
		System.out.println("전체 리스트, 총 " + this.getCount() + "개");

		for (TodoItem item : this.getList()) {
			System.out.println(item.toString());
		}
	}

	public ArrayList<TodoItem> getOrderedList(String orderby, int ordering) { // orderby 기준으로 정렬 출력
		ArrayList<TodoItem> list = new ArrayList<TodoItem>();
		Statement stmt;
		try {
			stmt = con.createStatement();
			String sql = "Select * From Todolist Order By " + orderby;
			if (ordering == 0)
				sql += " desc";
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				int id = rs.getInt("id");
				String title = rs.getString("title");
				String description = rs.getString("memo");
				String due = rs.getString("due_date");
				String current_date = rs.getString("current_date");
				int is_complete = rs.getInt("complete");
				int star = rs.getInt("star");
				String category = getcatename(rs.getInt("cate"));
				TodoItem t = new TodoItem(category, title, description, due,star);
				t.setId(id);
				t.setCurrent_date(current_date);
				t.setIs_complete(is_complete);
				t.setCate(rs.getInt("cate"));
				list.add(t);
			}
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return list;
	}

	public void reverseList() {
		Collections.reverse(list);
	}

	public void sortByDate() {
		Collections.sort(list, new TodoSortByDate());
	}

	public Boolean isDuplicate(String title) { // title이 중복 되었는지 확인하는 메소드
		PreparedStatement pstm;
		int count = 0;
		try {
			String sql = "Select count(id) From Todolist Where title like ?";
			pstm = con.prepareStatement(sql);
			pstm.setString(1, title);
			ResultSet rs = pstm.executeQuery();
			rs.next();
			count = rs.getInt("count(id)");
			pstm.close();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		if (count > 0)
			return true;
		else
			return false;
	}

	public int complete(String number) { // 해당 number를 완료 체크하는 메소드
		String sql = "Update Todolist set complete= ? " + "where id = ?";
		int num = Integer.parseInt(number);
		int count = 0;
		PreparedStatement pstm;
		try {
			pstm = con.prepareStatement(sql);
			pstm.setInt(1, 1);
			pstm.setInt(2, num);
			count = pstm.executeUpdate();
			pstm.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;
	}

	public ArrayList<TodoItem> getComp() { // 완료 체크된 항목들을 list 형태로 return
		ArrayList<TodoItem> list = new ArrayList<TodoItem>();
		Statement stmt;
		try {
			stmt = con.createStatement();
			String sql = "Select * From Todolist Where complete = 1";
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				int id = rs.getInt("id");
				String title = rs.getString("title");
				String description = rs.getString("memo");
				String due = rs.getString("due_date");
				String current_date = rs.getString("current_date");
				int is_complete = rs.getInt("complete");
				String category = getcatename(rs.getInt("cate"));
				TodoItem t = new TodoItem(category, title, description, due);
				t.setId(id);
				t.setCurrent_date(current_date);
				t.setIs_complete(is_complete);
				t.setCate(rs.getInt("cate"));
				list.add(t);
			}
			stmt.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	public boolean cateexist(String name) { // Category table에 이름이 있는지 확인하는 메소드
		String sql = "Select count(id) from Category where name = ?";
		try {
			PreparedStatement pstm = con.prepareStatement(sql);
			pstm.setString(1, name);
			ResultSet rs = pstm.executeQuery();
			int count = rs.getInt("count(id)");
			if (count > 0)
				return true;
			else
				return false;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	public int getcateid(String name) { // Category table의 해당되는 id return
		String sql = "Select id from Category where name = ?";
		int id = -1;
		try {
			PreparedStatement pstm = con.prepareStatement(sql);
			pstm.setString(1, name);
			ResultSet rs = pstm.executeQuery();
			id = rs.getInt("id");
			return id;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return id;
	}
	
	public String getcatename(int id) { // Category 테이블에서 id를 넣으면 name을 return 함
		String sql = "Select name from Category where id = ?";
		String name = "";
		try {
			PreparedStatement pstm = con.prepareStatement(sql);
			pstm.setInt(1, id);
			ResultSet rs = pstm.executeQuery();
			name = rs.getString("name");
			return name;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return name;
	}
	public void importdata(String filename) { // 처음 txt 파일을 db 파일로 옮기는 작업

		try {
			BufferedReader br = new BufferedReader(new FileReader(filename));
			String line;
			String sql = "insert into Todolist (title,memo,current_date,due_date,cate)" + "values(?,?,?,?,?)";
			int records = 0;
			while ((line = br.readLine()) != null) {
				StringTokenizer st = new StringTokenizer(line, "##");
				String category = st.nextToken();
				String title = st.nextToken();
				String desc = st.nextToken();
				String due_date = st.nextToken();
				String current_date = st.nextToken();
				int cate;
				if (cateexist(category))
					cate = getcateid(category);
				else {
					addcate(category);
					cate = getcateid(category);
				}
				PreparedStatement pstm = con.prepareStatement(sql);
				pstm.setString(1, title);
				pstm.setString(2, desc);
				pstm.setString(3, current_date);
				pstm.setString(4, due_date);
				pstm.setInt(5, cate);
				int count = pstm.executeUpdate();
				if (count > 0)
					records++;
			}
			System.out.println(records + "records read!!");
			br.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}