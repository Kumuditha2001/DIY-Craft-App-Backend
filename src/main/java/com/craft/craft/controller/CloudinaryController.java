package com.craft.craft.controller;

import com.craft.craft.service.CloudinaryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/media")
public class CloudinaryController {

    private static final Logger logger = LoggerFactory.getLogger(CloudinaryController.class);

    private final CloudinaryService cloudinaryService;

    @Autowired
    public CloudinaryController(CloudinaryService cloudinaryService) {
        this.cloudinaryService = cloudinaryService;
    }

    @PostMapping("/upload")
    public ResponseEntity<?> uploadMediaFile(@RequestParam("file") MultipartFile file) {
        try {
            if (file.isEmpty()) {
                return ResponseEntity.badRequest().body(createErrorResponse("File cannot be empty"));
            }

            Map<String, String> result = cloudinaryService.uploadFile(file);

            if (result.get("url").isEmpty()) {
                logger.warn("Upload succeeded but URL is empty. Full response: {}", result);
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(createErrorResponse("Upload succeeded but no URL returned"));
            }

            return ResponseEntity.ok(result);

        } catch (Exception e) {
            logger.error("File upload failed", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(createErrorResponse("Upload failed: " + e.getMessage()));
        }
    }

    @GetMapping("/fetch")
    public ResponseEntity<?> getAllMedia(
            @RequestParam(required = false, defaultValue = "image") String resourceType,
            @RequestParam(required = false, defaultValue = "50") int maxResults) {
        try {
            // Validate resource type
            if (!Arrays.asList("image", "video", "raw").contains(resourceType)) {
                return ResponseEntity.badRequest().body(createErrorResponse("Invalid resource type. Allowed values: image, video, raw"));
            }
            // Validate max results
            if (maxResults < 1 || maxResults > 500) {
                return ResponseEntity.badRequest().body(createErrorResponse("maxResults must be between 1 and 500"));
            }

            Map<?, ?> result = cloudinaryService.listAllMedia(resourceType, maxResults);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            logger.error("Failed to fetch media list", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(createErrorResponse("Failed to fetch media: " + e.getMessage()));
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteMediaFile(
            @RequestParam String publicId,
            @RequestParam String resourceType) {
        try {
            if (publicId == null || publicId.isBlank()) {
                return ResponseEntity.badRequest().body(createErrorResponse("Public ID cannot be empty"));
            }

            cloudinaryService.deleteFile(publicId, resourceType);
            return ResponseEntity.ok().build();

        } catch (Exception e) {
            logger.error("File deletion failed", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(createErrorResponse("Deletion failed: " + e.getMessage()));
        }
    }

    @GetMapping("/{publicId}")
    public ResponseEntity<?> getMedia(
            @PathVariable String publicId,
            @RequestParam String resourceType) {
        try {
            if (publicId == null || publicId.trim().isEmpty()) {
                return ResponseEntity.badRequest().body(createErrorResponse("Public ID cannot be empty"));
            }
            if (!Arrays.asList("image", "video", "raw").contains(resourceType)) {
                return ResponseEntity.badRequest().body(createErrorResponse("Invalid resource type. Allowed values: image, video, raw"));
            }

            Map<?, ?> result = cloudinaryService.getMediaByPublicId(publicId, resourceType);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            logger.error("Failed to fetch media: {}", publicId, e);
            if (e.getMessage() != null && e.getMessage().contains("not found")) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(createErrorResponse("Media not found"));
            }
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(createErrorResponse("Failed to fetch media: " + e.getMessage()));
        }
    }

    private Map<String, String> createErrorResponse(String message) {
        Map<String, String> response = new HashMap<>();
        response.put("error", message);
        return response;
    }
}