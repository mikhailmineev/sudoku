package ru.mikhailmineev.sudoku;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Sudoku {
    private static int size = 5;
    private static int[][] matrix = new int[size][size];
    private static Set<SudokuMatrix> solved = new HashSet<>();

    public static void main(String... args) {
	matrix[0] = new int[] { 1, 2, 3, 4, 5 };
	matrix[1] = new int[] { 2, 3, 4, 5, 1 };
	matrix[2] = new int[] { 3, 4, 5, 0, 0 };
	matrix[3] = new int[] { 4, 5, 0, 0, 0 };
	matrix[4] = new int[] { 5, 1, 0, 0, 0 };
	SudokuMatrix sudoku = SudokuMatrix.of(matrix);
	offerValues(sudoku, 0);
	System.out.println(solved.size());
    }

    public static void offerValues(SudokuMatrix sudoku, int deep) {
	List<Point> points = sudoku.getFreePoints();
	if (points.isEmpty()) {
	    System.out.println("Solved:");
	    sudoku.print();
	    solved.add(sudoku);
	    return;
	}
	for (Point point : points) {
	    for (int value = 1; value <= size; value++) {
		try {
		    offerValues(sudoku.offer(point.getRow(), point.getColumn(), value), deep + 1);
		} catch (SudokuValidationException e) {
		    continue;
		} catch (Exception e) {
		    System.out.println(sudoku);
		    System.out.println(points);
		    throw e;
		}
	    }
	}
    }
}
