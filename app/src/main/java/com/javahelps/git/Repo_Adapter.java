package com.javahelps.git;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Sneha on 03-10-2017.
 */

public class Repo_Adapter extends ArrayAdapter<String> {

    ArrayList<String> mList;
    Context mcontext ;
    public Repo_Adapter(@NonNull Context context , ArrayList<String> list) {
        super(context, 0);
        mList = list ;
        mcontext = context ;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if (convertView == null){
            convertView = LayoutInflater.from(mcontext).inflate(R.layout.list_view , null);
            ViewHolder viewHolder = new ViewHolder();
            Button button = (Button)convertView.findViewById(R.id.button2);
            viewHolder.button = button ;
            convertView.setTag(viewHolder);
        }
        ViewHolder holder = (ViewHolder)convertView.getTag();
        String item = mList.get(position);
        holder.button.setText(item.toString());

        return convertView ;
    }

    static class ViewHolder{
        Button button ;
    }
}
