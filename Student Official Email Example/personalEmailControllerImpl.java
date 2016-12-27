package edu.ucla.dt.studentweb.mvc.web.controller.personal.impl;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;

import edu.ucla.dt.studentweb.mvc.security.UserInfo;
import edu.ucla.dt.studentweb.mvc.service.StudentEmailHelper;
import edu.ucla.dt.studentweb.mvc.utils.UserUtils;
import edu.ucla.dt.studentweb.mvc.web.controller.AjaxBaseController;
import edu.ucla.dt.studentweb.mvc.web.controller.personal.PersonalEmailController;
import edu.ucla.dt.studentweb.mvc.web.domain.EmailData;
import edu.ucla.dt.studentweb.mvc.web.validation.personal.PersonalEmailValidator;
import edu.ucla.dt.studentweb.svc.dto.Email;

@Controller
public class PersonalEmailControllerImpl extends AjaxBaseController implements PersonalEmailController {
    private static final Logger logger = Logger.getLogger(PersonalEmailControllerImpl.class);
    private static final String padding = "    "; 
    private static final String EmailData = "emailData"; 
    
    @Autowired @Qualifier("studentEmailHelper")
    StudentEmailHelper stEmailHelper;
    
    @Autowired @Qualifier("personalEmailValidator")
    PersonalEmailValidator validator;

	@Override
	public String handleView(Model model) {
		logger.info("Official email - start");
		
		return "personal/contact";
	}

	@Override
	public void populateModel(Model model) {
		logger.debug( padding + "populating model ");

		UserInfo user = UserUtils.getUser();
		stEmailHelper.prepareEmailData(user, model);
		model.addAttribute("emailForm", this.createForm(model));
		
	}

	@Override
	public String handleProcess(@Valid @ModelAttribute("emailForm") Email form, BindingResult result, Model model, HttpServletRequest request, HttpServletResponse response) {
		logger.info(padding + "Official Email update ..................");

		if (result.hasErrors()) {
			logger.debug(padding + "got validation errors 2");
			response.setStatus(ValidationFailure);
            return "personal/email/edit";
		}
		
		Map<String, Email> emailChoices = (Map<String, Email>) model.asMap().get("emailChoices");
				
		UserInfo user = UserUtils.getUser();
		EmailData data = (EmailData) model.asMap().get(EmailData);
		stEmailHelper.updateEmailData(user, form, data, emailChoices);

		//update verify email flag
		stEmailHelper.updateEmailVerifyFlag(user, model);

		return null;

	}

	@Override
	public String handleShowForm(Model model) {
		logger.info("Official email form - start");
		
		return "personal/email/edit";
	}
	
	@Override
	public String verifyEmail(Model model) {
		logger.info("Official email  - verify");
		logger.debug("Official email: " + model.asMap());
		
		UserInfo user = UserUtils.getUser();

		stEmailHelper.updateEmailVerifyFlag(user, model);
		//stEmailHelper.updateEmailVerifyFlag(user.getUid());
		
		return "personal/email";
	}
	
	private Email createForm(Model model) {
		logger.debug( padding + "create Form ");
		
		EmailData data = (EmailData) model.asMap().get(EmailData);
		Email email = new Email();
		if (data != null) {
			email = stEmailHelper.getCurrentEmail(data);
			logger.debug( padding + "retrieve form " + email);
		}
		return email;
	}

}
