package ru.mikhailmineev.sudoku.exception;

import ru.mikhailmineev.sudoku.SudokuMatrix;

public class SudokuValidationException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    private SudokuMatrix sudoku;

    public SudokuValidationException(SudokuMatrix sudoku, int row, int column, int value) {
	super(String.format("validation failed for matrix \n%sat %d %d, value %d", sudoku.toString(), row, column,
		value));
	this.sudoku = sudoku;
    }

    public SudokuMatrix getSudoku() {
	return sudoku;
    }

}
