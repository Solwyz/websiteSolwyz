package com.solwyz.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

import java.io.IOException;
import java.util.Map;

@Service
public class CloudinaryService {

    private final Cloudinary cloudinary;

    public CloudinaryService(
            @Value("${cloudinary.cloud_name}") String cloudName,
            @Value("${cloudinary.api_key}") String apiKey,
            @Value("${cloudinary.api_secret}") String apiSecret) {
        this.cloudinary = new Cloudinary(ObjectUtils.asMap(
                "cloud_name", cloudName,
                "api_key", apiKey,
                "api_secret", apiSecret
        ));
    }

    private String uploadFile(MultipartFile file, String resourceType) throws IOException {
        if (file == null || file.isEmpty()) {
            throw new IOException("No file provided for upload.");
        }

        Map<String, Object> options = ObjectUtils.asMap(
        	    "resource_type", resourceType,   // required: "raw" for PDF
        	    "type", "upload",                // <- this is important!
        	    "use_filename", true,
        	    "unique_filename", false,
        	    "overwrite", true
        	);

        Map<?, ?> uploadResult = cloudinary.uploader().upload(file.getBytes(), options);
        System.out.println("Cloudinary Upload Result: " + uploadResult); // Debug log

        Object secureUrl = uploadResult.get("secure_url");
        if (secureUrl == null || secureUrl.toString().isBlank()) {
            throw new IOException("Upload failed: Cloudinary did not return a valid secure URL.");
        }

        return secureUrl.toString();
    }

    public String uploadImage(MultipartFile file) throws IOException {
        return uploadFile(file, "image");
    }

    public String uploadPdf(MultipartFile file) throws IOException {
        return uploadFile(file, "raw");
    }
}
