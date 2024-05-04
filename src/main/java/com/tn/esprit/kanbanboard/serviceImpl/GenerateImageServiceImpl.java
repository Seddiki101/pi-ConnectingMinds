package com.tn.esprit.kanbanboard.serviceImpl;

import com.azure.ai.openai.OpenAIClient;
import com.azure.ai.openai.OpenAIClientBuilder;
import com.azure.ai.openai.models.ImageGenerationData;
import com.azure.ai.openai.models.ImageGenerationOptions;
import com.azure.ai.openai.models.ImageGenerations;
import com.azure.core.credential.AzureKeyCredential;
import com.tn.esprit.kanbanboard.service.GenerateImageService;
import org.springframework.stereotype.Service;

@Service
public class GenerateImageServiceImpl implements GenerateImageService {
    @Override
    public String generateImage() {
        String imageUrl = "";
        String azureOpenaiKey = "ae23a08c02ec4abc959bb68f7dab072f";
        String endpoint = "https://chatgpt-assistantbot-pi-cloud.openai.azure.com/";

        OpenAIClient client = new OpenAIClientBuilder()
                .endpoint(endpoint)
                .credential(new AzureKeyCredential(azureOpenaiKey))
                .buildClient();

        String deploymentOrModelName = "Dalle3";
        ImageGenerationOptions imageGenerationOptions = new ImageGenerationOptions(
                "A drawing of the Seattle skyline in the style of Van Gogh");
        ImageGenerations images = client.getImageGenerations(deploymentOrModelName, imageGenerationOptions);

        for (ImageGenerationData imageGenerationData : images.getData()) {
            imageUrl=imageGenerationData.getUrl();
        }
        return !imageUrl.isEmpty() ? imageUrl : null;
    }
}
