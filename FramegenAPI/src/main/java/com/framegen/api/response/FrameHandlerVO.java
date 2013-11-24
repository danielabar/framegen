package com.framegen.api.response;

import java.util.concurrent.CompletionService;

public class FrameHandlerVO {

	private CompletionService<TaskResultVO> completionService;
	private Integer numTasks;
	
	public CompletionService<TaskResultVO> getCompletionService() {
		return completionService;
	}
	public void setCompletionService(CompletionService<TaskResultVO> completionService) {
		this.completionService = completionService;
	}
	
	public Integer getNumTasks() {
		return numTasks;
	}
	public void setNumTasks(Integer numTasks) {
		this.numTasks = numTasks;
	}
	
}
