package com.scoretracker.scoretracker.util;
import com.scoretracker.scoretracker.model.ScoreEntity;

import java.util.ArrayList;
import java.util.TreeMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import lombok.ToString;

@ToString
@Component
@Scope("singleton") 
public class ScoreTrackingMap {

    private static final Logger LOGGER=LoggerFactory.getLogger(ScoreTrackingMap.class);

    @Value("${rankSize}")
    private int mapSize;
    private TreeMap<Integer, ArrayList<ScoreEntity>> scoreTrackingTreeMap;
    private final Lock lock = new ReentrantLock(true);
    private boolean isScoreTrackingMapReady = true;

    public ScoreTrackingMap() {
        scoreTrackingTreeMap = new TreeMap<Integer, ArrayList<ScoreEntity>>();
    }

    public TreeMap<Integer, ArrayList<ScoreEntity>> getScoreTrackingTreeMap() {
        TreeMap<Integer, ArrayList<ScoreEntity>> clonedScoreTrackingMap = null;
        if (!isScoreTrackingMapReady || scoreTrackingTreeMap == null) {
            return clonedScoreTrackingMap;
        }
        try {
            LOGGER.info("Cloning score tracking map");
            clonedScoreTrackingMap = (TreeMap<Integer, ArrayList<ScoreEntity>>) scoreTrackingTreeMap.clone();
        } catch (Exception e) {
            clonedScoreTrackingMap = null;
            LOGGER.error("Exception occured while cloning the score tracking map ", e);
            
        }
        return clonedScoreTrackingMap;
    }

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
            LOGGER.error("Exception occured while adding the score entity to the maps ", e);
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

    public void generateScoreTrackingMap(ArrayList<ScoreEntity> entities) {
        lock.lock();
        isScoreTrackingMapReady = false;
        try {
            LOGGER.debug("Generating score tracking map");
            scoreTrackingTreeMap.clear();
            for (ScoreEntity entity : entities) {
                addToMap(entity);
            }
            while(scoreTrackingTreeMap.keySet().size() > mapSize) {
                scoreTrackingTreeMap.remove(scoreTrackingTreeMap.firstKey());
            }
        }catch(Exception e) {
            LOGGER.error("Exception occured while generating the score tracking map ", e);
        }
        finally {
            isScoreTrackingMapReady = true;
            lock.unlock();
        }
        return;
    }

}
