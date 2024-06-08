package com.duynguyen.sample_project.Models;

import java.util.ArrayList;

public class History {

    // 5 thuộc tính này là Header của History
    private int receiptID;
    private String fullname;
    private String note;
    private String startDay;
    private String endDay;



    // ArrayList này là Body của History, chứa tất cả ReceiptDetails
    private ArrayList<ReceiptDetail> list;
    // ReceiptDetail(receiptID, bookImage, bookName, author, quantity)


    public History(int receiptID, String fullname, String note, String startDay, String endDay, ArrayList<ReceiptDetail> list) {
        this.receiptID = receiptID;
        this.fullname = fullname;
        this.note = note;
        this.startDay = startDay;
        this.endDay = endDay;
        this.list = list;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getStartDay() {
        return startDay;
    }

    public void setStartDay(String startDay) {
        this.startDay = startDay;
    }

    public String getEndDay() {
        return endDay;
    }

    public void setEndDay(String endDay) {
        this.endDay = endDay;
    }

    public ArrayList<ReceiptDetail> getList() {
        return list;
    }

    public void setList(ArrayList<ReceiptDetail> list) {
        this.list = list;
    }
}
