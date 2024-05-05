package com.tn.esprit.kanbanboard.controller;

import com.tn.esprit.kanbanboard.entity.Conversation;
import com.tn.esprit.kanbanboard.entity.Message;
import com.tn.esprit.kanbanboard.entity.UserMessage;
import com.tn.esprit.kanbanboard.service.ConversationService;
import com.tn.esprit.kanbanboard.service.ImageGenerationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.ai.azure.openai.AzureOpenAiChatClient;

import java.util.*;

@RestController
@RequestMapping("/api")
public class AiAssistantBotController {
    private final ConversationService conversationService;
    private final AzureOpenAiChatClient chatClient;
    private final ImageGenerationService imageGenerationService;
    @Autowired
    public AiAssistantBotController(ConversationService conversationService, AzureOpenAiChatClient chatClient,ImageGenerationService imageGenerationService) {
        this.conversationService = conversationService;
        this.chatClient = chatClient;
        this.imageGenerationService = imageGenerationService;
    }
    @GetMapping("/conversation")
    public ResponseEntity<List<Conversation>> getAllConversations() {
        List<Conversation> list= conversationService.findAll();
        return ResponseEntity.ok(list);
    }
    @GetMapping("/conversation/user/{id}")
    public ResponseEntity<List<Conversation>> getAllConversationsByUserId(@PathVariable("id") Long userId) {
        List<Conversation> list= conversationService.findAllByUserId(userId);
        return ResponseEntity.ok(list);
    }
    @GetMapping("/conversation/{id}")
    public ResponseEntity<Conversation> getByConversationId(@PathVariable("id") Long id) {
        Optional<Conversation> optionalConversation= conversationService.findById(id);
        return optionalConversation.map(ResponseEntity::ok).orElse(ResponseEntity.noContent().build());
    }
    @GetMapping("/ai/generate/image")
    public ResponseEntity<String> generateImage(@RequestBody UserMessage userMessage) throws InterruptedException {
        String url = imageGenerationService.generateImage(userMessage.getMessage());
        return ResponseEntity.ok(url);
    }
    @PostMapping("/conversation")
    public ResponseEntity<Conversation> Conversation(
            @RequestParam(required = false) Long conversationId // Optional parameter for existing conversation ID
           , @RequestParam(required = false) Long userId, @RequestBody UserMessage userMessage
            ) {
        Conversation conversation;
        String message = userMessage.getMessage();
        if (conversationId != null) {
            // Fetch the existing conversation
            Optional<Conversation> optionalConversation = conversationService.findById(conversationId);
            if (optionalConversation.isPresent()) {
                conversation = optionalConversation.get();
            } else {
                return ResponseEntity.notFound().build(); // Conversation not found
            }
        } else {
            // Create a new conversation
            conversation = new Conversation();
            conversation.setUserId(userId);
        }
        conversation.setTimestamp(new Date());
        // Add system instruction message to the beginning of conversation
        if (conversation.getMessages().isEmpty()) {
            Message systemMessage = new Message();
            systemMessage.setRole("system");
            systemMessage.setContent("You are a helpful assistant, you give accurate answers in Markdown text format. Based on the chat history, please answer the final user’s content.");
            conversation.getMessages().add(systemMessage);
        }

        // Add the user's message to the conversation
        Message messageOfUser = new Message();
        messageOfUser.setRole("user");
        messageOfUser.setContent(message);
        conversation.getMessages().add(messageOfUser);

        // Call the Azure Open AI model with entire conversation history
        String modelResponse = callAzureOpenAI(conversation.getMessages());

        // Add the model's response to the conversation
        Message assistantMessage = new Message();
        assistantMessage.setRole("assistant");
        assistantMessage.setContent(modelResponse);
        conversation.getMessages().add(assistantMessage);

        // Save or update the conversation
        Conversation savedConversation = conversationService.update(conversation);

        return ResponseEntity.ok(savedConversation);
    }

    @DeleteMapping("/conversation/{id}")
    public ResponseEntity<String> deleteConversationById(@PathVariable("id") Long id) {
        conversationService.delete(id);
        return ResponseEntity.ok("deleted.");
    }
    private String prepareConversationHistory(List<Message> conversationHistory) {
        StringBuilder conversationBuilder = new StringBuilder();
        for (Message message : conversationHistory) {
            // Append role and content with appropriate separators
            conversationBuilder.append(message.getRole()).append(": ").append(message.getContent()).append("\n");
        }
        return conversationBuilder.toString();
    }
    private String callAzureOpenAI(List<Message> conversationHistory) {
        // Prepare conversation history as a single string
        String conversationText = prepareConversationHistory(conversationHistory);

        // Call the Azure Open AI model
        return chatClient.call(conversationText);
    }

}
