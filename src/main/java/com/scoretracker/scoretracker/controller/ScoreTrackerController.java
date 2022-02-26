package com.scoretracker.scoretracker.controller;

import java.util.List;

import com.scoretracker.scoretracker.model.ScoreEntity;
import com.scoretracker.scoretracker.service.ScoreTrackerService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class ScoreTrackerController {

	@Autowired
	private ScoreTrackerService scoreTrackerService;

    @GetMapping("/scores")
	public List<ScoreEntity> getAllScores() {
		return scoreTrackerService.getAllScores();
	}

	@GetMapping("/scores/{id}")
	public ScoreEntity getScore(@PathVariable String id) {
		return scoreTrackerService.getScore(id);
	}

	@PostMapping("/scores")
	public ScoreEntity saveScore(@RequestBody ScoreEntity scoreTracker) {
		return scoreTrackerService.saveScore(scoreTracker);
	}

	@PutMapping("/scores/{id}")
	public ScoreEntity updateScore(@RequestBody ScoreEntity scoreTracker, @PathVariable String id) {
		return scoreTrackerService.updateScore(scoreTracker);
	}

	@DeleteMapping("/scores/{id}")
	public void deleteScore(@PathVariable String id) {
		scoreTrackerService.deleteScore(id);
	}

	@PostMapping("/addScore")
	public String addScoreEntity(@RequestBody ScoreEntity scoreEntity) {
		scoreTrackerService.addToDB(scoreEntity);
		return "score added asynchronously";
	}


}
