package com.ali.codequeue.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Problem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private Difficulty difficulty;  // EASY, MEDIUM, HARD
    private String topic;       // Trees, Graphs, Arrays...
    private Integer leetcodeId; // the number on LeetCode (e.g. 1 for Two Sum)
    private String url;
    private String notes;

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public Difficulty getDifficulty() { return difficulty; }
    public void setDifficulty(Difficulty difficulty) { this.difficulty = difficulty; }

    public String getTopic() { return topic; }
    public void setTopic(String topic) { this.topic = topic; }

    public Integer getLeetcodeId() { return leetcodeId; }
    public void setLeetcodeId(Integer leetcodeId) { this.leetcodeId = leetcodeId; }

    public String getUrl() { return url; }
    public void setUrl(String url) { this.url = url; }
}

/*
 * CONCEPTS USED:
 *
 * package         → declares which folder this class belongs to. Keeps code organized.
 * import          → tells Java where to find things not built into the basic language (here: JPA annotations).
 * @Entity         → tells JPA to create a database table for this class. Every field becomes a column.
 * public class    → the blueprint for what a Problem looks like. Every problem in the DB is an object of this class.
 * @Id             → marks the primary key — the unique identifier for each row in the table.
 * @GeneratedValue → tells the database to auto-generate the ID (1, 2, 3...). You never set it manually.
 * Long id         → the primary key field. Long is the standard type for DB IDs in Java.
 * String fields   → the actual data (title, difficulty, topic, url). Each becomes a column in the table.
 * Integer         → used instead of int because database fields can be null.
 * Getters/Setters → the only way to read or change a private field from outside the class.
 */