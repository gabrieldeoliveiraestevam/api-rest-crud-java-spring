package com.example.crud.domain.product;

import jakarta.persistence.*;
import lombok.*;

// Indica o nome da tabela e da chave primaria id
// Anotações do lombok criam em tempo de execucao o construtor, getter, setter, etc
@Table(name="product")
@Entity(name="product")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of="id")
public class Product {
    // Valor de ID gerado automaticamente e formato desse id = UUID
    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String name;

    private Integer price_in_cents;

    public Product(String name, Integer price_in_cents){
        this.name = name;
        this.price_in_cents = price_in_cents;
    }
}
