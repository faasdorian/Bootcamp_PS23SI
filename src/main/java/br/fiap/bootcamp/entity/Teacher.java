package br.fiap.bootcamp.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "BOOTCAMP_TEACHERS")
public class Teacher {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "SEQ")
	@SequenceGenerator(name = "SEQ", sequenceName = "TEACHER_ID_SEQ", allocationSize = 1)
	@Column(name = "TEACHER_ID")
	private Integer teacherId;
	
	@Column(name = "TEACHER_NAME")
	private String name;

	@Column(name = "SUBJECT")
	private String subject;
	
	@Column(name = "TEACHER_COST")
	private Double cost;
	
	@Column(name = "EMAIL")
	private String email;
	
	@Column(name = "PHONE")
	private String phone;
	
	public Teacher() {}
	
	public Teacher(String name, String subject, Double cost, String email, String phone) {
		super();
		this.name = name;
		this.subject = subject;
		this.cost = cost;
		this.email = email;
		this.phone = phone;
	}
	
	public void setTeacherId(Integer teacherId) {
		this.teacherId = teacherId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public Double getCost() {
		return cost;
	}

	public void setCost(Double cost) {
		this.cost = cost;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Integer getTeacherId() {
		return teacherId;
	}
	
	

}
