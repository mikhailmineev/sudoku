package ru.mikhailmineev.sudoku.random;

public interface Random {
    
    double next();
    
    default double next(long parameter1) {
	throw new UnsupportedOperationException();
    }
    
    default double next(long parameter1, long parameter2) {
	throw new UnsupportedOperationException();
    }
    
    default double next(long... parameters) {
	throw new UnsupportedOperationException();
    }
}
