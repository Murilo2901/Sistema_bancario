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

    public boolean sacar(double valor) {
        if (valor <= 0) return false;
        if (valor > saldo) return false;
        this.saldo -= valor;
        return true;
    }

    public boolean transferir(Conta destino, double valor) {
        boolean sacou = this.sacar(valor);
        if (!sacou) return false;
        destino.depositar(valor);
        return true;
    }

    public abstract String mostrarDetalhes();

    @Override
    public String toString() {
        return "Conta[id=" + id + ", numero=" + numero + ", titular=" + titular.getNome() + ", saldo=" + saldo + "]";
    }
}
