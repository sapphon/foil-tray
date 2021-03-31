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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
public class DevelopmentTeamController {

    @Autowired
    private DevelopmentTeamRepository developmentTeamRepository;

    @GetMapping("/api/v1/team/{teamName}")
    public DevelopmentTeam getTeam(@PathVariable String teamName) {
        return developmentTeamRepository.findByName(teamName).orElse(null);
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

    @PostMapping("/api/v1/team/{teamName}/participant")
    public ResponseEntity<Participant> addParticipantToTeam(@PathVariable String teamName, @RequestBody Participant toAdd){
        Optional<DevelopmentTeam> teamMaybe = developmentTeamRepository.findByName(teamName);
        if(teamMaybe.isPresent()) {
            DevelopmentTeam teamWithNewMember = addTeamMember(teamMaybe.get(), toAdd);
            if(teamIsValidVisavisRolesAndParticipants(teamWithNewMember)) {
                developmentTeamRepository.save(teamWithNewMember);
                return ResponseEntity.ok(toAdd);
            }
            else return ResponseEntity.status(409).build();
        }
        else{
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/api/v1/team/{teamName}/role")
    public ResponseEntity<Role> addRoleToTeam(@PathVariable String teamName, @RequestBody Role toAdd){
        Optional<DevelopmentTeam> teamMaybe = developmentTeamRepository.findByName(teamName);
        if(teamMaybe.isPresent()) {
            DevelopmentTeam teamWithNewMember = addRole(teamMaybe.get(), toAdd);
                developmentTeamRepository.save(teamWithNewMember);
                return ResponseEntity.ok(toAdd);
        }
        else{
            return ResponseEntity.badRequest().build();
        }
    }

    private DevelopmentTeam addRole(DevelopmentTeam team, Role toAdd){
        List<Role> roles = new ArrayList<Role>(team.getComposition());
        roles.add(toAdd);
        team.setComposition(roles);
        return team;
    }

    private DevelopmentTeam addTeamMember(DevelopmentTeam developmentTeam, Participant toAdd) {
        List<Participant> members = new ArrayList<>(developmentTeam.getMembers());
        members.add(toAdd);
        developmentTeam.setMembers(members);
        return developmentTeam;
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
