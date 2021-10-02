package com.codeimmig.yannick.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.codeimmig.yannick.model.Doctor;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {

}
