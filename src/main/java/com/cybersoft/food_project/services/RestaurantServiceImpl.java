package com.cybersoft.food_project.services;

import com.cybersoft.food_project.dto.RestaurantDTO;
import com.cybersoft.food_project.dto.RestaurantDetailDTO;
import com.cybersoft.food_project.entity.FoodEntity;
import com.cybersoft.food_project.entity.RestaurantEntity;
import com.cybersoft.food_project.entity.RestaurantReviewEntity;
import com.cybersoft.food_project.model.FoodModel;
import com.cybersoft.food_project.repository.RestaurantRepository;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RestaurantServiceImpl implements RestaurantService {

    @Autowired
    RestaurantRepository restaurantRepository;

    @Autowired
    RedisTemplate redisTemplate;

    @Override
    public List<RestaurantDTO> getRestaurants() {

        List<RestaurantDTO> dtos = new ArrayList<>();
        List<RestaurantEntity> restaurantEntities = restaurantRepository.findAll();

        for (RestaurantEntity data: restaurantEntities) {
            RestaurantDTO restaurantDTO = new RestaurantDTO();

            restaurantDTO.setTitle(data.getName());
            restaurantDTO.setImage(data.getImage());

            float avgRate = 0;
            float sumRate = 0;
            for (RestaurantReviewEntity dataReview: data.getRestaurantReviews()) {
                sumRate += dataReview.getRate();
            }

            if(data.getRestaurantReviews().size() > 0) {
                avgRate = sumRate/data.getRestaurantReviews().size();
            }

            restaurantDTO.setAvgRate(avgRate);
            dtos.add(restaurantDTO);
        }

        return dtos;
    }

    @Override
//    @Cacheable("detail_restaurant")
    public RestaurantDetailDTO getDetaiRestaurant(int id) {

        // set timeout khi redis chet.

        RestaurantDetailDTO restaurantDetailDTO = new RestaurantDetailDTO();
        String key = "res" + id;
        Gson gson = new Gson();

        // loi nullPoiterException
        if(redisTemplate.hasKey(key)) {
            String data = (String) redisTemplate.opsForValue().get(key);
            restaurantDetailDTO = gson.fromJson(data, RestaurantDetailDTO.class);

        } else {
            // Optional: tức là có hoặc không có cũng được, hoặc dữ liệu có thể bị null
            Optional<RestaurantEntity> restaurantEntity = restaurantRepository.findById(id);
            if(restaurantEntity.isPresent()) {
                // Co gia tri xu ly
                restaurantDetailDTO.setTitle(restaurantEntity.get().getName());
                restaurantDetailDTO.setImage(restaurantEntity.get().getImage());

                float avgRate = 0;
                float sumRate = 0;
                for (RestaurantReviewEntity dataReview: restaurantEntity.get().getRestaurantReviews()) {
                    sumRate += dataReview.getRate();
                }

                if(restaurantEntity.get().getRestaurantReviews().size() > 0) {
                    avgRate = sumRate/restaurantEntity.get().getRestaurantReviews().size();
                }

                restaurantDetailDTO.setAvgRate(avgRate);

                List<FoodModel> foodModels = new ArrayList<>();
                for (FoodEntity foodEntity: restaurantEntity.get().getFoods()) {
                    FoodModel foodModel = new FoodModel();
                    foodModel.setId(foodEntity.getId());
                    foodModel.setName(foodEntity.getName());
                    foodModel.setImage(foodEntity.getImage());
                    foodModel.setPrice(foodEntity.getPrice());

                    foodModels.add(foodModel);
                }

                restaurantDetailDTO.setFoods(foodModels);

            }

            String json = gson.toJson(restaurantDetailDTO);
            redisTemplate.opsForValue().set(key, json);
        }

        return restaurantDetailDTO;
    }

    @CacheEvict( value = "detail_restaurant", allEntries = true)
    @Override
    public void clearAllCache() {
    }
}
