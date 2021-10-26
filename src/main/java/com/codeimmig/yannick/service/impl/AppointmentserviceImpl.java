package com.codeimmig.yannick.service.impl;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codeimmig.yannick.exception.AppointmentNotFoundException;
import com.codeimmig.yannick.model.Appointment;
import com.codeimmig.yannick.repo.AppointmentRepository;
import com.codeimmig.yannick.service.IAppointmentService;

@Service
public class AppointmentserviceImpl  implements IAppointmentService{
	@Autowired
	AppointmentRepository repo;

	@Override
	public Long saveAppointment(Appointment app) {
		return repo.save(app).getId();
	}

	@Override
	@org.springframework.transaction.annotation.Transactional(readOnly = true)
	public List<Appointment> getAllAppointments() {
		return repo.findAll();
	}

	@Override
	public void removeAppointment(Long id) {
		repo.delete(getOneAppointment(id));

	}

	@Override
	public Appointment getOneAppointment(Long id) {
		Optional<Appointment> optional=repo.findById(id);
		if(optional.isPresent()) {
			return optional.get();
		}
		throw new AppointmentNotFoundException(id+" not found ");
	}

	@Override
	public void updateAppointment(Appointment app) {
		repo.save(app);

	}

}
