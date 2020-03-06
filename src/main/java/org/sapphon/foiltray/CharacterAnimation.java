package org.sapphon.foiltray;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.File;
import java.util.List;

@Entity
public class CharacterAnimation {
    @Id
    @GeneratedValue
    int id;

    Game game;

    Character character;

    List<File> frames;

    AnimationMotion motion;

}
