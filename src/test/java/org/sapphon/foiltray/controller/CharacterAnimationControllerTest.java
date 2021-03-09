package org.sapphon.foiltray.controller;

import org.junit.jupiter.api.Test;
import org.sapphon.foiltray.model.AnimationMotion;
import org.sapphon.foiltray.model.CharacterAnimation;
import org.sapphon.foiltray.model.Game;
import org.sapphon.foiltray.model.Persona;
import org.sapphon.foiltray.repository.AnimationMotionRepository;
import org.sapphon.foiltray.repository.CharacterAnimationRepository;
import org.sapphon.foiltray.repository.CharacterRepository;
import org.sapphon.foiltray.repository.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = CharacterAnimationController.class)
public class CharacterAnimationControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GameRepository gameRepository;

    @MockBean
    private CharacterRepository characterRepository;

    @MockBean
    private AnimationMotionRepository motionRepository;

    @MockBean
    private CharacterAnimationRepository characterAnimationRepository;

    @Test
    public void testCannotGetAnAnimationForAGameThatDoesNotExist() throws Exception {
        when(gameRepository.findById(any())).thenReturn(Optional.empty());
        when(characterRepository.findById(any())).thenReturn(Optional.of(new Persona(2, "b")));
        when(motionRepository.findById(any())).thenReturn(Optional.of(new AnimationMotion(3, "c")));

        mockMvc.perform(get("/api/v1/art/character").queryParam("gameId", "1").queryParam("characterId", "2").queryParam("motionId", "3")).andExpect(status().isBadRequest());
    }

    @Test
    public void testCannotGetAnAnimationForAMotionThatDoesNotExist() throws Exception {
        when(gameRepository.findById(any())).thenReturn(Optional.empty());
        when(characterRepository.findById(any())).thenReturn(Optional.of(new Persona(2, "b")));
        when(motionRepository.findById(any())).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/v1/art/character").queryParam("gameId", "1").queryParam("characterId", "2").queryParam("motionId", "3")).andExpect(status().isBadRequest());
    }

    @Test
    public void testCannotGetAnAnimationForACharacterThatDoesNotExist() throws Exception {
        when(gameRepository.findById(any())).thenReturn(Optional.empty());
        when(characterRepository.findById(any())).thenReturn(Optional.empty());
        when(motionRepository.findById(any())).thenReturn(Optional.of(new AnimationMotion(3, "c")));

        mockMvc.perform(get("/api/v1/art/character").queryParam("gameId", "1").queryParam("characterId", "2").queryParam("motionId", "3")).andExpect(status().isBadRequest());
    }

    @Test
    public void testCannotGetANonExistentAnimation() throws Exception {
        when(gameRepository.findById(any())).thenReturn(Optional.of(new Game(1, "a")));
        when(characterRepository.findById(any())).thenReturn(Optional.of(new Persona(2, "b")));
        when(motionRepository.findById(any())).thenReturn(Optional.of(new AnimationMotion(3, "c")));

        when(characterAnimationRepository.findByGameAndCharacterAndMotion(any(), any(), any())).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/v1/art/character").queryParam("gameId", "1").queryParam("characterId", "2").queryParam("motionId", "3")).andExpect(status().isBadRequest());
    }

    @Test
    public void testCanGetOneAnimationCorrectly() throws Exception {
        Game expectedGame = new Game(1, "a");
        when(gameRepository.findById(1)).thenReturn(Optional.of(expectedGame));
        Persona expectedCharacter = new Persona(2, "b");
        when(characterRepository.findById(2)).thenReturn(Optional.of(expectedCharacter));
        AnimationMotion expectedMotion = new AnimationMotion(3, "c");
        when(motionRepository.findById(3)).thenReturn(Optional.of(expectedMotion));

        when(characterAnimationRepository.findByGameAndCharacterAndMotion(expectedGame, expectedCharacter, expectedMotion)).thenReturn(Optional.of(new CharacterAnimation(1, expectedGame, expectedCharacter, new ArrayList<String>(), expectedMotion)));

        mockMvc.perform(get("/api/v1/art/character").queryParam("gameId", "1").queryParam("characterId", "2").queryParam("motionId", "3")).andExpect(status().isOk());
    }


    @Test
    public void testCannotSubmitAnAnimationForAGameThatDoesNotExist() {

    }

    @Test
    public void testCannotSubmitAnAnimationForAMotionThatDoesNotExist() {

    }

    @Test
    public void testCannotSubmitAnAnimationForACharacterThatDoesNotExist() {

    }

    @Test
    public void testCanAppendAFrameToAnEmptyCharacterAnimation() throws Exception {
        Game expectedGame = new Game(1, "a");
        when(gameRepository.findById(1)).thenReturn(Optional.of(expectedGame));
        Persona expectedCharacter = new Persona(2, "b");
        when(characterRepository.findById(2)).thenReturn(Optional.of(expectedCharacter));
        AnimationMotion expectedMotion = new AnimationMotion(3, "c");
        when(motionRepository.findById(3)).thenReturn(Optional.of(expectedMotion));

        when(characterAnimationRepository.findByGameAndCharacterAndMotion(expectedGame, expectedCharacter, expectedMotion)).thenReturn(Optional.of(new CharacterAnimation(1, expectedGame, expectedCharacter, new ArrayList<String>(), expectedMotion)));

        mockMvc.perform(post("/api/v1/art/character/frame")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"frameData\":\"an image as a straaaaang\"}")
                .queryParam("gameId", "1")
                .queryParam("characterId", "2")
                .queryParam("motionId", "3")).andExpect(status().isOk());
        verify(characterAnimationRepository).save(any());
    }

    @Test
    public void testCanAppendAFrameToTheEndOfACharacterAnimationWhenIndexIsUnspecified() throws Exception {
        Game expectedGame = new Game(1, "a");
        when(gameRepository.findById(1)).thenReturn(Optional.of(expectedGame));
        Persona expectedCharacter = new Persona(2, "b");
        when(characterRepository.findById(2)).thenReturn(Optional.of(expectedCharacter));
        AnimationMotion expectedMotion = new AnimationMotion(3, "c");
        when(motionRepository.findById(3)).thenReturn(Optional.of(expectedMotion));

        when(characterAnimationRepository.findByGameAndCharacterAndMotion(expectedGame, expectedCharacter, expectedMotion)).thenReturn(Optional.of(new CharacterAnimation(1, expectedGame, expectedCharacter, Stream.of("a").collect(Collectors.toList()), expectedMotion)));

        mockMvc.perform(post("/api/v1/art/character/frame")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"frameData\":\"b\"}")
                .queryParam("gameId", "1")
                .queryParam("characterId", "2")
                .queryParam("motionId", "3")).andExpect(status().isOk());
        verify(characterAnimationRepository).save(new CharacterAnimation(1, expectedGame, expectedCharacter, Stream.of("a", "b").collect(Collectors.toList()), expectedMotion));
    }

    @Test
    public void testCanInsertAFrameInACharacterAnimation() throws Exception {
        Game expectedGame = new Game(1, "a");
        when(gameRepository.findById(1)).thenReturn(Optional.of(expectedGame));
        Persona expectedCharacter = new Persona(2, "b");
        when(characterRepository.findById(2)).thenReturn(Optional.of(expectedCharacter));
        AnimationMotion expectedMotion = new AnimationMotion(3, "c");
        when(motionRepository.findById(3)).thenReturn(Optional.of(expectedMotion));

        when(characterAnimationRepository.findByGameAndCharacterAndMotion(expectedGame, expectedCharacter, expectedMotion)).thenReturn(Optional.of(new CharacterAnimation(1, expectedGame, expectedCharacter, Stream.of("a").collect(Collectors.toList()), expectedMotion)));

        mockMvc.perform(post("/api/v1/art/character/frame")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"frameData\":\"b\"," +
                        "\"sequenceNumber\":0," +
                        "\"mode\":\"INSERT\"}")
                .queryParam("gameId", "1")
                .queryParam("characterId", "2")
                .queryParam("motionId", "3")).andExpect(status().isOk());
        verify(characterAnimationRepository).save(new CharacterAnimation(1, expectedGame, expectedCharacter, Stream.of("b", "a").collect(Collectors.toList()), expectedMotion));
    }

    @Test
    public void testCanReplaceAFrameInACharacterAnimation_AndModeIsReplaceWhenUnspecified() throws Exception {
        Game expectedGame = new Game(1, "a");
        when(gameRepository.findById(1)).thenReturn(Optional.of(expectedGame));
        Persona expectedCharacter = new Persona(2, "b");
        when(characterRepository.findById(2)).thenReturn(Optional.of(expectedCharacter));
        AnimationMotion expectedMotion = new AnimationMotion(3, "c");
        when(motionRepository.findById(3)).thenReturn(Optional.of(expectedMotion));

        when(characterAnimationRepository.findByGameAndCharacterAndMotion(expectedGame, expectedCharacter, expectedMotion)).thenReturn(Optional.of(new CharacterAnimation(1, expectedGame, expectedCharacter, Stream.of("a", "b", "c").collect(Collectors.toList()), expectedMotion)));

        mockMvc.perform(post("/api/v1/art/character/frame")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"frameData\":\"BBB\"," +
                        "\"sequenceNumber\":1}")
                .queryParam("gameId", "1")
                .queryParam("characterId", "2")
                .queryParam("motionId", "3")).andExpect(status().isOk());
        verify(characterAnimationRepository).save(new CharacterAnimation(1, expectedGame, expectedCharacter, Stream.of("a", "BBB", "c").collect(Collectors.toList()), expectedMotion));
    }
}