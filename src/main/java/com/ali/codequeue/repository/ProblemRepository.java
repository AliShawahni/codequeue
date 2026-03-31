package com.ali.codequeue.repository;

import com.ali.codequeue.model.Problem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProblemRepository extends JpaRepository<Problem, Long> {

}
/*
 * REPOSITORY LAYER SUMMARY
 *
 * What it is:
 * An interface that extends JpaRepository — Spring Boot implements it automatically.
 * You write no SQL, no implementation. Spring Boot does it all at startup.
 *
 * What it does:
 * It is the only layer that directly touches the database.
 * Everything above it (service, controller) works with Java objects.
 * Everything below it is database rows and SQL.
 * The repository translates between the two.
 *
 * JpaRepository<Problem, Long> means:
 * - Problem → the type of object this repository manages
 * - Long    → the type of the primary key (must match @Id field in the model)
 *
 * Methods you get for free:
 * save(problem)      → insert or update
 * findById(id)       → find one by id
 * findAll()          → get all rows
 * deleteById(id)     → delete one by id
 * count()            → count all rows
 */