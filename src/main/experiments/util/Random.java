package main.experiments.util;

import java.util.concurrent.ThreadLocalRandom;

public class Random {
    public Random() {

    }

    public static int getRandom(int valorMinimo, int valorMaximo) {
        return ThreadLocalRandom.current().nextInt(valorMinimo, valorMaximo + 1);
    }

    public static int getRandomOperation(int probabilidadeAdd, int probabilidadeContains) {
        final int ran = ThreadLocalRandom.current().nextInt(100);
        if (ran <= probabilidadeAdd) {
            return 3;
        } else if (ran <= probabilidadeContains + probabilidadeAdd) {
            return 2;
        } else {
            return 1;
        }
    }
}
