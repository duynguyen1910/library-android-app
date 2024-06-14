package com.duynguyen.sample_project.Models;

import java.io.Serializable;

public class Book implements Serializable {
    private int bookID;
    private String bookName;
    private String bookImageURI;
    private String desc;
    private String author;
    private int vote;
    private int quantity;
    private int inStock;
    private int bookCategoryID;
    private int totalBooksBorrowed;



    public Book(int bookID, String bookName, String bookImageURI, String desc, int vote, int quantity, int bookCategoryID) {
        this.bookID = bookID;
        this.bookName = bookName;
        this.bookImageURI = bookImageURI;
        this.desc = desc;
        this.vote = vote;
        this.quantity = quantity;
        this.bookCategoryID = bookCategoryID;
    }


    public int getInStock() {
        return inStock;
    }

    public void setInStock(int inStock) {
        this.inStock = inStock;
    }

    public Book(int bookID, String bookName, String bookImageURI, String desc, String author, int inStock, int bookCategoryID) {
        this.bookID = bookID;
        this.bookName = bookName;
        this.bookImageURI = bookImageURI;
        this.desc = desc;
        this.author = author;
        this.inStock = inStock;
        this.bookCategoryID = bookCategoryID;
    }

    //"CREATE TABLE BOOK(" +
//        "bookID INTEGER PRIMARY KEY AUTOINCREMENT," +
//        " bookName TEXT NOT NULL," +
//        " bookImage TEXT NOT NULL," +
//        " description TEXT NOT NULL," +
//        " author TEXT NOT NULL," +
//        " inStock INTEGER NOT NULL," +
//        "categoryID INTEGER NOT NULL," +
//        "FOREIGN KEY (categoryID) REFERENCES CATEGORY(categoryID))";
    public Book(int bookID, String bookName, String bookImageURI, String desc, String author, int quantity) {
        this.bookID = bookID;
        this.bookName = bookName;
        this.bookImageURI = bookImageURI;
        this.desc = desc;
        this.author = author;
        this.quantity = quantity;
    }

    public Book(String bookName, String bookImageURI, String desc, String author, int quantity, int bookCategoryID) {
        this.bookName = bookName;
        this.bookImageURI = bookImageURI;
        this.desc = desc;
        this.author = author;
        this.quantity = quantity;
        this.bookCategoryID = bookCategoryID;
    }

    public Book(String bookImageURI, String bookName, int bookCategoryID) {
        this.bookImageURI = bookImageURI;
        this.bookName = bookName;
        this.bookCategoryID = bookCategoryID;
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

    public String getBookImageURI() {
        return bookImageURI;
    }

    public void setBookImageURI(String bookImageURI) {
        this.bookImageURI = bookImageURI;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getVote() {
        return vote;
    }

    public void setVote(int vote) {
        this.vote = vote;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getBookCategoryID() {
        return bookCategoryID;
    }

    public void setBookCategoryID(int bookCategoryID) {
        this.bookCategoryID = bookCategoryID;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getTotalBooksBorrowed() {
        return totalBooksBorrowed;
    }

    public void setTotalBooksBorrowed(int totalBooksBorrowed) {
        this.totalBooksBorrowed = totalBooksBorrowed;
    }
}
