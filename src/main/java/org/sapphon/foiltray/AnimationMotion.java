package org.sapphon.foiltray;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
public class AnimationMotion {
    @Id
    @GeneratedValue
    int id;

    @NotNull
    String motionName;
}
