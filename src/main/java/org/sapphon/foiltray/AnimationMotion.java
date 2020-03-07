package org.sapphon.foiltray;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
@Data
@AllArgsConstructor
public class AnimationMotion {
    @Id
    @GeneratedValue
    int id;

    @NotNull
    String motionName;

    public AnimationMotion(String motionName){
        this.motionName = motionName;
    }
}
