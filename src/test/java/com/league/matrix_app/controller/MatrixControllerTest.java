package com.league.matrix_app.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.file.Files;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(MatrixController.class)
public class MatrixControllerTest {

	@Autowired
	private MockMvc mockMvc;

	private MockMultipartFile loadTestFile(String filename) throws Exception {
		ClassPathResource resource = new ClassPathResource("/" + filename);
		return new MockMultipartFile("file", filename, "text/csv", Files.readAllBytes(resource.getFile().toPath()));
	}

	@Test
	public void testEcho() throws Exception {
		mockMvc.perform(multipart("/v1/matrix-app/echo").file(loadTestFile("matrix.csv"))).andExpect(status().isOk())
				.andExpect(content().string("1,2,3\n4,5,6\n7,8,9"));
	}

	@Test
	public void testInvert() throws Exception {
		mockMvc.perform(multipart("/v1/matrix-app/invert").file(loadTestFile("matrix.csv"))).andExpect(status().isOk())
				.andExpect(content().string("1,4,7\n2,5,8\n3,6,9"));
	}

	@Test
	public void testFlatten() throws Exception {
		mockMvc.perform(multipart("/v1/matrix-app/flatten").file(loadTestFile("matrix.csv"))).andExpect(status().isOk())
				.andExpect(content().string("1,2,3,4,5,6,7,8,9"));
	}

	@Test
	public void testSum() throws Exception {
		mockMvc.perform(multipart("/v1/matrix-app/sum").file(loadTestFile("matrix.csv"))).andExpect(status().isOk())
				.andExpect(content().string("45"));
	}

	@Test
	public void testMultiply() throws Exception {
		mockMvc.perform(multipart("/v1/matrix-app/multiply").file(loadTestFile("matrix.csv")))
				.andExpect(status().isOk()).andExpect(content().string("362880"));
	}

	@Test
	public void testInvalidMatrix_nonInteger() throws Exception {
		mockMvc.perform(multipart("/v1/matrix-app/echo").file(loadTestFile("invalid_non_integer.csv")))
				.andExpect(status().isBadRequest())
				.andExpect(content().string(org.hamcrest.Matchers.containsString("Invalid number format")));
	}

	@Test
	public void testInvalidMatrix_nonFormat() throws Exception {
		mockMvc.perform(multipart("/v1/matrix-app/echo").file(loadTestFile("invalid_matrix.csv")))
				.andExpect(status().isBadRequest()).andExpect(
						content().string(org.hamcrest.Matchers.containsString("Matrix is in invalid format in csv")));
	}

	@Test
	public void testEmptyFile() throws Exception {
		mockMvc.perform(multipart("/v1/matrix-app/echo").file(loadTestFile("empty.csv")))
				.andExpect(status().isBadRequest())
				.andExpect(content().string(org.hamcrest.Matchers.containsString("Matrix is empty")));
	}

	@Test
	public void testInvert5X6Matrix() throws Exception {
		mockMvc.perform(multipart("/v1/matrix-app/invert").file(loadTestFile("5X6matrix.csv")))
				.andExpect(status().isOk()).andExpect(content().string("1,7,13,19,25\n" + "2,8,14,20,26\n"
						+ "3,9,15,21,27\n" + "4,10,16,22,28\n" + "5,11,17,23,29\n" + "6,12,18,24,30"));
	}

}
