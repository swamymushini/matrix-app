package com.league.matrix_app.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public class Utils {
	public static List<List<Integer>> parseCsvToMatrix(MultipartFile file) throws IOException {
		List<List<Integer>> matrix = new ArrayList<>();
		BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()));
		int expectedColumns = -1;

		String line;
		while ((line = reader.readLine()) != null) {
			String[] parts = line.trim().split(",");
			List<Integer> row = new ArrayList<>();
			for (String part : parts) {
				try {
					row.add(Integer.parseInt(part.trim()));
				} catch (NumberFormatException e) {
					throw new IllegalArgumentException("Invalid number format: " + part);
				}
			}
			if (expectedColumns == -1)
				expectedColumns = row.size();
			else if (row.size() != expectedColumns) {
				throw new IllegalArgumentException("Matrix is in invalid format in csv.");
			}
			matrix.add(row);
		}

		if (matrix.isEmpty()) {
			throw new IllegalArgumentException("Matrix is empty.");
		}

		return matrix;
	}
}
