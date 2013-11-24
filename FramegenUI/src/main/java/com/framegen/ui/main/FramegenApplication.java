package com.framegen.ui.main;

import java.util.Properties;

import com.framegen.ui.apibridge.FramegenUIServiceImpl;
import com.framegen.ui.mvp.*;

public class FramegenApplication {
	
	private static FramegenPropertiesLoader propertiesHelper = new FramegenPropertiesLoader();
	
	public static void main(String[] args) {
		java.awt.EventQueue.invokeLater(new Runnable() {

            @Override
			public void run() {
            	IFramegenModel framegenModel = new FramegenModelImpl();
            	IFramegenPresenter framegenPresenter = initializeFramegenPresenter(framegenModel);
                framegenPresenter.run();
            }

			private IFramegenPresenter initializeFramegenPresenter(IFramegenModel framegenModel) {
				IFramegenPresenter framegenPresenter = new FramegenPresenterImpl();
				framegenPresenter.setFramegenUIService(new FramegenUIServiceImpl());
				Properties framegenProps = propertiesHelper.initProperties();
				framegenPresenter.setFileOpenDefaultsVO(propertiesHelper.getFileOpenDefaults(framegenProps ));
                framegenPresenter.setModel(framegenModel);
                IFramegenView framegenView = new FramegenViewImpl();
                framegenPresenter.setView(framegenView);
				return framegenPresenter;
			}
        });

	}

}
