package org.sapphon.foiltray.controller;

import org.junit.jupiter.api.Test;
import org.sapphon.foiltray.model.AnimationMotion;
import org.sapphon.foiltray.repository.AnimationMotionRepository;
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

@WebMvcTest(controllers = AnimationMotionController.class)
public class AnimationMotionControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AnimationMotionRepository motionRepository;

    @Test
    public void testGetMotionByNameFailsIfMotionIsNotFound() throws Exception {
        mockMvc.perform(get("/api/v1/motion/doing_jumping_jacks")).andExpect(status().isBadRequest());
    }

    @Test
    public void testCanGetMotionByName() throws Exception {
        when(motionRepository.findByName("ooptydoo")).thenReturn(new AnimationMotion("ooptydoo"));
        String resultBody = mockMvc.perform(get("/api/v1/motion/ooptydoo")).andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
        assertThat(resultBody).isEqualTo("{\"id\":0,\"name\":\"ooptydoo\"}");
    }

    @Test
    public void testPostNewMotionFailsIfOneWithThatNameExists() throws Exception {
        when(motionRepository.findByName("doopdoop")).thenReturn(new AnimationMotion("doopdoop"));
        mockMvc.perform(post("/api/v1/motion").contentType(MediaType.APPLICATION_JSON).content("{name:\"doopdoop\"}")).andExpect(status().isBadRequest());
    }

    @Test
    public void testCanPostNewMotion() throws Exception {
        mockMvc.perform(post("/api/v1/motion")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"doopdoop\"}")).andExpect(status().isOk());
        verify(motionRepository).save(new AnimationMotion("doopdoop"));
    }
}
