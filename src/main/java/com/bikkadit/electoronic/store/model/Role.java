package com.bikkadit.electoronic.store.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "roles")
@Builder
public class Role {

    @Id
    private String roleId;

    private String roleName;

}
