package org.sapphon.foiltray.repository;

import org.sapphon.foiltray.model.AnimationMotion;
import org.sapphon.foiltray.model.CharacterAnimation;
import org.sapphon.foiltray.model.Game;
import org.sapphon.foiltray.model.Persona;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface CharacterAnimationRepository extends CrudRepository<CharacterAnimation, Integer> {
    Optional<CharacterAnimation> findByGameAndCharacterAndMotion(Game game, Persona character, AnimationMotion motion);
    List<CharacterAnimation> findAllByGameAndCharacter(Game game, Persona character);
}