package ru.mikhailmineev.sudoku.solver;

import java.util.function.UnaryOperator;

import ru.mikhailmineev.sudoku.Sudoku;

public interface Solver extends UnaryOperator<Sudoku> {
    int getDeep();
}