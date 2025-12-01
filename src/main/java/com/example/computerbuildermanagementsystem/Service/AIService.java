package com.example.computerbuildermanagementsystem.Service;

import com.example.computerbuildermanagementsystem.Repository.ComponentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AIService {
    private final OpenAiChatModel openAiChatModel;

    public String recommendPC(Double balance, String usage) {
        String promptText = "You are a PC hardware expert. " +
                "The customer has " + balance + " SAR and wants a PC for: " + usage + ". " +
                "Recommend ONLY the following components:\n" +
                "- CPU\n" +
                "- GPU\n" +
                "- RAM\n" +
                "- SSD\n" +
                "NO OTHER PARTS ALLOWED.\n" +
                "Stay inside the user's budget. Provide:\n" +
                "1. Component name\n" +
                "2. Approx price in SAR\n" +
                "3. Short reason.\n" +
                "Format clearly.";

        OpenAiChatOptions options = OpenAiChatOptions.builder()
                .build();

        Prompt prompt = new Prompt(promptText, options);
        ChatResponse response = openAiChatModel.call(prompt);

        return response.getResult().getOutput().getText();
    }

    public String generalHelp(String question) {
        String promptText = "You are a friendly PC troubleshooting expert.\n" +
                "The user asks: \"" + question + "\"\n\n" +
                "Rules:\n" +
                "- Give a clear and simple explanation.\n" +
                "- Provide steps the user can try.\n" +
                "- If the issue sounds hardware-critical, risky, or requires physical inspection, say: " +
                "\"This issue may require deeper inspection. Please book an appointment with a PCSpecialist.\"\n" +
                "- Keep the answer short and helpful.";

        OpenAiChatOptions options = OpenAiChatOptions.builder()
                .build();

        Prompt prompt = new Prompt(promptText, options);
        ChatResponse response = openAiChatModel.call(prompt);

        return response.getResult().getOutput().getText();
    }
}