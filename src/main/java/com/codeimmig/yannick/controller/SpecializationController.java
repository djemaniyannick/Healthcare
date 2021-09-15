package com.codeimmig.yannick.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.codeimmig.yannick.model.Specialization;
import com.codeimmig.yannick.service.ISpecializationService;

@Controller
@RequestMapping("/spec")
public class SpecializationController {
	@Autowired
	ISpecializationService service;
	/**
	 * Display All specialization
	 */
	@GetMapping("/all")
	public String viewAll(Model model) {
		List<Specialization> list=service.getAllSpecialization();
		model.addAttribute("list", list);
		return "specializationData";
	}
}
