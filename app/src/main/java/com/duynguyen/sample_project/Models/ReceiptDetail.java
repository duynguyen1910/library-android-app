package com.duynguyen.sample_project.Models;

public class ReceiptDetail {
    private int bookID;
    private String bookName, author;
    private int memberID;
    private String fullname, startDay, endDay, note;
    private int receiptID, quantity, status;

    public ReceiptDetail() {
    }

    public ReceiptDetail(int bookID, String bookName, String author, int memberID, String fullname, String startDay, String endDay, String note, int receiptID, int quantity, int status) {
        this.bookID = bookID;
        this.bookName = bookName;
        this.author = author;
        this.memberID = memberID;
        this.fullname = fullname;
        this.startDay = startDay;
        this.endDay = endDay;
        this.note = note;
        this.receiptID = receiptID;
        this.quantity = quantity;
        this.status = status;
    }

    public int getBookID() {
        return bookID;
    }

    public void setBookID(int bookID) {
        this.bookID = bookID;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getMemberID() {
        return memberID;
    }

    public void setMemberID(int memberID) {
        this.memberID = memberID;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
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

    public int getReceiptID() {
        return receiptID;
    }

    public void setReceiptID(int receiptID) {
        this.receiptID = receiptID;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
