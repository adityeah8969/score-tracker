package com.scoretracker.scoretracker.model;

import java.io.Serializable;

import lombok.*;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Data
@ToString
@EqualsAndHashCode
@Entity(name = "score_tracker")
public class ScoreEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id 
    @Column(name = "user_id") 
    @NotBlank(message = "userId is mandatory")
    private String userId;

    @Column(name = "user_name")
    @NotBlank(message = "userName is mandatory")
    private String userName;
    
    @Column(name = "score")
    private int score;

    public ScoreEntity(){}

    public ScoreEntity(String userId, String userName, int score) {
        this.userId = userId;
        this.userName = userName;
        this.score = score;
    }

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

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }


}
