package edu.ucla.dt.studentweb.mvc.service.impl;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import edu.ucla.dt.studentweb.dao.StudentContactDataDAO;
import edu.ucla.dt.studentweb.mvc.security.UserInfo;
import edu.ucla.dt.studentweb.mvc.service.StudentEmailHelper;
import edu.ucla.dt.studentweb.mvc.utils.UserUtils;
import edu.ucla.dt.studentweb.mvc.web.domain.EmailData;
import edu.ucla.dt.studentweb.mvc.web.form.admission.StudentContactForm;
import edu.ucla.dt.studentweb.svc.client.LogonWSClient;
import edu.ucla.dt.studentweb.svc.client.PreEditSummerWebServiceClient;
import edu.ucla.dt.studentweb.svc.client.StudentContactWebServiceClient;
import edu.ucla.dt.studentweb.svc.domain.Status;
import edu.ucla.dt.studentweb.svc.domain.WSResponse;
import edu.ucla.dt.studentweb.svc.domain.Status.ErrorLevel;
import edu.ucla.dt.studentweb.svc.dto.Email;
import edu.ucla.dt.studentweb.svc.dto.Email.PrivacyPolicy;
import edu.ucla.dt.studentweb.svc.dto.EmergencyContacts;
import edu.ucla.dt.studentweb.svc.dto.PreEditSummerData;
import edu.ucla.dt.studentweb.svc.dto.SIRInfo;
import edu.ucla.dt.studentweb.svc.dto.StudentContact;
import edu.ucla.dt.studentweb.svc.utils.GeneralUtils;

@Service("studentEmailHelper")
public class StudentEmailHelperImpl implements StudentEmailHelper {
	private static final Logger logger = Logger.getLogger(StudentEmailHelperImpl.class);
	private static final String padding = "        "; 
	
	@Autowired @Qualifier("studentContactClient")
	private StudentContactWebServiceClient studentContactClient;
	    
	@Autowired @Qualifier("preEditSummerClient")
	private PreEditSummerWebServiceClient preEditSummerClient;
	
	@Autowired
	private StudentContactDataDAO stContactDao;
	
	@Autowired @Qualifier("logonService")
	private LogonWSClient logonWs;
	
	@Override
	public void prepareEmailData(UserInfo user, Model model) {
		logger.debug("prepareEmailData started");
		
		// add student emails
		StudentContact contact = this.getStudentContact(user);
		
		EmailData data = this.getStudentEmailData(user, contact);
		model.addAttribute("emailData", data);
		
		// add privacy options constants
		model.addAttribute("privacyOptions", StudentEmailHelper.PrivacyOptions);
		
		// add other Email type
		model.addAttribute("otherEmailType", "uclaNewStudentEmail");
		
		// add student email choices
		model.addAttribute("emailChoices", this.getEmailChoices(user, data, Boolean.FALSE));
		
	}

	
	@Override
	public StudentContact getStudentContact(UserInfo user) {
		return studentContactClient.getStudentContactInfo("Current", user.getUid(), "N", "True", StudentContact.GET_EMAILS_FLAG).getData();
	}
	
	@Override
	public EmailData getStudentEmailData(UserInfo user) {
		StudentContact res = this.getStudentContact(user);
		logger.debug(padding + res);
		
		return this.getStudentEmailData(user, res);
	}
	
	@Override
	public Email getOfficialEmail(UserInfo user) {
		StudentContact res = this.getStudentContact(user);
		
		if (logger.isDebugEnabled())
			logger.debug("     >> email verify code: " + res.getEmailMiscInfo().getEmailVerifyFlag());
		
		return this.getOfficialEmail(res);
	}
	
	@Override
	public Email getOfficialEmail(StudentContact res) {
		Map<String, Email> data = res.getEmailList();
		Email current = null;
		
		if (!CollectionUtils.isEmpty(data) && data.containsKey(uclaOfficialEmail)) {
			current = data.get(uclaOfficialEmail);
		}
		return current;
	}

	@Override
	public Boolean getEmailVerifiedFlag(String uid) {
		return stContactDao.getEmailVerifiedFlag(uid);
	}
	
	@Override
	public void updateEmailVerifyFlag(String uid) {
		stContactDao.updateEmailVerifiedFlag(uid, Boolean.TRUE);
	}
	
