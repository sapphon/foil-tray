package org.sapphon.foiltray.repository;

import org.sapphon.foiltray.model.Persona;
import org.springframework.data.repository.CrudRepository;

public interface CharacterRepository extends CrudRepository<Persona, Integer> {
    Persona findByName(String name);
}