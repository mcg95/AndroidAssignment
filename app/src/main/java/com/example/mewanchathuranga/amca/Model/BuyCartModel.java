package com.example.mewanchathuranga.amca.Model;

/**
 * Created by mewanchathuranga on 19/03/2018.
 */
import com.google.android.gms.maps.model.LatLng;

public class BuyCartModel {
    LatLng deliAdd;
    String foodName;
    String foodPrice;
    String noOfItems;
    String orderNo;
    String orderTime;

    public BuyCartModel(String foodName, String noOfItems, String foodPrice, LatLng deliAdd, String orderNo, String orderTime) {
        this.foodName = foodName;
        this.noOfItems = noOfItems;
        this.foodPrice = foodPrice;
        this.deliAdd = deliAdd;
        this.orderNo = orderNo;
        this.orderTime = orderTime;
    }

    public String getFoodName() {
        return this.foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public String getNoOfItems() {
        return this.noOfItems;
    }

    public void setNoOfItems(String noOfItems) {
        this.noOfItems = noOfItems;
    }

    public String getFoodPrice() {
        return this.foodPrice;
    }

    public void setFoodPrice(String foodPrice) {
        this.foodPrice = foodPrice;
    }

    public LatLng getDeliAdd() {
        return this.deliAdd;
    }

    public void setDeliAdd(LatLng deliAdd) {
        this.deliAdd = deliAdd;
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
}

