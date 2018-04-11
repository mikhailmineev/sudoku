package ru.mikhailmineev.sudoku.random;

public abstract class AbstractRandom implements Random {

    private RandomProvider provider;

    public AbstractRandom(long seed) {
	provider = new RandomProvider(seed);
    }

    @Override
    public double next() {
	return provider.nextDouble();
    }
}
