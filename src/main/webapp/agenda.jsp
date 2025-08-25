<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="model.JavaBeans"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.ArrayList" %> 
<%
    // Recuperando o atributo "contatos" e verificando o tipo de dados de forma segura
    List<?> contatos = (List<?>) request.getAttribute("contatos");
    List<JavaBeans> lista = new ArrayList<>();

    if (contatos != null && !contatos.isEmpty()) {
        // Verificando se o elemento da lista é do tipo esperado
        for (Object contato : contatos) {
            if (contato instanceof JavaBeans) {
                lista.add((JavaBeans) contato);
            }
        }
    }
%>
<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <title>Agenda de contatos</title>
    <link rel="icon" href="imagens/favicon.png">
    <link rel="stylesheet" href="style.css">
</head>
<body>
    <h1>Agenda de Contatos</h1>
    <a href="novo.html" class="Botao1">Novo contato</a>
    <a href="report" class="Botao2">Relatório</a>
    <table id="tabela">
        <thead>
            <tr>
                <th>Id</th>
                <th>Nome</th>
                <th>Fone</th>
                <th>Email</th>
                <th>Opções</th>
            </tr>
        </thead>
        <tbody>
            <%
            // Iterando sobre a lista de contatos
            for (JavaBeans contato : lista) {
            %>
            <tr>
                <td><%= contato.getIdcon() %></td>
                <td><%= contato.getNome() %></td>
                <td><%= contato.getFone() %></td>
                <td><%= contato.getEmail() %></td>
                <td>
                    <a href="select?idcon=<%= contato.getIdcon() %>" class="Botao1">Editar</a>
                    <a href="javascript: confirmar(<%= contato.getIdcon() %>)" class="Botao2">Excluir</a>
                </td>
            </tr>
            <%
            }
            %>
        </tbody>
    </table>
    <script src="scripts/confirmador.js"></script>
</body>
</html>
