package br.senac.tads.dsw.dados_pessoais.entidade;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_conhecimentos")
public class ConhecimentoEntity {
    // ===-------====
    // LEMBRE-SE: gere os getters, setters e o construtor sem argumentos //
    // (obrigatório no JPA) para todos os campos desta classe.
    // ===========================
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true, length = 100)
    private String nome;

    public ConhecimentoEntity() {
        // Construtor sem argumentos (obrigatório para JPA)
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}