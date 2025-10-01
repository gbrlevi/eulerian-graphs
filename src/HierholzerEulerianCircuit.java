import java.util.HashSet;

public class HierholzerEulerianCircuit {

    private Stack<Integer> circuit;
    private final boolean isEulerian;
    private String errorMessage;

    public HierholzerEulerianCircuit(Graph G) {
        for (int v = 0; v < G.V(); v++) {
            if (G.degree(v) % 2 != 0) {
                this.isEulerian = false;
                this.errorMessage = "Não euleriano (graus ímpares).";
                return;
            }
        }
        int firstNonIsolatedVertex = -1;
        for (int v = 0; v < G.V(); v++) {
            if (G.degree(v) > 0) {
                firstNonIsolatedVertex = v;
                break;
            }
        }
        if (firstNonIsolatedVertex == -1) {
            this.isEulerian = true;
            this.circuit = new Stack<Integer>();
            return;
        }
        CC cc = new CC(G);
        int componentId = cc.id(firstNonIsolatedVertex);
        for (int v = 0; v < G.V(); v++) {
            if (G.degree(v) > 0 && cc.id(v) != componentId) {
                this.isEulerian = false;
                this.errorMessage = "Desconexo (dois componentes com arestas).";
                return;
            }
        }

        this.isEulerian = true;
        this.circuit = new Stack<Integer>();

        @SuppressWarnings("unchecked")
        HashSet<Integer>[] adj = (HashSet<Integer>[]) new HashSet[G.V()];
        for (int v = 0; v < G.V(); v++) {
            adj[v] = new HashSet<Integer>();
        }
        for (int v = 0; v < G.V(); v++) {
            for (int w : G.adj(v)) {
                adj[v].add(w);
            }
        }

        Stack<Integer> currentPath = new Stack<Integer>();
        currentPath.push(firstNonIsolatedVertex);

        while (!currentPath.isEmpty()) {
            int u = currentPath.peek();

            if (!adj[u].isEmpty()) {
                int v = adj[u].iterator().next();
                currentPath.push(v);

                adj[u].remove(v);
                adj[v].remove(u);
            } else {
                circuit.push(currentPath.pop());
            }
        }
    }

    public Iterable<Integer> circuit() {
        return this.isEulerian ? this.circuit : null;
    }
    public boolean hasEulerianCircuit() {
        return this.isEulerian;
    }
    public String getErrorMessage() {
        return this.errorMessage;
    }
}