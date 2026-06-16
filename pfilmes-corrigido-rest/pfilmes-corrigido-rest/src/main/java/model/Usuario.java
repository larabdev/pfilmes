package model;

public class Usuario {
    private int    id;
    private String email;
    private String senha; 

    public Usuario() {}

    public int    getId()           { return id; }
    public void   setId(int id)     { this.id = id; }

    public String getEmail()        { return email; }
    public void   setEmail(String e){ this.email = e; }

    public String getSenha()        { return senha; }
    public void   setSenha(String s){ this.senha = s; }
}