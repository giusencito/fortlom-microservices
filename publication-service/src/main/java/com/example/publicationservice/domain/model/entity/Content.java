package com.example.publicationservice.domain.model.entity;


import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@NoArgsConstructor
@Getter
@Setter
@Entity
@With
@AllArgsConstructor
@Table(name="contents")
@Inheritance(strategy = InheritanceType.JOINED)
public class Content {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @NotBlank
    @Size(max = 200)
    private String description;


    @NotNull
    private Long likes;

    @Temporal(TemporalType.DATE)
    private Date registerdate;



    private Long artistid;
}
