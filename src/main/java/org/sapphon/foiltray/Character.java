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
public class Character {
    @Id
    @GeneratedValue
    int id;

    @NotNull
    String name;

    public Character(String name){
        this.name = name;
    }
}
