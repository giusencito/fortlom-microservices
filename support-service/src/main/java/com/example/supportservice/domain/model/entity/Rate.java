package com.example.supportservice.domain.model.entity;


import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;


@NoArgsConstructor
@Getter
@Setter
@Entity
@With
@AllArgsConstructor
public class Rate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @NotNull
    private Integer rates;




    private Long artistid;



    private Long fanaticid;
}
