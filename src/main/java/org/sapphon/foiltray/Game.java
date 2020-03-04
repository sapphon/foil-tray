package org.sapphon.foiltray;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
public class Game {
    @Id
    int id;

    @NotNull
    String name;
}
