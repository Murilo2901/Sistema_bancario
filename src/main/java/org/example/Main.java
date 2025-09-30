package org.example;

import org.example.DAO.ContaDAO;
import org.example.Model.*;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        ContaDAO dao = new ContaDAO();
        Scanner sc = new Scanner(System.in);

        int opcao;
        do {
            System.out.println("\n===== MENU BANCÁRIO =====");
            System.out.println("1 - Cadastrar conta");
            System.out.println("2 - Listar contas");
            System.out.println("3 - Depositar");
            System.out.println("4 - Sacar");
            System.out.println("5 - Transferir");
            System.out.println("6 - Atualizar conta");
            System.out.println("7 - Remover conta");
            System.out.println("8 - Buscar conta por número");
            System.out.println("9 - Buscar contas por titular");
            System.out.println("10 - Aplicar rendimento poupança");
            System.out.println("0 - Sair");
            System.out.print("Escolha: ");
            opcao = sc.nextInt();
            sc.nextLine();

            try {
                switch (opcao) {
                    case 1 -> {
                        System.out.print("Número da conta: ");
                        String numero = sc.nextLine();
                        System.out.print("Nome do titular: ");
                        String titularNome = sc.nextLine();
                        System.out.print("CPF do titular (opcional): ");
                        String titularCpf = sc.nextLine();
                        System.out.print("Saldo inicial: ");
                        double saldo = sc.nextDouble();
                        sc.nextLine();

                        System.out.print("Tipo (1 - Corrente | 2 - Poupança): ");
                        int tipo = sc.nextInt();
                        sc.nextLine();

                        Conta conta;
                        Cliente cliente = new Cliente(titularNome, titularCpf);
                        if (tipo == 1) {
                            conta = new ContaCorrente(numero, cliente, saldo);
                        } else {
                            conta = new ContaPoupanca(numero, cliente, saldo);
                        }

                        dao.inserirConta(conta);
                        System.out.println("Conta cadastrada com sucesso!");
                    }
                    case 2 -> {
                        List<Conta> contas = dao.listarContas();
                        if (contas.isEmpty()) {
                            System.out.println("Nenhuma conta cadastrada.");
                        } else {
                            for (Conta c : contas) {
                                if (c instanceof ContaCorrente) {
                                    System.out.println("[Conta Corrente] " + c);
                                } else if (c instanceof ContaPoupanca) {
                                    System.out.println("[Conta Poupança] " + c);
                                } else {
                                    System.out.println(c);
                                }
                            }
                        }
                    }
                    case 3 -> {
                        System.out.print("Número da conta: ");
                        String numero = sc.nextLine();
                        System.out.print("Valor depósito: ");
                        double valor = sc.nextDouble();
                        sc.nextLine();

                        Conta conta = dao.buscarPorNumero(numero);
                        if (conta != null) {
                            conta.depositar(valor);
                            dao.atualizarSaldo(conta.getId(), conta.getSaldo());
                            System.out.println("Depósito realizado!");
                        } else {
                            System.out.println("Conta não encontrada!");
                        }
                    }
                    case 4 -> {
                        System.out.print("Número da conta: ");
                        String numero = sc.nextLine();
                        System.out.print("Valor saque: ");
                        double valor = sc.nextDouble();
                        sc.nextLine();

                        Conta conta = dao.buscarPorNumero(numero);
                        if (conta != null) {
                            if (conta.sacar(valor)) {
                                dao.atualizarSaldo(conta.getId(), conta.getSaldo());
                                System.out.println("Saque realizado!");
                            } else {
                                System.out.println("Saldo insuficiente!");
                            }
                        } else {
                            System.out.println("Conta não encontrada!");
                        }
                    }
                    case 5 -> {
                        System.out.print("Número conta origem: ");
                        String origem = sc.nextLine();
                        System.out.print("Número conta destino: ");
                        String destino = sc.nextLine();
                        System.out.print("Valor transferência: ");
                        double valor = sc.nextDouble();
                        sc.nextLine();

                        Conta contaOrigem = dao.buscarPorNumero(origem);
                        Conta contaDestino = dao.buscarPorNumero(destino);

                        if (contaOrigem != null && contaDestino != null) {
                            if (contaOrigem.transferir(contaDestino, valor)) {
                                dao.atualizarSaldo(contaOrigem.getId(), contaOrigem.getSaldo());
                                dao.atualizarSaldo(contaDestino.getId(), contaDestino.getSaldo());
                                System.out.println("Transferência realizada!");
                            } else {
                                System.out.println("Saldo insuficiente!");
                            }
                        } else {
                            System.out.println("Conta(s) não encontrada(s)!");
                        }
                    }
                    case 6 -> {
                        System.out.print("Número da conta a atualizar: ");
                        String numero = sc.nextLine();
                        Conta conta = dao.buscarPorNumero(numero);

                        if (conta != null) {
                            System.out.print("Novo titular: ");
                            String novoTitularNome = sc.nextLine();
                            System.out.print("Novo CPF (opcional): ");
                            String novoTitularCpf = sc.nextLine();
                            Cliente novoCliente = new Cliente(novoTitularNome, novoTitularCpf);
                            conta.setTitular(novoCliente);
                            dao.atualizarConta(conta);
                            System.out.println("Conta atualizada!");
                        } else {
                            System.out.println("Conta não encontrada!");
                        }
                    }
                    case 7 -> {
                        System.out.print("Número da conta a remover: ");
                        String numero = sc.nextLine();
                        Conta conta = dao.buscarPorNumero(numero);
                        if (conta != null) {
                            dao.removerConta(conta.getId());
                            System.out.println("Conta removida!");
                        } else {
                            System.out.println("Conta não encontrada!");
                        }
                    }
                    case 8 -> {
                        System.out.print("Número da conta: ");
                        String numero = sc.nextLine();
                        Conta conta = dao.buscarPorNumero(numero);
                        if (conta != null) {
                            System.out.println("Conta encontrada: " + conta);
                        } else {
                            System.out.println("Conta não encontrada.");
                        }
                    }

                    case 9 -> {
                        System.out.print("Nome do titular: ");
                        String nome = sc.nextLine();
                        List<Conta> contas = dao.buscarPorTitular(nome);
                        if (contas.isEmpty()) {
                            System.out.println("Nenhuma conta encontrada para esse titular.");
                        } else {
                            contas.forEach(System.out::println);
                        }
                    }

                    case 10 -> {
                        System.out.print("Número da conta poupança: ");
                        String numero = sc.nextLine();
                        Conta conta = dao.buscarPorNumero(numero);
                        if (conta instanceof ContaPoupanca poupanca) {
                            poupanca.aplicarRendimento();
                            dao.atualizarSaldo(poupanca.getId(), poupanca.getSaldo());
                            System.out.println("Rendimento aplicado! Novo saldo: " + poupanca.getSaldo());
                        } else {
                            System.out.println("Essa conta não é poupança.");
                        }
                    }
                    case 0 -> System.out.println("Saindo...");
                    default -> System.out.println("Opção inválida!");
                }
            } catch (SQLException e) {
                System.out.println("Erro no banco: " + e.getMessage());
            }
        } while (opcao != 0);

        sc.close();
    }
}
