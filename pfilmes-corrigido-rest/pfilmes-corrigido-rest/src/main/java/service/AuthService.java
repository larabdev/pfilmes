package service;

import dao.UsuarioDAO;
import model.Usuario;
import util.JwtUtil;

public class AuthService {

    private final UsuarioDAO dao = new UsuarioDAO();

    public String login(String email, String senha) {
        if (email == null || email.trim().isEmpty() || senha == null || senha.trim().isEmpty()) {
            throw new RuntimeException("E-mail e senha são obrigatórios.");
        }

        Usuario usuario = dao.buscarPorEmailESenha(email, senha);

        if (usuario == null) {
            throw new RuntimeException("E-mail ou senha inválidos.");
        }

        return JwtUtil.gerarToken(usuario.getEmail());
    }
}