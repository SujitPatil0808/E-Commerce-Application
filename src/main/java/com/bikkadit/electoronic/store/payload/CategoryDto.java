package com.bikkadit.electoronic.store.payload;

import lombok.*;

import javax.persistence.Column;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryDto {

    private String categoryId;

    private String title;

    private String description;

    private String coverImage;
}
