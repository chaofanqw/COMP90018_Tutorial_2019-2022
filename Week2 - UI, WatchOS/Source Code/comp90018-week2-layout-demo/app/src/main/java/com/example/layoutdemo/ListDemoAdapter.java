package com.example.layoutdemo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ListDemoAdapter extends ArrayAdapter<Fruit> {
    static class ViewHolder {
        @BindView(R.id.list_example_image)
        ImageView image;
        @BindView(R.id.list_example_text)
        TextView text;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    private int resourceId;

    public ListDemoAdapter(Context context, int resource, List<Fruit> objects) {
        super(context, resource, objects);
        resourceId = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Fruit fruit = getItem(position);
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

//        Set onClick listener - method 2
//        view.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(getContext(), ((ViewHolder) view.getTag()).text.getText(), Toast.LENGTH_SHORT).show();
//            }
//        });

        viewHolder.image.setImageResource(fruit.getFruitImage());
        viewHolder.text.setText(fruit.getFruitName());

        return view;
    }
}