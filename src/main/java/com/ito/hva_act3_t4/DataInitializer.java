package com.ito.hva_act3_t4;

import com.ito.hva_act3_t4.entity.Categoria;
import com.ito.hva_act3_t4.repository.CategoriaRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private final CategoriaRepository categoriaRepository;

    public DataInitializer(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (categoriaRepository.count() == 0) {
            categoriaRepository.save(new Categoria("Electrónica y Cómputo", "Laptops, tarjetas y periféricos"));
            categoriaRepository.save(new Categoria("Accesorios y Audio", "Auriculares, teclados y micrófonos"));
            categoriaRepository.save(new Categoria("Oficina y Redes", "Cables de red, routers y papelería"));
            System.out.println(">>> [INFO] Categorías iniciales cargadas en MySQL exitosamente.");
        }
    }
}