package com.league.matrix_app.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;

public class UtilsTest {

	@Test
	void testValidMatrix() throws Exception {
		String content = "1,2,3\n4,5,6\n7,8,9";
		MockMultipartFile file = new MockMultipartFile("file", "matrix.csv", "text/csv", content.getBytes());

		List<List<Integer>> matrix = Utils.parseCsvToMatrix(file);
		assertEquals(3, matrix.size());
		assertEquals(List.of(1, 2, 3), matrix.get(0));
		assertEquals(List.of(4, 5, 6), matrix.get(1));
		assertEquals(List.of(7, 8, 9), matrix.get(2));
	}

	@Test
	void testEmptyMatrixThrowsException() {
		MockMultipartFile file = new MockMultipartFile("file", "empty.csv", "text/csv", "".getBytes());

		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
				() -> Utils.parseCsvToMatrix(file));
		assertEquals("Matrix is empty.", exception.getMessage());
	}

	@Test
	void testInconsistentColumnCountThrowsException() {
		String content = "1,2,3\n4,5\n6,7,8";
		MockMultipartFile file = new MockMultipartFile("file", "invalid.csv", "text/csv", content.getBytes());

		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
				() -> Utils.parseCsvToMatrix(file));
		assertEquals("Matrix is in invalid format in csv.", exception.getMessage());
	}

	@Test
	void testNonNumericValueThrowsException() {
		String content = "1,2,a\n4,5,6";
		MockMultipartFile file = new MockMultipartFile("file", "bad.csv", "text/csv", content.getBytes());

		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
				() -> Utils.parseCsvToMatrix(file));
		assertTrue(exception.getMessage().contains("Invalid number format"));
	}

	@Test
	void testWhitespaceHandledProperly() throws Exception {
		String content = " 1 , 2 , 3 \n 4 , 5 , 6 ";
		MockMultipartFile file = new MockMultipartFile("file", "spaced.csv", "text/csv", content.getBytes());

		List<List<Integer>> matrix = Utils.parseCsvToMatrix(file);
		assertEquals(List.of(1, 2, 3), matrix.get(0));
		assertEquals(List.of(4, 5, 6), matrix.get(1));
	}
}
