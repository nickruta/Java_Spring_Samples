package edu.ucla.dt.studentweb.mvc.web.form.admission;

import java.util.Date;

import javax.validation.constraints.NotNull;

import org.codehaus.plexus.util.StringUtils;

import edu.ucla.dt.studentweb.mvc.web.domain.PersonalInfo;
import edu.ucla.dt.studentweb.mvc.web.domain.ResidencyStatus;
import edu.ucla.dt.studentweb.mvc.web.domain.TaxInfo;
import edu.ucla.dt.studentweb.svc.dto.AdmissionValidationGroups.Certify;
import edu.ucla.dt.studentweb.svc.dto.AdmissionValidationGroups.SLRParentIDStep;
import edu.ucla.dt.studentweb.svc.dto.AdmissionValidationGroups.SLRParentsDivorced;
import edu.ucla.dt.studentweb.svc.dto.AdmissionValidationGroups.SLRStep1;
import edu.ucla.dt.studentweb.svc.dto.AdmissionValidationGroups.SLRStep2;
import edu.ucla.dt.studentweb.svc.dto.AdmissionValidationGroups.SLRStep3;
import edu.ucla.dt.studentweb.svc.dto.FinancialSupportSource;
import edu.ucla.dt.studentweb.svc.dto.validation.DateNotBefore;
import edu.ucla.dt.studentweb.svc.dto.validation.DateNotBefore1940;

@DateNotBefore.List({
		@DateNotBefore( message = "{validation.slr.parentLivingWithDates2}", groups = { SLRParentsDivorced.class}, 
					    startDate = "parentLivingWithFromDate1", endDate = "parentLivingWithToDate1"),
		
		@DateNotBefore( message = "{validation.slr.ResidedOtherParentDates2}", groups = { SLRParentsDivorced.class}, 
					    startDate = "residedOtherParentFromDate1", endDate = "residedOtherParentToDate1"),
		
		@DateNotBefore( message = "{validation.slr.collegeStartEndDate}", groups = { SLRStep2.class}, 
					    startDate = "college1StartDate", endDate = "college1EndDate"),

		@DateNotBefore( message = "{validation.slr.collegeStartEndDate}", groups = { SLRStep2.class}, 
					    startDate = "college2StartDate", endDate = "college2EndDate"),

		@DateNotBefore( message = "{validation.slr.collegeStartEndDate}", groups = { SLRStep2.class}, 
						    startDate = "college3StartDate", endDate = "college3EndDate"),
		
		@DateNotBefore( message = "{validation.slr.profLicenseStartEndDate}", groups = { SLRStep3.class}, 
						    startDate = "profLicenseStartDate", endDate = "profLicenseEndDate")

})

@DateNotBefore1940.List({
	
	@DateNotBefore1940(  message = "{validation.slr.residencyStatus.student.dataAwarded}", dateFields = "residedOtherParentFromDate1", groups = { SLRParentsDivorced.class} ),
	@DateNotBefore1940(  message = "{validation.slr.residencyStatus.student.dataAwarded}", dateFields = "residedOtherParentToDate1", groups = { SLRParentsDivorced.class} ),
	@DateNotBefore1940(  message = "{validation.slr.residencyStatus.student.dataAwarded}", dateFields = "parentLivingWithFromDate1", groups = { SLRParentsDivorced.class} ),
	@DateNotBefore1940(  message = "{validation.slr.residencyStatus.student.dataAwarded}", dateFields = "parentLivingWithToDate1", groups = { SLRParentsDivorced.class} ),
	@DateNotBefore1940(  message = "{validation.slr.residencyStatus.student.dataAwarded}", dateFields = "parentLivingWithFromDate2", groups = { SLRParentsDivorced.class} ),
	@DateNotBefore1940(  message = "{validation.slr.residencyStatus.student.dataAwarded}", dateFields = "parentLivingWithToDate2", groups = { SLRParentsDivorced.class} ),
	@DateNotBefore1940(  message = "{validation.slr.residencyStatus.student.dataAwarded}", dateFields = "residedOtherParentFromDate2", groups = { SLRParentsDivorced.class} ),
	@DateNotBefore1940(  message = "{validation.slr.residencyStatus.student.dataAwarded}", dateFields = "residedOtherParentToDate2", groups = { SLRParentsDivorced.class} ),
	
	@DateNotBefore1940(  message = "{validation.slr.residencyStatus.student.dataAwarded}", dateFields = "college1StartDate", groups = { SLRStep2.class} ),
	@DateNotBefore1940(  message = "{validation.slr.residencyStatus.student.dataAwarded}", dateFields = "college1EndDate", groups = { SLRStep2.class} ),
	@DateNotBefore1940(  message = "{validation.slr.residencyStatus.student.dataAwarded}", dateFields = "college2StartDate", groups = { SLRStep2.class} ),
	@DateNotBefore1940(  message = "{validation.slr.residencyStatus.student.dataAwarded}", dateFields = "college2EndDate", groups = { SLRStep2.class} ),
	@DateNotBefore1940(  message = "{validation.slr.residencyStatus.student.dataAwarded}", dateFields = "college3StartDate", groups = { SLRStep2.class} ),
	@DateNotBefore1940(  message = "{validation.slr.residencyStatus.student.dataAwarded}", dateFields = "college3EndDate", groups = { SLRStep2.class} ),
	@DateNotBefore1940(  message = "{validation.slr.residencyStatus.student.dataAwarded}", dateFields = "profLicenseStartDate", groups = { SLRStep3.class} ),
	@DateNotBefore1940(  message = "{validation.slr.residencyStatus.student.dataAwarded}", dateFields = "profLicenseEndDate", groups = { SLRStep3.class} ),
	@DateNotBefore1940(  message = "{validation.slr.residencyStatus.student.dataAwarded}", dateFields = "homeStateStartDate", groups = { SLRStep3.class} ),
	@DateNotBefore1940(  message = "{validation.slr.residencyStatus.student.dataAwarded}", dateFields = "maritalStatusDate", groups = { SLRStep3.class} ),	
})
public class SLRData {
	
	
	
	private String residencyCompletedStatusCode;			//RES_CMPL_STAT_CD
	private String residencyTuitionUpdateCode;				//RES_TUIT_UPD_CD
	private String SIRStatusCode; 							//sir_stat_cd
	private String lastUpdatedTimestamp;					//LAST_UPD_TMSTMP
	
	
	//SLR Parent ID Page
	@NotNull(message = "{validation.slr.motherInfoAvailable}", groups = { SLRParentIDStep.class })
	private Boolean motherInfoAvailable;  				//par_info_aval_cd

	@NotNull(message = "{validation.slr.fatherInfoAvailable}", groups = { SLRParentIDStep.class })
	private Boolean fatherInfoAvailable;  				//par_info_aval_cd

	@NotNull(message = "{validation.slr.fatherDeceasedFlag}", groups = { SLRParentIDStep.class })
	private Boolean fatherDeceasedFlag;						//FATH_DECEASED_FL
	
	@NotNull(message = "{validation.slr.motherDeceasedFlag}", groups = { SLRParentIDStep.class })
	private Boolean motherDeceasedFlag;  					//MOTH_DECEASED_FL
    
