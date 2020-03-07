package org.sapphon.foiltray;

import org.junit.jupiter.api.Test;
import org.sapphon.foiltray.repository.AnimationMotionRepository;
import org.sapphon.foiltray.repository.CharacterAnimationRepository;
import org.sapphon.foiltray.repository.CharacterRepository;
import org.sapphon.foiltray.repository.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class FoilTrayApplicationTests {

    @Autowired
    CharacterAnimationRepository characterAnimationRepository;

    @Autowired
    AnimationMotionRepository animationMotionRepository;

    @Autowired
    CharacterRepository characterRepository;

    @Autowired
    GameRepository gameRepository;

    @Test
    public void contextLoads() {
    }

    @Test
    public void testCannotSubmitAnAnimationForAGameThatDoesNotExist() {
        gameRepository.deleteAll();
        characterRepository.save(new Character("Billy"));
        animationMotionRepository.save(new AnimationMotion("idle"));

    }

    @Test
    public void testCannotSubmitAnAnimationForAMotionThatDoesNotExist() {

    }

    @Test
    public void testCannotSubmitAnAnimationForACharacterThatDoesNotExist() {

    }

    @Test
    public void testCanSubmitAOneFileCharacterAnimation() {

    }

}
