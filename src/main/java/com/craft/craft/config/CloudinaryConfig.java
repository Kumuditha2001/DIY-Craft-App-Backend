package com.craft.craft.config;

import com.cloudinary.Cloudinary;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class CloudinaryConfig {

    @Value("${cloudinary.cloud-name}")
    private String cloudName;

    @Value("${cloudinary.api-key}")
    private String apiKey;

    @Value("${cloudinary.api-secret}")
    private String apiSecret;

    @PostConstruct
    public void validateConfig() {
        if (cloudName == null || cloudName.isBlank()) {
            throw new IllegalStateException("Cloudinary cloud name is not configured");
        }
        if (apiKey == null || apiKey.isBlank()) {
            throw new IllegalStateException("Cloudinary API key is not configured");
        }
        if (apiSecret == null || apiSecret.isBlank()) {
            throw new IllegalStateException("Cloudinary API secret is not configured");
        }

        // Remove any surrounding quotes if present
        cloudName = cloudName.replace("\"", "").trim();
        apiKey = apiKey.replace("\"", "").trim();
        apiSecret = apiSecret.replace("\"", "").trim();
    }

    @Bean
    public Cloudinary cloudinary() {
        Map<String, String> config = new HashMap<>();
        config.put("cloud_name", cloudName);
        config.put("api_key", apiKey);
        config.put("api_secret", apiSecret);
        return new Cloudinary(config);
    }
}