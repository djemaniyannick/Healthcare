package com.codeimmig.yannick.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.codeimmig.yannick.model.Appointment;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
	

}
