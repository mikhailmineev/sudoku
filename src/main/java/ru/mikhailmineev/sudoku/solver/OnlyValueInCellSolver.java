package ru.mikhailmineev.sudoku.solver;

import java.util.List;
import ru.mikhailmineev.sudoku.Point;
import ru.mikhailmineev.sudoku.Sudoku;
import ru.mikhailmineev.sudoku.exception.SudokuUnsolvableException;

public class OnlyValueInCellSolver implements Solver {

    private int deep;

    @Override
    public Sudoku apply(Sudoku sudoku) {
	deep = 0;
	return applyImpl(sudoku);
    }

    @Override
    public int getDeep() {
	return deep;
    }

    private Sudoku applyImpl(Sudoku sudoku) {
	deep++;
	List<Point> points = sudoku.getFreePoints();
	if (points.isEmpty()) {
	    // DEBUG System.out.println(String.format("Solved"));
	    return sudoku;
	}
	for (Point point : points) {
	    List<Byte> possibles = sudoku.possibleValues(point);
	    if (possibles.size() == 1) {
		// DEBUG System.out.println(String.format("Found solution %d at %s", possibles.get(0), point));
		return applyImpl(sudoku.offer(point, possibles.get(0)));
	    }
	}
	throw new SudokuUnsolvableException(sudoku);
    }

}
