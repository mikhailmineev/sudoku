package ru.mikhailmineev.sudoku;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import com.fasterxml.jackson.databind.ObjectMapper;

import ru.mikhailmineev.sudoku.experiments.Statistics;
import ru.mikhailmineev.sudoku.random.AlwaysZero;
import ru.mikhailmineev.sudoku.random.Exponential;
import ru.mikhailmineev.sudoku.random.Normal;
import ru.mikhailmineev.sudoku.random.Random;
import ru.mikhailmineev.sudoku.random.Uniform;
import ru.mikhailmineev.sudoku.rules.CompositeRule;
import ru.mikhailmineev.sudoku.rules.CrossUniqueRule;
import ru.mikhailmineev.sudoku.rules.HorizontalUniqueRule;
import ru.mikhailmineev.sudoku.rules.Rule;
import ru.mikhailmineev.sudoku.rules.VertivalUniqueRule;
import ru.mikhailmineev.sudoku.solver.Solutions;
import ru.mikhailmineev.sudoku.solver.MultipleSolver;
import ru.mikhailmineev.sudoku.solver.OnlyValueInCellSolver;
import ru.mikhailmineev.sudoku.solver.RandomSolutionSolver;
import ru.mikhailmineev.sudoku.solver.Solver;

public class SudokuCmd {
    private static final Rule rule = new CompositeRule(new VertivalUniqueRule(), new HorizontalUniqueRule(),
	    new CrossUniqueRule());
    private static final Solver solver = new OnlyValueInCellSolver();
    private static final ObjectMapper jsonMapper = new ObjectMapper();

    public static void main(String... args) {
	if (args.length != 1) {
	    return;
	}
	Sudoku sudoku;
	try {
	    String json = args[0];
	    byte[][] matrix = jsonMapper.readValue(json, byte[][].class);
	    sudoku = Sudoku.of(matrix, rule);
	    System.out.println(String.format("Trying to solve sudoku:\n%s", sudoku));
	} catch (Exception e) {
	    e.printStackTrace();
	    return;
	}

	List<Function<Long, Random>> randoms = new ArrayList<>();
	randoms.add(s -> new AlwaysZero());
	randoms.add(s -> new Uniform(s, 0, 1));
	randoms.add(s -> new Normal(s, 0, 1));
	randoms.add(s -> new Exponential(s, 1));

	long[] seeds = { 123L, 423423424234L, 432234L, 454353L };

	for (Function<Long, Random> randomProvider : randoms) {
	    Statistics statistics = new Statistics(randomProvider.apply(0L).getClass().getSimpleName());
	    for (long seed : seeds) {
		Random random = randomProvider.apply(seed);
		MultipleSolver multipleSolver = new RandomSolutionSolver(solver, random);

		long start = System.currentTimeMillis();
		Solutions solved = multipleSolver.apply(sudoku);
		long duration = System.currentTimeMillis() - start;

		long unsolvables = multipleSolver.getUnsolvable();
		long nodes = multipleSolver.getNodes();
		long leaves = solved.all().size();
		statistics.add(duration, unsolvables, nodes, leaves);
	    }
	    statistics.print();
	}
    }
}
