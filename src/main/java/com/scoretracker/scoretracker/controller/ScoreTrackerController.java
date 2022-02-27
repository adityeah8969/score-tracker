package com.scoretracker.scoretracker.controller;
import com.scoretracker.scoretracker.model.ScoreEntity;
import com.scoretracker.scoretracker.service.ScoreTrackerService;
import com.scoretracker.scoretracker.util.HelperUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
@Validated
public class ScoreTrackerController {

	@Autowired
	private ScoreTrackerService scoreTrackerService;

	@Autowired
	private HelperUtils helperUtils;

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

	// Above can be disabled, just used for testing
	// Following are the only controllers important to the application.

	@PostMapping("/addScore")
	public String addScoreEntity(@Valid @RequestBody ScoreEntity scoreEntity) {
		scoreTrackerService.addToDB(scoreEntity);
		scoreTrackerService.addToTrackingMap(scoreEntity);
		return "score added asynchronously";
	}

	@GetMapping("/getBestScores")
	public String getBestScores() {
		TreeMap<Integer, ArrayList<ScoreEntity>> scoreTrackingMap = scoreTrackerService.getScoreTrackingMap();
		if (scoreTrackingMap == null || scoreTrackingMap.size() == 0) {
			return "No scores found";
		} else {
			return helperUtils.beautifyResponse(scoreTrackingMap);
		}
	}

	@PostMapping("/generateScoreTrackingMap")
	public String generateScoreTrackingMap() {
		scoreTrackerService.generateScoreTrackingMap();
		return "generating the results";
	}

}
