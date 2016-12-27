package edu.ucla.dt.studentweb.svc.client.impl;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.ArrayUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.ucla.dt.studentservices.webservices.studentcontact.dataobject.StudentContactGetServiceRequest;
import edu.ucla.dt.studentservices.webservices.studentcontact.dataobject.StudentContactGetServiceResponse;
import edu.ucla.dt.studentservices.webservices.studentcontact.dataobject.StudentContactUpdateAddressesServiceRequest;
import edu.ucla.dt.studentservices.webservices.studentcontact.dataobject.StudentContactUpdateAddressesServiceResponse;
import edu.ucla.dt.studentservices.webservices.studentcontact.dataobject.StudentContactUpdateServiceRequest;
import edu.ucla.dt.studentservices.webservices.studentcontact.dataobject.StudentContactUpdateServiceResponse;
import edu.ucla.dt.studentservices.webservices.studentcontact.service.StudentContactWebService;
import edu.ucla.dt.studentweb.svc.client.StudentContactWebServiceClient;
import edu.ucla.dt.studentweb.svc.domain.Status;
import edu.ucla.dt.studentweb.svc.domain.Status.ErrorLevel;
import edu.ucla.dt.studentweb.svc.domain.WSResponse;
import edu.ucla.dt.studentweb.svc.dto.StudentContact;
import edu.ucla.dt.studentweb.svc.mapper.StudentContact2StudentContactUpdateServiceRequestMapper;
import edu.ucla.dt.studentweb.svc.mapper.StudentContactGetServiceResponse2StudentContactMapper;
import edu.ucla.dt.studentweb.svc.utils.WebserviceUtils;

@Service("studentContactClient")
public class StudentContactWebServiceClientImpl implements StudentContactWebServiceClient {
    private static final Logger logger = Logger.getLogger(StudentContactWebServiceClientImpl.class);
    private static final String padding = "            "; 
	private static List<Integer> messageExcludes = Arrays.asList(ArrayUtils.toObject(new int[] {2000}));
	private static List<Integer> messageErrors = Arrays.asList(ArrayUtils.toObject(new int[] {9702}));
	
	@Autowired
	private StudentContactWebService studentContactWS;
	
//
	@Override
	public WSResponse<StudentContact> getStudentContactInfo(String scope, String uid, String addressType, String emailAttributesFlag, int flag) {
		StudentContactGetServiceRequest request = new StudentContactGetServiceRequest();
		request.setAddressType(addressType);
		request.setScope(scope);
		request.setUid(uid);
		request.setGetEDEmailAttributesFlag(emailAttributesFlag);
		
		StudentContactGetServiceResponse response = studentContactWS.getStudentContactInfo(request, null);

		WSResponse<StudentContact> wsResult = new WSResponse<StudentContact>();
		wsResult.setError(response.getError());
		wsResult.setReturnValue(response.getReturnValue());

		Status status = new Status();
		status.setErrorLevel(ErrorLevel.success);
		if (response.getMessageList() != null)
			status.setMessages(
						WebserviceUtils.getBusinessMessages(
								response.getMessageList().getBusinessMessage(), messageExcludes));
		wsResult.setStatus(status);

		wsResult.setData(StudentContactGetServiceResponse2StudentContactMapper.map(response, flag));
		
		
		
		return wsResult;
	}

	@Override
	public WSResponse<Object> updateStudentContactInfo(String uid, StudentContact data, String logonId, String updateED) {
		WSResponse<Object> wsResult = new WSResponse<Object>();
		
		StudentContactUpdateServiceRequest request = null;
		
		
		if (data == null) {
			request = new StudentContactUpdateServiceRequest();
			request.setUID(uid);

			request.setUpdateED(updateED);
			request.setUserName(logonId);
			request.setActivateBOL("Y");
		}
		else {
			request = StudentContact2StudentContactUpdateServiceRequestMapper.map(data);
			request.setUID(uid);
			
		}
		
		StudentContactUpdateServiceResponse response = studentContactWS.updateStudentContactInfo(request, null);
		wsResult.setError(response.getError());
		wsResult.setReturnValue(response.getReturnValue());

		if (response.getMessageList() != null)
			wsResult.setStatus(
						WebserviceUtils.processMessagesWithException(
								response.getMessageList().getBusinessMessage(), messageExcludes, messageErrors, null, null));
		else
			wsResult.setStatus(new Status());		
		
		return wsResult;
	}

	@Override
	public StudentContactUpdateAddressesServiceResponse updateAddresses(StudentContactUpdateAddressesServiceRequest request) {
//		return studentContactService.updateAddresses(request);
		return null;
	}
	


}
