package org.sapphon.foiltray;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

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
}
