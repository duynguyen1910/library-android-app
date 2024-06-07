package com.duynguyen.sample_project.Models;

public class ReceiptDetail {
    private int receiptID, bookID;
    private String bookImageURI, bookName, author;
    private int memberID;
    private String fullname, startDay, endDay, note;
    private int quantity;
    private int status; // 0: có sẵn, 1: đang mượn, 2: bị mất

    public ReceiptDetail() {
    }


    // Constructor này để tạo UI hiển thị cartItem khi tạo phiếu mượn (Không insert vào database)
    public ReceiptDetail(int bookID, String bookImageURI, String bookName, String author, int quantity, int status) {
        this.bookID = bookID;
        this.bookImageURI = bookImageURI;
        this.bookName = bookName;
        this.author = author;
        this.quantity = quantity;
        this.status = status;
    }

    // Constructor này để tạo chi tiết phiếu mượn và insert vào database,
    // sử dụng phương thức ReceiptDetailDAO.addReceiptDetail(int receiptID, int bookID, int status, int quantity)
    public ReceiptDetail(int receiptID, int bookID, int quantity, int status) {
        this.receiptID = receiptID;
        this.bookID = bookID;
        this.quantity = quantity;
        this.status = status;
    }

    public ReceiptDetail(int receiptID, int bookID, String bookImageURI, String bookName, String author, int memberID, String fullname, String startDay, String endDay, String note, int quantity, int status) {
        this.receiptID = receiptID;
        this.bookID = bookID;
        this.bookImageURI = bookImageURI;
        this.bookName = bookName;
        this.author = author;
        this.memberID = memberID;
        this.fullname = fullname;
        this.startDay = startDay;
        this.endDay = endDay;
        this.note = note;
        this.quantity = quantity;
        this.status = status;
    }

    public String getBookImageURI() {
        return bookImageURI;
    }

    public void setBookImageURI(String bookImageURI) {
        this.bookImageURI = bookImageURI;
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
