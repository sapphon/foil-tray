package org.sapphon.foiltray;


import com.sun.tools.javac.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.File;

@Entity
public class CharacterAnimation {
    @Id
    int id;

    Game game;

    Character character;

    List<File> frames;

    AnimationMotion motion;

}
