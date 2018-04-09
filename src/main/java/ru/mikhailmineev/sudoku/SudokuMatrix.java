package ru.mikhailmineev.sudoku;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ru.mikhailmineev.sudoku.exception.SudokuValidationException;
import ru.mikhailmineev.sudoku.rules.Rule;

public class SudokuMatrix {
    private static final int SIZE = 5;
    private byte[][] matrix = new byte[SIZE][SIZE];
    private Rule rule;

    public static SudokuMatrix of(byte[][] matrix, Rule rule) {
	byte[][] cloned = deepCopy(matrix);
	SudokuMatrix result = new SudokuMatrix(cloned, rule);
	for (int row = 0; row < SIZE; row++) {
	    for (int column = 0; column < SIZE; column++) {
		if (!result.validate(row, column, cloned[row][column])) {
		    throw new IllegalArgumentException("bad matrix");
		}
	    }
	}
	return result;
    }

    private SudokuMatrix(byte[][] matrix, Rule rule) {
	this.matrix = matrix;
	this.rule = rule;
    }

    private SudokuMatrix(SudokuMatrix sudokuMatrix) {
	this(sudokuMatrix.matrix, sudokuMatrix.rule);
    }

    public SudokuMatrix offer(Point point, byte value) {
	return offer(point.getRow(), point.getColumn(), value);
    }

    public SudokuMatrix offer(int row, int column, byte value) {
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
	    throw new IllegalArgumentException(String.format("already filled with value \n%sat %d %d, offered %d",
		    toString(), row, column, value));
	}
	if (!validate(row, column, value)) {
	    throw new SudokuValidationException(this, row, column, value);
	}

	byte[][] newMatrix = deepCopy(matrix);
	newMatrix[row][column] = value;
	return new SudokuMatrix(newMatrix, rule);
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

    public byte getSize() {
	return SIZE;
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
	for (byte[] row : matrix) {
	    for (byte col : row) {
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

    private boolean validate(int row, int column, byte value) {
	if (value == 0) {
	    return true;
	}
	return rule.validate(matrix, SIZE, row, column, value);
    }

    private static byte[][] deepCopy(byte[][] array) {
	byte[][] result = new byte[SIZE][SIZE];
	for (int row = 0; row < SIZE; row++) {
	    for (int column = 0; column < SIZE; column++) {
		result[row][column] = array[row][column];
	    }
	}
	return result;
    }
}
