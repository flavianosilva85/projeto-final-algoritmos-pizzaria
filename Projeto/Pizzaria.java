package Projeto;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import Projeto.Pizza.TamanhoPizza;

/**
 * Curso: Análise e Desenvolvimento de Sistemas
 * Disciplina: Algoritmos e Estrutura de Dados
 * Unidade 4 - Atividade Final (Projeto Final: Pizzaria)
 * 
 * Implementação dos Requisitos do Projeto Final:
 * 1. Método Alterar Pedido (Busca por ID ou Nome, adição/remoção de pizzas e troca de sabor)
 * 2. Método Gerar Relatório (Faturamento, ranking de sabores e Grafo de conexões entre sabores)
 * 3. Método para Cálculo de Frete (Considera distância em km e quantidade/peso das pizzas)
 */
public class Pizzaria {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<Cliente> listaClientes = new ArrayList<>();
        List<Pedido> listaPedidos = new ArrayList<>();

        // Inicialização de dados para demonstração e testes práticos imediatos
        carregarDadosIniciais(listaClientes, listaPedidos);

        boolean continuar = true;
        while (continuar) {
            System.out.println("\n==================================================================");
            System.out.println("          SISTEMA DE GESTÃO DE PIZZARIA - UNYLEYA                 ");
            System.out.println("==================================================================");
            System.out.println("1 - Fazer um novo pedido");
            System.out.println("2 - Alterar um pedido existente (Requisito 1)");
            System.out.println("3 - Adicionar um cliente");
            System.out.println("4 - Gerar relatório de vendas e Grafo de Sabores (Requisito 2)");
            System.out.println("5 - Listar todos os clientes cadastrados");
            System.out.println("6 - Calcular frete de entrega (Requisito 3)");
            System.out.println("7 - Listar todos os pedidos");
            System.out.println("9 - Sair");
            System.out.println("------------------------------------------------------------------");
            System.out.print("Escolha uma opção: ");

            if (!scanner.hasNextInt()) {
                System.out.println(">> Por favor, digite um número válido do menu!");
                scanner.nextLine();
                continue;
            }

            int opcao = scanner.nextInt();
            scanner.nextLine();
            System.out.println();

            switch (opcao) {
                case 1:
                    fazerPedido(scanner, listaPedidos, listaClientes);
                    break;
                case 2:
                    alterarPedido(scanner, listaPedidos);
                    break;
                case 3:
                    listaClientes.add(adicionarCliente(scanner));
                    System.out.println(">> Cliente adicionado com sucesso!");
                    break;
                case 4:
                    gerarRelatorio(listaPedidos);
                    break;
                case 5:
                    gerarListaClientes(listaClientes);
                    break;
                case 6:
                    menuCalculoFrete(scanner, listaPedidos);
                    break;
                case 7:
                    listarTodosPedidos(listaPedidos);
                    break;
                case 9:
                    System.out.println("Encerrando o sistema da Pizzaria. Até logo!");
                    continuar = false;
                    scanner.close();
                    break;
                default:
                    System.out.println(">> Opção inválida! Escolha entre as opções listadas.");
                    break;
            }
        }
    }

    // =========================================================================
    // REQUISITO 1: MÉTODO ALTERAR PEDIDO
    // =========================================================================
    /**
     * Permite alterar pedidos existentes com os seguintes recursos:
     * - Busca por ID numérico ou por Nome do cliente
     * - Adição ou remoção de pizzas no pedido
     * - Alteração do sabor de uma pizza existente
     */
    private static void alterarPedido(Scanner scanner, List<Pedido> listaPedidos) {
        System.out.println("==================================================================");
        System.out.println("                   ALTERAÇÃO DE PEDIDO EXISTENTE                  ");
        System.out.println("==================================================================");

        if (listaPedidos.isEmpty()) {
            System.out.println(">> Não há nenhum pedido cadastrado no momento para alteração.");
            return;
        }

        System.out.println("Como deseja localizar o pedido?");
        System.out.println("1 - Buscar por ID do Pedido");
        System.out.println("2 - Buscar por Nome do Cliente");
        System.out.print("Opção: ");
        int tipoBusca = scanner.nextInt();
        scanner.nextLine();

        Pedido pedidoSelecionado = null;

        if (tipoBusca == 1) {
            System.out.print("Informe o ID do pedido: ");
            int idBusca = scanner.nextInt();
            scanner.nextLine();

            for (Pedido p : listaPedidos) {
                if (p.getId() == idBusca) {
                    pedidoSelecionado = p;
                    break;
                }
            }
        } else if (tipoBusca == 2) {
            System.out.print("Informe o nome (ou parte dele) do cliente: ");
            String nomeBusca = scanner.nextLine().trim().toLowerCase();

            List<Pedido> pedidosCliente = new ArrayList<>();
            for (Pedido p : listaPedidos) {
                if (p.getCliente().getNome().toLowerCase().contains(nomeBusca)) {
                    pedidosCliente.add(p);
                }
            }

            if (pedidosCliente.isEmpty()) {
                System.out.println(">> Nenhum pedido localizado para o cliente informado.");
                return;
            } else if (pedidosCliente.size() == 1) {
                pedidoSelecionado = pedidosCliente.get(0);
            } else {
                System.out.println("\nForam localizados múltiplos pedidos para este cliente:");
                for (int i = 0; i < pedidosCliente.size(); i++) {
                    System.out.println(String.format(" [%d] %s", (i + 1), pedidosCliente.get(i)));
                }
                System.out.print("Selecione qual deseja alterar (1 a " + pedidosCliente.size() + "): ");
                int escolha = scanner.nextInt();
                scanner.nextLine();
                if (escolha >= 1 && escolha <= pedidosCliente.size()) {
                    pedidoSelecionado = pedidosCliente.get(escolha - 1);
                }
            }
        } else {
            System.out.println(">> Opção de busca inválida.");
            return;
        }

        if (pedidoSelecionado == null) {
            System.out.println(">> Pedido não localizado no sistema.");
            return;
        }

        // Sub-menu de alterações no pedido localizado
        boolean emAlteracao = true;
        Cardapio cardapio = new Cardapio();

        while (emAlteracao) {
            System.out.println("\n--- PEDIDO SELECIONADO ---");
            pedidoSelecionado.exibirDetalhes();

            System.out.println("\nO que você deseja alterar neste pedido?");
            System.out.println("1 - Adicionar nova pizza ao pedido");
            System.out.println("2 - Remover uma pizza do pedido");
            System.out.println("3 - Alterar o sabor de uma pizza existente");
            System.out.println("0 - Concluir alterações e voltar ao menu principal");
            System.out.print("Opção: ");
            int subOpcao = scanner.nextInt();
            scanner.nextLine();

            switch (subOpcao) {
                case 1:
                    // 1.1 Adicionar nova pizza
                    Pizza novaPizza = montarPizza(scanner, cardapio);
                    pedidoSelecionado.adicionarPizza(novaPizza);
                    System.out.println("\n>> SUCESSO: Nova pizza adicionada ao pedido!");
                    break;

                case 2:
                    // 1.2 Remover pizza existente
                    if (pedidoSelecionado.getPizzas().size() <= 1) {
                        System.out.println(">> AVISO: O pedido possui apenas 1 pizza. Não é permitido esvaziar o pedido completamente.");
                        break;
                    }
                    System.out.println("\nEscolha a pizza a ser removida:");
                    for (int i = 0; i < pedidoSelecionado.getPizzas().size(); i++) {
                        System.out.println(String.format(" [%d] %s", (i + 1), pedidoSelecionado.getPizzas().get(i)));
                    }
                    System.out.print("Número da pizza a remover: ");
                    int pizzaRemover = scanner.nextInt() - 1;
                    scanner.nextLine();

                    if (pedidoSelecionado.removerPizza(pizzaRemover)) {
                        System.out.println("\n>> SUCESSO: Pizza removida do pedido com êxito!");
                    } else {
                        System.out.println(">> Índice de pizza inválido.");
                    }
                    break;

                case 3:
                    // 1.3 Alterar sabor da pizza
                    System.out.println("\nSelecione qual pizza deseja modificar o sabor:");
                    for (int i = 0; i < pedidoSelecionado.getPizzas().size(); i++) {
                        System.out.println(String.format(" [%d] %s", (i + 1), pedidoSelecionado.getPizzas().get(i)));
                    }
                    System.out.print("Número da pizza: ");
                    int pizzaModificar = scanner.nextInt() - 1;
                    scanner.nextLine();

                    if (pizzaModificar >= 0 && pizzaModificar < pedidoSelecionado.getPizzas().size()) {
                        Pizza pizzaAlvo = pedidoSelecionado.getPizzas().get(pizzaModificar);
                        System.out.println("\nSabores atuais desta pizza: " + pizzaAlvo.getSabores());

                        System.out.println("Escolha o novo sabor no cardápio:");
                        List<String> todosSabores = new ArrayList<>(cardapio.getCardapio().keySet());
                        for (int i = 0; i < todosSabores.size(); i++) {
                            System.out.println(String.format(" [%d] %-20s - R$ %.2f",
                                    (i + 1), todosSabores.get(i), cardapio.getCardapio().get(todosSabores.get(i))));
                        }
                        System.out.print("Selecione o novo sabor: ");
                        int novoSaborIndex = scanner.nextInt() - 1;
                        scanner.nextLine();

                        if (novoSaborIndex >= 0 && novoSaborIndex < todosSabores.size()) {
                            String novoSabor = todosSabores.get(novoSaborIndex);
                            List<String> novosSaboresList = new ArrayList<>();
                            novosSaboresList.add(novoSabor);

                            pizzaAlvo.setSabores(novosSaboresList);
                            pizzaAlvo.setPreco(cardapio.getPrecoJusto(novosSaboresList));
                            pedidoSelecionado.recalcularValorTotal();

                            System.out.println("\n>> SUCESSO: Sabor da pizza atualizado para \"" + novoSabor + "\"!");
                        } else {
                            System.out.println(">> Sabor inválido.");
                        }
                    } else {
                        System.out.println(">> Pizza não encontrada.");
                    }
                    break;

                case 0:
                    emAlteracao = false;
                    System.out.println("\n>> Alterações salvas com sucesso!");
                    break;

                default:
                    System.out.println(">> Opção inválida.");
                    break;
            }
        }
    }

    // =========================================================================
    // REQUISITO 2: MÉTODO GERAR RELATÓRIO COM GRAFOS
    // =========================================================================
    /**
     * Gera relatório analítico de vendas e utiliza grafos para mapear
     * coocorrências de sabores frequentemente pedidos juntos na mesma pizza.
     */
    private static void gerarRelatorio(List<Pedido> listaPedidos) {
        System.out.println("==================================================================");
        System.out.println("           RELATÓRIO GERENCIAL & ANÁLISE DE GRAFOS DE SABORES     ");
        System.out.println("==================================================================");

        if (listaPedidos.isEmpty()) {
            System.out.println("Nenhum pedido registrado para geração de relatórios.");
            return;
        }

        double faturamentoTotal = 0.0;
        int totalPizzasVendidas = 0;
        Map<String, Integer> frequenciaSabores = new HashMap<>();

        // Grafo de Coocorrência: Sabor A -> (Sabor B -> Quantidade de vezes pedidos na mesma pizza)
        Map<String, Map<String, Integer>> grafoConexoesSabores = new HashMap<>();

        for (Pedido pedido : listaPedidos) {
            faturamentoTotal += pedido.getValorTotal();

            for (Pizza pizza : pedido.getPizzas()) {
                totalPizzasVendidas++;
                List<String> sabores = pizza.getSabores();

                // 1. Contagem individual de frequência dos sabores
                for (String sabor : sabores) {
                    frequenciaSabores.put(sabor, frequenciaSabores.getOrDefault(sabor, 0) + 1);
                    grafoConexoesSabores.putIfAbsent(sabor, new HashMap<>());
                }

                // 2. Construção do Grafo Não-Direcionado de Sabores Compartilhados
                // Se a pizza tiver mais de 1 sabor, adiciona arestas entre cada par
                if (sabores.size() > 1) {
                    for (int i = 0; i < sabores.size(); i++) {
                        for (int j = i + 1; j < sabores.size(); j++) {
                            String s1 = sabores.get(i);
                            String s2 = sabores.get(j);

                            // Aresta S1 <---> S2
                            Map<String, Integer> vizinhosS1 = grafoConexoesSabores.get(s1);
                            vizinhosS1.put(s2, vizinhosS1.getOrDefault(s2, 0) + 1);

                            Map<String, Integer> vizinhosS2 = grafoConexoesSabores.get(s2);
                            vizinhosS2.put(s1, vizinhosS2.getOrDefault(s1, 0) + 1);
                        }
                    }
                }
            }
        }

        // PARTE 1: FATURAMENTO TOTAL
        System.out.println("\n--- 1. INDICADORES FINANCEIROS GERAIS ---");
        System.out.println("Total de Pedidos Realizados : " + listaPedidos.size());
        System.out.println("Total de Pizzas Vendidas    : " + totalPizzasVendidas + " unidades");
        System.out.printf("FATURAMENTO TOTAL ACUMULADO : R$ %.2f%n", faturamentoTotal);

        // PARTE 2: SABORES MAIS PEDIDOS (RANKING DECRESCENTE)
        System.out.println("\n--- 2. RANKING DE SABORES MAIS PEDIDOS ---");
        List<Map.Entry<String, Integer>> rankingSabores = new ArrayList<>(frequenciaSabores.entrySet());
        Collections.sort(rankingSabores, new Comparator<Map.Entry<String, Integer>>() {
            @Override
            public int compare(Map.Entry<String, Integer> a, Map.Entry<String, Integer> b) {
                return b.getValue().compareTo(a.getValue()); // Ordem decrescente
            }
        });

        int posicao = 1;
        for (Map.Entry<String, Integer> entry : rankingSabores) {
            System.out.println(String.format(" %02dº Lugar: %-22s -> %3d pedido(s)",
                    posicao, entry.getKey(), entry.getValue()));
            posicao++;
        }

        // PARTE 3: GRAFO DE CONEXÕES ENTRE SABORES (COOCORRÊNCIA NA MESMA PIZZA)
        System.out.println("\n--- 3. GRAFO DE LIGAÇÃO ENTRE SABORES (COMBINAÇÕES FREQUENTES) ---");
        System.out.println("Conexões mapeadas entre sabores que aparecem juntos na mesma pizza:");
        System.out.println("------------------------------------------------------------------");

        boolean encontrouConexao = false;
        List<String> arestasRegistradas = new ArrayList<>();

        for (Map.Entry<String, Map<String, Integer>> vertice : grafoConexoesSabores.entrySet()) {
            String saborOrigem = vertice.getKey();
            Map<String, Integer> vizinhos = vertice.getValue();

            for (Map.Entry<String, Integer> aresta : vizinhos.entrySet()) {
                String saborDestino = aresta.getKey();
                int pesoAresta = aresta.getValue();

                // Chave canônica para evitar imprimir arestas duplicadas (A-B e B-A)
                String par = (saborOrigem.compareTo(saborDestino) < 0)
                        ? saborOrigem + " <---> " + saborDestino
                        : saborDestino + " <---> " + saborOrigem;

                if (!arestasRegistradas.contains(par)) {
                    arestasRegistradas.add(par);
                    System.out.println(String.format(" [GRAFO] %-45s | Peso: %d pizza(s) juntas", par, pesoAresta));
                    encontrouConexao = true;
                }
            }
        }

        if (!encontrouConexao) {
            System.out.println("Nenhuma pizza de sabores combinados (meio a meio) registrada ainda.");
        } else {
            System.out.println("\nRepresentação em Lista de Adjacência do Grafo de Sabores:");
            for (Map.Entry<String, Map<String, Integer>> vertice : grafoConexoesSabores.entrySet()) {
                if (!vertice.getValue().isEmpty()) {
                    System.out.print("📍 Vértice [" + vertice.getKey() + "] ──▶ ");
                    List<String> conexoes = new ArrayList<>();
                    for (Map.Entry<String, Integer> v : vertice.getValue().entrySet()) {
                        conexoes.add(v.getKey() + " (" + v.getValue() + "x)");
                    }
                    System.out.println(String.join(", ", conexoes));
                }
            }
        }

        System.out.println("==================================================================");
    }

    // =========================================================================
    // REQUISITO 3: MÉTODO PARA CÁLCULO DE FRETE
    // =========================================================================
    /**
     * Calcula o frete de entrega considerando:
     * 1. A distância em quilômetros (R$ 1,50 por km)
     * 2. O peso/volume do pedido (R$ 1,00 por pizza no pedido)
     * 3. Taxa base de saída da pizzaria (R$ 5,00)
     * 
     * @param distanciaKm Distância em quilômetros
     * @param quantidadePizzas Quantidade de pizzas (peso/volume da entrega)
     * @return Valor monetário do frete em Reais (R$)
     */
    public static double calcularFrete(double distanciaKm, int quantidadePizzas) {
        double taxaBase = 5.00;
        double custoPorKm = 1.50;
        double adicionalPorPizza = 1.00; // Representa o peso/volume na bag do motoboy

        if (distanciaKm < 0) distanciaKm = 0;
        if (quantidadePizzas < 0) quantidadePizzas = 0;

        double valorFrete = taxaBase + (distanciaKm * custoPorKm) + (quantidadePizzas * adicionalPorPizza);
        return valorFrete;
    }

    /**
     * Menu interativo para simular ou aplicar cálculo de frete aos pedidos.
     */
    private static void menuCalculoFrete(Scanner scanner, List<Pedido> listaPedidos) {
        System.out.println("==================================================================");
        System.out.println("                 CÁLCULO DE FRETE DE ENTREGA                      ");
        System.out.println("==================================================================");
        System.out.println("1 - Calcular e vincular frete a um pedido existente");
        System.out.println("2 - Simular cálculo de frete livre");
        System.out.print("Opção: ");
        int op = scanner.nextInt();
        scanner.nextLine();

        if (op == 1) {
            if (listaPedidos.isEmpty()) {
                System.out.println(">> Nenhum pedido cadastrado no momento.");
                return;
            }
            System.out.println("\nSelecione o pedido:");
            for (int i = 0; i < listaPedidos.size(); i++) {
                System.out.println(String.format(" [%d] %s", (i + 1), listaPedidos.get(i)));
            }
            System.out.print("Número do pedido: ");
            int idx = scanner.nextInt() - 1;
            scanner.nextLine();

            if (idx >= 0 && idx < listaPedidos.size()) {
                Pedido p = listaPedidos.get(idx);
                System.out.print("Informe a distância até o endereço de entrega (em km): ");
                double dist = scanner.nextDouble();
                scanner.nextLine();

                double frete = calcularFrete(dist, p.getPizzas().size());
                p.setDistanciaKm(dist);
                p.setValorFrete(frete);

                System.out.println("\n--- DETALHAMENTO DO FRETE CALCULADO ---");
                System.out.printf("Distância           : %.2f km (R$ 1,50/km = R$ %.2f)%n", dist, (dist * 1.50));
                System.out.printf("Pizzas (Peso/Carga) : %d un (R$ 1,00/pizza = R$ %.2f)%n", p.getPizzas().size(), (p.getPizzas().size() * 1.00));
                System.out.println("Taxa Base de Saída  : R$ 5,00");
                System.out.printf(">> VALOR DO FRETE   : R$ %.2f%n", frete);
                System.out.printf(">> TOTAL COM FRETE  : R$ %.2f%n", p.getValorFinalComFrete());
            } else {
                System.out.println(">> Pedido inválido.");
            }
        } else if (op == 2) {
            System.out.print("Informe a distância até o cliente (em km): ");
            double dist = scanner.nextDouble();
            System.out.print("Informe a quantidade de pizzas (peso do pedido): ");
            int qtd = scanner.nextInt();
            scanner.nextLine();

            double freteSimulado = calcularFrete(dist, qtd);
            System.out.println("\n--- RESULTADO DA SIMULAÇÃO ---");
            System.out.printf("Distância        : %.2f km%n", dist);
            System.out.printf("Carga de Pizzas  : %d pizza(s)%n", qtd);
            System.out.printf(">> VALOR FRETE   : R$ %.2f%n", freteSimulado);
        } else {
            System.out.println(">> Opção inválida.");
        }
        System.out.println("==================================================================");
    }

    // =========================================================================
    // MÉTODOS AUXILIARES DO SISTEMA
    // =========================================================================
    private static void fazerPedido(Scanner scanner, List<Pedido> listaPedidos, List<Cliente> listaClientes) {
        if (listaClientes.isEmpty()) {
            System.out.println(">> Nenhum cliente cadastrado! Cadastre um cliente antes de realizar um pedido.");
            return;
        }

        List<Pizza> pizzas = new ArrayList<>();
        System.out.println("FAZER PEDIDO");

        int x = 1;
        System.out.println("Selecione um cliente: ");
        for (Cliente cliente : listaClientes) {
            System.out.println(x + " - " + cliente.getNome());
            x++;
        }
        System.out.print("Opção: ");
        int clienteIndex = scanner.nextInt() - 1;
        scanner.nextLine();

        if (clienteIndex < 0 || clienteIndex >= listaClientes.size()) {
            System.out.println(">> Cliente inválido.");
            return;
        }

        Cardapio cardapio = new Cardapio();
        boolean continuarAdicionando = true;

        while (continuarAdicionando) {
            Pizza pizza = montarPizza(scanner, cardapio);
            pizzas.add(pizza);

            System.out.println(">> Pizza adicionada com sucesso!");
            System.out.print("Deseja cadastrar mais uma pizza no pedido? (1 - Sim, 2 - Não): ");
            int opcao = scanner.nextInt();
            scanner.nextLine();

            if (opcao != 1) {
                continuarAdicionando = false;
            }
        }

        Pedido pedido = new Pedido(listaPedidos.size() + 1, listaClientes.get(clienteIndex), pizzas, somarPizzas(pizzas));
        listaPedidos.add(pedido);

        System.out.println("\n>> PEDIDO #" + pedido.getId() + " REGISTRADO COM SUCESSO!");
        pedido.exibirDetalhes();
    }

    private static Pizza montarPizza(Scanner scanner, Cardapio cardapio) {
        int x = 1;
        System.out.println("\nQual o tamanho da pizza? ");
        for (TamanhoPizza tamanhos : Pizza.TamanhoPizza.values()) {
            System.out.println(x + " - " + tamanhos);
            x++;
        }
        System.out.print("Opção: ");
        int tamanho = scanner.nextInt();
        scanner.nextLine();

        int quantiSabores = 0;
        while (quantiSabores < 1 || quantiSabores > 4) {
            System.out.print("Digite a quantidade de sabores (1 a 4): ");
            quantiSabores = scanner.nextInt();
            scanner.nextLine();
        }

        List<String> saboresList = new ArrayList<>(cardapio.getCardapio().keySet());
        List<String> saboresSelect = new ArrayList<>();

        for (int i = 0; i < quantiSabores; i++) {
            System.out.println("Selecione o " + (i + 1) + "º sabor: ");
            x = 1;
            for (String sabor : saboresList) {
                System.out.println(x + " - " + sabor);
                x++;
            }
            System.out.print("Opção: ");
            int opcao = scanner.nextInt();
            scanner.nextLine();
            if (opcao >= 1 && opcao <= saboresList.size()) {
                saboresSelect.add(saboresList.get(opcao - 1));
            } else {
                System.out.println("Opção inválida, adicionando Margherita por padrão.");
                saboresSelect.add("Margherita");
            }
        }

        return new Pizza(saboresSelect, cardapio.getPrecoJusto(saboresSelect), TamanhoPizza.getByIndex(tamanho - 1));
    }

    private static double somarPizzas(List<Pizza> pizzas) {
        double valorTotal = 0;
        for (Pizza pizza : pizzas) {
            valorTotal += pizza.getPreco();
        }
        return valorTotal;
    }

    private static Cliente adicionarCliente(Scanner scanner) {
        System.out.println("ADICIONAR CLIENTE");
        System.out.print("Digite o nome do cliente: ");
        String nome = scanner.nextLine();
        System.out.print("Digite o endereço do cliente: ");
        String endereco = scanner.nextLine();
        System.out.print("Digite o telefone do cliente: ");
        String telefone = scanner.nextLine();
        System.out.print("Digite o email do cliente: ");
        String email = scanner.nextLine();

        return new Cliente(nome, endereco, telefone, email);
    }

    private static void gerarListaClientes(List<Cliente> listaClientes) {
        System.out.println("======================= CLIENTES CADASTRADOS =====================");
        if (listaClientes.isEmpty()) {
            System.out.println("Lista de clientes está vazia.");
        } else {
            int x = 1;
            for (Cliente cliente : listaClientes) {
                System.out.println(String.format("Cliente #%d: %s", x, cliente));
                x++;
            }
        }
        System.out.println("==================================================================");
    }

    private static void listarTodosPedidos(List<Pedido> listaPedidos) {
        System.out.println("======================= PEDIDOS REGISTRADOS ======================");
        if (listaPedidos.isEmpty()) {
            System.out.println("Nenhum pedido cadastrado no momento.");
        } else {
            for (Pedido p : listaPedidos) {
                System.out.println(p);
            }
        }
        System.out.println("==================================================================");
    }

    /**
     * Carrega dados simulados de clientes e pedidos para testes rápidos da aplicação.
     */
    private static void carregarDadosIniciais(List<Cliente> clientes, List<Pedido> pedidos) {
        Cliente c1 = new Cliente("Flaviano Silva", "Rua das Acácias, 120", "11987654321", "flaviano@email.com");
        Cliente c2 = new Cliente("Mariana Souza", "Av. Paulista, 1500", "11912345678", "mariana@email.com");
        Cliente c3 = new Cliente("Carlos Eduardo", "Rua Augusta, 450", "11999887766", "carlos@email.com");

        clientes.add(c1);
        clientes.add(c2);
        clientes.add(c3);

        Cardapio cardapio = new Cardapio();

        // Pedido 1: Flaviano Silva - 2 pizzas (uma com 2 sabores, para formar aresta no Grafo)
        List<Pizza> pizzas1 = new ArrayList<>();
        List<String> s1 = new ArrayList<>();
        s1.add("Calabresa");
        s1.add("Quatro queijos"); // Conexão no grafo
        pizzas1.add(new Pizza(s1, cardapio.getPrecoJusto(s1), TamanhoPizza.GRANDE));

        List<String> s2 = new ArrayList<>();
        s2.add("Margherita");
        pizzas1.add(new Pizza(s2, cardapio.getPrecoJusto(s2), TamanhoPizza.BROTO));

        Pedido p1 = new Pedido(1, c1, pizzas1, somarPizzas(pizzas1));
        p1.setDistanciaKm(5.0);
        p1.setValorFrete(calcularFrete(5.0, 2));
        pedidos.add(p1);

        // Pedido 2: Mariana Souza - 1 pizza (Calabresa e Frango com Catupiry)
        List<Pizza> pizzas2 = new ArrayList<>();
        List<String> s3 = new ArrayList<>();
        s3.add("Calabresa");
        s3.add("Frango com Catupiry"); // Mais uma aresta
        pizzas2.add(new Pizza(s3, cardapio.getPrecoJusto(s3), TamanhoPizza.GRANDE));

        Pedido p2 = new Pedido(2, c2, pizzas2, somarPizzas(pizzas2));
        p2.setDistanciaKm(3.5);
        p2.setValorFrete(calcularFrete(3.5, 1));
        pedidos.add(p2);

        // Pedido 3: Carlos Eduardo - 1 pizza (Pepperoni e Quatro queijos)
        List<Pizza> pizzas3 = new ArrayList<>();
        List<String> s4 = new ArrayList<>();
        s4.add("Pepperoni");
        s4.add("Quatro queijos"); // Mais uma aresta
        pizzas3.add(new Pizza(s4, cardapio.getPrecoJusto(s4), TamanhoPizza.GIGA));

        Pedido p3 = new Pedido(3, c3, pizzas3, somarPizzas(pizzas3));
        p3.setDistanciaKm(8.0);
        p3.setValorFrete(calcularFrete(8.0, 1));
        pedidos.add(p3);
    }
}
