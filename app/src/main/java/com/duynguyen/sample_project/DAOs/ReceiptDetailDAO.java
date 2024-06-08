package com.duynguyen.sample_project.DAOs;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.duynguyen.sample_project.Database.DatabaseHandler;
import com.duynguyen.sample_project.Models.History;
import com.duynguyen.sample_project.Models.ReceiptDetail;

import java.util.ArrayList;

public class ReceiptDetailDAO {
    private final DatabaseHandler databaseHandler;

    public ReceiptDetailDAO(Context context) {
        databaseHandler = new DatabaseHandler(context);
    }

    // Method sử dụng khi ấn Submit để tạo phiếu mượn
    public long addReceiptDetail(int receiptID, int bookID, int status, int quantity) {
        SQLiteDatabase sqLiteDatabase = databaseHandler.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("receiptID", receiptID);
        contentValues.put("bookID", bookID);
        contentValues.put("quantity", quantity);
        contentValues.put("status", status);

        return sqLiteDatabase.insert("RECEIPTDETAIL", null, contentValues);
    }

    public ArrayList<History> getALLReceiptDetails() {
        ArrayList<History> listHistories = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = databaseHandler.getWritableDatabase();
        // firstQuery sẽ tạo ra được phần header của History
        String firstQuery = "SELECT  r.receiptID,  m.fullname, r.note,  r.startDay, r.endDay\n" +
                            "FROM MEMBER m, RECEIPT r, RECEIPTDETAIL d\n" +
                            "WHERE  m.memberID = r.memberID AND r.receiptID = d.receiptID\n" +
                            "ORDER BY r.receiptID;";


        Cursor firstCursor = sqLiteDatabase.rawQuery(firstQuery, null);
        if (firstCursor.getCount() > 0) {
            firstCursor.moveToFirst();
            do {

            } while (firstCursor.moveToNext());
        }


        firstCursor.close();
        // Second Query sẽ lấy được 1 item phần body của History
        // Sử dụng vòng lặp for cho receiptID để lấy toàn bộ phần body
        String secondQuery = "SELECT r.receiptID,  b.bookImage, b.bookName, b.author, d.quantity\n" +
                             "FROM BOOK b, RECEIPT r, RECEIPTDETAIL d\n" +
                             "WHERE b.bookID = d.bookID and r.receiptID = d.receiptID AND r.receiptID = 3;";
        Cursor secondCursor = sqLiteDatabase.rawQuery(secondQuery, null);

        if (secondCursor.getCount() > 0) {
            secondCursor.moveToFirst();
            do {

            } while (secondCursor.moveToNext());
        }
        secondCursor.close();


        return listHistories;
    }

    public void plusAReceiptDetail(ReceiptDetail detail) {
        int bookID = detail.getBookID();
        int receiptID = detail.getReceiptID();
        SQLiteDatabase sqLiteDatabase = databaseHandler.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("QUANTITY", detail.getQuantity() + 1);
        sqLiteDatabase.update("RECEIPTDETAIL", contentValues, "receiptID = ? AND bookID = ?", new String[]{String.valueOf(receiptID), String.valueOf(bookID)});
    }

    public void minusAReceiptDetail(ReceiptDetail detail) {
        int bookID = detail.getBookID();
        int receiptID = detail.getReceiptID();
        SQLiteDatabase sqLiteDatabase = databaseHandler.getWritableDatabase();
        int quantity = detail.getQuantity();
        if (quantity == 1) {
            sqLiteDatabase.delete("RECEIPTDETAIL", "receiptID = ? AND bookID = ?", new String[]{String.valueOf(receiptID), String.valueOf(bookID)});

        } else {
            ContentValues contentValues = new ContentValues();
            contentValues.put("QUANTITY", quantity - 1);
            sqLiteDatabase.update("RECEIPTDETAIL", contentValues, "receiptID = ? AND bookID = ?", new String[]{String.valueOf(receiptID), String.valueOf(bookID)});
        }

    }

    public boolean deleteAReceiptDetail(int receiptID, int bookID) {
        SQLiteDatabase sqLiteDatabase = databaseHandler.getWritableDatabase();
        int check = sqLiteDatabase.delete("RECEIPTDETAIL", "receiptID = ? AND bookID = ?", new String[]{String.valueOf(receiptID), String.valueOf(bookID)});
        return check > 0;
    }
}
