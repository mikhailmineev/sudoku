package ru.mikhailmineev.sudoku.random;

public class Normal extends AbstractRandom {

    private long mean;

    private long variance;

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
	    return second;
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
	    second = r * variance + mean;
	    ready = true;
	    return r * y * variance + mean;
	}
    }
}