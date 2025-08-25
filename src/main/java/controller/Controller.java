package controller;

import java.io.IOException;
import java.util.ArrayList;

import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import model.JavaBeans;
import model.DAO;

/**
 * Controller para manipulação dos contatos:
 * listar, criar, editar, excluir e gerar relatórios.
 */
@WebServlet(urlPatterns = {
    "/Controller", "/main", "/insert", "/select", "/update", "/delete", "/report"
})
public class Controller extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private final DAO dao = new DAO();
    private final JavaBeans contato = new JavaBeans();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getServletPath();

        switch (action) {
            case "/main":
                listarContatos(request, response);
                break;
            case "/insert":
                novoContato(request, response);
                break;
            case "/select":
                listarContato(request, response);
                break;
            case "/update":
                editarContato(request, response);
                break;
            case "/delete":
                removerContato(request, response);
                break;
            case "/report":
                gerarRelatorio(request, response);
                break;
            default:
                response.sendRedirect("index.html");
        }
    }

    /** Listar todos os contatos */
    protected void listarContatos(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        ArrayList<JavaBeans> lista = dao.listarContatos();
        request.setAttribute("contatos", lista);

        RequestDispatcher rd = request.getRequestDispatcher("agenda.jsp");
        rd.forward(request, response);
    }

    /** Criar novo contato */
    protected void novoContato(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        contato.setNome(request.getParameter("nome"));
        contato.setFone(request.getParameter("fone"));
        contato.setEmail(request.getParameter("email"));

        dao.inserirContato(contato);
        response.sendRedirect("main");
    }

    /** Exibir dados de um contato para edição */
    protected void listarContato(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String idcon = request.getParameter("idcon");
        contato.setIdcon(idcon);
        dao.selecionarContato(contato);

        request.setAttribute("idcon", contato.getIdcon());
        request.setAttribute("nome", contato.getNome());
        request.setAttribute("fone", contato.getFone());
        request.setAttribute("email", contato.getEmail());

        RequestDispatcher rd = request.getRequestDispatcher("editar.jsp");
        rd.forward(request, response);
    }

    /** Editar contato existente */
    protected void editarContato(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        contato.setIdcon(request.getParameter("idcon"));
        contato.setNome(request.getParameter("nome"));
        contato.setFone(request.getParameter("fone"));
        contato.setEmail(request.getParameter("email"));

        dao.alterarContato(contato);
        response.sendRedirect("main");
    }

    /** Remover contato */
    protected void removerContato(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String idcon = request.getParameter("idcon");
        contato.setIdcon(idcon);

        dao.deletarContato(contato);
        response.sendRedirect("main");
    }

    /** Gerar relatório PDF com os contatos */
    protected void gerarRelatorio(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Document documento = new Document();

        try {
            response.setContentType("application/pdf");
            response.addHeader("Content-Disposition", "inline; filename=contatos.pdf");

            PdfWriter.getInstance(documento, response.getOutputStream());
            documento.open();

            documento.add(new Paragraph("Lista de Contatos:"));
            documento.add(new Paragraph(" "));

            PdfPTable tabela = new PdfPTable(3);
            tabela.addCell(new PdfPCell(new Paragraph("Nome")));
            tabela.addCell(new PdfPCell(new Paragraph("Fone")));
            tabela.addCell(new PdfPCell(new Paragraph("E-mail")));

            ArrayList<JavaBeans> lista = dao.listarContatos();
            for (JavaBeans contato : lista) {
                tabela.addCell(contato.getNome());
                tabela.addCell(contato.getFone());
                tabela.addCell(contato.getEmail());
            }

            documento.add(tabela);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            documento.close();
        }
    }
}
