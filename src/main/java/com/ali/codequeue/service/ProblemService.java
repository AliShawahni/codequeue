package com.ali.codequeue.service;

import com.ali.codequeue.model.Problem;
import com.ali.codequeue.repository.ProblemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ProblemService {

    @Autowired
    private ProblemRepository problemRepository;

    public Problem addProblem(Problem problem){
        return problemRepository.save(problem);
    }
    public List<Problem> getAllProblems(){
        return problemRepository.findAll();
    }
    public Optional<Problem> getProblemById(Long id){
        return problemRepository.findById(id);
    }
    public void deleteProblem(Long id){
        problemRepository.deleteById(id);
    }
    public Problem updateProblem(Long id,Problem updated){
        Problem prev = problemRepository.findById(id).orElseThrow(() -> new RuntimeException("Problem not found"));
        prev.setTitle(updated.getTitle());
        prev.setDifficulty(updated.getDifficulty());
        prev.setTopic(updated.getTopic());
        prev.setNotes(updated.getNotes());
        return problemRepository.save(prev); //this line is responsible for updating the data in the database

    }






}

/*
 * CONCEPTS USED:
 *
 * @Service      → tells Spring Boot this class contains business logic.
 *                 Spring creates one instance at startup and keeps it ready.
 * @Autowired    → dependency injection. Spring automatically injects the
 *                 ProblemRepository into this class. You never write new ProblemRepository().
 *
 *
 * @Autowired
 private ProblemRepository problemRepository:
*
    1. Hibernate generates a real class that implements ProblemRepository
    2. Spring Boot creates one instance of that class at startup
    3. Spring Boot sees @Autowired on this field
    4. Spring Boot injects that instance into problemRepository
    5. Now ProblemService can call problemRepository.findAll() etc.
 */