package ru.mikhailmineev.sudoku;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SudokuMatrix {
    private static final int SIZE = 5;
    private int[][] matrix = new int[SIZE][SIZE];

    public static SudokuMatrix of(int[][] matrix) {
	int[][] cloned = deepCopy(matrix);
	for (int row = 0; row < SIZE; row++) {
	    for (int column = 0; column < SIZE; column++) {
		if (!validate(cloned, row, column, cloned[row][column])) {
		    throw new IllegalArgumentException("bad matrix");
		}
	    }
	}
	return new SudokuMatrix(cloned);
    }

    private SudokuMatrix(int[][] matrix) {
	this.matrix = matrix;
    }

    private SudokuMatrix(SudokuMatrix sudokuMatrix) {
	this(sudokuMatrix.matrix);
    }

    public SudokuMatrix offer(int row, int column, int value) {
	if (row < 0 || row >= SIZE) {
	    throw new IllegalArgumentException("row can be from 0 to " + (SIZE - 1));
	}
	if (column < 0 || column >= SIZE) {
	    throw new IllegalArgumentException("column can be from 0 to " + (SIZE - 1));
	}
	if (value < 1 || value > SIZE) {
	    throw new IllegalArgumentException(String.format("value can be from 1 to %d, actual is %d", SIZE, value));
	}
	if (matrix[row][column] != 0) {
	    throw new IllegalArgumentException(String.format("already filled with value \n%sat %d %d, offered %d", toString(), row, column,
			value));
	}
	if (!validate(row, column, value)) {
	    throw new SudokuValidationException(this, row, column, value);
	}

	int[][] newMatrix = deepCopy(matrix);
	newMatrix[row][column] = value;
	return new SudokuMatrix(newMatrix);
    }

    public List<Point> getFreePoints() {
	List<Point> result = new ArrayList<>();
	for (int row = 0; row < SIZE; row++) {
	    for (int column = 0; column < SIZE; column++) {
		if (matrix[row][column] == 0) {
		    result.add(new Point(row, column));
		}
	    }
	}
	return result;
    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + Arrays.deepHashCode(matrix);
	return result;
    }

    @Override
    public boolean equals(Object obj) {
	if (this == obj)
	    return true;
	if (obj == null)
	    return false;
	if (getClass() != obj.getClass())
	    return false;
	SudokuMatrix other = (SudokuMatrix) obj;
	if (!Arrays.deepEquals(matrix, other.matrix))
	    return false;
	return true;
    }

    @Override
    public String toString() {
	StringBuilder builder = new StringBuilder();
	for (int[] row : matrix) {
	    for (int col : row) {
		builder.append('|');
		builder.append(col);
	    }
	    builder.append('|');
	    builder.append('\n');
	}
	return builder.toString();
    }
    
    public void print() {
	System.out.print(toString());
    }

    private boolean validate(int row, int column, int value) {
	return validate(matrix, row, column, value);
    }
    
    private static int[][] deepCopy(int[][] array) {
	int[][] result = new int[SIZE][SIZE];
	for (int row = 0; row < SIZE; row++) {
	    for (int column = 0; column < SIZE; column++) {
		result[row][column] = array[row][column];
	    }
	}
	return result;
    }

    private static boolean validate(int[][] matrix, int row, int column, int value) {
	if (value == 0) {
	    return true;
	}
	// row check
	for (int i = 0; i < SIZE; i++) {
	    if (i == column) {
		continue;
	    }
	    if (matrix[row][i] == value) {
		return false;
	    }
	}
	// column check
	for (int i = 0; i < SIZE; i++) {
	    if (i == row) {
		continue;
	    }
	    if (matrix[i][column] == value) {
		return false;
	    }
	}
	return true;
    }
}
