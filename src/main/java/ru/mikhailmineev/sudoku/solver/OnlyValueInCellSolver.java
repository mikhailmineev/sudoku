package ru.mikhailmineev.sudoku.solver;

import java.util.ArrayList;
import java.util.List;
import java.util.function.UnaryOperator;

import ru.mikhailmineev.sudoku.Point;
import ru.mikhailmineev.sudoku.Sudoku;
import ru.mikhailmineev.sudoku.exception.SudokuUnsolvableException;
import ru.mikhailmineev.sudoku.exception.SudokuValidationException;

public class OnlyValueInCellSolver implements UnaryOperator<Sudoku> {

    @Override
    public Sudoku apply(Sudoku sudoku) {
	List<Point> points = sudoku.getFreePoints();
	if (points.isEmpty()) {
	    return sudoku;
	}
	for (Point point : points) {
	    List<Sudoku> possibles = new ArrayList<>();
	    for (byte value = 1; value <= sudoku.getSize(); value++) {
		try {
		    Sudoku possible = sudoku.offer(point, value);
		    possibles.add(possible);
		} catch (SudokuValidationException e) {
		    continue;
		}
	    }
	    if (possibles.size() == 1) {
		return apply(possibles.get(0));
	    }
	}
	throw new SudokuUnsolvableException(sudoku);
    }

}