	@Override
	public void updateEmailVerifyFlag(UserInfo user, Model model) {
		WSResponse<Object> res = logonWs.updateEmailVerifyFlag(user.getUid(), Boolean.TRUE);
		
		if (res != null && res.getStatus() != null && !ErrorLevel.success.equals(res.getStatus().getErrorLevel())) {
			model.addAttribute("errorMessages", res.getStatus().getMessages());
		}
	}
	
	@Override
	public Status updateEmailVerifyFlag(UserInfo user) {
		Status status = null;
		WSResponse<Object> res = logonWs.updateEmailVerifyFlag(user.getUid(), Boolean.TRUE);
		
		if (res!= null)
			status = res.getStatus();
		
		return status;
	}
	
	@Override
	public EmailData getStudentEmailData(UserInfo user, StudentContact res) {
		EmailData data = new EmailData();
		
//		StudentContact res = studentContactClient.getStudentContactInfo("Current", user.getUid(), "N", "True", StudentContact.GET_EMAILS_FLAG).getData();
//		logger.debug(padding + res);
		
		data.setEmails(res.getEmailList());
		
		// set applicant email
		Email applEmail = new Email(applicantEmail, res.getEmailMiscInfo().getApplicantEmail(), PrivacyPolicy.NA);
		data.setApplicantEmail(applEmail);
		
		if (UserUtils.hasRole("GROUP_STUDENT")) {
			//for students
			data.setQualifiesForCTSEmail(true);
		}
		else {
			// for admits
			
			if (StringUtils.hasText(user.getPersonData().getCollege()))
				data.setQualifiesForCTSEmail(
						this.isQualifiesForCTSEmail(
								res.getEmailMiscInfo().getEmailEligibility(), 
								this.getPreEditSummerStatusFlag(
										user.getUid(), 
										user.getPersonData().getCollege())));
			else {
				data.setQualifiesForCTSEmail(
						this.isQualifiesForCTSEmail(
								res.getEmailMiscInfo().getEmailEligibility(), 
								Boolean.FALSE));
			}
		}
		
		//set official/current email
		Email current = new Email();
		if (!CollectionUtils.isEmpty(data.getEmails()) && data.getEmails().containsKey(uclaOfficialEmail)) {
			Email officialEmail = data.getEmails().get(uclaOfficialEmail);
			
			current.setAddress(officialEmail.getAddress());
			current.setPrivacy(officialEmail.getPrivacy());
			current.setType(officialEmail.getType());
		}
		else if (data.getQualifiesForCTSEmail()) {
			//current.setType(uclaOfficialEmail);
			
		}
		else {
			// official email is not set, set applicant email as current
			current.setAddress(applEmail.getAddress());
			current.setPrivacy(applEmail.getPrivacy());
			current.setType(applEmail.getType());
		}
		data.setCurrent(current);
		
		logger.debug(padding + data);
		return data;
	}
	
	@Override
	public Map<String, Email> getEmailChoices(UserInfo user, EmailData data, Boolean useAdmissionEmailFlag) {
		Map<String, Email> emails = new HashMap<String, Email> ();
		
		Map<String, Boolean> emailFlag = new HashMap<String, Boolean> ();
		
		//include official email to email choices is available
		if (data.getEmails() != null) {
			if (data.getEmails().containsKey(uclaOfficialEmail)) {
				emailFlag.put(data.getEmails().get(uclaOfficialEmail).getAddress(), Boolean.TRUE);
				emails.put(uclaOfficialEmail, data.getEmails().get(uclaOfficialEmail));
			}
	
			// need to be sure not include duplicate email addressess to map
			for (String key : data.getEmails().keySet()) {
				Email emailEntry = data.getEmails().get(key);
				
				if (
						!emailFlag.containsKey(emailEntry.getAddress()) 
						&& !emailEntry.getType().equalsIgnoreCase(uclaEmergencyContactEmail)
					) {
					emailFlag.put(emailEntry.getAddress(), Boolean.TRUE);
					emails.put(key, emailEntry);
				}
			}
		}
		//emails.putAll(data.getEmails());
		
		//check if BOL email is in the list and add it if student qualifies
		if (
				(!CollectionUtils.isEmpty(emails) &&
				!emails.containsKey(uclaBOLEmail)  ||
				CollectionUtils.isEmpty(emails))
				
				&& !isQualifiesForOwnEmail(user.getSirs()) 
				&& !hasQualifiedOfficialEmail(data.getEmails())
				&& data.getQualifiesForCTSEmail()) {
			
			//generate BOL email
			Email bolEmail = generateBOLEmail(user.getLogonId());
			if (!emailFlag.containsKey(bolEmail.getAddress())) 
				emails.put(uclaBOLEmail, bolEmail);
		}
		
		if (useAdmissionEmailFlag 
				&& data.getApplicantEmail() != null 
				&& StringUtils.hasText(data.getApplicantEmail().getAddress())
				&& !emailFlag.containsKey(data.getApplicantEmail().getAddress())) {
			emails.put(applicantEmail, data.getApplicantEmail());
		}
		
		logger.debug(padding + "Email choices: " + emails);
		return emails;
	}

