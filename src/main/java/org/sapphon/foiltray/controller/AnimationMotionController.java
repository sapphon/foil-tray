package org.sapphon.foiltray.controller;

import org.sapphon.foiltray.model.AnimationMotion;
import org.sapphon.foiltray.repository.AnimationMotionRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class AnimationMotionController {

    private final AnimationMotionRepository animationMotionRepository;

    public AnimationMotionController(AnimationMotionRepository animationMotionRepository) {
        this.animationMotionRepository = animationMotionRepository;
    }

    @GetMapping("/api/v1/motion/{motionName}")
    public ResponseEntity<AnimationMotion> getMotion(@PathVariable String motionName) {
        AnimationMotion found = animationMotionRepository.findByName(motionName);
        return found != null ? ResponseEntity.ok(found) : new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/api/v1/motion")
    public ResponseEntity postMotion(@RequestBody AnimationMotion incomingMotion) {
        AnimationMotion found = animationMotionRepository.findByName(incomingMotion.getName());
        return found == null ? new ResponseEntity<>(animationMotionRepository.save(incomingMotion), HttpStatus.OK) : new ResponseEntity(HttpStatus.BAD_REQUEST);
    }
}
