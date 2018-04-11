package ru.mikhailmineev.sudoku;

public class Point {
    private int row;
    private int column;

    public Point(int row, int column) {
	this.row = row;
	this.column = column;
    }

    public int getRow() {
	return row;
    }

    public int getColumn() {
	return column;
    }

    @Override
    public String toString() {
	return "[row=" + row + ", column=" + column + "]";
    }
}
