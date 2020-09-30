// IBookManager.aidl
package com.shl.aidlreview;
import com.shl.aidlreview.Book;
// Declare any non-default types here with import statements

interface IBookManager {
    void addBook(in Book book);

    List<Book> getBooks();

}
