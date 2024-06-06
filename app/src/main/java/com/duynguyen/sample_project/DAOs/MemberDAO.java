package com.duynguyen.sample_project.DAOs;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.duynguyen.sample_project.Database.DatabaseHandler;
import com.duynguyen.sample_project.Models.Book;
import com.duynguyen.sample_project.Models.Member;

import java.util.ArrayList;

public class MemberDAO {
    private DatabaseHandler databaseHandler;
    public MemberDAO(Context context) {
        databaseHandler = new DatabaseHandler(context);
    }

    public ArrayList<Member> getListMembers() {
        SQLiteDatabase sqLiteDatabase = databaseHandler.getReadableDatabase();
        ArrayList<Member> list = new ArrayList<>();

        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM MEMBER", null);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                list.add(new Member(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getInt(5)));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }

    public Member getMemberByPhoneNumber(String phoneNumber) {
        SQLiteDatabase sqLiteDatabase = databaseHandler.getReadableDatabase();
        Member member = null;
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM MEMBER WHERE phoneNumber = ?", new String[]{phoneNumber});
        if (cursor != null && cursor.moveToFirst()) {
            member = new Member(cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getString(4),
                    cursor.getInt(5));
        }

        return member;
    }
    public Member getMemberByMemberID(int memberID) {
        SQLiteDatabase sqLiteDatabase = databaseHandler.getReadableDatabase();
        Member member = null;
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM MEMBER WHERE memberID = ?", new String[]{String.valueOf(memberID)});
        if (cursor != null && cursor.moveToFirst()) {
            member = new Member(cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getString(4),
                    cursor.getInt(5));
        }
        return member;
    }

    public boolean updateMember(Member member) {
        SQLiteDatabase sqLiteDatabase = databaseHandler.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("fullname", member.getFullname());
        contentValues.put("phoneNumber", member.getPhoneNumber());
        contentValues.put("address", member.getAddress());
        contentValues.put("password", member.getPassword());
        contentValues.put("role", member.getRole());

        long check = sqLiteDatabase.update("MEMBER", contentValues, "memberID = ?", new String[]{String.valueOf(member.getMemberID())});
        return check > 0;
    }
}
