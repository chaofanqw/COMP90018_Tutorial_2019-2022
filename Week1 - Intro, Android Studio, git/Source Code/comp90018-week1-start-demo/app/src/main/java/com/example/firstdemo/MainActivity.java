package com.example.firstdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {
    private String TAG = "First Demo";
    public static String MESSAGE = "Message";
    public static int MESSAGE_RECEIVE = 1;

    //    private Button button;
//    @BindView(R.id.button)
//    Button button;
//    @BindView(R.id.received_message)
//    TextView message;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        Step One: Show how to output Log from Logcat;
//        Log.d(TAG, "onCreate: " + "This is the first LOG");


//        Step Two: Show how to add listener to a button;
//        button = findViewById(R.id.button);
//        button.setOnClickListener(
//                new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        Log.d(TAG, "Click Button!");
//                    }
//                });


//       Step Three: Show how to use Butter Knife to add listener to a button
//        ButterKnife.bind(this);
    }

//    @OnClick(R.id.button)
//    public void outputLog() {
//        Log.d(TAG, "Click Button!");

//        Explicit Intents
//        Intent intent = new Intent(this, Main2Activity.class);
//        intent.putExtra(MESSAGE, "Hello from the first activity.");
//        startActivity(intent);

//        Implicit Intents
//        Intent intent = new Intent();
//        intent.setAction("SecondActivity");
//        intent.putExtra(MESSAGE, "Hello from the first activity.");
//        startActivity(intent);

//        Start Activity for Results
//        Intent intent = new Intent();
//        intent.setAction("SecondActivity");
//        intent.putExtra(MESSAGE, "Hello from the first activity.");
//        startActivityForResult(intent, MESSAGE_RECEIVE);
//    }


//    Receive Message from Called Activities
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        // Check which request we're responding to
//        if (requestCode == MESSAGE_RECEIVE) {
//            if (resultCode==RESULT_OK){
//                message.setText(data.getStringExtra(Main2Activity.RECEIVED_MESSAGE));
//            }
//        }
//    }

}
