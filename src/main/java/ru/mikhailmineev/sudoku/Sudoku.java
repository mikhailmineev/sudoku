package ru.mikhailmineev.sudoku;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.UnaryOperator;

import ru.mikhailmineev.sudoku.exception.SudokuUnsolvableException;
import ru.mikhailmineev.sudoku.exception.SudokuValidationException;
import ru.mikhailmineev.sudoku.rules.CompositeRule;
import ru.mikhailmineev.sudoku.rules.HorizontalUniqueRule;
import ru.mikhailmineev.sudoku.rules.Rule;
import ru.mikhailmineev.sudoku.rules.VertivalUniqueRule;
import ru.mikhailmineev.sudoku.solver.OnlyValueInCellSolver;

public class Sudoku {
    private static final Rule rule = new CompositeRule(new VertivalUniqueRule(), new HorizontalUniqueRule());
    private static final UnaryOperator<SudokuMatrix> solver = new OnlyValueInCellSolver();

    public static void main(String... args) {
	byte[][] matrix = new byte[5][5];
	matrix[0] = new byte[] { 1, 2, 3, 4, 0 };
	matrix[1] = new byte[] { 2, 3, 4, 0, 0 };
	matrix[2] = new byte[] { 3, 4, 0, 0, 0 };
	matrix[3] = new byte[] { 0, 0, 0, 0, 0 };
	matrix[4] = new byte[] { 0, 0, 0, 0, 0 };
	SudokuMatrix sudoku = SudokuMatrix.of(matrix, rule);
	try {
	    SudokuMatrix solved = solver.apply(sudoku);
	    System.out.println("Solved:");
	    solved.print();
	} catch (SudokuUnsolvableException e) {
	    Set<SudokuMatrix> solved = offerValuesAndSolve(e.getSudoku());
	    System.out.println(String.format("Found %d solutions", solved.size()));
	    solved.stream().forEach(System.out::println);
	}
    }

    public static Set<SudokuMatrix> offerValuesAndSolve(SudokuMatrix sudoku) {
	Set<SudokuMatrix> allSolved = new HashSet<>();
	List<Point> points = sudoku.getFreePoints();
	if (points.isEmpty()) {
	    allSolved.add(sudoku);
	    return allSolved;
	}
	Point point = points.get(0);
	for (byte value = 1; value <= sudoku.getSize(); value++) {
	    try {
		SudokuMatrix solved = solver.apply(sudoku.offer(point, value));
		allSolved.add(solved);
	    } catch (SudokuUnsolvableException e) {
		Set<SudokuMatrix> solved = offerValuesAndSolve(sudoku.offer(point, value));
		allSolved.addAll(solved);
	    } catch (SudokuValidationException e) {
		continue;
	    }
	}
	return allSolved;
    }
}
