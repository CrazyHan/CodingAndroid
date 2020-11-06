package com.shl.jetpack;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.os.Bundle;
import android.view.View;

import com.shl.jetpack.room.db.AppDatabase;
import com.shl.jetpack.room.entity.User;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    public String[] firstName = {"王", "杜", "赵", "钱", "孙", "李", "周", "吴"};
    public String[]  lastName = {"小白", "小绿", "小红", "三", "四", "五一", "六"};
    AppDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "牛逼大大").build();


        db.userDao().insertAll();
    }

    public void insert(View view) {
        User user = new User();
        Random random = new Random();
        int firstIndex = random.nextInt(firstName.length);
        user.firstName = firstName[firstIndex];

        int lastIndex = random.nextInt(lastName.length);
        user.lastName = lastName[lastIndex];

        db.userDao().insertAll(user);

    }

    public void delete(View view) {

        db.userDao().delete();

    }

    public void update(View view) {
    }

    public void search(View view) {
    }
}