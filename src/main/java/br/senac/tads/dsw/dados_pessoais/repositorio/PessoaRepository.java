package br.senac.tads.dsw.dados_pessoais.repositorio;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import br.senac.tads.dsw.dados_pessoais.entidade.PessoaEntity;

import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

@Repository
public interface PessoaRepository extends JpaRepository<PessoaEntity, Integer> {

    // Spring Data JPA gera a query automaticamente a partir do nome do método:
    // SELECT * FROM tb_pessoas WHERE username = :username
    Optional<PessoaEntity> findByUsername(String username);

    // Gera: SELECT COUNT(*) FROM tb_pessoas WHERE username = :username
    boolean existsByUsername(String username);

    //Versão JpaQL: opera sobre campos da entidade Java (não sobre colunas SQL)
    @Query("""
            SELECT p FROM PessoaEntity p
            WHERE LOWER (p.username) LIKE LOWER (CONCAT('%', :username, '%'))
            OR(p.nome) LIKE LOWER (CONCAT('%', :username, '%'))
            OR(p.email) LIKE LOWER (CONCAT('%', :username, '%'))
            """)
    List<PessoaEntity> buscarPorTermo(@Param("termo") String termo);
}