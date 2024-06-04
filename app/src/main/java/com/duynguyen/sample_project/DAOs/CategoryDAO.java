package com.duynguyen.sample_project.DAOs;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.duynguyen.sample_project.Database.DatabaseHandler;
import com.duynguyen.sample_project.Models.Book;
import com.duynguyen.sample_project.Models.Category;

import java.util.ArrayList;

public class CategoryDAO {
    private DatabaseHandler databaseHandler;

    public CategoryDAO(Context context) {
        databaseHandler = new DatabaseHandler(context);
    }

    public ArrayList<Category> getListCategory() {
        SQLiteDatabase sqLiteDatabase = databaseHandler.getReadableDatabase();
        ArrayList<Category> list = new ArrayList<>();

        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM CATEGORY", null);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();

            do {
                list.add(new Category(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getInt(2),
                        cursor.getInt(3)));
            } while (cursor.moveToNext());
        }

        cursor.close();
        return list;
    }

    public String getCategoryNameById(int id) {
        SQLiteDatabase sqLiteDatabase = databaseHandler.getReadableDatabase();
        String categoryName = null;

        Cursor cursor = sqLiteDatabase.rawQuery("SELECT name FROM CATEGORY WHERE id = ?", new String[]{String.valueOf(id)});
        if (cursor != null && cursor.moveToFirst()) {
            categoryName = cursor.getString(0);
            cursor.close();
        }
        return categoryName;
    }
}
