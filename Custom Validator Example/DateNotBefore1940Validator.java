package edu.ucla.dt.studentweb.svc.dto.validation;

import java.util.Calendar;
import java.util.Date;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class DateNotBefore1940Validator implements ConstraintValidator<DateNotBefore1940, Object> {
	
private String datefield;
	
	@Override 
	public void initialize(final DateNotBefore1940 dateNotBefore1940) {	
		datefield = dateNotBefore1940.dateFields();
	}

	@Override
	public boolean isValid(final Object value, final ConstraintValidatorContext context) {
		if (value == null) {
           return true;
       }
		
		final Date dateFieldValue = ConstraintValidatorHelper.getPropertyValue(Date.class, datefield, value);
		
		if (dateFieldValue == null)
			return true;
			
			   Calendar c = Calendar.getInstance();
			   c.setTime(dateFieldValue);
			   int fieldYear = c.get(Calendar.YEAR);
			   int year = 1940;
			   
			   if (fieldYear < year) {
				   
			    	   context.disableDefaultConstraintViolation();
			    	   context.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate())
			               .addNode(datefield)
			               .addConstraintViolation();
			               return false;
			       }
	   return true;
	}
}