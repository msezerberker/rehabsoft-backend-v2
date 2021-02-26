package com.hacettepe.rehabsoft.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.Collection;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@SequenceGenerator(name = "idgen", sequenceName = "users_seq", initialValue = 1, allocationSize = 1)
public class User extends BaseEntity{


    @Column(name = "username", unique = true)
    private String username;

    @Column(name = "user_password")
    private String password;

    @Column(name = "firstname")
    private String firstName;

    @Column(name = "surname")
    private String surname;

    @Column(name = "email", unique=true)
    private String email;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

    @OneToMany(mappedBy = "user")
    private Collection<Notification> notificationCollection;

}
