<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="myTags" uri="/WEB-INF/tags/functions.tld" %>


<tiles:importAttribute name="model" ignore="true"/>
<tiles:importAttribute name="addressListModel" ignore="true"/>
<tiles:importAttribute name="showAction" ignore="true"/>

<c:if test="${showAction == null}">
	<c:set value="true" var="showAction" />
</c:if>

<c:if test="${model != null }">
<spring:bind path="${model}">
	<c:set var="phoneList" value="${status.value}"/>
</spring:bind>
</c:if>

<c:if test="${addressListModel != null }">
<spring:bind path="${ addressListModel}">
	<c:set var="addressList" value="${status.value}"/>
</spring:bind>
</c:if>

<c:set var="phoneTypes">primary,home,personal,work,future</c:set>

<div id="phone-list">
	<div class="heading">Phones</div>
		
	<form:form id="phone-form" modelAttribute="phoneForm" name="phoneForm" method="POST">

	<spring:hasBindErrors name="phoneForm">
		<tiles:insertDefinition name="form-error-box.definition" flush="true" />
	</spring:hasBindErrors>
	<div id="phone-error-box"></div>

		<div class="row-fluid data-header">
			<div class="span1">Phone Type</div>
			<div class="span2">Phone Number</div>
			<div class="span3">Privacy Options</div>
			<c:if test="${showAction}">
			<div class="span4">Actions</div>
			</c:if>
		</div>

		<c:forEach var="type" items="${phoneTypes }">
			<c:set var="phone" value="${phoneList[type]}" />
			<spring:eval expression="( T(edu.ucla.dt.studentweb.mvc.utils.PersonalPhoneUtils).ifAddressExists(type, addressList) )" var="showPhone"  />
			<c:if test="${showPhone}">
				<div class="row-fluid data-aside">
					<div class="span1">
						<spring:message code="studentContact.phone.type.${type }" />
					</div>
					
					<div class="span2">
						<c:choose>
							<c:when test="${phoneForm != null && type == phoneForm.type}">
								<input type="hidden" name="formName" value="${type}" />
							
								<c:if test="${type != 'personal'}">
								<ul class="formFields">
									<li>
										<div>
										    <label>Phone Number</label>
											<form:input path="number" cssClass="field text" cssErrorClass="field text error" />
				 							<form:errors path="number" cssClass="error" />
										</div>
									</li>
								</ul>
								</c:if>
								
								<c:if test="${type == 'personal'}">
								<ul class="formFields">
									<tiles:insertDefinition name="emergency-notification.definition" flush="true">
										<tiles:putAttribute name="disabled" value="" />
										<tiles:putAttribute name="model" value="${phoneForm}" />
										<tiles:putAttribute name="modelPrefix" value="" />
										<tiles:putAttribute name="providers" value="${providers}" />
									</tiles:insertDefinition>
								
								</ul>
								</c:if>
							</c:when>
							
							<c:otherwise>
								<div>												
 								${myTags:phoneFormat(phone.number)}						
								</div>
		
								<c:if test="${phone != null && type == 'personal' }">
								<div>Send emergency text messages: <spring:message code="studentContact.emergency.notification.${phone.notificationFlag}" /></div>
								</c:if>
							</c:otherwise>
						</c:choose>
					</div>

					<div class="span3">
						<c:choose>
							<c:when test="${phoneForm != null && type == phoneForm.type && phoneForm.privacyOption != 'NA'}">
								<ul class="formFields">
								<li>
									<fieldset>
										<div>
										<c:forEach var="option" items="${privacyOptions}" varStatus="status">
											<form:radiobutton path="privacyOption" value="${option}" tabindex="" cssClass="field radio tabindex master" cssErrorClass="field radio tabindex master error" />
											<label for="privacyOption${status.index}" class="choice"><spring:message code="studentContact.email.privacyOption.${option }" /></label>
										
										</c:forEach>
										<form:errors path="privacyOption" cssClass="error" />
										</div>
									</fieldset>
								</li>
								</ul>
							</c:when>
							
							<c:when test="${phoneForm != null && type == phoneForm.type && phoneForm.privacyOption == 'NA'}">
								<spring:message code="studentContact.email.privacyOption.${phone.privacyOption}"/>
								<form:hidden path="privacyOption"/>
							</c:when>
							
							<c:otherwise>
								<c:if test="${! empty phone.privacyOption}">
								<spring:message code="studentContact.email.privacyOption.${phone.privacyOption}"/>
								</c:if>
							</c:otherwise>
						</c:choose>
					</div>
					
					<c:if test="${showAction}">
					<div class="span4">
						<c:choose>
							<c:when test="${phoneForm != null && phoneForm.type == type }">
								<a href="javascript:void(0);" class="k-button k-button-noborder" id="edit-address" onclick="return submitTabForm('#phone-form', '#phone_data');"><span class="k-icon k-i-tick"></span>Save</a><br /> 
								<a href="javascript:void(0);" class="k-button k-button-noborder" id="edit-address" onclick="return getAjaxForm('<c:url value="/app/personal/phone" />', '#phone_data', '#phone_data');"><span class="k-icon k-i-cancel"></span>Cancel</a>
							</c:when>
							
							<c:otherwise>
								<c:choose>
									<c:when test="${ phone == null || phone.isEmpty }">
										<a href="javascript:void(0);" class="k-button k-button-noborder" onclick="return getAjaxForm('<c:url value="/app/personal/phone/${type}/edit" />','#phone_data', '');"><span class="k-icon k-i-plus"></span>Add</a>
<%-- 										<a href="#" class="add-item-button ui-no-border" onclick="return getAjaxForm('<c:url value="/app/personal/phone/${type}/edit" />','#phone_data', '');">Add</a> --%>
									</c:when>
									
									<c:otherwise>
										<a href="javascript:void(0);" class="k-button k-button-noborder" onclick="return getAjaxForm('<c:url value="/app/personal/phone/${type}/edit" />','#phone_data', '');"><span class="k-icon k-i-pencil"></span>Edit</a><br/>
										<a href="javascript:void(0);" class="k-button k-button-noborder" onclick="return confirmAddressDeleteDialog('delete-phone', '<c:url value="/app/personal/phone/${type}/delete" />')"><span class="k-icon k-i-close"></span>Delete</a>
									</c:otherwise>
								</c:choose>
							</c:otherwise>
						</c:choose>
					</div>
					</c:if>
				</div>
			</c:if>
		</c:forEach>
	</form:form>
	
	<c:if test="${addressForm != null }">
	<script type="text/javascript">
		$(document).ready(function(){
			if (typeof(console) != 'undefined') console.log("loading countries and states");
			initAddress();	
			initDateFields('future');
		});
	</script>
	</c:if>
	
</div>
<div id="delete-phone-dialog" class="dialog">
	<div id="delete-phone-dialog-content">
	Are you sure you want to delete phone?
	</div>
</div>

