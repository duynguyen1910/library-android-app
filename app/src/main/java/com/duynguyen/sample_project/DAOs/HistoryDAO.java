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
    private DatabaseHandler databaseHandler;

    public HistoryDAO(Context context) {
        databaseHandler = new DatabaseHandler(context);
    }

    public ArrayList<History> getALLHistories() {
        ArrayList<History> listHistories = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = databaseHandler.getWritableDatabase();

        // Bước 1: Tạo header của History
        // firstQuery sẽ tạo ra được phần header của History
        String firstQuery = "SELECT  r.receiptID, m.fullname, r.startDay, r.endDay, r.note, r.status\n" +
                "FROM MEMBER m, RECEIPT r, RECEIPTDETAIL d\n" +
                "WHERE  m.memberID = r.memberID AND r.receiptID = d.receiptID\n" +
                "ORDER BY r.receiptID;";


        ArrayList<Receipt> headers = new ArrayList<>();
        Cursor firstCursor = sqLiteDatabase.rawQuery(firstQuery, null);
        if (firstCursor.getCount() > 0) {
            firstCursor.moveToFirst();
            do {
                headers.add(new Receipt(
                        firstCursor.getInt(0),
                        firstCursor.getString(1),
                        firstCursor.getString(2),
                        firstCursor.getString(3),
                        firstCursor.getString(4),
                        firstCursor.getInt(5)
                ));

            } while (firstCursor.moveToNext());
        }


        firstCursor.close();

        // Bước 2: Tạo body của History
        // Second Query sẽ lấy được 1 item phần body của History
        // Sử dụng vòng lặp for cho receiptID để lấy toàn bộ phần body

        for (Receipt header : headers) {
            String secondQuery = "SELECT r.receiptID, b.bookID, b.bookImage, b.bookName, b.author, d.quantity\n" +
                    "FROM BOOK b, RECEIPT r, RECEIPTDETAIL d\n" +
                    "WHERE b.bookID = d.bookID and r.receiptID = d.receiptID AND r.receiptID = ?;";
            Cursor secondCursor = sqLiteDatabase.rawQuery(secondQuery, new String[]{String.valueOf(header.getReceiptID())});

            ArrayList<ReceiptDetail> body = new ArrayList<>();
            if (secondCursor.getCount() > 0) {
                secondCursor.moveToFirst();
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
            secondCursor.close();

            // Bước 3: Kết hợp header và body để được History hoàn chỉnh
            listHistories.add(new History(
                    header.getReceiptID(),
                    header.getFullname(),
                    header.getStartDay(),
                    header.getEndDay(),
                    header.getNote(),
                    body
            ));
        }
        return listHistories;
    }
}
