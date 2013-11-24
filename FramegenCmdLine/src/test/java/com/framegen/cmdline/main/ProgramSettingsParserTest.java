package com.framegen.cmdline.main;

import static com.framegen.cmdline.settings.LineSettings.DEFAULT_MOVE_INCREMENT;
import static com.framegen.cmdline.settings.ProgramSettings.DEFAULT_GENERATED_IMAGE_NAME_PREFIX;
import static com.framegen.cmdline.settings.RotationSettings.DEFAULT_DEGREES;
import static com.framegen.cmdline.settings.RotationSettings.DEFAULT_STEP;
import static com.framegen.cmdline.settings.RotationSettings.DEFAULT_X_POSITION;
import static com.framegen.cmdline.settings.RotationSettings.DEFAULT_Y_POSITION;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.beust.jcommander.ParameterException;
import com.framegen.api.settings.FrameOption;
import com.framegen.api.settings.SettingsVO;

public class ProgramSettingsParserTest {
	private static final String EXPECTED_REQUIRED_MESSAGE_PREFIX_MULTIPLE = "The following options are required";
	private static final String EXPECTED_FILE_NOT_FOUND_MESSAGE = "Value of %s provided for -%s could not be found";
	private static final String EXPECTED_OUTPUT_DIR_NOT_DIR_MESSAGE = "Value of %s provided for -%s is not a directory";
	
	private ProgramSettingsParser fixture = new ProgramSettingsParser();

	@Before
	public void setUp() throws Exception {
		fixture = new ProgramSettingsParser();
	}
	
	@Test
	public void testParse_help() {
		String[] argv = { "--help" };
		
		SettingsVO result = fixture.parseAll(argv);
		
		assertNotNull(result);
		assertTrue(result.getProgramSettings().isHelp());
		
		FrameOption frameOption = null;
		try {
			frameOption = result.getFrameOption();
		} catch (UnsupportedOperationException e) {
			assertNull(frameOption);
		}
	}
	
	@Test
	public void testParse_validProgramSettings_noOptionSpecified() {
		String[] argv = { "-baseImage", "src\\test\\resources\\base.jpg", "-overlayImage", "src\\test\\resources\\overlay.png", "-outputDir", "src\\test\\resources" };
		
		SettingsVO result = fixture.parseAll(argv);
		
		assertNotNull(result);
		assertNotNull(result.getProgramSettings().getBaseImage());
		assertNotNull(result.getProgramSettings().getOverlayImage());
		assertTrue(result.getProgramSettings().getBaseImage().exists());
		assertTrue(result.getProgramSettings().getOverlayImage().exists());
		assertEquals(DEFAULT_GENERATED_IMAGE_NAME_PREFIX, result.getProgramSettings().getGeneratedImageNamePrefix());
		
		assertNull("LINE settings were not specified", result.getLineSettings());
		assertNull("ROTATE settings were not specified", result.getRotationSettings());
		
		FrameOption frameOption = null;
		try {
			frameOption = result.getFrameOption();
		} catch (UnsupportedOperationException e) {
			assertNull(frameOption);
		}
	}
	
	@Test
	public void testParse_valid_withLineSetting_defaults() {
		String[] argv = { "-baseImage", "src\\test\\resources\\base.jpg", "-overlayImage", "src\\test\\resources\\overlay.png", "-outputDir", "src\\test\\resources",
				"LINE", "--xStart=5", "--yStart=7", "--xEnd=20", "--yEnd=78"};
		
		SettingsVO result = fixture.parseAll(argv);
		
		assertNotNull(result);
		assertNotNull(result.getProgramSettings().getBaseImage());
		assertNotNull(result.getProgramSettings().getOverlayImage());
		assertTrue(result.getProgramSettings().getBaseImage().exists());
		assertTrue(result.getProgramSettings().getOverlayImage().exists());
		assertEquals(DEFAULT_GENERATED_IMAGE_NAME_PREFIX, result.getProgramSettings().getGeneratedImageNamePrefix());
		
		assertNotNull("LINE settings were specified", result.getLineSettings());
		assertEquals(FrameOption.LINE, result.getFrameOption());
		assertEquals("xStart", Integer.valueOf(5), result.getLineSettings().getxStart());
		assertEquals("yStart", Integer.valueOf(7), result.getLineSettings().getyStart());
		assertEquals(DEFAULT_MOVE_INCREMENT, result.getLineSettings().getMoveIncrement());
		assertFalse("Using default settings, no scaling will be applied", result.getLineSettings().scalingRequested());
		
		assertNull("ROTATE settings were not specified", result.getRotationSettings());
	}
	
