package org.sapphon.foiltray;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
public class AnimationMotion {
    @Id
    int id;

    @NotNull
    String motionName;
}
