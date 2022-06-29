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
@Table(name="usuario")
public class Usuario {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="idUsuario")
	private Integer idUsuario;
	@Column(name="nome", length = 150, nullable=false)
	private String nome;
	@Column(name="login", length=50, nullable=false)
	private String login;
	@Column(name="senha", length=50, nullable=false)
	private String senha;


}
