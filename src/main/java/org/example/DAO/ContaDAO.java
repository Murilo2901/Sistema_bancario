package org.example.DAO;

import org.example.Model.Cliente;
import org.example.Model.Conta;
import org.example.Model.ContaCorrente;
import org.example.Model.ContaPoupanca;
import org.example.Util.Conexao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ContaDAO {

    public void inserirConta(Conta conta) throws SQLException {
        String sql = """
                INSERT INTO conta (numero, cliente_id, tipo, saldo)
                VALUES (?, ?, ?, ?)
                """;

        try (Connection conn = Conexao.conectar()) {
            conn.setAutoCommit(false);
            try {
                int clienteId = obterOuCriarCliente(conn, conta.getTitular());

                try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                    stmt.setString(1, conta.getNumero());
                    stmt.setInt(2, clienteId);
                    String tipo = (conta instanceof ContaCorrente) ? "CORRENTE" : "POUPANCA";
                    stmt.setString(3, tipo);
                    stmt.setDouble(4, conta.getSaldo());
                    stmt.executeUpdate();
                }

                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                throw e;
            } finally {
                conn.setAutoCommit(true);
            }
        }
    }

    public List<Conta> listarContas() throws SQLException {
        String sql = """
                SELECT c.id as conta_id, c.numero, c.tipo, c.saldo,
                       cl.id as cliente_id, cl.nome as cliente_nome, cl.cpf as cliente_cpf
                FROM conta c
                JOIN cliente cl ON c.cliente_id = cl.id
                ORDER BY c.id
                """;

        List<Conta> contas = new ArrayList<>();
        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int contaId = rs.getInt("conta_id");
                String numero = rs.getString("numero");
                String tipo = rs.getString("tipo");
                double saldo = rs.getDouble("saldo");

                int clienteId = rs.getInt("cliente_id");
                String clienteNome = rs.getString("cliente_nome");
                String clienteCpf = rs.getString("cliente_cpf");

                Cliente cliente = new Cliente(clienteId, clienteNome, clienteCpf);

                Conta conta;
                if ("CORRENTE".equalsIgnoreCase(tipo)) {
                    conta = new ContaCorrente(contaId, numero, cliente, saldo);
                } else {
                    conta = new ContaPoupanca(contaId, numero, cliente, saldo);
                }
                contas.add(conta);
            }
        }
        return contas;
    }

    public Conta buscarPorNumero(String numero) throws SQLException {
        String sql = """
                SELECT c.id as conta_id, c.numero, c.tipo, c.saldo,
                       cl.id as cliente_id, cl.nome as cliente_nome, cl.cpf as cliente_cpf
                FROM conta c
                JOIN cliente cl ON c.cliente_id = cl.id
                WHERE c.numero = ?
                """;

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, numero);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                int contaId = rs.getInt("conta_id");
                String tipo = rs.getString("tipo");
                double saldo = rs.getDouble("saldo");

                int clienteId = rs.getInt("cliente_id");
                String clienteNome = rs.getString("cliente_nome");
                String clienteCpf = rs.getString("cliente_cpf");

                Cliente cliente = new Cliente(clienteId, clienteNome, clienteCpf);

                if ("CORRENTE".equalsIgnoreCase(tipo)) {
                    return new ContaCorrente(contaId, numero, cliente, saldo);
                } else {
                    return new ContaPoupanca(contaId, numero, cliente, saldo);
                }
            }
        }
        return null;
    }

    public List<Conta> buscarPorTitular(String nomeTitular) throws SQLException {
        String sql = """
                SELECT c.id as conta_id, c.numero, c.tipo, c.saldo,
                       cl.id as cliente_id, cl.nome as cliente_nome, cl.cpf as cliente_cpf
                FROM conta c
                JOIN cliente cl ON c.cliente_id = cl.id
                WHERE cl.nome LIKE ?
                """;
        List<Conta> contas = new ArrayList<>();
        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, "%" + nomeTitular + "%");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int contaId = rs.getInt("conta_id");
                String numero = rs.getString("numero");
                String tipo = rs.getString("tipo");
                double saldo = rs.getDouble("saldo");

                int clienteId = rs.getInt("cliente_id");
                String clienteNome = rs.getString("cliente_nome");
                String clienteCpf = rs.getString("cliente_cpf");

                Cliente cliente = new Cliente(clienteId, clienteNome, clienteCpf);

                Conta conta = "CORRENTE".equalsIgnoreCase(tipo)
                        ? new ContaCorrente(contaId, numero, cliente, saldo)
                        : new ContaPoupanca(contaId, numero, cliente, saldo);

                contas.add(conta);
            }
        }
        return contas;
    }

    public void atualizarConta(Conta conta) throws SQLException {
        String sql = """
                UPDATE conta
                SET numero = ?, cliente_id = ?, tipo = ?, saldo = ?
                WHERE id = ?
                """;

        try (Connection conn = Conexao.conectar()) {
            conn.setAutoCommit(false);
            try {
                int clienteId = obterOuCriarCliente(conn, conta.getTitular());

                try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                    stmt.setString(1, conta.getNumero());
                    stmt.setInt(2, clienteId);
                    String tipo = (conta instanceof ContaCorrente) ? "CORRENTE" : "POUPANCA";
                    stmt.setString(3, tipo);
                    stmt.setDouble(4, conta.getSaldo());
                    stmt.setInt(5, conta.getId());
                    stmt.executeUpdate();
                }

                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                throw e;
            } finally {
                conn.setAutoCommit(true);
            }
        }
    }

    public void atualizarSaldo(int idConta, double novoSaldo) throws SQLException {
        String sql = "UPDATE conta SET saldo = ? WHERE id = ?";
        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDouble(1, novoSaldo);
            stmt.setInt(2, idConta);
            stmt.executeUpdate();
        }
    }

    public void removerConta(int id) throws SQLException {
        String sql = "DELETE FROM conta WHERE id = ?";
        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    private int obterOuCriarCliente(Connection conn, Cliente cliente) throws SQLException {
        if (cliente.getCpf() != null && !cliente.getCpf().isBlank()) {
            String findCpf = "SELECT id FROM cliente WHERE cpf = ?";
            try (PreparedStatement ps = conn.prepareStatement(findCpf)) {
                ps.setString(1, cliente.getCpf());
                ResultSet rs = ps.executeQuery();
                if (rs.next()) return rs.getInt("id");
            }
        }

        String findNome = "SELECT id FROM cliente WHERE nome = ?";
        try (PreparedStatement ps = conn.prepareStatement(findNome)) {
            ps.setString(1, cliente.getNome());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getInt("id");
        }

        String insert = "INSERT INTO cliente (nome, cpf) VALUES (?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(insert, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, cliente.getNome());
            ps.setString(2, cliente.getCpf());
            ps.executeUpdate();
            ResultSet keys = ps.getGeneratedKeys();
            if (keys.next()) return keys.getInt(1);
        }

        throw new SQLException("Não foi possível inserir/obter cliente.");
    }
}
