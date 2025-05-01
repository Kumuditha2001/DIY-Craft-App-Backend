package com.craft.craft.Learning_Plan_Sharing.service;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.craft.craft.Learning_Plan_Sharing.model.LearningPlan;
import com.craft.craft.Learning_Plan_Sharing.repository.LearningRepository;

@Service
public class LearningPlanService {

    @Autowired
    private Cloudinary cloudinary;

    @Autowired
    private LearningRepository learninRepo;

    public LearningPlan createLearningPlan(String userId, String profilPic, String headline,
    String fullname, String title, String description, String timeline, MultipartFile thumbnail, MultipartFile video)
            throws IOException {

        String resourceUrl = null;
        String thumbnailUrl = null;

        // Handle video upload to Cloudinary
        if (video != null && !video.isEmpty()) {
            String uniqueFileName = UUID.randomUUID() + "_" + video.getOriginalFilename();

            Map uploadResult = cloudinary.uploader().upload(video.getBytes(),
                    ObjectUtils.asMap(
                            "resource_type", "video",
                            "public_id", "learning_videos/" + uniqueFileName));

            resourceUrl = uploadResult.get("secure_url").toString();
        }

        if (thumbnail != null && !thumbnail.isEmpty()) {
            String uniqueFileName = UUID.randomUUID() + "_" + thumbnail.getOriginalFilename();
    
            Map uploadResult = cloudinary.uploader().upload(thumbnail.getBytes(),
                    ObjectUtils.asMap(
                            "resource_type", "image",
                            "public_id", "learning_thumbnails/" + uniqueFileName));
    
            thumbnailUrl = uploadResult.get("secure_url").toString();
        }

        LearningPlan plan = new LearningPlan();
        plan.setUserId(userId);
        plan.setProfilPic(profilPic);
        plan.setHeadline(headline);
        plan.setFullname(fullname);
        plan.setTitle(title);
        plan.setDescription(description);
        plan.setTimeLine(timeline);
        plan.setThumbnail(thumbnailUrl);
        plan.setResources(resourceUrl);
        plan.setCreatedAt(new Date());


        return learninRepo.save(plan);
    }

    // Get All
    public List<LearningPlan> getAllPlans() {
        return learninRepo.findAll();
    }

    // Update by ID
    public LearningPlan updateLearningPlan(String id,
            String title, String description, String timeline, MultipartFile thumbnail, MultipartFile video)
            throws Exception {
        try {
            ObjectId objectId = new ObjectId(id);
            Optional<LearningPlan> optionalPlan = learninRepo.findById(objectId);

            if (optionalPlan.isPresent()) {
                LearningPlan plan = optionalPlan.get();

                plan.setTitle(title);
                plan.setDescription(description);
                plan.setTimeLine(timeline);

                if (video != null && !video.isEmpty()) {
                    String uniqueFileName = UUID.randomUUID() + "_" + video.getOriginalFilename();
                    Map uploadResult = cloudinary.uploader().upload(video.getBytes(),
                            ObjectUtils.asMap(
                                    "resource_type", "video",
                                    "public_id", "learning_videos/" + uniqueFileName));

                    String resourceUrl = uploadResult.get("secure_url").toString();
                    plan.setResources(resourceUrl);
                }

                if (thumbnail != null && !thumbnail.isEmpty()) {
                    String uniqueFileName = UUID.randomUUID() + "_" + thumbnail.getOriginalFilename();
                    Map uploadResult = cloudinary.uploader().upload(thumbnail.getBytes(),
                            ObjectUtils.asMap(
                                    "resource_type", "image",
                                    "public_id", "learning_thumbnails/" + uniqueFileName));
    
                    String thumbnailUrl = uploadResult.get("secure_url").toString();
                    plan.setThumbnail(thumbnailUrl);
                }

                return learninRepo.save(plan);
            } else {
                throw new NoSuchElementException("Learning plan not found.");
            }
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid ObjectId format.");
        }
    }

    // Delete by ID and userId
    public boolean deletePlanById(String id) {
        try {
            ObjectId objectId = new ObjectId(id);

            if (learninRepo.existsById(objectId)) {
                learninRepo.deleteById(objectId);
                return true;
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Something Went Wrong..!");
        }

        return false;
    }
}
