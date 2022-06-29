package br.com.castgroup.sistema3.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginPostRequest {
	
	private String login;
	private String senha;

}
