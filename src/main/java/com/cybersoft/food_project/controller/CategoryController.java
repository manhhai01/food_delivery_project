package com.cybersoft.food_project.controller;

import com.cybersoft.food_project.entity.CategoryEntity;
import com.cybersoft.food_project.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    CategoryService categoryService;

    @GetMapping("")
    public ResponseEntity<?> getExplorerCategory(){
        List<CategoryEntity> categoryEntities = categoryService.getExplorerCategory();

        return new ResponseEntity<>(categoryEntities, HttpStatus.OK);
    }

}
