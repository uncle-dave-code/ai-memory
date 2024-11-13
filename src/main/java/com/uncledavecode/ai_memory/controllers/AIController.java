package com.uncledavecode.ai_memory.controllers;

import com.uncledavecode.ai_memory.model.dtos.ChatResponse;
import com.uncledavecode.ai_memory.model.dtos.RequestParams;
import com.uncledavecode.ai_memory.services.AIServices;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ai")
@RequiredArgsConstructor
public class AIController {

    private final AIServices aiService;

    @PostMapping("/joke")
    private ResponseEntity<ChatResponse> getJoke(
            @RequestBody RequestParams requestParams) {
        var result = aiService.getJoke(requestParams.message());

        return ResponseEntity.ok(result);
    }
}