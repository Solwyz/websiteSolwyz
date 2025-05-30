package com.solwyz.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.solwyz.entity.Department;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {

	//Department save(Department category);

	

	List<Department> findAll();

	

	

}
