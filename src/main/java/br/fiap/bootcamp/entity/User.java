package br.fiap.bootcamp.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "BOOTCAMP_USERS")
public class User {
	
	@Id
	@Column(name = "USER_ID")
	private Integer userId;
	@Column(name = "USERNAME")
	private String username;
	@Column(name = "USER_PASSWORD")
	private String password;
	@Column(name = "USER_TYPE")
	private String type;
	
	public String getUsername() {
		return username;
	}
	public String getPassword() {
		return password;
	}
	public String getType() {
		return type;
	}

}
