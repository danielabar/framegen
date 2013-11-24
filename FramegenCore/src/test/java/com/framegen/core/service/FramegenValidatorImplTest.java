package com.framegen.core.service;

import static com.framegen.core.service.FramegenValidatorImpl.MAX_PREFIX_LENGTH;
import static com.framegen.core.service.FramegenValidatorImpl.PREFIX_LENGTH_EXCEEDED_MESSAGE_FORMAT;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;

import com.framegen.api.settings.ProgramSettingsVO;
import com.framegen.api.settings.SettingsVO;
import com.framegen.api.settings.option.FadeSettingsVO;
import com.framegen.api.settings.option.LineSettingsVO;
import com.framegen.core.FramegenCoreTestCase;
import com.framegen.core.validation.IOptionValidator;
import com.framegen.core.validation.IOptionValidatorFactory;

public class FramegenValidatorImplTest extends FramegenCoreTestCase {
	
	private FramegenValidatorImpl fixture;
	private IOptionValidatorFactory optionValidatorFactory;
	private IOptionValidator optionValidator;

	@Override
	@Before
	public void setUp() throws Exception {
		super.setUp();
		optionValidatorFactory = mock(IOptionValidatorFactory.class);
		optionValidator = mock(IOptionValidator.class);
		fixture = new FramegenValidatorImpl();
		fixture.setOptionValidatorFactory(optionValidatorFactory);
	}
	
	@Test
	public void testValidate_settingsNull() {
		List<String> validate = fixture.validate(null);
		verifyZeroInteractions(optionValidatorFactory);
		assertEquals(1, validate.size());
	}
	
	@Test
	public void testValidateFileIsImage_fail() throws IOException, URISyntaxException {
		File file = getTdf().createImageFileFromResource("document", "docx", "test.docx");
		List<String> errorMessages = new ArrayList<String>();
		String errorMessage = "not image";
		
		fixture.validateFileIsImage(file, errorMessages, errorMessage );
		
		assertEquals(errorMessage, errorMessages.get(0));
	}
	
	@Test
	public void testValidateFileIsImage_success() throws IOException, URISyntaxException {
		File file = getTdf().createImageFileFromResource("base", "jpg", "base_canon.jpg");
		List<String> errorMessages = new ArrayList<String>();
		String errorMessage = "not image";
		
		fixture.validateFileIsImage(file, errorMessages, errorMessage );
		
		assertEquals(0, errorMessages.size());
	}
	
	@Test
	public void testProgramSettingsValid_optionSettingsInvalid() throws IOException {
		ProgramSettingsVO programSettings = getTdf().createValidProgramSettingsVO();
		LineSettingsVO lineSettings = new LineSettingsVO();;
		SettingsVO settingsVO = getTdf().createSettingsWithLine(programSettings, lineSettings);
		List<String> optionErrorMessages = Arrays.asList("something wrong with option settings");
		
		when(optionValidatorFactory.getOptionValidator(settingsVO)).thenReturn(optionValidator);
		when(optionValidator.validate(settingsVO)).thenReturn(optionErrorMessages);
		
		List<String> actualErrorMessages = fixture.validate(settingsVO);
		
		assertEquals(optionErrorMessages.get(0), actualErrorMessages.get(0));
		
		verify(optionValidatorFactory).getOptionValidator(settingsVO);
		verify(optionValidator).validate(settingsVO);
		
		verifyNoMoreInteractions(optionValidatorFactory);
		verifyNoMoreInteractions(optionValidator);
	}
	
	@Test
	public void testProgramSettingsValid_optionSettingsValid() throws IOException {
		ProgramSettingsVO programSettings = getTdf().createValidProgramSettingsVO();
		LineSettingsVO lineSettings = new LineSettingsVO();;
		SettingsVO settingsVO = getTdf().createSettingsWithLine(programSettings, lineSettings);
		List<String> optionErrorMessages = new ArrayList<String>();
		
		when(optionValidatorFactory.getOptionValidator(settingsVO)).thenReturn(optionValidator);
		when(optionValidator.validate(settingsVO)).thenReturn(optionErrorMessages);
		
		List<String> actualErrorMessages = fixture.validate(settingsVO);
		
		assertEquals(0, actualErrorMessages.size());
		
		verify(optionValidatorFactory).getOptionValidator(settingsVO);
		verify(optionValidator).validate(settingsVO);
		
		verifyNoMoreInteractions(optionValidatorFactory);
		verifyNoMoreInteractions(optionValidator);
	}
	
