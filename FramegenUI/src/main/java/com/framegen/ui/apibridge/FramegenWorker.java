package com.framegen.ui.apibridge;

import java.util.List;

import javax.swing.SwingWorker;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import com.framegen.api.response.FramegenResponseVO;
import com.framegen.ui.mvp.IFramegenModel;
import com.framegen.ui.mvp.IFramegenPresenter;

public class FramegenWorker extends SwingWorker<FramegenResponseVO, String> {
	
	private final IFramegenModel model;
	private final IFramegenPresenter presenter;
	private final IFramegenUIService service;
	
	public FramegenWorker(IFramegenModel model, IFramegenPresenter presenter) {
		super();
		this.model = model;
		this.presenter = presenter;
		this.service = new FramegenUIServiceImpl();
	}

	@Override
	protected FramegenResponseVO doInBackground() throws Exception {
		FramegenResponseVO responseVO = service.generateFrames(model);
		
		if (responseVO.isValid()) {
			Integer numTasks = responseVO.getFrameHandlerVO().getNumTasks();
			for (int i = 0; i < numTasks; i++) {
				String status = service.getStatus(responseVO.getSettingsVO(), responseVO.getFrameHandlerVO());
				if (StringUtils.isNotEmpty(status)) {
					publish(status);
				}
			}
		}
		return responseVO;
	}
	
	@Override
	protected void done() {
		super.done();
		try {
			presenter.updateFinalProgress(get());	// get() returns final results of doInBackground()
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void process(List<String> chunks) {
		super.process(chunks);
		if (CollectionUtils.isNotEmpty(chunks)) {
			presenter.updateProgress(chunks);
			presenter.updateProgressBar(chunks.size());
		}
	}

}
