package com.codeimmig.yannick.service;

import java.util.List;
import com.codeimmig.yannick.model.Appointment;
public interface IAppointmentService {
		public Long saveAppointment(Appointment app);
		public List<Appointment> getAllAppointments();
		public void removeAppointment(Long id);
		public Appointment getOneAppointment(Long id);
		public void updateAppointment(Appointment app);
	
}
