package org.accolite.model;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.persistence.*;


@Entity
@Table(name = "users")
@NamedQuery(name = "findAll", query = "SELECT s FROM User s")
public class User extends PanacheEntityBase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long id;

    @Column
    public String name;

}
