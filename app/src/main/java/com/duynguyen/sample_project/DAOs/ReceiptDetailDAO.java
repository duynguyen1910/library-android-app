package com.duynguyen.sample_project.DAOs;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import com.duynguyen.sample_project.Database.DatabaseHandler;


public class ReceiptDetailDAO {
    private final DatabaseHandler databaseHandler;

    public ReceiptDetailDAO(Context context) {
        databaseHandler = new DatabaseHandler(context);
    }

    // Method sử dụng khi ấn Submit để tạo phiếu mượn
    public long addReceiptDetail(int receiptID, int bookID, int quantity) {
        long result;

        SQLiteDatabase sqLiteDatabase = null;
        try {
            sqLiteDatabase = databaseHandler.getWritableDatabase();

            ContentValues contentValues = new ContentValues();
            contentValues.put("receiptID", receiptID);
            contentValues.put("bookID", bookID);
            contentValues.put("quantity", quantity);

            result = sqLiteDatabase.insert("RECEIPTDETAIL", null, contentValues);
        } finally {
            if (sqLiteDatabase != null && sqLiteDatabase.isOpen()) {
                sqLiteDatabase.close();
            }
        }

        return result;
    }


}
