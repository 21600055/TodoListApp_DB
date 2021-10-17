package com.todo.dao;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TodoItem {
	private int id;
	private String title;
	private String desc;
	private String current_date;
	private String category;
	private String due_date;
	private int is_complete;
	private int cate;
	private int star;

	public int getStar() {
		return star;
	}

	public void setStar(int star) {
		this.star = star;
	}

	public int getCate() {
		return cate;
	}

	public void setCate(int cate) {
		this.cate = cate;
	}

	public int getIs_complete() {
		return is_complete;
	}

	public void setIs_complete(int is_complete) {
		this.is_complete = is_complete;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public TodoItem(String title, String desc) {
		this.title = title;
		this.desc = desc;
		SimpleDateFormat f = new SimpleDateFormat("yyyy/MM/dd kk:mm:ss");
		this.current_date = f.format(new Date());
	}

	public TodoItem(String category, String title, String desc, String due_date) {
		this.category = category;
		this.title = title;
		this.desc = desc;
		this.due_date = due_date;
		SimpleDateFormat f = new SimpleDateFormat("yyyy/MM/dd kk:mm:ss");
		this.current_date = f.format(new Date());
		this.star = 0;
	}

	public TodoItem(String category, String title, String desc, String due_date, int star) {
		this.category = category;
		this.title = title;
		this.desc = desc;
		this.due_date = due_date;
		SimpleDateFormat f = new SimpleDateFormat("yyyy/MM/dd kk:mm:ss");
		this.current_date = f.format(new Date());
		this.star = star;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getCurrent_date() {
		return current_date;
	}

	public void setCurrent_date(String current_date) {
		this.current_date = current_date;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getDue_date() {
		return due_date;
	}

	public void setDue_date(String due_date) {
		this.due_date = due_date;
	}

	public String toSaveString() {
		return this.getCategory() + "##" + this.getTitle() + "##" + this.getDesc() + "##" + this.getDue_date() + "##"
				+ this.getCurrent_date() + "\n";
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		String star = "중요도: ";
		if (this.getStar() == 0)
			star = "";
		else
			for (int i = 0; i < this.getStar(); i++)
				star += "★";
		
		if (this.is_complete == 0)
			return this.getId() + ". " + "[" + this.getCategory() + "] " + this.getTitle() + " - " + this.getDesc()
					+ " - " + this.getDue_date() + " - " + this.getCurrent_date() + star;
		else
			return this.getId() + ". " + "[" + this.getCategory() + "] " + this.getTitle() + "[V]" + " - "
					+ this.getDesc() + " - " + this.getDue_date() + " - " + this.getCurrent_date() + star;
	}
}
