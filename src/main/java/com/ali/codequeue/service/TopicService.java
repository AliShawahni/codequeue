package com.ali.codequeue.service;

import com.ali.codequeue.model.Topic;
import com.ali.codequeue.repository.TopicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TopicService {
    @Autowired
    private TopicRepository topicRepository;

    public String getNotes(String topic) {
        return topicRepository.findById(topic).map(Topic::getNotes).orElse("");
    }

    public Topic updateNotes(String topic, String notes) {
        Topic t = topicRepository.findById(topic)
                .orElseThrow(() -> new RuntimeException("Topic not found: " + topic));
        t.setNotes(notes);
        return topicRepository.save(t);
    }


}
