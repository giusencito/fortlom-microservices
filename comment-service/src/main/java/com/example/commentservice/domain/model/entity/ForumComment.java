package com.example.commentservice.domain.model.entity;

import lombok.*;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@Entity
@With
@AllArgsConstructor
@Table(name="forumcomments")
@PrimaryKeyJoinColumn(referencedColumnName = "id")
public class ForumComment extends Comment{


    private Long forumid;
}
