package controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Avaliacao;
import model.Categoria;
import service.AvaliacaoService;
import util.JsonUtil;

public class AvaliacaoController extends HttpServlet {

    // Service usado pelo Controller. Ele concentra as regras antes de chamar o DAO.
    private final AvaliacaoService service = new AvaliacaoService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // API RESTful - READ:
        // GET /api/avaliacoes lista avaliacoes.
        // GET /api/avaliacoes/{id} busca uma avaliacao.
        // GET /api/avaliacoes/ranking?categoriaId=1 retorna o ranking em JSON.
        // GET /api/categorias retorna as categorias em JSON.
        if (ehApi(req)) {
            atenderGetApi(req, resp);
            return;
        }

        // Fluxo das telas JSP antigas.
        String action = req.getParameter("action");
        if (action == null) action = "ranking";

        try {
            switch (action) {
                case "form":
                    req.setAttribute("filmeId", req.getParameter("filmeId"));
                    req.setAttribute("categorias", service.listarCategorias());
                    req.getRequestDispatcher("/view/avaliar.jsp").forward(req, resp);
                    break;

                case "categorias":
                    JsonUtil.escrever(resp, HttpServletResponse.SC_OK, categoriasParaJson(service.listarCategorias()));
                    break;

                case "ranking":
                default:
                    int categoriaId = lerCategoria(req);
                    req.setAttribute("ranking", service.ranking(categoriaId));
                    req.setAttribute("categorias", service.listarCategorias());
                    req.setAttribute("categoriaSelecionada", categoriaId);
                    req.getRequestDispatcher("/view/ranking.jsp").forward(req, resp);
                    break;
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
        // POST /api/avaliacoes recebe um JSON e cria uma avaliacao.
        if (ehApi(req)) {
            criarJson(req, resp);
            return;
        }

        // Fluxo antigo do formulario JSP de avaliacao.
        try {
            Avaliacao a = montarAvaliacaoFormulario(req);
            service.avaliar(a);
            resp.sendRedirect(req.getContextPath() + "/avaliacoes?action=ranking&categoriaId=" + a.getCategoriaId());
        } catch (RuntimeException e) {
            JsonUtil.escrever(resp, HttpServletResponse.SC_BAD_REQUEST, JsonUtil.erro(e.getMessage()));
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        try {
            // API RESTful - UPDATE:
            // PUT /api/avaliacoes/{id} atualiza uma avaliacao pelo id da URL.
            Integer id = idDaUrl(req);
            if (id == null) {
                JsonUtil.escrever(resp, HttpServletResponse.SC_BAD_REQUEST, JsonUtil.erro("Informe o id na URL."));
                return;
            }

            Avaliacao avaliacao = montarAvaliacaoJson(JsonUtil.lerObjeto(req));
            avaliacao.setId(id);
            service.atualizar(avaliacao);
            JsonUtil.escrever(resp, HttpServletResponse.SC_OK, avaliacaoParaJson(avaliacao));
        } catch (RuntimeException e) {
            JsonUtil.escrever(resp, HttpServletResponse.SC_BAD_REQUEST, JsonUtil.erro(e.getMessage()));
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        try {
            // API RESTful - DELETE:
            // DELETE /api/avaliacoes/{id} remove uma avaliacao pelo id.
            Integer id = idDaUrl(req);
            if (id == null) {
                JsonUtil.escrever(resp, HttpServletResponse.SC_BAD_REQUEST, JsonUtil.erro("Informe o id na URL."));
                return;
            }

            service.deletar(id);
            JsonUtil.escrever(resp, HttpServletResponse.SC_OK, JsonUtil.mensagem("Avaliacao removida com sucesso."));
        } catch (RuntimeException e) {
            JsonUtil.escrever(resp, HttpServletResponse.SC_BAD_REQUEST, JsonUtil.erro(e.getMessage()));
        }
    }

    private void atenderGetApi(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            // READ auxiliar: lista todas as categorias em JSON.
            if (req.getServletPath().equals("/api/categorias")) {
                JsonUtil.escrever(resp, HttpServletResponse.SC_OK, categoriasParaJson(service.listarCategorias()));
                return;
            }

            // READ auxiliar: retorna o ranking de filmes por categoria em JSON.
            if ("/ranking".equals(req.getPathInfo())) {
                int categoriaId = lerCategoria(req);
                JsonUtil.escrever(resp, HttpServletResponse.SC_OK, rankingParaJson(service.ranking(categoriaId)));
                return;
            }

            // Se nao tiver id, lista todas as avaliacoes.
            Integer id = idDaUrl(req);
            if (id == null) {
                JsonUtil.escrever(resp, HttpServletResponse.SC_OK, avaliacoesParaJson(service.listar()));
                return;
            }

            // Se tiver id, busca apenas uma avaliacao.
            Avaliacao avaliacao = service.buscarPorId(id);
            if (avaliacao == null) {
                JsonUtil.escrever(resp, HttpServletResponse.SC_NOT_FOUND, JsonUtil.erro("Avaliacao nao encontrada."));
                return;
            }
            JsonUtil.escrever(resp, HttpServletResponse.SC_OK, avaliacaoParaJson(avaliacao));
        } catch (RuntimeException e) {
            JsonUtil.escrever(resp, HttpServletResponse.SC_BAD_REQUEST, JsonUtil.erro(e.getMessage()));
        }
    }

    private void criarJson(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            // Converte o JSON recebido em um objeto Avaliacao e chama o CREATE no Service.
            Avaliacao avaliacao = montarAvaliacaoJson(JsonUtil.lerObjeto(req));
            service.avaliar(avaliacao);
            JsonUtil.escrever(resp, HttpServletResponse.SC_CREATED, avaliacaoParaJson(avaliacao));
        } catch (RuntimeException e) {
            JsonUtil.escrever(resp, HttpServletResponse.SC_BAD_REQUEST, JsonUtil.erro(e.getMessage()));
        }
    }

    private Avaliacao montarAvaliacaoFormulario(HttpServletRequest req) {
        // Monta o Model Avaliacao a partir do formulario JSP.
        Avaliacao a = new Avaliacao();
        a.setFilmeId(Integer.parseInt(req.getParameter("filme")));
        a.setCategoriaId(Integer.parseInt(req.getParameter("categoria")));
        a.setNota(Integer.parseInt(req.getParameter("nota")));
        a.setComentario(req.getParameter("comentario"));
        return a;
    }

    private Avaliacao montarAvaliacaoJson(Map<String, String> json) {
        // Monta o Model Avaliacao a partir do corpo JSON da API RESTful.
        Avaliacao a = new Avaliacao();
        a.setFilmeId(inteiro(json, "filmeId", "filme"));
        a.setCategoriaId(inteiro(json, "categoriaId", "categoria"));
        a.setNota(inteiro(json, "nota"));
        a.setComentario(json.get("comentario"));
        return a;
    }

    private int lerCategoria(HttpServletRequest req) {
        String categoria = req.getParameter("categoria");
        if (categoria == null || categoria.trim().isEmpty()) {
            categoria = req.getParameter("categoriaId");
        }
        if (categoria == null || categoria.trim().isEmpty()) return 1;
        return Integer.parseInt(categoria);
    }

    private boolean ehApi(HttpServletRequest req) {
        // Se a rota comeca com /api/, o controller responde JSON.
        // Caso contrario, continua usando JSP.
        return req.getServletPath().startsWith("/api/");
    }

    private Integer idDaUrl(HttpServletRequest req) {
        // Extrai o id de URLs REST como /api/avaliacoes/1.
        String path = req.getPathInfo();
        if (path == null || "/".equals(path) || "/ranking".equals(path)) return null;
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

    private String avaliacoesParaJson(List<Avaliacao> avaliacoes) {
        // Converte a lista de avaliacoes para um array JSON.
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < avaliacoes.size(); i++) {
            sb.append(avaliacaoParaJson(avaliacoes.get(i)));
            if (i < avaliacoes.size() - 1) sb.append(",");
        }
        sb.append("]");
        return sb.toString();
    }

    private String avaliacaoParaJson(Avaliacao a) {
        // Converte um objeto Avaliacao para JSON.
        return "{\"id\":" + a.getId()
                + ",\"filmeId\":" + a.getFilmeId()
                + ",\"categoriaId\":" + a.getCategoriaId()
                + ",\"nota\":" + a.getNota()
                + ",\"comentario\":\"" + JsonUtil.escapar(a.getComentario()) + "\"}";
    }

    private String categoriasParaJson(List<Categoria> categorias) {
        // Converte a lista de categorias para JSON.
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < categorias.size(); i++) {
            Categoria c = categorias.get(i);
            sb.append("{\"id\":").append(c.getId())
                    .append(",\"nome\":\"").append(JsonUtil.escapar(c.getNome())).append("\"}");
            if (i < categorias.size() - 1) sb.append(",");
        }
        sb.append("]");
        return sb.toString();
    }

    private String rankingParaJson(List<String> ranking) {
        // Converte o ranking para um array JSON.
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < ranking.size(); i++) {
            sb.append("\"").append(JsonUtil.escapar(ranking.get(i))).append("\"");
            if (i < ranking.size() - 1) sb.append(",");
        }
        sb.append("]");
        return sb.toString();
    }
}
