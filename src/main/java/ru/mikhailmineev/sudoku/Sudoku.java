package ru.mikhailmineev.sudoku;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ru.mikhailmineev.sudoku.exception.SudokuValidationException;
import ru.mikhailmineev.sudoku.rules.Rule;

public class Sudoku {
    private final int size;
    private final byte[][] matrix;
    private final Rule rule;

    public static Sudoku of(byte[][] matrix, Rule rule) {
	byte[][] cloned = deepCopy(matrix);

	int size = matrix.length;
	for (byte[] row : matrix) {
	    if (row == null || row.length != size) {
		throw new IllegalArgumentException("Sudoku can be only square, aboting");
	    }
	}

	Sudoku result = new Sudoku(cloned, size, rule);
	for (int row = 0; row < result.getSize(); row++) {
	    for (int column = 0; column < result.getSize(); column++) {
		if (!result.validate(row, column, cloned[row][column])) {
		    throw new IllegalArgumentException("sudoku with errors");
		}
	    }
	}
	return result;
    }

    private Sudoku(byte[][] matrix, int size, Rule rule) {
	this.matrix = matrix;
	this.size = size;
	this.rule = rule;
    }

    private Sudoku(Sudoku sudokuMatrix) {
	this(sudokuMatrix.matrix, sudokuMatrix.size, sudokuMatrix.rule);
    }

    public Sudoku offer(Point point, byte value) {
	return offer(point.getRow(), point.getColumn(), value);
    }

    public Sudoku offer(int row, int column, byte value) {
	if (row < 0 || row >= size) {
	    throw new IllegalArgumentException("row can be from 0 to " + (size - 1));
	}
	if (column < 0 || column >= size) {
	    throw new IllegalArgumentException("column can be from 0 to " + (size - 1));
	}
	if (value < 1 || value > size) {
	    throw new IllegalArgumentException(String.format("value can be from 1 to %d, actual is %d", size, value));
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
	return new Sudoku(newMatrix, size, rule);
    }

    public List<Point> getFreePoints() {
	List<Point> result = new ArrayList<>();
	for (int row = 0; row < size; row++) {
	    for (int column = 0; column < size; column++) {
		if (matrix[row][column] == 0) {
		    result.add(new Point(row, column));
		}
	    }
	}
	return result;
    }

    public int getSize() {
	return size;
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
	Sudoku other = (Sudoku) obj;
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
	return rule.validate(matrix, size, row, column, value);
    }

    private static byte[][] deepCopy(byte[][] array) {
	int rowCount = array.length;
	byte[][] result = new byte[rowCount][];
	for (int row = 0; row < rowCount; row++) {
	    int colCount = array[row].length;
	    result[row] = new byte[colCount];
	    for (int column = 0; column < colCount; column++) {
		result[row][column] = array[row][column];
	    }
	}
	return result;
    }
}
