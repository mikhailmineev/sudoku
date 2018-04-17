package ru.mikhailmineev.sudoku.random;

public class AlwaysZero implements Random{

    @Override
    public double next() {
	return 0;
    }

    @Override
    public double limits(long a, long b) {
	return 0;
    }

}
