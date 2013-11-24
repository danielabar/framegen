package com.framegen.core.validation;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.framegen.core.FramegenCoreTestCase;

public class OptionValidatorBaseTest extends FramegenCoreTestCase {
	
	private OptionValidatorBase fixture;

	@Override
	@Before
	public void setUp() throws Exception {
		super.setUp();
		fixture = new OptionValidatorBase();
	}

	@Test
	public void testValidateDoubleGreaterThanZero_greaterZero() {
		Double value = Double.valueOf("23");
		String errorMessage = "error";
		List<String> errors = new ArrayList<String>();
		
		fixture.validateDoubleGreaterThanZero(value, errorMessage, errors);
		
		assertEquals(0, errors.size());
	}
	
	@Test
	public void testValidateDoubleGreaterThanZero_smallerZero() {
		Double value = Double.valueOf("-2");
		String errorMessage = "error";
		List<String> errors = new ArrayList<String>();
		
		fixture.validateDoubleGreaterThanZero(value, errorMessage, errors);
		
		assertEquals(1, errors.size());
		assertEquals(errorMessage, errors.get(0));
	}
	
	@Test
	public void testValidateDoubleGreaterThanZero_isZero() {
		Double value = Double.valueOf("0");
		String errorMessage = "error";
		List<String> errors = new ArrayList<String>();
		
		fixture.validateDoubleGreaterThanZero(value, errorMessage, errors);
		
		assertEquals(1, errors.size());
		assertEquals(errorMessage, errors.get(0));
	}
	
	@Test
	public void testValidateDoubleGreaterThanZero_null() {
		Double value = null;
		String errorMessage = "error";
		List<String> errors = new ArrayList<String>();
		
		fixture.validateDoubleGreaterThanZero(value, errorMessage, errors);
		
		assertEquals(0, errors.size());
	}

}
