package org.api.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@Builder // Required for unit tests
@NoArgsConstructor // Required for unit tests
@AllArgsConstructor // Required for unit tests
public class Video {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String title;
    private String synopsis;
    private String director;
    private String actors;
    private Integer yearOfRelease;
    private String genre;
    private Integer runningTime;
    private String content;
    private Boolean isDelisted = false;
    private Integer impressions = 0;
    private Integer views = 0;
}
