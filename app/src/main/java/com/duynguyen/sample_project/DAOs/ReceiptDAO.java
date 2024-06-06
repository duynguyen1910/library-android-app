package com.duynguyen.sample_project.DAOs;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.duynguyen.sample_project.Database.DatabaseHandler;
import com.duynguyen.sample_project.Models.Receipt;

public class ReceiptDAO {
    private final DatabaseHandler databaseHandler;
    private final Context context;

    public ReceiptDAO(Context context) {
        this.context = context;
        databaseHandler = new DatabaseHandler(context);
    }

    public void addReceipt(Receipt receipt) {
        SQLiteDatabase sqLiteDatabase = databaseHandler.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("startDay", receipt.getStartDay());
        contentValues.put("endDay", receipt.getEndDay());
        contentValues.put("note", receipt.getNote());
        contentValues.put("memberID", receipt.getMemberID());

        long check = sqLiteDatabase.insert("RECEIPT", null, contentValues);
        if (check != -1) {
            Toast.makeText(context, "Receipt created successfully", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Receipt created failed", Toast.LENGTH_SHORT).show();
        }

    }

    public boolean updateReceipt(int memberID, String endDay, String note) {
        SQLiteDatabase sqLiteDatabase = databaseHandler.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("endDay", endDay);
        contentValues.put("note", note);
        long check = sqLiteDatabase.update("RECEIPT", contentValues, "memberID = ?", new String[]{String.valueOf(memberID)});
        return check > 0;
    }
}
