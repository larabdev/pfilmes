package model;

public class Avaliacao {
    private int id;
    private int filmeId;
    private int categoriaId;
    private int nota;
    private String comentario;

    public Avaliacao() {}

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getFilmeId() { return filmeId; }
    public void setFilmeId(int filmeId) { this.filmeId = filmeId; }

    public int getCategoriaId() { return categoriaId; }
    public void setCategoriaId(int categoriaId) { this.categoriaId = categoriaId; }

    public int getNota() { return nota; }
    public void setNota(int nota) { this.nota = nota; }

    public String getComentario() { return comentario; }
    public void setComentario(String comentario) { this.comentario = comentario; }
}
