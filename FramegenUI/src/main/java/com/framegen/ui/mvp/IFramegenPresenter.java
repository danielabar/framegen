package com.framegen.ui.mvp;

import java.util.List;

import com.framegen.api.response.ResponseVO;
import com.framegen.ui.apibridge.IFramegenUIService;
import com.framegen.ui.main.FileOpenDefaultsVO;

public interface IFramegenPresenter {
	
	public FileOpenDefaultsVO getFileOpenDefaults();
	
	public void setFileOpenDefaultsVO (FileOpenDefaultsVO fileOpenDefaults);
	
	public IFramegenModel getModel();
	
	public void setModel(IFramegenModel frameGenModel);
	
	public IFramegenView getView();
	
	public void setView(IFramegenView framegenView);
	
	public void run();
	
	public void generateFrames();
	
	public void updateFinalProgress(ResponseVO responseVO);
	
	public void updateProgress(List<String> messages);
	
	public void initProgressIndicators(Integer max);
	
	public void updateProgressBar(Integer increment);

	public void setFramegenUIService(IFramegenUIService framegenUIService);

	public IFramegenUIService getFramegenUIService();

}