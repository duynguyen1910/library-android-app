package com.duynguyen.sample_project.DAOs;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.duynguyen.sample_project.Database.DatabaseHandler;
import com.duynguyen.sample_project.Models.Member;

import java.util.ArrayList;

public class MemberDAO {
    private final DatabaseHandler databaseHandler;

    public MemberDAO(Context context) {
        databaseHandler = new DatabaseHandler(context);
    }

    public ArrayList<Member> getListMembers() {
        SQLiteDatabase sqLiteDatabase = null;
        ArrayList<Member> list = new ArrayList<>();

        try {
            sqLiteDatabase = databaseHandler.getReadableDatabase();
            try (Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM MEMBER", null)) {
                if (cursor.moveToFirst()) {
                    do {
                        list.add(new Member(
                                cursor.getInt(0),
                                cursor.getString(1),
                                cursor.getInt(2),
                                cursor.getString(3),
                                cursor.getString(4),
                                cursor.getString(5),
                                cursor.getInt(6)
                        ));
                    } while (cursor.moveToNext());
                }
            }
        } finally {
            if (sqLiteDatabase != null && sqLiteDatabase.isOpen()) {
                sqLiteDatabase.close();
            }
        }

        return list;
    }


    public Member getMemberByPhoneNumber(String phoneNumber) {
        SQLiteDatabase sqLiteDatabase = null;
        Member member = null;

        try {
            sqLiteDatabase = databaseHandler.getReadableDatabase();
            try (Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM MEMBER WHERE phoneNumber = ?", new String[]{phoneNumber})) {
                if (cursor.moveToFirst()) {
                    member = new Member(
                            cursor.getInt(0),
                            cursor.getString(1),
                            cursor.getInt(2),
                            cursor.getString(3),
                            cursor.getString(4),
                            cursor.getString(5),
                            cursor.getInt(6)
                    );
                }
            }
        } finally {
            if (sqLiteDatabase != null && sqLiteDatabase.isOpen()) {
                sqLiteDatabase.close();
            }
        }

        return member;
    }


    public boolean updateMember(Member member) {
        SQLiteDatabase sqLiteDatabase = null;
        long check;

        try {
            sqLiteDatabase = databaseHandler.getWritableDatabase();

            ContentValues contentValues = new ContentValues();
            contentValues.put("fullname", member.getFullname());
            contentValues.put("phoneNumber", member.getPhoneNumber());
            contentValues.put("address", member.getAddress());
            contentValues.put("password", member.getPassword());
            contentValues.put("role", member.getRole());

            check = sqLiteDatabase.update("MEMBER", contentValues, "memberID = ?", new String[]{String.valueOf(member.getMemberID())});
        } finally {
            if (sqLiteDatabase != null && sqLiteDatabase.isOpen()) {
                sqLiteDatabase.close();
            }
        }

        return check > 0;
    }

    public boolean updateMemberPassword(String newPassword, int memberID) {
        SQLiteDatabase sqLiteDatabase = null;
        long check;

        try {
            sqLiteDatabase = databaseHandler.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put("password", newPassword);

            check = sqLiteDatabase.update("MEMBER", contentValues, "memberID = ?", new String[]{String.valueOf(memberID)});
        } finally {
            if (sqLiteDatabase != null && sqLiteDatabase.isOpen()) {
                sqLiteDatabase.close();
            }
        }

        return check > 0;
    }

}
