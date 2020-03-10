package org.sapphon.foiltray.controller;

import org.sapphon.foiltray.repository.CharacterRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.sapphon.foiltray.Character;

@Controller
public class CharacterController {
    private final CharacterRepository characterRepository;

    public CharacterController(CharacterRepository characterRepository) {
        this.characterRepository = characterRepository;
    }

    @GetMapping("/api/v1/character/{characterName}")
    public ResponseEntity<Character> getGame(@PathVariable String characterName) {
        Character found = characterRepository.findByName(characterName);
        return found != null ? ResponseEntity.ok(found) : new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PostMapping("api/v1/character")
    public ResponseEntity postGame(@RequestBody Character incomingCharacter){
        Character found = characterRepository.findByName(incomingCharacter.getName());
        return found == null ? new ResponseEntity<>(characterRepository.save(incomingCharacter), HttpStatus.OK) : new ResponseEntity(HttpStatus.BAD_REQUEST);
    }
}