	@NotNull(message = "{validation.slr.parentsDivorced}", groups = { SLRParentIDStep.class })
	private Boolean parentsDivorced;				// par_dvrc_sep_fl    
    

	//SLR Parents Divorced Page
	@NotNull(message = "{validation.slr.parentLivingWith}", groups = { SLRParentsDivorced.class })
	private Boolean parentLivingWith;			// live_with_par_cd
	private Date parentLivingWithFromDate1;	// live_par_strt_dt1
	private Date parentLivingWithToDate1;		// live_par_end_dt1   
	private Boolean resAddrSameAsPermAddr1;		// live_addr_perm_fl1
    private Boolean resAddrInCA1;				// live_addr_ca_fl1
    private String parentLivingWithFromDate2;		// live_par_end_dt2
    private String parentLivingWithToDate2;		// live_par_end_dt2   
    private String resAddrSameAsPermAddr2;		// live_addr_perm_fl2
    private String resAddrInCA2;				// live_addr_ca_fl1
	@NotNull(message = "{validation.slr.parentLivingWith}", groups = { SLRParentsDivorced.class })
    private Boolean residedWithOtherParent;		// WITH_OTH_PAR_FL
    private Date residedOtherParentFromDate1;		// oth_par_strt_dt1
    private Date residedOtherParentToDate1;		// oth_par_end_dt1
    private Boolean resOPAddrSameAsPermAddr1;		// oth_addr_perm_fl1
    private Boolean resOPAddrInCA1; 					// oth_addr_ca_fl1

    private String residedOtherParentFromDate2;		// oth_par_strt_dt2
    private String residedOtherParentToDate2;		// oth_par_end_dt2
    private String resOPAddrSameAsPermAddr2;		// oth_addr_perm_fl2
    private String resOPAddrInCA2; 					// oth_addr_ca_fl2
	

	private String ursaErrFieldNm;
	private String ursaErrText;	
	
	//remove capital
	private String MothersRelationship;					// moth_rel_cd
	private String FathersRelationship;				//fath_rel_cd	
							
	
	//SLR Page 1
	private ResidencyStatus residencyStatus; 
	
    @NotNull(message = "{validation.slr.usVetInCaFl}", groups = { SLRStep1.class })
	private Boolean usVetInCaFl;
    
	@NotNull(message = "{validation.slr.cAClaimedAsDependent}", groups = { SLRStep1.class })
	private Boolean cAClaimedAsDependent;				// ca_dep_claim_fl
	@NotNull(message = "{validation.slr.financiallyIndependentFl}", groups = { SLRStep1.class })
	private Boolean financiallyIndependentFl;  				//fin_indep_fl
	
	@NotNull(message = "{validation.slr.cA_HSGraduateFlag}", groups = { SLRStep1.class })
	private Boolean cA_HSGraduateFlag;           			//ca_hs_grad_fl
	
	 //SLR Page 2 - 1
	@NotNull(message = "{validation.slr.caResidentAbsent}", groups = { SLRStep2.class })
	private Boolean caResidentAbsent;					// CA_RES_ABS_FL
	private String caResidentAbsentExpl;    // Trim$(rs!ca_res_abs_rea_tx1) & Trim$(rs!ca_res_abs_rea_tx2)

	 //Check box 2 years ago
	private FinancialSupportSource twoYearsAgoSupport;
    
	//  Check box last year
	private FinancialSupportSource lastYearSupport;
    
    //  Check box this year
	private FinancialSupportSource thisYearSupport;
	
    //SLR Page Two - 2
    private TaxInfo taxInfo;    
   
    @NotNull(message = "{validation.slr.othStateReqforBenefitsLastYear}", groups = { SLRStep2.class })
    private Boolean othStateReqforBenefitsLastYear;	// nonca_finaid_ly_fl
    
    @NotNull(message = "{validation.slr.othStateReqforBenefitsThisYear}", groups = { SLRStep2.class })
    private Boolean othStateReqforBenefitsThisYear;	// nonca_finaid_ty_fl
    
    private String othStateReqforBenefitsText;	//nonca_finaid_txt
    
    @NotNull(message = "{validation.slr.cAEmployeeLastYear}", groups = { SLRStep2.class })
    private Boolean cAEmployeeLastYear;				// ca_emp_ly_fl
    @NotNull(message = "{validation.slr.cAEmployeeThisYear}", groups = { SLRStep2.class })
    private Boolean cAEmployeeThisYear;			// ca_emp_ty_fl
   
    @NotNull(message = "{validation.slr.othStateEmployeeLastYear}", groups = { SLRStep2.class })
    private Boolean othStateEmployeeLastYear;		// nonca_emp_ly_fl
    @NotNull(message = "{validation.slr.othStateEmployeeThisYear}", groups = { SLRStep2.class })
    private Boolean othStateEmployeeThisYear;	// nonca_emp_ty_fl
   
    
    @NotNull(message = "{validation.slr.attendedCollege}", groups = { SLRStep2.class })
    private Boolean attendedCollege;					// att_coll_fl
    private String college1Name;					// coll_nm1
    private String college1State;					// coll_state_cd1
    private Date college1StartDate;				// coll_strt_dt1
    private Date college1EndDate;					// coll_end_dt1
    private String college2Name;					// coll_nm2
    private String college2State;					// coll_state_cd2
    private Date college2StartDate;				// coll_strt_dt2
    private Date college2EndDate;					// coll_end_dt2
    private String college3Name;					// coll_nm3
    private String college3State;					// coll_state_cd3
    private Date college3StartDate;				// coll_strt_dt3
    private Date college3EndDate;					// coll_end_dt3
    
    @NotNull(message = "{validation.slr.uSMilStudent}", groups = { SLRStep2.class })
    private Boolean uSMilStudent;					// stu_mil_duty_fl
    private String uSMilStateStudent;				// stu_mil_state_cd
    
    @NotNull(message = "{validation.slr.uSMilSpouse}", groups = { SLRStep2.class })
    private Boolean uSMilSpouse;						// sps_mil_duty_fl
    private String uSMilStateSpouse;				// sps_mil_state_cd
    
    @NotNull(message = "{validation.slr.selectiveServiceReg}", groups = { SLRStep2.class })
    private Boolean selectiveServiceReg;				// sel_svc_fl
    private String selectiveServiceRegState;		// sel_svc_state_cd
    
    // SLR Page 2 End

    // SLR Page 3 Start
    PersonalInfo personalInfo;
    
    @NotNull(message = "{validation.slr.profLicense}", groups = { SLRStep3.class })
    private Boolean profLicense;					// prof_lic_fl
    private Date profLicenseStartDate;		// prof_lic_strt_dt
    private Date profLicenseEndDate;			// prof_lic_end_dt
    private String profLicenseState;			// prof_lic_state_cd    
    

    @NotNull(message = "{validation.slr.uSVet}", groups = { SLRStep3.class })
    private Boolean uSVet;						// vet_fl
    @NotNull(message = "{validation.slr.wardOfCourt}", groups = { SLRStep3.class })
    private Boolean wardOfCourt;					// ward_court_fl
    @NotNull(message = "{validation.slr.haveLegalDependents}", groups = { SLRStep3.class })
    private Boolean haveLegalDependents;			// non_sps_dep_fl
    @NotNull(message = "{validation.slr.employedGradStudent}", groups = { SLRStep3.class })
    private Boolean employedGradStudent;			// grad_stu_49_emp_fl    
    
