package com.framegen.ui.main;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.*;

import java.io.File;
import java.util.Properties;

import org.junit.Before;
import org.junit.Test;

import com.framegen.ui.FramegenUITestCase;

public class FramegenPropertiesLoaderTest extends FramegenUITestCase {
	
	private FramegenPropertiesLoader fixture;
	private DirectoryValidator directoryValidator;

	@Override
	@Before
	public void setUp() throws Exception {
		super.setUp();
		directoryValidator = mock(DirectoryValidator.class);
		fixture = new FramegenPropertiesLoader(directoryValidator);
	}

	@Test
	public void testGetFileOpenDefaults() {
		String base = "C:\\user\\base";
		String overlay = "C:\\user\\overlay";
		String output = "C:\\user\\output";
		Properties props = getTdf().createProperties(base, overlay, output);
		
		File baseDir = mock(File.class);
		File overlayDir = mock(File.class);
		File outputDir = mock(File.class);
		
		when(directoryValidator.isDirectory(base)).thenReturn(baseDir);
		when(directoryValidator.isDirectory(overlay)).thenReturn(overlayDir);
		when(directoryValidator.isDirectory(output)).thenReturn(outputDir);
		
		FileOpenDefaultsVO fileOpenDefaults = fixture.getFileOpenDefaults(props );
		assertEquals("base dir", baseDir, fileOpenDefaults.getBaseImageOpenDir());
		assertEquals("overlay dir", overlayDir, fileOpenDefaults.getOverlayImageOpenDir());
		assertEquals("output dir", outputDir, fileOpenDefaults.getOutputOpenDir());
		
		verify(directoryValidator).isDirectory(base);
		verify(directoryValidator).isDirectory(overlay);
		verify(directoryValidator).isDirectory(output);
	}
	
	@Test
	public void testGetFileOpenDefaults_overlayPropMissing() {
		String base = "C:\\user\\base";
		String overlay = null;
		String output = "C:\\user\\output";
		Properties props = getTdf().createProperties(base, overlay, output);
		
		File baseDir = mock(File.class);
		File outputDir = mock(File.class);
		
		when(directoryValidator.isDirectory(base)).thenReturn(baseDir);
		when(directoryValidator.isDirectory(output)).thenReturn(outputDir);
		
		FileOpenDefaultsVO fileOpenDefaults = fixture.getFileOpenDefaults(props );
		assertEquals("base dir", baseDir, fileOpenDefaults.getBaseImageOpenDir());
		assertNull("overlay dir", fileOpenDefaults.getOverlayImageOpenDir());
		assertEquals("output dir", outputDir, fileOpenDefaults.getOutputOpenDir());
		
		verify(directoryValidator).isDirectory(base);
		verify(directoryValidator).isDirectory(output);
		verify(directoryValidator, never()).isDirectory(overlay);
	}
	
	@Test
	public void testGetFileOpenDefaults_noProps() {
		FileOpenDefaultsVO fileOpenDefaults = fixture.getFileOpenDefaults(null);
		
		verifyNoMoreInteractions(directoryValidator);
		
		assertNotNull(fileOpenDefaults);
		assertNull(fileOpenDefaults.getBaseImageOpenDir());
		assertNull(fileOpenDefaults.getOverlayImageOpenDir());
		assertNull(fileOpenDefaults.getOutputOpenDir());
	}

}
