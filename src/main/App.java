package main;

import java.util.ArrayList;

import main.experiments.CoarseListExperiment;
import main.experiments.FineListExperiment;
import main.lists.util.BaseMonitoredList;
import main.lists.util.IntegerListThread;
import main.lists.util.IntegerListThread.Operation;

public abstract class App<T extends BaseMonitoredList<Integer>> {
    public static void main(String[] args) throws Exception {
        FineListExperiment.main(args);
    }

    public final int MONITORING_INTERVAL = 10; // seconds
    public static final int MIN_VALUE = 0; // inclusive
    public static final int MAX_VALUE = 10000; // inclusive
    protected final int EXPERIMENT_RUNTIME = 60; // seconds

    protected ArrayList<IntegerListThread<T>> allThreads = new ArrayList<>();

    public abstract T getList();

    public void startThreads(int numThreads, Operation operation, final T list) {
        for (int i = 0; i < numThreads; i++) {
            IntegerListThread<T> t = new IntegerListThread<>(list, operation, null);
            allThreads.add(t);
            t.start();
        }
    }

    public void stopAllThreads() {
        for (IntegerListThread<T> t : allThreads) {
            t.terminate();
            try {
                t.join();
            } catch (InterruptedException ex) {
                System.err.println(ex.toString());
            }
        }
    }

    public void runExperiment(String[] args) {
        if (args.length != 3) {
            System.out.println("Usage: java <Experiment> numAddThreads numRemoveThreads numContainsThreads");
        } else {
            System.out.println("Executando experimentos");
            this.runExperiment(Integer.parseInt(args[0]), Integer.parseInt(args[1]), Integer.parseInt(args[2]));
        }
    }

    public void runExperiment(int numAddThreads, int numRemoveThreads, int numContainsThreads) {
        this.getList().getMonitor().setNumAddThreads(numAddThreads);
        this.getList().getMonitor().setNumRemoveThreads(numRemoveThreads);
        this.getList().getMonitor().setNumContainsThreads(numContainsThreads);
        System.out.println(numAddThreads);
        this.getList().startMonitor();

        startThreads(numAddThreads, Operation.Add, this.getList());
        startThreads(numRemoveThreads, Operation.Remove, this.getList());
        startThreads(numContainsThreads, Operation.Contains, this.getList());


        try {
            Thread.sleep(EXPERIMENT_RUNTIME * 1000);
        } catch (InterruptedException ex) {
            System.err.println(ex.toString());
        }

        stopAllThreads();
        this.getList().stopMonitor();

    }

}

// Setup básico.
// Lista e um conjunto de threads executoras.
// Cada thread executora fica em loop onde sorteia um tipo de operação (veja
// interface) e invoca o método correspondente.
// Parametrização das threads:
// número de threads
// número de iterações por thread
// distribuição de probabilidade para operações add, delete, contains
// o espaço de chaves para sortear operações
// Parametrização da lista (questões envolvendo a lista)
// população inicial - gerar uma lista inicialmente populada - para diminuir
// tempo de warm-up (Ex.: 100k, 200k, 300k elementos)
// limitar tamanho da lista para nao ter crescimento indefinido, ou entao
// limitar o espaço de chaves tal que
// ao inserir uma chave que já está presente nao vai aumentar a lista. O aumento
// da lista vai gerar tempos crescentes para as operacoes.
// Precisamos de uma lista de tamanho estacionário.
// Tempo de execução e warm-up. Definir um tempo inicial onde medidas não são
// tomadas pois a lista está ainda variando
// rapidamente de tamanho e assim de tempo de execucao, influenciando na média.
// Sugere-se inicialmente 10 s de warmup e 1 min de experimento. Entretanto
// estes valores devem ser investigados empiricamente.
// Ou seja, veja se os resultados são sensíveis a estes parâmetros.
// Métricas a serem observadas. As seguintes métricas são de interesse:
// a) Vazão geral do sistema em operações realizadas
// b) Vazão por tipo de operação.
// c) Tamanho médio da lista.
// Montar os testes e aplicar às várias implementações de listas, neste momento
// para coarse e fine grained.