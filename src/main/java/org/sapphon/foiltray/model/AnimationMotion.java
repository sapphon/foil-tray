package org.sapphon.foiltray.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class AnimationMotion {
    @Id
    @GeneratedValue
    int id;

    @NonNull
    String name;

    public AnimationMotion(){}
}
