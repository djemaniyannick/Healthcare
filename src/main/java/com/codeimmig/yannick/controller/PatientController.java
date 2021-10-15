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

import com.codeimmig.yannick.exception.PatientNotFoundException;
import com.codeimmig.yannick.model.Patient;
import com.codeimmig.yannick.service.IPatientService;

@Controller
@RequestMapping("/patient")
public class PatientController {
	@Autowired
	IPatientService service;
	
	@GetMapping("/register")
	public String register(@RequestParam(value = "message", required = false) String message, Model model) {
		model.addAttribute("message", message);
		return "patientRegister";
	}

	@PostMapping("/save")
	public String savePatient(Model model, @ModelAttribute Patient patient) {
		Long id=service.savePatient(patient);
		String message="Patient ("+id+") is created";
		model.addAttribute("message", message);
		model.addAttribute("patient", new Patient());
		return "patientRegister";
	}
	
	@GetMapping("/all")
	public String getAllPatient(@RequestParam(value = "message", required = false) String message, Model model) {
		List<Patient> list=service.getAllPatient();
		model.addAttribute("message", message);
		model.addAttribute("list", list);
		return "patientData";	
	}

	@GetMapping("/delete")
	public String deletePatient(@RequestParam("id")Long id, RedirectAttributes redirectAttributes) {
		try {
			service.removePatient(id);
			redirectAttributes.addAttribute("message", "Record "+id+" is removed");

		} catch (PatientNotFoundException e) {
			e.printStackTrace();
		}
		return "redirect:all";
	}
	
	@GetMapping("/edit")
	public String editPatient(Model model , @RequestParam("id") Long id, RedirectAttributes attributes) {
		String page=null;
		try {
			Patient pat=service.getOnePatient(id);
			model.addAttribute("patient", pat);
			page="editPatient";
			
		} catch (PatientNotFoundException e) {
			e.printStackTrace();
			attributes.addAttribute("message", e.getMessage());
			page="redirect:all";
		}
		return page;
	}
	
	@PostMapping("/update")
	public String updatePatient(@ModelAttribute Patient patient, RedirectAttributes attributes) {
		service.updatePatient(patient);
		attributes.addAttribute("message", "Record ("+patient.getId()+") is updated");
		return "redirect:all";
	}
}
