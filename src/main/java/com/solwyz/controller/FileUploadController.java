//package com.solwyz.controller;
//
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.multipart.MultipartFile;
//
//import com.solwyz.service.AwsS3Service;
//
//@RestController
//@RequestMapping("/api/files")
//public class FileUploadController {
//
//    private final AwsS3Service awsS3Service;
//
//    public FileUploadController(AwsS3Service awsS3Service) {
//        this.awsS3Service = awsS3Service;
//    }
//
//    @PostMapping("/upload")
//    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
//        try {
//            String fileUrl = awsS3Service.uploadFile(file);
//            return ResponseEntity.ok(fileUrl);
//        } catch (Exception e) {
//            return ResponseEntity.badRequest().body("Upload failed: " + e.getMessage());
//        }
//    }
//}
