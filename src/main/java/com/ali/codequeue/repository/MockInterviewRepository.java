package com.ali.codequeue.repository;

import com.ali.codequeue.model.MockInterview;
import com.ali.codequeue.model.Problem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MockInterviewRepository extends JpaRepository<MockInterview, Long> {
}
