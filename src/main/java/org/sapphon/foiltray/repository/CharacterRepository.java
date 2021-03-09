package org.sapphon.foiltray.repository;

import org.sapphon.foiltray.model.Persona;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CharacterRepository extends CrudRepository<Persona, Integer> {
    Persona findByName(String name);
    List<Persona> findAll();
}