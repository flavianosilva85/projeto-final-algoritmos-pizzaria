# Unyleya - Análise e Desenvolvimento de Sistemas
## Disciplina: Algoritmos e Estrutura de Dados (2º Período)
### Atividade Final - Projeto Pizzaria (Entrega Final com Vídeo)

---

## 👨‍💻 Identificação do Aluno
- **Aluno:** Flaviano Silva
- **Curso:** Análise e Desenvolvimento de Sistemas
- **Disciplina:** Algoritmos e Estrutura de Dados
- **Atividade:** Projeto Final Integrador (Pizzaria)
- **Repositório Base do Professor:** [ITalents/BE-Algoritmos-e-Estrutura-de-Dados](https://github.com/ITalents/BE-Algoritmos-e-Estrutura-de-Dados/tree/main/Unidade%2004%2FProjeto)
- **Linguagem:** Java (JDK 8 ou superior)

---

## 📋 Visão Geral do Projeto

Este projeto consiste no aprimoramento do sistema de gestão de pedidos de uma pizzaria desenvolvido originalmente em Java durante as aulas da Unidade 4. A partir da estrutura disponibilizada, foram implementados três módulos cruciais de lógica algorítmica e estruturas de dados:

1. **Alteração de Pedidos Existentes** (Localização por ID ou Nome, adição/remoção de pizzas e alteração de sabores).
2. **Relatório Gerencial com Grafo de Sabores** (Faturamento total, ranking de popularidade e Grafo de coocorrência de sabores na mesma pizza).
3. **Cálculo de Frete de Entrega** (Função matemática baseada em distância em quilômetros e peso/volume das pizzas).

---

## 🛠️ Detalhamento dos Requisitos Implementados

### 1. Método `alterarPedido` (Requisito 1)
- **Localização Flexível:** Permite ao operador buscar o pedido tanto pelo **ID numérico** do pedido quanto por **Nome do cliente** (com busca textual insensível a maiúsculas/minúsculas).
- **Adição Dinâmica de Pizzas:** Permite selecionar o tamanho (`BROTO`, `GRANDE`, `GIGA`) e montar novos sabores a partir do `Cardapio`, adicionando a pizza à coleção do pedido.
- **Remoção de Pizzas:** Exibe os itens numerados e remove a pizza selecionada com validação de segurança (não permitindo esvaziar pedidos com apenas 1 pizza).
- **Alteração de Sabores:** Permite selecionar uma pizza específica do pedido e substituir seu sabor atual por qualquer outro sabor do cardápio, recalculando instantaneamente o preço com `Cardapio.getPrecoJusto()`.
- **Recálculo em Tempo Real:** Qualquer alteração (adição, remoção ou troca de sabor) invoca automaticamente `recalcularValorTotal()`, mantendo o pedido sempre consistente.

### 2. Método `gerarRelatorio` com Grafo de Sabores (Requisito 2)
- **Faturamento Acumulado:** Itera sobre a lista de pedidos somando os valores totais para gerar o balanço financeiro global da pizzaria.
- **Ranking de Sabores Mais Pedidos:** Utiliza um `HashMap<String, Integer>` para tabular a frequência de cada sabor e ordena os resultados em ordem decrescente via `Collections.sort()` com `Comparator`.
- **Grafo de Coocorrência de Sabores:**
  - Implementado como um grafo não-direcionado ponderado utilizando Lista de Adjacências: `Map<String, Map<String, Integer>>`.
  - Para cada pizza com múltiplos sabores (ex: meia Calabresa e meia Quatro queijos), o algoritmo cria ou incrementa o peso da aresta entre os dois sabores.
  - O relatório renderiza todas as conexões mapeadas (ex: `Calabresa <---> Quatro queijos [Peso: 2 pizzas juntas]`), além da estrutura formal de lista de adjacências de cada vértice.

### 3. Método `calcularFrete` (Requisito 3)
- **Fórmula de Frete:**
  $$\text{Frete (R\$)} = \text{Taxa Base (R\$ 5,00)} + (\text{Distância em km} \times \text{R\$ 1,50}) + (\text{Qtd de Pizzas} \times \text{R\$ 1,00})$$
- **Componentes:**
  - *Distância (km):* Representa o deslocamento do entregador e consumo de combustível.
  - *Peso/Carga (Pizzas):* Representa o volume físico transportado na mochila térmica do motoboy.
  - *Taxa Base Fixa:* Custo operacional mínimo de saída.
- **Integração:** O sistema permite tanto a simulação avulsa de frete quanto a vinculação direta a pedidos cadastrados, exibindo o extrato final com subtotal e frete discriminados.

---

## 🎥 Roteiro para Gravação do Vídeo (Aprox. 5 Minutos)

> [!IMPORTANT]
> A apresentação em vídeo é obrigatória para a aprovação final. Siga o roteiro cronometrado abaixo para cobrir todos os pontos avaliados com clareza e segurança:

| Tempo | Etapa | O que falar e demonstrar na tela |
| :--- | :--- | :--- |
| **0:00 - 0:45** | **Apresentação** | *"Olá, professor e tutor! Meu nome é Flaviano Silva, sou aluno de Análise e Desenvolvimento de Sistemas da Unyleya. Neste vídeo, apresento a implementação da Atividade Final de Algoritmos e Estrutura de Dados, onde aprimorei o sistema de pedidos de pizzaria com alteração de pedidos, relatório com grafos de sabores e cálculo de frete."* |
| **0:45 - 2:00** | **Requisito 1 (Alterar Pedido)** | Mostre o terminal executando a opção **2 (Alterar Pedido)**.<br>1. Demonstre a busca por **Nome** digitando `Flaviano` ou por **ID** digitando `1`.<br>2. Demonstre a alteração do sabor de uma pizza (trocando para outro sabor do cardápio).<br>3. Demonstre a adição de uma nova pizza e veja o valor total sendo recalculado na hora. Mostre no código a classe `Pedido.java` e o método `recalcularValorTotal()`. |
| **2:00 - 3:30** | **Requisito 2 (Relatório e Grafo)** | Mostre a opção **4 (Gerar Relatório de Vendas)**.<br>1. Destaque o faturamento total acumulado e o ranking ordenado de sabores mais vendidos.<br>2. Mostre o **Grafo de Coocorrência de Sabores**: explique como o grafo foi modelado em Java (`Map<String, Map<String, Integer>>`) onde cada sabor é um vértice e pizzas meio-a-meio geram arestas com pesos proporcionais à frequência das combinações. |
| **3:30 - 4:30** | **Requisito 3 (Cálculo de Frete)** | Mostre a opção **6 (Calcular Frete de Entrega)**.<br>1. Selecione um pedido existente e informe uma distância (ex: 5 km).<br>2. Mostre a fórmula em ação: taxa base (R$ 5,00) + R$ 1,50/km + R$ 1,00 por pizza.<br>3. Explique que a quantidade de pizzas atende ao requisito de peso/volume da entrega. |
| **4:30 - 5:00** | **Conclusão** | *"Como vimos, todos os três requisitos foram plenamente atendidos integrando Programação Orientada a Objetos, Coleções Dinâmicas e Teoria dos Grafos. O código está versionado no GitHub e empacotado para avaliação. Muito obrigado!"* |

---

## 🚀 Como Compilar e Executar

No terminal (PowerShell ou CMD) dentro da pasta `Atividade Final`:

```bash
# Compilar todos os arquivos do pacote Projeto:
javac Projeto/*.java

# Executar a aplicação principal:
java Projeto.Pizzaria
```

---

## 📦 Estrutura de Arquivos da Entrega

```text
Atividade Final/
├── Projeto/
│   ├── Cardapio.java
│   ├── Cliente.java
│   ├── Pizza.java
│   ├── Pedido.java
│   └── Pizzaria.java
├── .gitignore
├── Atividade Final.docx
├── AtividadeFinal_Pizzaria_Java.zip
└── README.md
```
