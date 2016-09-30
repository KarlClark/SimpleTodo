package com.codepath.simpletodo;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.codepath.simpletodo.data.TodoListItem;

import java.util.ArrayList;
import java.util.Calendar;

import static com.codepath.simpletodo.data.TodoListItem.HIGH;
import static com.codepath.simpletodo.data.TodoListItem.LOW;
import static com.codepath.simpletodo.data.TodoListItem.MEDIUM;

/**
 * Created by Karl on 9/27/2016.
 */
public class TodoListAdapter extends ArrayAdapter<TodoListItem> {

    private int width = 0;
    private float todayMillies;
    private Context context;
    private boolean firstTime = true;
    private int originalBackgroudColor;
    private static final String TAG = "## My Info ##";

    public TodoListAdapter(Context context, ArrayList<TodoListItem> users) {
        super(context, 0, users);
        todayMillies = Calendar.getInstance().getTimeInMillis();
        this.context = context;
    }

    private static class ViewHolder {
        TextView tvPriority;
        TextView tvItem;
        FrameLayout frBackground;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TodoListItem todoListItem = getItem(position);
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.adapter_item, parent, false);
            viewHolder.tvPriority = (TextView) convertView.findViewById(R.id.tvPriority);
            viewHolder.tvItem = (TextView) convertView.findViewById(R.id.tvItem);
            viewHolder.frBackground = (FrameLayout)convertView.findViewById(R.id.frlBackground);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        if (firstTime){
            originalBackgroudColor = ((ColorDrawable)viewHolder.frBackground.getBackground()).getColor();
            firstTime=false;
        }
        if (width == 0){
            viewHolder.tvPriority.setText("MEDIUM");
            viewHolder.tvPriority.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
            width = viewHolder.tvPriority.getMeasuredWidth();
        }
        switch (todoListItem.getPriority()){
            case LOW:
                viewHolder.tvPriority.setText("LOW");
                viewHolder.tvPriority.setTextColor(Color.BLUE);
                break;
            case MEDIUM:
                viewHolder.tvPriority.setText("MEDIUM");
                viewHolder.tvPriority.setTextColor(Color.GREEN);
                break;
            case HIGH:
                viewHolder.tvPriority.setText("HIGH");
                viewHolder.tvPriority.setTextColor(Color.RED);
                break;

        }
        ViewGroup.LayoutParams params = viewHolder.tvPriority.getLayoutParams();
        params.width = width;
        viewHolder.tvPriority.setLayoutParams(params);
        viewHolder.tvItem.setText(todoListItem.getItem());
        if (todoListItem.getStatus()){
            viewHolder.frBackground.setBackgroundColor(ContextCompat.getColor(context,R.color.light_blue));
        }else{
            if ((todayMillies - todoListItem.getDueDate())/(24*60*60*1000) > 1){
                viewHolder.frBackground.setBackgroundColor(ContextCompat.getColor(context, R.color.light_red));
            }else {
                viewHolder.frBackground.setBackgroundColor(originalBackgroudColor);
            }
        }
        //Log.i(TAG,"days ago= " + (todayMillies - todoListItem.getDueDate())/(24*60*60*1000));
        return convertView;
    }
}
