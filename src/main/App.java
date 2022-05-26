package main;

import main.experiments.CoarseListExperiment;
import main.experiments.FineListExperiment;
import main.experiments.LazyListExperiment;
import main.experiments.LockFreeListExperiment;
import main.experiments.OptimisticListExperiment;

public class App {
        public static void main(String[] args) throws Exception {
                CoarseListExperiment.runExperiment(numeroThreads, tempoExecucao, probabilidadeAdd,
                                probabilidadeContains,
                                valorMinimo, valorMaximo, tamanhoPopulacaoInicial, warmup);
                FineListExperiment.runExperiment(numeroThreads, tempoExecucao, probabilidadeAdd, probabilidadeContains,
                                valorMinimo, valorMaximo, tamanhoPopulacaoInicial, warmup);
                LazyListExperiment.runExperiment(numeroThreads, tempoExecucao, probabilidadeAdd, probabilidadeContains,
                                valorMinimo, valorMaximo, tamanhoPopulacaoInicial, warmup);
                OptimisticListExperiment.runExperiment(numeroThreads, tempoExecucao, probabilidadeAdd,
                                probabilidadeContains,
                                valorMinimo, valorMaximo, tamanhoPopulacaoInicial, warmup);
                LockFreeListExperiment.runExperiment(numeroThreads, tempoExecucao, probabilidadeAdd,
                                probabilidadeContains,
                                valorMinimo, valorMaximo, tamanhoPopulacaoInicial, warmup);
        }

        // Setup básico. Lista e um conjunto de threads executoras. Cada thread
        // executora fica em loop
        // onde sorteia um tipo de operação (veja interface) e invoca o método
        // correspondente.

        // Parametrização das threads:
        // 1 - número de threads
        static int numeroThreads = 6;
        // 2 - acabar threads por tempo (configura) - main para de contabilizar depois
        // de um tempo e interrompe todas as threads.
        // Controle externo (main?) às threads que fazem acesso, que executa o que
        // segue:
        // - lançar threads
        // - passar tempo de aquecimento
        // - inciar contabilizacao
        // - passar tempo de experimento (medicao)
        // - parar threads
        static int tempoExecucao = 10;

        // 3 - distribuição de probabilidade para operações add, delete, contains. ->>
        // prob add > prob delete
        static int probabilidadeAdd = 40, probabilidadeDelete = 30, probabilidadeContains = 30;

        // 4 - o espaço de valores para sortear operações --> limita tamanho máximo da
        // lista (nao duplica valor dentro)
        static int valorMinimo = 1;

        static int valorMaximo = 5000;

        // 5 - medir tamanho médio da lista
        static volatile int countAdd = 0, countContains = 0, countRemove = 0;
        // Parametrização da lista (questões envolvendo a lista)

        // 1 - população inicial - gerar uma lista inicialmente populada - para diminuir
        // tempo de warm-up (Ex.: 100k, 200k, 300k elementos)
        static int tamanhoPopulacaoInicial = 5000;
        // 2 - limitar tamanho da lista para nao ter crescimento indefinido, ou entao
        // limitar o espaço de chaves tal que ao inserir uma chave que já está presente
        // nao vai aumentar a lista. O aumento da lista vai gerar tempos crescentes para
        // as operacoes. Precisamos de uma lista de tamanho estacionário.

        // 3 - Tempo de execução e warm-up. Definir um tempo inicial onde medidas não
        // são tomadas pois a lista está ainda variando
        // rapidamente de tamanho e assim de tempo de execucao, influenciando na média.
        // Sugere-se inicialmente 10 s de warmup e 1 min de experimento. Entretanto
        // estes valores devem ser investigados empiricamente.
        // Ou seja, veja se os resultados são sensíveis a estes parâmetros.
        static int warmup = 10;

}
