package com.craft.craft.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
public class CloudinaryService {

    private static final Logger logger = LoggerFactory.getLogger(CloudinaryService.class);

    private final Cloudinary cloudinary;

    @Autowired
    public CloudinaryService(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }

    public Map<String, String> uploadFile(MultipartFile file) throws IOException {
        try {
            if (file == null || file.isEmpty()) {
                throw new IllegalArgumentException("File cannot be null or empty");
            }

            Map<String, Object> options = new HashMap<>();
            options.put("resource_type", "auto"); // Let Cloudinary detect the type

            logger.info("Uploading file: {} ({} bytes)", file.getOriginalFilename(), file.getSize());

            Map<?, ?> uploadResult = cloudinary.uploader().upload(file.getBytes(), options);

            if (uploadResult == null) {
                throw new IOException("Cloudinary returned null response");
            }

            logger.debug("Cloudinary response: {}", uploadResult);

            Map<String, String> result = new HashMap<>();

            // Safely handle all possible null values
            result.put("url", getStringOrEmpty(uploadResult, "secure_url"));
            result.put("public_id", getStringOrEmpty(uploadResult, "public_id"));
            result.put("resource_type", getStringOrEmpty(uploadResult, "resource_type"));

            // Only add video-specific fields if this is a video
            if ("video".equals(uploadResult.get("resource_type"))) {
                result.put("duration", getStringOrEmpty(uploadResult, "duration"));
                result.put("thumbnail_url", getStringOrEmpty(uploadResult, "thumbnail_url"));
            }

            logger.info("Successfully uploaded file. Public ID: {}", result.get("public_id"));

            return result;

        } catch (IOException e) {
            logger.error("Failed to upload file to Cloudinary", e);
            throw new IOException("Cloudinary upload failed: " + e.getMessage(), e);
        }
    }

    private String getStringOrEmpty(Map<?, ?> map, String key) {
        Object value = map.get(key);
        return value != null ? value.toString() : "";
    }

    public void deleteFile(String publicId, String resourceType) throws IOException {
        try {
            if (publicId == null || publicId.isBlank()) {
                throw new IllegalArgumentException("Public ID cannot be null or empty");
            }

            Map<String, String> options = new HashMap<>();
            options.put("resource_type", resourceType.toLowerCase());

            logger.info("Deleting file with public ID: {}", publicId);

            Map<?, ?> result = cloudinary.uploader().destroy(publicId, options);

            if (result == null || !"ok".equals(result.get("result"))) {
                String error = result != null ? result.get("result").toString() : "null response";
                throw new IOException("Failed to delete file: " + error);
            }

            logger.info("Successfully deleted file with public ID: {}", publicId);

        } catch (Exception e) {
            logger.error("Failed to delete file from Cloudinary", e);
            throw new IOException("Cloudinary deletion failed: " + e.getMessage(), e);
        }
    }
}