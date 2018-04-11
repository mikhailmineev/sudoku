package ru.mikhailmineev.sudoku.random;

public class Uniform extends AbstractRandom {

    private long a;
    
    private long b;

    public Uniform(long seed, long a, long b) {
	super(seed);
	this.a = a;
	this.b = b;
    }

    @Override
    public double next() {
	return a + super.next() * (b - a);
    }
}
