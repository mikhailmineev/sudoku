package ru.mikhailmineev.sudoku.random;

public class Exponential extends AbstractRandom {

    private double mean;

    public Exponential(long seed, long mean) {
	super(seed);
	this.mean = mean;
    }

    @Override
    public double next() {
	return -Math.log(super.next()) * mean;
    }

    @Override
    public double next(double parameter1) {
	mean = parameter1;
	return next();
    }

    @Override
    public double next(double parameter1, double parameter2) {
	return next(parameter1);
    }

    @Override
    public double limits(long a, long b) {
	double mean = (a + b) / 2.0;
	return next(mean);
    }

}