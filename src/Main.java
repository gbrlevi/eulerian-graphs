public class Main {
    public static void main(String[] args) {
        String[] testFiles = {
                "data/c4.txt",
                "data/c4_isolado9.txt",
                "data/caminho_triangulo.txt",
                "data/konigsberg.txt",
                "data/desconexo.txt"
        };

        StdOut.println("=================================================");
        StdOut.println("   INICIANDO TESTES DOS CIRCUITOS EULERIANOS   ");
        StdOut.println("=================================================");
        StdOut.println();

        for (String fileName : testFiles) {
            StdOut.println("--- Análise do arquivo: " + fileName + " ---");
                In in = new In(fileName);
                Graph G = new Graph(in);

                HierholzerEulerianCircuit eulerianCircuitFinder = new HierholzerEulerianCircuit(G);

                if (eulerianCircuitFinder.hasEulerianCircuit()) {
                    Iterable<Integer> circuit = eulerianCircuitFinder.circuit();
                    StringBuilder output = new StringBuilder();
                    for (int v : circuit) {
                        output.append(v).append(" ");
                    }
                    if (!output.isEmpty()) {
                        StdOut.println("Circuito Euleriano: " + output.toString().trim());
                    } else {
                        StdOut.println("Resultado: Grafo euleriano, mas sem arestas. Circuito vazio.");
                    }
                } else {
                    StdOut.println("Resultado: " + eulerianCircuitFinder.getErrorMessage());
                }

            StdOut.println();
        }
        for(String fileName : testFiles){
            System.out.println("--------------- " + fileName + " ---------------");
            In in = new In(fileName);
            Graph g = new Graph(in);
            FleuryAlgorithm fleury = new FleuryAlgorithm(g);

            if (fleury.cycle() != null) {
                System.out.println("Resultado: Ciclo Euleriano encontrado.");
                System.out.println(fleury.cycle());
            } else {
                System.out.println("Resultado: " + fleury.getValidationMessage());
            }
            System.out.println();
        }
    }
}