package org.sapphon.foiltray.repository;

import org.sapphon.foiltray.model.AnimationMotion;
import org.springframework.data.repository.CrudRepository;

public interface AnimationMotionRepository extends CrudRepository<AnimationMotion, Integer> {
    AnimationMotion findByName(String name);
}