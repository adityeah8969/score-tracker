package com.scoretracker.scoretracker.service;
import com.scoretracker.scoretracker.model.ScoreEntity;
import com.scoretracker.scoretracker.repository.ScoreTrackerRepository;
import com.scoretracker.scoretracker.util.ScoreTrackingMap;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class ScoreTrackerServiceImpl implements ScoreTrackerService{

    private static final Logger LOGGER=LoggerFactory.getLogger(ScoreTrackerServiceImpl.class);
    
    @Autowired
    private ScoreTrackerRepository scoreTrackerRepository;

    @Autowired
    private ScoreTrackingMap scoreTrackingMap;

    @Override
    public ScoreEntity getScore(String userId) {
        ScoreEntity scoreEntity;
        try {
            LOGGER.info("Getting score for userId: {}", userId);
            scoreEntity = (ScoreEntity)scoreTrackerRepository.findById(userId).get();
        } catch (Exception e) {
            scoreEntity = null;
            LOGGER.error("Exception occured while fetching score for userID %s: ",userId, e);
        } 
        return scoreEntity;
    }

    @Override
    public ScoreEntity saveScore(ScoreEntity scoreEntity) {
        try {
            LOGGER.info("Saving score for userID %s: ",scoreEntity.getUserId());
            scoreEntity = (ScoreEntity)scoreTrackerRepository.save(scoreEntity);
        } catch (Exception e) {
            scoreEntity = null;
            LOGGER.error("Exception occured while saving the score ", scoreEntity, e);
        }
        return scoreEntity;
    }

    @Override
    public List<ScoreEntity> getAllScores() {
        List<ScoreEntity> scoreEntities;
        try{
            LOGGER.info("Fetching all scores from DB");
            scoreEntities = (List<ScoreEntity>)scoreTrackerRepository.findAll();
        } catch (Exception e) {
            scoreEntities = new ArrayList<ScoreEntity>();
            LOGGER.error("Exception occured while getting the list of scores ", e);
        }
        return scoreEntities;
    }

    @Override
    public void deleteScore(String userId) {
        try {
            LOGGER.info("Deleting the score for userId %s", userId);
            scoreTrackerRepository.deleteById(userId);
        } catch (Exception e) {
            LOGGER.error("Exception occured while deleting the score %s", userId, e);
        }
        return;
    }

    @Override
    public ScoreEntity updateScore(ScoreEntity scoreEntity) {
        ScoreEntity scoreEntityInDB = getScore(scoreEntity.getUserId());
        if (scoreEntityInDB != null) {
            scoreEntityInDB.setScore(scoreEntity.getScore());
            return saveScore(scoreEntityInDB);
        } else {
           return saveScore(scoreEntity);
        }
    }

    @Async
    @Override
    public void addToDB(ScoreEntity scoreEntity) {
        LOGGER.info("Adding score to DB ", scoreEntity);
        scoreEntity = updateScore(scoreEntity);
        return;
    }

    @Async
    @Override
    public void addToTrackingMap(ScoreEntity scoreEntity) {
        LOGGER.info("Adding score to tracking map ", scoreEntity);
        scoreTrackingMap.addEntityToMap(scoreEntity);
        return;
    }

    @Async
    @Override
    public void generateScoreTrackingMap() {
        ArrayList<ScoreEntity> entities = (ArrayList<ScoreEntity>)getAllScores();
        scoreTrackingMap.generateScoreTrackingMap(entities);
        return;
    }

    @Override
    public TreeMap<Integer, ArrayList<ScoreEntity>> getScoreTrackingMap() {
        return scoreTrackingMap.getScoreTrackingTreeMap();
    }

}
