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

    public int addReceipt(Receipt receipt) {
        // Phương thức này trả về id của dòng được insert vào database nếu thành công
        // trả về -1 nếu thành viên chưa trả sách hoặc đã trả sách nhưng có lỗi xảy ra trong quá trình insert
        int newReceiptID;

        SQLiteDatabase sqLiteDatabase = databaseHandler.getWritableDatabase();

        // Kiểm tra đã trả phiếu mượn trước đó chưa
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM RECEIPT WHERE memberID = ?", new String[]{String.valueOf(receipt.getMemberID())});

//        boolean canBorrow = true;
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();  // Kiểm tra nếu có ít nhất một dòng kết quả
            // Lấy giá trị cột endDay của dòng đầu tiên
            String endDay = cursor.getString(2);
            if (endDay.length() == 0) {
                Toast.makeText(context, "Thành viên này có phiếu chưa trả. Không thể mượn", Toast.LENGTH_SHORT).show();
                newReceiptID = -1;
                return newReceiptID;
            }
        }
        cursor.close();
        ContentValues contentValues = new ContentValues();
        contentValues.put("startDay", receipt.getStartDay());
        contentValues.put("endDay", receipt.getEndDay());
        contentValues.put("note", receipt.getNote());
        contentValues.put("memberID", receipt.getMemberID());
        contentValues.put("status", receipt.getStatus());


        long check = sqLiteDatabase.insert("RECEIPT", null, contentValues);
        // phương thức insert return  ID của dòng mới được insert vào database nếu thành công
        // return -1 nếu có lỗi xảy ra
        if (check != -1) {
            Toast.makeText(context, "Receipt created successfully", Toast.LENGTH_SHORT).show();
            newReceiptID = (int) check;

        } else {
            Toast.makeText(context, "Receipt created failed", Toast.LENGTH_SHORT).show();
            newReceiptID = -1;

        }

        return newReceiptID;



    }

    public boolean updateReceipt(int memberID, String endDay, String note) {
        SQLiteDatabase sqLiteDatabase = databaseHandler.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("endDay", endDay);
        contentValues.put("note", note);
        long check = sqLiteDatabase.update("RECEIPT", contentValues, "memberID = ?", new String[]{String.valueOf(memberID)});
        return check > 0;
    }

    public ArrayList<Receipt> getReceiptByMemberID(int memberID) {

        // This method returns a new ArrayList<Receip>  with constructor Receipt(receptID, startDay, endDay, note, memeberID, status);
        ArrayList<Receipt> receipt = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = databaseHandler.getWritableDatabase();
        String query = "SELECT * FROM RECEIPT WHERE memberID = ?";
        Cursor cursor = sqLiteDatabase.rawQuery(query, new String[]{String.valueOf(memberID)});
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                receipt.add(new Receipt(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getInt(4),
                        cursor.getInt(5)
                ));
            } while (cursor.moveToNext());
        }
        cursor.close();

        return receipt;
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
}
