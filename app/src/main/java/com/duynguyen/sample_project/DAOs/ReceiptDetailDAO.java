package com.duynguyen.sample_project.DAOs;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.duynguyen.sample_project.Database.DatabaseHandler;
import com.duynguyen.sample_project.Models.Receipt;
import com.duynguyen.sample_project.Models.ReceiptDetail;

public class ReceiptDetailDAO {
    private final DatabaseHandler databaseHandler;
    private final Context context;

    public ReceiptDetailDAO(Context context) {
        this.context = context;
        databaseHandler = new DatabaseHandler(context);
    }

    public void addReceiptDetail(int receiptID, int bookID, int status, int quantity) {
        SQLiteDatabase sqLiteDatabase = databaseHandler.getWritableDatabase();

        // kiểm tra ReceiptDetail có tồn tại chưa
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT quantity, status FROM RECEIPTDETAIL WHERE receiptID = ? AND bookID = ?", new String[]{String.valueOf(receiptID), String.valueOf(bookID)});


        if (cursor.getCount() > 0){
            cursor.moveToFirst();
            ContentValues contentValues = new ContentValues();
            contentValues.put("quantity", cursor.getInt(0) + quantity);
            contentValues.put("status", status);
            sqLiteDatabase.update("RECEIPTDETAIL", contentValues , "receiptID = ? AND bookID = ?", new String[]{String.valueOf(receiptID), String.valueOf(bookID)});
        }else {
            ContentValues contentValues = new ContentValues();
            contentValues.put("receiptID", receiptID);
            contentValues.put("bookID", bookID);
            contentValues.put("quantity", quantity);
            contentValues.put("status", status);
            sqLiteDatabase.insert("RECEIPTDETAIL", null, contentValues);
        }
    }

    public void plusAReceiptDetail(ReceiptDetail detail) {
        int bookID = detail.getBookID();
        int receiptID = detail.getReceiptID();
        SQLiteDatabase sqLiteDatabase = databaseHandler.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("QUANTITY", detail.getQuantity() + 1);
        sqLiteDatabase.update("RECEIPTDETAIL", contentValues , "receiptID = ? AND bookID = ?", new String[]{String.valueOf(receiptID), String.valueOf(bookID)});
    }

    public void minusAReceiptDetail(ReceiptDetail detail){
        int bookID = detail.getBookID();
        int receiptID = detail.getReceiptID();
        SQLiteDatabase sqLiteDatabase = databaseHandler.getWritableDatabase();
        int quantity =  detail.getQuantity();
        if (quantity == 1){
            sqLiteDatabase.delete("RECEIPTDETAIL", "receiptID = ? AND bookID = ?", new String[]{String.valueOf(receiptID), String.valueOf(bookID)});

        }else {
            ContentValues contentValues = new ContentValues();
            contentValues.put("QUANTITY", quantity - 1);
            sqLiteDatabase.update("RECEIPTDETAIL", contentValues , "receiptID = ? AND bookID = ?", new String[]{String.valueOf(receiptID), String.valueOf(bookID)});
        }

    }

    public boolean deleteAReceiptDetail(int receiptID, int bookID) {
        SQLiteDatabase sqLiteDatabase = databaseHandler.getWritableDatabase();
        int check =  sqLiteDatabase.delete("RECEIPTDETAIL", "receiptID = ? AND bookID = ?", new String[]{String.valueOf(receiptID), String.valueOf(bookID)});
        return check > 0;
    }
}
