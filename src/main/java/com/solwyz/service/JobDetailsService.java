package com.solwyz.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.solwyz.entity.JobDetails;
import com.solwyz.repo.JobDetailsRepository;

@Service
public class JobDetailsService {
	
	@Autowired
	private JobDetailsRepository jobDetailsRepository;
	

	 public JobDetails createJobDetails(JobDetails jobDetails) {
	        return jobDetailsRepository.save(jobDetails);
	    }
	 
	  
	    public List<JobDetails> getAllJobDetails() {
	        return jobDetailsRepository.findAll();
	    }

	   
	    public JobDetails getJobDetailsById(Long id) {
	        return jobDetailsRepository.findById(id)
	                .orElseThrow(() -> new RuntimeException("JobDetails not found with id " + id));
	    }

	    
	    public JobDetails updateJobDetails(Long id, JobDetails jobDetails) {
	        JobDetails existing = getJobDetailsById(id);
	        existing.setDesignation(jobDetails.getDesignation());
	        existing.setLoaction(jobDetails.getLoaction());
	        existing.setExperience(jobDetails.getExperience());
	        existing.setJobType(jobDetails.getJobType());
	        existing.setQualification(jobDetails.getQualification());
	        existing.setEmailId(jobDetails.getEmailId());
	        existing.setResponsibilities(jobDetails.getResponsibilities());
	        existing.setRequirements(jobDetails.getRequirements());
	        return jobDetailsRepository.save(existing);
	    }

	    
	    public void deleteJobDetails(Long id) {
	    	
	    	if (!jobDetailsRepository.existsById(id)) {
	            throw new RuntimeException("Blog not found with id: " + id);
	        }
	        jobDetailsRepository.deleteById(id);
	    }
	    
	        
	     
	    
}
