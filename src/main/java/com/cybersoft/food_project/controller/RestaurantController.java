package com.cybersoft.food_project.controller;

import com.cybersoft.food_project.dto.RestaurantDTO;
import com.cybersoft.food_project.dto.RestaurantDetailDTO;
import com.cybersoft.food_project.services.RestaurantService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/restaurant")
public class RestaurantController {

    @Autowired
    RestaurantService restaurantService;

    @Autowired
    RedisTemplate redisTemplate;

    @GetMapping("")
    public ResponseEntity<?> getRestaurant() {

        List<RestaurantDTO> restaurants = restaurantService.getRestaurants();

        System.out.println("Kiem tra:" + restaurants.size());
        return new ResponseEntity<>(restaurants, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getDetailRestaurant(@PathVariable("id") int id) {

        RestaurantDetailDTO detailDTO = restaurantService.getDetaiRestaurant(id);

        return new ResponseEntity<>(detailDTO, HttpStatus.OK);
    }

    @GetMapping("/clear-cache")
    public ResponseEntity<?> clearCacheRestaurant() {
        restaurantService.clearAllCache();
        return new ResponseEntity<>("", HttpStatus.OK);
    }

}
