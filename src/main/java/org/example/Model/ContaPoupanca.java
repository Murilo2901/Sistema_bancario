package org.example.Model;

public class ContaPoupanca extends Conta {
    private static final double TAXA_RENDIMENTO = 0.03;

    public ContaPoupanca(int id, String numero, Cliente titular, double saldo) {
        super(id, numero, titular, saldo);
    }

    public ContaPoupanca(String numero, Cliente titular, double saldo) {
        super(numero, titular, saldo);
    }

    public void aplicarRendimento() {
        double rendimento = getSaldo() * TAXA_RENDIMENTO;
        setSaldo(getSaldo() + rendimento);
        System.out.println("Rendimento de " + rendimento + " aplicado! Novo saldo: " + getSaldo());
    }
    @Override
    public String mostrarDetalhes() {
        return "[Conta Poupança] " + toString();
    }
}
