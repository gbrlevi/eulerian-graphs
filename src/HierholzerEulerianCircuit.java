import java.util.Iterator;

public class HierholzerEulerianCircuit {

    private Stack<Integer> circuit;
    private final boolean isEulerian;
    private String errorMessage;

    /**
     * @param G O grafo a ser analisado.
     */
    public HierholzerEulerianCircuit(Graph G) {
        for (int v = 0; v < G.V(); v++) {
            if (G.degree(v) % 2 != 0) {
                this.isEulerian = false;
                this.errorMessage = "Erro: O grafo contém vértices de grau ímpar.";
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
                this.errorMessage = "Erro: O grafo não é conexo (ignorando vértices isolados).";
                return;
            }
        }

        this.isEulerian = true;
        this.circuit = new Stack<Integer>();

        @SuppressWarnings("unchecked")
        Iterator<Integer>[] adj = (Iterator<Integer>[]) new Iterator[G.V()];
        for (int v = 0; v < G.V(); v++) {
            adj[v] = G.adj(v).iterator();
        }

        Stack<Integer> currentPath = new Stack<Integer>();
        currentPath.push(firstNonIsolatedVertex);

        while (!currentPath.isEmpty()) {
            int u = currentPath.peek();

            if (adj[u].hasNext()) {
                int v = adj[u].next();
                currentPath.push(v);
            } else {
                circuit.push(currentPath.pop());
            }
        }
    }

    /**
     * Retorna o circuito euleriano.
     * @return Um iterável contendo a sequência de vértices do circuito, ou null se não existir.
     */
    public Iterable<Integer> circuit() {
        return this.isEulerian ? this.circuit : null;
    }

    /**
     * Verifica se o grafo possui um circuito euleriano.
     */
    public boolean hasEulerianCircuit() {
        return this.isEulerian;
    }

    /**
     * Retorna a mensagem de erro se o grafo não for euleriano.
     */
    public String getErrorMessage() {
        return this.errorMessage;
    }
}