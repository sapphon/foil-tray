package org.sapphon.foiltray;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
public class Character {
    @Id
    int id;

    @NotNull
    String name;
}
