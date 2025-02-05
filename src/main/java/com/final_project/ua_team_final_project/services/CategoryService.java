package com.final_project.ua_team_final_project.services;

import com.final_project.ua_team_final_project.models.Category;
import com.final_project.ua_team_final_project.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    public void addCategory(String categoryName) {
        Category category = new Category();
        category.setName(categoryName);
        categoryRepository.save(category);
    }

    public Optional<Long> getCategoryIdByName(String categoryName) {
        return categoryRepository.getIdByName(categoryName);
    }

}
