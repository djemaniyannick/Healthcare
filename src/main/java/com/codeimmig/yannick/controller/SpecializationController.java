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
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.codeimmig.yannick.exception.SpecializationNotFoundException;
import com.codeimmig.yannick.model.Specialization;
import com.codeimmig.yannick.service.ISpecializationService;
import com.codeimmig.yannick.view.SpecializationExcelView;

//import org.slf4j.Logger;
@Controller
@RequestMapping("/spec")
public class SpecializationController {
	
	@Autowired
	private ISpecializationService service;
	//private static final Logger LOG=org.slf4j.LoggerFactory.getLogger(Specialization.class);

	/***
	 * 1. Show Register page
	 */
	@GetMapping("/register")
	public String displayRegister() {
		return "SpecializationRegister";
	}
	/**
	 2. Save Specialisation 
	 * @param specialization
	 * @param model
	 * @return
	 */

	/**
	 * 2. On Submit Form save data
	 */
	@PostMapping("/save")
	public String saveForm(
			@ModelAttribute Specialization specialization,
			Model model
			)
	{
		Long id = service.saveSpecialization(specialization);
		String message ="Record ("+id+") is created";
		model.addAttribute("message", message);
		return "SpecializationRegister";
	}

	/**
	/**
	 *3. Display All specialization
	 */
	@GetMapping("/all")
	public String viewAll(Model model, @RequestParam(value="message", required = false) String message) {
		List<Specialization> list=service.getAllSpecialization();
		model.addAttribute("list", list);
		model.addAttribute("message",message);
		return "specializationData";
	}
	
	/**
	 *4. Delete on Specialization
	 */
	@GetMapping("/delete")
	public String deleteData(@RequestParam Long id, RedirectAttributes attributes) {
		try {
			service.removeSpecialization(id);
			attributes.addAttribute("message", "Record"+id+"is removed");

		} catch (SpecializationNotFoundException e) {
			e.printStackTrace();
			attributes.addAttribute("message", e.getMessage());
		}
		return "redirect:all";
	}
	/**
	 * 5-Fetch Data into Edit page
	 *  End user clicks on Link, may enter ID manually.
	 *  If entered id is present in DB
	 *     > Load Row as Object
	 *     > Send to Edit Page
	 *     > Fill in Form
	 *  Else
	 *    > Redirect to all (Data Page)
	 *    > Show Error message (Not found)     
	 * @param id
	 * @param model
	 * @param attributes
	 * @return
	 */

	@GetMapping("/edit")
	public String editSpec(@RequestParam Long id, Model model, RedirectAttributes attributes) {
		String page=null;
		try {
			Specialization spec=service.getOneSpecialization(id);
			model.addAttribute("specialization", spec);
			page="SpecializationEdit";

		} catch (SpecializationNotFoundException e) {
			e.printStackTrace();
			attributes.addAttribute("message", e.getMessage());
			page="redirect:all";
		}

		return page;
	}
	/**
	 7. update specialization
	 * @param specialization
	 * @param attributes
	 * @return
	 */
	@PostMapping("/update")
	public String updateSpec(@ModelAttribute Specialization specialization, RedirectAttributes attributes) {
		service.updateSpecialization(specialization);
		attributes.addAttribute("message", "Record ("+specialization.getId()+") is updated");
		return "redirect:all";
	}

	/**
	 * 8. Read code and check with service
	 *    Return message back to UI 
	 */
	@GetMapping("/checkCode")
	@ResponseBody
	public String validateSpecCode(
			@RequestParam String code,
			@RequestParam Long id
			) 
	{
		String message = "";
		if(id==0 && service.isSpecCodeExist(code)) { //register check
			message = code + ", already exist";
		} else if(id!=0 && service.isSpecCodeExistForEdit(code,id)) { //edit check
			message = code + ", already exist";
		} 
		
		return message; //this is not viewName(it is message)
	}
	/**
	 *7-excel export
	 * @return
	 */
	@GetMapping("/excel")
	public ModelAndView exportToExcel() {
		ModelAndView m=new ModelAndView();
		m.setView(new SpecializationExcelView());
		//read data from DB 
		List<Specialization> list=service.getAllSpecialization();
		m.addObject("list", list);
		return m;
	}
}
