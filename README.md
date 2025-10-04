# Análises de complexidade de Hierholzer

Complexidade de Tempo: O(V+E)
A complexidade é linear relativamente ao número de vértices (V) e arestas (E). Todas as etapas executadas — verificação 
de graus, verificação de conectividade (baseada em DFS/BFS) e a própria execução de Hierholzer — têm custo linear.

Complexidade de Espaço: O(V+E)
O espaço utilizado também é linear. É dominado pelo armazenamento do grafo em listas de adjacência e pelas estruturas 
de dados auxiliares (pilhas e a cópia do grafo para o algoritmo), cujo tamanho é proporcional a V e E.

# Análises de complexidade de Fleury

Complexidade de Tempo: O(E²)
Como o algoritmo envolve a remoção de arestas, significa que todas as arestas são percorridas ao menos uma vez, resultando
em uma complexidade O(E). Além disso, o algoritmo utiliza DFS para detecção de pontes, em O(V+E), resultando em uma
complexidade de O(E²).

Complexidade de espaço: O(V+E)
Como o grafo é armazenado em uma lista de adjacências (hashmap de vértices com seus vizinhos), 
a complexidade de espaço será de O(V+E) para a estrutura do grafo, 
além do espaço necessário para a lista do caminho euleriano, resultando em um espaço linear.
