/**
 * File Name: ScreenSADDAO.java
 *
 * Purpose:  This file contains the code implementation to update Student Address
 *           Supplimental Information which includes the Emergency Contact Information
 *           along with other fields such as "EmailVerifyFlag" and "ApplicantEmail".
 *             
 * Additional Information:
 * 
 * Development History:
 * Revision No.    	Author                              Date
 * 1				Norma Morelock						1/21/09
 * 
 *************************************************************************************/
package edu.ucla.dt.studentservices.dataaccessobject;

import java.util.ArrayList;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import edu.ucla.dt.studentservices.dataobject.SASScreenDO;
import edu.ucla.dt.studentservices.global.constants.GlobalConstants;
import edu.ucla.dt.studentservices.global.constants.SASScreen;
import edu.ucla.dt.studentservices.global.exceptions.StudentServicesException;
import edu.ucla.dt.studentservices.global.util.DataValidationUtil;
import edu.ucla.dt.studentservices.global.util.StudentAppContext;
import edu.ucla.dt.studentservices.global.util.ZInterfaceUtil;
import edu.ucla.dt.studentservices.global.zinterface.DEType;
import edu.ucla.dt.studentservices.global.zinterface.DataElement;
import edu.ucla.dt.studentservices.global.zinterface.ZInterface;
import edu.ucla.dt.studentservices.global.zinterface.ZInterfaceRequest;
import edu.ucla.dt.studentservices.global.zinterface.ZInterfaceResponse;

public class SASScreenDAO {
	private static final Log log = LogFactory.getLog(SASScreenDAO.class);

	/*
	 * sURSABusinessFunction cannot be greeater than 8 chars
	 */
	private static final String sURSABusinessFunction = "SCONTACT";

	private static final String OPERATION_updateStudentContactInfo = "updateStudentContactInfo";
	private static final String OPERATION_updateStudentEmail = "updateStudentEmail";
	private static final String DAOName = "SASScreenDAO";