	@Test
	public void testProgramSettingsInvalid_optionSettingsInvalid() throws IOException {
		File baseImage = getTdf().createTempFile("base", "jpg");
		File overlayImage = getTdf().createTempFile("overlay", "png");
		File outputDir = getTdf().createNonExistingDir();
		String generatedImageNamePrefix = StringUtils.EMPTY;
		ProgramSettingsVO programSettings = getTdf().createProgramSettingsVO(baseImage, overlayImage, outputDir, generatedImageNamePrefix );
		LineSettingsVO lineSettings = new LineSettingsVO();
		SettingsVO settingsVO = getTdf().createSettingsWithLine(programSettings, lineSettings);
		List<String> optionErrorMessages = Arrays.asList("something wrong with option settings");
		
		when(optionValidatorFactory.getOptionValidator(settingsVO)).thenReturn(optionValidator);
		when(optionValidator.validate(settingsVO)).thenReturn(optionErrorMessages);
		
		List<String> actualErrorMessages = fixture.validate(settingsVO);
		
		assertEquals(3, actualErrorMessages.size());
		
		verify(optionValidatorFactory).getOptionValidator(settingsVO);
		verify(optionValidator).validate(settingsVO);
		
		verifyNoMoreInteractions(optionValidatorFactory);
		verifyNoMoreInteractions(optionValidator);
	}
	
	@Test
	public void testValidateOutputDirectory_valid() throws IOException {
		File baseImage = getTdf().createTempFile("base", "jpg");
		File overlayImage = getTdf().createTempFile("overlay", "png");
		File outputDir = getTdf().createTempDir();
		String generatedImageNamePrefix = StringUtils.EMPTY;
		ProgramSettingsVO programSettings = getTdf().createProgramSettingsVO(baseImage, overlayImage, outputDir, generatedImageNamePrefix );
		SettingsVO settingsVO = getTdf().createSettingsWithLine(programSettings , null);
		
		List<String> messages = new ArrayList<String>();
		fixture.validateOutputDirectory(settingsVO, messages);
		
		assertEquals(0, messages.size());
	}
	
	@Test
	public void testValidateOutputDitrectory_notExists() throws IOException {
		File baseImage = getTdf().createTempFile("base", "jpg");
		File overlayImage = getTdf().createTempFile("overlay", "png");
		File outputDir = getTdf().createNonExistingDir();
		String generatedImageNamePrefix = StringUtils.EMPTY;
		ProgramSettingsVO programSettings = getTdf().createProgramSettingsVO(baseImage, overlayImage, outputDir, generatedImageNamePrefix );
		SettingsVO settingsVO = getTdf().createSettingsWithLine(programSettings , null);
		
		List<String> messages = new ArrayList<String>();
		fixture.validateOutputDirectory(settingsVO, messages);
		
		assertEquals(1, messages.size());
	}

	@Test
	public void testValidatePrefix_empty() throws IOException {
		File baseImage = getTdf().createTempFile("base", "jpg");
		File overlayImage = getTdf().createTempFile("overlay", "png");
		File outputDir = getTdf().createTempDir();
		String generatedImageNamePrefix = StringUtils.EMPTY;
		ProgramSettingsVO programSettings = getTdf().createProgramSettingsVO(baseImage, overlayImage, outputDir, generatedImageNamePrefix );
		SettingsVO settingsVO = getTdf().createSettingsWithLine(programSettings , null);
		
		List<String> messages = new ArrayList<String>();
		fixture.validatePrefix(settingsVO, messages );
		
		assertEquals(1, messages.size());
	}
	
	@Test
	public void testValidatePrefix_tooLong() throws IOException {
		File baseImage = getTdf().createTempFile("base", "jpg");
		File overlayImage = getTdf().createTempFile("overlay", "png");
		File outputDir = getTdf().createTempDir();
		String generatedImageNamePrefix = getTdf().createString("a", MAX_PREFIX_LENGTH + 1);
		ProgramSettingsVO programSettings = getTdf().createProgramSettingsVO(baseImage, overlayImage, outputDir, generatedImageNamePrefix );
		SettingsVO settingsVO = getTdf().createSettingsWithLine(programSettings , null);
		
		List<String> messages = new ArrayList<String>();
		fixture.validatePrefix(settingsVO, messages );
		
		assertEquals(1, messages.size());
		
		String expectedMessage = String.format(PREFIX_LENGTH_EXCEEDED_MESSAGE_FORMAT, MAX_PREFIX_LENGTH, 1);
		String actualMessage = messages.get(0);
		assertEquals(expectedMessage, actualMessage);
	}
	
