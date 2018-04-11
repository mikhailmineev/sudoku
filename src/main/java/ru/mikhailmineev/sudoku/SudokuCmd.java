package ru.mikhailmineev.sudoku;

import java.util.Set;
import java.util.function.UnaryOperator;

import com.fasterxml.jackson.databind.ObjectMapper;

import ru.mikhailmineev.sudoku.rules.CompositeRule;
import ru.mikhailmineev.sudoku.rules.CrossUniqueRule;
import ru.mikhailmineev.sudoku.rules.HorizontalUniqueRule;
import ru.mikhailmineev.sudoku.rules.Rule;
import ru.mikhailmineev.sudoku.rules.VertivalUniqueRule;
import ru.mikhailmineev.sudoku.solver.MultipleSolutionSolver;
import ru.mikhailmineev.sudoku.solver.OnlyValueInCellSolver;

public class SudokuCmd {
    private static final Rule rule = new CompositeRule(new VertivalUniqueRule(), new HorizontalUniqueRule(),
	    new CrossUniqueRule());
    private static final UnaryOperator<Sudoku> solver = new OnlyValueInCellSolver();
    private static final MultipleSolutionSolver multipleSolver = new MultipleSolutionSolver(solver);
    private static final ObjectMapper jsonMapper = new ObjectMapper();

    public static void main(String... args) {
	if (args.length > 0) {
	    String json = args[0];
	    try {
		byte[][] matrix = jsonMapper.readValue(json, byte[][].class);
		Sudoku sudoku = Sudoku.of(matrix, rule);
		System.out.println(String.format("Trying to solve sudoku \n%s", sudoku));
		Set<Sudoku> solved = multipleSolver.apply(sudoku);
		System.out.println(String.format("Unsolvable situations: %d", multipleSolver.getUnsolvable()));
		System.out.println(String.format("Found %d solution(s):", solved.size()));
		solved.stream().forEach(System.out::println);
	    } catch (Exception e) {
		e.printStackTrace();
		return;
	    }
	}
    }
}
