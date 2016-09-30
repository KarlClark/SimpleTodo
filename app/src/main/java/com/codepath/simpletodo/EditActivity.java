package com.codepath.simpletodo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;

import com.codepath.simpletodo.data.TodoListItem;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class EditActivity extends AppCompatActivity implements CustomDatePickerDialogFragment.OnCustomDateDialogClickedListener{

    private Spinner spPriority, spStatus;
    private EditText etTask, etNote, etDueDate;
    private TodoListItem todoListItem = null;
    private static SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd, yyyy", Locale.US);
    public static final String TODO_LIST_ITEM_EXTRA = "todo_list_item_extra";
    public static final String DELETE_EXTRA = "delete_extra";
    private static final String TAG = "## My Info ##";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getViews();
        getData();
        etDueDate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus) {
                    CustomDatePickerDialogFragment customDatePickerDialogFragment = CustomDatePickerDialogFragment.newInstance(getResources().getString(R.string.due_date));
                    customDatePickerDialogFragment.setCancelable(false);
                    customDatePickerDialogFragment.setField(etDueDate);
                    customDatePickerDialogFragment.show(getSupportFragmentManager(), "tag");
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_edit, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = new Intent();
        if (item.getItemId() == R.id.delete){
            intent.putExtra(DELETE_EXTRA, true);
        }else{
            putData();
            intent.putExtra(DELETE_EXTRA, false);
        }
        intent.putExtra(TODO_LIST_ITEM_EXTRA, todoListItem);
        setResult(RESULT_OK, intent);
        finish();
        return true;
    }

    @Override
    public void onBackPressed(){
        putData();
        super.onBackPressed();
    }

    @Override
    public void onCustomDateDialogClicked(EditText et) {
        etTask.requestFocus();
        etTask.setSelection(etTask.getText().length());
    }

    private void getViews(){
        etTask = (EditText)findViewById(R.id.etTask);
        etNote = (EditText)findViewById(R.id.etNote);
        etDueDate = (EditText)findViewById(R.id.etDueDate);
        spPriority = (Spinner)findViewById(R.id.spPriority);
        spStatus = (Spinner)findViewById(R.id.spStatus);
    }

    private void getData(){
        todoListItem = (TodoListItem)getIntent().getSerializableExtra(TODO_LIST_ITEM_EXTRA);
        if (todoListItem != null){
            etTask.setText(todoListItem.getItem());
            etNote.setText(todoListItem.getNote());
            etDueDate.setText(getDisplayDate(todoListItem.getDueDate()));
            spPriority.setSelection(todoListItem.getPriority());
            spStatus.setSelection((todoListItem.getStatus())? 1 : 0);
            etTask.requestFocus();
            etTask.setSelection(etTask.getText().length());
        }else{
            etDueDate.setText(getDisplayDate(Calendar.getInstance().getTimeInMillis()));
        }
    }

    private void putData(){
        if(todoListItem == null) {
            todoListItem = new TodoListItem();
        }
        todoListItem.setItem(etTask.getText().toString());
        todoListItem.setNote(etNote.getText().toString());
        todoListItem.setDueDate(getLongDate(etDueDate.getText().toString()));
        todoListItem.setPriority(spPriority.getSelectedItemPosition());
        todoListItem.setStatus((spStatus.getSelectedItemPosition() == 0) ? false : true);
        Intent intent = new Intent();
        intent.putExtra(TODO_LIST_ITEM_EXTRA, todoListItem);
        intent.putExtra(DELETE_EXTRA, false);
        setResult(RESULT_OK, intent);
    }

    public static String getDisplayDate(long longDate){
        Date date = new Date(longDate);
        return dateFormat.format(date);
    }

    public static long getLongDate(String displayDate){
        try {
            Date date = dateFormat.parse(displayDate);
            return date.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0L;
    }
}
