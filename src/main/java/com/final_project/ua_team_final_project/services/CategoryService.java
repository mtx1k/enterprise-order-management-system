package com.final_project.ua_team_final_project.services;

import com.final_project.ua_team_final_project.models.Category;
import com.final_project.ua_team_final_project.repositories.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public void addCategory(String categoryName) {
        Category category = new Category();
        category.setName(categoryName);
        categoryRepository.save(category);
    }

    public Optional<Long> getCategoryIdByName(String categoryName) {
        Optional<Category> category = categoryRepository.findByName(categoryName);
        return category.map(Category::getCategoryId);
    }

}
