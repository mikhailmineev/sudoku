package ru.mikhailmineev.sudoku.random;

public class Exponential extends AbstractRandom {

    private long mean;

    public Exponential(long seed, long mean) {
	super(seed);
	this.mean = mean;
    }

    @Override
    public double next() {
	return -Math.log(super.next()) * mean;
    }
}