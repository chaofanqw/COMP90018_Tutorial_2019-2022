package com.example.databasedemo;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {
    private String TAG = "GreenDao Demo";
    private DBManager dbManager;
    private UserAdapter userAdapter;

    @BindView(R.id.name)
    EditText name;
    @BindView(R.id.age)
    EditText age;
    @BindView(R.id.insert)
    Button insert;
    @BindView(R.id.delete)
    Button delete;
    @BindView(R.id.listview)
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        dbManager = DBManager.getDbManager(this);

        userAdapter = new UserAdapter(this, R.layout.single_user, dbManager.queryAllUser());
        listView.setAdapter(userAdapter);
    }

    @OnClick(R.id.insert)
    public void insertUser() {
        String user_name = determineName();
        Long user_age = determineAge();

        if (user_name != null && user_age != null) {
            UserObject user = new UserObject();
            user.setName(user_name);
            user.setAge(user_age);
            dbManager.insertUser(user);
            updateList();
        }
    }

    @OnClick(R.id.delete)
    public void deleteUser() {
        String user_name = determineName();
        Long user_age = determineAge();

        if (user_name != null && user_age != null) {
            List<UserObject> users = dbManager.queryUser(user_name, user_age);
            dbManager.deleteUsers(users);
        } else if (user_name != null) {
            List<UserObject> users = dbManager.queryUser(user_name);
            dbManager.deleteUsers(users);
        } else if (user_age != null) {
            List<UserObject> users = dbManager.queryUser(user_age);
            dbManager.deleteUsers(users);
        }

        updateList();
    }

    private void updateList() {
        userAdapter.clear();
        userAdapter.addAll(dbManager.queryAllUser());
        userAdapter.notifyDataSetChanged();
    }

    private String determineName() {
        String user_name = String.valueOf(name.getText());
        if (user_name.equals(""))
            return null;
        else
            return user_name;
    }

    private Long determineAge() {
        String user_age = String.valueOf(age.getText());
        if (user_age.equals(""))
            return null;
        else if (user_age.matches("^[0-9]+$")) {
            return Long.valueOf(user_age);
        } else {
            return null;
        }
    }
}
