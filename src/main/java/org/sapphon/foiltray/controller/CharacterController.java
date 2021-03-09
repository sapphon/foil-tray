package org.sapphon.foiltray.controller;

import lombok.Data;
import org.sapphon.foiltray.model.Persona;
import org.sapphon.foiltray.repository.CharacterRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Controller
public class CharacterController {
    private final CharacterRepository characterRepository;

    @Data
    private class Characters{
        List<Persona> characters;
        public Characters(List<Persona> characters){ this.characters = characters; }

    }

    public CharacterController(CharacterRepository characterRepository) {
        this.characterRepository = characterRepository;
        if(characterRepository.count() == 0){
            addDefaultCharacters(characterRepository);
        }
    }

    private void addDefaultCharacters(CharacterRepository characterRepository) {
        characterRepository.save(new Persona("Player"));
        characterRepository.save(new Persona("Friend"));
        characterRepository.save(new Persona("Enemy"));
    }

    @GetMapping("/api/v1/character/{characterName}")
    public ResponseEntity<Persona> getGame(@PathVariable String characterName) {
        Persona found = characterRepository.findByName(characterName);
        return found != null ? ResponseEntity.ok(found) : new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/api/v1/characters")
    public ResponseEntity<Characters> getAllCharacters() {
        return ResponseEntity.ok(new Characters(characterRepository.findAll()));
    }

    @PostMapping("api/v1/character")
    public ResponseEntity postGame(@RequestBody Persona incomingCharacter) {
        Persona found = characterRepository.findByName(incomingCharacter.getName());
        return found == null ? new ResponseEntity<>(characterRepository.save(incomingCharacter), HttpStatus.OK) : new ResponseEntity(HttpStatus.BAD_REQUEST);
    }
}
