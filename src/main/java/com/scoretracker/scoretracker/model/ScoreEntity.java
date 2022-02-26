package com.scoretracker.scoretracker.model;

import java.io.Serializable;

import lombok.*;
import javax.persistence.*;

@Data
@ToString
@EqualsAndHashCode
@Entity(name = "score_tracker")
public class ScoreEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id 
    @Column(name = "user_id") 
    private String userId;
    
    @Column(name = "score")
    private int score;

    public ScoreEntity(String userId, int score) {
        this.userId = userId;
        this.score = score;
    }
    
    public ScoreEntity(){}

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }


}
