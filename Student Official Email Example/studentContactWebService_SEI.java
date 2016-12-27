package edu.ucla.dt.studentservices.webservices.studentcontact.service;


public interface StudentContactWebService_SEI extends java.rmi.Remote
{
  public edu.ucla.dt.studentservices.webservices.studentcontact.dataobject.StudentContactUpdateServiceResponse updateStudentContactInfo(edu.ucla.dt.studentservices.webservices.studentcontact.dataobject.StudentContactUpdateServiceRequest request);
  public edu.ucla.dt.studentservices.webservices.studentcontact.dataobject.StudentContactGetServiceResponse getStudentContactInfo(edu.ucla.dt.studentservices.webservices.studentcontact.dataobject.StudentContactGetServiceRequest request);
  public edu.ucla.dt.studentservices.webservices.studentcontact.dataobject.StudentContactUpdateAddressesServiceResponse updateAddresses(edu.ucla.dt.studentservices.webservices.studentcontact.dataobject.StudentContactUpdateAddressesServiceRequest request);
}