    private Boolean parentsClaimedThisYear;		// ca_exmp_thisyr_fl
    private Boolean parentsClaimedLastYear;		// ca_exmp_lastyr_fl
    private Boolean parentsClaimed2YearsAgo;		// ca_exmp_2yrsago_fl
    
    @NotNull(message = "{validation.slr.maritalStatus}", groups = { SLRStep3.class })
    private String maritalStatus;				// marr_stat_cd
    private Date maritalStatusDate;			// marr_stat_dt
    private String maritalStatusState;			// marr_stat_state_cd
    
    @NotNull(message = "{validation.slr.homeState}", groups = { SLRStep3.class })
    private String homeState;					// perm_home_state_cd
    @NotNull(message = "{validation.slr.homeStateStartDate}", groups = { SLRStep3.class })
    private Date homeStateStartDate;			// perm_home_strt_dt
    @NotNull(message = "{validation.slr.cAAfterEducation}", groups = { SLRStep3.class })
    private Boolean cAAfterEducation;			// ca_aft_edu_fl  
    
    // ends SLR Page 3
  
   private String SLRStatusCode;				   //slr_stat_cd
   private String residencyAge;
   private String birthDate;
    
       
    //certify
    @NotNull(message = "{validation.slr.studentAgreement}", groups = { Certify.class })
    private String studentAgreement;
    
    @NotNull(message = "{validation.slr.infoRelease}", groups = { Certify.class })
    private Boolean infoRelease;
        
	
	public SLRData() {
		super();
		
		personalInfo = new PersonalInfo();
		taxInfo = new TaxInfo();
		residencyStatus = new ResidencyStatus();
 
		twoYearsAgoSupport = new FinancialSupportSource();
		lastYearSupport = new FinancialSupportSource();
		thisYearSupport = new FinancialSupportSource();
	}
	
	public void init() {
		twoYearsAgoSupport.init();
		lastYearSupport.init();
		thisYearSupport.init();
	}


	/**
	 * @return the profLicenseStartDate
	 */
	public Date getProfLicenseStartDate() {
		return profLicenseStartDate;
	}





	/**
	 * @param profLicenseStartDate the profLicenseStartDate to set
	 */
	public void setProfLicenseStartDate(Date profLicenseStartDate) {
		this.profLicenseStartDate = profLicenseStartDate;
	}





	/**
	 * @return the profLicenseEndDate
	 */
	public Date getProfLicenseEndDate() {
		return profLicenseEndDate;
	}





	/**
	 * @param profLicenseEndDate the profLicenseEndDate to set
	 */
	public void setProfLicenseEndDate(Date profLicenseEndDate) {
		this.profLicenseEndDate = profLicenseEndDate;
	}





	/**
	 * @return the profLicenseState
	 */
	public String getProfLicenseState() {
		return profLicenseState;
	}


	/**
	 * @param profLicenseState the profLicenseState to set
	 */
	public void setProfLicenseState(String profLicenseState) {
		this.profLicenseState = StringUtils.trim(profLicenseState);
	}




	/**
	 * @return the maritalStatus
	 */
	public String getMaritalStatus() {
		return maritalStatus;
	}


	/**
	 * @param maritalStatus the maritalStatus to set
	 */
	public void setMaritalStatus(String maritalStatus) {
		this.maritalStatus = StringUtils.trim(maritalStatus);
	}


	

	/**
	 * @return the maritalStatusState
	 */
	public String getMaritalStatusState() {
		return maritalStatusState;
	}


	/**
	 * @param maritalStatusState the maritalStatusState to set
	 */
	public void setMaritalStatusState(String maritalStatusState) {
		this.maritalStatusState = StringUtils.trim(maritalStatusState);
	}


	/**
	 * @return the homeState
	 */
	public String getHomeState() {
		return homeState;
	}


	/**
	 * @param homeState the homeState to set
	 */
	public void setHomeState(String homeState) {
		this.homeState = StringUtils.trim(homeState);
	}


	
	/**
	 * @return the maritalStatusDate
	 */
	public Date getMaritalStatusDate() {
		return maritalStatusDate;
	}





	/**
	 * @param maritalStatusDate the maritalStatusDate to set
	 */
	public void setMaritalStatusDate(Date maritalStatusDate) {
		this.maritalStatusDate = maritalStatusDate;
	}





	/**
	 * @return the homeStateStartDate
	 */
	public Date getHomeStateStartDate() {
		return homeStateStartDate;
	}





	/**
	 * @param homeStateStartDate the homeStateStartDate to set
	 */
	public void setHomeStateStartDate(Date homeStateStartDate) {
		this.homeStateStartDate = homeStateStartDate;
	}





	/**
	 * @return the sLRStatusCode
	 */
	public String getSLRStatusCode() {
		return SLRStatusCode;
	}


	/**
	 * @param sLRStatusCode the sLRStatusCode to set
	 */
	public void setSLRStatusCode(String sLRStatusCode) {
		SLRStatusCode = StringUtils.trim(sLRStatusCode);
	}




	/**
	 * @return the studentAgreement
	 */
	public String getStudentAgreement() {
		return studentAgreement;
	}

	/**
	 * @param studentAgreement the studentAgreement to set
	 */
	public void setStudentAgreement(String studentAgreement) {
		this.studentAgreement = studentAgreement;
	}

	/**
	 * @return the infoRelease
	 */
	public Boolean getInfoRelease() {
		return infoRelease;
	}

	/**
	 * @param infoRelease the infoRelease to set
	 */
	public void setInfoRelease(Boolean infoRelease) {
		this.infoRelease = infoRelease;
	}


	/**
	 * @return the profLicense
	 */
	public Boolean getProfLicense() {
		return profLicense;
	}



	/**
	 * @return the wardOfCourt
	 */
	public Boolean getWardOfCourt() {
		return wardOfCourt;
	}


	/**
	 * @return the haveLegalDependents
	 */
	public Boolean getHaveLegalDependents() {
		return haveLegalDependents;
	}


	/**
	 * @return the employedGradStudent
	 */
	public Boolean getEmployedGradStudent() {
		return employedGradStudent;
	}


	/**
	 * @return the parentsClaimedThisYear
	 */
	public Boolean getParentsClaimedThisYear() {
		return parentsClaimedThisYear;
	}


	/**
	 * @return the parentsClaimedLastYear
	 */
	public Boolean getParentsClaimedLastYear() {
		return parentsClaimedLastYear;
	}


	/**
	 * @return the parentsClaimed2YearsAgo
	 */
	public Boolean getParentsClaimed2YearsAgo() {
		return parentsClaimed2YearsAgo;
	}


	/**
	 * @return the othStateEmployeeLastYear
	 */
	public Boolean getOthStateEmployeeLastYear() {
		return othStateEmployeeLastYear;
	}


	/**
	 * @return the othStateEmployeeThisYear
	 */
	public Boolean getOthStateEmployeeThisYear() {
		return othStateEmployeeThisYear;
	}


