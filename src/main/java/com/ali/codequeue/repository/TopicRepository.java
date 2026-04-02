package com.ali.codequeue.repository;

import com.ali.codequeue.model.Topic;
import org.springframework.data.jpa.repository.JpaRepository;
public interface TopicRepository extends JpaRepository<Topic, String>{
}
