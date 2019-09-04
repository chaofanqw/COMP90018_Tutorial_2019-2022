package com.example.layoutdemo;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import butterknife.ButterKnife;


public class LayoutDemoFragment extends Fragment {
    static int LINEAR_DEMO = R.layout.fragment_layout_demo_linear;
    static int RELATIVE_DEMO = R.layout.fragment_layout_demo_relative;
    static int LIST_DEMO = R.layout.fragment_layout_demo_list;
    static int RECYCLER_DEMO = R.layout.fragment_layout_demo_recycler;
    static String LAYOUT_TYPE = "type";

    private int layout = R.layout.fragment_layout_demo_linear;
    private ListView listView;
    private RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (this.getArguments() != null)
            this.layout = getArguments().getInt(LAYOUT_TYPE);

        View view = inflater.inflate(layout, container, false);
        ButterKnife.bind(this, view);
        initializeList(view);

        return view;
    }

    // Recommended method to generate new LayoutDemoFragment
    // Instead of calling new LayoutDemoFragment() directlyA
    static Fragment newInstance(int layout) {
        Fragment fragment = new LayoutDemoFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(LAYOUT_TYPE, layout);
        fragment.setArguments(bundle);

        return fragment;
    }

    // To bind ListView and RecyclerView to the corresponding layout
    private void initializeList(View view) {
        // To bind ListView adapter to ListView
        if (this.layout == R.layout.fragment_layout_demo_list) {
            ListDemoAdapter adapter = new ListDemoAdapter(getActivity(), R.layout.list_example, getFruits());
            listView = view.findViewById(R.id.demo_list_view);
            listView.setAdapter(adapter);

            // To set onItemClickListener - method 1
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Fruit fruit = (Fruit)adapterView.getItemAtPosition(i);
                    Toast.makeText(getContext(), fruit.getFruitName(), Toast.LENGTH_SHORT).show();
                }
            });
        }
        // To bind RecyclerView adapter to RecyclerView
        else if (this.layout == R.layout.fragment_layout_demo_recycler) {
            RecyclerDemoAdapter adapter = new RecyclerDemoAdapter(getFruits(), R.layout.recycler_example);
            recyclerView = view.findViewById(R.id.demo_recycler);
            // To lay out children in a staggered grid formation
            recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
            recyclerView.setAdapter(adapter);
        }
    }

    // To generate an array of fruit example for ListView and RecyclerView Demonstration
    private ArrayList<Fruit> getFruits() {
        ArrayList<Fruit> fruits = new ArrayList<>();
        fruits.add(new Fruit(R.drawable.apple, "Apple"));
        fruits.add(new Fruit(R.drawable.bananas, "Bananas"));
        fruits.add(new Fruit(R.drawable.cherry, "Cherry"));
        fruits.add(new Fruit(R.drawable.grapes, "Grapes"));
        fruits.add(new Fruit(R.drawable.lemon, "Lemon"));
        fruits.add(new Fruit(R.drawable.orange, "Orange"));
        fruits.add(new Fruit(R.drawable.melon, "Melon"));
        fruits.add(new Fruit(R.drawable.peach, "Peach"));
        fruits.add(new Fruit(R.drawable.pear, "Pear"));
        fruits.add(new Fruit(R.drawable.pomegranate, "Pomegranate"));
        fruits.add(new Fruit(R.drawable.strawberry, "Strawberry"));
        fruits.add(new Fruit(R.drawable.watermelon, "Watermelon"));

        return fruits;
    }

}
