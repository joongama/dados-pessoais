package br.senac.tads.dsw.dados_pessoais.repositorio;

import br.senac.tads.dsw.dados_pessoais.entidade.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, Integer> {
}
