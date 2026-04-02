package com.ali.codequeue.service;

import com.ali.codequeue.model.MockInterview;
import com.ali.codequeue.repository.MockInterviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MockInterviewService {
    @Autowired
    private MockInterviewRepository mockInterviewRepository;

    public MockInterview saveMockIntreview(MockInterview mockInterview){
        mockInterview.setDate(java.time.LocalDate.now());
        return mockInterviewRepository.save(mockInterview);
    }
    public List<MockInterview> getAllMockIntreviews(){
        return mockInterviewRepository.findAll();
    }
    public void deleteMockIntreviews(Long id){
        mockInterviewRepository.deleteById(id);
    }



}
