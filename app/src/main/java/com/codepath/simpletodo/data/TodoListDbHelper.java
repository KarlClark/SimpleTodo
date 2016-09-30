package com.codepath.simpletodo.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

import static com.codepath.simpletodo.data.TodoListContract.TodoListEntry.DUE_DATE;
import static com.codepath.simpletodo.data.TodoListContract.TodoListEntry.ITEM;
import static com.codepath.simpletodo.data.TodoListContract.TodoListEntry.NOTE;
import static com.codepath.simpletodo.data.TodoListContract.TodoListEntry.PRIORITY;
import static com.codepath.simpletodo.data.TodoListContract.TodoListEntry.STATUS;
import static com.codepath.simpletodo.data.TodoListContract.TodoListEntry.TABLE_NAME;
import static com.codepath.simpletodo.data.TodoListContract.TodoListEntry._ID;

/**
 * Created by Karl on 9/26/2016.
 */
public class TodoListDbHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "todolist.db";
    private static final String TAG = "## My Info ##";
    private Context context;
    private static TodoListDbHelper todoListDbHelper;

    public static synchronized TodoListDbHelper getInstance(Context context) {
        if (todoListDbHelper == null) {
            todoListDbHelper = new TodoListDbHelper(context.getApplicationContext());
        }
        return todoListDbHelper;
    }


    private TodoListDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        final String SQL_CREATE_TODOLIST_TABLE = "CREATE TABLE " + TABLE_NAME + " (" +
                _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                ITEM +  " TEXT NOT NULL, " +
                PRIORITY + " INTEGER NOT NULL, " +
                DUE_DATE + " LONG, " +
                STATUS + " BOOLEAN NOT NULL, " +
                NOTE + " TEXT " +
                " );";

        sqLiteDatabase.execSQL(SQL_CREATE_TODOLIST_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
    }

    public ArrayList<TodoListItem> getTodoList(){
        ArrayList<TodoListItem> al = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = new String[]{_ID, ITEM, PRIORITY, DUE_DATE, STATUS, NOTE};
        Cursor cursor = db.query(TABLE_NAME, columns, null, null, null, null, null);
        while (cursor.moveToNext()){
            al.add(new TodoListItem(cursor.getInt(0), cursor.getString(1), cursor.getString(5),
                    cursor.getInt(2), cursor.getLong(3), cursor.getInt(4)==1));
        }
        return al;
    }

    public void deleteItem(TodoListItem todoListItem){
        SQLiteDatabase db = this.getReadableDatabase();
        db.delete(TABLE_NAME, _ID + "=" + todoListItem.getId(), null);
    }

    public void addOrUpdateItem(TodoListItem todoListItem){
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues cv= new ContentValues();
        cv.put(ITEM, todoListItem.getItem());
        cv.put(PRIORITY, todoListItem.getPriority());
        cv.put(DUE_DATE, todoListItem.getDueDate());
        cv.put(STATUS,(todoListItem.getStatus())? 1 : 0);
        cv.put(NOTE, todoListItem.getNote());
        if (todoListItem.getId() == -1) {
            todoListItem.setId(db.insert(TABLE_NAME, null, cv));
        }else{
            db.update(TABLE_NAME, cv, _ID + " = " + todoListItem.getId(), null);
        }
    }
}
