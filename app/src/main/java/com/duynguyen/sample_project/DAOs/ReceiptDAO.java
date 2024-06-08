package com.duynguyen.sample_project.DAOs;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
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

    public int addReceipt(Receipt receipt) {
        // This method returns the ID of the inserted row if successful
        // Returns -1 if the member hasn't returned previous books or if an error occurs during insertion
        int newReceiptID = 0;
        SQLiteDatabase sqLiteDatabase = null;
        Cursor cursor = null;

        try {
            sqLiteDatabase = databaseHandler.getWritableDatabase();

            // Check if there are any unreturned receipts for the member
            cursor = sqLiteDatabase.rawQuery("SELECT * FROM RECEIPT WHERE memberID = ?", new String[]{String.valueOf(receipt.getMemberID())});
            if (cursor.moveToFirst()) {  // Check if there is at least one result row
                String endDay = cursor.getString(cursor.getColumnIndexOrThrow("endDay"));
                if (endDay == null || endDay.isEmpty()) {
                    Toast.makeText(context, "This member has an unreturned receipt. Cannot borrow.", Toast.LENGTH_SHORT).show();
                    return -1;
                }
            }

            ContentValues contentValues = new ContentValues();
            contentValues.put("startDay", receipt.getStartDay());
            contentValues.put("endDay", receipt.getEndDay());
            contentValues.put("note", receipt.getNote());
            contentValues.put("memberID", receipt.getMemberID());
            contentValues.put("status", receipt.getStatus());

            // insert() method returns the row ID of the newly inserted row, or -1 if an error occurred
            long result = sqLiteDatabase.insert("RECEIPT", null, contentValues);
            if (result != -1) {
                newReceiptID = (int) result;
            } else {
                newReceiptID = -1;
            }
        } catch (Exception e) {
            e.printStackTrace();
            newReceiptID = -1;
        } finally {
            // Ensure the cursor is closed
            if (cursor != null) {
                cursor.close();
            }
            // Ensure the database is closed
            if (sqLiteDatabase != null && sqLiteDatabase.isOpen()) {
                sqLiteDatabase.close();
            }
        }

        return newReceiptID;
    }


    public void updateReceipt(int receiptID, String endDay, int status) {
        SQLiteDatabase sqLiteDatabase = databaseHandler.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("endDay", endDay);
        contentValues.put("status", status);

        sqLiteDatabase.update("RECEIPT", contentValues, "receiptID = ?", new String[]{String.valueOf(receiptID)});
    }





}
