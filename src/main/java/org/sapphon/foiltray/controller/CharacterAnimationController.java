package org.sapphon.foiltray.controller;

import org.sapphon.foiltray.controller.request.CharacterAnimationRequest;
import org.sapphon.foiltray.model.AnimationMotion;
import org.sapphon.foiltray.model.CharacterAnimation;
import org.sapphon.foiltray.model.Game;
import org.sapphon.foiltray.model.Persona;
import org.sapphon.foiltray.repository.AnimationMotionRepository;
import org.sapphon.foiltray.repository.CharacterAnimationRepository;
import org.sapphon.foiltray.repository.CharacterRepository;
import org.sapphon.foiltray.repository.GameRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Optional;

@Controller
public class CharacterAnimationController {
    private CharacterAnimationRepository characterAnimationRepository;
    private CharacterRepository characterRepository;
    private AnimationMotionRepository motionRepository;
    private GameRepository gameRepository;

    public CharacterAnimationController(CharacterAnimationRepository characterAnimationRepository, CharacterRepository characterRepository, AnimationMotionRepository motionRepository, GameRepository gameRepository) {
        this.characterAnimationRepository = characterAnimationRepository;
        this.characterRepository = characterRepository;
        this.motionRepository = motionRepository;
        this.gameRepository = gameRepository;
    }

    @GetMapping("/api/v1/art/character")
    public ResponseEntity getCharacterAnimation(CharacterAnimationRequest request) {
        Optional<Game> game = gameRepository.findById(request.getGameId());
        Optional<Persona> character = characterRepository.findById(request.getCharacterId());
        Optional<AnimationMotion> motion = motionRepository.findById(request.getMotionId());

        if (game.isPresent() && character.isPresent() && motion.isPresent()) {
            Optional<CharacterAnimation> found = characterAnimationRepository.findByGameAndCharacterAndMotion(game.get(), character.get(), motion.get());

            if (found.isPresent()) {
                return new ResponseEntity<>(found, HttpStatus.OK);
            }
        }

        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }
}
