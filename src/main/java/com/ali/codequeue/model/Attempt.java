package com.ali.codequeue.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class Attempt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "problem_id")
    private Problem problem;

    private LocalDate date;
    private Result result;        // FAIL, PASS, EASY
    private Integer confidence;   // 1 to 5
    private String notes;
    private Boolean flaggedForReview;
    private Integer timeSpentMinutes;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Problem getProblem() { return problem; }
    public void setProblem(Problem problem) { this.problem = problem; }

    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }

    public Result getResult() { return result; }
    public void setResult(Result result) { this.result = result; }

    public Integer getConfidence() { return confidence; }
    public void setConfidence(Integer confidence) { this.confidence = confidence; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }

    public Boolean getFlaggedForReview() { return flaggedForReview; }
    public void setFlaggedForReview(Boolean flaggedForReview) { this.flaggedForReview = flaggedForReview; }

    public Integer getTimeSpentMinutes() { return timeSpentMinutes; }
    public void setTimeSpentMinutes(Integer timeSpentMinutes) { this.timeSpentMinutes = timeSpentMinutes; }
}

/*
 * CONCEPTS USED:
 *
 * @Entity          → tells JPA to create a database table for this class
 * @Id              → marks the primary key
 * @GeneratedValue  → auto-generates the id (1, 2, 3...)
 * @ManyToOne       → many attempts can belong to one problem
 * @JoinColumn      → the column in the DB that stores the reference (problem_id)
 * LocalDate        → Java type for a date without time (year, month, day)
 * Boolean          → can be true, false, or null (unlike primitive boolean)
 * Getters/Setters  → controlled access to private fields
 */