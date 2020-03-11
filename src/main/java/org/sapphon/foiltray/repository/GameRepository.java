package org.sapphon.foiltray.repository;

import org.sapphon.foiltray.model.Game;
import org.springframework.data.repository.CrudRepository;

public interface GameRepository extends CrudRepository<Game, Integer> {
    Game findByName(String gameName);
}