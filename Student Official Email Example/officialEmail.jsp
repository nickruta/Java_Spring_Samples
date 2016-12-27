<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<s:eval expression='@termUtils.getTermDescription("${currentSir.term}")' var="term" />

<tiles:importAttribute name="disabled" ignore="true"/>

<script type="text/javascript">
var currentStep = '${currentStep.id}';
var slrSteps = [];
<c:forEach var="step" items="${admissionSteps}" varStatus="status">
	slrSteps.push("${step.id}");
</c:forEach>

$(document).ready(function() {

	// init dependent elements
	$('input:radio.master:checked').each(function() {
		//console.log("element " + $(this).attr("name") + ', value = ' + $(this).val());
		__showHideDependentElement($(this).attr('name'), $(this).val());
	});

	$.each([
            $('input:radio[name="email.type"]')
           ], function() {
       //	console.log("element " + $(this).attr("name"));
    	$(this).click(showHideDependentElement);
    	$(this).change(function (){
    		//console.log("element " + $('#email\\.address').val());
    		$('#email\\.address').val('');
    	});
    });

	//highlightFormFields();
	initInputCharLimit(".studentContacts-otherEmail", "#studentContacts-otherEmailCount", 60);
	
	stepsNavigation() ;
	
});
</script>

<div id="page_header_section">
	<h2 id="interior-split-page-header-full">${currentStep.name}</h2>
</div>
<div id="main-wrapper" class="row">
	
	<div id="main-content" role="main1" class="cf panel-9">

		<tiles:insertAttribute name="status-bar" ignore="true" />

		<div id="${currentStep.id}" class="collapse-content show-content">
	
			<form:form commandName="stContactForm" cssClass="admissionForm">
				<spring:hasBindErrors name="stContactForm">
					<tiles:insertDefinition name="form-error-box.definition" flush="true" />
				</spring:hasBindErrors>	
	
				<ul class="formFields">

					<li class="first section">
						<section><h3>Official E-mail</h3></section>
						<label>Please select your official e-mail from the addresses listed below or enter a new e-mail address.</label>
						<p class="desc">Make sure to check your e-mail regularly. The university uses e-mail as the primary medium to send you 
						critical information.</p>
					</li>

					<li class="complex">
						<fieldset>
							<div>
								<c:forEach var="entry" items="${emailChoices}" varStatus="pEmail">
										<form:radiobutton path="email.type" tabindex="" value="${entry.key}" cssClass="field radio tabindex master" cssErrorClass="field radio tabindex master error" />
										<label for="email.type${pEmail.index}" class="choice">${entry.value.address}</label>
								</c:forEach>
				
								<form:radiobutton path="email.type" tabindex="" value="uclaNewStudentEmail" cssClass="field radio tabindex master" cssErrorClass="field radio tabindex master error" />
								<label for="email3" class="choice">Other e-mail address</label>
							</div>
						</fieldset>
						<ul class="inner">
							<li class="dependent complex email-type email-typeuclaNewStudentEmail">
								<div>
									<span class="left">
									<c:set var="stContactForm.email.address" value="" />
									<form:input path="email.address" tabindex=""	cssClass="field text tabindex" maxLength="60" cssErrorClass="field text tabindex field-error" />
									</span>
								</div>
							</li>
						</ul>
						<p></p>
						<form:errors path="email.address" cssClass="error" />			
			
					</li>
		
					<li class="section">
			   	     	<section><h3>Emergency Notification</h3></section>
					</li>	
					<li>
						<label class="desc">The university will only use this information to notify you in the case of an emergency.</label>
					</li>
					<li>
						<div>
						     <label>Your Cell Phone Number</label>
							 <form:input path="emergencyContact.phone.number"  tabindex=""  class="one-half last" cssClass="field text tabindex" maxLength="15" cssErrorClass="field text tabindex field-error" disabled="${disabled}" />
							 <form:errors path="emergencyContact.phone.number" cssClass="error" />
					 	</div>
					</li>
					<li>
						<div>
							<label>Your Cell Phone Provider</label>
							<form:select path="emergencyContact.phone.provider" cssClass="field select tabindex" tabindex="" disabled="${disabled}" cssErrorClass="field select tabindex error">
								<form:options items="${providers}" itemValue="value" itemLabel="name" />
							</form:select>
							<form:errors path="emergencyContact.phone.provider" cssClass="error" />
						</div>
					</li>
					<li>		
						<label><form:checkbox
											path="emergencyContact.phone.notificationFlag" 
											tabindex="" cssClass="field checkbox-certify"
											cssErrorClass="field checkbox-certify master tabindex error"
											 /> I want to receive BruinAlert emergency text
									messages
						</label>
						<form:errors path="emergencyContact.phone.notificationFlag" cssClass="error" />
					</li>
		
					<li class="section">
						<section><h3>Emergency Contact/Next of Kin</h3></section>
					</li>	
					<li>
						<label class="desc">Please provide the name, telephone number and e-mail of someone who can be contacted in case of an 
						emergency (illness, accident) affecting you. Please keep this information current -- it is not released to the public nor 
						published -- it is available only to University staff.</label>
					</li>
					<tiles:insertDefinition name="emergency-contact.definition" flush="true">
						<tiles:putAttribute name="disabled" value="${disabled}" />
						<tiles:putAttribute name="model" value="${emergencyContact.contact}" />
						<tiles:putAttribute name="modelPrefix" value="emergencyContact.contact." />
						<tiles:putAttribute name="contactTypes" value="${contactTypes}" />
						<tiles:putAttribute name="showAddress" value="false" />
					</tiles:insertDefinition>
		
				</ul>
				<form:hidden path="saveForLater"/>
			</form:form>
		</div>
		<tiles:insertAttribute name="main-navigation" ignore="true" />
	</div>
	<div id="right-sidebar" class="cf panel-3">

		<tiles:insertDefinition name="admission/info-widget.view" flush="true" >
			<tiles:putAttribute name="dueDate" value="${requestScope.dueDate}" />
		</tiles:insertDefinition>

		<c:set var="code" value="widget.help.admission.${currentSir.career}" />
		<s:eval expression="@widgetUtils.getWidget(code, null)" var="widget" />
		
		${widget}
			
		<tiles:insertDefinition name="admission/quickLinks-widget.view" flush="true" >
			<tiles:putAttribute name="career" value="${currentSir.career}" />
		</tiles:insertDefinition>
	
	</div>
</div>