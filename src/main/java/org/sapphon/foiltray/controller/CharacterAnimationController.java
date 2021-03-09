package org.sapphon.foiltray.controller;

import org.sapphon.foiltray.controller.request.AnimationFrameRequest;
import org.sapphon.foiltray.controller.request.CharacterAnimationRequest;
import org.sapphon.foiltray.controller.request.CharacterMotionAnimationRequest;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;

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

    private Optional<CharacterAnimation> getByRequest(CharacterMotionAnimationRequest request) {
        Optional<Game> game = gameRepository.findById(request.getGameId());
        Optional<Persona> character = characterRepository.findById(request.getCharacterId());
        Optional<AnimationMotion> motion = motionRepository.findById(request.getMotionId());

        if (game.isPresent() && character.isPresent() && motion.isPresent()) {
            return characterAnimationRepository.findByGameAndCharacterAndMotion(game.get(), character.get(), motion.get());
        } else return Optional.empty();
    }

    private Optional<List<CharacterAnimation>> getByRequest(CharacterAnimationRequest request) {
        Optional<Game> game = gameRepository.findById(request.getGameId());
        Optional<Persona> character = characterRepository.findById(request.getCharacterId());

        if (game.isPresent() && character.isPresent()) {
            return Optional.of(characterAnimationRepository.findAllByGameAndCharacter(game.get(), character.get()));
        } else return Optional.empty();
    }

    @GetMapping("/api/v1/art/character")
    public ResponseEntity getCharacterAnimation(CharacterMotionAnimationRequest request) {
        Optional<CharacterAnimation> found = getByRequest(request);
        if (found.isPresent()) {
            return new ResponseEntity<>(found, HttpStatus.OK);
        } else return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/api/v1/art/character/frame")
    public ResponseEntity postFrame(CharacterMotionAnimationRequest request, @RequestBody AnimationFrameRequest incomingFrame) {
        Optional<CharacterAnimation> foundMaybe = getByRequest(request);
        if (foundMaybe.isPresent()) {
            CharacterAnimation found = foundMaybe.get();
            if (incomingFrame.getSequenceNumber() == null || incomingFrame.getSequenceNumber() == -1) {
                found.getFrames().add(incomingFrame.getFrameData());
            } else if (incomingFrame.getMode() != null) {
                if (incomingFrame.getMode().equals(AnimationFrameRequest.AnimationFrameRequestMode.INSERT)) {
                    found.getFrames().add(incomingFrame.getSequenceNumber(), incomingFrame.getFrameData());
                } else if (incomingFrame.getMode().equals(AnimationFrameRequest.AnimationFrameRequestMode.REPLACE)) {
                    found.getFrames().remove((int)incomingFrame.getSequenceNumber());
                    found.getFrames().add(incomingFrame.getSequenceNumber(), incomingFrame.getFrameData());
                }
            }
            characterAnimationRepository.save(found);
            return new ResponseEntity<>(HttpStatus.OK);
        } else return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/api/v1/art/character/all-motions")
    public ResponseEntity getAllArtByCharacter(CharacterAnimationRequest request){
        Optional<List<CharacterAnimation>> foundMaybe = getByRequest(request);
        if(foundMaybe.isPresent()){
            return ResponseEntity.ok(foundMaybe.get());
        }else{
            return ResponseEntity.badRequest().build();
        }
    }
}
