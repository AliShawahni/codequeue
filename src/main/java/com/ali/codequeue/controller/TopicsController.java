package com.ali.codequeue.controller;

import com.ali.codequeue.model.Attempt;
import com.ali.codequeue.model.Problem;
import com.ali.codequeue.model.Result;
import com.ali.codequeue.model.Topic;
import com.ali.codequeue.service.AttemptService;
import com.ali.codequeue.service.ProblemService;
import com.ali.codequeue.service.RecommendationService;
import com.ali.codequeue.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/topics")
public class TopicsController {
    public record TopicStats(String topic, int totalProblems, int solvedProblems, double passRate, double avgConfidence) {}
    @Autowired
    private ProblemService problemService;
    @Autowired
    private AttemptService attemptService;
    @Autowired
    private RecommendationService recommendationService;

    @GetMapping
    public List<TopicStats> getTopicStats(){
        List<Problem> allProblems = problemService.getAllProblems();
        List<Attempt> allAttempts = attemptService.getAllAttempts(); // need to add this method

        Map<String, List<Problem>> problemsByTopic = allProblems.stream()
                .collect(Collectors.groupingBy(Problem::getTopic));

        Map<String, List<Attempt>> attemptsByTopic = allAttempts.stream()
                .collect(Collectors.groupingBy(a -> a.getProblem().getTopic()));

        return problemsByTopic.keySet().stream().map(topic -> {
            List<Problem> problems = problemsByTopic.get(topic);
            List<Attempt> attempts = attemptsByTopic.getOrDefault(topic, Collections.emptyList());

            long succeededAttempts = attempts.stream()
                    .filter(a -> a.getResult() == Result.PASS || a.getResult() == Result.EASY)
                    .count();

            long solvedProblems = attempts.stream()
                    .filter(a -> a.getResult() == Result.PASS || a.getResult() == Result.EASY)
                    .map(a -> a.getProblem().getId()).distinct().count();

            double passRate = attempts.isEmpty() ? 0.0 : succeededAttempts / (double) attempts.size();
            double avgConfidence = attempts.stream().mapToInt(Attempt::getConfidence).average().orElse(2.5);

            return new TopicStats(topic, problems.size(), (int) solvedProblems, passRate, avgConfidence);
        }).collect(Collectors.toList());
    }

    @GetMapping("/{topic}/problems/solved")
    public List<Problem> getSolvedProblemsOfTopic(@PathVariable String topic){
        List<Attempt> allTopicAttempts = attemptService.getAttemptsOfTopic(topic);
        return allTopicAttempts.stream().filter(a -> (a.getResult() == Result.PASS || a.getResult() == Result.EASY) ).map(Attempt::getProblem).distinct().toList();
    }

    @GetMapping("/{topic}/recommendations")
    public List<Problem> getQueue(@PathVariable String topic, @RequestParam String mode){
        return mode.equals("discover")
                ? recommendationService.getDiscoverQueueOfTopic(topic)
                : recommendationService.getReviewQueueOfTopic(topic);
    }

    @Autowired
    private TopicService topicService;

    @GetMapping("/{topic}/notes")
    public String getNotes(@PathVariable String topic) {
        return topicService.getNotes(topic);
    }

    @PutMapping("/{topic}/notes")
    public Topic updateNotes(@PathVariable String topic, @RequestBody String notes) {
        return topicService.updateNotes(topic, notes);
    }
    /*
     * @RequestParam  → extracts query parameters from the URL (?key=value)
     *                  e.g. /topics/Trees/recommendations?mode=discover
     *                  Spring automatically maps ?mode=discover to the mode parameter
     *
     * Different from @PathVariable which extracts from the path itself (/{topic})
     *
     * The ternary operator routes to the correct service method based on the mode value
     */










}
