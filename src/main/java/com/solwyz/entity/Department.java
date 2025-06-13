package com.solwyz.entity;



import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;

@Entity
@AllArgsConstructor
public class Department {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	private Integer vaccancy = 0;


	@OneToMany(mappedBy = "department", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Designation> designations;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public Department() {
		super();
		
	}
	public Integer getVaccancy() {
		return vaccancy;
	}
	public void setVaccancy(Integer vaccancy) {
		this.vaccancy = vaccancy;
	}
	public List<Designation> getDesignations() {
		return designations;
	}
	public void setDesignations(List<Designation> designations) {
		this.designations = designations;
	}
	
	

	
}
