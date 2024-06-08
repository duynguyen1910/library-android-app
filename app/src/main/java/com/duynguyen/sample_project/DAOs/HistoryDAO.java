package com.duynguyen.sample_project.DAOs;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.duynguyen.sample_project.Database.DatabaseHandler;
import com.duynguyen.sample_project.Models.History;
import com.duynguyen.sample_project.Models.Receipt;
import com.duynguyen.sample_project.Models.ReceiptDetail;

import java.util.ArrayList;

public class HistoryDAO {
    private final DatabaseHandler databaseHandler;

    public HistoryDAO(Context context) {
        databaseHandler = new DatabaseHandler(context);
    }

    public ArrayList<History> getAllHistories() {
        ArrayList<History> listHistories = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = null;

        try {
            sqLiteDatabase = databaseHandler.getWritableDatabase();

            // Step 1: Create the header part of History
            String firstQuery = "SELECT r.receiptID, m.fullname, m.phoneNumber, m.address, r.startDay, r.endDay, r.note, r.status " +
                    "FROM MEMBER m, RECEIPT r, RECEIPTDETAIL d " +
                    "WHERE m.memberID = r.memberID AND r.receiptID = d.receiptID " +
                    "GROUP BY r.receiptID";

            ArrayList<Receipt> headers = new ArrayList<>();
            try (Cursor firstCursor = sqLiteDatabase.rawQuery(firstQuery, null)) {
                if (firstCursor.moveToFirst()) {
                    do {
                        headers.add(new Receipt(
                                firstCursor.getInt(0),
                                firstCursor.getString(1),
                                firstCursor.getString(2),
                                firstCursor.getString(3),
                                firstCursor.getString(4),
                                firstCursor.getString(5),
                                firstCursor.getString(6),
                                firstCursor.getInt(7)
                        ));
                    } while (firstCursor.moveToNext());
                }
            }

            // Step 2: Create the body part of History
            String secondQuery = "SELECT r.receiptID, b.bookID, b.bookImage, b.bookName, b.author, d.quantity " +
                    "FROM BOOK b, RECEIPT r, RECEIPTDETAIL d " +
                    "WHERE b.bookID = d.bookID AND r.receiptID = d.receiptID AND r.receiptID = ?";

            for (Receipt header : headers) {
                ArrayList<ReceiptDetail> body = new ArrayList<>();
                try (Cursor secondCursor = sqLiteDatabase.rawQuery(secondQuery, new String[]{String.valueOf(header.getReceiptID())})) {
                    if (secondCursor.moveToFirst()) {
                        do {
                            body.add(new ReceiptDetail(
                                    secondCursor.getInt(0),
                                    secondCursor.getInt(1),
                                    secondCursor.getString(2),
                                    secondCursor.getString(3),
                                    secondCursor.getString(4),
                                    secondCursor.getInt(5)
                            ));
                        } while (secondCursor.moveToNext());
                    }
                }

                // Step 3: Combine header and body to get a complete History
                listHistories.add(new History(
                        header.getReceiptID(),
                        header.getFullname(),
                        header.getPhoneNumber(),
                        header.getAddress(),
                        header.getStartDay(),
                        header.getEndDay(),
                        header.getNote(),
                        header.getStatus(),
                        body
                ));
            }
        } finally {
            // Ensure the database is closed
            if (sqLiteDatabase != null && sqLiteDatabase.isOpen()) {
                sqLiteDatabase.close();
            }
        }

        return listHistories;
    }


    public ArrayList<History> getAllBorrowingHistories() {
        ArrayList<History> listHistories = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = null;

        try {
            sqLiteDatabase = databaseHandler.getWritableDatabase();

            // Step 1: Create the header part of History
            String firstQuery = "SELECT r.receiptID, m.fullname, m.phoneNumber, m.address, r.startDay, r.endDay, r.note, r.status " +
                    "FROM MEMBER m, RECEIPT r, RECEIPTDETAIL d " +
                    "WHERE m.memberID = r.memberID AND r.receiptID = d.receiptID AND r.status = 0 " +
                    "GROUP BY r.receiptID";

            ArrayList<Receipt> headers = new ArrayList<>();
            try (Cursor firstCursor = sqLiteDatabase.rawQuery(firstQuery, null)) {
                if (firstCursor.moveToFirst()) {
                    do {
                        headers.add(new Receipt(
                                firstCursor.getInt(0),
                                firstCursor.getString(1),
                                firstCursor.getString(2),
                                firstCursor.getString(3),
                                firstCursor.getString(4),
                                firstCursor.getString(5),
                                firstCursor.getString(6),
                                firstCursor.getInt(7)
                        ));
                    } while (firstCursor.moveToNext());
                }
            }

            // Step 2: Create the body part of History
            String secondQuery = "SELECT r.receiptID, b.bookID, b.bookImage, b.bookName, b.author, d.quantity " +
                    "FROM BOOK b, RECEIPT r, RECEIPTDETAIL d " +
                    "WHERE b.bookID = d.bookID AND r.receiptID = d.receiptID AND r.receiptID = ?";

            for (Receipt header : headers) {
                ArrayList<ReceiptDetail> body = new ArrayList<>();
                try (Cursor secondCursor = sqLiteDatabase.rawQuery(secondQuery, new String[]{String.valueOf(header.getReceiptID())})) {
                    if (secondCursor.moveToFirst()) {
                        do {
                            body.add(new ReceiptDetail(
                                    secondCursor.getInt(0),
                                    secondCursor.getInt(1),
                                    secondCursor.getString(2),
                                    secondCursor.getString(3),
                                    secondCursor.getString(4),
                                    secondCursor.getInt(5)
                            ));
                        } while (secondCursor.moveToNext());
                    }
                }

                // Step 3: Combine header and body to get a complete History
                listHistories.add(new History(
                        header.getReceiptID(),
                        header.getFullname(),
                        header.getPhoneNumber(),
                        header.getAddress(),
                        header.getStartDay(),
                        header.getEndDay(),
                        header.getNote(),
                        header.getStatus(),
                        body
                ));
            }
        } finally {
            // Ensure the database is closed
            if (sqLiteDatabase != null && sqLiteDatabase.isOpen()) {
                sqLiteDatabase.close();
            }
        }

        return listHistories;
    }


}
