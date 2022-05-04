package com.example.userservice.domain.model.entity;
import lombok.*;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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
@Table(name="artists")
@PrimaryKeyJoinColumn(referencedColumnName = "id")
public class Artist extends Person{

    @NotNull
    private Long artistfollowers;

    private String instagramLink;

    private String facebookLink;

    private String twitterLink;
}
