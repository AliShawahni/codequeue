package com.ali.codequeue.service;

import com.ali.codequeue.model.Attempt;
import com.ali.codequeue.model.Problem;
import com.ali.codequeue.repository.AttemptRepository;
import com.ali.codequeue.repository.ProblemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AttemptService {

    @Autowired
    private AttemptRepository attemptRepository;

    @Autowired
    private ProblemRepository problemRepository;

    public Attempt addAttempt(Long problemId, Attempt attempt) {
        Problem problem = problemRepository.findById(problemId)
                .orElseThrow(() -> new RuntimeException("Problem not found"));
        attempt.setProblem(problem);
        return attemptRepository.save(attempt);
    }

    public List<Attempt> getAttemptsByProblem(Long problemId) {
        return attemptRepository.findByProblemId(problemId);
    }

    public void deleteAttempt(Long id) {
        attemptRepository.deleteById(id);
    }

    public Attempt updateNotes(Long id, String notes) {
        Attempt attempt = attemptRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Attempt not found"));
        attempt.setNotes(notes);
        return attemptRepository.save(attempt);
    }

    public Attempt toggleFlaggedForReview(Long id) {
        Attempt attempt = attemptRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Attempt not found"));
        attempt.setFlaggedForReview(!attempt.getFlaggedForReview());
        return attemptRepository.save(attempt);
    }
}

/*
 * CONCEPTS USED:
 *
 * @Service        → Spring Boot manages this class, creates one instance at startup
 * @Autowired      → Spring injects the repositories automatically (dependency injection)
 *
 * addAttempt      → fetches the Problem first, links it to the Attempt, then saves
 *                   you must link the attempt to a problem before saving — otherwise
 *                   problem_id in the DB will be null
 *
 * getAttemptsByProblem → uses derived query findByProblemId() in the repository
 *                        DB does the filtering, not Java — efficient
 *
 * updateNotes     → finds the attempt, updates only the notes field, saves back to DB
 *
 * toggleFlaggedForReview → flips the boolean using ! operator
 *                          true → false, false → true
 *
 * orElseThrow     → unwraps Optional. If empty, throws RuntimeException immediately
 */