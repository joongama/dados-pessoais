package br.senac.tads.dsw.dados_pessoais.entidade;

import java.util.HashSet;
import java.util.Set;
import java.time.LocalDate;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

@Entity // Marca a classe como uma entidade JPA
        // (Será uma tabela no banco de dados)
@Table(name = "tb_pessoas") // Nome da tabela no banco de dados
public class PessoaEntity {

    // ==========================
    // LEMBRE-SE: gere os getters, setters e o construtor sem argumentos
    // (obrigatório no JPA) para todos os campos desta classe.
    // ==========================

    @Id // Chave primária
    // Anotação abaixo faz banco gerar o ID automaticamente (autoincrement)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true, length = 64) // Coluna NOT NULL, UNIQUE,
    private String username; // VARCHAR(64)

    @Column(nullable = false, length = 100)
    private String nome;
    @Column(nullable = false, length = 100)
    private String email;
    @Column(length = 20)
    private String telefone;
    @Column(name = "data_nascimento") // Nome da coluna no banco
    private LocalDate dataNascimento;
    @Column(length = 255)
    private String senha;
    // Relacionamento: uma pessoa pode ter vários conhecimentos,
    // e o mesmo conhecimento pode ser associado a várias pessoas.
    // @JoinTable - define a tabela de junção que une as duas entidades
    // CascadeType.PERSIST/MERGE - salvar/atualizar propaga para os conhecimentos;
    // REMOVE (via ALL) é omitido intencionalmente: em @ManyToMany o Hibernate
    // remove as entradas
    // da tabela de junção automaticamente ao deletar a pessoa - cascadear o REMOVE
    // para
    // tb_conhecimentos causaria violação de FK caso a FK da junção ainda existisse.
    // FetchType. EAGER - os conhecimentos são carregados junto com a pessoa -
    // simplifica o uso
    // para este exemplo didático, porém deve ser usado com cuidado em projetos
    // reais pois pode causar problemas de desempenho)
    @ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.EAGER)
    @JoinTable(name = "tb_pessoas_conhecimentos", // (1)

            joinColumns = @JoinColumn(name = "pessoa_id"), // (2)

            inverseJoinColumns = @JoinColumn(name = "conhecimento_id") // (3)
    )
    private Set<ConhecimentoEntity> conhecimentos = new HashSet<>();

    // (1) nome da tabela de junção
    // (2) FK que aponta para tb_pessoas
    // (3) FK que aponta para tb_conhecimentos

    public PessoaEntity() {
        // Construtor sem argumentos (obrigatório para JPA)
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Set<ConhecimentoEntity> getConhecimentos() {
        return conhecimentos;
    }

    public void setConhecimentos(Set<ConhecimentoEntity> conhecimentos) {
        this.conhecimentos = conhecimentos;
    }
}
