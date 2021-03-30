package org.sapphon.foiltray.controller;

import org.sapphon.foiltray.model.DevelopmentTeam;
import org.sapphon.foiltray.model.Participant;
import org.sapphon.foiltray.model.Role;
import org.sapphon.foiltray.repository.DevelopmentTeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
public class DevelopmentTeamController {

    @Autowired
    private DevelopmentTeamRepository developmentTeamRepository;

    @GetMapping("/api/v1/team/{teamId}")
    public DevelopmentTeam getTeam(@PathVariable Integer teamId) {
        return developmentTeamRepository.findById(teamId).orElse(null);
    }

    @PostMapping("/api/v1/team/add")
    public ResponseEntity<DevelopmentTeam> addDevelopmentTeam(@RequestBody DevelopmentTeam teamToAdd){
        if(developmentTeamRepository.findByName(teamToAdd.getName()).isPresent()){
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        else if(!teamIsValidVisavisRolesAndParticipants(teamToAdd)){
            return ResponseEntity.badRequest().build();
        }
        else{
            return ResponseEntity.ok(developmentTeamRepository.save(teamToAdd));
        }
    }

    private boolean teamIsValidVisavisRolesAndParticipants(DevelopmentTeam teamToAdd) {
        if(teamToAdd.getMembers() == null){
            return true;
        }
        Set<Role> rolesOnTeam = teamToAdd.getMembers().stream().map(Participant::getRole).collect(Collectors.toSet());
        for (Role role: rolesOnTeam) {
            if(teamToAdd.getMembers().stream().filter(person -> person.getRole() == role).count() > teamToAdd.getComposition().stream().filter(slot -> slot.equals(role)).count()){
                return false;
            }
        }
        return true;
    }
}
