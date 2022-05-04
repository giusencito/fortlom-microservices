package com.example.userservice.domain.model.entity;
import com.example.userservice.domain.model.enumeration.RolName;
import lombok.*;

import javax.persistence.*;
@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@With
@Table(name = "roles")
public class Rol {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private RolName name;
}
