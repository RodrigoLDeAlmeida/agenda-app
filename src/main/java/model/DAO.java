package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class DAO {

    /** Parâmetros de conexão */
    private final String driver = "com.mysql.cj.jdbc.Driver";
    private final String url = "jdbc:mysql://127.0.0.1:3306/dbagenda?useTimezone=true&serverTimezone=UTC";
    private final String user = "root";
    private final String password = "root";

    /** Conectar ao banco */
    private Connection conectar() {
        try {
            Class.forName(driver);
            return DriverManager.getConnection(url, user, password);
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

    /** CREATE - Inserir contato */
    public void inserirContato(JavaBeans contato) {
        String sql = "INSERT INTO contatos (nome, fone, email) VALUES (?,?,?)";
        try (Connection con = conectar(); PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setString(1, contato.getNome());
            pst.setString(2, contato.getFone());
            pst.setString(3, contato.getEmail());
            pst.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    /** READ - Listar contatos */
    public ArrayList<JavaBeans> listarContatos() {
        ArrayList<JavaBeans> contatos = new ArrayList<>();
        String sql = "SELECT * FROM contatos ORDER BY nome";
        try (Connection con = conectar(); PreparedStatement pst = con.prepareStatement(sql); ResultSet rs = pst.executeQuery()) {
            while (rs.next()) {
                contatos.add(new JavaBeans(
                    rs.getString("idcon"),
                    rs.getString("nome"),
                    rs.getString("fone"),
                    rs.getString("email")
                ));
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return contatos;
    }

    /** READ - Selecionar contato por ID */
    public void selecionarContato(JavaBeans contato) {
        String sql = "SELECT * FROM contatos WHERE idcon=?";
        try (Connection con = conectar(); PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setString(1, contato.getIdcon());
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                contato.setIdcon(rs.getString("idcon"));
                contato.setNome(rs.getString("nome"));
                contato.setFone(rs.getString("fone"));
                contato.setEmail(rs.getString("email"));
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    /** UPDATE - Alterar contato */
    public void alterarContato(JavaBeans contato) {
        String sql = "UPDATE contatos SET nome=?, fone=?, email=? WHERE idcon=?";
        try (Connection con = conectar(); PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setString(1, contato.getNome());
            pst.setString(2, contato.getFone());
            pst.setString(3, contato.getEmail());
            pst.setString(4, contato.getIdcon());
            pst.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    /** DELETE - Deletar contato */
    public void deletarContato(JavaBeans contato) {
        String sql = "DELETE FROM contatos WHERE idcon=?";
        try (Connection con = conectar(); PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setString(1, contato.getIdcon());
            pst.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
