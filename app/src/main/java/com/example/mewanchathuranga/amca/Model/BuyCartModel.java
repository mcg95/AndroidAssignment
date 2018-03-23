package com.example.mewanchathuranga.amca.Model;

/**
 * Created by mewanchathuranga on 19/03/2018.
 */
public class BuyCartModel {
    String foodname;
    String price;
    String foodqty;
    String foodid;

    public BuyCartModel(){}

    public String getFoodname() {
        return foodname;
    }

    public void setFoodname(String foodname) {
        this.foodname = foodname;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getFoodqty() {
        return foodqty;
    }

    public void setFoodqty(String foodqty) {
        this.foodqty = foodqty;
    }

    public String getFoodid() {
        return foodid;
    }

    public void setFoodid(String foodid) {
        this.foodid = foodid;
    }
}

