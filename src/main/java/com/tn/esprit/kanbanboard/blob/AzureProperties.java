package com.tn.esprit.kanbanboard.blob;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties("azure.myblob")
public class AzureProperties {
    private String connectionstring;
    private String container;
}
