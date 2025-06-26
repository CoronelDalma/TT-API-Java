package com.talento.ApiEcommerce.Articulo.Service;

import com.talento.ApiEcommerce.Articulo.Model.Articulo;
import com.talento.ApiEcommerce.Articulo.Repository.ArticuloRepository;
import com.talento.ApiEcommerce.Category.Model.Category;
import com.talento.ApiEcommerce.Category.Repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
            articulo.setImagesUrl(List.of("https://via.placeholder.com/300x300?text=Sin+imagen"));
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
                        .orElseGet(() -> categoryRepository.save(new Category(cat.getName()))))
                .toList();

        articulo.setCategories(categories);
        return this.save(articulo);
    }
}
