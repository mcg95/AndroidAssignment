package com.example.mewanchathuranga.amca.Model;

/**
 * Created by mewanchathuranga on 19/03/2018.
 */
public class FoodMenuModel {
    public String categoryid;
    public String image;
    public String name;

    public FoodMenuModel(String name, String image, String categoryid) {
        this.name = name;
        this.image = image;
        this.categoryid = categoryid;
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

    public String getCategoryid() {
        return this.categoryid;
    }

    public void setCategoryid(String categoryid) {
        this.categoryid = categoryid;
    }
}
