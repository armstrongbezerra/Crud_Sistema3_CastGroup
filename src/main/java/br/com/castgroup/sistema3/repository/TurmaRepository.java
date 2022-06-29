package br.com.castgroup.sistema3.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.castgroup.sistema3.model.Turma;

@Repository
public interface TurmaRepository extends CrudRepository<Turma, Integer>{

}
