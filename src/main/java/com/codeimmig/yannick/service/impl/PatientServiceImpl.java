package com.codeimmig.yannick.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codeimmig.yannick.exception.PatientNotFoundException;
import com.codeimmig.yannick.model.Patient;
import com.codeimmig.yannick.repo.PatientRepository;
import com.codeimmig.yannick.service.IPatientService;

@Service
public class PatientServiceImpl implements IPatientService {
	@Autowired
	private PatientRepository repo;

	@Override
	public Long savePatient(Patient pat) {
		return repo.save(pat).getId();
	}

	@Override
	public List<Patient> getAllPatient() {
		return repo.findAll();
	}

	@Override
	public void removePatient(Long id) {
		repo.delete(getOnePatient(id));
		
	}

	@Override
	public Patient getOnePatient(Long id) {
		Optional<Patient> optional=repo.findById(id);
		if(optional.isPresent()) {
			return optional.get();
		}
		else {
			throw new PatientNotFoundException(id+"not found");
		}
	}

	@Override
	public void updatePatient(Patient pat) {
		repo.save(pat);
		
	}

}