	/**
	 * @return the attendedCollege
	 */
	public Boolean getAttendedCollege() {
		return attendedCollege;
	}


	/**
	 * @return the selectiveServiceReg
	 */
	public Boolean getSelectiveServiceReg() {
		return selectiveServiceReg;
	}


	public String getResAddrInCA2() {
		return resAddrInCA2;
	}
	public void setResAddrInCA2(String resAddrInCA2) {
		this.resAddrInCA2 = StringUtils.trim(resAddrInCA2);
	}
	public String getResAddrSameAsPermAddr2() {
		return resAddrSameAsPermAddr2;
	}
	public void setResAddrSameAsPermAddr2(String resAddrSameAsPermAddr2) {
		this.resAddrSameAsPermAddr2 = StringUtils.trim(resAddrSameAsPermAddr2);
	}
	public String getParentLivingWithFromDate2() {
		return parentLivingWithFromDate2;
	}
	public void setParentLivingWithFromDate2(String parentLivingWithFromDate2) {
		this.parentLivingWithFromDate2 = StringUtils.trim(parentLivingWithFromDate2);
	}
	public String getParentLivingWithToDate2() {
		return parentLivingWithToDate2;
	}
	public void setParentLivingWithToDate2(String parentLivingWithToDate2) {
		this.parentLivingWithToDate2 = StringUtils.trim(parentLivingWithToDate2);
	}
	public String getResidedOtherParentFromDate2() {
		return residedOtherParentFromDate2;
	}
	public void setResidedOtherParentFromDate2(String residedOtherParentFromDate2) {
		this.residedOtherParentFromDate2 = StringUtils.trim(residedOtherParentFromDate2);
	}
	public String getResidedOtherParentToDate2() {
		return residedOtherParentToDate2;
	}
	public void setResidedOtherParentToDate2(String residedOtherParentToDate2) {
		this.residedOtherParentToDate2 = StringUtils.trim(residedOtherParentToDate2);
	}
	public Boolean getResOPAddrSameAsPermAddr1() {
		return resOPAddrSameAsPermAddr1;
	}
	public void setResOPAddrSameAsPermAddr1(Boolean resOPAddrSameAsPermAddr1) {
		this.resOPAddrSameAsPermAddr1 = resOPAddrSameAsPermAddr1;
	}
	public String getResOPAddrSameAsPermAddr2() {
		return resOPAddrSameAsPermAddr2;
	}
	public void setResOPAddrSameAsPermAddr2(String resOPAddrSameAsPermAddr2) {
		this.resOPAddrSameAsPermAddr2 = StringUtils.trim(resOPAddrSameAsPermAddr2);
	}
	public String getResOPAddrInCA2() {
		return resOPAddrInCA2;
	}
	public void setResOPAddrInCA2(String resOPAddrInCA2) {
		this.resOPAddrInCA2 = StringUtils.trim( resOPAddrInCA2);
	}
	public Date getParentLivingWithToDate1() {
		return parentLivingWithToDate1;
	}
	public void setParentLivingWithToDate1(Date parentLivingWithToDate1) {
		this.parentLivingWithToDate1 = parentLivingWithToDate1;
	}
	public Boolean getResAddrSameAsPermAddr1() {
		return resAddrSameAsPermAddr1;
	}
	public void setResAddrSameAsPermAddr1(Boolean resAddrSameAsPermAddr1) {
		this.resAddrSameAsPermAddr1 = resAddrSameAsPermAddr1;
	}
	public Boolean getResAddrInCA1() {
		return resAddrInCA1;
	}
	public void setResAddrInCA1(Boolean resAddrInCA1) {
		this.resAddrInCA1 = resAddrInCA1;
	}
	public Date getResidedOtherParentFromDate1() {
		return residedOtherParentFromDate1;
	}
	public void setResidedOtherParentFromDate1(Date residedOtherParentFromDate1) {
		this.residedOtherParentFromDate1 = residedOtherParentFromDate1;
	}
	public Date getResidedOtherParentToDate1() {
		return residedOtherParentToDate1;
	}
	public void setResidedOtherParentToDate1(Date residedOtherParentToDate1) {
		this.residedOtherParentToDate1 = residedOtherParentToDate1;
	}
	public Boolean getResOPAddrInCA1() {
		return resOPAddrInCA1;
	}
	public void setResOPAddrInCA1(Boolean resOPAddrInCA1) {
		this.resOPAddrInCA1 = resOPAddrInCA1;
	}
	public void setParentLivingWithFromDate1(Date parentLivingWithFromDate1) {
		this.parentLivingWithFromDate1 = parentLivingWithFromDate1;
	}
	public Date getParentLivingWithFromDate1() {
		return parentLivingWithFromDate1;
	}
	public Boolean getResidedWithOtherParent() {
		return residedWithOtherParent;
	}
	public void setResidedWithOtherParent(Boolean residedWithOtherParent) {
		this.residedWithOtherParent = residedWithOtherParent;
	}
	public Boolean getMotherInfoAvailable() {
		return motherInfoAvailable;
	}
	public void setMotherInfoAvailable(Boolean motherInfoAvailable) {
		this.motherInfoAvailable = motherInfoAvailable;
	}
	public Boolean getFatherInfoAvailable() {
		return fatherInfoAvailable;
	}
	public void setFatherInfoAvailable(Boolean fatherInfoAvailable) {
		this.fatherInfoAvailable = fatherInfoAvailable;
	}

	public String getResidencyCompletedStatusCode() {
		return residencyCompletedStatusCode;
	}
	public void setResidencyCompletedStatusCode(String residencyCompletedStatusCode) {
		this.residencyCompletedStatusCode = StringUtils.trim(residencyCompletedStatusCode);
	}
	public String getResidencyTuitionUpdateCode() {
		return residencyTuitionUpdateCode;
	}
	public void setResidencyTuitionUpdateCode(String residencyTuitionUpdateCode) {
		this.residencyTuitionUpdateCode = StringUtils.trim(residencyTuitionUpdateCode);
	}
	public String getSIRStatusCode() {
		return SIRStatusCode;
	}
	public void setSIRStatusCode(String sIRStatusCode) {
		SIRStatusCode = StringUtils.trim(sIRStatusCode);
	}
	public String getLastUpdatedTimestamp() {
		return lastUpdatedTimestamp;
	}
	public void setLastUpdatedTimestamp(String lastUpdatedTimestamp) {
		this.lastUpdatedTimestamp = lastUpdatedTimestamp;
	}
	
/**
	 * @return the cA_HSGraduateFlag
	 */
	public Boolean getcA_HSGraduateFlag() {
		return cA_HSGraduateFlag;
	}


	/**
	 * @param cA_HSGraduateFlag the cA_HSGraduateFlag to set
	 */
	public void setcA_HSGraduateFlag(Boolean cA_HSGraduateFlag) {
		this.cA_HSGraduateFlag = cA_HSGraduateFlag;
	}



	public Boolean getFatherDeceasedFlag() {
		return fatherDeceasedFlag;
	}
	public void setFatherDeceasedFlag(Boolean fatherDeceasedFlag) {
		this.fatherDeceasedFlag = fatherDeceasedFlag;
	}
	public Boolean getMotherDeceasedFlag() {
		return motherDeceasedFlag;
	}
	public void setMotherDeceasedFlag(Boolean motherDeceasedFlag) {
		this.motherDeceasedFlag = motherDeceasedFlag;
	}
	
