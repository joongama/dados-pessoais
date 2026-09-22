package br.senac.tads.dsw.dados_pessoais;

public class Mensagem {
    private String nome;
    private String texto;

    public Mensagem(String nome, String texto) {
        this.nome = nome;
        this.texto = texto;
    }

    public String getNome() {
        return nome;
    }

    public String getTexto() {
        return texto;
    }

}
