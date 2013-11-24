package com.framegen.ui.mvp;

import java.util.List;

import com.framegen.api.response.NumberFramesResponseVO;
import com.framegen.api.response.ResponseVO;
import com.framegen.ui.apibridge.FramegenWorker;
import com.framegen.ui.apibridge.IFramegenUIService;
import com.framegen.ui.main.FileOpenDefaultsVO;
import com.framegen.ui.util.MessageCreator;

public class FramegenPresenterImpl implements IFramegenPresenter {
	
	private FileOpenDefaultsVO fileOpenDefaults;
	private IFramegenModel framegenModel;
	private IFramegenView framegenView;
	private IFramegenUIService framegenUIService;
	
	@Override
	public IFramegenUIService getFramegenUIService() {
		return framegenUIService;
	}

	@Override
	public void setFramegenUIService(IFramegenUIService framegenUIService) {
		this.framegenUIService = framegenUIService;
	}

	@Override
	public FileOpenDefaultsVO getFileOpenDefaults() {
		return fileOpenDefaults;
	}

	@Override
	public void setFileOpenDefaultsVO(FileOpenDefaultsVO fileOpenDefaults) {
		this.fileOpenDefaults = fileOpenDefaults;
	}

	@Override
	public IFramegenModel getModel() {
		return this.framegenModel;
	}

	@Override
	public void setModel(IFramegenModel framegenModel) {
		this.framegenModel = framegenModel;
	}

	@Override
	public IFramegenView getView() {
		return this.framegenView;
	}

	@Override
	public void setView(IFramegenView framegenView) {
		this.framegenView = framegenView;
	}

	@Override
	public void run() {
//		initializeModelWithDefaults();
		
		framegenView.setPresenter(this);
		framegenView.updateViewFromModel();	
		framegenView.open();
	}

	// experiment
//	private void initializeModelWithDefaults() {
//		framegenModel.setGeneratedImageNamePrefix("combined_");	
//		
//		// experiment with LINE prepopulation - but would only happen for one tab, would need repeated calls to framegenView.updateViewFromModel();	
//		LineSettingsVO vo = new LineSettingsVO();
//		vo.setxStart(1);
//		IOptionSettingsVO optionSettingsVO = vo;
//		framegenModel.setOptionSettingsVO(optionSettingsVO);
//	}

	@Override
	public void generateFrames() {
		framegenView.updateModelFromView();
		initProgressIndicators();
		generateFramesInTheBackground();
		framegenView.showProgressDialog();
	}

	private void generateFramesInTheBackground() {
		new FramegenWorker(getModel(), this).execute();
	}

	private void initProgressIndicators() {
		NumberFramesResponseVO numberOfFramesResponseVO = framegenUIService.getNumberOfFrames(getModel());
		if (numberOfFramesResponseVO.isValid()) {
			initProgressIndicators(numberOfFramesResponseVO.getNumberOfFrames());
		}
	}

	@Override
	public void updateFinalProgress(ResponseVO responseVO) {
		if (responseVO.isValid()) {
			handleValidResponse(responseVO); 
		} else {
			handleInvalidResponse(responseVO);
		}
	}

	@Override
	public void updateProgress(List<String> messages) {
		framegenView.updateProgressLabel(messages);
	}

	@Override
	public void initProgressIndicators(Integer max) {
		framegenView.initProgressIndicators(max);
	}

	@Override
	public void updateProgressBar(Integer increment) {
		framegenView.updateProgressBar(increment);
	}

	private void handleValidResponse(ResponseVO responseVO) {
		framegenView.updateFinalProgressLabel(getInfo(responseVO));
	}

	private String getInfo(ResponseVO responseVO) {
		return "Frames generated successfully";
	}

	private void handleInvalidResponse(ResponseVO responseVO) {
		if (responseVO.hasException()) {
			framegenView.showException(responseVO.getException());
		}
		if (responseVO.hasError()) {
			framegenView.showError(new MessageCreator().apply(responseVO.getErrorMessages()));
		}
	}

	
}
