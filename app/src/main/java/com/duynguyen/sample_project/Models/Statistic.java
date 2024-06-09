package com.duynguyen.sample_project.Models;

public class Statistic {
    private int bookID;
    private String bookImage;
    private String bookName;
    private String author;
    private int SumQuantity;

    public Statistic(int bookID, String bookImage, String bookName, String author, int sumQuantity) {
        this.bookID = bookID;
        this.bookImage = bookImage;
        this.bookName = bookName;
        this.author = author;
        SumQuantity = sumQuantity;
    }

    public int getBookID() {
        return bookID;
    }

    public void setBookID(int bookID) {
        this.bookID = bookID;
    }

    public String getBookImage() {
        return bookImage;
    }

    public void setBookImage(String bookImage) {
        this.bookImage = bookImage;
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

    public int getSumQuantity() {
        return SumQuantity;
    }

    public void setSumQuantity(int sumQuantity) {
        SumQuantity = sumQuantity;
    }
}
