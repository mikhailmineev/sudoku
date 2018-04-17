package ru.mikhailmineev.sudoku.solver;

import java.util.List;

import ru.mikhailmineev.sudoku.Point;
import ru.mikhailmineev.sudoku.Sudoku;
import ru.mikhailmineev.sudoku.exception.SudokuUnsolvableException;
import ru.mikhailmineev.sudoku.random.Random;

public class RandomSolutionSolver implements MultipleSolver {

    private Solver solver;
    private Random random;
    private int unsolvable = 0;
    private int nodes = 0;

    public RandomSolutionSolver(Solver solver, Random random) {
	this.solver = solver;
	this.random = random;
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
	    List<Point> freePoints = mostSolved.getFreePoints();
	    int index = getAppliable(0, freePoints.size());
	    Point point = freePoints.get(index);
	    List<Byte> possibles = mostSolved.possibleValues(point);
	    // DEBUG System.out.println(String.format("Unsolvable situation. Trying %s with
	    // values %s", point, possibles));
	    for (byte value : possibles) {
		allSolved.merge(applyImpl(mostSolved.offer(point, value)));
	    }
	}
	return allSolved;
    }

    private int getAppliable(int a, int b) {
	int result;
	do {
	    result = (int) Math.floor(random.limits(a, b));
	    // DEBUG System.out.println(String.format("Need up to %d, offering %d", b, result));
	} while ((result < a) || (result >= b));
	return result;
    }
}