	public String getUrsaErrFieldNm() {
		return ursaErrFieldNm;
	}
	public void setUrsaErrFieldNm(String ursaErrFieldNm) {
		this.ursaErrFieldNm = StringUtils.trim(ursaErrFieldNm);
	}
	public String getUrsaErrText() {
		return ursaErrText;
	}
	public void setUrsaErrText(String ursaErrText) {
		this.ursaErrText = StringUtils.trim(ursaErrText);
	}
	public String getMothersRelationship() {
		return MothersRelationship;
	}
	public void setMothersRelationship(String mothersRelationship) {
		MothersRelationship = StringUtils.trim(mothersRelationship);
	}
	public String getFathersRelationship() {
		return FathersRelationship;
	}
	public void setFathersRelationship(String fathersRelationship) {
		FathersRelationship = StringUtils.trim(fathersRelationship);
	}
	
	

	/**
 * @return the othStateReqforBenefitsText
 */
public String getOthStateReqforBenefitsText() {
	return othStateReqforBenefitsText;
}


/**
 * @param othStateReqforBenefitsText the othStateReqforBenefitsText to set
 */
public void setOthStateReqforBenefitsText(String othStateReqforBenefitsText) {
	this.othStateReqforBenefitsText = StringUtils.trim(othStateReqforBenefitsText);
}



	public Boolean getParentsDivorced() {
		return parentsDivorced;
	}
	public void setParentsDivorced(Boolean parentsDivorced) {
		this.parentsDivorced = parentsDivorced;
	}
	public Boolean getParentLivingWith() {
		return parentLivingWith;
	}
	public void setParentLivingWith(Boolean parentLivingWith) {
		this.parentLivingWith = parentLivingWith;
	}
	
	public String getResidencyAge() {
		return residencyAge;
	}
	public void setResidencyAge(String residencyAge) {
		this.residencyAge = StringUtils.trim(residencyAge);
	}
	public String getBirthDate() {
		return birthDate;
	}
	public void setBirthDate(String birthDate) {
		this.birthDate = birthDate;
	}


	// SLR Page 2 getter / Setter
	

	/**
	 * @return the uSMilStudent
	 */
	public Boolean getuSMilStudent() {
		return uSMilStudent;
	}


	/**
	 * @return the caResidentAbsent
	 */
	public Boolean getCaResidentAbsent() {
		return caResidentAbsent;
	}


	/**
	 * @param caResidentAbsent the caResidentAbsent to set
	 */
	public void setCaResidentAbsent(Boolean caResidentAbsent) {
		this.caResidentAbsent = caResidentAbsent;
	}


	/**
	 * @return the caResidentAbsentExpl
	 */
	public String getCaResidentAbsentExpl() {
		return caResidentAbsentExpl;
	}


	/**
	 * @param caResidentAbsentExpl the caResidentAbsentExpl to set
	 */
	public void setCaResidentAbsentExpl(String caResidentAbsentExpl) {
		this.caResidentAbsentExpl = StringUtils.trim(caResidentAbsentExpl);
	}


	/**
	 * @param uSMilStudent the uSMilStudent to set
	 */
	public void setuSMilStudent(Boolean uSMilStudent) {
		this.uSMilStudent = uSMilStudent;
	}


	/**
	 * @return the uSMilSpouse
	 */
	public Boolean getuSMilSpouse() {
		return uSMilSpouse;
	}


	/**
	 * @param uSMilSpouse the uSMilSpouse to set
	 */
	public void setuSMilSpouse(Boolean uSMilSpouse) {
		this.uSMilSpouse = uSMilSpouse;
	}


	/**
	 * @param attendedCollege the attendedCollege to set
	 */
	public void setAttendedCollege(Boolean attendedCollege) {
		this.attendedCollege = attendedCollege;
	}


	/**
	 * @param selectiveServiceReg the selectiveServiceReg to set
	 */
	public void setSelectiveServiceReg(Boolean selectiveServiceReg) {
		this.selectiveServiceReg = selectiveServiceReg;
	}


	/**
	 * @return the cAEmployeeLastYear
	 */
	public Boolean getcAEmployeeLastYear() {
		return cAEmployeeLastYear;
	}


	/**
	 * @param cAEmployeeLastYear the cAEmployeeLastYear to set
	 */
	public void setcAEmployeeLastYear(Boolean cAEmployeeLastYear) {
		this.cAEmployeeLastYear = cAEmployeeLastYear;
	}


	/**
	 * @return the cAEmployeeThisYear
	 */
	public Boolean getcAEmployeeThisYear() {
		return cAEmployeeThisYear;
	}


	/**
	 * @param cAEmployeeThisYear the cAEmployeeThisYear to set
	 */
	public void setcAEmployeeThisYear(Boolean cAEmployeeThisYear) {
		this.cAEmployeeThisYear = cAEmployeeThisYear;
	}


	/**
	 * @param othStateEmployeeLastYear the othStateEmployeeLastYear to set
	 */
	public void setOthStateEmployeeLastYear(Boolean othStateEmployeeLastYear) {
		this.othStateEmployeeLastYear = othStateEmployeeLastYear;
	}


	/**
	 * @param othStateEmployeeThisYear the othStateEmployeeThisYear to set
	 */
	public void setOthStateEmployeeThisYear(Boolean othStateEmployeeThisYear) {
		this.othStateEmployeeThisYear = othStateEmployeeThisYear;
	}



	/**
	 * @return the cAClaimedAsDependent
	 */
	public Boolean getcAClaimedAsDependent() {
		return cAClaimedAsDependent;
	}


	/**
	 * @param cAClaimedAsDependent the cAClaimedAsDependent to set
	 */
	public void setcAClaimedAsDependent(Boolean cAClaimedAsDependent) {
		this.cAClaimedAsDependent = cAClaimedAsDependent;
	}


	/**
	 * @return the financiallyIndependentFl
	 */
	public Boolean getFinanciallyIndependentFl() {
		return financiallyIndependentFl;
	}


	/**
	 * @param financiallyIndependentFl the financiallyIndependentFl to set
	 */
	public void setFinanciallyIndependentFl(Boolean financiallyIndependentFl) {
		this.financiallyIndependentFl = financiallyIndependentFl;
	}


	/**
	 * @return the uSVet
	 */
	public Boolean getuSVet() {
		return uSVet;
	}


	/**
	 * @param uSVet the uSVet to set
	 */
	public void setuSVet(Boolean uSVet) {
		this.uSVet = uSVet;
	}


	/**
	 * @return the cAAfterEducation
	 */
	public Boolean getcAAfterEducation() {
		return cAAfterEducation;
	}


	/**
	 * @param cAAfterEducation the cAAfterEducation to set
	 */
	public void setcAAfterEducation(Boolean cAAfterEducation) {
		this.cAAfterEducation = cAAfterEducation;
	}


	/**
	 * @param profLicense the profLicense to set
	 */
	public void setProfLicense(Boolean profLicense) {
		this.profLicense = profLicense;
	}


