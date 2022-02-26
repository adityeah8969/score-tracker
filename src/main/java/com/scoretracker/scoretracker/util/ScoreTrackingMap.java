package com.scoretracker.scoretracker.util;

import java.util.ArrayList;
import java.util.TreeMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.scoretracker.scoretracker.model.ScoreEntity;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@Scope("singleton") 
public class ScoreTrackingMap {

    @Value("${rankSize}")
    private int mapSize;
    private TreeMap<Integer, ArrayList<ScoreEntity>> scoreTrackingTreeMap;
    private final Lock lock = new ReentrantLock(true);

    public ScoreTrackingMap() {
        scoreTrackingTreeMap = new TreeMap<Integer, ArrayList<ScoreEntity>>();
    }

    public TreeMap<Integer, ArrayList<ScoreEntity>> getScoreTrackingTreeMap() {
        return scoreTrackingTreeMap;
    }

    public void setScoreTrackingTreeMap(TreeMap<Integer, ArrayList<ScoreEntity>> scoreTrackingTreeMap) {
        this.scoreTrackingTreeMap = scoreTrackingTreeMap;
    }

    @Async
    public void addEntityToMap(ScoreEntity scoreEntity) {
        lock.lock();
        try {
            if (scoreTrackingTreeMap.keySet().size() < mapSize) {
                addToMap(scoreEntity);
            } else if (scoreEntity.getScore() >= scoreTrackingTreeMap.firstKey()) {
                addToMap(scoreEntity);
                if(scoreTrackingTreeMap.keySet().size() > mapSize) {
                    scoreTrackingTreeMap.remove(scoreTrackingTreeMap.firstKey());
                }
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        finally {
            lock.unlock();
        }
        return;
    }

    public void addToMap(ScoreEntity scoreEntity) {
        if (scoreTrackingTreeMap.containsKey(scoreEntity.getScore())) {
            scoreTrackingTreeMap.get(scoreEntity.getScore()).add(scoreEntity);
        } else {
            ArrayList<ScoreEntity> entities = new ArrayList<ScoreEntity>();
            entities.add(scoreEntity);
            scoreTrackingTreeMap.put(scoreEntity.getScore(), entities);
        }
        return;
    }

}
