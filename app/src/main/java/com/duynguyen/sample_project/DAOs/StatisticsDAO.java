package com.duynguyen.sample_project.DAOs;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.duynguyen.sample_project.Database.DatabaseHandler;
import com.duynguyen.sample_project.Models.Book;
import com.duynguyen.sample_project.Models.Statistic;

import java.util.ArrayList;

public class StatisticsDAO {
    private  DatabaseHandler databaseHandler;

    public StatisticsDAO(Context context) {
        databaseHandler = new DatabaseHandler(context);
    }

    public ArrayList<Statistic> getListBooksWithHighestQuantities(int limit) {
//        The method getListBookWithBiggestQuantity retrieves a list of books
//        with the highest quantities from a database

        SQLiteDatabase sqLiteDatabase = null;
        ArrayList<Statistic> list = new ArrayList<>();

        try {
            sqLiteDatabase = databaseHandler.getReadableDatabase();

            String query = "SELECT b.bookID,  b.bookImage, b.bookName, b.author, sum(d.quantity)\n" +
                    "FROM BOOK b,  RECEIPTDETAIL d\n" +
                    "WHERE b.bookID = d.bookID \n" +
                    "GROUP BY b.bookid\n" +
                    "ORDER BY sum(d.quantity) DESC\n" +
                    "LIMIT ?;";
            try (Cursor cursor = sqLiteDatabase.rawQuery(query, new String[]{String.valueOf(limit)})) {
                if (cursor.moveToFirst()) {
                    do {
                        list.add(new Statistic(
                                cursor.getInt(0),
                                cursor.getString(1),
                                cursor.getString(2),
                                cursor.getString(3),
                                cursor.getInt(4)
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
}
