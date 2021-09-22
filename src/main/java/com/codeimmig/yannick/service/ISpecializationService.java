package com.codeimmig.yannick.service;

import java.util.List;

import com.codeimmig.yannick.model.Specialization;

public interface ISpecializationService {
	public Long saveSpecialization(Specialization spec);
	public List<Specialization> getAllSpecialization();
	public void removeSpecialization(Long id);
	public Specialization getOneSpecialization(Long id);
	public void updateSpecialization(Specialization spec);
	
	public boolean isSpecCodeExist(String specCode); 
	public boolean isSpecCodeExistForEdit(String specCode,Long id);
}
