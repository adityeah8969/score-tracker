package com.scoretracker.scoretracker.util;
import com.scoretracker.scoretracker.model.ScoreEntity;

import java.util.ArrayList;
import java.util.TreeMap;

import org.springframework.stereotype.Component;

@Component
public class HelperUtils {

    public String beautifyResponse(TreeMap<Integer, ArrayList<ScoreEntity>> scoreTrackingMap) {
        String beautifiedString = "";
        for (Integer key : scoreTrackingMap.descendingKeySet()) {
            beautifiedString += key + ": ";
            for (ScoreEntity scoreEntity : scoreTrackingMap.get(key)) {
                beautifiedString += scoreEntity.getUserName() + " ";
            }
            beautifiedString += "\n";
        }
        return beautifiedString;
    }

}
