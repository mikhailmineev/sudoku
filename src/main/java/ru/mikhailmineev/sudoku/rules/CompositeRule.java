package ru.mikhailmineev.sudoku.rules;

import java.util.Arrays;
import java.util.List;

public class CompositeRule implements Rule {
    private final List<Rule> rules;

    public CompositeRule(Rule... rules) {
	this.rules = Arrays.asList(rules);
    }

    @Override
    public boolean validate(byte[][] matrix, int size, int row, int column, byte value) {
	for (Rule rule : rules) {
	    if (!rule.validate(matrix, size, row, column, value)) {
		return false;
	    }
	}
	return true;
    }
}
