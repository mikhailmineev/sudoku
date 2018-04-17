package ru.mikhailmineev.sudoku.random;

public class Normal extends AbstractRandom {

    private double mean;

    private double variance;

    private boolean ready = false;

    private double second = 0.0;

    public Normal(long seed, long mean, long variance) {
	super(seed);
	this.mean = mean;
	this.variance = variance;
    }

    @Override
    public double next() {
	if (ready) {
	    ready = false;
	    return second * variance + mean;
	} else {
	    double x;
	    double y;
	    double s;
	    do {
		x = super.next();
		y = super.next();
		s = x * x + y * y;
	    } while (s > 1.0 || s == 0.0);

	    double r = Math.sqrt(-2.0 * Math.log(s) / s);
	    second = r * x;
	    ready = true;
	    return r * y * variance + mean;
	}
    }

    @Override
    public double next(double parameter1) {
	mean = parameter1;
	return next();
    }

    @Override
    public double next(double parameter1, double parameter2) {
	mean = parameter1;
	variance = parameter2;
	return next();
    }

    @Override
    public double limits(long a, long b) {
	double mean = (a + b) / 2.0;
	double variance = (b - a) / 6.0;
	return next(mean, variance);
    }
}