package br.com.castgroup.sistema3.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name="curso")
public class Curso {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idCurso;
	@Column(name="nome", length = 150, nullable = false)
	private String nome;
	@Column(name="sigla", length = 50, nullable = false)
	private String sigla;

}
