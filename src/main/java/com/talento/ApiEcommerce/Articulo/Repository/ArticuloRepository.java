package com.talento.ApiEcommerce.Articulo.Repository;

import com.talento.ApiEcommerce.Articulo.Model.Articulo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticuloRepository extends JpaRepository<Articulo, Long> {

    // Custom
    List<Articulo> findByName(String name);

    // (LIKE '%texto%')
    List<Articulo> findByNameContaining(String texto);

    List<Articulo> findByPriceGreaterThan(Double price);

    List<Articulo> findByPriceBetween(Double min, Double max);

    List<Articulo> findByNameIgnoreCase(String name);

    List<Articulo> findAllByOrderByPriceAsc();

    List<Articulo> findByNameAndPriceGreaterThan(String nombre, Double precio);
}
