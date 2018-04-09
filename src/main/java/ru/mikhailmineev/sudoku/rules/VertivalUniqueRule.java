package ru.mikhailmineev.sudoku.rules;

public class VertivalUniqueRule implements Rule {

    @Override
    public boolean validate(byte[][] matrix, int size, int row, int column, byte value) {
	for (int i = 0; i < size; i++) {
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
