package org.sapphon.foiltray.repository;

import org.sapphon.foiltray.model.Game;
import org.sapphon.foiltray.model.Role;
import org.springframework.data.repository.CrudRepository;

public interface RoleRepository extends CrudRepository<Role, Integer> {
}