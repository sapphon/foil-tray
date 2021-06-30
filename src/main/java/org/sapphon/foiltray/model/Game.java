package org.sapphon.foiltray.model;

import lombok.*;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class Game {
    @Id
    @GeneratedValue
    int id;

    @NonNull
    String name;

    @ElementCollection
    List<String> backgroundImages = new ArrayList<String>();


    public Game() {
    }
}
