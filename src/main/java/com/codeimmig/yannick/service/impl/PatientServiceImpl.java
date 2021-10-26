package com.codeimmig.yannick.service.impl;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codeimmig.yannick.constants.UserRoles;
import com.codeimmig.yannick.exception.PatientNotFoundException;
import com.codeimmig.yannick.model.Patient;
import com.codeimmig.yannick.model.User;
import com.codeimmig.yannick.repo.PatientRepository;
import com.codeimmig.yannick.service.IPatientService;
import com.codeimmig.yannick.service.IUserService;
import com.codeimmig.yannick.util.UserUtil;

@Service
public class PatientServiceImpl implements IPatientService {
	@Autowired
	private PatientRepository repo;
	
	@Autowired
	private IUserService userService;
	
	@Autowired
	private UserUtil util;
	

	

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
	
	@Override
	@Transactional
	public Long savePatient(Patient pat) {
		Long id=repo.save(pat).getId();
		
		if(id!=null){
			User user=new User();
			user.setDisplayName(pat.getFirstName()+" "+pat.getLastName());
			user.setUsername(pat.getEmail());
			user.setPassword(util.genPwd());
			user.setRole(UserRoles.PATIENT.name());
			userService.saveUser(user);
		}
		return id;
	}

}
