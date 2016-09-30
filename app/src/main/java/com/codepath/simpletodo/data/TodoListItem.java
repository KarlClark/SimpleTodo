package com.codepath.simpletodo.data;

import java.io.Serializable;

/**
 * Created by Karl on 9/26/2016.
 */
public class TodoListItem implements Serializable{
    private String item, note;
    private int priority;
    private long id, dueDate;
    private boolean status;
    public static final int LOW = 0;
    public static final int MEDIUM = 1;
    public static final int HIGH = 2;
    private static final long serialVersionUID = 4654897646L;

    public TodoListItem(){
        id = -1L;
    }

    public TodoListItem(long id, String item, String note, int priority, long dueDate, boolean status) {
        this.id = id;
        this.item = item;
        this.note = note;
        this.priority = priority;
        this.dueDate = dueDate;
        this.status = status;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public long getDueDate() {
        return dueDate;
    }

    public void setDueDate(long dueDate) {
        this.dueDate = dueDate;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getItem() {

        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }
}
