package com.talento.ApiEcommerce.Articulo.Service;

import com.talento.ApiEcommerce.Articulo.Model.Articulo;
import com.talento.ApiEcommerce.Articulo.Repository.ArticuloRepository;
import com.talento.ApiEcommerce.Category.Model.Category;
import com.talento.ApiEcommerce.Category.Repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ArticuloServiceImpl implements ArticuloService{

    private final ArticuloRepository repository;
    private final CategoryRepository categoryRepository;

    @Autowired
    public ArticuloServiceImpl(ArticuloRepository repository, CategoryRepository categoryRepository) {
        this.repository = repository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<Articulo> getAll() {
        return repository.findAll();
    }

    @Override
    public Optional<Articulo> getArticuloById(Long id) {
        return repository.findById(id);
    }

    @Override
    public Articulo save(Articulo articulo) {
        List<String> images = articulo.getImagesUrl();
        if (images == null || images.isEmpty()) {
            articulo.setImagesUrl(List.of("https://cdn.dummyjson.com/products/images/laptops/Apple%20MacBook%20Pro%2014%20Inch%20Space%20Grey/1.png"));
        }
        return repository.save(articulo);
    }


    // todo posiblemente no sea necesario ahora
    @Override
    public Articulo update(Long id, Articulo articulo) {
        return repository.save(articulo);
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    public Articulo saveWithCategory(Articulo articulo) {
        List<Category> categories = articulo.getCategories().stream()
                .map(cat -> categoryRepository.findByName(cat.getName())
                        .orElseGet(() -> categoryRepository.save(new Category(cat.getName(), cat.getIcon()))))
                .collect(Collectors.toList());

        articulo.setCategories(categories);
        return this.save(articulo);
    }

    @Override
    public List<Articulo> findByNameContainig(String text) {
        return repository.findByNameContaining(text);
    }

    @Override
    public List<Articulo> getByCategoryName(String categoryName) {
        return repository.findByCategoriesNameIgnoreCase(categoryName);
    }

    @Override
    public List<Articulo> getByCategoryId(Long idCategory) {
        return repository.findByCategoriesId(idCategory);
    }
}
