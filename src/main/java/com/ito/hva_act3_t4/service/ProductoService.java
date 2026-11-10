package com.ito.hva_act3_t4.service;

import com.ito.hva_act3_t4.entity.Categoria;
import com.ito.hva_act3_t4.entity.Producto;
import com.ito.hva_act3_t4.repository.CategoriaRepository;
import com.ito.hva_act3_t4.repository.ProductoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductoService {

    private final ProductoRepository productoRepository;
    private final CategoriaRepository categoriaRepository;

    public ProductoService(ProductoRepository productoRepository, CategoriaRepository categoriaRepository) {
        this.productoRepository = productoRepository;
        this.categoriaRepository = categoriaRepository;
    }

    public List<Producto> listarTodos() {
        return productoRepository.findAll();
    }

    public Optional<Producto> buscarPorId(Long id) {
        return productoRepository.findById(id);
    }

    public List<Categoria> listarCategorias() {
        return categoriaRepository.findAll();
    }

    public Producto guardarProducto(Producto producto, Long categoriaId) {
        Categoria categoria = categoriaRepository.findById(categoriaId)
                .orElseThrow(() -> new IllegalArgumentException("La categoria con ID " + categoriaId + " no existe."));
        producto.setCategoria(categoria);
        return productoRepository.save(producto);
    }

    public Producto actualizarProducto(Long id, Producto productoActualizado, Long categoriaId) {
        return productoRepository.findById(id).map(prod -> {
            prod.setNombre(productoActualizado.getNombre());
            prod.setPrecio(productoActualizado.getPrecio());
            prod.setStock(productoActualizado.getStock());
            if (categoriaId != null) {
                Categoria cat = categoriaRepository.findById(categoriaId)
                        .orElseThrow(() -> new IllegalArgumentException("La categoria con ID " + categoriaId + " no existe."));
                prod.setCategoria(cat);
            }
            return productoRepository.save(prod);
        }).orElseThrow(() -> new RuntimeException("Producto con ID " + id + " no encontrado"));
    }

    public boolean eliminarProducto(Long id) {
        if (productoRepository.existsById(id)) {
            productoRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public Categoria guardarCategoria(Categoria categoria) {
        return categoriaRepository.save(categoria);
    }
}