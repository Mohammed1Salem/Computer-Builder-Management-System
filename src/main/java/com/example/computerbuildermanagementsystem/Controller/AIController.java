package com.example.computerbuildermanagementsystem.Controller;

import com.example.computerbuildermanagementsystem.Api.ApiResponse;
import com.example.computerbuildermanagementsystem.Service.AIService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/ai")
@RequiredArgsConstructor
public class AIController {

    private final AIService aiService;

    @GetMapping(value = "/recommend-pc/{balance}/{usage}", produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> recommendPC(@PathVariable Double balance, @PathVariable String usage) {
        String recommendation = aiService.recommendPC(balance, usage);
        return ResponseEntity.status(200).body(recommendation);
    }

    @GetMapping(value = "/general-help/{question}", produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> generalHelp(@PathVariable String question) {
        String answer = aiService.generalHelp(question);
        return ResponseEntity.status(200).body(answer);
    }

}