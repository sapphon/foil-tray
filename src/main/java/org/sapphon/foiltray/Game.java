package org.sapphon.foiltray;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
@Data
@AllArgsConstructor
public class Game {
    @Id
    @GeneratedValue
    int id;

    @NotNull
    String name;
}
