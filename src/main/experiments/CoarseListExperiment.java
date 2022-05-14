package main.experiments;

import main.App;
import main.experiments.util.Monitor;
import main.lists.CoarseList;

public class CoarseListExperiment extends App<CoarseList<Integer>> {

    public CoarseList<Integer> coarseList;

    public CoarseListExperiment() {
        Monitor<Integer> monitor = new Monitor<>("CoarseList", MONITORING_INTERVAL);
        this.coarseList = new CoarseList<>(monitor, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }

    public static void main(String[] args) {
        new CoarseListExperiment().runExperiment(args);
    }

    @Override
    public CoarseList<Integer> getList() {
        return this.coarseList;
    }
}