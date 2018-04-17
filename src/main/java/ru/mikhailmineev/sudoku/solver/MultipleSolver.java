package ru.mikhailmineev.sudoku.solver;

import java.util.function.Function;

import ru.mikhailmineev.sudoku.Sudoku;

public interface MultipleSolver extends Function<Sudoku, Solutions> {

    int getUnsolvable();

    int getNodes();
}