package org.sapphon.foiltray.controller;

import org.junit.jupiter.api.Test;
import org.sapphon.foiltray.Game;
import org.sapphon.foiltray.repository.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
public class GameControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GameRepository gameRepository;

    @Test
    public void testGetGameByNameFailsIfGameIsNotFound() throws Exception{
        mockMvc.perform(get("/api/v1/game/ooptydoo")).andExpect(status().isBadRequest());
    }

    @Test
    public void testCanGetGameByName() throws Exception{
        when(gameRepository.findByName("ooptydoo")).thenReturn(new Game("ooptydoo"));
        String resultBody = mockMvc.perform(get("/api/v1/game/ooptydoo")).andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
        assertThat(resultBody).isEqualTo("{\"id\":0,\"name\":\"ooptydoo\"}");
    }

    @Test
    public void testPostNewGameFailsIfOneWithThatNameExists() throws Exception {
        when(gameRepository.findByName("doopdoop")).thenReturn(new Game("doopdoop"));
        mockMvc.perform(post("/api/v1/game").contentType(MediaType.APPLICATION_JSON).content( "{name:\"doopdoop\"}")).andExpect(status().isBadRequest());
    }

    @Test
    public void testCanPostNewGame() throws Exception {
        mockMvc.perform(post("/api/v1/game")
                .contentType(MediaType.APPLICATION_JSON)
                .content( "{\"name\":\"doopdoop\"}")).andExpect(status().isOk());
        verify(gameRepository).save(new Game("doopdoop"));
    }
}
