
package br.senac.tads.dsw.dados_pessoais.repositorio;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import br.senac.tads.dsw.dados_pessoais.entidade.ConhecimentoEntity;

@Repository
public interface ConhecimentoRepository extends JpaRepository<ConhecimentoEntity, Integer> {

    // Gera: SELECT * FROM tb_conhecimentos WHERE LOWER (nome) = LOWER (: nome)
    // Usado para reutilizar um conhecimento já existente (sem distinção de
    // maiúsculas)
    Optional<ConhecimentoEntity> findByNomeIgnoreCase(String nome);
}