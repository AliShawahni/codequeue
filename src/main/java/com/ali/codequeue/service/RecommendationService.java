package com.ali.codequeue.service;

import com.ali.codequeue.model.Attempt;
import com.ali.codequeue.model.Difficulty;
import com.ali.codequeue.model.Problem;
import com.ali.codequeue.model.Result;
import com.ali.codequeue.repository.AttemptRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class RecommendationService {

    @Autowired
    private AttemptRepository attemptRepository;

    @Autowired
    private ProblemService problemService;

    public List<Problem> getReviewQueueOfTopic(String topic) {
        List<Attempt> topicAttempts =  attemptRepository.findByProblemTopic(topic);
        return getReviewQueueHelper(topicAttempts);

    }

    public List<Problem> getReviewQueue() {
        List<Attempt> allAttempts = attemptRepository.findAll();
        return getReviewQueueHelper(allAttempts);
        }


    public List<Problem> getReviewQueueHelper(List<Attempt> allAttempts){
        Map<Long, Attempt> latestAttemptPerProblem = new HashMap<>();
        for (Attempt a : allAttempts) {
            Long pid = a.getProblem().getId();
            if (!latestAttemptPerProblem.containsKey(pid) ||
                    a.getDate().isAfter(latestAttemptPerProblem.get(pid).getDate())) {
                latestAttemptPerProblem.put(pid, a);
            }
        }

        return latestAttemptPerProblem.values().stream()
                .sorted((a1, a2) -> Double.compare(
                        calculatePriorityScore(a2),
                        calculatePriorityScore(a1)))
                .limit(10)
                .map(Attempt::getProblem)
                .collect(Collectors.toList());
    }

    public List<Problem> getDiscoverQueue() {
        List<Attempt> allAttempts = attemptRepository.findAll();
        return getDiscoverQueueHelper(allAttempts);
    }
    public List<Problem> getDiscoverQueueOfTopic(String topic){
        List<Attempt> topicAttempts =  attemptRepository.findByProblemTopic(topic);
        return getDiscoverQueueHelper(topicAttempts);
    }

    public List<Problem> getDiscoverQueueHelper(List<Attempt> allAttempts) {
        Set<Long> attemptedIds = allAttempts.stream()
                .map(a -> a.getProblem().getId())
                .collect(Collectors.toSet());

        Map<String, List<Attempt>> attemptsByTopic = allAttempts.stream()
                .collect(Collectors.groupingBy(a -> a.getProblem().getTopic()));

        Map<String, Double> weaknessCache = new HashMap<>();

        return problemService.getAllProblems().stream()
                .filter(p -> !attemptedIds.contains(p.getId()))
                .sorted((p1, p2) -> {
                    double w1 = weaknessCache.computeIfAbsent(p1.getTopic(),
                            t -> calculateTopicWeaknessFromList(attemptsByTopic.getOrDefault(t, Collections.emptyList())));
                    double w2 = weaknessCache.computeIfAbsent(p2.getTopic(),
                            t -> calculateTopicWeaknessFromList(attemptsByTopic.getOrDefault(t, Collections.emptyList())));
                    double s1 = w1 * getDifficultyMultiplier(p1.getDifficulty(), w1);
                    double s2 = w2 * getDifficultyMultiplier(p2.getDifficulty(), w2);
                    return Double.compare(s2, s1);
                })
                .limit(10)
                .collect(Collectors.toList());
    }

    private double calculatePriorityScore(Attempt attempt) {
        long daysSinceLast = ChronoUnit.DAYS.between(attempt.getDate(), LocalDate.now());
        double recencyScore = Math.min(daysSinceLast / 30.0, 1.0);

        double resultScore = switch (attempt.getResult()) {
            case FAIL -> 1.0;
            case PASS -> 0.5;
            case EASY -> 0.2;
        };

        double confidenceScore = (6 - attempt.getConfidence()) / 5.0;

        return (0.20 * recencyScore) + (0.35 * resultScore) + (0.45 * confidenceScore);
    }

    private double calculateTopicWeaknessFromList(List<Attempt> attempts) {
        if (attempts.isEmpty()) return 0.5;

        double avgConfidence = attempts.stream()
                .mapToInt(Attempt::getConfidence)
                .average()
                .orElse(3.0);

        long failCount = attempts.stream()
                .filter(a -> a.getResult() == Result.FAIL)
                .count();

        double confidenceWeakness = (6 - avgConfidence) / 5.0;
        double resultPenalty = (double) failCount / attempts.size();

        return (0.6 * confidenceWeakness) + (0.4 * resultPenalty);
    }

    private double calculateTopicWeakness(String topic) {
        List<Attempt> attempts = attemptRepository.findByProblemTopic(topic);
        return calculateTopicWeaknessFromList(attempts);
    }

    private double getDifficultyMultiplier(Difficulty difficulty, double topicWeakness) {
        if (topicWeakness > 0.7) {
            return switch (difficulty) {
                case EASY -> 1.0;
                case MEDIUM -> 0.3;
                case HARD -> 0.0;
            };
        } else if (topicWeakness > 0.4) {
            return switch (difficulty) {
                case EASY -> 0.3;
                case MEDIUM -> 1.0;
                case HARD -> 0.2;
            };
        } else {
            return switch (difficulty) {
                case EASY -> 0.0;
                case MEDIUM -> 0.7;
                case HARD -> 0.3;
            };
        }
    }
}