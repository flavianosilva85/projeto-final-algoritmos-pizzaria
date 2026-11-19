package Projeto;

import java.util.List;

/**
 * Representa um pedido realizado na pizzaria, contendo cliente, lista de pizzas e valores financeiros.
 */
public class Pedido {
    private int id;
    private Cliente cliente;
    private List<Pizza> pizzas;
    private double valorTotal;
    private double distanciaKm;
    private double valorFrete;

    public Pedido(int id, Cliente cliente, List<Pizza> pizzas, double valorTotal) {
        this.id = id;
        this.cliente = cliente;
        this.pizzas = pizzas;
        this.valorTotal = valorTotal;
        this.distanciaKm = 0.0;
        this.valorFrete = 0.0;
    }

    // Getters e Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public List<Pizza> getPizzas() {
        return pizzas;
    }

    public void setPizzas(List<Pizza> pizzas) {
        this.pizzas = pizzas;
        recalcularValorTotal();
    }

    public double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public double getDistanciaKm() {
        return distanciaKm;
    }

    public void setDistanciaKm(double distanciaKm) {
        this.distanciaKm = distanciaKm;
    }

    public double getValorFrete() {
        return valorFrete;
    }

    public void setValorFrete(double valorFrete) {
        this.valorFrete = valorFrete;
    }

    public double getValorFinalComFrete() {
        return valorTotal + valorFrete;
    }

    /**
     * Adiciona uma nova pizza ao pedido e atualiza automaticamente o valor total.
     */
    public void adicionarPizza(Pizza pizza) {
        if (pizza != null) {
            this.pizzas.add(pizza);
            recalcularValorTotal();
        }
    }

    /**
     * Remove uma pizza do pedido pelo índice e recalcula o valor total.
     */
    public boolean removerPizza(int index) {
        if (index >= 0 && index < pizzas.size()) {
            pizzas.remove(index);
            recalcularValorTotal();
            return true;
        }
        return false;
    }

    /**
     * Recalcula a soma dos preços de todas as pizzas que compõem o pedido.
     */
    public void recalcularValorTotal() {
        double soma = 0.0;
        for (Pizza p : pizzas) {
            soma += p.getPreco();
        }
        this.valorTotal = soma;
    }

    /**
     * Exibe o extrato detalhado do pedido no console.
     */
    public void exibirDetalhes() {
        System.out.println("==================================================================");
        System.out.println(String.format("PEDIDO #%03d - Cliente: %s", id, cliente.getNome()));
        System.out.println("Telefone: " + cliente.getTelefone() + " | Endereço: " + cliente.getEndereco());
        System.out.println("------------------------------------------------------------------");
        System.out.println("Itens do Pedido (" + pizzas.size() + " pizza(s)):");
        for (int i = 0; i < pizzas.size(); i++) {
            System.out.println(String.format("  [%d] %s", (i + 1), pizzas.get(i)));
        }
        System.out.println("------------------------------------------------------------------");
        System.out.printf("Subtotal Pizzas  : R$ %.2f%n", valorTotal);
        if (valorFrete > 0) {
            System.out.printf("Frete (%.1f km)   : R$ %.2f%n", distanciaKm, valorFrete);
            System.out.printf("TOTAL A PAGAR    : R$ %.2f%n", getValorFinalComFrete());
        }
        System.out.println("==================================================================");
    }

    @Override
    public String toString() {
        return String.format("Pedido #%03d | Cliente: %-15s | Pizzas: %d | Total: R$ %.2f",
                id, cliente.getNome(), pizzas.size(), valorTotal);
    }
}
