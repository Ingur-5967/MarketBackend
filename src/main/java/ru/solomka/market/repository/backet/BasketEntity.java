package ru.solomka.market.repository.backet;

import jakarta.persistence.*;
import lombok.*;
import ru.solomka.market.repository.product.ProductEntity;
import ru.solomka.market.repository.user.UserEntity;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(catalog = "enterprice", name="basket")
public class BasketEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @OneToMany(cascade = CascadeType.ALL)
    private List<ProductEntity> products;

    @OneToOne
    @JoinColumn(name = "owner")
    private UserEntity user;
}