package ru.mikhailmineev.sudoku.solver;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import ru.mikhailmineev.sudoku.Sudoku;

public class Solutions {

    private List<Sudoku> list = new ArrayList<>();
    private Set<Sudoku> set = new HashSet<>();

    public Solutions() {
    }

    public void add(Sudoku sudoku) {
	list.add(sudoku);
	set.add(sudoku);
    }

    public void merge(Solutions sudokus) {
	list.addAll(sudokus.list);
	set.addAll(sudokus.set);
    }

    public Set<Sudoku> unique() {
	return set;
    }

    public List<Sudoku> all() {
	return list;
    }
}
