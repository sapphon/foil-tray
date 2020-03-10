package org.sapphon.foiltray.controller;

import org.sapphon.foiltray.Game;
import org.sapphon.foiltray.repository.GameRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class GameController {

    private final GameRepository gameRepository;

    public GameController(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    @GetMapping("/api/v1/game/{gameName}")
    public ResponseEntity<Game> getGame(@PathVariable String gameName) {
        Game found = gameRepository.findByName(gameName);
        return found != null ? ResponseEntity.ok(found) : new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PostMapping("api/v1/game")
    public ResponseEntity postGame(@RequestBody Game incomingGame) {
        Game found = gameRepository.findByName(incomingGame.getName());
        return found == null ? new ResponseEntity<>(gameRepository.save(incomingGame), HttpStatus.OK) : new ResponseEntity(HttpStatus.BAD_REQUEST);
    }
}
