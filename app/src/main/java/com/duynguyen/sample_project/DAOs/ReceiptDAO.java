package com.duynguyen.sample_project.DAOs;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.duynguyen.sample_project.Database.DatabaseHandler;
import com.duynguyen.sample_project.Models.Receipt;
import com.duynguyen.sample_project.Models.ReceiptDetail;

import java.util.ArrayList;

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

    public ArrayList<ReceiptDetail> getReceipt(){
        ArrayList<ReceiptDetail> receipt = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = databaseHandler.getWritableDatabase();
        String query = " SELECT b.bookID, b.bookName, b.author, m.memberID, m.fullname, r.startDay, r.endDay, r.note, r.receiptID,  d.quantity, d.status\n" +
                "   FROM BOOK b, MEMBER m, RECEIPT r, RECEIPTDETAIL d\n" +
                "   WHERE b.bookID = d.bookID and m.memberID = r.memberID \n" +
                "   and r.receiptID = d.receiptID";
        Cursor cursor = sqLiteDatabase.rawQuery(query, null);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                receipt.add(new ReceiptDetail(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getInt(3),
                        cursor.getString(4),
                        cursor.getString(5),
                        cursor.getString(6),
                        cursor.getString(7),
                        cursor.getInt(8),
                        cursor.getInt(9),
                        cursor.getInt(10)
                ));
            } while (cursor.moveToNext());
        }
        cursor.close();

        return receipt;
    }

    public int getReceiptDetailCount(){
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
}
