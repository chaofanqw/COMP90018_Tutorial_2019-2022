package com.example.databasedemo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UserAdapter extends ArrayAdapter<UserObject> {
    class ViewHolder {
        @BindView(R.id.user_id)
        TextView user_id;
        @BindView(R.id.user_name)
        TextView user_name;
        @BindView(R.id.user_age)
        TextView user_age;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    private int resourceId;

    public UserAdapter(Context context, int textViewResourceId, List<UserObject> objects) {
        super(context, textViewResourceId, objects);
        resourceId = textViewResourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        UserObject user = getItem(position);
        View view;
        ViewHolder viewHolder;
        if (convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(resourceId, null);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        } else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }

        viewHolder.user_id.setText(String.valueOf(user.getId()));
        viewHolder.user_name.setText(user.getName());
        viewHolder.user_age.setText(String.valueOf(user.getAge()));

        return view;
    }
}