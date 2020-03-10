package org.sapphon.foiltray.repository;

import org.sapphon.foiltray.Character;
import org.springframework.data.repository.CrudRepository;

public interface CharacterRepository extends CrudRepository<Character, Integer> {
    Character findByName(String name);
}