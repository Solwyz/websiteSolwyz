package com.solwyz.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.solwyz.entity.Designation;
import com.solwyz.pojo.response.ApiResponse;
import com.solwyz.service.DesignationService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/designation")
public class DesignationController {

    @Autowired
    private DesignationService designationService;

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<Designation>> addDesignation(@RequestBody Designation designation) {
        Designation saved = designationService.addDesignation(designation);
        return ResponseEntity.ok(new ApiResponse<>("Designation created successfully", saved));
    }

    @GetMapping("/all")
    public ResponseEntity<ApiResponse<List<Designation>>> getAllDesignations() {
        List<Designation> designations = designationService.getAllDesignation();
        return ResponseEntity.ok(new ApiResponse<>("success", designations));
    }
    
    @GetMapping("/{departmentId}")
    public ResponseEntity<ApiResponse<List<Map<String, Object>>>> getDesignationsByDepartment(
            @PathVariable Long departmentId) {
        return ResponseEntity.ok(new ApiResponse<>("success", designationService.getDesignationsByDepartment(departmentId)));
    }

    @GetMapping("/byId/{id}")
    public ResponseEntity<ApiResponse<Designation>> getDesignationById(@PathVariable Long id) {
        Designation designation = designationService.getDesignationById(id);
        return ResponseEntity.ok(new ApiResponse<>("success", designation));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse<String>> deleteDesignation(@PathVariable Long id) {
        designationService.deleteDesignationById(id);
        return ResponseEntity.ok(new ApiResponse<>("Designation deleted successfully", "Deleted ID: " + id));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse<Designation>> updateDesignation(@PathVariable Long id, @RequestBody Designation designation) {
        Designation updatedDesignation = designationService.updateDesignation(id, designation);
        return new ResponseEntity<>(new ApiResponse<>("Designation updated successfully", updatedDesignation), HttpStatus.OK);
    }
}
