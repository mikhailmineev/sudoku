package ru.mikhailmineev.sudoku.experiments;

import java.util.LongSummaryStatistics;

public class Statistics {
    private LongSummaryStatistics executionTime = new LongSummaryStatistics();
    private LongSummaryStatistics unsolvables = new LongSummaryStatistics();
    private LongSummaryStatistics nodes = new LongSummaryStatistics();
    private LongSummaryStatistics leaves = new LongSummaryStatistics();
    private String name;

    public Statistics(String name) {
	this.name = name;
    }

    public void add(long executionTime, long unsolvables, long nodes, long leaves) {
	this.executionTime.accept(executionTime);
	this.unsolvables.accept(unsolvables);
	this.nodes.accept(nodes);
	this.leaves.accept(leaves);
    }

    public void print() {
	log("Statistics of %s:", name);
	log("Execution time: \t\t\t%fms", executionTime.getAverage());
	log("Unsolvables (branches with > 1 child): \t%f", unsolvables.getAverage());
	log("Nodes: \t\t\t\t\t%f", nodes.getAverage());
	log("Leaves: \t\t\t\t%f", leaves.getAverage());
    }

    private static void log(String format, Object... args) {
	System.out.println(String.format(format, args));
    }
}
