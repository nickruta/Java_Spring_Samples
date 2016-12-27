package edu.ucla.dt.studentweb.mvc.web.controller.personal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import edu.ucla.dt.studentweb.svc.dto.Email;

@RequestMapping("app/personal/email")
public interface PersonalEmailController {

	public static final String url = "app/personal/email";
	
	@ModelAttribute
	public void populateModel(Model model);

//	@ModelAttribute(value = "emailForm")
//	public Email createForm(Model model);

	@RequestMapping(method = RequestMethod.GET)
    public String handleView(Model model);
	
	@RequestMapping(value="/edit", method = RequestMethod.POST)
	public String handleProcess(@Valid @ModelAttribute("emailForm") Email form, BindingResult result, Model model, HttpServletRequest request, HttpServletResponse response);
	
	@RequestMapping(value="/edit", method = RequestMethod.GET)
	public String handleShowForm(Model model);

	@RequestMapping(value="/verify", method = RequestMethod.GET)
	public String verifyEmail(Model model);
}
