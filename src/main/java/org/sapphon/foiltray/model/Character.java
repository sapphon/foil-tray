package org.sapphon.foiltray.model;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Data
@RequiredArgsConstructor
public class Character {
    @Id
    @GeneratedValue
    int id;

    @NonNull
    String name;

    public Character() {
    }
}
