package com.example.publicationservice.domain.model.entity;
import lombok.*;

import java.util.Date;
import java.util.List;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;






@NoArgsConstructor
@Getter
@Setter
@Entity
@With
@AllArgsConstructor
@Table(name="publication")
public class Publication {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;



    @NotNull
    @NotBlank
    @Size(max = 200)
    private String publicationdescription;


    @NotNull
    private Long likes;

    @Temporal(TemporalType.DATE)
    private Date registerdate;



    private Long artistid;














}
