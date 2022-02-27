package com.scoretracker.scoretracker.service;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import com.scoretracker.scoretracker.model.ScoreEntity;

public interface ScoreTrackerService {
    ScoreEntity saveScore(ScoreEntity scoreTracker);
    ScoreEntity updateScore(ScoreEntity scoreTracker);
    List<ScoreEntity> getAllScores();
    ScoreEntity getScore(String userId);
    void deleteScore(String userId);
    void addToDB(ScoreEntity scoreEntity);
    void addToTrackingMap(ScoreEntity scoreEntity);
    void generateScoreTrackingMap();
    TreeMap<Integer, ArrayList<ScoreEntity>> getScoreTrackingMap();
}
