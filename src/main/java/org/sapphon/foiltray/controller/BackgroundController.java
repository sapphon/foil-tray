package org.sapphon.foiltray.controller;

import org.sapphon.foiltray.controller.request.AnimationFrameRequest;
import org.sapphon.foiltray.controller.request.CharacterMotionAnimationRequest;
import org.sapphon.foiltray.model.Game;
import org.sapphon.foiltray.repository.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


@Controller
public class BackgroundController {

    final GameRepository gameRepository;

    public BackgroundController(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    @PostMapping("/api/v1/game/{gameName}/art/background/frame")
    public ResponseEntity postBackgroundFrame(@PathVariable String gameName, @RequestBody AnimationFrameRequest incomingFrame) {
        Game gameFound = gameRepository.findByName(gameName);
        if(gameFound != null){
            if(incomingFrame.getMode().equals(AnimationFrameRequest.AnimationFrameRequestMode.REPLACE)) {
                gameFound.setBackgroundImages(Collections.singletonList(incomingFrame.getFrameData()));
            }
            else if(incomingFrame.getMode().equals(AnimationFrameRequest.AnimationFrameRequestMode.INSERT)){
                List<String> backgroundImages = new ArrayList<>(gameFound.getBackgroundImages());
                backgroundImages.add(incomingFrame.getSequenceNumber(), incomingFrame.getFrameData());
                gameFound.setBackgroundImages(backgroundImages);
            }
            gameRepository.save(gameFound);
            return ResponseEntity.ok().build();
        }else{
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/api/v1/game/{gameName}/art/background/frames")
    public ResponseEntity getAllBackgroundFrames(@PathVariable String gameName) {
        Game gameFound = gameRepository.findByName(gameName);
        if(gameFound != null){
            return ResponseEntity.ok(gameFound.getBackgroundImages());
        } else{
            return ResponseEntity.badRequest().build();
        }
    }

}
