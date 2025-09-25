package org.example.Model;

public class ContaPoupanca extends Conta {

    public ContaPoupanca(int id, String numero, Cliente titular, double saldo) {
        super(id, numero, titular, saldo);
    }

    public ContaPoupanca(String numero, Cliente titular, double saldo) {
        super(numero, titular, saldo);
    }

    @Override
    public String mostrarDetalhes() {
        return "[Conta Poupança] " + toString();
    }
}
