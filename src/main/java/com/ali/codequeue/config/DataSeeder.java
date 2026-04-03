package com.ali.codequeue.config;

import com.ali.codequeue.model.Difficulty;
import com.ali.codequeue.model.Problem;
import com.ali.codequeue.model.Topic;
import com.ali.codequeue.repository.ProblemRepository;
import com.ali.codequeue.repository.TopicRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.io.InputStream;

import java.io.File;

@Component
public class DataSeeder implements CommandLineRunner {

    @Autowired
    private ProblemRepository problemRepository;

    @Autowired
    private TopicRepository topicRepository;

    @Override
    public void run(String... args) throws Exception {

        if (problemRepository.count() == 0) {
            System.out.println("Seeding database from problems.json...");

            ObjectMapper mapper = new ObjectMapper();
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream("problems.json");
            JsonNode root = mapper.readTree(inputStream);

            JsonNode problems;
            if (root.isArray()) {
                problems = root;
            } else {
                problems = root.elements().next();
            }

            System.out.println("Total problems found: " + problems.size());

            int count = 0;
            for (JsonNode q : problems) {
                try {
                    Problem problem = new Problem();
                    problem.setLeetcodeId(q.path("frontend_id").asInt());
                    problem.setTitle(q.path("title").asText());

                    String diff = q.path("difficulty").asText().toUpperCase();
                    problem.setDifficulty(Difficulty.valueOf(diff));

                    JsonNode topics = q.path("topics");
                    if (topics.isArray() && topics.size() > 0) {
                        problem.setTopic(topics.get(0).asText());
                    } else {
                        problem.setTopic("General");
                    }

                    String slug = q.path("problem_slug").asText();
                    problem.setUrl("https://leetcode.com/problems/" + slug);

                    problemRepository.save(problem);
                    count++;

                } catch (Exception e) {
                    // skip problems with invalid data
                }
            }
            System.out.println("Seeded " + count + " problems successfully.");
        } else {
            System.out.println("Database already seeded. Skipping.");
        }

        if (topicRepository.count() == 0) {
            problemRepository.findAll().stream()
                    .map(Problem::getTopic)
                    .distinct()
                    .forEach(name -> topicRepository.save(new Topic(name)));
            System.out.println("Topics seeded successfully.");
        }
    }
}