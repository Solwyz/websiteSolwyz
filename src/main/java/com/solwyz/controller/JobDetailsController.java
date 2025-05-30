package com.solwyz.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.solwyz.entity.JobDetails;
import com.solwyz.pojo.response.ApiResponse;
import com.solwyz.service.JobDetailsService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/jobs")
public class JobDetailsController {
	
	@Autowired
	private JobDetailsService jobDetailsService;
	
	 @PostMapping("/create")
	    public ResponseEntity<JobDetails> createJobDetails(@RequestBody JobDetails jobDetails) {
	        return ResponseEntity.ok(jobDetailsService.createJobDetails(jobDetails));
	    }
	 
	 @GetMapping("/all")
	 public ResponseEntity<ApiResponse<List<JobDetails>>> getAllJobDetails() {
	     List<JobDetails> jobDetailsList = jobDetailsService.getAllJobDetails();
	     return ResponseEntity.ok(new ApiResponse<>("success", jobDetailsList));
	 }
	 

	    @GetMapping("/{id}")
	    public ResponseEntity<JobDetails> getJobDetailsById(@PathVariable Long id) {
	        return ResponseEntity.ok(jobDetailsService.getJobDetailsById(id));
	    }

	    @PutMapping("/{id}")
	    public ResponseEntity<JobDetails> updateJobDetails(@PathVariable Long id, @RequestBody JobDetails jobDetails) {
	        return ResponseEntity.ok(jobDetailsService.updateJobDetails(id, jobDetails));
	    }

	    @DeleteMapping("/delete/{id}")
	    public ResponseEntity<String> deleteJobDetails(@PathVariable Long id) {
	        jobDetailsService.deleteJobDetails(id);
	        return ResponseEntity.ok("JobDetails deleted successfully");
	    }

}
