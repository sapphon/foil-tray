package org.sapphon.foiltray.repository;

import org.sapphon.foiltray.CharacterAnimation;
import org.springframework.data.repository.CrudRepository;
import org.sapphon.foiltray.Character;
import org.springframework.stereotype.Repository;

public interface CharacterRepository extends CrudRepository<Character, Integer>{
    Character findByName(String name);
}