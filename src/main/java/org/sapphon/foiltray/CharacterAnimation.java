package org.sapphon.foiltray;


import javax.persistence.*;
import java.io.File;
import java.util.List;

@Entity
public class CharacterAnimation {
    @Id
    @GeneratedValue
    int id;

    @ManyToOne
    public Game game;

    //Character character;

    @ElementCollection
    List<File> frames;

    //AnimationMotion motion;

}
