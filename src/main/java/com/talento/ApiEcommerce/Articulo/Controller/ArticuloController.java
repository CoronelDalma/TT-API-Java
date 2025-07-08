package com.talento.ApiEcommerce.Articulo.Controller;

import com.talento.ApiEcommerce.Articulo.Model.Articulo;
import com.talento.ApiEcommerce.Articulo.Service.ArticuloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*") // Permite solicitudes desde cualquier origen (CORS)
@RestController
@RequestMapping("/api/articulos")
public class ArticuloController {
    private final ArticuloService service;

    @Autowired
    public ArticuloController(ArticuloService service) {
        this.service = service;
    }

    @GetMapping
    public List<Articulo> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Articulo> getById(@PathVariable Long id) {
        return service.getArticuloById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/item/{text}")
    public List<Articulo> searchByText(@PathVariable String text) {
        return service.findByNameContainig(text);
    }

    @GetMapping("/category/{categoryName}")
    public List<Articulo> getAllByCategoryName(@PathVariable String categoryName) {
        return service.getByCategoryName(categoryName);
    }

    @PostMapping("/{id}/images")
    public ResponseEntity<Articulo> addImages(@PathVariable Long id, @RequestBody String url) {
        Optional<Articulo> articulo = service.getArticuloById(id);
        if (articulo.isEmpty()) return ResponseEntity.notFound().build();

        // Todo crear DTO para extraer la url de la imagen
        Articulo updatedArticulo = articulo.get();
        updatedArticulo.getImagesUrl().add(url);
        return ResponseEntity.ok(service.save(updatedArticulo));
    }

    @PostMapping
    public ResponseEntity<Articulo> create(@RequestBody Articulo articulo) {
        Articulo saved = service.saveWithCategory(articulo);
        return ResponseEntity.ok(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Articulo> update(@PathVariable Long id, @RequestBody Articulo newArt) {
        Optional<Articulo> art = service.getArticuloById(id);
        return service.getArticuloById(id)
                .map(articulo -> {
                    articulo.setName(newArt.getName());
                    articulo.setDescription(newArt.getDescription());
                    articulo.setPrice(newArt.getPrice());
                    articulo.setImagesUrl(newArt.getImagesUrl());
                    articulo.setStock(newArt.getStock());
                    articulo.setCategories(newArt.getCategories() != null ? newArt.getCategories() : new ArrayList<>());
                    return ResponseEntity.ok(service.saveWithCategory(articulo));

                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (service.getArticuloById(id).isPresent()) {
            service.delete(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
