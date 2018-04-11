package ru.mikhailmineev.sudoku.random;

public class RandomProvider {
    long seed;

    public RandomProvider(long seed) {
	this.seed = seed;
    }

    public double nextDouble() {
	seed = (seed * 69069 + 1) % 4294967296L;
	return seed / 4294967296.0;
    }
}
