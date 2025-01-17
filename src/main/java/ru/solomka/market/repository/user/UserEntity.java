package ru.solomka.market.repository.user;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import ru.solomka.market.repository.backet.BasketEntity;
import ru.solomka.market.secure.user.enums.UserPermission;

@Table(catalog = "enterprice", name = "users")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class UserEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @NotBlank
    @Size(min = 5, max = 20)
    private String username;

    @NotBlank
    private String password;

    @Email
    @NotBlank
    private String email;

    @PositiveOrZero
    private Double balance;

    @Enumerated(EnumType.STRING)
    private UserPermission role;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "basket_id")
    private BasketEntity basket;
}