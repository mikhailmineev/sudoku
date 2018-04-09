package ru.mikhailmineev.sudoku.rules;

@FunctionalInterface
public interface Rule {
    boolean validate(byte[][] matrix, int size, int row, int column, byte value);
}
