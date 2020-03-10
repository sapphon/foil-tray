package org.sapphon.foiltray.controller;

import org.junit.jupiter.api.Test;
import org.sapphon.foiltray.Game;
import org.sapphon.foiltray.Character;
import org.sapphon.foiltray.repository.CharacterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = CharacterController.class)
public class CharacterControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CharacterRepository characterRepository;

    @Test
    public void testGetGameByNameFailsIfGameIsNotFound() throws Exception{
        mockMvc.perform(get("/api/v1/character/ooptydoo")).andExpect(status().isBadRequest());
    }

    @Test
    public void testCanGetGameByName() throws Exception{
        when(characterRepository.findByName("ooptydoo")).thenReturn(new Character("ooptydoo"));
        String resultBody = mockMvc.perform(get("/api/v1/character/ooptydoo")).andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
        assertThat(resultBody).isEqualTo("{\"id\":0,\"name\":\"ooptydoo\"}");
    }

    @Test
    public void testPostNewGameFailsIfOneWithThatNameExists() throws Exception {
        when(characterRepository.findByName("doopdoop")).thenReturn(new Character("doopdoop"));
        mockMvc.perform(post("/api/v1/character").contentType(MediaType.APPLICATION_JSON).content( "{name:\"doopdoop\"}")).andExpect(status().isBadRequest());
    }

    @Test
    public void testCanPostNewGame() throws Exception {
        mockMvc.perform(post("/api/v1/character")
                .contentType(MediaType.APPLICATION_JSON)
                .content( "{\"name\":\"doopdoop\"}")).andExpect(status().isOk());
        verify(characterRepository).save(new Character("doopdoop"));
    }
}
