package com.scoretracker.scoretracker.repository;

import com.scoretracker.scoretracker.model.ScoreEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScoreTrackerRepository extends CrudRepository<ScoreEntity, String> {
}
