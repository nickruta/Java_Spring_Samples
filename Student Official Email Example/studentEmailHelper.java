package edu.ucla.dt.studentweb.mvc.service;

import java.util.Map;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import edu.ucla.dt.studentweb.mvc.security.UserInfo;
import edu.ucla.dt.studentweb.mvc.web.domain.EmailData;
import edu.ucla.dt.studentweb.mvc.web.form.admission.StudentContactForm;
import edu.ucla.dt.studentweb.svc.domain.Status;
import edu.ucla.dt.studentweb.svc.dto.Email;
import edu.ucla.dt.studentweb.svc.dto.EmergencyContacts;
import edu.ucla.dt.studentweb.svc.dto.StudentContact;

public interface StudentEmailHelper {
    public static final String[] PrivacyOptions = {"Release", "DoNotRelease"};

    public static final String uclaAndersonSchoolEmail = "uclaAndersonSchoolEmail";
    public static final String uclaBOLEmail = "uclaBOLEmail";
    public static final String uclaLawSchoolEmail = "uclaLawSchoolEmail";
    public static final String uclaOfficialEmail = "uclaOfficialEmail";
    public static final String uclaOtherStudentEmail = "uclaOtherStudentEmail";
    public static final String uclaEmergencyContactEmail = "uclaEmergencyContactEmail";
    public static final String applicantEmail = "applicantEmail";
    
    public StudentContact getStudentContact(UserInfo user);
    
	public Email getCurrentEmail(EmailData data);
	public EmailData getStudentEmailData(UserInfo user, StudentContact stContact);
	public EmailData getStudentEmailData(UserInfo user);
	public Map<String, Email> getEmailChoices(UserInfo user, EmailData data, Boolean useAdmissionEmailFlag);
	public void updateEmailData(UserInfo user, 
								Email email, 
								EmailData data, 
								Map<String, Email> emailChoices);

	public void updateApplicantContactData(UserInfo user, StudentContactForm contactForm);

	public void updateEmailPrivacyOptions(UserInfo user, 
											Email newEmail, 
											EmailData data);
	
	public StudentContact prepareEmergencyContactData(EmergencyContacts contact, EmailData data);
	
	public Email getOfficialEmail(UserInfo user);
	public Email getOfficialEmail(StudentContact res);
	
	@Transactional(value = "db2", readOnly = true)
	public Boolean getEmailVerifiedFlag(String uid);
	
	@Transactional(value = "db2", propagation=Propagation.REQUIRED)
	public void updateEmailVerifyFlag(String uid);
	
	public void prepareEmailData(UserInfo user, Model model);

	public void updateEmailVerifyFlag(UserInfo user, Model model);
	public Status updateEmailVerifyFlag(UserInfo user);
	
}
