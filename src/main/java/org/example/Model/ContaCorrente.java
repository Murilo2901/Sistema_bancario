package org.example.Model;

public class ContaCorrente extends Conta {
    private static final double TAXA_SAQUE = 2.50;

    public ContaCorrente(int id, String numero, Cliente titular, double saldo) {
        super(id, numero, titular, saldo);
    }

    public ContaCorrente(String numero, Cliente titular, double saldo) {
        super(numero, titular, saldo);
    }

    @Override
    public boolean sacar(double valor) {
        double total = valor + TAXA_SAQUE;
        if (getSaldo() >= total && valor > 0) {
            setSaldo(getSaldo() - total);
            return true;
        }
        return false;
    }

    @Override
    public String mostrarDetalhes() {
        return "[Conta Corrente] " + toString();
    }
}