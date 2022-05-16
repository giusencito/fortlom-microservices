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
@Table(name="events")
@PrimaryKeyJoinColumn(referencedColumnName = "id")
public class Event extends Content{


    @NotNull
    @NotBlank
    @Size(max = 200)
    private String name;


    @NotNull
    @NotBlank
    private String ticketLink;


    @Temporal(TemporalType.DATE)
    private Date releasedDate;


}
