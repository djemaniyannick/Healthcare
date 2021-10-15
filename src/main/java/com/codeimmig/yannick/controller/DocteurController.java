package com.codeimmig.yannick.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.codeimmig.yannick.exception.DoctorNotFoundException;
import com.codeimmig.yannick.model.Doctor;
import com.codeimmig.yannick.service.IDoctorService;
import com.codeimmig.yannick.service.ISpecializationService;
import com.codeimmig.yannick.util.MyMailUtil;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@RequestMapping("/doctor")
@Controller
public class DocteurController {
	@Autowired
	private IDoctorService service;

	@Autowired
	private ISpecializationService specializationService;

	@Autowired
	private MyMailUtil mailUtil;

	/**
	 * 1-
	 * @return
	 */

	private void createDynamicUi(Model model) {
		model.addAttribute("specializations",specializationService.getSpecIdAndName());
	}

	@GetMapping("/register")
	public String displayRegister(@RequestParam(value = "message", required = false) String message, Model model) {
		model.addAttribute("message", message);
		createDynamicUi(model);
		return "DoctorRegister";
	}

	/**
	 *2- method to save doctor
	 * @param doctor
	 * @param model
	 * @return
	 */
	//2. save on submit
	@PostMapping("/save")
	public String save(
			@ModelAttribute Doctor doctor,
			RedirectAttributes attributes
			)
	{
		Long id = service.saveDoctor(doctor);
		String message = "Doctor ("+id+") is created";
		attributes.addAttribute("message", message);
		log.info("Before enter to send pdf method");
		if(id!=null) {
			new Thread(new Runnable() {
				public void run() {
					log.debug("id: {}",id);
					mailUtil.send(
							doctor.getEmail(), 
							"SUCCESS", 
							message,
							new ClassPathResource("/static/myres/healthcare.pdf"));
				}
			}).start();
		}
		log.info("after finish sendding mail(pdf)");
		return "redirect:register";
	}
	/**
	 * 3-display all doctor
	 * @param model
	 * @param message
	 * @return
	 */
	@GetMapping("/all")
	public String getAllDoctor(Model model, @RequestParam(value = "message", required = false) String message ) {
		List<Doctor> list=service.getAllDoctor();
		model.addAttribute("message",message);
		model.addAttribute("list", list);
		return "DoctorData";	
	}
	/**
	 * 4-delete doctor
	 * @param id
	 * @param attributes
	 * @return
	 */
	@GetMapping("/delete")
	public String deleteDoctor(@RequestParam("id") Long id, RedirectAttributes attributes ) {
		try {
			service.removeDoctor(id);
			attributes.addAttribute("message", "Record "+id+" is removed");

		} catch (DoctorNotFoundException e) {
			e.printStackTrace();
			attributes.addAttribute("message",e.getMessage());
		}
		return "redirect:all";
	}

	/**
	 5-Fetch Data into Edit page
	 *  End user clicks on Link, may enter ID manually.
	 *  If entered id is present in DB
	 *     > Load Row as Object
	 *     > Send to Edit Page
	 *     > Fill in Form
	 *  Else
	 *    > Redirect to all (Data Page)
	 *    > Show Error message (Not found) 
	 * @param model
	 * @param id
	 * @param attributes
	 * @return
	 */


	@GetMapping("/edit")   
	public String  editDoctor(Model model, @RequestParam("id") Long id,RedirectAttributes attributes) {
		String page=null;

		try {
			Doctor doc=service.getOneDoctor(id);
			model.addAttribute("doctor", doc);
			createDynamicUi(model);
			page="DoctorEdit";

		} catch (DoctorNotFoundException e) {
			e.printStackTrace();
			model.addAttribute("message", e.getMessage());
			page="redirect:all";
		}
		return page;
	}

	/**
	 * 
	 * @param doctor
	 * @param attributes
	 * @return
	 */

	@PostMapping("/update")
	public String doUpdate(@ModelAttribute Doctor doctor, RedirectAttributes attributes) {
		service.updateDoctor(doctor);
		attributes.addAttribute("message", "Record ("+doctor.getId()+") is updated");
		return "redirect:all";
	}

}
