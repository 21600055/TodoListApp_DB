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
	
	public TodoItem(String title, String desc){
        this.title=title;
        this.desc=desc;
        SimpleDateFormat f = new SimpleDateFormat("yyyy/MM/dd kk:mm:ss");
        this.current_date=f.format(new Date()); 
    }
    
    public TodoItem(String category,String title,String desc,String due_date) {
    	this.category = category;
    	this.title=title;
        this.desc=desc;
        this.due_date = due_date;
        SimpleDateFormat f = new SimpleDateFormat("yyyy/MM/dd kk:mm:ss");
        this.current_date=f.format(new Date());
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
    	return this.getCategory()+"##"+this.getTitle()+"##"+this.getDesc()+"##"+this.getDue_date()+"##"+
    			this.getCurrent_date()+"\n";
    }

    
    
    @Override
	public String toString() {
		// TODO Auto-generated method stub
		
		if (this.is_complete == 0)
			return this.getId()+". "+"[" + this.getCategory()+ "] " + this.getTitle() + " - " + this.getDesc() + " - " +
					this.getDue_date() + " - " + this.getCurrent_date();
		else 
			return this.getId()+". "+"[" + this.getCategory()+ "] " + this.getTitle() +"[V]" +" - " + this.getDesc() + " - " +
					this.getDue_date() + " - " + this.getCurrent_date();
	}
}
