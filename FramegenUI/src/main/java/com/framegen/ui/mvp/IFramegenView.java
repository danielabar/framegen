package com.framegen.ui.mvp;

import java.util.List;

import com.framegen.api.settings.FrameOption;


public interface IFramegenView {

	public IFramegenPresenter getPresenter();
	
	public void setPresenter(IFramegenPresenter framegenPresenter);
	
	public void updateModelFromView();

	public void updateViewFromModel();

	public void open();

	public void close();
	
	public void enableDisableOverlaySelection(FrameOption frameOption);

	public void showError(String errorMessage);
    
	public void showException(Exception e);
    
	public void showProgressDialog();
    
	public void updateFinalProgressLabel(String updateText);
    
	public void updateProgressLabel(List<String> messages);
    
	public void initProgressIndicators(Integer max);
    
	public void updateProgressBar(Integer increment);
}
