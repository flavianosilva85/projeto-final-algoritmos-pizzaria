# Unyleya - Análise e Desenvolvimento de Sistemas
## Disciplina: Algoritmos e Estrutura de Dados (2º Período)
### Atividade Final - Projeto de Gestão de Pizzaria

---

## 👨‍💻 Identificação do Aluno
- **Aluno:** Flaviano Silva
- **Curso:** Análise e Desenvolvimento de Sistemas
- **Disciplina:** Algoritmos e Estrutura de Dados
- **Atividade:** Projeto Final Integrador (Pizzaria)
- **Repositório Base do Professor:** [ITalents/BE-Algoritmos-e-Estrutura-de-Dados](https://github.com/ITalents/BE-Algoritmos-e-Estrutura-de-Dados/tree/main/Unidade%2004%2FProjeto)
- **Linguagem:** Java (JDK 8 ou superior)

---

## 🎥 Vídeo de Apresentação

[![Assistir à apresentação no YouTube](https://img.youtube.com/vi/vFVAeolQM4E/hqdefault.jpg)](https://youtu.be/vFVAeolQM4E)

**Link:** https://youtu.be/vFVAeolQM4E

O vídeo demonstra os três requisitos rodando no terminal e explica o código de cada um.

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

## 🚀 Como Executar o Projeto

### Opção 1: Execução Automática (Windows)
Basta dar um duplo clique no script:
```text
Executar_Pizzaria.bat
```
O script compila os arquivos do pacote `Projeto` e inicializa a aplicação no terminal automaticamente.

### Opção 2: Compilação Manual via Terminal (PowerShell / CMD)
Dentro da pasta raiz do projeto:

```bash
# Compilar todos os arquivos do pacote Projeto:
javac Projeto/*.java

# Executar a aplicação principal:
java Projeto.Pizzaria
```

### Opção 3: Visualizador Web (Suporte Visual)
Para abrir o painel complementar no navegador com o grafo de sabores desenhado:
```text
Abrir_No_Navegador.bat
```
Ou abra diretamente o arquivo `visualizador_web.html` em qualquer navegador.

### ⚠️ Observações de Uso
- **Números decimais:** o `Scanner` usa o idioma do sistema. Com o Windows em português, digite a distância com vírgula (ex.: `3,5`) ou um número inteiro (ex.: `5`).
- **Menus:** digite apenas os números das opções exibidas; letras ou opções fora da lista encerram a aplicação.
- **Dados de teste:** ao iniciar, o sistema carrega 3 clientes e 3 pedidos (incluindo pizzas meio a meio) para que o relatório e o grafo já tenham conteúdo.
- **Preço por tamanho:** o preço da pizza é a média dos sabores escolhidos; o tamanho (BROTO, GRANDE, GIGA) não altera o valor, como no código-base da disciplina.

---

## 📦 Estrutura de Arquivos

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
├── Executar_Pizzaria.bat
├── Abrir_No_Navegador.bat
├── visualizador_web.html
└── README.md
```

> O arquivo `AtividadeFinal_Pizzaria_Java.zip` contém o projeto completo (pasta `Projeto/`, scripts `.bat`, visualizador web e README), pronto para extrair e executar.
