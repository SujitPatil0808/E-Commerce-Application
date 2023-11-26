package com.bikkadit.electoronic.store.model;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Category {

    @Id
    @Column(name = "category_id")
    private String categoryId;

    @Column(name = "category_image")
    private String title;

    @Column(name = "category_description")
    private String description;

    @Column(name = "catergory_image")
    private String coverImage;
}
