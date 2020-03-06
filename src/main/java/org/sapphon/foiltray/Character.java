package org.sapphon.foiltray;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
public class Character {
    @Id
    @GeneratedValue
    int id;

    @NotNull
    String name;
}