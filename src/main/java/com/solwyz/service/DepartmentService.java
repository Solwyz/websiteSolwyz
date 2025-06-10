package com.solwyz.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.solwyz.entity.Department;
import com.solwyz.entity.Designation;
import com.solwyz.exception.ResourceNotFoundException;
import com.solwyz.repo.ApplicationFormRepository;
import com.solwyz.repo.DepartmentRepository;
import com.solwyz.repo.DesignationRepository;

@Service
public class DepartmentService {

	@Autowired
	private DepartmentRepository departmentRepository;
	
	@Autowired
	private DesignationRepository designationRepository;
	
	@Autowired
	private ApplicationFormRepository applicantRepository;
	
	
	
	public Department addCategory(Department department) {
		return departmentRepository.save(department);
	}


	public void deleteCategoryById(Long id) {
		if (!departmentRepository.existsById(id)) {
			throw new RuntimeException("department not found with id: " + id);
		}
		departmentRepository.deleteById(id);
	}

	public Department updateCategory(Long id, Department updatedCategory) {
		Department existingCategory = departmentRepository.findById(id)
				.orElseThrow(() -> new RuntimeException(id + " does not exist"));

		existingCategory.setName(updatedCategory.getName());

		return departmentRepository.save(existingCategory);
	}




	public List<Map<String, Object>> getAllDepartmentsWithApplicantCount() {
	    List<Department> departments = departmentRepository.findAll();
	    List<Map<String, Object>> result = new ArrayList<>();

	    for (Department dept : departments) {
	        long count = applicantRepository.countByDesignationDepartmentId(dept.getId());

	        Map<String, Object> map = new HashMap<>();
	        map.put("id", dept.getId());
	        map.put("name", dept.getName());
	        map.put("vaccancy",dept.getVaccancy());
	        map.put("applicantCount", count);
	        result.add(map);
	    }

	    return result;
	}

}
