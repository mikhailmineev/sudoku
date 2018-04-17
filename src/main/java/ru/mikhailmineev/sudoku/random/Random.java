package ru.mikhailmineev.sudoku.random;

public interface Random {
    
    double next();
    
    default double next(double parameter1) {
	throw new UnsupportedOperationException();
    }
    
    default double next(double parameter1, double parameter2) {
	throw new UnsupportedOperationException();
    }
    
    double limits(long a, long b);
}
