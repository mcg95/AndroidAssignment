package com.example.mewanchathuranga.amca.Model;

/**
 * Created by mewanchathuranga on 19/03/2018.
 */

public class PendingDelivery {
    public String customerName = this.customerName;
    public String dTimeFrame = this.dTimeFrame;
    public String deliAdd = this.deliAdd;
    public String noOfItems = this.noOfItems;
    public String orderNo = this.orderNo;
    public String orderTime = this.orderTime;
    public String restName = this.restName;
    public String totalAmount = this.totalAmount;

    public String getRestName() {
        return this.restName;
    }

    public void setRestName(String restName) {
        this.restName = restName;
    }

    public String getDeliAdd() {
        return this.deliAdd;
    }

    public void setDeliAdd(String deliAdd) {
        this.deliAdd = deliAdd;
    }

    public String getNoOfItems() {
        return this.noOfItems;
    }

    public void setNoOfItems(String noOfItems) {
        this.noOfItems = noOfItems;
    }

    public String getOrderNo() {
        return this.orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getOrderTime() {
        return this.orderTime;
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }

    public String getdTimeFrame() {
        return this.dTimeFrame;
    }

    public void setdTimeFrame(String dTimeFrame) {
        this.dTimeFrame = dTimeFrame;
    }

    public String getTotalAmount() {
        return this.totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getCustomerName() {
        return this.customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }
}
