package main;


public abstract class App {
    public static void main(String[] args) throws Exception {
        runExperiment(args);
    }

    public static void runExperiment(String[] args) {
        if (args.length != 3) {
            System.out.println("Usage: java <Experiment> numAddThreads numRemoveThreads numContainsThreads");
        } else {
            System.out.println("Executando experimentos");
        }
    }

   
}
