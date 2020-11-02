package com.hacettepe.rehabsoft.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Collection;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "il")
@Data
@NoArgsConstructor
@AllArgsConstructor
@SequenceGenerator(name = "idgen", sequenceName = "il_seq", initialValue = 1, allocationSize = 1)
public class Il extends BaseEntity{
    @Column(name = "il_name")
    private String ilName;

    @OneToMany(mappedBy = "il", cascade = CascadeType.ALL)
    private Collection<Ilce> ilceCollection;
}
