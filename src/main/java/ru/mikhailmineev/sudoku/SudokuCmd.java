package ru.mikhailmineev.sudoku;

import com.fasterxml.jackson.databind.ObjectMapper;

import ru.mikhailmineev.sudoku.rules.CompositeRule;
import ru.mikhailmineev.sudoku.rules.CrossUniqueRule;
import ru.mikhailmineev.sudoku.rules.HorizontalUniqueRule;
import ru.mikhailmineev.sudoku.rules.Rule;
import ru.mikhailmineev.sudoku.rules.VertivalUniqueRule;
import ru.mikhailmineev.sudoku.solver.MultipleSolutionSolutions;
import ru.mikhailmineev.sudoku.solver.MultipleSolutionSolver;
import ru.mikhailmineev.sudoku.solver.OnlyValueInCellSolver;
import ru.mikhailmineev.sudoku.solver.Solver;

public class SudokuCmd {
    private static final Rule rule = new CompositeRule(new VertivalUniqueRule(), new HorizontalUniqueRule(),
	    new CrossUniqueRule());
    private static final Solver solver = new OnlyValueInCellSolver();
    private static final MultipleSolutionSolver multipleSolver = new MultipleSolutionSolver(solver);
    private static final ObjectMapper jsonMapper = new ObjectMapper();

    public static void main(String... args) {
	if (args.length > 0) {
	    String json = args[0];
	    try {
		byte[][] matrix = jsonMapper.readValue(json, byte[][].class);
		Sudoku sudoku = Sudoku.of(matrix, rule);
		System.out.println(String.format("Trying to solve sudoku:\n%s", sudoku));

		long start = System.currentTimeMillis();
		MultipleSolutionSolutions solved = multipleSolver.apply(sudoku);
		long duration = System.currentTimeMillis() - start;

		log("Statistics:");
		log("Execution time: %dms", duration);
		log("Unsolvable situations (branches with > 1 child): %d", multipleSolver.getUnsolvable());
		log("Nodes: %d", multipleSolver.getNodes());
		log("Found %d solution(s):", solved.unique().size());
		solved.unique().stream().forEach(System.out::println);
	    } catch (Exception e) {
		e.printStackTrace();
		return;
	    }
	}
    }

    private static void log(String format, Object... args) {
	System.out.println(String.format(format, args));
    }
}
