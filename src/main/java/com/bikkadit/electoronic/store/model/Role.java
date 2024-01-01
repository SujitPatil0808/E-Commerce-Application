package com.bikkadit.electoronic.store.model;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

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
