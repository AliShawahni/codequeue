package com.ali.codequeue.controller;

import com.ali.codequeue.model.Attempt;
import com.ali.codequeue.service.AttemptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/attempts")
public class AttemptController {

    @Autowired
    private AttemptService attemptService;

    @GetMapping("/{problemId}")
    public List<Attempt> getAllAttempts(@PathVariable Long problemId) {
        return attemptService.getAttemptsByProblem(problemId);
    }

    @PostMapping("/{problemId}")
    public Attempt addAttempt(@PathVariable Long problemId, @RequestBody Attempt attempt) {
        return attemptService.addAttempt(problemId, attempt);
    }

    @DeleteMapping("/{id}")
    public void deleteAttempt(@PathVariable Long id) {
        attemptService.deleteAttempt(id);
    }

    @PutMapping("/{id}/notes")
    public Attempt updateNotes(@PathVariable Long id, @RequestBody String notes) {
        return attemptService.updateNotes(id, notes);
    }

    @PutMapping("/{id}/flag")
    public Attempt toggleFlag(@PathVariable Long id) {
        return attemptService.toggleFlaggedForReview(id);
    }
    @PutMapping("/{id}")
    public Attempt updateAttempt(@PathVariable Long id, @RequestBody Attempt attempt) {
        return attemptService.updateAttempt(id, attempt);
    }
}

/*
 * CONCEPTS USED:
 *
 * @RestController  → handles HTTP requests, returns JSON automatically
 * @RequestMapping  → base URL for all endpoints (/attempts)
 * @GetMapping      → GET /attempts/{problemId} → get all attempts for a problem
 * @PostMapping     → POST /attempts/{problemId} → log a new attempt
 * @DeleteMapping   → DELETE /attempts/{id} → delete an attempt
 * @PutMapping      → PUT /attempts/{id}/notes → update notes
 *                    PUT /attempts/{id}/flag → toggle flagged for review
 * @PathVariable    → extracts value from URL
 * @RequestBody     → converts incoming JSON → Java object
 */