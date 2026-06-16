package util;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class JwtUtil {

    private static final String SECRET = "pfilmes_secret_key_2024_muito_segura";
    private static final long   EXPIRATION_MS = 3_600_000L; // 1 hora

    public static String gerarToken(String email) {
        String header  = base64Url("{\"alg\":\"HS256\",\"typ\":\"JWT\"}");
        long   exp     = System.currentTimeMillis() + EXPIRATION_MS;
        String payload = base64Url(
            "{\"sub\":\"" + email + "\",\"exp\":" + exp + "}"
        );
        String assinatura = assinar(header + "." + payload);
        return header + "." + payload + "." + assinatura;
    }

    public static String validarToken(String token) {
        if (token == null || token.trim().isEmpty()) {
            throw new RuntimeException("Token ausente.");
        }

        String[] partes = token.split("\\.");
        if (partes.length != 3) {
            throw new RuntimeException("Formato de token inválido.");
        }

        // Verifica assinatura
        String assinaturaEsperada = assinar(partes[0] + "." + partes[1]);
        if (!assinaturaEsperada.equals(partes[2])) {
            throw new RuntimeException("Assinatura do token inválida.");
        }
        
        // verifica expiraçao
        String payloadJson = new String(
            Base64.getUrlDecoder().decode(partes[1]), StandardCharsets.UTF_8
        );

        long exp = extrairExp(payloadJson);
        if (System.currentTimeMillis() > exp) {
            throw new RuntimeException("Token expirado.");
        }

        return extrairSub(payloadJson);
    }

    private static String base64Url(String texto) {
        return Base64.getUrlEncoder()
                     .withoutPadding()
                     .encodeToString(texto.getBytes(StandardCharsets.UTF_8));
    }

    private static String assinar(String dados) {
        try {
            Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(new SecretKeySpec(
                SECRET.getBytes(StandardCharsets.UTF_8), "HmacSHA256"
            ));
            return Base64.getUrlEncoder()
                         .withoutPadding()
                         .encodeToString(mac.doFinal(
                             dados.getBytes(StandardCharsets.UTF_8)
                         ));
        } catch (Exception e) {
            throw new RuntimeException("Erro ao assinar token.", e);
        }
    }

    private static long extrairExp(String json) {
        // Extrai o valor numérico de "exp":VALOR
        int idx = json.indexOf("\"exp\":");
        if (idx == -1) throw new RuntimeException("Campo exp não encontrado no token.");
        int start = idx + 6;
        int end   = json.indexOf("}", start);
        if (end == -1) end = json.length();
        return Long.parseLong(json.substring(start, end).trim());
    }

    private static String extrairSub(String json) {
        int idx = json.indexOf("\"sub\":\"");
        if (idx == -1) throw new RuntimeException("Campo sub não encontrado no token.");
        int start = idx + 7;
        int end   = json.indexOf("\"", start);
        return json.substring(start, end);
    }
}
