package com.internal.service.somruinternal.error;

//import com.example.springbootexceptionhandling.LowerCaseClassNameResolver;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.annotation.JsonTypeIdResolver;
import org.hibernate.validator.internal.engine.path.PathImpl;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import javax.validation.ConstraintViolation;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

//@JsonTypeInfo(include = JsonTypeInfo.As.WRAPPER_OBJECT, use = JsonTypeInfo.Id.CUSTOM, property = "error", visible = true)
//@JsonTypeIdResolver(LowerCaseClassNameResolver.class)
public
class ApiError {

    private HttpStatus status;
//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private Date timestamp;
    private String message;
    private String debugMessage;
    private List<ApiSubError> subErrors;
    private String sugestion;
    private final String DEFAULT_SUGGESTION = "Please contact support and share the debug message with them";
    private String errorCode;

    private ApiError() {
//        timestamp = LocalDateTime.now();
    	timestamp = new Date();
    }

    public ApiError(HttpStatus status) {
        this();
        this.status = status;
        errorCode = status.toString();
    }

    public ApiError(HttpStatus status, Throwable ex) {
        this();
        this.status = status;
        this.message = "Unexpected error";
        this.debugMessage = ex.getLocalizedMessage();
        this.sugestion = DEFAULT_SUGGESTION;
        errorCode = status.toString();
    }

    public ApiError(HttpStatus status, String message, Throwable ex) {
        this();
        this.status = status;
        this.message = message;
        this.debugMessage = ex.getLocalizedMessage();
        this.sugestion = DEFAULT_SUGGESTION;
        errorCode = status.toString();
    }

    private void addSubError(ApiSubError subError) {
        if (getSubErrors() == null) {
            setSubErrors(new ArrayList<>());
        }
        getSubErrors().add(subError);
    }

    private void addValidationError(String object, String field, Object rejectedValue, String message) {
        addSubError(new ApiValidationError(object, field, rejectedValue, message));
    }

    private void addValidationError(String object, String message) {
        addSubError(new ApiValidationError(object, message));
    }

    private void addValidationError(FieldError fieldError) {
        this.addValidationError(
                fieldError.getObjectName(),
                fieldError.getField(),
                fieldError.getRejectedValue(),
                fieldError.getDefaultMessage());
    }

    public void addValidationErrors(List<FieldError> fieldErrors) {
        fieldErrors.forEach(this::addValidationError);
    }

    private void addValidationError(ObjectError objectError) {
        this.addValidationError(
                objectError.getObjectName(),
                objectError.getDefaultMessage());
    }

    public void addValidationError(List<ObjectError> globalErrors) {
        globalErrors.forEach(this::addValidationError);
    }

    /**
     * Utility method for adding error of ConstraintViolation. Usually when a @Validated validation fails.
     *
     * @param cv the ConstraintViolation
     */
    private void addValidationError(ConstraintViolation<?> cv) {
        this.addValidationError(
                cv.getRootBeanClass().getSimpleName(),
                ((PathImpl) cv.getPropertyPath()).getLeafNode().asString(),
                cv.getInvalidValue(),
                cv.getMessage());
    }

    public void addValidationErrors(Set<ConstraintViolation<?>> constraintViolations) {
        constraintViolations.forEach(this::addValidationError);
    }

    /**
     * @return the status
     */
    public HttpStatus getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    /**
     * @return the timestamp
     */
    public Date getTimestamp() {
        return timestamp;
    }

    /**
     * @param timestamp the timestamp to set
     */
    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    /**
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * @param message the message to set
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * @return the debugMessage
     */
    public String getDebugMessage() {
        return debugMessage;
    }

    /**
     * @param debugMessage the debugMessage to set
     */
    public void setDebugMessage(String debugMessage) {
        this.debugMessage = debugMessage;
    }

    /**
     * @return the subErrors
     */
    public List<ApiSubError> getSubErrors() {
        return subErrors;
    }

    /**
     * @param subErrors the subErrors to set
     */
    public void setSubErrors(List<ApiSubError> subErrors) {
        this.subErrors = subErrors;
    }

	public String getSugestion() {
		return sugestion;
	}

	public void setSugestion(String sugestion) {
		this.sugestion = sugestion;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}


}
