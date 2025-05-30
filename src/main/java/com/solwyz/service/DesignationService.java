package com.solwyz.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.solwyz.entity.Department;
import com.solwyz.entity.Designation;
import com.solwyz.entity.JobDetails;
import com.solwyz.exception.ResourceNotFoundException;
import com.solwyz.repo.DepartmentRepository;
import com.solwyz.repo.DesignationRepository;
import com.solwyz.repo.JobDetailsRepository;

@Service
public class DesignationService {

	@Autowired
	private DesignationRepository designationRepository;

	@Autowired
	private DepartmentRepository departmentRepository;
	
	@Autowired
	private JobDetailsRepository jobDetailsRepository;
	
	public Designation addDesignation(Designation designation) {
	    
	    if (designation.getDepartment() != null && designation.getDepartment().getId() != null) {
	        Department department = departmentRepository.findById(designation.getDepartment().getId())
	                .orElseThrow(() -> new RuntimeException("Department not found"));
	        designation.setDepartment(department);
	    }

	    
	    if (designation.getJobDetails() != null && designation.getJobDetails().getId() != null) {
	        JobDetails jobDetails = jobDetailsRepository.findById(designation.getJobDetails().getId())
	                .orElseThrow(() -> new RuntimeException("JobDetails not found"));
	        designation.setJobDetails(jobDetails);
	    }

	    return designationRepository.save(designation);
	}


	public List<Designation> getAllDesignation() {

		return designationRepository.findAll();
	}

	public void deleteDesignationById(Long id) {
		if (!designationRepository.existsById(id)) {
			throw new RuntimeException("Category not found with id: " + id);
		}
		designationRepository.deleteById(id);
	}

	public Designation updateDesignation(Long id, Designation designation) {

		Designation existingdesignation = designationRepository.findById(id)
				.orElseThrow(() -> new RuntimeException(id + " does not exist"));

		existingdesignation.setName(designation.getName());
		existingdesignation.setExperience(designation.getExperience());

		return designationRepository.save(existingdesignation);
	}

	public Designation getDesignationById(Long id) {
		return designationRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("designation not found with id: " + id));
	}
}