	@Override
	public Email getCurrentEmail(EmailData data) {
		Email email = new Email();
		Email current = data.getCurrent();
		email.setPrivacy(current.getPrivacy());
		email.setType(current.getType());
		
		if (applicantEmail.equalsIgnoreCase(current.getType())) 
			email.setAddress(current.getAddress());
			
		return email;
	}
	
	@Override
	public StudentContact prepareEmergencyContactData(EmergencyContacts contact, EmailData data) {
		
		StudentContact contactData = new StudentContact();
		contactData.setEmailList(new HashMap<String, Email>());
		
		contactData.setEmergencyContacts(contact);
		
		if (data.getEmails() != null && 
			!data.getEmails().containsKey(uclaEmergencyContactEmail)  ) {
			
			if (contact != null && contact.getContact() != null && contact.getContact().getEmail() != null &&
					StringUtils.hasText(contact.getContact().getEmail().getAddress()))
					contactData.getEmailList().put(
								uclaEmergencyContactEmail, 
								new Email(uclaEmergencyContactEmail, contact.getContact().getEmail().getAddress(), null));
			
		}
		else {
			//check if emergency contact email was changed
			if (contact != null && 
					contact.getContact() != null &&
					contact.getContact().getEmail() != null &&
					StringUtils.hasText(contact.getContact().getEmail().getAddress())) {

				Email email = data.getEmails().get(uclaEmergencyContactEmail);

				if (!email.getAddress().equalsIgnoreCase(contact.getContact().getEmail().getAddress())) {
					// Add changed emergency contact email
					contactData.getEmailList().put(
							uclaEmergencyContactEmail, 
							new Email(uclaEmergencyContactEmail, contact.getContact().getEmail().getAddress(), null));
				}
			}
			else {
				// Delete emergency contact email
				contactData.getEmailList().put(
						uclaEmergencyContactEmail, 
						new Email(uclaEmergencyContactEmail, "DELETE", null));
			}
		}
		
		logger.debug(padding + "Updating Application Contact data: " + contactData);
		
		return contactData;
	}

	@Override
	public void updateApplicantContactData(UserInfo user, StudentContactForm contactForm) {
		// get Email data
		EmailData data = this.getStudentEmailData(user);		

//		StudentContactForm stuContact = studentContactClient.getAdmissionEmailForm(user, termCd, careerCd, null, null);
		EmergencyContacts  emergencyContacts = contactForm.getEmergencyContact();
		Email email = contactForm.getEmail();
	
		// get Email Choices
		Map<String, Email> emailChoices = this.getEmailChoices(user, data, true);
		
		StudentContact contactData = this.prepareEmergencyContactData(emergencyContacts, data);
		
		logger.debug("      >> prepared Emergency Contact Data " + contactData);
		
		Boolean preEditSummerStatusFlag = false;
		if (StringUtils.hasText(user.getPersonData().getCollege()))
			preEditSummerStatusFlag = this.getPreEditSummerStatusFlag(user.getUid(), user.getPersonData().getCollege());
		
		Email emailBOL = this.getEmailForType(data, uclaBOLEmail);
		this.activateBOLServices(user.getUid(), user.getLogonId(), emailBOL.getAddress(), preEditSummerStatusFlag);
		
		boolean officialEmailChangedFlag = false;
		if (email != null) {
			officialEmailChangedFlag  = this.update(user, email, contactData, data, emailChoices);
		}

		if(!officialEmailChangedFlag) {
			// need to update emergency contact
			logger.debug("      >> no changes in official email, updating Emergency Contact Data");
			WSResponse<Object> res = studentContactClient.updateStudentContactInfo(user.getUid(), contactData, null, null);
		}
	}
	