	/**
	 * @param wardOfCourt the wardOfCourt to set
	 */
	public void setWardOfCourt(Boolean wardOfCourt) {
		this.wardOfCourt = wardOfCourt;
	}


	/**
	 * @param haveLegalDependents the haveLegalDependents to set
	 */
	public void setHaveLegalDependents(Boolean haveLegalDependents) {
		this.haveLegalDependents = haveLegalDependents;
	}


	/**
	 * @param employedGradStudent the employedGradStudent to set
	 */
	public void setEmployedGradStudent(Boolean employedGradStudent) {
		this.employedGradStudent = employedGradStudent;
	}


	/**
	 * @param parentsClaimedThisYear the parentsClaimedThisYear to set
	 */
	public void setParentsClaimedThisYear(Boolean parentsClaimedThisYear) {
		this.parentsClaimedThisYear = parentsClaimedThisYear;
	}


	/**
	 * @param parentsClaimedLastYear the parentsClaimedLastYear to set
	 */
	public void setParentsClaimedLastYear(Boolean parentsClaimedLastYear) {
		this.parentsClaimedLastYear = parentsClaimedLastYear;
	}


	/**
	 * @param parentsClaimed2YearsAgo the parentsClaimed2YearsAgo to set
	 */
	public void setParentsClaimed2YearsAgo(Boolean parentsClaimed2YearsAgo) {
		this.parentsClaimed2YearsAgo = parentsClaimed2YearsAgo;
	}


	/**
	 * @return the college1Name
	 */
	public String getCollege1Name() {
		return college1Name;
	}


	/**
	 * @param college1Name the college1Name to set
	 */
	public void setCollege1Name(String college1Name) {
		this.college1Name = StringUtils.trim(college1Name);
	}


	/**
	 * @return the college1State
	 */
	public String getCollege1State() {
		return college1State;
	}


	/**
	 * @param college1State the college1State to set
	 */
	public void setCollege1State(String college1State) {
		this.college1State = StringUtils.trim(college1State);
	}


	

	/**
	 * @return the college1StartDate
	 */
	public Date getCollege1StartDate() {
		return college1StartDate;
	}


	/**
	 * @param college1StartDate the college1StartDate to set
	 */
	public void setCollege1StartDate(Date college1StartDate) {
		this.college1StartDate = college1StartDate;
	}


	/**
	 * @return the college1EndDate
	 */
	public Date getCollege1EndDate() {
		return college1EndDate;
	}


	/**
	 * @param college1EndDate the college1EndDate to set
	 */
	public void setCollege1EndDate(Date college1EndDate) {
		this.college1EndDate = college1EndDate;
	}


	/**
	 * @return the college2StartDate
	 */
	public Date getCollege2StartDate() {
		return college2StartDate;
	}


	/**
	 * @param college2StartDate the college2StartDate to set
	 */
	public void setCollege2StartDate(Date college2StartDate) {
		this.college2StartDate = college2StartDate;
	}


	/**
	 * @return the college2EndDate
	 */
	public Date getCollege2EndDate() {
		return college2EndDate;
	}


	/**
	 * @param college2EndDate the college2EndDate to set
	 */
	public void setCollege2EndDate(Date college2EndDate) {
		this.college2EndDate = college2EndDate;
	}


	/**
	 * @return the college3StartDate
	 */
	public Date getCollege3StartDate() {
		return college3StartDate;
	}


	/**
	 * @param college3StartDate the college3StartDate to set
	 */
	public void setCollege3StartDate(Date college3StartDate) {
		this.college3StartDate = college3StartDate;
	}


	/**
	 * @return the college3EndDate
	 */
	public Date getCollege3EndDate() {
		return college3EndDate;
	}


	/**
	 * @param college3EndDate the college3EndDate to set
	 */
	public void setCollege3EndDate(Date college3EndDate) {
		this.college3EndDate = college3EndDate;
	}


	/**
	 * @return the college2Name
	 */
	public String getCollege2Name() {
		return college2Name;
	}


	/**
	 * @param college2Name the college2Name to set
	 */
	public void setCollege2Name(String college2Name) {
		this.college2Name = college2Name;
	}


	/**
	 * @return the college2State
	 */
	public String getCollege2State() {
		return college2State;
	}


	/**
	 * @param college2State the college2State to set
	 */
	public void setCollege2State(String college2State) {
		this.college2State = college2State;
	}


	

	/**
	 * @return the college3Name
	 */
	public String getCollege3Name() {
		return college3Name;
	}


	/**
	 * @param college3Name the college3Name to set
	 */
	public void setCollege3Name(String college3Name) {
		this.college3Name = college3Name;
	}


	/**
	 * @return the college3State
	 */
	public String getCollege3State() {
		return college3State;
	}


	/**
	 * @param college3State the college3State to set
	 */
	public void setCollege3State(String college3State) {
		this.college3State = college3State;
	}


	

	/**
	 * @return the uSMilStateStudent
	 */
	public String getuSMilStateStudent() {
		return uSMilStateStudent;
	}


	/**
	 * @param uSMilStateStudent the uSMilStateStudent to set
	 */
	public void setuSMilStateStudent(String uSMilStateStudent) {
		this.uSMilStateStudent = StringUtils.trim(uSMilStateStudent);
	}


	/**
	 * @return the uSMilStateSpouse
	 */
	public String getuSMilStateSpouse() {
		return uSMilStateSpouse;
	}


	/**
	 * @param uSMilStateSpouse the uSMilStateSpouse to set
	 */
	public void setuSMilStateSpouse(String uSMilStateSpouse) {
		this.uSMilStateSpouse = StringUtils.trim(uSMilStateSpouse);
	}


	/**
	 * @return the selectiveServiceRegState
	 */
	public String getSelectiveServiceRegState() {
		return selectiveServiceRegState;
	}


	/**
	 * @param selectiveServiceRegState the selectiveServiceRegState to set
	 */
	public void setSelectiveServiceRegState(String selectiveServiceRegState) {
		this.selectiveServiceRegState = StringUtils.trim(selectiveServiceRegState);
	}


	public ResidencyStatus getResidencyStatus() {
		return residencyStatus;
	}


	public void setResidencyStatus(ResidencyStatus residencyStatus) {
		this.residencyStatus = residencyStatus;
	}

	


	/**
	 * @return the othStateReqforBenefitsLastYear
	 */
	public Boolean getOthStateReqforBenefitsLastYear() {
		return othStateReqforBenefitsLastYear;
	}





	/**
	 * @param othStateReqforBenefitsLastYear the othStateReqforBenefitsLastYear to set
	 */
	public void setOthStateReqforBenefitsLastYear(
			Boolean othStateReqforBenefitsLastYear) {
		this.othStateReqforBenefitsLastYear = othStateReqforBenefitsLastYear;
	}





	/**
	 * @return the othStateReqforBenefitsThisYear
	 */
	public Boolean getOthStateReqforBenefitsThisYear() {
		return othStateReqforBenefitsThisYear;
	}

	/**
	 * @param othStateReqforBenefitsThisYear the othStateReqforBenefitsThisYear to set
	 */
	public void setOthStateReqforBenefitsThisYear(
			Boolean othStateReqforBenefitsThisYear) {
		this.othStateReqforBenefitsThisYear = othStateReqforBenefitsThisYear;
	}

