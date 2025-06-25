package com.talento.ApiEcommerce.Category.Service;

import com.talento.ApiEcommerce.Category.Model.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryService {
    List<Category> getAll();
    Optional<Category> getCategoryById(Long id);
    List<Category> getCategoryByName(String name);
    Category save(Category category);
    void deleteById(Long id);
}
