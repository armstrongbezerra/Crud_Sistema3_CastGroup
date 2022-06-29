package br.com.castgroup.sistema3.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.castgroup.sistema3.model.Professor;

@Repository
public interface ProfessorRepository extends CrudRepository<Professor, Integer>{

}
