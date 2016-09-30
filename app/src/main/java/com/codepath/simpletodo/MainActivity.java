package com.codepath.simpletodo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.codepath.simpletodo.data.TodoListDbHelper;
import com.codepath.simpletodo.data.TodoListItem;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<TodoListItem> todoListItems = new ArrayList<>();
    TodoListAdapter todoListAdapter;
    ListView lvItems;
    int position;
    TodoListDbHelper dbHelper;
    private static final int EDIT_TAG = 0;
    private static final int NEW_TAG = 1;
    private static final String TAG = "## My Info ##";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbHelper = TodoListDbHelper.getInstance(this);
        lvItems = (ListView)findViewById(R.id.lvItems);
        todoListItems = dbHelper.getTodoList();
        todoListAdapter = new TodoListAdapter(this,todoListItems);
        lvItems.setAdapter(todoListAdapter);
        setUpListVewListeners();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = new Intent(this,EditActivity.class);
        startActivityForResult(intent, NEW_TAG);
        return super.onOptionsItemSelected(item);
    }



    private void setUpListVewListeners(){
        lvItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                deleteTask(position);
                todoListAdapter.notifyDataSetChanged();
                return true;
            }
        });

        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MainActivity.this.position = position;
                Intent intent = new Intent(MainActivity.this, EditActivity.class);
                intent.putExtra(EditActivity.TODO_LIST_ITEM_EXTRA, todoListItems.get(position));
                startActivityForResult(intent, EDIT_TAG);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent){
        TodoListItem todoListItem = (TodoListItem)intent.getSerializableExtra(EditActivity.TODO_LIST_ITEM_EXTRA);
        boolean deleted = intent.getBooleanExtra(EditActivity.DELETE_EXTRA, false);
        Log.i(TAG, "onActivityResult requestCode= " + requestCode + "  deleted= " + deleted);

        switch (requestCode){

            case NEW_TAG:
                Log.i(TAG, "New Tag");
                if ( ! deleted){
                    dbHelper.addOrUpdateItem(todoListItem);
                    todoListAdapter.add(todoListItem);
                    lvItems.post(new Runnable() {
                        @Override
                        public void run() {
                            lvItems.setSelection(todoListItems.size()-1);
                        }
                    });

                }
                break;

            case EDIT_TAG:
                if (deleted){
                    dbHelper.deleteItem(todoListItem);
                    todoListItems.remove(position);
                    todoListAdapter.notifyDataSetChanged();
                }else{
                    dbHelper.addOrUpdateItem(todoListItem);
                    todoListItems.remove(position);
                    todoListItems.add(position,todoListItem);
                    todoListAdapter.notifyDataSetChanged();
                }
                lvItems.post(new Runnable() {
                    @Override
                    public void run() {
                        lvItems.setSelection(position);
                    }
                });
        }
    }

    private void deleteTask(int position){
        dbHelper.deleteItem(todoListItems.get(position));
        todoListItems.remove(position);
    }
}


