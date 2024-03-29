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
@Table(name="publications")
@PrimaryKeyJoinColumn(referencedColumnName = "id")
public class Publication extends Content{

    @OneToMany(targetEntity = Multimedia.class,cascade = CascadeType.ALL)
    @JoinColumn(name = "publicationid",referencedColumnName = "id")
    private List<Multimedia> multimedia;



}