	@Test
	public void testParse_valid_withLineSetting_overrideDefaults() {
		String[] argv = { "-baseImage", "src\\test\\resources\\base.jpg", "-overlayImage", "src\\test\\resources\\overlay.png", "-outputDir", "src\\test\\resources",
				"LINE", "--xStart=5", "--yStart=7", "--xEnd=20", "--yEnd=78", "--moveInc=10", "--xScale=3", "--yScale=9"};
		
		SettingsVO result = fixture.parseAll(argv);
		
		assertNotNull(result);
		assertNotNull(result.getProgramSettings().getBaseImage());
		assertNotNull(result.getProgramSettings().getOverlayImage());
		assertTrue(result.getProgramSettings().getBaseImage().exists());
		assertTrue(result.getProgramSettings().getOverlayImage().exists());
		assertEquals(DEFAULT_GENERATED_IMAGE_NAME_PREFIX, result.getProgramSettings().getGeneratedImageNamePrefix());
		
		assertNotNull("LINE settings were specified", result.getLineSettings());
		assertEquals(FrameOption.LINE, result.getFrameOption());
		assertEquals("xStart", Integer.valueOf(5), result.getLineSettings().getxStart());
		assertEquals("yStart", Integer.valueOf(7), result.getLineSettings().getyStart());
		assertEquals("move increment", Double.valueOf(10L), result.getLineSettings().getMoveIncrement());
		assertTrue("scaling was requested", result.getLineSettings().scalingRequested());
		
		assertNull("ROTATE settings were not specified", result.getRotationSettings());
	}
	
	@Test
	public void testParse_valid_withRotationSetting_defaults() {
		String[] argv = { "-baseImage", "src\\test\\resources\\base.jpg", "-overlayImage", "src\\test\\resources\\overlay.png", "-outputDir", "src\\test\\resources",
				"ROTATE"};
		
		SettingsVO result = fixture.parseAll(argv);
		
		assertNotNull(result);
		assertNotNull(result.getProgramSettings().getBaseImage());
		assertNotNull(result.getProgramSettings().getOverlayImage());
		assertTrue(result.getProgramSettings().getBaseImage().exists());
		assertTrue(result.getProgramSettings().getOverlayImage().exists());
		assertEquals(DEFAULT_GENERATED_IMAGE_NAME_PREFIX, result.getProgramSettings().getGeneratedImageNamePrefix());
		
		assertNotNull("ROTATE settings were specified", result.getRotationSettings());
		assertEquals(FrameOption.ROTATE, result.getFrameOption());
		assertEquals(DEFAULT_X_POSITION, result.getRotationSettings().getxPosition());
		assertEquals(DEFAULT_Y_POSITION, result.getRotationSettings().getyPosition());
		assertEquals(DEFAULT_DEGREES, result.getRotationSettings().getDegrees());
		assertEquals(DEFAULT_STEP, result.getRotationSettings().getStep());
		
		assertNull("LINE settings were not specified", result.getLineSettings());
	}
	
	@Test
	public void testParse_withLineSettings_missingXStart() {
		String[] argv = { "-baseImage", "src\\test\\resources\\base.jpg", "-overlayImage", "src\\test\\resources\\overlay.png", "-outputDir", "src\\test\\resources",
				"LINE", "--yStart=7"};
		try {
			fixture.parseAll(argv);
		} catch (ParameterException e) {
			assertTrue(e.getMessage().contains(EXPECTED_REQUIRED_MESSAGE_PREFIX_MULTIPLE));
			assertTrue(e.getMessage().contains("xStart"));
		}
	}
	
	@Test
	public void testParse_valid_overrideDefaults() {
		String[] argv = { "-baseImage", "src\\test\\resources\\base.jpg", "-overlayImage", "src\\test\\resources\\overlay.png", "-outputDir", "src\\test\\resources",
				"-genImagePrefix", "together_"};
		SettingsVO result = fixture.parseAll(argv);
		assertNotNull(result);
		assertNotNull(result.getProgramSettings().getBaseImage());
		assertNotNull(result.getProgramSettings().getOverlayImage());
		assertTrue(result.getProgramSettings().getBaseImage().exists());
		assertTrue(result.getProgramSettings().getOverlayImage().exists());
		assertEquals("together_", result.getProgramSettings().getGeneratedImageNamePrefix());
	}
	
	@Test
	public void testParse_missingRequiredArgs() {
		String[] argv = { "-baseImage", "src\\test\\resources\\base.jpg" };
		try {
			fixture.parseAll(argv);
		} catch (ParameterException e) {
			assertTrue(e.getMessage().contains(EXPECTED_REQUIRED_MESSAGE_PREFIX_MULTIPLE));
			assertTrue(e.getMessage().contains("overlayImage"));
			assertTrue(e.getMessage().contains("outputDir"));
			assertFalse("optional", e.getMessage().contains("genImagePrefix"));
		}
	}
	
	@Test
	public void testParse_badPathBaseImage() {
		String[] argv = { "-baseImage", "src\\test\\resources\\notexists.jpg" };
		try {
			fixture.parseAll(argv);
		} catch (ParameterException e) {
			assertEquals(String.format(EXPECTED_FILE_NOT_FOUND_MESSAGE, "src\\test\\resources\\notexists.jpg", "baseImage"), e.getMessage());
		}
	}
	
	@Test
	public void testParse_outputDirNotADir() {
		String[] argv = { "-baseImage", "src\\test\\resources\\base.jpg", "-overlayImage", "src\\test\\resources\\overlay.png", "-outputDir", "src\\test\\resources\\overlay.png" };
		try {
			fixture.parseAll(argv);
		} catch (ParameterException e) {
			assertEquals(String.format(EXPECTED_OUTPUT_DIR_NOT_DIR_MESSAGE, "src\\test\\resources\\overlay.png", "outputDir"), e.getMessage());
		}
	}

}
