package org.sapphon.foiltray.controller;

import org.junit.jupiter.api.Test;
import org.sapphon.foiltray.model.Game;
import org.sapphon.foiltray.repository.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.util.Lists.newArrayList;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = BackgroundController.class)
class BackgroundControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GameRepository gameRepository;

    @Test
    public void canSubmitOneBackgroundFrameToAGameWithoutAny() throws Exception {
        Game expectedGame = new Game("gameWithNoInitialBackgroundFrames");
        when(gameRepository.findByName("gameWithNoInitialBackgroundFrames")).thenReturn(expectedGame);
        String singleBackgroundFrame = "a background frame";

        mockMvc.perform(post("/api/v1/game/gameWithNoInitialBackgroundFrames/art/background/frame")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"frameData\":\""+singleBackgroundFrame+"\"}"))
                .andExpect(status().isOk());

        expectedGame.setBackgroundImages(newArrayList(singleBackgroundFrame));
        verify(gameRepository).save(expectedGame);
    }

    @Test
    public void canReplaceABackgroundFrame(){
        fail("Write me!");
    }

    @Test
    public void canInsertABackgroundFrame(){
        fail("Write me!");
    }

    @Test
    public void canGetAllBackgroundFramesByGameName(){
        Game expectedGame = new Game("gameWith2Frames");
        fail("Write me!");
    }
}