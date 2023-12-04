package com.example.crud.controllers;

import com.example.crud.domain.product.Product;
import com.example.crud.domain.product.ProductRepository;
import com.example.crud.domain.product.RequestProduct;
import com.example.crud.domain.product.RequestProductUpdate;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

// String faz as instancias atraves das anotacoes
@RestController
@RequestMapping("/product") // Endpoint que o controller escuta
public class ProductController {
    @Autowired // Injeção de dependência
    private ProductRepository repository;
    @GetMapping // Quando o end point mapeado for um GET eh acionada esse metodo
    public ResponseEntity getAllProducts(){
        var allProducts = repository.findAllByActiveTrue();
        return ResponseEntity.ok(allProducts);
    }

    // Valida o Request do POST com base na classe RequestProduct
    @PostMapping
    public ResponseEntity registerProduct(@RequestBody @Valid RequestProduct data){
        // Instancia a classe e grava na tabela product
        Product newProduct = new Product(data);
        repository.save(newProduct);
        return ResponseEntity.ok(newProduct);
    }

    @PutMapping()
    @Transactional // Organiza os comandos de alteracao no banco (SET)
    public ResponseEntity updateProduct(@RequestBody @Valid RequestProductUpdate data){
        // Optional => O retorno do repository pode ser um Product ou um undefined
        Optional<Product> optionalProduct = repository.findById(data.id());

        // Verifica se o objeto product retornou, ou seja, se existe o produto
        if (optionalProduct.isPresent()){
            Product product = optionalProduct.get(); // Busca a instância
            // A instancia está vinculada com o banco de dados. Então a atualização já é feita no banco.
            product.setName(data.name());
            product.setPrice_in_cents(data.price_in_cents());
            return ResponseEntity.ok(product);
        }

        throw new EntityNotFoundException();
    }

    // Enviando o ID pela URL
    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity updateProduct(@PathVariable @Valid @NotNull String id){
        Optional<Product> optionalProduct = repository.findById(id);

        if (optionalProduct.isPresent()){
            Product product = optionalProduct.get();
            product.setActive(false);
            return ResponseEntity.noContent().build();
        }

        throw new EntityNotFoundException();
    }
}