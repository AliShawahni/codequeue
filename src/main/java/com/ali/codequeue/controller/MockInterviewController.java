package com.ali.codequeue.controller;

import com.ali.codequeue.model.MockInterview;
import com.ali.codequeue.service.MockInterviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/mock")
public class MockInterviewController {

    @Autowired
    private MockInterviewService mockInterviewService;

    @PostMapping
    public MockInterview saveSession(@RequestBody MockInterview mockInterview){
        return mockInterviewService.saveMockIntreview(mockInterview);
    }
    @GetMapping
    public List<MockInterview> getAllSessions(){
        return mockInterviewService.getAllMockIntreviews();
    }
    @DeleteMapping("/{id}")
    public void deleteSession(@PathVariable Long id){
        mockInterviewService.deleteMockIntreviews(id);
    }

}
