package com.shl.aidlreview;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    IBookManager iBookManager = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }


    int count = 0;

    public void add(View view) throws RemoteException {
        count++;
        Book book = new Book();
        book.id = count;
        book.name = "dd" + count;
        iBookManager.addBook(book);
    }

    public void read(View view) throws RemoteException {
        Log.i("AIDLReview", iBookManager.getBooks().toString());
    }

    public void bind(View view) {
        bindService(new Intent(this, BookService.class), new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                Log.i("AIDLReview", "client 连接成功");
                iBookManager = IBookManager.Stub.asInterface(service);
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                Log.i("AIDLReview", "client 断开成功");
            }
        },BIND_AUTO_CREATE);
    }
}
