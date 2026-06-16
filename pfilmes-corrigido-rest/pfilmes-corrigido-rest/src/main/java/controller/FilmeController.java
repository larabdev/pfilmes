package controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Filme;
import service.FilmeService;
import util.JsonUtil;

public class FilmeController extends HttpServlet {

    // Service usado pelo Controller. O Controller nao acessa o banco diretamente;
    // ele chama o Service, que chama o DAO.
    private final FilmeService service = new FilmeService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // API RESTful - READ:
        // GET /api/filmes lista todos os filmes em JSON.
        // GET /api/filmes/{id} busca um filme especifico em JSON.
        if (ehApi(req)) {
            listarOuBuscarJson(req, resp);
            return;
        }

        // Fluxo das telas JSP antigas. Continua funcionando para o site visual.
        String action = req.getParameter("action");
        if (action == null) action = "listar";

        try {
            switch (action) {
                case "novo":
                    req.getRequestDispatcher("/view/formFilme.jsp").forward(req, resp);
                    break;

                case "editar": {
                    int id = Integer.parseInt(req.getParameter("id"));
                    req.setAttribute("filme", service.buscarPorId(id));
                    req.getRequestDispatcher("/view/editarFilme.jsp").forward(req, resp);
                    break;
                }

                case "deletar": {
                    int id = Integer.parseInt(req.getParameter("id"));
                    service.deletar(id);
                    resp.sendRedirect(req.getContextPath() + "/filmes");
                    break;
                }

                case "listar":
                    req.setAttribute("filmes", service.listar());
                    req.getRequestDispatcher("/view/filmes.jsp").forward(req, resp);
                    break;

                default:
                    JsonUtil.escrever(resp, HttpServletResponse.SC_BAD_REQUEST, JsonUtil.erro("Acao desconhecida."));
            }
        } catch (RuntimeException e) {
            JsonUtil.escrever(resp, HttpServletResponse.SC_BAD_REQUEST, JsonUtil.erro(e.getMessage()));
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        // API RESTful - CREATE:
        // POST /api/filmes recebe um JSON no corpo da requisicao e cria um filme.
        if (ehApi(req)) {
            criarJson(req, resp);
            return;
        }

        // Fluxo antigo dos formularios JSP, usando action=salvar ou action=atualizar.
        String action = req.getParameter("action");
        if (action == null) action = "salvar";

        try {
            switch (action) {
                case "salvar": {
                    service.salvar(montarFilmeFormulario(req));
                    resp.sendRedirect(req.getContextPath() + "/filmes");
                    break;
                }

                case "atualizar": {
                    Filme f = montarFilmeFormulario(req);
                    f.setId(Integer.parseInt(req.getParameter("id")));
                    service.atualizar(f);
                    resp.sendRedirect(req.getContextPath() + "/filmes");
                    break;
                }

                default:
                    JsonUtil.escrever(resp, HttpServletResponse.SC_BAD_REQUEST, JsonUtil.erro("Acao desconhecida."));
            }
        } catch (RuntimeException e) {
            resp.sendRedirect(req.getContextPath() + "/filmes");
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        try {
            // API RESTful - UPDATE:
            // PUT /api/filmes/{id} pega o id da URL e os novos dados do JSON.
            Integer id = idDaUrl(req);
            if (id == null) {
                JsonUtil.escrever(resp, HttpServletResponse.SC_BAD_REQUEST, JsonUtil.erro("Informe o id na URL."));
                return;
            }

            Filme filme = montarFilmeJson(JsonUtil.lerObjeto(req));
            filme.setId(id);
            service.atualizar(filme);
            JsonUtil.escrever(resp, HttpServletResponse.SC_OK, filmeParaJson(filme));
        } catch (RuntimeException e) {
            JsonUtil.escrever(resp, HttpServletResponse.SC_BAD_REQUEST, JsonUtil.erro(e.getMessage()));
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        try {
            // API RESTful - DELETE:
            // DELETE /api/filmes/{id} remove o filme informado na URL.
            Integer id = idDaUrl(req);
            if (id == null) {
                JsonUtil.escrever(resp, HttpServletResponse.SC_BAD_REQUEST, JsonUtil.erro("Informe o id na URL."));
                return;
            }

            service.deletar(id);
            JsonUtil.escrever(resp, HttpServletResponse.SC_OK, JsonUtil.mensagem("Filme removido com sucesso."));
        } catch (RuntimeException e) {
            JsonUtil.escrever(resp, HttpServletResponse.SC_BAD_REQUEST, JsonUtil.erro(e.getMessage()));
        }
    }

    private void listarOuBuscarJson(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            // Se nao tiver id na URL, o READ retorna a lista completa.
            Integer id = idDaUrl(req);
            if (id == null) {
                JsonUtil.escrever(resp, HttpServletResponse.SC_OK, filmesParaJson(service.listar()));
                return;
            }

            // Se tiver id na URL, o READ retorna apenas um filme.
            Filme filme = service.buscarPorId(id);
            if (filme == null) {
                JsonUtil.escrever(resp, HttpServletResponse.SC_NOT_FOUND, JsonUtil.erro("Filme nao encontrado."));
                return;
            }
            JsonUtil.escrever(resp, HttpServletResponse.SC_OK, filmeParaJson(filme));
        } catch (RuntimeException e) {
            JsonUtil.escrever(resp, HttpServletResponse.SC_BAD_REQUEST, JsonUtil.erro(e.getMessage()));
        }
    }

    private void criarJson(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            // Converte o JSON recebido em um objeto Filme e chama o CREATE no Service.
            Filme filme = montarFilmeJson(JsonUtil.lerObjeto(req));
            service.salvar(filme);
            JsonUtil.escrever(resp, HttpServletResponse.SC_CREATED, filmeParaJson(filme));
        } catch (RuntimeException e) {
            JsonUtil.escrever(resp, HttpServletResponse.SC_BAD_REQUEST, JsonUtil.erro(e.getMessage()));
        }
    }

