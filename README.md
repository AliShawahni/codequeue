# CodeQueue

A full-stack LeetCode practice tracker with a dual-mode recommendation engine.

## What it does

CodeQueue helps you practice LeetCode problems smarter — not just randomly. It tracks your attempt history and uses a scoring algorithm to recommend what to practice next.

**Two modes:**
- **Review Mode** — ranks problems you've already attempted by urgency, based on recency, result, and confidence
- **Discover Mode** — surfaces unsolved problems from your weakest topics at the right difficulty level for your current skill

## How the algorithm works

### Review Mode
Each attempted problem gets a priority score:
```
score = (0.20 × recencyScore) + (0.35 × resultScore) + (0.45 × confidenceScore)

recencyScore    = min(daysSinceLastAttempt / 30, 1.0)
resultScore     = FAIL → 1.0 | PASS → 0.5 | EASY → 0.2
confidenceScore = (6 - confidence) / 5.0
```

### Discover Mode
Each unsolved problem gets a topic boost score:
```
topicWeakness = (0.6 × confidenceWeakness) + (0.4 × failRate)
score = topicWeakness × difficultyMultiplier
```

Difficulty multiplier adapts to your level in that topic:
- Very weak (>0.7) → prioritizes Easy problems
- Moderate (0.4–0.7) → prioritizes Medium problems  
- Strong (≤0.4) → prioritizes Medium/Hard problems

## Tech Stack

| Layer | Technology |
|---|---|
| Backend | Java 17+, Spring Boot 4, Maven |
| Database | H2 (file-based), JPA/Hibernate |
| Frontend | React |
| Data | 2913 LeetCode problems seeded on startup |

## Project Structure
```
src/main/java/com/ali/codequeue/
├── model/          # Problem, Attempt, enums
├── repository/     # JPA repositories
├── service/        # Business logic + recommendation engine
├── controller/     # REST API endpoints
└── config/         # DataSeeder, CORS
```

## API Endpoints

| Method | Endpoint | Description |
|---|---|---|
| GET | /problems | Get all problems |
| POST | /problems | Add a problem |
| PUT | /problems/{id} | Update a problem |
| DELETE | /problems/{id} | Delete a problem |
| POST | /attempts/{problemId} | Log an attempt |
| GET | /attempts/{problemId} | Get attempts for a problem |
| PUT | /attempts/{id}/notes | Update notes |
| PUT | /attempts/{id}/flag | Toggle flagged for review |
| GET | /recommendations/review | Get review queue |
| GET | /recommendations/discover | Get discover queue |

## Running locally
```bash
# Clone and run backend
git clone https://github.com/AliShawahni/codequeue.git
cd codequeue
./mvnw spring-boot:run

# Backend runs on localhost:8080
# H2 console at localhost:8080/h2-console
```

## Frontend

The React frontend is available at [codequeue-frontend](https://github.com/AliShawahni/codequeue-frontend)
