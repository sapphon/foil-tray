package org.sapphon.foiltray.model;


import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;
import java.io.File;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
public class CharacterAnimation {
    @Id
    @GeneratedValue
    int id;

    @ManyToOne
    public Game game;

    @ManyToOne
    Persona character;

    @ElementCollection
    List<File> frames;

    @ManyToOne
    AnimationMotion motion;

}
