package com.food_easy_back.backend_food_easy.model.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Entity
@Table(name = "users")
public class UserEntity  implements Serializable{

    @Id
    @Column(name = "id_users")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idUser;

    @Column(nullable = false, length = 200)
    private String password;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "username", nullable = false ,  unique = true)
    private String username;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "register_date", updatable = false)
    private LocalDate registerDate;

    @Column(nullable = false, columnDefinition = "TINYINT")
    private Boolean locked;

    @Column( nullable = false, columnDefinition = "TINYINT")
    private Boolean disabled;


    @ManyToOne
    private StoreEntity store;
    

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER,cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private List<UserRoleEntity> roles;


    @PrePersist
    protected void onCreate() {
        registerDate = LocalDate.now();
    }
}