package ru.mikhailmineev.sudoku.exception;

import ru.mikhailmineev.sudoku.Sudoku;

public class SudokuValidationException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    private Sudoku sudoku;

    public SudokuValidationException(Sudoku sudoku, int row, int column, int value) {
	super(String.format("validation failed for matrix \n%sat %d %d, value %d", sudoku.toString(), row, column,
		value));
	this.sudoku = sudoku;
    }

    public Sudoku getSudoku() {
	return sudoku;
    }

}
