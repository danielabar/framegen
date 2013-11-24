package com.framegen.core.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.junit.Before;
import org.junit.Test;

import com.framegen.api.settings.ProgramSettingsVO;
import com.framegen.api.settings.SettingsVO;
import com.framegen.api.settings.option.LineSettingsVO;
import com.framegen.core.FramegenCoreTestCase;

public class DiskSpaceValidatorImplTest extends FramegenCoreTestCase {
	
	private DiskSpaceValidatorImpl fixture;
	private File mockOutputDir;

	@Override
	@Before
	public void setUp() throws Exception {
		super.setUp();
		mockOutputDir = mock(File.class);
		fixture = new DiskSpaceValidatorImpl();
	}

	@Test
	public void testValidateDiskSpace() throws IOException, URISyntaxException {
		File baseImage = getTdf().createImageFileFromResource("base", "jpg", "base_canon.jpg");
		File overlayImage = getTdf().createImageFileFromResource("overlay", "png", "overlay_dad.png");
		ProgramSettingsVO programSettings = getTdf().createProgramSettingsVO(baseImage, overlayImage, mockOutputDir, "test_");
		
		LineSettingsVO lineSettings = getTdf().createLineSettings(900, 800, 50, 25, Double.valueOf("90"), null, null, null);
		SettingsVO settings = getTdf().createSettingsWithLine(programSettings, lineSettings);
		
		Long usableSpace = convertGigsToBytes(Double.valueOf("500")).longValue();
		when(mockOutputDir.getUsableSpace()).thenReturn(usableSpace);
		
		Double allFrameSizeBytes = convertMegsToBytes(Double.valueOf("50"));
		List<String> validateDiskSpace = fixture.validateDiskSpace(allFrameSizeBytes, settings);
		
		verify(mockOutputDir).getUsableSpace();
		
		assertTrue(CollectionUtils.isEmpty(validateDiskSpace));
	}
	
	@Test
	public void testValidateDiskSpace_exceeds() throws IOException, URISyntaxException {
		File baseImage = getTdf().createImageFileFromResource("base", "jpg", "base_canon.jpg");
		File overlayImage = getTdf().createImageFileFromResource("overlay", "png", "overlay_dad.png");
		ProgramSettingsVO programSettings = getTdf().createProgramSettingsVO(baseImage, overlayImage, mockOutputDir, "test_");
		
		LineSettingsVO lineSettings = getTdf().createLineSettings(900, 800, 50, 25, Double.valueOf("90"), null, null, null);
		SettingsVO settings = getTdf().createSettingsWithLine(programSettings, lineSettings);
		
		Long usableSpace = convertGigsToBytes(Double.valueOf("500")).longValue();
		when(mockOutputDir.getUsableSpace()).thenReturn(usableSpace);
		
		Double allFrameSizeBytes = convertGigsToBytes(Double.valueOf("200"));
		List<String> validateDiskSpace = fixture.validateDiskSpace(allFrameSizeBytes, settings);
		
		verify(mockOutputDir).getUsableSpace();
		
		assertEquals(1, validateDiskSpace.size());
		assertEquals("Frame generation would use approximately 40% of free disk space which exceeds max allowed of 30%", validateDiskSpace.get(0));
	}

	protected Double convertMegsToBytes(Double megs) {
		return megs * 1024 * 1024;
	}
	
	protected Double convertGigsToBytes(Double gigs) {
		return gigs * 1024 * 1024 * 1024;
	}

}
