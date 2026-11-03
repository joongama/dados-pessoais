package br.senac.tads.dsw.dados_pessoais.repositorio;

import br.senac.tads.dsw.dados_pessoais.entidade.UsuarioSistemaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioSistemaRepository extends JpaRepository<UsuarioSistemaEntity, Integer> {
    Optional<UsuarioSistemaEntity> findByUsername(String username);
}