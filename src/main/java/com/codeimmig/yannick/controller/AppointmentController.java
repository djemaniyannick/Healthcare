package com.codeimmig.yannick.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.codeimmig.yannick.exception.AppointmentNotFoundException;
import com.codeimmig.yannick.exception.DoctorNotFoundException;
import com.codeimmig.yannick.model.Appointment;
import com.codeimmig.yannick.service.IAppointmentService;
import com.codeimmig.yannick.service.IDoctorService;

@Controller
@RequestMapping("/appointment")
public class AppointmentController {
	@Autowired
	IAppointmentService service;
	
	@Autowired
	IDoctorService doctorService;
	private void commonUi(Model model) {
		model.addAttribute("doctors", doctorService.getDoctorIdAndNames());
	}
	
	@GetMapping("/register")
	public String displayregister( @RequestParam(value = "message", required = false) String message, Model model) {
		model.addAttribute("message", message);
		commonUi(model);
		return "AppointmentRegister";
	}	
	/**
	 *2- method to save appointment
	 * @param appointment
	 * @param model
	 * @return
	 */
	//2. save on submit
	@PostMapping("/save")
	public String saveAppointment(@ModelAttribute Appointment appointment, Model model) {
		java.lang.Long id=service.saveAppointment(appointment);
		model.addAttribute("message","Appointment created with Id:"+id);
		model.addAttribute("appointment",new Appointment()) ;
		commonUi(model);
		return "AppointmentRegister";
	}	
	/**
	 * 
	 * @param model
	 * @param message
	 * @return
	 */
	
	@GetMapping("/all")
	public String getAllAppointment(Model model, @RequestParam(value = "message", required = false) String message ) {
		List<Appointment> list=service.getAllAppointments();
		model.addAttribute("message",message);
		model.addAttribute("list", list);
		return "AppointmentData";	
	}
	/**
	 * 
	 * @param id
	 * @param attributes
	 * @return
	 */
	@GetMapping("/delete")
	public String deleteAppointment(@RequestParam("id") Long id, RedirectAttributes attributes ) {
		try {
			service.removeAppointment(id);
			attributes.addAttribute("message", "Record "+id+" is removed");

		} catch (AppointmentNotFoundException e) {
			e.printStackTrace();
			attributes.addAttribute("message",e.getMessage());
		}
		return "redirect:all";
	}
	/**
	 * 
	 * @param model
	 * @param id
	 * @param attributes
	 * @return
	 */
	@GetMapping("/edit")   
	public String  editAppointment(Model model, @RequestParam("id") Long id,RedirectAttributes attributes) {
		String page=null;

		try {
			Appointment app=service.getOneAppointment(id);
			model.addAttribute("appointment", app);
			commonUi(model);
			page="AppointmentEdit";

		} catch (DoctorNotFoundException e) {
			e.printStackTrace();
			model.addAttribute("message", e.getMessage());
			page="redirect:all";
		}
		return page;
	}
	/**
	 * 
	 * @param appointment
	 * @param attributes
	 * @return
	 */
	@PostMapping("/update")
	public String doUpdate(@ModelAttribute Appointment appointment, RedirectAttributes attributes) {
		service.updateAppointment(appointment);
		attributes.addAttribute("message", "Record ("+appointment.getId()+") is updated");
		return "redirect:all";
	}
	
}
