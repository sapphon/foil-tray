package org.sapphon.foiltray.controller;

import org.junit.jupiter.api.Test;
import org.sapphon.foiltray.model.DevelopmentTeam;
import org.sapphon.foiltray.model.Role;
import org.sapphon.foiltray.repository.DevelopmentTeamRepository;
import org.sapphon.foiltray.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.assertj.core.util.Lists.newArrayList;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = DevelopmentTeamController.class)
public class DevelopmentTeamControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DevelopmentTeamRepository teamRepository;

    @MockBean
    private RoleRepository roleRepository;

    @Test
    public void testCanAddADevelopmentTeam() throws Exception {
        DevelopmentTeam expectedTeam = new DevelopmentTeam(1, "hey ho let's go", newArrayList(), newArrayList());

        when(teamRepository.save(expectedTeam)).thenReturn(expectedTeam);

        mockMvc.perform(post("/api/v1/team/add").contentType(MediaType.APPLICATION_JSON).content("{\"name\":\"hey ho let's go\"}")).andExpect(status().isOk());
    }

    @Test
    public void testCanAddTeamWithRole() throws Exception {
        when(roleRepository.findAll()).thenReturn(newArrayList(new Role(1, "Game Designer"), new Role(2, "Level Designer")));
        DevelopmentTeam firstTeam = new DevelopmentTeam(1, "first", newArrayList(), newArrayList(new Role(1, "Game Designer")));
        mockMvc.perform(post("/api/v1/team/add").contentType(MediaType.APPLICATION_JSON).content("{\"name\":\"first\", \"composition\":[{\"roleName\":\"Game Designer\"}]}")).andExpect(status().isOk());
    }

    @Test
    public void testCannotAddTeamWithSameNameAsExistingTeam() throws Exception {
        when(teamRepository.findByName("the name")).thenReturn(Optional.of(new DevelopmentTeam(1, "the name", newArrayList(), newArrayList())));
        mockMvc.perform(post("/api/v1/team/add").contentType(MediaType.APPLICATION_JSON).content("{\"name\":\"the name\"}")).andExpect(status().isConflict());

    }

    @Test
    public void testCannotAddTeamWithParticipantWhoIsNotReal() {
        //TODO
    }
}
