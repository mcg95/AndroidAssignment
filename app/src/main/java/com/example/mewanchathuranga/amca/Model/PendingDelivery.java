package com.example.mewanchathuranga.amca.Model;

/**
 * Created by mewanchathuranga on 19/03/2018.
 */

public class PendingDelivery {
    public String customerName = this.customerName;
    public String deliAdd = this.deliAdd;
    public String noOfItems = this.noOfItems;
    public String orderTime = this.orderTime;
    public String totalAmount = this.totalAmount;
    public String latLng = this.latLng;

    public String getCustomerName() {
        return customerName;
    }

    public PendingDelivery(){}


    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getDeliAdd() {
        return deliAdd;
    }

    public void setDeliAdd(String deliAdd) {
        this.deliAdd = deliAdd;
    }

    public String getNoOfItems() {
        return noOfItems;
    }

    public void setNoOfItems(String noOfItems) {
        this.noOfItems = noOfItems;
    }

    public String getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getLatLng() {
        return latLng;
    }

    public void setLatLng(String latLng) {
        this.latLng = latLng;
    }

    public PendingDelivery(String customerName, String deliAdd, String noOfItems, String orderTime, String totalAmount, String latLng) {

        this.customerName = customerName;
        this.deliAdd = deliAdd;
        this.noOfItems = noOfItems;
        this.orderTime = orderTime;
        this.totalAmount = totalAmount;
        this.latLng = latLng;
    }
}