package com.league.matrix_app.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.league.matrix_app.utils.Utils;

@RestController
@RequestMapping("v1/matrix-app")
public class MatrixController {

    @PostMapping("/echo")
    public ResponseEntity<String> echo(@RequestParam("file") MultipartFile file) {
        try {
            List<List<Integer>> matrix = Utils.parseCsvToMatrix(file);
            String response = matrixToString(matrix);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException | IOException e) {
            return ResponseEntity.badRequest().body("Failed to process file: " + e.getMessage());
        }
    }

    @PostMapping("/invert")
    public ResponseEntity<String> invert(@RequestParam("file") MultipartFile file) {
        try {
            List<List<Integer>> matrix = Utils.parseCsvToMatrix(file);
            List<List<Integer>> transposed = transposeMatrix(matrix);
            String response = matrixToString(transposed);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException | IOException e) {
            return ResponseEntity.badRequest().body("Failed to process file: " + e.getMessage());
        }
    }

    @PostMapping("/flatten")
    public ResponseEntity<String> flatten(@RequestParam("file") MultipartFile file) {
        try {
            List<List<Integer>> matrix = Utils.parseCsvToMatrix(file);
            List<String> flatList = new ArrayList<>();
            for (List<Integer> row : matrix) {
                for (Integer value : row) {
                    flatList.add(String.valueOf(value));
                }
            }
            return ResponseEntity.ok(String.join(",", flatList));
        } catch (IllegalArgumentException | IOException e) {
            return ResponseEntity.badRequest().body("Failed to process file: " + e.getMessage());
        }
    }

    @PostMapping("/sum")
    public ResponseEntity<String> sum(@RequestParam("file") MultipartFile file) {
        try {
            List<List<Integer>> matrix = Utils.parseCsvToMatrix(file);
            int sum = matrix.stream().flatMap(List::stream).mapToInt(Integer::intValue).sum();
            return ResponseEntity.ok(String.valueOf(sum));
        } catch (IllegalArgumentException | IOException e) {
            return ResponseEntity.badRequest().body("Failed to process file: " + e.getMessage());
        }
    }

    @PostMapping("/multiply")
    public ResponseEntity<String> multiply(@RequestParam("file") MultipartFile file) {
        try {
            List<List<Integer>> matrix = Utils.parseCsvToMatrix(file);
            long product = 1;
            for (List<Integer> row : matrix) {
                for (Integer value : row) {
                    product *= value;
                }
            }
            return ResponseEntity.ok(String.valueOf(product));
        } catch (IllegalArgumentException | IOException e) {
            return ResponseEntity.badRequest().body("Failed to process file: " + e.getMessage());
        }
    }


    private List<List<Integer>> transposeMatrix(List<List<Integer>> matrix) {
        int rows = matrix.size();
        int cols = matrix.get(0).size();
        List<List<Integer>> transposed = new ArrayList<>();

        for (int i = 0; i < cols; i++) {
            List<Integer> newRow = new ArrayList<>();
            for (List<Integer> row : matrix) {
                newRow.add(row.get(i));
            }
            transposed.add(newRow);
        }
        return transposed;
    }

    private String matrixToString(List<List<Integer>> matrix) {
        return matrix.stream()
                .map(row -> row.stream().map(String::valueOf).collect(Collectors.joining(",")))
                .collect(Collectors.joining("\n"));
    }
}
