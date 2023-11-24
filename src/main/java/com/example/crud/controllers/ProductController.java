package com.example.crud.controllers;

import com.example.crud.domain.product.Product;
import com.example.crud.domain.product.ProductRepository;
import com.example.crud.domain.product.RequestProduct;
import com.example.crud.domain.product.RequestProductUpdate;
import jakarta.validation.Valid;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

// String faz as instancias atraves das anotacoes
@RestController
@RequestMapping("/product") // Endpoint que o controller escuta
public class ProductController {
    @Autowired // Injeção de dependência
    private ProductRepository repository;
    @GetMapping // Quando o end point mapeado for um GET eh acionada esse metodo
    public ResponseEntity getAllProducts(){
        var allProducts = repository.findAll();
        return ResponseEntity.ok(allProducts);
    }

    // Valida o Request do POST com base na classe RequestProduct
    @PostMapping
    public ResponseEntity registerProduct(@RequestBody @Valid RequestProduct data){
        // Instancia a classe e grava na tabela product
        Product newProduct = new Product(data.name(), data.price_in_cents());
        repository.save(newProduct);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity updateProduct(@PathVariable @Valid String id, @RequestBody @Valid RequestProductUpdate data){
        Product product = repository.getReferenceById(id);
        return ResponseEntity.ok().build();
    }

    // Enviando o ID pela URL
    @DeleteMapping("/{id}")
    public ResponseEntity updateProduct(@PathVariable @Valid String id){
        repository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}