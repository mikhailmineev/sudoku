package ru.mikhailmineev.sudoku.random;

public class Uniform extends AbstractRandom {

    private double a;

    private double b;

    public Uniform(long seed, long a, long b) {
	super(seed);
	this.a = a;
	this.b = b;
    }

    @Override
    public double next() {
	return a + super.next() * (b - a);
    }

    @Override
    public double next(double parameter1) {
	a = parameter1;
	return next();
    }

    @Override
    public double next(double parameter1, double parameter2) {
	a = parameter1;
	b = parameter2;
	return next();
    }

    @Override
    public double limits(long a, long b) {
	return next(a, b);
    }
}
