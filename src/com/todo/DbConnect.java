package com.todo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnect {

		private static Connection con = null;
		
		public static void closeConnection() {
			if(con!=null) {
				try {
					con.close();
				} catch(SQLException e) {
					e.printStackTrace();
				}
			}
		}
		
		public static Connection getConnection() {
			if(con==null) {
				try {
					Class.forName("org.sqlite.JDBC");
					String dbFile = "Todolist.db";
					con = DriverManager.getConnection("jdbc:sqlite:"+dbFile);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			return con;
		}
}
