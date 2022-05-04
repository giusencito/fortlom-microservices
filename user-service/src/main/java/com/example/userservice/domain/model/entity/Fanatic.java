package com.example.userservice.domain.model.entity;
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
@Table(name="fanatics")
@PrimaryKeyJoinColumn(referencedColumnName = "id")
public class Fanatic extends Person{

    @NotNull
    @NotBlank
    @Size(max = 30)
    @Column(unique = true)
    private String fanaticalias;
}
