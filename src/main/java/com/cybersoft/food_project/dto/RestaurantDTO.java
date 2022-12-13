package com.cybersoft.food_project.dto;

import com.cybersoft.food_project.entity.FoodEntity;

import java.util.Set;

public class RestaurantDTO {
    private String title;
    private String image;
    private float avgRate;

    public RestaurantDTO() {
    }

    public RestaurantDTO(String title, String image, float avgRate) {
        this.title = title;
        this.image = image;
        this.avgRate = avgRate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public float getAvgRate() {
        return avgRate;
    }

    public void setAvgRate(float avgRate) {
        this.avgRate = avgRate;
    }
}
