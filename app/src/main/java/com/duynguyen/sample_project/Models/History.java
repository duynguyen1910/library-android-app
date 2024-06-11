package com.duynguyen.sample_project.Models;

import java.io.Serializable;
import java.util.ArrayList;

public class History implements Serializable {

    // 5 thuộc tính này là Header của History
    private int receiptID;
    private String fullname;
    private String creator;
    private String phoneNumber;
    private String address;
    private String note;
    private String startDay;
    private String endDay;
    private int status;


    // ArrayList này là Body của History, chứa tất cả ReceiptDetails
    private ArrayList<ReceiptDetail> detailsList;
    // ReceiptDetail(receiptID, bookID, bookImage, bookName, author, quantity)


    public History(int receiptID, String creator, String fullname, String phoneNumber, String address, String startDay, String endDay, String note, int status, ArrayList<ReceiptDetail> detailsList) {
        this.receiptID = receiptID;
        this.creator = creator;
        this.fullname = fullname;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.startDay = startDay;
        this.endDay = endDay;
        this.note = note;
        this.status = status;
        this.detailsList = detailsList;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public int getReceiptID() {
        return receiptID;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setReceiptID(int receiptID) {
        this.receiptID = receiptID;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
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

    public ArrayList<ReceiptDetail> getDetailsList() {
        return detailsList;
    }

    public void setDetailsList(ArrayList<ReceiptDetail> detailsList) {
        this.detailsList = detailsList;
    }
}
