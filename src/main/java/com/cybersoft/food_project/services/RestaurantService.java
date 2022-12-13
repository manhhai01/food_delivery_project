package com.cybersoft.food_project.services;

import com.cybersoft.food_project.dto.RestaurantDTO;
import com.cybersoft.food_project.dto.RestaurantDetailDTO;

import java.util.List;

public interface RestaurantService {
    List<RestaurantDTO> getRestaurants();
    RestaurantDetailDTO getDetaiRestaurant(int id);
    void clearAllCache();
}
