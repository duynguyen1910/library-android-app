package com.duynguyen.sample_project.Models;

public class ReceiptDetail {
    private int bookID, receiptID;
    private String bookName, bookAuthor;
    private int quantity, status;

    public ReceiptDetail() {
    }

    public ReceiptDetail(int bookID, int receiptID, String bookName, String bookAuthor, int quantity, int status) {
        this.bookID = bookID;
        this.receiptID = receiptID;
        this.bookName = bookName;
        this.bookAuthor = bookAuthor;
        this.quantity = quantity;
        this.status = status;
    }

    public int getReceiptID() {
        return receiptID;
    }

    public void setReceiptID(int receiptID) {
        this.receiptID = receiptID;
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

    public String getBookAuthor() {
        return bookAuthor;
    }

    public void setBookAuthor(String bookAuthor) {
        this.bookAuthor = bookAuthor;
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
