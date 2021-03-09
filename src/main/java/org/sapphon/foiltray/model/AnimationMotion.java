package org.sapphon.foiltray.model;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@RequiredArgsConstructor
public class AnimationMotion {
    @Id
    @GeneratedValue
    int id;

    @NonNull
    String name;

    public AnimationMotion(){}

    public AnimationMotion(int id, String name){
        this.id=id;
        this.name=name;
    }
}