	@Override
	public void updateEmailData(UserInfo user, 
								Email newEmail, 
								EmailData data,
								Map<String, Email> emailChoices) {
		StudentContact contactData = new StudentContact();
		contactData.setEmailList(new HashMap<String, Email>());
		
		if (newEmail != null) {
			this.update(user, newEmail, contactData, data, emailChoices);
		}
		
	}
	
	private boolean update(UserInfo user, 
						Email newEmail, 
						StudentContact contactData, 
						EmailData data,
						Map<String, Email> emailChoices) {
		
		boolean officialEmailChangedFlag = false;
		boolean officialEmailOtherFlag = false;
		
		logger.info("newEmail = " + newEmail);
		logger.debug("emailChoices = " + emailChoices);

		// if new email type is not uclaNewStudentEmail, get email address from email choices
		// email choices should have the key
		if (!"uclaNewStudentEmail".equalsIgnoreCase(newEmail.getType())) {
			newEmail.setAddress(emailChoices.get(newEmail.getType()).getAddress());
		}
		
		logger.debug("Current official email: " + data.getCurrent());
		logger.debug("New official email: " + data.getCurrent());
		
		Map<String, Email> existingEmailList = data.getEmails();

		if (// current official email is empty
			data.getCurrent() == null
			|| data.getCurrent().getAddress() == null
			
			// if current official email address was changed
			|| !newEmail.getAddress().equals(data.getCurrent().getAddress()) 
			
			//if user selected different choice
			|| !newEmail.getType().equals(data.getCurrent().getType())
			) {
			
			officialEmailChangedFlag = true;
			
			// changing official email
			if (existingEmailList != null &&
					!existingEmailList.containsKey((newEmail.getType()))) {
				officialEmailOtherFlag = true;
				contactData.getEmailList().put(uclaOtherStudentEmail, new Email(uclaOtherStudentEmail, newEmail.getAddress(), newEmail.getPrivacy()));
			}
			else
				contactData.getEmailList().put(uclaOfficialEmail, new Email(uclaOfficialEmail, newEmail.getAddress(), newEmail.getPrivacy()));
		
			logger.debug(padding + "Ready to update official email " + contactData);
			
			// sending update request
			WSResponse<Object> res = studentContactClient.updateStudentContactInfo(user.getUid(), contactData, null, null);
			
			logger.debug(padding + "updateEmailData:: Email data was updated " + res);
		}

		// if current official email has the same address as BOL email
		// and new email has type uclaNewStudentEmail
		// we need to make one more call to make new email official
//		Email emailBOL = this.getEmailForType(data, uclaBOLEmail);
//		if (StringUtils.hasText(data.getCurrent().getAddress())
//				&& emailBOL != null 
//				&& StringUtils.hasText(emailBOL.getAddress())
//				&& emailBOL.getAddress().equalsIgnoreCase(data.getCurrent().getAddress())
//				&& existingEmailList != null &&
//				!existingEmailList.containsKey((newEmail.getType()))) {

		// If added uclaOtherStudentEmail, need to make one more call to make email official
		if (officialEmailOtherFlag) {
			contactData.getEmailList().clear();
			contactData.getEmailList().put(uclaOfficialEmail, new Email(uclaOfficialEmail, newEmail.getAddress(), newEmail.getPrivacy()));

			logger.debug(padding + "Making extra call to make email official " + contactData);

			// sending update request
			WSResponse<Object> res = studentContactClient.updateStudentContactInfo(user.getUid(), contactData, null, null);
		}
		
		if (officialEmailChangedFlag) {
			// re-reading data
			data = this.getStudentEmailData(user);
	
			//check if privacy option was changed and update them in separate webservice call
			// privacy options cannot be updated by one webservice call with email changes
			if (newEmail.getType() != null && !newEmail.getPrivacy().equals(data.getCurrent().getPrivacy())) {

				logger.debug(padding + "Updating privacy options ");
				updateEmailPrivacyOptions(user, newEmail, data);
			
			}
		}
		
		return officialEmailChangedFlag;
	}
	
	
	@Override
	public void updateEmailPrivacyOptions(UserInfo user, Email newEmail, EmailData data) {
		if (!newEmail.getPrivacy().equals(data.getCurrent().getPrivacy())) {
			logger.debug(padding + "Updation emails privacy options to " + newEmail.getPrivacy().name());
//			logger.debug(padding + "email data  " + data);

			StudentContact contactData = new StudentContact();
			
			Map<String, Email> emails = new HashMap<String, Email>();
			
			// updating only official email
			for (Email em : data.getEmails().values()) {
				if (!newEmail.getPrivacy().equals(em.getPrivacy()) 
						&& uclaOfficialEmail.equalsIgnoreCase(em.getType())
//						&& !uclaOtherStudentEmail.equalsIgnoreCase(em.getType())
//						&& !uclaEmergencyContactEmail.equalsIgnoreCase(em.getType())
						) {
					em.setPrivacy(newEmail.getPrivacy());
					emails.put(em.getType(), em);
				}
			}
			logger.debug(padding + "email data to update  " + emails);
		
			contactData.setEmailList(emails);
			WSResponse<Object> res = studentContactClient.updateStudentContactInfo(user.getUid(), contactData, null, null);
			logger.debug(padding + "updateEmailPrivacyOptions: Email data was updated " + res);
		}
	}
	