	/**
	 * Method to update student contact information.
	 * 
	 * @param sasScreenDO
	 * @param studentAppContext
	 * @return SASScreenDO
	 * @throws StudentServicesException
	 */
	public SASScreenDO updateStudentContactInfo(SASScreenDO sasScreenDO,
			StudentAppContext stdCtx) throws StudentServicesException {
		log.debug("Enter " + DAOName + " - " + OPERATION_updateStudentContactInfo
				+ ".");

		final String BLANK_SPACE = " ";

		String UID = sasScreenDO.getUID();

		// CellPhoneInfoDO Info
		String cellPhone = sasScreenDO.getCellPhone();
		String cellPhoneProvider = sasScreenDO.getCellPhoneProvider();
		String sendTextMsgFlag = sasScreenDO.getSendTextMsgFlag();
		// WorkPhonenfoDO Info
		String workPhone = sasScreenDO.getWorkPhone();
		// MiscellaneousInfoDO Info
		String emailVerifyFlag = sasScreenDO.getEmailVerifyFlag();
		String applicantEmail = sasScreenDO.getApplicantEmail();
		// AddressDO Info
		String NOKName = sasScreenDO.getNOKName();
		String street1 = sasScreenDO.getStreet1();
		String street2 = sasScreenDO.getStreet2();
		String city = sasScreenDO.getCity();
		String stateCode = sasScreenDO.getStateCode();
		String zipCode = sasScreenDO.getZipCode();
		String provinceCode = sasScreenDO.getProvinceCode();
		String countryCode = sasScreenDO.getCountryCode();
		String phoneNumber = sasScreenDO.getPhoneNumberForAddress();
		String relationshipCode = sasScreenDO.getRelationshipCode();

		// Spacing out Flags and Parts of the existing info on the
		// SAS screen is a legitimate update. If the field is not
		// null, but contains an empty string, assume they wish to
		// space out the value on the screen. For example, spaces
		// in Flag fields means "Release". Stejskal 1/21/09.

		// CellPhoneInfoDO Info
		boolean cellPhoneBlankOut = false;
		boolean cellPhoneProviderBlankOut = false;
		boolean sendTextMsgFlagBlankOut = false;
		
		if (cellPhone != null) {
			if (cellPhone.trim().equals("")) {
				cellPhone = BLANK_SPACE;
				cellPhoneBlankOut = true;
			}
		}
		if (cellPhoneProvider != null) {
			if (cellPhoneProvider.trim().equals("")) {
				cellPhoneProvider = BLANK_SPACE;
				cellPhoneProviderBlankOut = true;
			}
		}
		if (sendTextMsgFlag != null) {
			if (sendTextMsgFlag.trim().equals("")) {
				sendTextMsgFlag = BLANK_SPACE;
				sendTextMsgFlagBlankOut = true;
			}
		}

		if (cellPhoneBlankOut == true || cellPhoneProviderBlankOut == true || sendTextMsgFlagBlankOut == true) {
			cellPhone = BLANK_SPACE;
			cellPhoneProvider = BLANK_SPACE;
			sendTextMsgFlag = BLANK_SPACE;
		}
		
		// WorkPhoneInfoDO Info
		if (workPhone != null) {
			if (workPhone.trim().equals("")) {
				workPhone = BLANK_SPACE;
			}
		}

		// MiscellaneousInfoDO Info
		if (emailVerifyFlag != null) {
			if (emailVerifyFlag.trim().equals("")) {
				emailVerifyFlag = BLANK_SPACE;
			}
		}
		if (applicantEmail != null) {
			if (applicantEmail.trim().equals("")) {
				applicantEmail = BLANK_SPACE;
			}
		}

		// AddressDO Info
		if (NOKName != null) {
			if (NOKName.trim().equals("")) {
				NOKName = BLANK_SPACE;
			}
		}
		if (street1 != null) {
			if (street1.trim().equals("")) {
				street1 = BLANK_SPACE;
			}
		}
		if (street2 != null) {
			if (street2.trim().equals("")) {
				street2 = BLANK_SPACE;
			}
		}
		if (city != null) {
			if (city.trim().equals("")) {
				city = BLANK_SPACE;
			}
		}
		if (stateCode != null) {
			if (stateCode.trim().equals("")) {
				stateCode = BLANK_SPACE;
			}
		}
		if (zipCode != null) {
			if (zipCode.trim().equals("")) {
				zipCode = BLANK_SPACE;
			}
		}
		if (provinceCode != null) {
			if (provinceCode.trim().equals("")) {
				provinceCode = BLANK_SPACE;
			}
		}
		if (countryCode != null) {
			if (countryCode.trim().equals("")) {
				countryCode = BLANK_SPACE;
			}
		}
		if (phoneNumber != null) {
			if (phoneNumber.trim().equals("")) {
				phoneNumber = BLANK_SPACE;
			}
		}
		if (relationshipCode != null) {
			if (relationshipCode.trim().equals("")) {
				relationshipCode = BLANK_SPACE;
			}
		}

		
		DataValidationUtil.isValidUID(UID);
		
		log.debug("log cell phone stuff");
		log.debug(sendTextMsgFlag + " - " + sendTextMsgFlag + "*****");
		log.debug(cellPhoneProvider + " - " + cellPhoneProvider + "*****");
		log.debug(cellPhone + " - " + cellPhone);
		log.debug(NOKName + " - " + NOKName);
		log.debug(DAOName + " - " + OPERATION_updateStudentContactInfo + ". UID = "
				+ UID);

		// Instantiate ZInterface object
		ZInterface objZinterface = new ZInterface();

		ZInterfaceRequest zreq = ZInterfaceUtil.createRequest(stdCtx,
				SASScreen.SCREEN_NAME, sURSABusinessFunction, false, 1);

		// build the DataElelments
		ArrayList<DataElement> dataElementList = new ArrayList<DataElement>();

		DataElement screenname = new DataElement(SASScreen.SCREEN_FIELD,
				SASScreen.SCREEN_NAME, DEType.Key,
				GlobalConstants.STRING_NINETY_NINE);
		dataElementList.add(screenname);

		// UID
		DataElement UIDElement = new DataElement(SASScreen.UID, UID,
				DEType.Key, GlobalConstants.STRING_NINETY_NINE);
		dataElementList.add(UIDElement);

		// update TextMessage Flag if not null
		if (sendTextMsgFlag != null) {
			DataElement sendTextMsgFlagElement = new DataElement(
					SASScreen.SENDTEXTMSG, sendTextMsgFlag, DEType.Update);
			dataElementList.add(sendTextMsgFlagElement);
		}

		// update Cell phone if not null
		if (cellPhone != null) {
			DataElement cellPhoneElement = new DataElement(SASScreen.CELLPHONE,
					cellPhone, DEType.Update);
			dataElementList.add(cellPhoneElement);
		}

		// update Cell phone provider
		if (cellPhoneProvider != null) {
			DataElement cellPhoneProviderElement = new DataElement(
					SASScreen.PROVIDER, cellPhoneProvider, DEType.Update);
			dataElementList.add(cellPhoneProviderElement);
		}
		
		// update Work Phone 
		if (workPhone != null) {
			DataElement workPhoneElement = new DataElement(
					SASScreen.WORKPHONE, workPhone, DEType.Update);
			dataElementList.add(workPhoneElement);
		}
		
		// update Applicant Email 
		if (applicantEmail != null) {
			DataElement applicantEmailElement = new DataElement(
					SASScreen.APPLICANTEMAIL, applicantEmail, DEType.Update);
			dataElementList.add(applicantEmailElement);
		}
		
		// relationship code
		if (relationshipCode != null) {
			DataElement relationshipCodeElement = new DataElement(
					SASScreen.RELATIONSHIP, relationshipCode, DEType.Update);
			dataElementList.add(relationshipCodeElement);
		}

		// NOK/Emergency contact phone number
		if (phoneNumber != null) {
			DataElement phoneNumberElement = new DataElement(SASScreen.PHONE,
					phoneNumber, DEType.Update);
			dataElementList.add(phoneNumberElement);
		}

		// NOK/Emergency contact canadian province code
		if (provinceCode != null) {
			DataElement provinceCodeElement = new DataElement(
					SASScreen.PROVINCECODE, provinceCode, DEType.Update);
			dataElementList.add(provinceCodeElement);
		}

		// NOK/Emergency contact zipcode
		if (zipCode != null) {
			DataElement zipCodeElement = new DataElement(SASScreen.ZIPCODE,
					zipCode, DEType.Update);
			dataElementList.add(zipCodeElement);
		}

		// NOK/Emergency contact country code
		if (countryCode != null) {
			DataElement countryCodeElement = new DataElement(
					SASScreen.COUNTRYCODE, countryCode, DEType.Update);
			dataElementList.add(countryCodeElement);
		}

		// NOK/Emergency contact state code
		if (stateCode != null) {
			DataElement stateCodeElement = new DataElement(SASScreen.STATECODE,
					stateCode, DEType.Update);
			dataElementList.add(stateCodeElement);
		}

		// NOK/Emergency contact city
		if (city != null) {
			DataElement cityElement = new DataElement(SASScreen.CITY, city,
					DEType.Update);
			dataElementList.add(cityElement);
		}

		// NOK/Emergency contact street2 address
		if (street2 != null) {
			DataElement street2Element = new DataElement(
					SASScreen.STREETADDRESS2, street2, DEType.Update);
			dataElementList.add(street2Element);
		}

		// NOK/Emergency contact street1 address
		if (street1 != null) {
			DataElement street1Element = new DataElement(
					SASScreen.STREETADDRESS1, street1, DEType.Update);
			dataElementList.add(street1Element);
		}

		// NOK/Emergency contact name
		if (NOKName != null) {
			DataElement NOKNameElement = new DataElement(SASScreen.NAME,
					NOKName, DEType.Update);
			dataElementList.add(NOKNameElement);
		}

		// Add dataelements to zinterface request
		zreq.setDataElementList(dataElementList);
		zreq.setKeepScreenOpen(false);

		// execute update
		ZInterfaceResponse zresp = objZinterface.executeUpdate(zreq);

		// Set the z messages from ZResponse
		sasScreenDO.setZmessageList(zresp.getZMessageList());

		log.debug("Exit " + DAOName + " - " + OPERATION_updateStudentContactInfo
				+ ".");
		return sasScreenDO;

	}
	
	
	/**
	 * Method to update student contact information.
	 * 
	 * @param sasScreenDO
	 * @param studentAppContext
	 * @return SASScreenDO
	 * @throws StudentServicesException
	 */
	public SASScreenDO updateStudentEmail(SASScreenDO sasScreenDO,
			StudentAppContext stdCtx) throws StudentServicesException {
		log.debug("Enter " + DAOName + " - " + OPERATION_updateStudentEmail
				+ ".");

		final String BLANK_SPACE = " ";

		String UID = sasScreenDO.getUID();

		// CellPhoneInfoDO Info
		String emailAddress = sasScreenDO.getEmailAddress();
		String emailAddressPrivacyFlag = sasScreenDO.getEmailAddressPrivacyFlag();
		String emailValidateFlag = sasScreenDO.getEmailVerifyFlag();
		
		
		
		DataValidationUtil.isValidUID(UID);
		
		log.debug(DAOName + " - " + OPERATION_updateStudentContactInfo + ". UID = "
				+ UID);

		// Instantiate ZInterface object
		ZInterface objZinterface = new ZInterface();

		ZInterfaceRequest zreq = ZInterfaceUtil.createRequest(stdCtx,SASScreen.SCREEN_NAME, sURSABusinessFunction, false, 1);

		// build the DataElelments
		ArrayList<DataElement> dataElementList = new ArrayList<DataElement>();

		DataElement screenname = new DataElement(SASScreen.SCREEN_FIELD,SASScreen.SCREEN_NAME, DEType.Key,
				GlobalConstants.STRING_NINETY_NINE);
		dataElementList.add(screenname);

		// UID
		DataElement UIDElement = new DataElement(SASScreen.UID, UID,DEType.Key, GlobalConstants.STRING_NINETY_NINE);
		dataElementList.add(UIDElement);

		// update emailAddress Flag if not null
		if (emailAddress != null) {
			DataElement emailAddressElement = new DataElement(
					SASScreen.APPLICANTEMAIL, emailAddress, DEType.Update);
			dataElementList.add(emailAddressElement);
		}

		// update emailAddressPrivacyFlag if not null
		if (emailAddressPrivacyFlag != null) {
			DataElement emailAddPriFLDataElement = new DataElement(SASScreen.EMAILPRIVACYFLAG,
					emailAddressPrivacyFlag, DEType.Update);
			dataElementList.add(emailAddPriFLDataElement);
		}

		//	update emailValidateFlag if not null
		if (emailValidateFlag != null) {
			DataElement emailValidateFlagDE = new DataElement(SASScreen.EMAILVALIDATEFLAG,
					emailValidateFlag, DEType.Update);
			dataElementList.add(emailValidateFlagDE);
		}

		// Add dataelements to zinterface request
		zreq.setDataElementList(dataElementList);
		zreq.setKeepScreenOpen(false);

		// execute update
		ZInterfaceResponse zresp = objZinterface.executeUpdate(zreq);

		// Set the z messages from ZResponse
		sasScreenDO.setZmessageList(zresp.getZMessageList());

		log.debug("Exit " + DAOName + " - " + OPERATION_updateStudentContactInfo
				+ ".");
		return sasScreenDO;

	}
}
