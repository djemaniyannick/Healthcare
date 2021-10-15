package com.codeimmig.yannick.service;

import java.util.List;
import com.codeimmig.yannick.model.Patient;

public interface IPatientService {
	public Long savePatient(Patient pat);
	public List<Patient> getAllPatient();
	public void removePatient(Long id);
	public Patient getOnePatient(Long id);
	public void updatePatient(Patient pat);
}
