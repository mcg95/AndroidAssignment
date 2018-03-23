package com.example.mewanchathuranga.amca.Model;

/**
 * Created by mewanchathuranga on 19/03/2018.
 */


public class FoodListModel {
    private String description;
    private String foodid;
    private String image;
    private String menuid;
    private String name;
    private String price;

    public FoodListModel(String name, String image, String description, String price, String menuid, String foodkey) {
        this.name = name;
        this.image = image;
        this.description = description;
        this.price = price;
        this.menuid = menuid;
        this.foodid = foodkey;
    }
    public FoodListModel(){}

    public String getFoodid() {
        return this.foodid;
    }

    public void setFoodid(String foodid) {
        this.foodid = foodid;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return this.image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrice() {
        return this.price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getMenuid() {
        return this.menuid;
    }

    public void setMenuid(String menuid) {
        this.menuid = menuid;
    }
}
