package org.sapphon.foiltray.repository;

import org.sapphon.foiltray.model.AnimationMotion;
import org.sapphon.foiltray.model.CharacterAnimation;
import org.sapphon.foiltray.model.Game;
import org.sapphon.foiltray.model.Character;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CharacterAnimationRepository extends CrudRepository<CharacterAnimation, Integer> {
    Optional<CharacterAnimation> findByGameAndCharacterAndMotion(Game game, Character character, AnimationMotion motion);
}