package ru.mikhailmineev.sudoku.exception;

import ru.mikhailmineev.sudoku.Sudoku;

public class SudokuUnsolvableException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    private Sudoku sudoku;

    public SudokuUnsolvableException(Sudoku sudoku) {
	super(String.format("sudoku is unsolvable \n%s", sudoku.toString()));
	this.sudoku = sudoku;
    }

    public Sudoku getSudoku() {
	return sudoku;
    }

}
