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
		this.con = DbConnect.getConnection();
	}

	public int addItem(TodoItem t) {
		String sql = "insert into Todolist (category,title,desc,current_date,due_date)"
				+"values(?,?,?,?,?);";
		PreparedStatement pstm;
		int count = 0;
		try {
			pstm = con.prepareStatement(sql);
			pstm.setString(1,t.getCategory());
			pstm.setString(2,t.getTitle());
			pstm.setString(3,t.getDesc());
			pstm.setString(4,t.getCurrent_date());
			pstm.setString(5,t.getDue_date());
			count = pstm.executeUpdate();
			pstm.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return count;
	}

	public int deleteItem(int index) {
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

	public int editItem(TodoItem t) {
		String sql = "Update Todolist set category=?, title=?, desc=? ,current_date=?, due_date=?"
				+"where id = ?;";
		PreparedStatement pstm;
		int count = 0;
		try {
			pstm = con.prepareStatement(sql);
			pstm.setString(1,t.getCategory());
			pstm.setString(2,t.getTitle());
			pstm.setString(3,t.getDesc());
			pstm.setString(4,t.getCurrent_date());
			pstm.setString(5,t.getDue_date());
			pstm.setInt(6, t.getId());
			count = pstm.executeUpdate();
			pstm.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;
	}

	public ArrayList<TodoItem> getList() {
		ArrayList<TodoItem> list = new ArrayList<TodoItem>();
		Statement stmt;
		try {
			stmt = con.createStatement();
			String sql = "Select * From Todolist;";
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()) {
	            int id = rs.getInt("id");
	            String category = rs.getString("category");
	            String title = rs.getString("title");
	            String description = rs.getString("desc");
	            String due = rs.getString("due_date");
	            String current_date = rs.getString("current_date");
	            int is_complete = rs.getInt("is_completed");
	            TodoItem t = new TodoItem(category,title, description, due);
	            t.setId(id);
	            t.setCurrent_date(current_date);
	            t.setIs_complete(is_complete);
	            list.add(t);
	         }
	         stmt.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	
	public ArrayList<TodoItem> getList(String keyword) {
		ArrayList<TodoItem> list = new ArrayList<TodoItem>();
		PreparedStatement pstm;
		keyword = "%"+keyword+"%";
		
		try {
			String sql = "Select * From Todolist Where title Like ? or desc like ?;";
			pstm = con.prepareStatement(sql);
			pstm.setString(1,keyword);
			pstm.setString(2,keyword);
			ResultSet rs = pstm.executeQuery();
			while(rs.next()) {
	            int id = rs.getInt("id");
	            String category = rs.getString("category");
	            String title = rs.getString("title");
	            String description = rs.getString("desc");
	            String due = rs.getString("due_date");
	            String current_date = rs.getString("current_date");
	            int is_complete = rs.getInt("is_completed");
	            TodoItem t = new TodoItem(category,title, description, due);
	            t.setId(id);
	            t.setCurrent_date(current_date);
	            t.setIs_complete(is_complete);
	            list.add(t);
	         }
	         pstm.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	
	public int getCount() {
		 Statement stmt;
	      int count=0;
	      try {
	         stmt = this.getCon().createStatement();
	         String sql = "SELECT count(id) FROM TodoList;";
	         ResultSet rs = stmt.executeQuery(sql);
	         rs.next();
	         count = rs.getInt("count(id)");
	         stmt.close();
	      } catch (SQLException e) {
	         e.printStackTrace();
	      }
	      return count;
	}
	
	public ArrayList<String> getCategories(){
		ArrayList<String> list = new ArrayList<String>();
		Statement stmt;
		try {
			stmt = con.createStatement();
			String sql = "Select Distinct category From Todolist";
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()) {
	            String category = rs.getString("category");
	            list.add(category);
	         }
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public ArrayList<TodoItem> getListCategory(String keyword){
		ArrayList<TodoItem> list = new ArrayList<TodoItem>();
		PreparedStatement pstm;
		
		String sql = "SELECT * FROM Todolist Where category = ?";
		try {
			pstm = con.prepareStatement(sql);
			pstm.setString(1, keyword);
			ResultSet rs = pstm.executeQuery();
			while(rs.next()) {
	            int id = rs.getInt("id");
	            String category = rs.getString("category");
	            String title = rs.getString("title");
	            String description = rs.getString("desc");
	            String due = rs.getString("due_date");
	            String current_date = rs.getString("current_date");
	            int is_complete = rs.getInt("is_completed");
	            TodoItem t = new TodoItem(category,title, description, due);
	            t.setId(id);
	            t.setCurrent_date(current_date);
	            t.setIs_complete(is_complete);
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
		System.out.println("전체 리스트, 총 "+this.getCount()+"개");
		
		for (TodoItem item : this.getList()) {
			System.out.println(item.toString());
		}
	}
	
	public void find(String[] schoice) {
		String t = "";
		int ct = 1;
		for(int i = 1;i<schoice.length;i++) {
			if(i==schoice.length-1) t += schoice[i];
			else t += schoice[i] + " ";
		}
		for(TodoItem item : list) {
			if(item.getTitle().contains(t)||item.getDesc().contains(t))
				System.out.println(ct+". "+item.toString());
			ct++;
		}
	}
	
	public void find_cate(String[] schoice) {
		String t = "";
		int ct = 1;
		for(int i = 1;i<schoice.length;i++) {
			if(i==schoice.length-1) t += schoice[i];
			else t += schoice[i] + " ";
		}
		for(TodoItem item : list) {
			if(item.getCategory().contains(t))
				System.out.println(ct+". "+item.toString());
			ct++;
		}
	}
	
	public void ls_cate() {
		HashSet<String> set = new HashSet<String>();
		for(TodoItem item : list) set.add(item.getCategory());
		int size = set.size();
		int ct = 1;
		Iterator<String> iter = set.iterator();
		while(iter.hasNext()) {
			if(ct<size) System.out.print(iter.next()+" / ");
			else System.out.print(iter.next());
			ct++;
		}
		System.out.println();
		System.out.println("총 "+size+"개의 카테고리가 등록되어 있습니다.");
	}
	
	public ArrayList<TodoItem> getOrderedList(String orderby,int ordering){
		ArrayList<TodoItem> list = new ArrayList<TodoItem>();
		Statement stmt;
		try {
			stmt = con.createStatement();
			String sql = "Select * From Todolist Order By "+ orderby;
			if(ordering==0) sql+=" desc";
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()) {
	            int id = rs.getInt("id");
	            String category = rs.getString("category");
	            String title = rs.getString("title");
	            String description = rs.getString("desc");
	            String due = rs.getString("due_date");
	            String current_date = rs.getString("current_date");
	            int is_complete = rs.getInt("is_completed");
	            TodoItem t = new TodoItem(category,title, description, due);
	            t.setId(id);
	            t.setCurrent_date(current_date);
	            t.setIs_complete(is_complete);
	            list.add(t);
	         }
	         stmt.close();
		} catch(SQLException e) {
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

	public Boolean isDuplicate(String title) {
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
		if (count>0) return true;
		else return false;
	}
	
	public int complete(String number) {
		String sql = "Update Todolist set is_completed= ? "
				+"where id = ?";
		int num = Integer.parseInt(number);
		int count = 0;
		PreparedStatement pstm;
		try {
			pstm = con.prepareStatement(sql);
			pstm.setInt(1,1);
			pstm.setInt(2,num);
			count = pstm.executeUpdate();
			pstm.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;
	}
	
	public ArrayList<TodoItem> getComp(){
		ArrayList<TodoItem> list = new ArrayList<TodoItem>();
		Statement stmt;
		try {
			stmt = con.createStatement();
			String sql = "Select * From Todolist Where is_completed = 1";
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()) {
	            int id = rs.getInt("id");
	            String category = rs.getString("category");
	            String title = rs.getString("title");
	            String description = rs.getString("desc");
	            String due = rs.getString("due_date");
	            String current_date = rs.getString("current_date");
	            int is_complete = rs.getInt("is_completed");
	            TodoItem t = new TodoItem(category,title, description, due);
	            t.setId(id);
	            t.setCurrent_date(current_date);
	            t.setIs_complete(is_complete);
	            list.add(t);
	         }
	         stmt.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
		
	}
	
	public void importdata(String filename) {
		
		try {
			BufferedReader br = new BufferedReader(new FileReader(filename));
			String line;
			String sql = "insert into Todolist (category,title,desc,current_date,due_date)"
					+"values(?,?,?,?,?)";
			int records = 0;
			while((line = br.readLine())!=null) {
				StringTokenizer st = new StringTokenizer(line,"##");
				String category = st.nextToken();
				String title = st.nextToken();
				String desc = st.nextToken();
				String due_date = st.nextToken();
				String current_date = st.nextToken();
				
				PreparedStatement pstm = this.getCon().prepareStatement(sql);
				pstm.setString(1,category);
				pstm.setString(2,title);
				pstm.setString(3,desc);
				pstm.setString(4,current_date);
				pstm.setString(5,due_date);
				int count = pstm.executeUpdate();
				if (count>0) records++;
			}
			System.out.println(records+"records read!!");
			br.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
