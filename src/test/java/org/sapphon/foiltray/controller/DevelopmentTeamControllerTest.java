package org.sapphon.foiltray.controller;

import org.junit.jupiter.api.Test;
import org.sapphon.foiltray.model.DevelopmentTeam;
import org.sapphon.foiltray.model.Participant;
import org.sapphon.foiltray.model.Role;
import org.sapphon.foiltray.repository.DevelopmentTeamRepository;
import org.sapphon.foiltray.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.util.Lists.newArrayList;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
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
    public void testCanAddTeamWithParticipantWhoFitsRole() throws Exception {
        String participantString = "{\"role\":\"Game Designer\", \"participantName\":\"Gary.  Gary?\"}";
        String roleString = "{\"roleName\":\"Game Designer\"}";
        when(teamRepository.findByName("the name")).thenReturn(Optional.empty());
        mockMvc.perform(post("/api/v1/team/add").contentType(MediaType.APPLICATION_JSON).content("{\"name\":\"the name\", \"composition\":["+roleString+"], \"members\":["+participantString+"]}")).andExpect(status().isOk());
    }

    @Test
    public void testCannotAddTeamWithSameNameAsExistingTeam_CausesConflict() throws Exception {
        when(teamRepository.findByName("the name")).thenReturn(Optional.of(new DevelopmentTeam(1, "the name", newArrayList(), newArrayList())));
        mockMvc.perform(post("/api/v1/team/add").contentType(MediaType.APPLICATION_JSON).content("{\"name\":\"the name\"}")).andExpect(status().isConflict());

    }

    @Test
    public void testCannotAddTeamWithParticipantWhoDoesNotFitRoles_CausesBadRequest() throws Exception {
        String participantString = "{\"role\":\"Game Designer\", \"participantName\":\"Gary.  Gary?\"}";
        when(teamRepository.findByName("the name")).thenReturn(Optional.empty());
        mockMvc.perform(post("/api/v1/team/add").contentType(MediaType.APPLICATION_JSON).content("{\"name\":\"the name\", \"composition\":[], \"members\":["+participantString+"]}")).andExpect(status().isBadRequest());
    }

    @Test
    public void testCannotAddParticipantWhoDoesNotFitTeamRoles_CausesConflict() throws Exception {
        Role character_artist = new Role("Character Artist");
        DevelopmentTeam teamWithUnfilledRole = new DevelopmentTeam(1, "the name", newArrayList(), Collections.singletonList(character_artist));
        when(teamRepository.findByName("the name")).thenReturn(Optional.of(teamWithUnfilledRole));
        mockMvc.perform(post("/api/v1/team/the name/participant").contentType(MediaType.APPLICATION_JSON).content("{\"participantName\":\"Penelope\", \"role\":\"Environment Artist\"}")).andExpect(status().isConflict());
        verify(teamRepository, times(0)).save(any());
    }

    @Test
    public void testCanAddParticipantToExistingTeam() throws Exception {
        Role character_artist = new Role("Character Artist");
        DevelopmentTeam teamWithUnfilledRole = new DevelopmentTeam(1, "the name", newArrayList(), Collections.singletonList(character_artist));
        when(teamRepository.findByName("the name")).thenReturn(Optional.of(teamWithUnfilledRole));
        mockMvc.perform(post("/api/v1/team/the name/participant").contentType(MediaType.APPLICATION_JSON).content("{\"participantName\":\"Penelope\", \"role\":\"Character Artist\"}")).andExpect(status().isOk());
        teamWithUnfilledRole.setMembers(Collections.singletonList(new Participant(1, character_artist, "Penelope")));
        verify(teamRepository).save(teamWithUnfilledRole);
    }

    @Test
    public void testCanAddMultiplesOfARoleToAnExistingTeam() throws Exception {
        Role character_artist = new Role("Character Artist");
        Role character_artist2 = new Role("Character Artist");
        Role character_artist3 = new Role("Character Artist");
        DevelopmentTeam teamWithUnfilledRole = new DevelopmentTeam(1, "the name", newArrayList(), Arrays.asList(character_artist, character_artist2));
        when(teamRepository.findByName("the name")).thenReturn(Optional.of(teamWithUnfilledRole));
        mockMvc.perform(post("/api/v1/team/the name/role").contentType(MediaType.APPLICATION_JSON).content("{\"roleName\":\"Character Artist\"}")).andExpect(status().isOk());
        teamWithUnfilledRole.setComposition(newArrayList(character_artist, character_artist2, character_artist3));
        verify(teamRepository).save(teamWithUnfilledRole);
    }
}
