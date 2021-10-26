package com.codeimmig.yannick.service;

import java.util.List;
import java.util.Map;

import com.codeimmig.yannick.model.Doctor;

public interface IDoctorService {
	public Long saveDoctor(Doctor doc);
	public List<Doctor> getAllDoctor();
	public void removeDoctor(Long id);
	public Doctor getOneDoctor(Long id);
	public void updateDoctor(Doctor doc);
	public Map<Long,String> getDoctorIdAndNames();
}
