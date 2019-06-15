package com.gabriel.box.application.repository.entity;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Box {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private String name;
	
	private Integer pills;
	
	@Basic
	@Temporal(TemporalType.TIMESTAMP)
	private Date ultimaDose;
	
	@ManyToOne
	@JoinColumn(name="user_id")
	private User user;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getPills() {
		return pills;
	}

	public void setPills(Integer pills) {
		this.pills = pills;
	}

	public Date getUltimaDose() {
		return ultimaDose;
	}

	public void setUltimaDose(Date ultimaDose) {
		this.ultimaDose = ultimaDose;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
}
