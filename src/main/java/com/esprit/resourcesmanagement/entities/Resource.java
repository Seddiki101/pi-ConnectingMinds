package com.esprit.resourcesmanagement.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Entity representing the resources table.
 */
@Entity
@Getter
@Setter
@Table(name="resources")
public class Resource implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long resourceId;
    private String name;
    private String description;
    private String url;
    private Long size= 0L;
    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private byte[] content;

    private String contentType;
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
    private int likes=0;
    private int dislikes=0;
    private int downloads=0;
    private int views=0;
    private int nbreReviews=0;
    private Long userId;
    private String dateCreation ;
    //@OneToMany(mappedBy = "resource", cascade = CascadeType.ALL)
    //private List<Review> reviews = new ArrayList<>();


    @Override
    public String toString() {
        return "Resource{" +
                "resourceId=" + resourceId +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", url='" + url + '\'' +
                ", size=" + size +
                ", content=" + Arrays.toString(content) +
                ", contentType='" + contentType + '\'' +
                ", category=" + category +
                ", likes=" + likes +
                ", dislikes=" + dislikes +
                ", downloads=" + downloads +
                ", views=" + views +
                ", userId=" + userId +
                ", dateCreation='" + dateCreation + '\'' +
                '}';
    }
}
