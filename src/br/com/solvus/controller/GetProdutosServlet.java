package br.com.solvus.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import br.com.solvus.jdbc.Produto;

/**
 * Servlet implementation class StudentControllerServlet
 */
@WebServlet("/GetProdutosServlet")
public class GetProdutosServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private ProdutoDbUtil produtoDbUtil;
	private FornecedorDbUtil fornecedorDbUtil;

	@Resource(name = "jdbc/TesteProgramador1Web")
	private DataSource dataSource;

	@Override
	public void init() throws ServletException {
		super.init();

		try {
			produtoDbUtil = new ProdutoDbUtil(dataSource);
			fornecedorDbUtil = new FornecedorDbUtil(dataSource);
		} catch (Exception exc) {
			throw new ServletException(exc);
		}
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();

		try {
			String idFornecedorSelecionadoString = request.getParameter("idFornecedorSelecionado");
			int idFornecedorSelecionado = Integer.parseInt(idFornecedorSelecionadoString);

			List<Produto> produtos = fornecedorDbUtil.getProdutosFornecedor(idFornecedorSelecionado);

			

			for (Produto produto : produtos) {
				out.print(
						"<option value='" + produto.getIdProduto() + " '>  " + produto.getNomeProduto() + " </option>");

			}
		} catch (Exception exc) {
			exc.printStackTrace();
			out.print("Error getting product name " + exc.toString());
		} finally {
			out.close();
		}
	}
}
