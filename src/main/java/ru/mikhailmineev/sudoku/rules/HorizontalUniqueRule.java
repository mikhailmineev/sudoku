package ru.mikhailmineev.sudoku.rules;

public class HorizontalUniqueRule implements Rule {

    @Override
    public boolean validate(byte[][] matrix, int size, int row, int column, byte value) {
	for (int i = 0; i < size; i++) {
	    if (i == column) {
		continue;
	    }
	    if (matrix[row][i] == value) {
		return false;
	    }
	}
	return true;
    }
}
