package com.codeimmig.yannick.service.impl;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codeimmig.yannick.exception.DoctorNotFoundException;
import com.codeimmig.yannick.model.Doctor;
import com.codeimmig.yannick.repo.DoctorRepository;
import com.codeimmig.yannick.service.IDoctorService;
import com.codeimmig.yannick.util.MyCollectionsUtil;

@Service
public class DoctorServiceImpl  implements IDoctorService{
	@Autowired
	private DoctorRepository repo;
	
	
	@Override
	public Long saveDoctor(Doctor doc) {
		return repo.save(doc).getId();
	}

	@Override
	public List<Doctor> getAllDoctor() {
		return repo.findAll();
	}

	@Override
	public void removeDoctor(Long id) {
		 repo.delete(getOneDoctor(id));
	}

	@Override
	public Doctor getOneDoctor(Long id) {
		Optional<Doctor> optional=repo.findById(id);
		if(optional.isPresent()) {
			return optional.get();
		}
		else {
			throw new  DoctorNotFoundException(+id+", not found ");
		}
	}

	@Override
	public void updateDoctor(Doctor doc) {
		repo.save(doc);	
	}
	
	@Override
	public Map<Long, String> getDoctorIdAndNames() {
		List<Object[]> list = repo.getDoctorIdAndNames();
		return MyCollectionsUtil.convertToMapIndex(list);
	}
}
