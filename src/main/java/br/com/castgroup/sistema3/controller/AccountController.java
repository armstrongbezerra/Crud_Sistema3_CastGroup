package br.com.castgroup.sistema3.controller;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import br.com.castgroup.sistema3.model.Usuario;
import br.com.castgroup.sistema3.repository.UsuarioRepository;
import br.com.castgroup.sistema3.request.AccountPostRequest;
import br.com.castgroup.sistema3.security.MD5Cryptography;
import io.swagger.annotations.ApiOperation;

@Controller
@Transactional
public class AccountController {
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	private static final String ENDPOINT = "/api/account";
	
	@ApiOperation("Serviço para criar conta de usuário")
	@RequestMapping(value = ENDPOINT, method = RequestMethod.POST)
	@CrossOrigin
	public ResponseEntity<String> post(@RequestBody AccountPostRequest request){
		try {
			if(usuarioRepository.findByLogin(request.getLogin()) != null) {
			
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("O login informado já está cadastrado no sistema");
			}
			
			Usuario usuario = new Usuario();
			usuario.setNome(request.getNome());
			usuario.setLogin(request.getLogin());
			usuario.setSenha(MD5Cryptography.encrypt(request.getSenha()));
			
			usuarioRepository.save(usuario);
			
			return ResponseEntity.status(HttpStatus.OK).body("Conta de usuário cadastrada com sucesso!");
			
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
			
		}
	}


}
