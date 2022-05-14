package main.experiments;
import main.App;
import main.experiments.util.Monitor;
import main.lists.FineList;

public class FineListExperiment extends App<FineList<Integer>> {

    public FineList<Integer> fineList;

    public FineListExperiment() {
        Monitor<Integer> monitor = new Monitor<>("FineList", MONITORING_INTERVAL);
        this.fineList = new FineList<>(monitor, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }

    public static void main(String[] args) {
        new FineListExperiment().runExperiment(args);
    }

    @Override
    public FineList<Integer> getList() {
        return this.fineList;
    }
}