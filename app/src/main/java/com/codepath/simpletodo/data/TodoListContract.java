package com.codepath.simpletodo.data;

import android.provider.BaseColumns;

/**
 * Created by Karl on 9/26/2016.
 */
public class TodoListContract{

    public static final class TodoListEntry implements BaseColumns {

        public static final String TABLE_NAME = "todolist";

        public static final String ITEM = "item";
        public static final String PRIORITY = "priority";
        public static final String DUE_DATE = "due_date";
        public static final String STATUS = "status";
        public static final String NOTE = "note";
    }
}
