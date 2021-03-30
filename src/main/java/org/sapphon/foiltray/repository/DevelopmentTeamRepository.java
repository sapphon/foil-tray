package org.sapphon.foiltray.repository;

import org.sapphon.foiltray.model.DevelopmentTeam;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface DevelopmentTeamRepository extends CrudRepository<DevelopmentTeam, Integer> {
    Optional<DevelopmentTeam> findByName(String name);
}
