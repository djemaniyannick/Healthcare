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

import com.codeimmig.yannick.exception.SpecializationNotFoundException;
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
	public String viewAll(Model model,@RequestParam(value="message", required = false) String message) {
		List<Specialization> list=service.getAllSpecialization();
		model.addAttribute("list", list);
		model.addAttribute("message",message);
		return "specializationData";
	}
	/**
	 *2. Delete on Specialization
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

	/***
	 * 3. Show Register page
	 */
	@GetMapping("/register")
	public String displayRegister() {
		return "SpecializationRegister";
	}
	/**
	 4. Save Specialisation 
	 * @param specialization
	 * @param model
	 * @return
	 */

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

	/**
	 * Fetch Data into Edit page
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

	@PostMapping("/update")
	public String updateSpec(@ModelAttribute Specialization specialization, RedirectAttributes attributes) {
		service.updateSpecialization(specialization);
		attributes.addAttribute("message", "Record ("+specialization.getId()+") is updated");
		return "redirect:all";
	}

	@GetMapping("/checkCode")
	@ResponseBody
	public String validateSpecCode(@RequestParam String code, @RequestParam Long id) {
		String message="";
		if(id==0 && service.isSpecCodeExist(code)) { // register check 
			message=code +", already exist ";
		}else if(id!=0 && service.isSpecCodeExist(code)) { // edit check
			message=code +", already exist ";
		}
		return message; //this is not viewName(it is message)
	}
}
