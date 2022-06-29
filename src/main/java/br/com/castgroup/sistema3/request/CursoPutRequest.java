package br.com.castgroup.sistema3.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CursoPutRequest {
	
	private Integer idCurso;
	private String nome;
	private String sigla;

}
