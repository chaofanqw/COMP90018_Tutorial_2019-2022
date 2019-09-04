package com.example.layoutdemo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


//RecyclerView Adapter for Fruits
public class RecyclerDemoAdapter extends RecyclerView.Adapter<RecyclerDemoAdapter.ViewHolder> {
    static class ViewHolder extends RecyclerView.ViewHolder {
        // to bind with ImageView from R.layout.recycler_example.xml
        // to show the fruit image
        @BindView(R.id.list_example_image)
        ImageView image;
        // to bind with TextView from R.layout.recycler_example.xml
        // to show the fruit name
        @BindView(R.id.list_example_text)
        TextView text;

        ViewHolder(View view) {
            super(view);

            // To show how to add click listener to a item in recyclerView
            // Set onClickListener for the fruit array;
            // When clicking a item from the list, a new Toast shows the name of the clicked fruit;
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(view.getContext(), text.getText(), Toast.LENGTH_SHORT).show();
                }
            });

            ButterKnife.bind(this, view);
        }

    }

    // an array of fruits need to display at recyclerView
    private List<Fruit> fruits;
    // the resource id of item layout
    private int resourceId;

    // to initialize adapter with fruits array, and the resource id of layout
    public RecyclerDemoAdapter(List<Fruit> fruits, int resourceId) {
        this.fruits = fruits;
        this.resourceId = resourceId;
    }

    @NonNull
    @Override
    //  Called when RecyclerView needs a new {@link ViewHolder} of the given type to represent an item.
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(resourceId, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    // to bind the resources to viewHolder, including fruit image resource id and fruit name
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.image.setImageResource(fruits.get(position).getFruitImage());
        holder.text.setText(fruits.get(position).getFruitName());
    }

    @Override
    // to get the size of the fruits array
    public int getItemCount() {
        return fruits.size();
    }

}