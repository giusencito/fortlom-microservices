package com.example.userservice.domain.model.entity;
import lombok.*;
import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
@Getter
@Setter
@With
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "persons")
@Inheritance(strategy = InheritanceType.JOINED)
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @NotBlank
    @Size(max = 30)
    @Column(unique = true)
    private String username;


    @NotNull
    @NotBlank
    @Size(max = 30)
    private String realname;

    @NotNull
    @NotBlank
    @Size(max = 30)
    private String lastname;

    @NotNull
    @NotBlank
    @Size(max = 100)
    @Email
    @Column(unique = true)
    private String email;

    @NotNull
    @NotBlank
    private String password;

    @Lob
    private byte[] content;

    private String imageprofiletype;
}
