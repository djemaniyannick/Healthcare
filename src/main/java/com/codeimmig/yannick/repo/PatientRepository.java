package com.codeimmig.yannick.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.codeimmig.yannick.model.Patient;

public interface PatientRepository extends JpaRepository<Patient, Long> {

}
