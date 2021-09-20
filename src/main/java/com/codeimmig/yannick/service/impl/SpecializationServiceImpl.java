package com.codeimmig.yannick.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codeimmig.yannick.model.Specialization;
import com.codeimmig.yannick.repo.SpecialisationRepository;
import com.codeimmig.yannick.service.ISpecializationService;
@Service
public class SpecializationServiceImpl implements ISpecializationService {
	@Autowired
	SpecialisationRepository repo;

	@Override
	public Long saveSpecialization(Specialization spec) {
		
		return repo.save(spec).getId();
	}

	@Override
	public List<Specialization> getAllSpecialization() {
		
		return repo.findAll();
	}

	@Override
	public void removeSpecialization(Long id) {
		repo.deleteById(id);
		
	}

	@Override
	public Specialization getOneSpecialization(Long id) {
		// TODO Auto-generated method stub
		Optional<Specialization> optional=repo.findById(id);
		if(optional.isPresent()) {
			return optional.get();
		}else {
			return null;
		}
	}

	@Override
	public void updateSpecialization(Specialization spec) {
		repo.save(spec);
		
	}
	
	@Override
	public boolean isSpecCodeExist(String specCode) {
		return repo.getSpecCodeCount(specCode)>0;
	}

}
