package com.scoretracker.scoretracker.service;

import java.util.List;

import com.scoretracker.scoretracker.model.ScoreEntity;
import com.scoretracker.scoretracker.repository.ScoreTrackerRepository;
import com.scoretracker.scoretracker.util.ScoreTrackingMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class ScoreTrackerServiceImpl implements ScoreTrackerService{
    
    @Autowired
    private ScoreTrackerRepository scoreTrackerRepository;

    @Autowired
    private ScoreTrackingMap scoreTrackingMap;

    @Override
    public ScoreEntity getScore(String userId) {
        return (ScoreEntity)scoreTrackerRepository.findById(userId).get();
    }

    @Override
    public ScoreEntity saveScore(ScoreEntity scoreTracker) {
        return scoreTrackerRepository.save(scoreTracker);
    }

    @Override
    public List<ScoreEntity> getAllScores() {
        return (List<ScoreEntity>)scoreTrackerRepository.findAll();
    }

    @Override
    public void deleteScore(String userId) {
        scoreTrackerRepository.deleteById(userId);
    }

    @Override
    public ScoreEntity updateScore(ScoreEntity scoreTracker) {
        ScoreEntity scoreTrackerInDB = scoreTrackerRepository.findById(scoreTracker.getUserId()).get();
        if (scoreTrackerInDB != null) {
            scoreTrackerInDB.setScore(scoreTracker.getScore());
            return scoreTrackerRepository.save(scoreTrackerInDB);
        } else {
           return scoreTrackerRepository.save(scoreTracker);
        }
    }

    @Async
    @Override
    public void addToDB(ScoreEntity scoreEntity) {
        scoreEntity = updateScore(scoreEntity);
        // print the result here
    }

    @Async
    @Override
    public void addToPriorityQueue(ScoreEntity scoreEntity) {
        // TODO Auto-generated method stub
        
    }

}
