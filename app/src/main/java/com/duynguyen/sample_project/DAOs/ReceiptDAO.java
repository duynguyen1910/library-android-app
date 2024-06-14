package com.duynguyen.sample_project.DAOs;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;
import com.duynguyen.sample_project.Database.DatabaseHandler;
import com.duynguyen.sample_project.Models.Member;
import com.duynguyen.sample_project.Models.Receipt;

public class ReceiptDAO {
    private final DatabaseHandler databaseHandler;
    private final Context context;

    public ReceiptDAO(Context context) {
        this.context = context;
        databaseHandler = new DatabaseHandler(context);
    }

    public boolean checkBorrowAbility(Member member) {
        SQLiteDatabase sqLiteDatabase = null;
        try {
            sqLiteDatabase = databaseHandler.getReadableDatabase();
            String query = "SELECT * FROM RECEIPT\n" +
                    "WHERE memberID = ? and status = 0";
            try (Cursor cursor = sqLiteDatabase.rawQuery(query, new String[]{String.valueOf(member.getMemberID())})) {
                if (cursor.getCount() > 0) {
                    return false;
                }
            }
        } finally {
            if (sqLiteDatabase != null && sqLiteDatabase.isOpen()) {
                sqLiteDatabase.close();
            }
        }
        return true;
    }




    public int addReceipt(Receipt receipt) {

        int newReceiptID;
        SQLiteDatabase sqLiteDatabase = null;
        Cursor cursor = null;

        try {
            sqLiteDatabase = databaseHandler.getWritableDatabase();
            String query = "SELECT * FROM RECEIPT WHERE memberID = ? and status = 0";

            cursor = sqLiteDatabase.rawQuery(query, new String[]{String.valueOf(receipt.getMemberID())});
            if (cursor.getCount() > 0) {
                Toast.makeText(context, "Thành viên này có phiếu mượn chưa trả. Không thể tạo phiếu!", Toast.LENGTH_SHORT).show();
                return -1;
            }

            ContentValues contentValues = new ContentValues();
            contentValues.put("creator", receipt.getCreator());
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

    public int getReceiptDetailCount() {
        SQLiteDatabase sqLiteDatabase = databaseHandler.getWritableDatabase();
        String query = " SELECT b.bookID, b.bookName, b.author, m.memberID, m.fullname, r.startDay, r.endDay, r.note, r.receiptID,  d.quantity, d.status\n" +
                "   FROM BOOK b, MEMBER m, RECEIPT r, RECEIPTDETAIL d\n" +
                "   WHERE b.bookID = d.bookID and m.memberID = r.memberID \n" +
                "   and r.receiptID = d.receiptID";
        Cursor cursor = sqLiteDatabase.rawQuery(query, null);
        int count = cursor.getCount();
        cursor.close();
        return count;
    }

    @SuppressLint("Range")
    public int getReceiptTotalByMemberID(int memberID) {
        SQLiteDatabase sqLiteDatabase = databaseHandler.getReadableDatabase();
        int totalReceipt = 0;

        String query = "SELECT COUNT(*) AS total FROM RECEIPT WHERE memberID = ?";
        Cursor cursor = sqLiteDatabase.rawQuery(query, new String[]{String.valueOf(memberID)});

        if (cursor.moveToFirst()) {
            totalReceipt = cursor.getInt(cursor.getColumnIndex("total"));
        }

        cursor.close();
        return totalReceipt;
    }

}
