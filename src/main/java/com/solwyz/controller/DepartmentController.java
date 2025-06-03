package com.solwyz.controller;


import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

import com.solwyz.entity.Department;
import com.solwyz.entity.Designation;
import com.solwyz.pojo.response.ApiResponse;
import com.solwyz.service.DepartmentService;

import io.swagger.v3.oas.annotations.tags.Tag;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/departments")
@Tag(name = "department Authentication", description = "APIs for All department page")
public class DepartmentController {
	
	@Autowired
	private DepartmentService categoryService;
	
	@PostMapping("/create")
	public ResponseEntity<Department> addCategory(@RequestBody Department category) {
		return ResponseEntity.ok(categoryService.addCategory(category));
	}
	

	@GetMapping("/all")
	public ResponseEntity<ApiResponse<List<Map<String, Object>>>> getAllDepartments() {
	    return ResponseEntity.ok(new ApiResponse<>("success", categoryService.getAllDepartmentsWithApplicantCount()));
	}
	

	

//	@GetMapping("/{departmentId}")
//	public ResponseEntity<ApiResponse<List<Designation>>> getDesignationsByDepartment(@PathVariable Long departmentId) {
//	    List<Designation> designations = categoryService.getDesignationsByDepartment(departmentId);
//	    return ResponseEntity.ok(new ApiResponse<>("success", designations));
//	}


	 @DeleteMapping("/delete/{id}")
	    public ResponseEntity<String> deleteCategory(@PathVariable Long id) {
	        categoryService.deleteCategoryById(id);
	        return ResponseEntity.ok("Category deleted successfully.");
	    }
	 
	 @PutMapping("/{id}")
	    public ResponseEntity<Department> updateCategory(@PathVariable Long id, @RequestBody Department category) {
	        Department updatedCategory = categoryService.updateCategory(id, category);
	        return new ResponseEntity<>(updatedCategory, HttpStatus.OK);
	    }
}
