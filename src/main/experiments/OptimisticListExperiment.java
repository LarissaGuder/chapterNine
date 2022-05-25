package main.experiments;

import main.experiments.util.Random;
import main.lists.OptimisticList;

public class OptimisticListExperiment {
    static boolean running = true;
    static boolean count = false;

    static volatile int countAdd = 0, countContains = 0, countRemove = 0;

    static OptimisticList<Integer> optimisticList = new OptimisticList<>();

    static Thread thread(int n, int probabilidadeAdd,
            int probabilidadeContains, int valorMinimo, int valorMaximo) {

        Thread t = new Thread() {
            @Override
            public void run() {
                while (running) {
                    if (Random.getRandomOperation(probabilidadeAdd, probabilidadeContains) == 3) {
                        optimisticList.add(Random.getRandom(valorMinimo, valorMaximo));
                        if (count) {
                            countAdd++;
                        }

                    } else if (Random.getRandomOperation(probabilidadeAdd, probabilidadeContains) == 2) {
                        optimisticList.contains(Random.getRandom(valorMinimo, valorMaximo));
                        if (count)
                            countContains++;
                    } else {
                        optimisticList.remove(Random.getRandom(valorMinimo, valorMaximo));
                        if (count)
                            countRemove++;
                    }
                }
            }

        };
        t.start();
        return t;
    }

    // Consolidado
    static void testThreads(int TH, int tempoExecucao, int probabilidadeAdd,
            int probabilidadeContains, int valorMinimo, int valorMaximo) {
        Thread[] threads = new Thread[TH];
        Thread t;
        for (int i = 0; i < TH; i++) {
            t = thread(i, probabilidadeAdd,
                    probabilidadeContains, valorMinimo, valorMaximo);
            threads[i] = t;
        }

        try {
            // WarmUp
            Thread.sleep(10000);
            count = true;

            Thread.sleep(tempoExecucao * 1000);
            running = false;
            for (int i = 0; i < TH; i++) {
                threads[i].join();
            }
        } catch (InterruptedException e) {
            System.err.println(e.toString());

        } finally {
        }
    }

    public static void runExperiment(int numeroThreads, int tempoExecucao, int probabilidadeAdd,
            int probabilidadeContains, int valorMinimo, int valorMaximo, int tamanhoPopulacaoInicial,
            int warmup) {

        for (int i = 2; i <= numeroThreads; i = i + 2) {
            optimisticList = new OptimisticList<>();
            countRemove = 0;
            countContains = 0;
            countAdd = 0;

            for (int k = 0; k < tamanhoPopulacaoInicial; k++) {
                optimisticList.add(k);
            }

            running = true;
            count = false;

            testThreads(i, tempoExecucao, probabilidadeAdd, probabilidadeContains, valorMinimo, valorMaximo);
            System.out.print(i + ",");
            System.out.print(countAdd / tempoExecucao + ",");
            System.out.print(countContains / tempoExecucao + ",");
            System.out.print(countRemove / tempoExecucao + ",");
            System.out.println(optimisticList.count());
        }

    }
}
