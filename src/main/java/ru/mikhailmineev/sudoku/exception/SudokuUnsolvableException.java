package ru.mikhailmineev.sudoku.exception;

import ru.mikhailmineev.sudoku.SudokuMatrix;

public class SudokuUnsolvableException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    private SudokuMatrix sudoku;

    public SudokuUnsolvableException(SudokuMatrix sudoku) {
	super(String.format("sudoku is unsolvable \n%s", sudoku.toString()));
	this.sudoku = sudoku;
    }

    public SudokuMatrix getSudoku() {
	return sudoku;
    }

}
