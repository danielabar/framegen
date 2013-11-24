package com.framegen.api.response;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;

public class ResponseVO implements Serializable {

	private static final long serialVersionUID = -3098312077874223681L;
	
	
	
	private List<String> errorMessages = new ArrayList<String>();
	private List<String> infoMessages = new ArrayList<String>();
	private Exception exception;
	
	public List<String> getErrorMessages() {
		return errorMessages;
	}
	public void setErrorMessages(List<String> errorMessages) {
		this.errorMessages = errorMessages;
	}
	
	public void addErrorMessage(String errorMessage) {
		this.errorMessages.add(errorMessage);
	}
	
	public List<String> getInfoMessages() {
		return infoMessages;
	}
	public void setInfoMessages(List<String> infoMessages) {
		this.infoMessages = infoMessages;
	}
	
	public void addInfoMessage(String infoMessage) {
		this.infoMessages.add(infoMessage);
	}
	
	public Exception getException() {
		return exception;
	}
	public void setException(Exception exception) {
		this.exception = exception;
	}
	
	public boolean isValid() {
		return !(hasError()) && !(hasException());
	}
	
	public boolean hasError() {
		return CollectionUtils.isNotEmpty(errorMessages);
	}
	
	public boolean hasException() {
		return this.exception != null;
	}

	

}
