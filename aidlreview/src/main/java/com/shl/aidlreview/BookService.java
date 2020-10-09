package com.shl.aidlreview;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class BookService extends Service {

    List<Book> books = new ArrayList<>();

    public BookService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i("AIDLReview", "Service 创建了");

    }

    @Override
    public IBinder onBind(Intent intent) {
        return new IBookManager.Stub() {
            @Override
            public void addBook(Book book) throws RemoteException {
                Log.i("AIDLReview", "Service 添加书进来" + book.toString());
                books.add(book);
            }

            @Override
            public List<Book> getBooks() throws RemoteException {
                Log.i("AIDLReview", "Service 读取" + books.toString());
                return books;
            }
        };
    }




}