	public TaxInfo getTaxInfo() {
		return taxInfo;
	}


	public void setTaxInfo(TaxInfo taxInfo) {
		this.taxInfo = taxInfo;
	}


	public PersonalInfo getPersonalInfo() {
		return personalInfo;
	}


	public void setPersonalInfo(PersonalInfo personalInfo) {
		this.personalInfo = personalInfo;
	}

	public Boolean getCompletedFlag() {
		Boolean res = Boolean.FALSE;
		
		if ("C".equalsIgnoreCase(this.getSIRStatusCode())) {
			res = Boolean.TRUE;
		}
		return res;
	}
	
	public Boolean isCollege2HasData() {
		Boolean res = Boolean.FALSE;
		
		if (!StringUtils.isEmpty(this.college2Name)
				|| !StringUtils.isEmpty(this.college2State)
				|| this.college2StartDate != null
				|| this.college2EndDate != null)
			res = Boolean.TRUE;
		
		return res;
	}

	public Boolean isCollege3HasData() {
		Boolean res = Boolean.FALSE;
		
		if (!StringUtils.isEmpty(this.college3Name)
				|| !StringUtils.isEmpty(this.college3State)
				|| this.college3StartDate != null
				|| this.college3EndDate != null)
			res = Boolean.TRUE;
		
		return res;
	}


	public FinancialSupportSource getTwoYearsAgoSupport() {
		return twoYearsAgoSupport;
	}

	public void setTwoYearsAgoSupport(FinancialSupportSource twoYearsAgoSupport) {
		this.twoYearsAgoSupport = twoYearsAgoSupport;
	}

	public FinancialSupportSource getLastYearSupport() {
		return lastYearSupport;
	}

	public void setLastYearSupport(FinancialSupportSource lastYearSupport) {
		this.lastYearSupport = lastYearSupport;
	}

	public FinancialSupportSource getThisYearSupport() {
		return thisYearSupport;
	}

	public void setThisYearSupport(FinancialSupportSource thisYearSupport) {
		this.thisYearSupport = thisYearSupport;
	}
	

	public Boolean getUsVetInCaFl() {
		return usVetInCaFl;
	}

