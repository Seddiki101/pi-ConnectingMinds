package com.tn.esprit.kanbanboard.serviceImpl;

import com.azure.ai.openai.OpenAIClient;
import com.azure.ai.openai.OpenAIClientBuilder;
import com.azure.ai.openai.models.ImageGenerationData;
import com.azure.ai.openai.models.ImageGenerationOptions;
import com.azure.ai.openai.models.ImageGenerationQuality;
import com.azure.ai.openai.models.ImageGenerations;
import com.azure.core.credential.AzureKeyCredential;
import com.tn.esprit.kanbanboard.service.ImageGenerationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

@Service
public class ImageGenerationServiceImpl implements ImageGenerationService {
    @Autowired
    private Environment env;
    @Override
    public String generateImage(String prompt) {
            String endpoint = env.getProperty("my-endpoint");
            String azureOpenaiKey = env.getProperty("my-key");
            String imageGeneratedUrl="";

            ImageGenerationQuality quality = ImageGenerationQuality.STANDARD;
            OpenAIClient client = new OpenAIClientBuilder()
                    .endpoint(endpoint)
                    .credential(new AzureKeyCredential(azureOpenaiKey))
                    .buildClient();

            String deploymentOrModelName = "Dalle3";
            ImageGenerationOptions imageGenerationOptions = new ImageGenerationOptions(
                    prompt).setQuality(quality);
            ImageGenerations images = client.getImageGenerations(deploymentOrModelName, imageGenerationOptions);

            for (ImageGenerationData imageGenerationData : images.getData()) {
              imageGeneratedUrl=imageGenerationData.getUrl();
            }
        return imageGeneratedUrl;
    }
}
