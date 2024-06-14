package com.duynguyen.sample_project.DAOs;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.duynguyen.sample_project.Database.DatabaseHandler;
import com.duynguyen.sample_project.Models.Book;

import java.util.ArrayList;

public class BookDAO {
    private final DatabaseHandler databaseHandler;

    public BookDAO(Context context) {
        databaseHandler = new DatabaseHandler(context);
    }

    public ArrayList<Book> getListProduct() {
        SQLiteDatabase sqLiteDatabase = null;
        ArrayList<Book> list = new ArrayList<>();

        try {
            sqLiteDatabase = databaseHandler.getReadableDatabase();
            try (Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM BOOK", null)) {
                if (cursor.moveToFirst()) {
                    do {

//                        "CREATE TABLE BOOK(" +
//                                "bookID INTEGER PRIMARY KEY AUTOINCREMENT," +
//                                " bookName TEXT NOT NULL," +
//                                " bookImage TEXT NOT NULL," +
//                                " description TEXT NOT NULL," +
//                                " author TEXT NOT NULL," +
//                                " inStock INTEGER NOT NULL," +
//                                "categoryID INTEGER NOT NULL," +
//                                "FOREIGN KEY (categoryID) REFERENCES CATEGORY(categoryID))";
                        list.add(new Book(
                                cursor.getInt(0),
                                cursor.getString(1),
                                cursor.getString(2),
                                cursor.getString(3),
                                cursor.getString(4),
                                cursor.getInt(5),
                                cursor.getInt(6)
                        ));
                    } while (cursor.moveToNext());
                }
            }
        } finally {
            if (sqLiteDatabase != null && sqLiteDatabase.isOpen()) {
                sqLiteDatabase.close();
            }
        }


        return list;
    }


    public Book getBookById(int bookId) {

        SQLiteDatabase sqLiteDatabase = null;
        Book book = null;

        try {
            sqLiteDatabase = databaseHandler.getReadableDatabase();
            try (Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM BOOK" +
                    " WHERE bookID = ?", new String[]{String.valueOf(bookId)})) {

                if (cursor.moveToFirst()) {
                    book = new Book(cursor.getInt(0), cursor.getString(1),
                            cursor.getString(2), cursor.getString(3),
                            cursor.getString(4), cursor.getInt(5));
                }
            }
        } finally {
            if (sqLiteDatabase != null && sqLiteDatabase.isOpen()) {
                sqLiteDatabase.close();
            }
        }

        return book;
    }

    public ArrayList<Book> getBookByCategoryId(int categoryID) {
        SQLiteDatabase sqLiteDatabase = null;
        ArrayList<Book> list = new ArrayList<>();

        try {
            sqLiteDatabase = databaseHandler.getReadableDatabase();
            try (Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM BOOK WHERE categoryID = ?",
                    new String[]{String.valueOf(categoryID)})) {
                if (cursor.getCount() > 0) {
                    cursor.moveToFirst();

                    do {
                        list.add(new Book(cursor.getInt(0),
                                cursor.getString(1),
                                cursor.getString(2),
                                cursor.getString(3),
                                cursor.getString(4),
                                cursor.getInt(5)));
                    } while (cursor.moveToNext());
                }
            }
        } finally {
            if (sqLiteDatabase != null && sqLiteDatabase.isOpen()) {
                sqLiteDatabase.close();
            }
        }
        return list;
    }


    public int getInStock(int bookId) {
        SQLiteDatabase sqLiteDatabase = null;
        try {
            sqLiteDatabase = databaseHandler.getReadableDatabase();
            try (Cursor cursor = sqLiteDatabase.rawQuery("SELECT inStock FROM BOOK" +
                    " WHERE bookID = ?", new String[]{String.valueOf(bookId)})) {

                if (cursor.moveToFirst()) {
                    return cursor.getInt(0);
                }
            }
        } finally {
            if (sqLiteDatabase != null && sqLiteDatabase.isOpen()) {
                sqLiteDatabase.close();
            }
        }
        return -1;

    }


    public boolean addBook(Book book) {
        SQLiteDatabase sqLiteDatabase = null;
        try {
            sqLiteDatabase = databaseHandler.getWritableDatabase();


            ContentValues contentValues = new ContentValues();
            contentValues.put("bookName", book.getBookName());
            contentValues.put("bookImage", book.getBookImageURI());
            contentValues.put("description", book.getDesc());
            contentValues.put("author", book.getAuthor());
            contentValues.put("inStock", book.getQuantity());
            contentValues.put("categoryID", book.getBookCategoryID());


            long newRowId = sqLiteDatabase.insert("BOOK", null, contentValues);


            return newRowId != -1;
        } finally {
            if (sqLiteDatabase != null && sqLiteDatabase.isOpen()) {
                sqLiteDatabase.close();
            }
        }
    }


    public boolean updateBook(Book book) {
        SQLiteDatabase sqLiteDatabase = null;
        try {
            sqLiteDatabase = databaseHandler.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put("bookName", book.getBookName());
            contentValues.put("bookImage", book.getBookImageURI());
            contentValues.put("description", book.getDesc());
            contentValues.put("author", book.getAuthor());
            contentValues.put("inStock", book.getQuantity());
            contentValues.put("categoryID", book.getBookCategoryID());
            long check = sqLiteDatabase.update("BOOK", contentValues, "bookID = ?", new String[]{String.valueOf(book.getBookID())});
            return check > 0;
        } finally {
            if (sqLiteDatabase != null && sqLiteDatabase.isOpen()) {
                sqLiteDatabase.close();
            }
        }
    }

    public void updateInStock(int bookID, int newInStock) {
        SQLiteDatabase sqLiteDatabase = null;
        try {
            sqLiteDatabase = databaseHandler.getWritableDatabase();
            ContentValues contentValues = new ContentValues();

            contentValues.put("inStock", newInStock);

            sqLiteDatabase.update("BOOK", contentValues, "bookID = ?", new String[]{String.valueOf(bookID)});
        } finally {
            if (sqLiteDatabase != null && sqLiteDatabase.isOpen()) {
                sqLiteDatabase.close();
            }
        }
    }


}
