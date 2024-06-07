package com.duynguyen.sample_project.Models;

public class Receipt {
    private int ID;
    private String startDay,endDay, note;
    private int memberID;



    public Receipt() {
    }


    // Constructor này dùng để lấy phiếu mượn trả về từ database qua phương thức, ReceiptDAO.getReceiptByMemberID(int memberID)
    public Receipt(int ID, String startDay, String endDay, String note, int memberID) {
        this.ID = ID;
        this.startDay = startDay;
        this.endDay = endDay;
        this.note = note;
        this.memberID = memberID;
    }

    // Constructor này để tạo phiếu mượn, insert vào database, sử dụng phương thức,  ReceiptDAO.addReceipt(Receipt receipt)
    public Receipt(String startDay, String endDay, String note, int memberID) {
        this.startDay = startDay;
        this.endDay = endDay;
        this.note = note;
        this.memberID = memberID;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getMemberID() {
        return memberID;
    }

    public void setMemberID(int memberID) {
        this.memberID = memberID;
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

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
