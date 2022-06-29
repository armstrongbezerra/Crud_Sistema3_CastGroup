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
@Table(name="professor")
public class Professor {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idProfessor;
	@Column(name= "nome", length = 50, nullable = false)
	private String nome;
	@Column(name= "endereco", length = 150, nullable = false)
	private String endereco;

}
