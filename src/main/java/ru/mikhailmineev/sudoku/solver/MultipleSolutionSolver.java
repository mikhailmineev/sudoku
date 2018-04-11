package ru.mikhailmineev.sudoku.solver;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;
import java.util.function.UnaryOperator;

import ru.mikhailmineev.sudoku.Point;
import ru.mikhailmineev.sudoku.Sudoku;
import ru.mikhailmineev.sudoku.exception.SudokuUnsolvableException;
import ru.mikhailmineev.sudoku.exception.SudokuValidationException;

public class MultipleSolutionSolver implements Function<Sudoku, Set<Sudoku>> {

    private UnaryOperator<Sudoku> solver;
    private int unsolvable = 0;

    public MultipleSolutionSolver(UnaryOperator<Sudoku> solver) {
	this.solver = solver;
    }
    
    public int getUnsolvable() {
        return unsolvable;
    }

    @Override
    public Set<Sudoku> apply(Sudoku sudoku) {
	Set<Sudoku> allSolved = new HashSet<>();
	try {
	    allSolved.add(solver.apply(sudoku));
	} catch (SudokuUnsolvableException e) {
	    unsolvable ++;
	    for (byte value = 1; value <= sudoku.getSize(); value++) {
		try {
		    Point point = e.getSudoku().getFreePoints().get(0);
		    allSolved.addAll(apply(e.getSudoku().offer(point, value)));
		} catch (SudokuValidationException e1) {
		    continue;
		}
	    }
	}
	return allSolved;
    }
}
