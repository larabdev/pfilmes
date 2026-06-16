package util;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public final class JsonUtil {

    private JsonUtil() {}

    public static Map<String, String> lerObjeto(HttpServletRequest req) throws IOException {
        // Le o corpo da requisicao HTTP enviado pela API RESTful.
        // Exemplo de corpo: {"titulo":"Interestelar","anoLancamento":2014}
        StringBuilder body = new StringBuilder();
        try (BufferedReader reader = req.getReader()) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                body.append(linha);
            }
        }
        return parseObjetoSimples(body.toString());
    }

    public static void escrever(HttpServletResponse resp, int status, String json) throws IOException {
        // Escreve uma resposta HTTP no formato JSON e define o status correto.
        // Exemplos de status: 200 OK, 201 Created, 400 Bad Request, 404 Not Found.
        resp.setStatus(status);
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.getWriter().print(json);
    }

    public static String erro(String mensagem) {
        // Modelo padrao de erro em JSON.
        return "{\"erro\":\"" + escapar(mensagem) + "\"}";
    }

    public static String mensagem(String mensagem) {
        // Modelo padrao de mensagem de sucesso em JSON.
        return "{\"mensagem\":\"" + escapar(mensagem) + "\"}";
    }

    public static String escapar(String valor) {
        // Evita quebrar o JSON quando o texto tem aspas, barras ou quebras de linha.
        if (valor == null) return "";
        return valor.replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\r", "\\r")
                .replace("\n", "\\n");
    }

    private static Map<String, String> parseObjetoSimples(String json) {
        // Parser simples para o modelo JSON usado neste trabalho.
        // Ele transforma {"campo":"valor"} em Map<String, String>.
        Map<String, String> valores = new HashMap<>();
        if (json == null) return valores;

        String texto = json.trim();
        if (texto.startsWith("{")) texto = texto.substring(1);
        if (texto.endsWith("}")) texto = texto.substring(0, texto.length() - 1);

        StringBuilder token = new StringBuilder();
        boolean dentroString = false;
        boolean escapado = false;

        for (int i = 0; i <= texto.length(); i++) {
            char c = i < texto.length() ? texto.charAt(i) : ',';
            if (escapado) {
                token.append(c);
                escapado = false;
                continue;
            }
            if (c == '\\') {
                token.append(c);
                escapado = true;
                continue;
            }
            if (c == '"') dentroString = !dentroString;

            if (c == ',' && !dentroString) {
                adicionarPar(valores, token.toString());
                token.setLength(0);
            } else {
                token.append(c);
            }
        }

        return valores;
    }

    private static void adicionarPar(Map<String, String> valores, String par) {
        int separador = localizarDoisPontos(par);
        if (separador < 0) return;

        String chave = limpar(par.substring(0, separador));
        String valor = limpar(par.substring(separador + 1));
        if (!chave.isEmpty()) valores.put(chave, valor);
    }

    private static int localizarDoisPontos(String texto) {
        boolean dentroString = false;
        boolean escapado = false;
        for (int i = 0; i < texto.length(); i++) {
            char c = texto.charAt(i);
            if (escapado) {
                escapado = false;
                continue;
            }
            if (c == '\\') {
                escapado = true;
                continue;
            }
            if (c == '"') dentroString = !dentroString;
            if (c == ':' && !dentroString) return i;
        }
        return -1;
    }

    private static String limpar(String valor) {
        String texto = valor.trim();
        if ("null".equals(texto)) return "";
        if (texto.startsWith("\"") && texto.endsWith("\"") && texto.length() >= 2) {
            texto = texto.substring(1, texto.length() - 1);
        }
        return texto.replace("\\n", "\n")
                .replace("\\r", "\r")
                .replace("\\\"", "\"")
                .replace("\\\\", "\\");
    }
}
