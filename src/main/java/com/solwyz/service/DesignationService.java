package com.solwyz.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.solwyz.entity.Department;
import com.solwyz.entity.Designation;
import com.solwyz.entity.JobDetails;
import com.solwyz.enums.Status;
import com.solwyz.exception.ResourceNotFoundException;
import com.solwyz.repo.ApplicationFormRepository;
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

	@Autowired
	private ApplicationFormRepository applicantRepository;

	public Designation addDesignation(Designation designation) {

		if (designation.getDepartment() != null && designation.getDepartment().getId() != null) {
			Department department = departmentRepository.findById(designation.getDepartment().getId())
					.orElseThrow(() -> new RuntimeException("Department not found"));

			int currentVacancy = department.getVaccancy() != null ? department.getVaccancy() : 0;
			department.setVaccancy(currentVacancy + 1);

			departmentRepository.save(department);

			designation.setDepartment(department);
		}

		JobDetails jobDetails = designation.getJobDetails();
		if (jobDetails != null) {
			if (jobDetails.getId() == null || jobDetails.getId() == 0) {
				jobDetails = jobDetailsRepository.save(jobDetails);
			} else {
				jobDetails = jobDetailsRepository.findById(jobDetails.getId())
						.orElseThrow(() -> new RuntimeException("JobDetails not found"));
			}
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
		Designation existingDesignation = designationRepository.findById(id)
				.orElseThrow(() -> new RuntimeException(id + " does not exist"));

		existingDesignation.setName(designation.getName());
		existingDesignation.setExperience(designation.getExperience());
		existingDesignation.setStatus(designation.getStatus());

		if (designation.getJobDetails() != null) {
			JobDetails existingJobDetails = existingDesignation.getJobDetails();
			JobDetails newJobDetails = designation.getJobDetails();

			if (existingJobDetails == null) {
				existingJobDetails = new JobDetails();
			}

			existingJobDetails.setDesignation(newJobDetails.getDesignation());
			existingJobDetails.setLoaction(newJobDetails.getLoaction());
			existingJobDetails.setExperience(newJobDetails.getExperience());
			existingJobDetails.setJobType(newJobDetails.getJobType());
			existingJobDetails.setQualification(newJobDetails.getQualification());
			existingJobDetails.setEmailId(newJobDetails.getEmailId());
			existingJobDetails.setResponsibilities(newJobDetails.getResponsibilities());
			existingJobDetails.setRequirements(newJobDetails.getRequirements());

			existingDesignation.setJobDetails(existingJobDetails);
		}

		if (designation.getDepartment() != null) {
			existingDesignation.setDepartment(designation.getDepartment());
		}

		return designationRepository.save(existingDesignation);
	}

	public Designation getDesignationById(Long designationId) {
		return designationRepository.findById(designationId)
				.orElseThrow(() -> new ResourceNotFoundException("designation not found with id: " + designationId));
	}

	public List<Map<String, Object>> getDesignationsByDepartment(Long departmentId) {
		List<Designation> designations = designationRepository.findByDepartmentId(departmentId);
		List<Map<String, Object>> result = new ArrayList<>();

		for (Designation des : designations) {
			long count = applicantRepository.countByDesignationId(des.getId());

			Map<String, Object> map = new HashMap<>();
			map.put("id", des.getId());
			map.put("name", des.getName());
			map.put("experience", des.getExperience());
			map.put("applicantCount", count);
			map.put("status", des.getStatus());
			result.add(map);
		}

		return result;
	}

	public Designation updateDesignationStatus(Long id, Status newStatus) {
		Designation designation = designationRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Designation with id " + id + " not found"));

		designation.setStatus(newStatus);
		return designationRepository.save(designation);
	}

}