	public void setUsVetInCaFl(Boolean usVetInCaFl) {
		this.usVetInCaFl = usVetInCaFl;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("SLRData [");
		if (residencyCompletedStatusCode != null)
			builder.append("residencyCompletedStatusCode=")
					.append(residencyCompletedStatusCode).append(", ");
		if (residencyTuitionUpdateCode != null)
			builder.append("residencyTuitionUpdateCode=")
					.append(residencyTuitionUpdateCode).append(", ");
		if (SIRStatusCode != null)
			builder.append("SIRStatusCode=").append(SIRStatusCode).append(", ");
		if (lastUpdatedTimestamp != null)
			builder.append("lastUpdatedTimestamp=")
					.append(lastUpdatedTimestamp).append(", ");
		if (motherInfoAvailable != null)
			builder.append("motherInfoAvailable=").append(motherInfoAvailable)
					.append(", ");
		if (fatherInfoAvailable != null)
			builder.append("fatherInfoAvailable=").append(fatherInfoAvailable)
					.append(", ");
		if (fatherDeceasedFlag != null)
			builder.append("fatherDeceasedFlag=").append(fatherDeceasedFlag)
					.append(", ");
		if (motherDeceasedFlag != null)
			builder.append("motherDeceasedFlag=").append(motherDeceasedFlag)
					.append(", ");
		if (parentsDivorced != null)
			builder.append("parentsDivorced=").append(parentsDivorced)
					.append(", ");
		if (parentLivingWith != null)
			builder.append("parentLivingWith=").append(parentLivingWith)
					.append(", ");
		if (parentLivingWithFromDate1 != null)
			builder.append("parentLivingWithFromDate1=")
					.append(parentLivingWithFromDate1).append(", ");
		if (parentLivingWithToDate1 != null)
			builder.append("parentLivingWithToDate1=")
					.append(parentLivingWithToDate1).append(", ");
		if (resAddrSameAsPermAddr1 != null)
			builder.append("resAddrSameAsPermAddr1=")
					.append(resAddrSameAsPermAddr1).append(", ");
		if (resAddrInCA1 != null)
			builder.append("resAddrInCA1=").append(resAddrInCA1).append(", ");
		if (parentLivingWithFromDate2 != null)
			builder.append("parentLivingWithFromDate2=")
					.append(parentLivingWithFromDate2).append(", ");
		if (parentLivingWithToDate2 != null)
			builder.append("parentLivingWithToDate2=")
					.append(parentLivingWithToDate2).append(", ");
		if (resAddrSameAsPermAddr2 != null)
			builder.append("resAddrSameAsPermAddr2=")
					.append(resAddrSameAsPermAddr2).append(", ");
		if (resAddrInCA2 != null)
			builder.append("resAddrInCA2=").append(resAddrInCA2).append(", ");
		if (residedWithOtherParent != null)
			builder.append("residedWithOtherParent=")
					.append(residedWithOtherParent).append(", ");
		if (residedOtherParentFromDate1 != null)
			builder.append("residedOtherParentFromDate1=")
					.append(residedOtherParentFromDate1).append(", ");
		if (residedOtherParentToDate1 != null)
			builder.append("residedOtherParentToDate1=")
					.append(residedOtherParentToDate1).append(", ");
		if (resOPAddrSameAsPermAddr1 != null)
			builder.append("resOPAddrSameAsPermAddr1=")
					.append(resOPAddrSameAsPermAddr1).append(", ");
		if (resOPAddrInCA1 != null)
			builder.append("resOPAddrInCA1=").append(resOPAddrInCA1)
					.append(", ");
		if (residedOtherParentFromDate2 != null)
			builder.append("residedOtherParentFromDate2=")
					.append(residedOtherParentFromDate2).append(", ");
		if (residedOtherParentToDate2 != null)
			builder.append("residedOtherParentToDate2=")
					.append(residedOtherParentToDate2).append(", ");
		if (resOPAddrSameAsPermAddr2 != null)
			builder.append("resOPAddrSameAsPermAddr2=")
					.append(resOPAddrSameAsPermAddr2).append(", ");
		if (resOPAddrInCA2 != null)
			builder.append("resOPAddrInCA2=").append(resOPAddrInCA2)
					.append(", ");
		if (ursaErrFieldNm != null)
			builder.append("ursaErrFieldNm=").append(ursaErrFieldNm)
					.append(", ");
		if (ursaErrText != null)
			builder.append("ursaErrText=").append(ursaErrText).append(", ");
		if (MothersRelationship != null)
			builder.append("MothersRelationship=").append(MothersRelationship)
					.append(", ");
		if (FathersRelationship != null)
			builder.append("FathersRelationship=").append(FathersRelationship)
					.append(", ");
		if (residencyStatus != null)
			builder.append("residencyStatus=").append(residencyStatus)
					.append(", ");
		if (cAClaimedAsDependent != null)
			builder.append("cAClaimedAsDependent=")
					.append(cAClaimedAsDependent).append(", ");
		if (financiallyIndependentFl != null)
			builder.append("financiallyIndependentFl=")
					.append(financiallyIndependentFl).append(", ");
		if (cA_HSGraduateFlag != null)
			builder.append("cA_HSGraduateFlag=").append(cA_HSGraduateFlag)
					.append(", ");
		if (caResidentAbsent != null)
			builder.append("caResidentAbsent=").append(caResidentAbsent)
					.append(", ");
		if (caResidentAbsentExpl != null)
			builder.append("caResidentAbsentExpl=")
					.append(caResidentAbsentExpl).append(", ");
		if (twoYearsAgoSupport != null)
			builder.append("twoYearsAgoSupport=").append(twoYearsAgoSupport)
					.append(", ");
		if (lastYearSupport != null)
			builder.append("lastYearSupport=").append(lastYearSupport)
					.append(", ");
		if (thisYearSupport != null)
			builder.append("thisYearSupport=").append(thisYearSupport)
					.append(", ");
		if (usVetInCaFl != null)
			builder.append("usVetInCaFl=").append(usVetInCaFl).append(", ");
		if (taxInfo != null)
			builder.append("taxInfo=").append(taxInfo).append(", ");
		if (othStateReqforBenefitsLastYear != null)
			builder.append("othStateReqforBenefitsLastYear=")
					.append(othStateReqforBenefitsLastYear).append(", ");
		if (othStateReqforBenefitsThisYear != null)
			builder.append("othStateReqforBenefitsThisYear=")
					.append(othStateReqforBenefitsThisYear).append(", ");
		if (othStateReqforBenefitsText != null)
			builder.append("othStateReqforBenefitsText=")
					.append(othStateReqforBenefitsText).append(", ");
		if (cAEmployeeLastYear != null)
			builder.append("cAEmployeeLastYear=").append(cAEmployeeLastYear)
					.append(", ");
		if (cAEmployeeThisYear != null)
			builder.append("cAEmployeeThisYear=").append(cAEmployeeThisYear)
					.append(", ");
		if (othStateEmployeeLastYear != null)
			builder.append("othStateEmployeeLastYear=")
					.append(othStateEmployeeLastYear).append(", ");
		if (othStateEmployeeThisYear != null)
			builder.append("othStateEmployeeThisYear=")
					.append(othStateEmployeeThisYear).append(", ");
		if (attendedCollege != null)
			builder.append("attendedCollege=").append(attendedCollege)
					.append(", ");
		if (college1Name != null)
			builder.append("college1Name=").append(college1Name).append(", ");
		if (college1State != null)
			builder.append("college1State=").append(college1State).append(", ");
		if (college1StartDate != null)
			builder.append("college1StartDate=").append(college1StartDate)
					.append(", ");
		if (college1EndDate != null)
			builder.append("college1EndDate=").append(college1EndDate)
					.append(", ");
		if (college2Name != null)
			builder.append("college2Name=").append(college2Name).append(", ");
		if (college2State != null)
			builder.append("college2State=").append(college2State).append(", ");
		if (college2StartDate != null)
			builder.append("college2StartDate=").append(college2StartDate)
					.append(", ");
		if (college2EndDate != null)
			builder.append("college2EndDate=").append(college2EndDate)
					.append(", ");
		if (college3Name != null)
			builder.append("college3Name=").append(college3Name).append(", ");
		if (college3State != null)
			builder.append("college3State=").append(college3State).append(", ");
		if (college3StartDate != null)
			builder.append("college3StartDate=").append(college3StartDate)
					.append(", ");
		if (college3EndDate != null)
			builder.append("college3EndDate=").append(college3EndDate)
					.append(", ");
		if (uSMilStudent != null)
			builder.append("uSMilStudent=").append(uSMilStudent).append(", ");
		if (uSMilStateStudent != null)
			builder.append("uSMilStateStudent=").append(uSMilStateStudent)
					.append(", ");
		if (uSMilSpouse != null)
			builder.append("uSMilSpouse=").append(uSMilSpouse).append(", ");
		if (uSMilStateSpouse != null)
			builder.append("uSMilStateSpouse=").append(uSMilStateSpouse)
					.append(", ");
		if (selectiveServiceReg != null)
			builder.append("selectiveServiceReg=").append(selectiveServiceReg)
					.append(", ");
		if (selectiveServiceRegState != null)
			builder.append("selectiveServiceRegState=")
					.append(selectiveServiceRegState).append(", ");
		if (personalInfo != null)
			builder.append("personalInfo=").append(personalInfo).append(", ");
		if (profLicense != null)
			builder.append("profLicense=").append(profLicense).append(", ");
		if (profLicenseStartDate != null)
			builder.append("profLicenseStartDate=")
					.append(profLicenseStartDate).append(", ");
		if (profLicenseEndDate != null)
			builder.append("profLicenseEndDate=").append(profLicenseEndDate)
					.append(", ");
		if (profLicenseState != null)
			builder.append("profLicenseState=").append(profLicenseState)
					.append(", ");
		if (uSVet != null)
			builder.append("uSVet=").append(uSVet).append(", ");
		if (wardOfCourt != null)
			builder.append("wardOfCourt=").append(wardOfCourt).append(", ");
		if (haveLegalDependents != null)
			builder.append("haveLegalDependents=").append(haveLegalDependents)
					.append(", ");
		if (employedGradStudent != null)
			builder.append("employedGradStudent=").append(employedGradStudent)
					.append(", ");
		if (parentsClaimedThisYear != null)
			builder.append("parentsClaimedThisYear=")
					.append(parentsClaimedThisYear).append(", ");
		if (parentsClaimedLastYear != null)
			builder.append("parentsClaimedLastYear=")
					.append(parentsClaimedLastYear).append(", ");
		if (parentsClaimed2YearsAgo != null)
			builder.append("parentsClaimed2YearsAgo=")
					.append(parentsClaimed2YearsAgo).append(", ");
		if (maritalStatus != null)
			builder.append("maritalStatus=").append(maritalStatus).append(", ");
		if (maritalStatusDate != null)
			builder.append("maritalStatusDate=").append(maritalStatusDate)
					.append(", ");
		if (maritalStatusState != null)
			builder.append("maritalStatusState=").append(maritalStatusState)
					.append(", ");
		if (homeState != null)
			builder.append("homeState=").append(homeState).append(", ");
		if (homeStateStartDate != null)
			builder.append("homeStateStartDate=").append(homeStateStartDate)
					.append(", ");
		if (cAAfterEducation != null)
			builder.append("cAAfterEducation=").append(cAAfterEducation)
					.append(", ");
		if (SLRStatusCode != null)
			builder.append("SLRStatusCode=").append(SLRStatusCode).append(", ");
		if (residencyAge != null)
			builder.append("residencyAge=").append(residencyAge).append(", ");
		if (birthDate != null)
			builder.append("birthDate=").append(birthDate).append(", ");
		if (studentAgreement != null)
			builder.append("studentAgreement=").append(studentAgreement)
					.append(", ");
		if (infoRelease != null)
			builder.append("infoRelease=").append(infoRelease);
		builder.append("]");
		return builder.toString();
	}
}
