package com.junit;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestReporter;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.OS;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
//@TestInstance(TestInstance.Lifecycle.PER_METHOD)//By default
@DisplayName("When running MathUtilsTest..")
public class MathUtilsTest {
	MathUtils mathUtils;
	TestInfo testInfo;
	TestReporter testReporter;

	@BeforeAll
	void beforeAllInit() {
		System.out.println("This method will get called before all");
	}

	@BeforeEach
	void init(TestInfo testInfo, TestReporter testReporter) {
		this.testInfo=testInfo;
		this.testReporter=testReporter;
		mathUtils = new MathUtils();
		testReporter.publishEntry("Running "+testInfo.getDisplayName()+" with tagName " + testInfo.getTags());
	}

	@AfterEach
	void cleanup() {
		System.out.println("Clean up..");
	}

	@Nested
	@DisplayName("test Add Method")
	@Tag("Math")
	class AddTest {
		@Test
		@DisplayName("When adding the two positive numbers")
		void testAddPositive() {
			int expected = 2;
			int actual = mathUtils.add(1, 1);
			assertEquals(expected, actual,()->"should return the right sum");
		}

		@Test
		@DisplayName("When adding the two negative numbers")
		void testAddNegative() {
			int expected = -2;
			int actual = mathUtils.add(-1, -1);
			assertEquals(expected, actual, ()-> "should return the right sum");
		}
	}

	@Test
	@DisplayName("test Multiply Method")
	@Tag("Math")
	void testMultiply() {
		assertAll(() -> assertEquals(4, mathUtils.multiply(2, 2)), () -> assertEquals(0, mathUtils.multiply(2, 0)),
				() -> assertEquals(-2, mathUtils.multiply(2, -1)));
	}

	@Test
	@EnabledOnOs(OS.WINDOWS)
	@DisplayName("test Divide method")
	@Tag("Math")
	void testDivide() {
		boolean isServerUp = true;
		assumeTrue(isServerUp);
		assertThrows(ArithmeticException.class, () -> mathUtils.divide(1, 0), ()->"Divide by 0 should throw exception");
	}

	@RepeatedTest(3)
	@DisplayName("test ComputeCircleArea method")
	@Tag("Circle")
	void testComputeCircleArea() {
		assertEquals(314.1592653589793, mathUtils.computeCircleArea(10), ()->"Should return right circle area");
	}

	@Test
	@DisplayName("test TTD method..should not run")
	@Disabled
	void testTTD() {
		fail("This test should disabled");
	}

}
