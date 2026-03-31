package com.ali.codequeue.controller;

import com.ali.codequeue.model.Problem;
import com.ali.codequeue.service.RecommendationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/recommendations")
public class RecommendationController {

    @Autowired
    private RecommendationService recommendationService;

    @GetMapping("/review")
    public List<Problem> getReviewQueue() {
        return recommendationService.getReviewQueue();
    }

    @GetMapping("/discover")
    public List<Problem> getDiscoverQueue() {
        return recommendationService.getDiscoverQueue();
    }
}

/*
 * CONCEPTS USED:
 *
 * @RestController  → handles HTTP requests, returns JSON automatically
 * @RequestMapping  → base URL (/recommendations)
 * @GetMapping      → GET /recommendations/review   → returns review queue
 *                    GET /recommendations/discover  → returns discover queue
 * @Autowired       → injects RecommendationService automatically
 */