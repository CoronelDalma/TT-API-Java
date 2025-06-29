package com.talento.ApiEcommerce.Articulo.Service;

import com.talento.ApiEcommerce.Articulo.Model.Articulo;

import java.util.List;
import java.util.Optional;

public interface ArticuloService {
    List<Articulo> getAll();
    Optional<Articulo> getArticuloById(Long id);
    Articulo save(Articulo articulo);
    Articulo update(Long id, Articulo articulo);
    void delete(Long id);
    Articulo saveWithCategory(Articulo articulo);
    List<Articulo> findByNameContainig(String text);
    List<Articulo> getByCategoryName(String categoryName);
    List<Articulo> getByCategoryId(Long idCategory);
}
