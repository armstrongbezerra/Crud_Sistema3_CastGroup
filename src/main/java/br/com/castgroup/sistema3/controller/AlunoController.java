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

import br.com.castgroup.sistema3.model.Aluno;
import br.com.castgroup.sistema3.repository.AlunoRepository;
import br.com.castgroup.sistema3.request.ALunoPutRequest;
import br.com.castgroup.sistema3.request.AlunoGetResponse;
import br.com.castgroup.sistema3.request.AlunoPostRequest;
import io.swagger.annotations.ApiOperation;

@Controller
@Transactional
public class AlunoController {
	
	@Autowired
	private AlunoRepository alunoRepository;
	
	private static final String ENDPOINT = "api/aluno";
	
	
	@ApiOperation("Serviço para cadastro de Aluno")
	@RequestMapping(value = ENDPOINT, method = RequestMethod.POST)
	@CrossOrigin
	public ResponseEntity<String>post(@RequestBody AlunoPostRequest request){
		try {
			Aluno aluno = new Aluno();			
			aluno.setNome(request.getNome());
			aluno.setEndereco(request.getEndereco());
			
			alunoRepository.save(aluno);
			
			return ResponseEntity.status(HttpStatus.OK).body("Aluno cadastrado com sucesso!");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro" + e.getMessage());
		}
	}
	
	@ApiOperation("Serviço para consultar todos os Alunos")
	@RequestMapping(value = ENDPOINT, method = RequestMethod.GET)
	@CrossOrigin
	public ResponseEntity<List<AlunoGetResponse>>get(){
		List<AlunoGetResponse> response = new ArrayList<AlunoGetResponse>();
		
		for(Aluno aluno: alunoRepository.findAll()) {
			
			AlunoGetResponse item = new AlunoGetResponse();
			
			item.setIdAluno(aluno.getIdAluno());
			item.setNome(aluno.getNome());
			item.setEndereco(aluno.getEndereco());
			
			response.add(item);
		}
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	
	@ApiOperation("Serviço para consultar aluno por Id")
	@RequestMapping(value = ENDPOINT + "/{idAluno}", method = RequestMethod.GET)
	@CrossOrigin
	public ResponseEntity<AlunoGetResponse>getById(@PathVariable("idAluno") Integer idAluno){
		Optional<Aluno> item = alunoRepository.findById(idAluno);
		
		if(item.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}else {
			AlunoGetResponse response = new AlunoGetResponse();
			Aluno aluno = item.get();
			
			response.setIdAluno(aluno.getIdAluno());
			response.setNome(aluno.getNome());
			response.setEndereco(aluno.getEndereco());
			
			return ResponseEntity.status(HttpStatus.OK).body(response);
		}
	}
	
	
	@ApiOperation("Serviço para atualização no cadastro de Aluno")
	@RequestMapping(value = ENDPOINT, method = RequestMethod.PUT)
	@CrossOrigin
	public ResponseEntity<String>put(@RequestBody ALunoPutRequest request){
		try {
			Optional<Aluno> item = alunoRepository.findById(request.getIdAluno());
			
			if (item.isEmpty()) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Aluno não encontrado.");
				
			} else {
				Aluno aluno = item.get();
				aluno.setNome(request.getNome());
				aluno.setEndereco(request.getEndereco());
				
				alunoRepository.save(aluno);
				
				return ResponseEntity.status(HttpStatus.OK).body("Cadastro de Aluno atualizado com sucesso!");
			}
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro: " + e.getMessage());
		}
	}
	
	
	@ApiOperation("Serviço para excluir cadastro de Aluno")
	@RequestMapping(value = ENDPOINT + "/{idAluno}", method = RequestMethod.DELETE)
	@CrossOrigin
	public ResponseEntity<String>delete(@PathVariable("idAluno") Integer idAluno){
		try {
			Optional<Aluno> item = alunoRepository.findById(idAluno);
			
			if(item.isEmpty()) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Aluno não encontrado.");
			}else {
				Aluno aluno = item.get();
				alunoRepository.delete(aluno);
				return ResponseEntity.status(HttpStatus.OK).body("Aluno excluído com sucesso!");
			}
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro: " + e.getMessage());
		}
		
	}

}
