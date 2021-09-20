package com.codeimmig.yannick.service;

import java.util.List;

import com.codeimmig.yannick.model.Specialization;

public interface ISpecializationService {
	Long saveSpecialization(Specialization spec);
	List<Specialization> getAllSpecialization();
	void removeSpecialization(Long id);
	Specialization getOneSpecialization(Long id);
	public void updateSpecialization(Specialization spec);
	public boolean isSpecCodeExist(String specCode); 
	public boolean isSpecCodeExistForEdit(String specCode,Long id);

}
