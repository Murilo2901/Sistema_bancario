package org.example.Model;

public abstract class Conta {
    private int id;
    private String numero;
    private Cliente titular;
    private double saldo;

    public Conta(int id, String numero, Cliente titular, double saldo) {
        this.id = id;
        this.numero = numero;
        this.titular = titular;
        this.saldo = saldo;
    }

    public Conta(String numero, Cliente titular, double saldo) {
        this.numero = numero;
        this.titular = titular;
        this.saldo = saldo;
    }

    public int getId() {
        return id;
    }

    public String getNumero() {
        return numero;
    }

    public Cliente getTitular() {
        return titular;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public void setTitular(Cliente titular) {
        this.titular = titular;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public void depositar(double valor) {
        if (valor > 0) this.saldo += valor;
    }

    public void sacar(double valor) throws Exception {
        if (valor <= 0) throw new Exception("Valor inválido para saque.");
        if (valor > saldo) throw new Exception("Saldo insuficiente.");
        this.saldo -= valor;
    }

    public void transferir(Conta destino, double valor) throws Exception {
        this.sacar(valor);
        destino.depositar(valor);
    }

    public abstract String mostrarDetalhes();

    @Override
    public String toString() {
        return "Conta[id=" + id + ", numero=" + numero + ", titular=" + titular.getNome() + ", saldo=" + saldo + "]";
    }
}
