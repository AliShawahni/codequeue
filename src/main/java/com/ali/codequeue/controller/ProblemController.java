package com.ali.codequeue.controller;

import com.ali.codequeue.model.Problem;
import com.ali.codequeue.service.ProblemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/problems")
public class ProblemController {

    @Autowired
    private ProblemService problemService;

    @GetMapping
    public List<Problem> getAllProblems() {
        return problemService.getAllProblems();
    }

    @GetMapping("/{id}")
    public Optional<Problem> getProblemById(@PathVariable Long id) {
        return problemService.getProblemById(id);
    }

    @PostMapping
    public Problem addProblem(@RequestBody Problem problem) {
        return problemService.addProblem(problem);
    }

    @PutMapping("/{id}")
    public Problem updateProblem(@PathVariable Long id, @RequestBody Problem problem) {
        return problemService.updateProblem(id, problem);
    }

    @DeleteMapping("/{id}")
    public void deleteProblem(@PathVariable Long id) {
        problemService.deleteProblem(id);
    }
}

/*
 * CONCEPTS USED:
 *
 * @RestController        → handles HTTP requests, returns JSON automatically
 * @RequestMapping        → base URL for all endpoints in this class (/problems)
 * @GetMapping            → handles GET requests (read data)
 * @PostMapping           → handles POST requests (create data)
 * @PutMapping            → handles PUT requests (update data)
 * @DeleteMapping         → handles DELETE requests (delete data)
 * @PathVariable          → extracts value from URL e.g. /problems/42 → id = 42
 * @RequestBody           → converts incoming JSON → Java object automatically
 */