    private Filme montarFilmeFormulario(HttpServletRequest req) {
        // Monta o Model Filme a partir dos campos do formulario JSP.
        Filme f = new Filme();
        f.setTitulo(req.getParameter("titulo"));
        f.setAnoLancamento(Integer.parseInt(req.getParameter("ano")));
        f.setDiretor(req.getParameter("diretor"));
        f.setGenero(req.getParameter("genero"));
        f.setSinopse(req.getParameter("sinopse"));
        return f;
    }

    private Filme montarFilmeJson(Map<String, String> json) {
        // Monta o Model Filme a partir do corpo JSON da API RESTful.
        Filme f = new Filme();
        f.setTitulo(json.get("titulo"));
        f.setAnoLancamento(inteiro(json, "anoLancamento", "ano"));
        f.setDiretor(json.get("diretor"));
        f.setGenero(json.get("genero"));
        f.setSinopse(json.get("sinopse"));
        return f;
    }

    private boolean ehApi(HttpServletRequest req) {
        // Se a rota comeca com /api/, o controller responde JSON.
        // Caso contrario, o controller segue o fluxo JSP.
        return req.getServletPath().startsWith("/api/");
    }

    private Integer idDaUrl(HttpServletRequest req) {
        // Extrai o id de URLs REST como /api/filmes/1.
        String path = req.getPathInfo();
        if (path == null || "/".equals(path)) return null;
        String id = path.replace("/", "").trim();
        if (id.isEmpty()) return null;
        return Integer.parseInt(id);
    }

    private int inteiro(Map<String, String> json, String... chaves) {
        for (String chave : chaves) {
            String valor = json.get(chave);
            if (valor != null && !valor.trim().isEmpty()) return Integer.parseInt(valor);
        }
        return 0;
    }

    private String filmeParaJson(Filme f) {
        // Converte um objeto Filme para o modelo JSON enviado como resposta da API.
        return "{\"id\":" + f.getId()
                + ",\"titulo\":\"" + JsonUtil.escapar(f.getTitulo()) + "\""
                + ",\"anoLancamento\":" + f.getAnoLancamento()
                + ",\"diretor\":\"" + JsonUtil.escapar(f.getDiretor()) + "\""
                + ",\"genero\":\"" + JsonUtil.escapar(f.getGenero()) + "\""
                + ",\"sinopse\":\"" + JsonUtil.escapar(f.getSinopse()) + "\"}";
    }

    private String filmesParaJson(List<Filme> filmes) {
        // Converte a lista de filmes para um array JSON.
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < filmes.size(); i++) {
            sb.append(filmeParaJson(filmes.get(i)));
            if (i < filmes.size() - 1) sb.append(",");
        }
        sb.append("]");
        return sb.toString();
    }
}
