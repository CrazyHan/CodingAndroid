// IBookManager.aidl
package com.shl.aidlreview;
import com.shl.aidlreview.Book;//需要导入，自定义类型需要单独在一个 AIDL 里面声名 parcelable
// Declare any non-default types here with import statements

interface IBookManager {
    void addBook(in Book book);//作为参数一定得加一个 in

    List<Book> getBooks();

}
