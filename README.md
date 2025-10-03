# Análises de complexidade de Hierholzer

Complexidade de Tempo: O(V+E)
A complexidade é linear relativamente ao número de vértices (V) e arestas (E). Todas as etapas executadas — verificação 
de graus, verificação de conectividade (baseada em DFS/BFS) e a própria execução de Hierholzer — têm custo linear.

Complexidade de Espaço: O(V+E)
O espaço utilizado também é linear. É dominado pelo armazenamento do grafo em listas de adjacência e pelas estruturas 
de dados auxiliares (pilhas e a cópia do grafo para o algoritmo), cujo tamanho é proporcional a V e E.