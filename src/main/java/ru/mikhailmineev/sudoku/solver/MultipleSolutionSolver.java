package ru.mikhailmineev.sudoku.solver;

import java.util.List;
import java.util.function.Function;

import ru.mikhailmineev.sudoku.Point;
import ru.mikhailmineev.sudoku.Sudoku;
import ru.mikhailmineev.sudoku.exception.SudokuUnsolvableException;

public class MultipleSolutionSolver implements Function<Sudoku, MultipleSolutionSolutions> {

    private Solver solver;
    private int unsolvable = 0;
    private int nodes = 0;

    public MultipleSolutionSolver(Solver solver) {
	this.solver = solver;
    }

    public int getUnsolvable() {
	return unsolvable;
    }

    public int getNodes() {
	return nodes;
    }

    @Override
    public MultipleSolutionSolutions apply(Sudoku sudoku) {
	unsolvable = 0;
	nodes = 0;
	return applyImpl(sudoku);
    }

    private MultipleSolutionSolutions applyImpl(Sudoku sudoku) {
	MultipleSolutionSolutions allSolved = new MultipleSolutionSolutions();
	try {
	    allSolved.add(solver.apply(sudoku));
	    nodes += solver.getDeep();
	} catch (SudokuUnsolvableException e) {
	    unsolvable++;
	    nodes += solver.getDeep();
	    Sudoku mostSolved = e.getSudoku();
	    Point point = mostSolved.getFreePoints().get(0);
	    List<Byte> possibles = mostSolved.possibleValues(point);
	    System.out.println(String.format("Unsolvable situation. Trying %s with values %s", point, possibles));
	    for (byte value : possibles) {
		allSolved.merge(applyImpl(mostSolved.offer(point, value)));
	    }
	}
	return allSolved;
    }
}
