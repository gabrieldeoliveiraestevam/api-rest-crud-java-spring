package com.example.crud.domain.product;

import org.springframework.data.jpa.repository.JpaRepository;

// Biblioteca JPA facilita o acesso ao banco de dados
// Informa o Entidade/Tabela e o tipo do ID da Entidade/Tabela
// Somente com essa implementação já temos vários métodos de acesso a tabela implementados
public interface ProductRepository extends JpaRepository<Product, String> {}
