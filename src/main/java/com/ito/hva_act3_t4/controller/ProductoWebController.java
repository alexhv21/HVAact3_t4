package com.ito.hva_act3_t4.controller;

import com.ito.hva_act3_t4.entity.Producto;
import com.ito.hva_act3_t4.service.ProductoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/productos")
public class ProductoWebController {

    private final ProductoService productoService;

    public ProductoWebController(ProductoService productoService) {
        this.productoService = productoService;
    }

    // Listar todos los productos
    @GetMapping
    public String listar(Model model) {
        model.addAttribute("productos", productoService.listarTodos());
        return "productos/listar";
    }

    // Formulario para nuevo producto
    @GetMapping("/nuevo")
    public String mostrarFormularioNuevo(Model model) {
        model.addAttribute("producto", new Producto());
        model.addAttribute("categorias", productoService.listarCategorias());
        return "productos/formulario";
    }

    // Formulario para editar producto
    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable Long id, Model model) {
        Producto producto = productoService.buscarPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("ID inválido: " + id));
        model.addAttribute("producto", producto);
        model.addAttribute("categorias", productoService.listarCategorias());
        return "productos/formulario";
    }

    // Guardar o actualizar producto
    @PostMapping("/guardar")
    public String guardarProducto(@ModelAttribute Producto producto, @RequestParam Long categoriaId) {
        if (producto.getId() != null) {
            productoService.actualizarProducto(producto.getId(), producto, categoriaId);
        } else {
            productoService.guardarProducto(producto, categoriaId);
        }
        return "redirect:/productos";
    }

    // Eliminar producto desde la vista web
    @GetMapping("/eliminar/{id}")
    public String eliminarProducto(@PathVariable Long id) {
        productoService.eliminarProducto(id);
        return "redirect:/productos";
    }
}