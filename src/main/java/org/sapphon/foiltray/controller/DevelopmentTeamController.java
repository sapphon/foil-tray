package org.sapphon.foiltray.controller;

import org.sapphon.foiltray.model.DevelopmentTeam;
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
        else{
            return ResponseEntity.ok(developmentTeamRepository.save(teamToAdd));
        }
    }
}
