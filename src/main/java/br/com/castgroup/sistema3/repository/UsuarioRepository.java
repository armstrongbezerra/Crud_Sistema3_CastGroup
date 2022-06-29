package br.com.castgroup.sistema3.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import br.com.castgroup.sistema3.model.Usuario;

public interface UsuarioRepository extends CrudRepository<Usuario, Integer>{
	
	@Query("select u from Usuario u where u.login =:login")
	Usuario findByLogin(@Param("login") String login);
	
	@Query("select u from Usuario u where u.login =:login and u.senha = :senha")
	Usuario findByLoginAndSenha(@Param("login") String login, @Param("senha") String senha);

}