	@Test
	public void testValidatePrefix_validMaxLength() throws IOException {
		File baseImage = getTdf().createTempFile("base", "jpg");
		File overlayImage = getTdf().createTempFile("overlay", "png");
		File outputDir = getTdf().createTempDir();
		String generatedImageNamePrefix = getTdf().createString("a", MAX_PREFIX_LENGTH);
		ProgramSettingsVO programSettings = getTdf().createProgramSettingsVO(baseImage, overlayImage, outputDir, generatedImageNamePrefix );
		SettingsVO settingsVO = getTdf().createSettingsWithLine(programSettings , null);
		
		List<String> messages = new ArrayList<String>();
		fixture.validatePrefix(settingsVO, messages );
		
		assertEquals(0, messages.size());
	}
	
	@Test
	public void testValidatePrefix_valid() throws IOException {
		File baseImage = getTdf().createTempFile("base", "jpg");
		File overlayImage = getTdf().createTempFile("overlay", "png");
		File outputDir = getTdf().createTempDir();
		String generatedImageNamePrefix = "testPrefix_";
		ProgramSettingsVO programSettings = getTdf().createProgramSettingsVO(baseImage, overlayImage, outputDir, generatedImageNamePrefix );
		SettingsVO settingsVO = getTdf().createSettingsWithLine(programSettings , null);
		
		List<String> messages = new ArrayList<String>();
		fixture.validatePrefix(settingsVO, messages );
		
		assertEquals(0, messages.size());
	}
	
	@Test
	public void testOverlayImage_notSpecified_isRequired() throws IOException {
		File baseImage = getTdf().createTempFile("base", "jpg");
		File overlayImage = null;
		File outputDir = getTdf().createTempDir();
		String generatedImageNamePrefix = "testPrefix_";
		ProgramSettingsVO programSettings = getTdf().createProgramSettingsVO(baseImage, overlayImage, outputDir, generatedImageNamePrefix );
		LineSettingsVO lineSettings = getTdf().createLineSettings(1, 1, 20, 20, Double.valueOf("1"), null, null, null);
		SettingsVO settingsVO = getTdf().createSettingsWithLine(programSettings , lineSettings );
		
		List<String> messages = new ArrayList<String>();
		fixture.validateOverlayImage(settingsVO, messages);
		
		assertEquals(1, messages.size());
	}
	
	@Test
	public void testOverlayImage_notSpecified_notRequired() throws IOException {
		File baseImage = getTdf().createTempFile("base", "jpg");
		File overlayImage = null;
		File outputDir = getTdf().createTempDir();
		String generatedImageNamePrefix = "testPrefix_";
		ProgramSettingsVO programSettings = getTdf().createProgramSettingsVO(baseImage, overlayImage, outputDir, generatedImageNamePrefix );
		FadeSettingsVO fadeSettings = getTdf().createFadeSettings(20);
		SettingsVO settingsVO = getTdf().createSettingsWithFade(programSettings, fadeSettings);
		
		List<String> messages = new ArrayList<String>();
		fixture.validateOverlayImage(settingsVO, messages);
		
		assertEquals(0, messages.size());
	}
	
	@Test
	public void testOverlayImage_isSpecified_isRequired() throws IOException {
		File baseImage = getTdf().createTempFile("base", "jpg");
		File overlayImage = getTdf().createTempFile("overlay", "png");
		File outputDir = getTdf().createTempDir();
		String generatedImageNamePrefix = "testPrefix_";
		ProgramSettingsVO programSettings = getTdf().createProgramSettingsVO(baseImage, overlayImage, outputDir, generatedImageNamePrefix );
		LineSettingsVO lineSettings = getTdf().createLineSettings(1, 1, 20, 20, Double.valueOf("1"), null, null, null);
		SettingsVO settingsVO = getTdf().createSettingsWithLine(programSettings, lineSettings);
		
		List<String> messages = new ArrayList<String>();
		fixture.validateOverlayImage(settingsVO, messages);
		
		assertEquals(0, messages.size());
	}
	
	@Test
	public void testOverlayImage_isSpecified_notRequired() throws IOException {
		File baseImage = getTdf().createTempFile("base", "jpg");
		File overlayImage = getTdf().createTempFile("overlay", "png");
		File outputDir = getTdf().createTempDir();
		String generatedImageNamePrefix = "testPrefix_";
		ProgramSettingsVO programSettings = getTdf().createProgramSettingsVO(baseImage, overlayImage, outputDir, generatedImageNamePrefix );
		FadeSettingsVO fadeSettings = getTdf().createFadeSettings(20);
		SettingsVO settingsVO = getTdf().createSettingsWithFade(programSettings, fadeSettings);
		
		List<String> messages = new ArrayList<String>();
		fixture.validateOverlayImage(settingsVO, messages);
		
		assertEquals(0, messages.size());
	}

}
