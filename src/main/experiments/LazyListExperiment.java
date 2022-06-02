package main.experiments;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import main.experiments.util.Random;
import main.lists.LazyList;

public class LazyListExperiment {
    static boolean running = true;
    static boolean count = false;

    static volatile int countAdd = 0, countContains = 0, countRemove = 0;

    static LazyList<Integer> lazyList = new LazyList<>();

    static Thread thread(int n, int probabilidadeAdd,
            int probabilidadeContains, int valorMinimo, int valorMaximo) {

        Thread t = new Thread() {
            @Override
            public void run() {
                while (running) {
                    if (Random.getRandomOperation(probabilidadeAdd, probabilidadeContains) == 3) {
                        lazyList.add(Random.getRandom(valorMinimo, valorMaximo));
                        if (count) {
                            countAdd++;
                        }

                    } else if (Random.getRandomOperation(probabilidadeAdd, probabilidadeContains) == 2) {
                        lazyList.contains(Random.getRandom(valorMinimo, valorMaximo));
                        if (count)
                            countContains++;
                    } else {
                        lazyList.remove(Random.getRandom(valorMinimo, valorMaximo));
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

        PrintWriter writer;
        try {
            writer = new PrintWriter("LazyListExperiment.csv", "UTF-8");
            writer.println("threads,add,contains,remove,population");
            for (int i = 2; i <= numeroThreads; i = i + 2) {
                lazyList = new LazyList<>();
                countRemove = 0;
                countContains = 0;
                countAdd = 0;

                for (int k = tamanhoPopulacaoInicial; k > 0; k--) {
                    lazyList.add(k);
                }

                running = true;
                count = false;

                testThreads(i, tempoExecucao, probabilidadeAdd, probabilidadeContains, valorMinimo, valorMaximo);
                writer.print(i + ",");
                writer.print(countAdd / tempoExecucao + ",");
                writer.print(countContains / tempoExecucao + ",");
                writer.print(countRemove / tempoExecucao + ",");
                writer.println(lazyList.count());
            }
            writer.close();
        } catch (FileNotFoundException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }

    }
}
