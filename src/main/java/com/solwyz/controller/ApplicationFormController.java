package com.solwyz.controller;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.solwyz.entity.ApplicationForm;
import com.solwyz.service.ApplicationFormService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/application")
public class ApplicationFormController {


	@Autowired
	private ApplicationFormService applicationFormService;
	
	
	@PostMapping(value = "/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<ApplicationForm> createApplication(
	        @RequestParam String name,
	        @RequestParam String email,
	        @RequestParam String phoneNo,
	        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateOfBirth,
	        @RequestParam String highestQualification,
	        @RequestParam Long designationId, 
	        @RequestPart("resume") MultipartFile resumeFile) throws IOException {

	    ApplicationForm savedApplication = applicationFormService.createApplication(
	            name, email, phoneNo, dateOfBirth, highestQualification, designationId, resumeFile);

	    return ResponseEntity.status(HttpStatus.CREATED).body(savedApplication);
	}

    @GetMapping("/all")
    public ResponseEntity<List<ApplicationForm>> getAllApplications() {
        List<ApplicationForm> applications = applicationFormService.getAllApplications();
        return ResponseEntity.ok(applications);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<ApplicationForm> getApplicationById(@PathVariable Long id) {
        ApplicationForm application = applicationFormService.getApplicationById(id);
        return ResponseEntity.ok(application);
    }
    
    //byDesignation-list by designation
    @GetMapping("/all/{designationId}")
    public ResponseEntity<Map<String, Object>> getApplicationsByDesignation(@PathVariable Long designationId) {
        Map<String, Object> response = applicationFormService.getApplicationsByDesignationId(designationId);
        return ResponseEntity.ok(response);
    }


//    @PutMapping(value = "/update/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//    public ResponseEntity<ApplicationForm> updateApplication(
//            @PathVariable Long id,
//            @RequestParam String name,
//            @RequestParam String email,
//            @RequestParam String phoneNo,
//            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateOfBirth,
//            @RequestParam String highestQualification,
//            @RequestPart(value = "resume", required = false) MultipartFile resumeFile) throws IOException {
//
//        ApplicationForm updatedApplication = applicationFormService.updateApplication(
//                id, name, email, phoneNo, dateOfBirth, highestQualification, resumeFile);
//
//        return ResponseEntity.ok(updatedApplication);
//    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteApplication(@PathVariable Long id) {
        applicationFormService.deleteApplication(id);
        return ResponseEntity.noContent().build();
    }


}