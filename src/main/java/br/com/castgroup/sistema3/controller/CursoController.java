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

import br.com.castgroup.sistema3.model.Curso;

import br.com.castgroup.sistema3.repository.CursoRepository;
import br.com.castgroup.sistema3.request.CursoGetResponse;
import br.com.castgroup.sistema3.request.CursoPostRequest;
import br.com.castgroup.sistema3.request.CursoPutRequest;
import io.swagger.annotations.ApiOperation;

@Controller
@Transactional
public class CursoController {
	
	@Autowired
	private CursoRepository cursoRepository;
	
	private static final String ENDPOINT = "api/curso";
	
	
	@ApiOperation("Serviço para cadastro de Curso")
	@RequestMapping(value = ENDPOINT, method = RequestMethod.POST)
	@CrossOrigin
	public ResponseEntity<String>post(@RequestBody CursoPostRequest request){
		try {
			Curso curso = new Curso();
			
			curso.setNome(request.getNome());
			curso.setSigla(request.getSigla());
			
			cursoRepository.save(curso);
			
			return ResponseEntity.status(HttpStatus.OK).body("Curso cadastrado com sucesso!");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro" + e.getMessage());
		}
	}
	
	
	@ApiOperation("Serviço para consultar todos os Cursos")
	@RequestMapping(value = ENDPOINT, method = RequestMethod.GET)
	@CrossOrigin
	public ResponseEntity<List<CursoGetResponse>>get(){
		List<CursoGetResponse> response = new ArrayList<CursoGetResponse>();
		
		for(Curso curso: cursoRepository.findAll()) {
			
			CursoGetResponse item = new CursoGetResponse();
			
			item.setIdCurso(curso.getIdCurso());
			item.setNome(curso.getNome());
			item.setSigla(curso.getSigla());
			
			response.add(item);
		}
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	
	@ApiOperation("Serviço para consultar curso por Id")
	@RequestMapping(value = ENDPOINT + "/{idCurso}", method = RequestMethod.GET)
	@CrossOrigin
	public ResponseEntity<CursoGetResponse>getById(@PathVariable("idCurso") Integer idCurso){
		Optional<Curso> item = cursoRepository.findById(idCurso);
		
		if(item.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}else {
			CursoGetResponse response = new CursoGetResponse();
			Curso curso = item.get();
			
			response.setIdCurso(curso.getIdCurso());
			response.setNome(curso.getNome());
			response.setSigla(curso.getSigla());
			
			return ResponseEntity.status(HttpStatus.OK).body(response);
		}
	}
	
	
	@ApiOperation("Serviço para atualização no cadastro de Curso")
	@RequestMapping(value = ENDPOINT, method = RequestMethod.PUT)
	@CrossOrigin
	public ResponseEntity<String>put(@RequestBody CursoPutRequest request){
		try {
			Optional<Curso> item = cursoRepository.findById(request.getIdCurso());
			
			if (item.isEmpty()) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Curso não encontrado.");
				
			} else {
				Curso curso = item.get();
				curso.setNome(request.getNome());
				curso.setSigla(request.getSigla());
				
				cursoRepository.save(curso);
				
				return ResponseEntity.status(HttpStatus.OK).body("Cadastro de Curso atualizado com sucesso!");
			}
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro: " + e.getMessage());
		}
	}
	
	
	@ApiOperation("Serviço para excluir cadastro de Curso")
	@RequestMapping(value = ENDPOINT + "/{idCurso}", method = RequestMethod.DELETE)
	@CrossOrigin
	public ResponseEntity<String>delete(@PathVariable("idCurso") Integer idCurso){
		try {
			Optional<Curso> item = cursoRepository.findById(idCurso);
			
			if(item.isEmpty()) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Curso não encontrado.");
			}else {
				Curso curso = item.get();
				cursoRepository.delete(curso);
				return ResponseEntity.status(HttpStatus.OK).body("Curso excluído com sucesso!");
			}
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro: " + e.getMessage());
		}
		
	}

}
