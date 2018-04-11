package ru.mikhailmineev.sudoku.rules;

public class CrossUniqueRule implements Rule {

    @Override
    public boolean validate(byte[][] matrix, int size, int row, int column, byte value) {
	if (row == column) {
	    for (int i = 0; i < size; i++) {
		if (i == column) {
		    continue;
		}
		if (matrix[i][i] == value) {
		    return false;
		}
	    }
	}
	if (row == size - 1 - column) {
	    for (int i = 0; i < size; i++) {
		if (i == row) {
		    continue;
		}
		if (matrix[i][size - 1 - i] == value) {
		    return false;
		}
	    }
	}
	return true;
    }
}
