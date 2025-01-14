package ru.solomka.market.repository.backet;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CollectionType;
import ru.solomka.market.repository.product.ProductEntity;
import ru.solomka.market.repository.user.UserEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

    @ElementCollection(fetch = FetchType.LAZY)
    @MapKeyJoinColumn(name = "product_id")
    @CollectionTable(name = "basket_products")
    @Column(name = "quantity")
    private Map<ProductEntity, Integer> products;

    @OneToOne
    @JoinColumn(name = "owner")
    private UserEntity user;
}