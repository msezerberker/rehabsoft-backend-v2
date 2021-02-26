package com.hacettepe.rehabsoft.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import net.minidev.json.annotate.JsonIgnore;

import javax.persistence.*;
import java.util.Collection;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "roles")
@Data
@NoArgsConstructor
@AllArgsConstructor
@SequenceGenerator(name = "idgen", sequenceName = "roles_seq", initialValue = 1, allocationSize = 1)
public class Role extends BaseEntity {

    //Her role bir id'ye sahip: Simdilik iki ana rol var: Fizyoterapist ve Hasta


    @Column(name = "role_name", unique=true)
    private String name;


    @OneToMany(mappedBy = "role", cascade = CascadeType.ALL)
    private Collection<User> users;
}
