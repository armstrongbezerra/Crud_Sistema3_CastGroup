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

import br.com.castgroup.sistema3.model.Turma;
import br.com.castgroup.sistema3.repository.TurmaRepository;
import br.com.castgroup.sistema3.request.TurmaGetResponse;
import br.com.castgroup.sistema3.request.TurmaPostRequest;
import br.com.castgroup.sistema3.request.TurmaPutRequest;
import io.swagger.annotations.ApiOperation;


@Controller
@Transactional
public class TurmaController {
	
	
	@Autowired
	private TurmaRepository turmaRepository;
	
	private static final String ENDPOINT = "api/turma";
	
	
	@ApiOperation("Serviço para cadastro de Turma")
	@RequestMapping(value = ENDPOINT, method = RequestMethod.POST)
	@CrossOrigin
	public ResponseEntity<String>post(@RequestBody TurmaPostRequest request){
		try {
			Turma turma = new Turma();
			
			turma.setAno(request.getAno());
			turma.setSemestre(request.getSemestre());
			
			turmaRepository.save(turma);
			
			return ResponseEntity.status(HttpStatus.OK).body("Turma cadastrada com sucesso!");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro" + e.getMessage());
		}
	}
	
	
	@ApiOperation("Serviço para consultar todos as Turmas")
	@RequestMapping(value = ENDPOINT, method = RequestMethod.GET)
	@CrossOrigin
	public ResponseEntity<List<TurmaGetResponse>>get(){
		List<TurmaGetResponse> response = new ArrayList<TurmaGetResponse>();
		
		for(Turma turma: turmaRepository.findAll()) {
			
			TurmaGetResponse item = new TurmaGetResponse();
			
			item.setIdTurma(turma.getIdTurma());
			item.setAno(turma.getAno());
			item.setSemestre(turma.getSemestre());
			
			response.add(item);
		}
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	
	@ApiOperation("Serviço para consultar turma por Id")
	@RequestMapping(value = ENDPOINT + "/{idTurma}", method = RequestMethod.GET)
	@CrossOrigin
	public ResponseEntity<TurmaGetResponse>getById(@PathVariable("idTurma") Integer idTurma){
		Optional<Turma> item = turmaRepository.findById(idTurma);
		
		if(item.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}else {
			TurmaGetResponse response = new TurmaGetResponse();
			Turma turma = item.get();
			
			response.setIdTurma(turma.getIdTurma());
			response.setAno(turma.getAno());
			response.setSemestre(turma.getSemestre());
			
			return ResponseEntity.status(HttpStatus.OK).body(response);
		}
	}
	
	@ApiOperation("Serviço para atualização no cadastro de Turma")
	@RequestMapping(value = ENDPOINT, method = RequestMethod.PUT)
	@CrossOrigin
	public ResponseEntity<String>put(@RequestBody TurmaPutRequest request){
		try {
			Optional<Turma> item = turmaRepository.findById(request.getIdTurma());
			
			if (item.isEmpty()) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Turma não encontrada.");
				
			} else {
				Turma turma = item.get();
				turma.setAno(request.getAno());
				turma.setSemestre(request.getSemestre());
				
				turmaRepository.save(turma);
				
				return ResponseEntity.status(HttpStatus.OK).body("Cadastro de Turma atualizado com sucesso!");
			}
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro: " + e.getMessage());
		}
	}
	
	@ApiOperation("Serviço para excluir cadastro de Turma")
	@RequestMapping(value = ENDPOINT + "/{idTurma}", method = RequestMethod.DELETE)
	@CrossOrigin
	public ResponseEntity<String>delete(@PathVariable("idTurma") Integer idTurma){
		try {
			Optional<Turma> item = turmaRepository.findById(idTurma);
			
			if(item.isEmpty()) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Turma não encontrada.");
			}else {
				Turma turma = item.get();
				turmaRepository.delete(turma);
				return ResponseEntity.status(HttpStatus.OK).body("Turma excluída com sucesso!");
			}
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro: " + e.getMessage());
		}
		
	}
	
	

}
