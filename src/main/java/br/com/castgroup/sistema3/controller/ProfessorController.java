package br.com.castgroup.sistema3.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import br.com.castgroup.sistema3.model.Professor;
import br.com.castgroup.sistema3.repository.ProfessorRepository;
import br.com.castgroup.sistema3.request.ProfessorGetResponse;
import br.com.castgroup.sistema3.request.ProfessorPostRequest;
import br.com.castgroup.sistema3.request.ProfessorPutRequest;
import io.swagger.annotations.ApiOperation;


@Controller
@Transactional
public class ProfessorController {
	
	
	@Autowired
	private ProfessorRepository professorRepository;
	
	private static final String ENDPOINT = "api/professor";
	
	
	@ApiOperation("Serviço para cadastro de Professor")
	@RequestMapping(value = ENDPOINT, method = RequestMethod.POST)
	@CrossOrigin
	public ResponseEntity<String>post(@RequestBody ProfessorPostRequest request){
		try {
			Professor professor = new Professor();
			
			professor.setNome(request.getNome());
			professor.setEndereco(request.getEndereco());
			
			
			professorRepository.save(professor);
			
			return ResponseEntity.status(HttpStatus.OK).body("Professor cadastrado com sucesso!");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro" + e.getMessage());
		}
	}
	
	
	@ApiOperation("Serviço para consultar todos os Professores")
	@RequestMapping(value = ENDPOINT, method = RequestMethod.GET)
	@CrossOrigin
	public ResponseEntity<List<ProfessorGetResponse>>get(){
		List<ProfessorGetResponse> response = new ArrayList<ProfessorGetResponse>();
		
		for(Professor professor: professorRepository.findAll()) {
			
			ProfessorGetResponse item = new ProfessorGetResponse();
			
			item.setIdProfessor(professor.getIdProfessor());
			item.setNome(professor.getNome());
			item.setEndereco(professor.getEndereco());
			
			response.add(item);
		}
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	
	@ApiOperation("Serviço para consultar professor por Id")
	@RequestMapping(value = ENDPOINT + "/{idProfessor}", method = RequestMethod.GET)
	@CrossOrigin
	public ResponseEntity<ProfessorGetResponse>getById(@PathVariable("idProfessor") Integer idProfessor){
		Optional<Professor> item = professorRepository.findById(idProfessor);
		
		if(item.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}else {
			ProfessorGetResponse response = new ProfessorGetResponse();
			Professor professor = item.get();
			
			response.setIdProfessor(professor.getIdProfessor());
			response.setNome(professor.getNome());
			response.setEndereco(professor.getEndereco());
			
			return ResponseEntity.status(HttpStatus.OK).body(response);
		}
	}
	
	
	@ApiOperation("Serviço para atualização no cadastro de Professor")
	@RequestMapping(value = ENDPOINT, method = RequestMethod.PUT)
	@CrossOrigin
	public ResponseEntity<String>put(@RequestBody ProfessorPutRequest request){
		try {
			Optional<Professor> item = professorRepository.findById(request.getIdProfessor());
			
			if (item.isEmpty()) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Professor não encontrado.");
				
			} else {
				Professor professor = item.get();
				professor.setNome(request.getNome());
				professor.setEndereco(request.getEndereco());
				
				professorRepository.save(professor);
				
				return ResponseEntity.status(HttpStatus.OK).body("Cadastro de Professor atualizado com sucesso!");
			}
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro: " + e.getMessage());
		}
	}
	
	
	@ApiOperation("Serviço para excluir cadastro de Professor")
	@RequestMapping(value = ENDPOINT + "/{idProfessor}", method = RequestMethod.DELETE)
	@CrossOrigin
	public ResponseEntity<String>delete(@PathVariable("idProfessor") Integer idProfessor){
		try {
			Optional<Professor> item = professorRepository.findById(idProfessor);
			
			if(item.isEmpty()) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Professor não encontrado.");
			}else {
				Professor professor = item.get();
				professorRepository.delete(professor);
				return ResponseEntity.status(HttpStatus.OK).body("Professor excluído com sucesso!");
			}
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro: " + e.getMessage());
		}
		
	}

}



