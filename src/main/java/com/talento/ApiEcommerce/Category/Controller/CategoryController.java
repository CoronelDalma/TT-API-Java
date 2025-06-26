package com.talento.ApiEcommerce.Category.Controller;

import com.talento.ApiEcommerce.Category.Model.Category;
import com.talento.ApiEcommerce.Category.Service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*") // Permite solicitudes desde cualquier origen (CORS)
@RestController
@RequestMapping("/api/categories")
public class CategoryController {
    private final CategoryService service;

    @Autowired
    public CategoryController(CategoryService service) {
        this.service = service;
    }

    @GetMapping
    public List<Category> getAllCategories() {
        return service.getAll();
    }

    @GetMapping("/category/{id}")
    public ResponseEntity<Category> getById(@PathVariable Long id) {
        return service.getCategoryById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/{text}")
    public List<Category> getByName(@PathVariable String text) {
        return service.getCategoryByName(text);
    }

    @PostMapping
    public Category createCategory(@RequestBody Category category) {
        return service.save(category);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategoryById(@PathVariable Long id) {
        if(service.getCategoryById(id).isPresent()) {
            service.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
