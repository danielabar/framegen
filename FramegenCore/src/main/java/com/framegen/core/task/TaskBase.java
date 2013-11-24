package com.framegen.core.task;

import java.io.File;

import com.framegen.api.response.TaskResultVO;

public class TaskBase<V> {

	public TaskBase() {
		super();
	}

	protected TaskResultVO buildTaskResultVO(File output, Integer sequence) {
		TaskResultVO resultVO = new TaskResultVO();
		resultVO.setSequence(sequence);
		double frameSize = output.length();
		resultVO.setFrameSize(Double.valueOf(frameSize));
		return resultVO;
	}

}