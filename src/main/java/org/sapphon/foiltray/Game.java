package org.sapphon.foiltray;

import lombok.*;
import org.springframework.beans.factory.annotation.Required;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
@Data
@RequiredArgsConstructor
public class Game {
    @Id
    @GeneratedValue
    int id;

    @NonNull
    String name;

    public Game(){}
}
