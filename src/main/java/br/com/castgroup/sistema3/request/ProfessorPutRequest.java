package br.com.castgroup.sistema3.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProfessorPutRequest {
	
	private Integer idProfessor;
	private String nome;
	private String endereco;

}
