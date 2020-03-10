package org.sapphon.foiltray.repository;

import org.sapphon.foiltray.CharacterAnimation;
import org.sapphon.foiltray.Game;
import org.springframework.data.repository.CrudRepository;

public interface GameRepository extends CrudRepository<Game, Integer>{
    Game findByName(String gameName);
}