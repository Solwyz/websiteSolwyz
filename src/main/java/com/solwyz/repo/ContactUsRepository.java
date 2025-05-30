package com.solwyz.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.solwyz.entity.ContactUs;

@Repository
public interface ContactUsRepository extends JpaRepository<ContactUs, Long>{

}
