package ru.mikhailmineev.sudoku.experiments;

import java.util.LongSummaryStatistics;

public class Statistics {
    private LongSummaryStatistics executionTime = new LongSummaryStatistics();
    private LongSummaryStatistics unsolvables = new LongSummaryStatistics();
    private LongSummaryStatistics nodes = new LongSummaryStatistics();
    private LongSummaryStatistics leaves = new LongSummaryStatistics();

    public void add(long executionTime, long unsolvables, long nodes, long leaves) {
	this.executionTime.accept(executionTime);
	this.unsolvables.accept(unsolvables);
	this.nodes.accept(nodes);
	this.leaves.accept(leaves);
    }

    public void print() {
	log("Statistics:");
	log("Execution time: %fms", executionTime.getAverage());
	log("Unsolvable situations (branches with > 1 child): %f", unsolvables.getAverage());
	log("Nodes: %f", nodes.getAverage());
	log("Leaves: %f", leaves.getAverage());
    }

    private static void log(String format, Object... args) {
	System.out.println(String.format(format, args));
    }
}
