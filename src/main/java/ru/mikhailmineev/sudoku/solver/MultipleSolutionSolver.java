package ru.mikhailmineev.sudoku.solver;

import java.util.List;

import ru.mikhailmineev.sudoku.Point;
import ru.mikhailmineev.sudoku.Sudoku;
import ru.mikhailmineev.sudoku.exception.SudokuUnsolvableException;

public class MultipleSolutionSolver implements MultipleSolver {

    private Solver solver;
    private int unsolvable = 0;
    private int nodes = 0;

    public MultipleSolutionSolver(Solver solver) {
	this.solver = solver;
    }

    @Override
    public int getUnsolvable() {
	return unsolvable;
    }

    @Override
    public int getNodes() {
	return nodes;
    }

    @Override
    public Solutions apply(Sudoku sudoku) {
	unsolvable = 0;
	nodes = 0;
	return applyImpl(sudoku);
    }

    private Solutions applyImpl(Sudoku sudoku) {
	Solutions allSolved = new Solutions();
	try {
	    allSolved.add(solver.apply(sudoku));
	    nodes += solver.getDeep();
	} catch (SudokuUnsolvableException e) {
	    unsolvable++;
	    nodes += solver.getDeep();
	    Sudoku mostSolved = e.getSudoku();
	    Point point = mostSolved.getFreePoints().get(0);
	    List<Byte> possibles = mostSolved.possibleValues(point);
	    // DEBUG System.out.println(String.format("Unsolvable situation. Trying %s with values %s", point, possibles));
	    for (byte value : possibles) {
		allSolved.merge(applyImpl(mostSolved.offer(point, value)));
	    }
	}
	return allSolved;
    }
}
