package com.example.firstdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Main2Activity extends AppCompatActivity {
    public static String RECEIVED_MESSAGE = "received message";

    @BindView(R.id.previous_message)
    TextView message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        ButterKnife.bind(this);

        Intent intent = getIntent();
        message.setText(intent.getStringExtra(MainActivity.MESSAGE));
    }

    @Override
    public void onBackPressed() {
        Intent returnIntent = new Intent();
        returnIntent.putExtra(RECEIVED_MESSAGE, "Hello from the second activity.");
        setResult(RESULT_OK, returnIntent);

        super.onBackPressed();
    }
}
