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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.codeimmig.yannick.model.Specialization;
import com.codeimmig.yannick.service.ISpecializationService;
import org.slf4j.Logger;




@Controller
@RequestMapping("/spec")
public class SpecializationController {
	@Autowired
	private ISpecializationService service;
	private static final Logger LOG=org.slf4j.LoggerFactory.getLogger(Specialization.class);
	/**
	 *1. Display All specialization
	 */
	@GetMapping("/all")
	public String viewAll(Model model) {
		List<Specialization> list=service.getAllSpecialization();
		model.addAttribute("list", list);
		return "specializationData";
	}
	/**
	 *2. Delete on Specialization
	 */
	@GetMapping("/delete")
	public String deleteData(@RequestParam Long id, RedirectAttributes attributes) {
		service.removeSpecialization(id);
		attributes.addAttribute("message", "Record"+id+"is removed");
		return "redirect:all";
	}
	
	/***
	 * 3. Show Register page
	 */
	@GetMapping("/register")
	public String displayRegister() {
		return "SpecializationRegister";
	}
	
	@PostMapping("/save")
	public String saveSpecialization(@ModelAttribute Specialization specialization, Model model) {
		try {
			LOG.info("enter to save Method");
			Long id=service.saveSpecialization(specialization);
			String message="Specialization "+id+" create successfull !!!";
			LOG.debug(message);
			model.addAttribute("message", message);
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error("unable to save specialization"+e.getMessage());
		}
		LOG.info("About to leave save method");
		return "SpecializationRegister";
	}
	
	@GetMapping("/edit")
	public String editSpec(@RequestParam Long id, Model model) {
		Specialization spec=service.getOneSpecialization(id);
		model.addAttribute("specialization", spec);
		return "SpecializationEdit";
	}
	
	@PostMapping("/update")
	public String updateSpec(@ModelAttribute Specialization specialization, RedirectAttributes attributes) {
		service.updateSpecialization(specialization);
		attributes.addAttribute("message", "Record ("+specialization.getId()+") is updated");
		return "redirect:all";
	}
	
	@GetMapping("/checkCode")
	@ResponseBody
	public String validateSpecCode(@RequestParam String code) {
		String message="";
		if(service.isSpecCodeExist(code)) {
			message=code +", already exist ";
		}
		return message; //this is not viewName(it is message)
	}
}
