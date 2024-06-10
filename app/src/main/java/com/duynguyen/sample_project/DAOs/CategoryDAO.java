package com.duynguyen.sample_project.DAOs;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.duynguyen.sample_project.Database.DatabaseHandler;
import com.duynguyen.sample_project.Models.Category;

import java.util.ArrayList;

public class CategoryDAO {
    private final DatabaseHandler databaseHandler;

    public CategoryDAO(Context context) {
        databaseHandler = new DatabaseHandler(context);
    }

    public ArrayList<Category> getListCategory() {

        SQLiteDatabase sqLiteDatabase = null;
        ArrayList<Category> list = new ArrayList<>();

        try {
            sqLiteDatabase = databaseHandler.getReadableDatabase();
            try (Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM CATEGORY", null)) {
                if (cursor.moveToFirst()) {
                    do {
                        list.add(new Category(
                                cursor.getInt(0),
                                cursor.getString(1),
                                cursor.getInt(2),
                                cursor.getInt(3)
                        ));
                    } while (cursor.moveToNext());
                }
            }
        } finally {
            if (sqLiteDatabase != null && sqLiteDatabase.isOpen()) {
                sqLiteDatabase.close();
            }
        }





        // Close the cursor in the finally block

        return list;
    }


    public String getCategoryNameById(int id) {
        SQLiteDatabase sqLiteDatabase = null;
        String categoryName = null;
        try {
            sqLiteDatabase = databaseHandler.getReadableDatabase();
            try (Cursor cursor = sqLiteDatabase.rawQuery("SELECT name FROM CATEGORY WHERE id = ?", new String[]{String.valueOf(id)})) {
                if (cursor != null && cursor.moveToFirst()) {
                    categoryName = cursor.getString(0);
                }
            }
        } finally {
            if (sqLiteDatabase != null && sqLiteDatabase.isOpen()) {
                sqLiteDatabase.close();
            }
        }




        return categoryName;
    }

}
