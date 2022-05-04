package com.example.forumservice.domain.model.entity;

import lombok.*;

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
@Table(name="forums")
public class Forum {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @NotBlank
    @Size(max = 20)
    @Column(unique = true)
    private String forumname;


    @NotNull
    @NotBlank
    @Size(max = 200)
    private String forumdescription;


    private Long personid;






}