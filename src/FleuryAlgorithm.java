import java.util.*;

public class FleuryAlgorithm {

    private List<Integer> cycle = new ArrayList<>();
    private boolean isEulerian = false;
    private String validationMessage = "";

    public FleuryAlgorithm(Graph G) {
        int firstNonIsolatedVertex = 0;
        List<Integer> oddVertices = new ArrayList<>();
        for (int v = 0; v < G.V(); v++) {
            if (G.degree(v) % 2 != 0) {
                oddVertices.add(v);
            }
        }
        CC cc = new CC(G);
        int componentId = cc.id(firstNonIsolatedVertex);
        for (int v = 0; v < G.V(); v++) {
            if (G.degree(v) > 0 && cc.id(v) != componentId) {
                this.isEulerian = false;
                this.validationMessage = "Desconexo (dois componentes com arestas)";
                return;
            }
        }

        if (!oddVertices.isEmpty()) {
            this.validationMessage = "Não euleriano (graus ímpares).";
            return;
        }
        // --- FIM DA VALIDAÇÃO ---

        this.isEulerian = true;
        if (G.E() == 0) return;

        Map<Integer, List<Integer>> adj = createMutableCopy(G);
        int startVertex = 0;
        for (int v = 0; v < G.V(); v++) {
            if (!adj.get(v).isEmpty()) {
                startVertex = v;
                break;
            }
        }
        findCycle(startVertex, adj);
    }

    public Iterable<Integer> cycle() {
        return this.isEulerian ? this.cycle : null;
    }

    public String getValidationMessage() {
        return this.validationMessage;
    }

    private void findCycle(int u, Map<Integer, List<Integer>> adj) {
        int currentV = u;
        cycle.add(currentV);
        while (countEdges(adj) > 0) {
            int nextV = -1;
            List<Integer> neighbors = adj.get(currentV);
            if (neighbors == null || neighbors.isEmpty()) break;

            if (neighbors.size() == 1) {
                nextV = neighbors.get(0);
            } else {
                // ### A CORREÇÃO ESTÁ AQUI ###
                // Iteramos sobre uma CÓPIA da lista de vizinhos para evitar o erro.
                for (int neighbor : new ArrayList<>(neighbors)) {
                    if (!isBridge(currentV, neighbor, adj)) {
                        nextV = neighbor;
                        break;
                    }
                }
                if (nextV == -1) nextV = neighbors.get(0);
            }
            removeEdge(currentV, nextV, adj);
            currentV = nextV;
            cycle.add(currentV);
        }
    }

    // O restante da classe permanece igual...
    private Map<Integer, List<Integer>> createMutableCopy(Graph G) {
        Map<Integer, List<Integer>> adj = new HashMap<>();
        for (int v = 0; v < G.V(); v++) {
            adj.put(v, new LinkedList<>());
        }
        for (int v = 0; v < G.V(); v++) {
            for (int w : G.adj(v)) {
                if (v < w) {
                    adj.get(v).add(w);
                    adj.get(w).add(v);
                }
            }
        }
        return adj;
    }

    private boolean isBridge(int u, int v, Map<Integer, List<Integer>> adj) {
        if (adj.get(u).size() == 1) return false;
        removeEdge(u, v, adj);
        Set<Integer> visited = new HashSet<>();
        dfs(u, visited, adj);
        addEdge(u, v, adj);
        return !visited.contains(v);
    }

    private void dfs(int u, Set<Integer> visited, Map<Integer, List<Integer>> adj) {
        visited.add(u);
        if (adj.get(u) != null) {
            for (int vert : adj.get(u)) {
                if (!visited.contains(vert)) dfs(vert, visited, adj);
            }
        }
    }

    private void removeEdge(int u, int v, Map<Integer, List<Integer>> adj) {
        adj.get(u).remove(Integer.valueOf(v));
        adj.get(v).remove(Integer.valueOf(u));
    }

    private void addEdge(int u, int v, Map<Integer, List<Integer>> adj) {
        adj.get(u).add(v);
        adj.get(v).add(u);
    }

    private int countEdges(Map<Integer, List<Integer>> adj) {
        int count = 0;
        for(List<Integer> neighbors : adj.values()) {
            count += neighbors.size();
        }
        return count / 2;
    }
}