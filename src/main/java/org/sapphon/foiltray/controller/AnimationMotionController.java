package org.sapphon.foiltray.controller;

import lombok.Data;
import org.sapphon.foiltray.model.AnimationMotion;
import org.sapphon.foiltray.repository.AnimationMotionRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Controller
public class AnimationMotionController {

    private final AnimationMotionRepository animationMotionRepository;

    @Data
    private class Motions{
        List<AnimationMotion> animationMotions;
        public Motions(List<AnimationMotion> motions){
            this.animationMotions = motions;
        }
    }

    public AnimationMotionController(AnimationMotionRepository animationMotionRepository) {
        this.animationMotionRepository = animationMotionRepository;
        if(animationMotionRepository.count() == 0){
            addDefaultMotions(animationMotionRepository);
        }
    }

    private void addDefaultMotions(AnimationMotionRepository repository) {
        repository.save(new AnimationMotion("Idle"));
        repository.save(new AnimationMotion("Move"));
        repository.save(new AnimationMotion("Jump"));
        repository.save(new AnimationMotion("Get Item"));
        repository.save(new AnimationMotion("Use Item"));
        repository.save(new AnimationMotion("Talk"));
        repository.save(new AnimationMotion("Hit"));
        repository.save(new AnimationMotion("Shoot"));
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

    @GetMapping("/api/v1/motions")
    public ResponseEntity<Motions> getMotions(){
        return new ResponseEntity<>(new Motions(StreamSupport
                .stream(animationMotionRepository.findAll().spliterator(), false)
                .collect(Collectors.toList())), HttpStatus.OK);
    }

    @DeleteMapping("/api/v1/motion/{motionName}")
    public ResponseEntity deleteMotion(@PathVariable String motionName){
        AnimationMotion found = animationMotionRepository.findByName(motionName);
        if(found != null){
            animationMotionRepository.delete(found);
            return ResponseEntity.ok().build();
        }else return ResponseEntity.badRequest().build();
    }
}
