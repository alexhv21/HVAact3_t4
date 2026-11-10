package com.ito.hva_act3_t4.controller;

import com.ito.hva_act3_t4.entity.Producto;
import com.ito.hva_act3_t4.service.ProductoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/productos")
public class ProductoRestController {

    private final ProductoService productoService;

    public ProductoRestController(ProductoService productoService) {
        this.productoService = productoService;
    }

    // 1. READ: Listar todos los productos
    @GetMapping
    public List<Producto> listar() {
        return productoService.listarTodos();
    }

    // 2. READ: Detalle de un producto por ID
    @GetMapping("/{id}")
    public ResponseEntity<Producto> buscarPorId(@PathVariable Long id) {
        return productoService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // 3. CREATE: Guardar nuevo producto asociado a su categoría
    @PostMapping("/crear")
    public ResponseEntity<Producto> crear(@RequestBody Producto producto, @RequestParam Long categoriaId) {
        Producto nuevo = productoService.guardarProducto(producto, categoriaId);
        return new ResponseEntity<>(nuevo, HttpStatus.CREATED);
    }

    // 4. UPDATE: Actualizar datos de un producto y su categoría
    @PutMapping("/actualizar/{id}")
    public ResponseEntity<Producto> actualizar(@PathVariable Long id, 
                                              @RequestBody Producto producto, 
                                              @RequestParam(required = false) Long categoriaId) {
        try {
            Producto actualizado = productoService.actualizarProducto(id, producto, categoriaId);
            return ResponseEntity.ok(actualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // 5. DELETE: Eliminar producto por ID
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        if (productoService.eliminarProducto(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}