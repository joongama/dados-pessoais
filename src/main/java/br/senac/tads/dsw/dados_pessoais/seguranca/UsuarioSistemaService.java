package br.senac.tads.dsw.dados_pessoais.seguranca;

import br.senac.tads.dsw.dados_pessoais.entidade.UsuarioSistemaEntity;
import br.senac.tads.dsw.dados_pessoais.repositorio.UsuarioSistemaRepository;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioSistemaService implements UserDetailsService {

    // Antes: Map<String, UsuarioSistema> em memória
    // Agora: busca no banco pelo repositório
    private final UsuarioSistemaRepository repository;

    public UsuarioSistemaService(UsuarioSistemaRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        // 1. Busca a entidade pelo username; se não existir, lança a exceção
        UsuarioSistemaEntity entity = repository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado: " + username));

        // 2. Extrai o nome de cada RoleEntity e converte para SimpleGrantedAuthority
        List<GrantedAuthority> authorities = entity.getRoles().stream()
                .map(role -> (GrantedAuthority) new SimpleGrantedAuthority(role.getNome()))
                .toList();

        // 3. Converte a entidade para UsuarioSistema (o objeto que o Spring Security usa)
        return new UsuarioSistema(entity.getUsername(), entity.getSenha(), authorities);
    }
}