	private Email generateBOLEmail(String logonId) {
		Email email = new Email();
		
		StringBuilder sb = new StringBuilder();
		sb.append(StringUtils.trimWhitespace(logonId));
		sb.append("@ucla.edu");
		
		email.setAddress(sb.toString());
		email.setType(uclaBOLEmail);
		
		return email;
	}
	
 	private Boolean isQualifiesForCTSEmail(String emailEligibility, Boolean preEditStatusFlag) {
		Boolean res = Boolean.FALSE;
		
		if (preEditStatusFlag ||
			(emailEligibility != null && emailEligibility.startsWith("BOL"))) {
			res = Boolean.TRUE;
		}
		
		logger.debug(padding + "isQualifiesForCTSEmail = " + res);
		return res;
	}
	
	private Boolean hasQualifiedOfficialEmail(Map<String, Email> emails) {
		Boolean res = Boolean.FALSE;
		if (emails != null && (emails.containsKey(uclaBOLEmail) || emails.containsKey(uclaLawSchoolEmail) || emails.containsKey(uclaAndersonSchoolEmail))) {
			res = Boolean.TRUE;
		}
		logger.debug(padding + "hasQualifiedOfficialEmail = " + res);
		return res;
	}
	
	private Boolean isQualifiesForOwnEmail(Collection<SIRInfo> sirs) {
		Boolean res = Boolean.FALSE;
		
		if (sirs != null) {
			for (SIRInfo sir : sirs) {
				if ("Y".equalsIgnoreCase(sir.getAuthorizedEmail())) {
					res = Boolean.TRUE;
					break;
				}
			}
		}
		
		logger.debug(padding + "isQualifiesForOwnEmail = " + res);
		return res;
	}
	
	private Boolean getPreEditSummerStatusFlag(String uid, String college) {
		Boolean res = Boolean.FALSE;
		
		if ("SM".equalsIgnoreCase(college)) {
			WSResponse<PreEditSummerData> data = preEditSummerClient.enrollmentSummerPreEdit(uid, GeneralUtils.getCurrentSummerTerm());
			res = "Passed".equalsIgnoreCase(data.getData().getStatusFlag());
		}
		
		return res;
	}
	
	private void activateBOLServices(String uid, String logonId, String emailBOL, Boolean preEditSummerStatusFLag) {
		String flag = "N";
		
		if (preEditSummerStatusFLag) {
			// N
		}
		else if (StringUtils.hasText(emailBOL)) {
			flag = "Y";
		}
		logger.debug(padding + "Activate BOL Services with flag " + flag);
		
		//update
		studentContactClient.updateStudentContactInfo(uid, null, logonId, flag);
	}

	private Email getEmailForType(EmailData data, String type) {
		Email email = null;
		if (data.getEmails() == null || !data.getEmails().containsKey(type)) {
			email = new Email();
			email.setType(type);
		}
		else {
			email = data.getEmails().get(type);
		}
		return email;
	}
}
